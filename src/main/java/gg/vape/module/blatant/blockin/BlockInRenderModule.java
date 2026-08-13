package gg.vape.module.blatant.blockin;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventPostEntityUpdate;
import gg.vape.event.impl.EventPreEntityUpdate;
import gg.vape.event.impl.EventRender3D;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.MouseRotationController;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.World;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class BlockInRenderModule
extends Mod {
    private static final long MODULE_ID = 4400050581515796479L;

    private BlockPlacementGraph expectedPreUpdateSnapshot;
    private EntityPlayer simulatedPlayer;
    private BlockPathPlanner planner;
    private final Color outlineColor;
    private BlockPlacementGraph actualPreUpdateSnapshot;
    private final Color fillColor = new Color(0, 0, 0, 150);

    @Override
    public void onEnable() {
        Vape.debugLog("\nSimulation test module enabled, ready to simulate player movement.\n");
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPreEntityUpdate(EventPreEntityUpdate eventPreEntityUpdate) {
        GameSettings gameSettings = eventPreEntityUpdate.getGameSettings();
        EntityPlayerSP entityPlayerSP = eventPreEntityUpdate.getThePlayer();
        World world = entityPlayerSP.getWorld();
        if (entityPlayerSP.isNull() || world.isNull()) {
            return;
        }
        this.actualPreUpdateSnapshot = new BlockPlacementGraph(entityPlayerSP);
        if (this.planner == null) {
            this.planner = new BlockPathPlanner(
                    entityPlayerSP, entityPlayerSP, world, this.actualPreUpdateSnapshot);
            this.planner.applySnapshot(this.actualPreUpdateSnapshot);
            this.simulatedPlayer = this.planner.getSimulatedPlayer();
            MouseRotationController mouseRotationController = this.copyAdaptiveRotationController(entityPlayerSP);
            if (mouseRotationController != null) {
                this.planner.setRotationController(mouseRotationController);
            }
        }
        this.simulatedPlayer.H(entityPlayerSP.J());
        this.simulatedPlayer.l(entityPlayerSP.D());
        this.simulatedPlayer.C(entityPlayerSP.V());
        this.simulatedPlayer.D(entityPlayerSP.j());
        this.simulatedPlayer.z(entityPlayerSP.s());
        this.simulatedPlayer.o(entityPlayerSP.P$src$F$14ztfk8());
        boolean forward = gameSettings.Y().u();
        boolean backward = gameSettings.s().u();
        boolean left = gameSettings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg().u();
        boolean right = gameSettings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3().u();
        boolean jump = gameSettings.O().u();
        boolean sneak = gameSettings.d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0().u();
        boolean sprint = gameSettings.r().u();
        this.planner.setInput(forward, backward, left, right, jump, sneak);
        this.planner.setSprintKeyDown(sprint);
        this.expectedPreUpdateSnapshot = new BlockPlacementGraph(this.planner);
        this.planner.simulateTick(false);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPostEntityUpdate(EventPostEntityUpdate eventPostEntityUpdate) {
        EntityPlayerSP entityPlayerSP = eventPostEntityUpdate.getThePlayer();
        if (entityPlayerSP.isNull() || this.planner == null) {
            return;
        }
        BlockPlacementGraph actualPostUpdateSnapshot = new BlockPlacementGraph(entityPlayerSP);
        BlockPlacementGraph simulatedPostUpdateSnapshot = new BlockPlacementGraph(this.planner);
        this.planner.updateRotation();
        if (!this.actualPreUpdateSnapshot.matchesSimulationState(this.expectedPreUpdateSnapshot)) {
            Vape.debugLog("PRE UPDATE SNAPSHOT IS OFF");
        }
        if (!actualPostUpdateSnapshot.matchesSimulationState(simulatedPostUpdateSnapshot)) {
            Vape.debugLog("POST UPDATE SNAPSHOT IS OFF");
        }
    }


    @EventHandler
    public void onRender3D(EventRender3D eventRender3D) {
        EntityPlayerSP entityPlayerSP = eventRender3D.getThePlayer();
        if (entityPlayerSP.isNull() || this.planner == null) {
            return;
        }
        this.renderSimulatedPlayer();
    }

    public BlockInRenderModule() {
        super("Simulation", (int)MODULE_ID, Category.UTILITY);
        this.outlineColor = new Color(255, 255, 255, 150);
    }

    private MouseRotationController copyAdaptiveRotationController(EntityPlayer entityPlayer) {
        MouseRotationController mouseRotationController = RotationManager.INSTANCE.getActiveController();
        if (mouseRotationController == null) {
            return null;
        }
        if (mouseRotationController instanceof AdaptiveRotationController) {
            AdaptiveRotationController adaptiveRotationController = (AdaptiveRotationController)mouseRotationController;
            AdaptiveRotationController adaptiveRotationController2 = new AdaptiveRotationController(entityPlayer);
            adaptiveRotationController2.copyFrom(adaptiveRotationController);
            return adaptiveRotationController2;
        }
        return null;
    }

    @Override
    public void onDisable() {
        if (this.planner != null) {
            this.planner = null;
            this.simulatedPlayer = null;
            Vape.debugLog("\n\nSimulation test module disabled, simulator reset.\n");
        }
    }

    private void renderSimulatedPlayer() {
        double currentX = this.simulatedPlayer.z();
        double currentY = this.simulatedPlayer.N();
        double currentZ = this.simulatedPlayer.h();
        double previousX = this.simulatedPlayer.f();
        double previousY = this.simulatedPlayer.H();
        double previousZ = this.simulatedPlayer.R();
        float partialTicks = Minecraft.getTimer().renderPartialTicks();
        double renderX = previousX + (currentX - previousX) * partialTicks;
        double renderY = previousY + (currentY - previousY) * partialTicks;
        double renderZ = previousZ + (currentZ - previousZ) * partialTicks;
        RenderUtil.d();
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().B(1.0);
        RenderUtils.g();
        OpenGlBackendHolder.backend.enableCapability(3042);
        GL11.glBlendFunc((int)770, (int)771);
        OpenGlBackendHolder.backend.setLineWidth(1.5f);
        OpenGlBackendHolder.backend.disableCapability(3553);
        OpenGlBackendHolder.backend.enableCapability(2848);
        OpenGlBackendHolder.backend.disableCapability(2929);
        GL11.glDepthMask((boolean)false);
        double renderCameraX;
        double renderCameraY;
        double renderCameraZ;
        if (ForgeVersion.MC_1_20_6.d()) {
            renderCameraX = RenderManager.getInterpolatedRenderPosX();
            renderCameraY = RenderManager.getInterpolatedRenderPosY();
            renderCameraZ = RenderManager.getInterpolatedRenderPosZ();
        } else {
            RenderManager renderManager = Minecraft.D();
            renderCameraX = renderManager.getRenderPosX();
            renderCameraY = renderManager.getRenderPosY();
            renderCameraZ = renderManager.getRenderPosZ();
        }
        double width = this.simulatedPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMaxX()
                - this.simulatedPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMinX()
                + this.simulatedPlayer.b();
        double halfWidth = width / 2.0;
        RenderUtil.u(renderX - halfWidth, renderY, renderZ - halfWidth,
                width, this.simulatedPlayer.Y(), width, 1.5,
                this.fillColor, this.outlineColor, renderCameraX, renderCameraY, renderCameraZ);
        GL11.glDepthMask((boolean)true);
        OpenGlBackendHolder.backend.enableCapability(2929);
        OpenGlBackendHolder.backend.enableCapability(3553);
        OpenGlBackendHolder.backend.disableCapability(2848);
        OpenGlBackendHolder.backend.disableCapability(3042);
        RenderUtils.f();
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
        RenderUtil.Y();
    }
}
