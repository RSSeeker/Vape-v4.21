package gg.vape.module.render.esp;

import com.google.common.collect.Lists;
import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.event.impl.EventPreRenderEntity;
import gg.vape.event.impl.EventPreRenderLiving;
import gg.vape.event.impl.EventPreRenderPlayerSpec;
import gg.vape.event.impl.EventRender3D;
import gg.vape.event.impl.EventSetArmorModel;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.render.ESP;
import gg.vape.render.OffscreenRenderContext;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtils;
import gg.vape.utils.render.StencilUtil;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderLivingBase;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.WorldClient;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class ESPOutline
extends SubModule<ESP> {
    private final ESP parentEsp = (ESP)this.getParent();
    private boolean renderingOutline;
    private boolean legacyRendererObserved;
    private int legacyRendererObservationCount;

    @EventHandler
    public void onPreRenderLiving(EventPreRenderLiving event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (Vape.INSTANCE.getClientSettings().isBot(event.getEntity()) && this.parentEsp.hideBots.getEffectiveValue().booleanValue()) {
            return;
        }
        if (this.parentEsp.enemyOnly.getEffectiveValue().booleanValue() && (this.parentEsp.enemyListOnly.getEffectiveValue() != false ? !Vape.INSTANCE.getEnemyManager().isEnemy(event.getEntity().getName()) : !Vape.INSTANCE.getClientSettings().isValidTarget(event.getEntity(), false))) {
            return;
        }
        if (event.getWorld().isNull()) {
            return;
        }
        EntityPlayerSP viewer = event.getThePlayer();
        Entity entity = event.getEntity();
        if (entity.equals(viewer)) {
            return;
        }
        if (this.renderingOutline) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTick(EventPrePlayerTick eventPrePlayerTick) {
        if (Minecraft.gameSettings().M()) {
            this.parentEsp.mode.setValue(this.parentEsp.threeDimensionalMode);
            Vape.INSTANCE.getNotificationManager().showInfo("ESP reverted to 3D mode", "Disable fast render to use outline", 5000L);
        }
    }

    public ESPOutline(Mod parent, String name) {
        super(parent, name);
    }


    @EventHandler
    public void onSetArmorModel(EventSetArmorModel event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (Vape.INSTANCE.getClientSettings().isBot(event.getEntity()) && this.parentEsp.hideBots.getEffectiveValue().booleanValue()) {
            return;
        }
        if (this.parentEsp.enemyOnly.getEffectiveValue().booleanValue() && (this.parentEsp.enemyListOnly.getEffectiveValue() != false ? !Vape.INSTANCE.getEnemyManager().isEnemy(event.getEntity().getName()) : !Vape.INSTANCE.getClientSettings().isValidTarget(event.getEntity(), false))) {
            return;
        }
        if (event.getWorld().isNull()) {
            return;
        }
        if (this.renderingOutline) {
            event.setResult(0);
            event.setCancelled(true);
        }
    }

    public boolean isRenderingOutline() {
        return this.renderingOutline;
    }

    @EventHandler
    public void onPreRenderEntity(EventPreRenderEntity event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (this.parentEsp.resolveEntityColor(event.getThePlayer(), event.getEntity()) == null) {
            return;
        }
        if (this.renderingOutline && ForgeVersion.MC_1_7_10.L()) {
            RenderUtils.g();
            RenderUtils.w(this.parentEsp.playerColor.getMutableColor());
            RenderUtils.f();
            return;
        }
        if (!event.getEntity().isInstance(MappedClasses.z5) && event.getEntity().isInstance(MappedClasses.Yl)) {
            this.legacyRendererObserved = true;
            ++this.legacyRendererObservationCount;
        }
    }

    @EventHandler
    public void onPreRenderPlayerSpec(EventPreRenderPlayerSpec event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (Vape.INSTANCE.getClientSettings().isBot(event.getClientPlayer()) && this.parentEsp.hideBots.getEffectiveValue().booleanValue()) {
            return;
        }
        if (this.parentEsp.enemyOnly.getEffectiveValue().booleanValue() && (this.parentEsp.enemyListOnly.getEffectiveValue() != false ? !Vape.INSTANCE.getEnemyManager().isEnemy(event.getClientPlayer().getName()) : !Vape.INSTANCE.getClientSettings().isValidTarget(event.getClientPlayer(), false))) {
            return;
        }
        if (event.getWorld().isNull()) {
            return;
        }
        if (this.renderingOutline) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRender3D(EventRender3D event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (ForgeVersion.MC_1_7_10.B() && (!this.legacyRendererObserved || this.legacyRendererObservationCount < 10)) {
            return;
        }
        EntityPlayerSP viewer = event.getThePlayer();
        WorldClient world = event.getWorld();
        StencilUtil.ensureFramebufferStencilBuffer();
        int displayList = GL11.glGenLists((int)1);
        StencilUtil.getInstance().pushLayer();
        GL11.glPushMatrix();
        RenderUtils.g();
        boolean depthEnabled = GL11.glIsEnabled((int)2929);
        boolean blendEnabled = GL11.glIsEnabled((int)3042);
        boolean textureEnabled = GL11.glIsEnabled((int)3553);
        boolean lightingEnabled = GL11.glIsEnabled((int)2896);
        boolean lineSmoothingEnabled = GL11.glIsEnabled((int)2848);
        boolean alphaTestEnabled = GL11.glIsEnabled((int)3008);
        GlStateManager.disableDepth();
        StencilUtil.getInstance().configureLayerMask(true);
        GL11.glNewList((int)displayList, (int)4864);
        for (Object entityHandle : world.z()) {
            Entity entity = new Entity(entityHandle);
            MutableColor color = this.parentEsp.resolveEntityColor(event.getThePlayer(), entity);
            if (color == null || this.parentEsp.enemyOnly.getEffectiveValue().booleanValue() && (this.parentEsp.enemyListOnly.getEffectiveValue() == false ? !Vape.INSTANCE.getClientSettings().isValidTarget(entity, false) : !Vape.INSTANCE.getEnemyManager().isEnemy(entity.getName()))) continue;
            if (entity.equals(viewer) || !entity.isInstance(MappedClasses.Yl)) continue;
            double previousX = entity.M();
            double previousY = entity.W();
            double previousZ = entity.m$src$D$fwnne5();
            double renderX = previousX + (entity.z() - previousX) * (double)event.getTicks() - RenderManager.getInterpolatedRenderPosX();
            double renderY = previousY + (entity.N() - previousY) * (double)event.getTicks() - RenderManager.getInterpolatedRenderPosY();
            double renderZ = previousZ + (entity.h() - previousZ) * (double)event.getTicks() - RenderManager.getInterpolatedRenderPosZ();
            boolean entityWasInvisible = entity.J$src$Z$fdev5g();
            entity.q(false);
            GL11.glPushMatrix();
            GL11.glLineWidth((float)3.0f);
            OpenGlBackendHolder.backend.enableCapability(2848);
            GlStateManager.enableAlpha();
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            event.getEntityRenderer().B(0.0);
            RenderLivingBase renderLivingBase = new RenderLivingBase(Minecraft.D().getEntityRenderObject(entity).getObject());
            if (renderLivingBase.isNotNull()) {
                this.renderingOutline = true;
                ArrayList arrayList = null;
                if (ForgeVersion.MC_1_7_10.Y()) {
                    List<Object> list = renderLivingBase.getLayerRenderers();
                    arrayList = Lists.newArrayList(list);
                    list.clear();
                }
                float[] colorComponents = RenderUtils.d(color.l());
                OpenGlBackendHolder.backend.setColor(colorComponents[0], colorComponents[1], colorComponents[2], colorComponents[3]);
                renderLivingBase.doRender(entity, renderX, renderY, renderZ, event.getTicks(), event.getTicks());
                if (ForgeVersion.MC_1_7_10.Y()) {
                    renderLivingBase.setLayerRenderers(arrayList);
                }
                this.renderingOutline = false;
            }
            entity.q(entityWasInvisible);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            event.getEntityRenderer().O(1.0);
            GL11.glPopMatrix();
        }
        GlStateManager.enableAlpha();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        event.getEntityRenderer().B(0.0);
        GL11.glEndList();
        GL11.glPolygonMode((int)1032, (int)6913);
        GL11.glCallList((int)displayList);
        GL11.glPolygonMode((int)1032, (int)6912);
        GL11.glCallList((int)displayList);
        StencilUtil.getInstance().configureLayerMask(false);
        GL11.glPolygonMode((int)1032, (int)6914);
        GL11.glCallList((int)displayList);
        StencilUtil.getInstance().configureEqualLayerTest();
        GL11.glPolygonMode((int)1032, (int)6913);
        GL11.glCallList((int)displayList);
        GL11.glPolygonMode((int)1032, (int)6912);
        GL11.glCallList((int)displayList);
        GL11.glPolygonMode((int)1032, (int)6914);
        StencilUtil.getInstance().popLayer();
        GlStateManager.r(2929, depthEnabled);
        GL11.glDeleteLists((int)displayList, (int)1);
        GlStateManager.r(2896, lightingEnabled);
        GlStateManager.r(3042, blendEnabled);
        GlStateManager.r(3553, textureEnabled);
        if (lineSmoothingEnabled) {
            OpenGlBackendHolder.backend.enableCapability(2848);
        } else {
            OpenGlBackendHolder.backend.disableCapability(2848);
        }
        GlStateManager.r(3008, alphaTestEnabled);
        event.getEntityRenderer().O(1.0);
        GL11.glPopMatrix();
        RenderUtils.f();
    }
}
