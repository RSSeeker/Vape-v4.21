package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventRender3D;
import gg.vape.event.impl.EventRenderTracers3D;
import gg.vape.mapping.InsertedEventCallback;
import gg.vape.module.control.SharedModuleControlClaims;
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

    private static Exception a(Exception exception) {
        return exception;
    }

    @Override
    public boolean fire() {
        RenderManager.updateInterpolatedRenderPosition(this.N);
        boolean bl = EventRender3D.getEventListeners().hasListeners() || EventRenderTracers3D.getEventListeners().hasListeners();
        boolean bl2 = false;
        boolean bl3 = false;
        if (EventRender3D.getEventListeners().hasListeners()) {
            bl2 = new EventRender3D(this.y, this.N).fire();
        }
        GameSettings gameSettings = Minecraft.gameSettings();
        EntityRenderer entityRenderer = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf();
        if (EventRenderTracers3D.getEventListeners().hasListeners()) {
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
        if (ForgeVersion.MC_26_2.d()) {
            this.y = MatrixStack.A();
            this.y.i(new Matrix4f(object));
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
        this.N = f;
    }
}
