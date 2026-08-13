package gg.vape.module.render.esp;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreRenderLiving;
import gg.vape.event.impl.EventRender2D;
import gg.vape.friend.FriendEntry;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.render.ESP;
import gg.vape.module.render.entity.ProjectedEntityBounds;
import gg.vape.module.render.entity.RenderEntityContext;
import gg.vape.module.render.entity.RenderEntityContextCache;
import gg.vape.render.OffscreenRenderContext;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.RenderWorldLastEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class ESP2D
extends SubModule<ESP> {
    private final List<ProjectedEntityBounds> pendingBounds;
    private final ESP parentEsp = (ESP)this.getParent();
    private static final String NON_ASCII_PATTERN = "[^\u00a7^\\x00-\\x7F]";

    @EventHandler
    public void onRender2D(EventRender2D event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        SmoothFontRenderer fontRenderer = Vape.INSTANCE.getFontManager().W(0.9, true);
        OpenGlBackendHolder.backend.pushMatrix();
        float renderScale = 1.0f;
        float coordinateScale = 2.0f;
        OpenGlBackendHolder.backend.scale(renderScale, renderScale, renderScale);
        GlStateManager.enableAlpha();
        float renderResolutionMultiplier = RenderWorldLastEvent.getRenderResolutionMultiplier();
        boolean blendEnabled = GL11.glIsEnabled((int)3042);
        RenderUtils.g();
        for (ProjectedEntityBounds projectedEntityBounds : this.pendingBounds) {
            double textWidth;
            float displayHeight = event.getDisplayHeight();
            double left = projectedEntityBounds.minX / (double)coordinateScale / (double)renderScale / (double)renderResolutionMultiplier;
            double right = projectedEntityBounds.maxX / (double)coordinateScale / (double)renderScale / (double)renderResolutionMultiplier;
            double top = ((double)displayHeight - projectedEntityBounds.maxY / (double)renderResolutionMultiplier) / (double)coordinateScale / (double)renderScale;
            double bottom = ((double)displayHeight - projectedEntityBounds.minY / (double)renderResolutionMultiplier) / (double)coordinateScale / (double)renderScale;
            GlStateManager.disableTexture2D();
            OpenGlBackendHolder.backend.setLineWidth(1.0f);
            GlStateManager.enableBlend();
            boolean friend = projectedEntityBounds.context.isFriend();
            boolean enemy = projectedEntityBounds.context.isEnemy();
            boolean priorityTarget = enemy || friend;
            if (this.parentEsp.showBoundingBox.getEffectiveValue().booleanValue() && (!this.parentEsp.priorityOnly.getEffectiveValue().booleanValue() || priorityTarget)) {
                float boxAlpha = (float)projectedEntityBounds.color.getAlpha() / 255.0f;
                if (GuiRenderPrimitives.d()) {
                    double boxHeight = bottom - top;
                    double boxWidth = right - left;
                    double borderWidth = 1.0;
                    Color borderColor = new Color(0.0f, 0.0f, 0.0f, 0.4f * boxAlpha);
                    BufferedGuiRenderPrimitives.fillRect(left, top, boxWidth, borderWidth, borderColor);
                    BufferedGuiRenderPrimitives.fillRect(left, top, borderWidth, boxHeight, borderColor);
                    BufferedGuiRenderPrimitives.fillRect(right, bottom, -borderWidth, -boxHeight, borderColor);
                    BufferedGuiRenderPrimitives.fillRect(right, bottom, -boxWidth, -borderWidth, borderColor);
                    boxHeight = (bottom -= 1.0) - (top += 1.0);
                    boxWidth = (right -= 1.0) - (left += 1.0);
                    BufferedGuiRenderPrimitives.fillRect(left, top, boxWidth, borderWidth, borderColor);
                    BufferedGuiRenderPrimitives.fillRect(left, top, borderWidth, boxHeight, borderColor);
                    BufferedGuiRenderPrimitives.fillRect(right, bottom, -borderWidth, -boxHeight, borderColor);
                    BufferedGuiRenderPrimitives.fillRect(right, bottom, -boxWidth, -borderWidth, borderColor);
                    boxHeight = (bottom += 0.5) - (top -= 0.5);
                    boxWidth = (right += 0.5) - (left -= 0.5);
                    BufferedGuiRenderPrimitives.fillRect(left, top, boxWidth, borderWidth, projectedEntityBounds.color);
                    BufferedGuiRenderPrimitives.fillRect(left, top, borderWidth, boxHeight, projectedEntityBounds.color);
                    BufferedGuiRenderPrimitives.fillRect(right, bottom, -borderWidth, -boxHeight, projectedEntityBounds.color);
                    BufferedGuiRenderPrimitives.fillRect(right, bottom, -boxWidth, -borderWidth, projectedEntityBounds.color);
                } else {
                    OpenGlBackendHolder.backend.setColor(0.0, 0.0, 0.0, 0.4 * (double)boxAlpha);
                    GL11.glBegin((int)2);
                    GL11.glVertex2d((double)left, (double)top);
                    GL11.glVertex2d((double)right, (double)top);
                    GL11.glVertex2d((double)right, (double)bottom);
                    GL11.glVertex2d((double)left, (double)bottom);
                    GL11.glEnd();
                    GL11.glBegin((int)2);
                    GL11.glVertex2d((double)(left + 1.0), (double)(top + 1.0));
                    GL11.glVertex2d((double)(right - 1.0), (double)(top + 1.0));
                    GL11.glVertex2d((double)(right - 1.0), (double)(bottom - 1.0));
                    GL11.glVertex2d((double)(left + 1.0), (double)(bottom - 1.0));
                    GL11.glEnd();
                    GlStateManager.enableBlend();
                    RenderUtils.w(projectedEntityBounds.color);
                    GL11.glBegin((int)2);
                    GL11.glVertex2d((double)(left + 0.5), (double)(top + 0.5));
                    GL11.glVertex2d((double)(right - 0.5), (double)(top + 0.5));
                    GL11.glVertex2d((double)(right - 0.5), (double)(bottom - 0.5));
                    GL11.glVertex2d((double)(left + 0.5), (double)(bottom - 0.5));
                    GL11.glEnd();
                    GlStateManager.disableBlend();
                }
            }
            if (projectedEntityBounds.entity.isInstance(MappedClasses.zm)) {
                EntityLivingBase entityLivingBase = new EntityLivingBase(projectedEntityBounds.entity.getObject());
                float effectiveHealth = projectedEntityBounds.context.getEffectiveHealth();
                if (this.parentEsp.healthBar.getEffectiveValue().booleanValue() && effectiveHealth >= 0.0f && projectedEntityBounds.context.getMaxHealth() >= 0.0f) {
                    double healthFraction = Math.min(1.0f, effectiveHealth / projectedEntityBounds.context.getMaxHealth());
                    if (GuiRenderPrimitives.d()) {
                        BufferedGuiRenderPrimitives.fillQuad(left - 2.0, bottom - 0.5, left - 2.0, top + 0.5, left - 4.0, top + 0.5, left - 4.0, bottom - 0.5, new Color(0.0f, 0.0f, 0.0f, 0.4f));
                    } else {
                        GlStateManager.enableBlend();
                        OpenGlBackendHolder.backend.setColor(0.0, 0.0, 0.0, 0.4);
                        GL11.glBegin((int)7);
                        GL11.glVertex2d((double)(left - 2.0), (double)(bottom - 0.5));
                        GL11.glVertex2d((double)(left - 2.0), (double)(top + 0.5));
                        GL11.glVertex2d((double)(left - 4.0), (double)(top + 0.5));
                        GL11.glVertex2d((double)(left - 4.0), (double)(bottom - 0.5));
                        GL11.glEnd();
                    }
                    double healthBarHeight = bottom - top - 1.0;
                    double healthBarTop = top + healthBarHeight * healthFraction;
                    double red = 0.0;
                    double green = 0.0;
                    double blue = 0.0;
                    double alpha = 0.0;
                    if (healthFraction >= 0.9) {
                        green = 1.0;
                        alpha = 1.0;
                    } else if (healthFraction >= 0.75) {
                        red = 0.9;
                        green = 1.0;
                        alpha = 1.0;
                    } else if (healthFraction >= 0.5) {
                        red = 1.0;
                        green = 1.0;
                        alpha = 1.0;
                    } else if (healthFraction >= 0.25) {
                        red = 1.0;
                        green = 0.5;
                        alpha = 1.0;
                    } else if (healthFraction >= 0.0) {
                        red = 1.0;
                        alpha = 1.0;
                    }
                    if (GuiRenderPrimitives.d()) {
                        BufferedGuiRenderPrimitives.fillQuad(left - 2.5, healthBarTop, left - 2.5, top + 1.0, left - 3.5, top + 1.0, left - 3.5, healthBarTop, new Color((int)(red * 255.0), (int)(green * 255.0), (int)(blue * 255.0), (int)(alpha * 255.0)));
                    } else {
                        GL11.glColor4d((double)red, (double)green, (double)blue, (double)alpha);
                        GL11.glBegin((int)7);
                        GL11.glVertex2d((double)(left - 2.5), (double)healthBarTop);
                        GL11.glVertex2d((double)(left - 2.5), (double)(top + 1.0));
                        GL11.glVertex2d((double)(left - 3.5), (double)(top + 1.0));
                        GL11.glVertex2d((double)(left - 3.5), (double)healthBarTop);
                        GL11.glEnd();
                    }
                }
                if (this.parentEsp.showName.getEffectiveValue().booleanValue()) {
                    FriendEntry friendEntry;
                    String displayName = this.parentEsp.useDisplayName.getEffectiveValue() == false || priorityTarget ? projectedEntityBounds.context.getName() : projectedEntityBounds.context.getTypeName();
                    if (this.parentEsp.useDisplayName.getEffectiveValue().booleanValue()) {
                        displayName = displayName.replaceAll(NON_ASCII_PATTERN, "");
                    }
                    if (friend && (friendEntry = Vape.INSTANCE.getFriendManager().findTargetedFriend(projectedEntityBounds.context.getName())) != null) {
                        displayName = friendEntry.getDisplayName();
                    }
                    textWidth = fontRenderer.N(displayName);
                    if (this.parentEsp.showNameBackground.getEffectiveValue().booleanValue()) {
                        Color backgroundColor = priorityTarget ? projectedEntityBounds.color : new Color(0, 0, 0, 95);
                        GlStateManager.disableTexture2D();
                        boolean entityHighlighted = entityLivingBase.P();
                        double borderWidth = entityHighlighted ? 1.5 : 0.5;
                        Color borderColor = entityHighlighted ? new Color(255, 0, 0, 200) : new Color(0, 0, 0, 102);
                        float backgroundLeft = (float)(right + (left - right) / 2.0 - textWidth / 2.0 - 1.5);
                        float backgroundTop = (float)(top - 10.0);
                        float backgroundRight = (float)(right + (left - right) / 2.0 + textWidth / 2.0 + 1.5);
                        float backgroundBottom = (float)(top - 1.0);
                        if (GuiRenderPrimitives.d()) {
                            BufferedGuiRenderPrimitives.fillBorderAdjustedRect(backgroundLeft, backgroundTop + 1.0f, backgroundRight - backgroundLeft, backgroundBottom - backgroundTop + 1.0f, borderWidth, backgroundColor, borderColor);
                        } else {
                            RenderUtils.M(backgroundLeft, backgroundTop, backgroundRight, backgroundBottom, borderWidth, backgroundColor, borderColor);
                        }
                        GlStateManager.enableTexture2D();
                    }
                    fontRenderer.g(displayName, right + (left - right) / 2.0 - textWidth / 2.0, top - 8.0, priorityTarget ? -1 : projectedEntityBounds.color.getRGB());
                }
            }
            OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            OpenGlBackendHolder.backend.disableCapability(2848);
            GlStateManager.enableTexture2D();
        }
        RenderUtils.f();
        if (blendEnabled) {
            GlStateManager.enableBlend();
        } else {
            GlStateManager.disableBlend();
        }
        RenderUtil.Y();
        this.pendingBounds.clear();
    }


    @EventHandler
    public void onPreRenderLiving(EventPreRenderLiving event) {
        if (event.getWorld().isNull()) {
            return;
        }
        Entity entity = event.getEntity();
        double renderX = event.getX();
        double renderY = event.getY();
        double renderZ = event.getZ();
        MutableColor color = this.parentEsp.resolveEntityColor(event.getThePlayer(), entity.getObject());
        if (color == null) {
            return;
        }
        EntityLivingBase entityLivingBase = new EntityLivingBase(entity.getObject());
        RenderUtil.d();
        float expansion = entity.b();
        AxisAlignedBB worldBounds = entity.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().expand(expansion, expansion, expansion);
        AxisAlignedBB relativeBounds = AxisAlignedBB.create(worldBounds.getMinX() - entity.z(), worldBounds.getMinY() - entity.N(), worldBounds.getMinZ() - entity.h(), worldBounds.getMaxX() - entity.z(), worldBounds.getMaxY() - entity.N(), worldBounds.getMaxZ() - entity.h());
        RenderEntityContext renderEntityContext = RenderEntityContextCache.getOrCreate(entityLivingBase, event.getThePlayer());
        ProjectedEntityBounds projectedEntityBounds = new ProjectedEntityBounds(renderX, renderY, renderZ, relativeBounds, entity, renderEntityContext, color);
        if (projectedEntityBounds.onScreen) {
            this.pendingBounds.add(projectedEntityBounds);
            if (this.parentEsp.showName.getEffectiveValue().booleanValue()) {
                event.setCancelled(true);
            }
        }
        RenderUtil.Y();
    }

    public ESP2D(Mod parent, String name) {
        super(parent, name);
        this.pendingBounds = new ArrayList<ProjectedEntityBounds>();
    }
}
