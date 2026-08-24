package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventRender3D;
import gg.vape.event.impl.EventRenderTracers3D;
import gg.vape.mapping.InsertedEventCallback;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.rotation.LocalPlayerRotationUtil;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.wrapper.impl.EntityRenderer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Matrix4f;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderManager;
import org.lwjgl.opengl.GL11;

public class EventRender3DCallback
implements InsertedEventCallback {
    private final float N;
    private static boolean r;
    private static float D;
    private final MatrixStack y;
    private final Object modelViewObject;

    private static Exception a(Exception exception) {
        return exception;
    }

    @Override
    public boolean fire() {
        RenderManager.updateInterpolatedRenderPosition(this.N);
        // Both the EventRender3D pass and the tracers pass drain world batches,
        // and each drain's cleanup (resetProjectionMatrix) clears the cached
        // modelViewMatrix. Re-inject the pure-rotation modelViewMatrix captured
        // when this callback was built, so updateProjectionMatrix uses the correct
        // branch for every world batch (NameTags billboards, mineral borders and
        // tracers) instead of the translated fallback (which only matched -Z).
        // Gated to 1.21.10+, the versions that use the modelViewMatrix branch;
        // older versions are left untouched.
        if (ForgeVersion.MC_1_21_10.d() && !ForgeVersion.MC_26_1.d() && this.modelViewObject != null) {
            LocalPlayerRotationUtil.setModelViewMatrix(this.modelViewObject);
        }
        boolean bl = EventRender3D.getEventListeners().hasListeners() || EventRenderTracers3D.getEventListeners().hasListeners();
        boolean bl2 = false;
        boolean bl3 = false;
        if (EventRender3D.getEventListeners().hasListeners()) {
            bl2 = new EventRender3D(this.y, this.N).fire();
        }
        GameSettings gameSettings = Minecraft.gameSettings();
        EntityRenderer entityRenderer = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf();
        if (EventRenderTracers3D.getEventListeners().hasListeners()) {
            // The EventRender3D pass above flushed world batches, whose cleanup
            // (resetProjectionMatrix) cleared the cached modelViewMatrix again.
            // The tracers pass re-renders the world and drains world batches
            // (tracers lines), so re-inject the pure-rotation modelViewMatrix so
            // updateProjectionMatrix uses the correct branch for that pass.
            if (ForgeVersion.MC_1_21_10.d() && !ForgeVersion.MC_26_1.d() && this.modelViewObject != null) {
                LocalPlayerRotationUtil.setModelViewMatrix(this.modelViewObject);
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                try {
                    boolean bl4 = gameSettings.k();
                    gameSettings.O(false);
                    entityRenderer.s(this.N, 0);
                    bl3 = new EventRenderTracers3D(this.y, this.N).fire();
                    gameSettings.O(bl4);
                }
                catch (Exception exception) {
                    Vape.logThrowable(exception);
                }
            } else {
                SharedModuleControlClaims.renderPass.blockRender();
                GL11.glPushMatrix();
                GlStateManager.F$src$V$acq27m();
                entityRenderer.Y(this.N);
                bl3 = new EventRenderTracers3D(this.y, this.N).fire();
                GL11.glPopMatrix();
                SharedModuleControlClaims.renderPass.clearClaimed();
            }
        }
        if (bl) {
            entityRenderer.B(1.0);
            OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        D = this.N;
        return bl2 || bl3;
    }

    public EventRender3DCallback(Object object) {
        this.modelViewObject = object;
        if (ForgeVersion.MC_26_1.d()) {
            this.y = MatrixStack.A();
            this.y.i(new Matrix4f(object));
        } else if (ForgeVersion.MC_1_21_10.d()) {
            this.y = MatrixStack.A();
            this.y.i(new Matrix4f(object));
            LocalPlayerRotationUtil.setModelViewMatrix(object);
        } else {
            this.y = new MatrixStack(object);
        }
        this.N = Minecraft.getTimer().renderPartialTicks();
    }

    static {
        D = -1.0f;
    }

    public EventRender3DCallback(float f) {
        this.y = null;
        this.modelViewObject = null;
        this.N = f;
    }
}
