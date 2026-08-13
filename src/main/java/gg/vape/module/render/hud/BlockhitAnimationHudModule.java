package gg.vape.module.render.hud;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventRenderItemInFirstPerson;
import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.MathUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.ItemRenderer;
import gg.vape.wrapper.impl.ItemRendererBridge;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.RenderHelper;
import org.lwjgl.opengl.GL11;

public class BlockhitAnimationHudModule
extends HudModule {
    private TimerUtil blockhitTimer = new TimerUtil();
    private boolean renderBlockhit;


    public BlockhitAnimationHudModule() {
        super("Blockhit Animation", HudModuleGroup.GAME, "animations");
        this.setSuffix("Shows 1.7 style blockhit animation constantly when blockhitting");
    }

    private void applyViewRotation(float pitch, float yaw) {
        GL11.glPushMatrix();
        GlStateManager.d(pitch, 1.0f, 0.0f, 0.0f);
        GlStateManager.d(yaw, 0.0f, 1.0f, 0.0f);
        RenderHelper.e();
        GL11.glPopMatrix();
    }

    @EventHandler
    public void onTick(EventPreTick eventPreTick) {
        if (this.isHoldingSword() && ClientSettings.isUseItemButtonDown()) {
            if (Minecraft.thePlayer().o$src$Z$1iprrmi() && Minecraft.thePlayer().j$src$I$1in0s92() > 0) {
                this.blockhitTimer.reset();
            }
            if (!this.blockhitTimer.hasTimeElapsed(200L)) {
                this.renderBlockhit = true;
                return;
            }
        }
        this.renderBlockhit = this.shouldRenderBlockhit();
    }

    private void applyItemTransform(float equipProgress, float swingProgress) {
        GL11.glTranslatef((float)0.56f, (float)-0.52f, (float)-0.71999997f);
        GL11.glTranslatef((float)0.0f, (float)(equipProgress * -0.6f), (float)0.0f);
        GlStateManager.d(45.0f, 0.0f, 1.0f, 0.0f);
        float squaredSwing = MathUtil.sin(swingProgress * swingProgress * (float)Math.PI);
        float swingArc = MathUtil.sin(MathUtil.sqrt(swingProgress) * (float)Math.PI);
        GlStateManager.d(squaredSwing * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.d(swingArc * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.d(swingArc * -80.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef((float)0.4f, (float)0.4f, (float)0.4f);
    }

    private void renderBlockhitItem(ItemRenderer itemRenderer, float partialTicks) {
        float equipProgress = 1.0f - (itemRenderer.e()
                + (itemRenderer.R() - itemRenderer.e()) * partialTicks);
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        float swingProgress = entityPlayerSP.L(partialTicks);
        float pitch = entityPlayerSP.D()
                + (entityPlayerSP.V() - entityPlayerSP.D()) * partialTicks;
        float yaw = entityPlayerSP.j()
                + (entityPlayerSP.J() - entityPlayerSP.j()) * partialTicks;
        this.applyViewRotation(pitch, yaw);
        itemRenderer.X(entityPlayerSP);
        this.applyCameraLag(entityPlayerSP, partialTicks);
        OpenGlBackendHolder.backend.enableCapability(32826);
        GL11.glPushMatrix();
        this.applyItemTransform(equipProgress, swingProgress);
        this.applyBlockingTransform();
        itemRenderer.g(entityPlayerSP, itemRenderer.k(), ItemRendererBridge.firstPerson());
        GL11.glPopMatrix();
        OpenGlBackendHolder.backend.disableCapability(32826);
        RenderHelper.s();
    }

    @EventHandler
    public void onRenderItemInFirstPerson(EventRenderItemInFirstPerson event) {
        if (this.renderBlockhit) {
            this.renderBlockhitItem(event.getItemRenderer(), event.partialTicks);
            event.setCancelled(true);
        }
    }

    private void applyCameraLag(EntityPlayerSP entityPlayerSP, float partialTicks) {
        float prevPitch = entityPlayerSP.n$src$F$1u53eru()
                + (entityPlayerSP.t$src$F$1u8e6c0() - entityPlayerSP.n$src$F$1u53eru()) * partialTicks;
        float prevYaw = entityPlayerSP.x$src$F$1ualcpg()
                + (entityPlayerSP.q$src$F$1u6qsjx() - entityPlayerSP.x$src$F$1ualcpg()) * partialTicks;
        GlStateManager.d((entityPlayerSP.V() - prevPitch) * 0.1f, 1.0f, 0.0f, 0.0f);
        GlStateManager.d((entityPlayerSP.J() - prevYaw) * 0.1f, 0.0f, 1.0f, 0.0f);
    }

    private boolean shouldRenderBlockhit() {
        if (Minecraft.thePlayer().isNull()) {
            return false;
        }
        RayTraceResult rayTraceResult = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (rayTraceResult.isNotNull() && rayTraceResult.getTypeOfHit().equals(RayTraceResult_type.block())) {
            return false;
        }
        if (this.isHoldingSword()) {
            if (Minecraft.thePlayer().o$src$Z$1iprrmi() && Minecraft.thePlayer().j$src$I$1in0s92() > 0) {
                this.blockhitTimer.reset();
            }
            return !this.blockhitTimer.hasTimeElapsed(200L);
        }
        return false;
    }

    private void applyBlockingTransform() {
        GL11.glTranslatef((float)-0.5f, (float)0.2f, (float)0.0f);
        GlStateManager.d(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.d(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.d(60.0f, 0.0f, 1.0f, 0.0f);
    }

    public boolean isHoldingSword() {
        if (Minecraft.currentScreen().isNotNull()) {
            return false;
        }
        return Minecraft.thePlayer().getHeldItemHand().isNotNull() && ItemStackScoreUtil.h(Minecraft.thePlayer().getHeldItemHand().getItem());
    }
}
