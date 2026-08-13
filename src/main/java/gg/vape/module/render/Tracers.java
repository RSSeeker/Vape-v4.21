package gg.vape.module.render;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventRenderTracers3D;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.render.entity.RenderEntityContext;
import gg.vape.module.render.entity.RenderEntityContextCache;
import gg.vape.module.render.entity.RenderEntityContextEntry;
import gg.vape.utils.MutableColor;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.Vec3d;
import gg.vape.utils.render.BufferedRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.ActiveRenderInfo;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class Tracers
extends Mod {
    private final BooleanValue showInvisibles;
    private final RandomValue animalDistance;
    private double medianDistance;
    private final BooleanValue highlightFocusing;
    private final BooleanValue colorByDistance;
    private static final long MODULE_COLOR = 6957373267435107050L;
    private final BooleanValue mobDistanceCheck;
    private final BooleanValue renderAnimals;
    private final Map<EntityLivingBase, RenderEntityContextEntry> entries;
    private final ColorValue playerColor;
    private final RandomValue mobDistance;
    private final BooleanValue animalDistanceCheck;
    private final ColorValue mobColor;
    private final BooleanValue renderPlayers;
    private final BooleanValue renderMobs;
    private final BooleanValue enemyOnly = BooleanValue.create(this, "Enemy Only", false);
    private final BooleanValue enemyListOnly = BooleanValue.create(this, "Enemies List Only", false);
    private final RandomValue playerDistance;
    private List<EntityLivingBase> sortedEntities;
    private final ColorValue animalColor;
    private final BooleanValue playerDistanceCheck;

    private static int compareDistance(EntityPlayerSP player, EntityLivingBase first, EntityLivingBase second) {
        double firstDistance = RenderEntityContextCache.getOrCreate(first, player).getDistance();
        double secondDistance = RenderEntityContextCache.getOrCreate(second, player).getDistance();
        return Double.compare(firstDistance, secondDistance);
    }

    private void updateMedianDistance(EntityPlayerSP player) {
        this.sortedEntities.sort((first, second) -> Tracers.compareDistance(player, first, second));
        ArrayList<Double> distances = new ArrayList<Double>();
        for (Map.Entry<EntityLivingBase, RenderEntityContextEntry> entry : this.entries.entrySet()) {
            distances.add(entry.getValue().getContext().getDistance());
        }
        Collections.sort(distances);
        this.medianDistance = distances.get(distances.size() / 2);
    }

    private void collectEntities(EntityPlayerSP player, WorldClient world) {
        for (Object entityHandle : world.z()) {
            Entity entity = new Entity(entityHandle);
            if (!entity.isInstance(MappedClasses.zm) || entity.equals(player)) continue;
            EntityLivingBase livingEntity = new EntityLivingBase(entity);
            RenderEntityContext context = RenderEntityContextCache.getOrCreate(livingEntity, player);
            if (context.isSyntheticEntity() || !this.showInvisibles.getEffectiveValue().booleanValue() && context.isInvisibleWithoutEquipment() || context.isBot() || this.enemyOnly.getEffectiveValue().booleanValue() && (this.enemyListOnly.getEffectiveValue() != false ? !context.isEnemy() : !Vape.INSTANCE.getClientSettings().isValidTarget(entity, false))) continue;
            float distance = (float)context.getDistance();
            if (entity.isInstance(MappedClasses.lG)) {
                if (!this.renderPlayers.getEffectiveValue().booleanValue() || this.playerDistanceCheck.getEffectiveValue().booleanValue() && ((double)distance < this.playerDistance.getMinimumValue() || (double)distance > this.playerDistance.getMaximumValue())) continue;
                if (context.isFriend() && Vape.INSTANCE.getFriendManager().recolorVisuals.getEffectiveValue().booleanValue()) {
                    this.entries.put(livingEntity, new RenderEntityContextEntry(context, Vape.INSTANCE.getFriendManager().friendColor.getMutableColor()));
                    continue;
                }
                if (context.isEnemy() && Vape.INSTANCE.getEnemyManager().useColor.getEffectiveValue().booleanValue()) {
                    this.entries.put(livingEntity, new RenderEntityContextEntry(context, Vape.INSTANCE.getEnemyManager().enemyColor.getMutableColor()));
                    continue;
                }
                this.entries.put(livingEntity, new RenderEntityContextEntry(context, this.playerColor.getMutableColor()));
                continue;
            }
            boolean mob = false;
            if (ForgeVersion.MC_1_17.d()) {
                if (entity.isInstance(MappedClasses.Yw) || entity.isInstance(MappedClasses.Zo)) {
                    mob = true;
                }
            } else if (entity.isInstance(MappedClasses.Fr) || entity.isInstance(MappedClasses.Zo)) {
                mob = true;
            }
            if (mob) {
                if (!this.renderMobs.getEffectiveValue().booleanValue() || this.mobDistanceCheck.getEffectiveValue().booleanValue() && ((double)distance < this.mobDistance.getMinimumValue() || (double)distance > this.mobDistance.getMaximumValue())) continue;
                this.entries.put(livingEntity, new RenderEntityContextEntry(context, this.mobColor.getMutableColor()));
                continue;
            }
            if (!this.renderAnimals.getEffectiveValue().booleanValue() || entity.isInstance(MappedClasses.Zo) || this.animalDistanceCheck.getEffectiveValue().booleanValue() && ((double)distance < this.animalDistance.getMinimumValue() || (double)distance > this.animalDistance.getMaximumValue())) continue;
            this.entries.put(livingEntity, new RenderEntityContextEntry(context, this.animalColor.getMutableColor()));
        }
    }


    public Tracers() {
        super("Tracers", (int)MODULE_COLOR, Category.RENDER);
        this.renderPlayers = BooleanValue.create(this, "Render Players", true);
        this.renderMobs = BooleanValue.create(this, "Render Mobs", false);
        this.renderAnimals = BooleanValue.create(this, "Render Animals", false);
        this.playerDistanceCheck = BooleanValue.create(this, "Distance Check", false);
        this.mobDistanceCheck = BooleanValue.create(this, "Distance Check", false);
        this.animalDistanceCheck = BooleanValue.create(this, "Distance Check", false);
        this.showInvisibles = BooleanValue.create(this, "Invisibles", false);
        this.colorByDistance = BooleanValue.create(this, "Color by distance", false);
        this.highlightFocusing = BooleanValue.create(this, "Highlight if focusing", false, "If another player is looking at you their tracer will be highlighted");
        this.playerDistance = RandomValue.create(this, "Player Distance", "#", "", 0.0, 0.0, 32.0, 256.0);
        this.mobDistance = RandomValue.create(this, "Mob Distance", "#", "", 0.0, 0.0, 32.0, 256.0);
        this.animalDistance = RandomValue.create(this, "Animal Distance", "#", "", 0.0, 0.0, 32.0, 256.0);
        this.playerColor = ColorValue.create(this, "Player Color", new Color(0, 150, 255, 255));
        this.mobColor = ColorValue.create(this, "Mob Color", new Color(255, 154, 0));
        this.animalColor = ColorValue.create(this, "Animal Color", new Color(255, 255, 255));
        this.entries = new HashMap<EntityLivingBase, RenderEntityContextEntry>();
        this.renderPlayers.addDependentValues(this.enemyOnly, this.playerDistanceCheck, this.playerColor);
        this.renderMobs.addDependentValues(this.mobDistanceCheck, this.mobColor);
        this.renderAnimals.addDependentValues(this.animalDistanceCheck, this.animalColor);
        this.playerDistanceCheck.addDependentValues(this.playerDistance);
        this.mobDistanceCheck.addDependentValues(this.mobDistance);
        this.animalDistanceCheck.addDependentValues(this.animalDistance);
        this.addValue(this.showInvisibles, this.colorByDistance, this.highlightFocusing, this.renderPlayers, this.enemyOnly, this.playerDistanceCheck, this.playerDistance, this.playerColor, this.renderAnimals, this.animalDistanceCheck, this.animalDistance, this.animalColor, this.renderMobs, this.mobDistanceCheck, this.mobDistance, this.mobColor);
    }

    private void drawTracer(EntityPlayerSP player, Entity entity, Color color, float lineWidth, float partialTicks, double cameraX, double cameraY, double cameraZ, double startY, boolean outlined) {
        double targetX = entity.M() + (entity.z() - entity.M()) * (double)partialTicks - cameraX;
        double targetY = entity.W() + (entity.N() - entity.W()) * (double)partialTicks - cameraY;
        double targetZ = entity.m$src$D$fwnne5() + (entity.h() - entity.m$src$D$fwnne5()) * (double)partialTicks - cameraZ;
        boolean blendEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
        boolean lightingEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(2896);
        GL11.glBlendFunc((int)770, (int)771);
        if (!blendEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3042);
        }
        if (lightingEnabled) {
            OpenGlBackendHolder.backend.disableCapability(2896);
        }
        GL11.glBlendFunc((int)770, (int)771);
        OpenGlBackendHolder.backend.enableCapability(2848);
        OpenGlBackendHolder.backend.disableCapability(3553);
        double startX = 0.0;
        double startZ = 0.0;
        if (ForgeVersion.MC_1_12_2.d()) {
            Vec3d viewDirection = new Vec3d(0.0, 0.0, 1.0);
            if (ForgeVersion.MC_1_16_5.d()) {
                viewDirection.rotateAroundXAxis((float)(-Math.toRadians(Minecraft.D().getPlayerViewY())));
                viewDirection.rotateAroundYAxis((float)(-Math.toRadians(Minecraft.D().getPlayerViewX())));
            } else {
                viewDirection.rotateAroundXAxis((float)(-Math.toRadians(player.V())));
                viewDirection.rotateAroundYAxis((float)(-Math.toRadians(player.J())));
            }
            startX = viewDirection.getX();
            startY += ForgeVersion.MC_1_16_5.d() ? viewDirection.getY() - (double)player.X() : viewDirection.getY();
            startZ = viewDirection.getZ();
            if (ForgeVersion.MC_1_16_5.d() && Minecraft.gameSettings().x() != 0) {
                ActiveRenderInfo activeRenderInfo = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().l();
                double cameraOffsetX = RenderManager.getInterpolatedRenderPosX() - activeRenderInfo.o().getX();
                double cameraOffsetY = RenderManager.getInterpolatedRenderPosY() - activeRenderInfo.o().getY();
                double cameraOffsetZ = RenderManager.getInterpolatedRenderPosZ() - activeRenderInfo.o().getZ();
                targetX += cameraOffsetX;
                targetY += cameraOffsetY;
                targetZ += cameraOffsetZ;
            }
        }
        targetY += (double)entity.X();
        if (GuiRenderPrimitives.d()) {
            if (outlined) {
                BufferedRenderPrimitives.drawLine3D(startX, startY, startZ, targetX, targetY, targetZ, lineWidth + lineWidth * 0.5f, Color.black);
            }
            BufferedRenderPrimitives.drawLine3D(startX, startY, startZ, targetX, targetY, targetZ, lineWidth, color);
        } else {
            if (outlined) {
                GL11.glLineWidth((float)(lineWidth + lineWidth * 0.5f));
                GL11.glBegin((int)1);
                RenderUtils.w(Color.black);
                GL11.glVertex3d((double)startX, (double)startY, (double)startZ);
                GL11.glVertex3d((double)targetX, (double)targetY, (double)targetZ);
                GL11.glEnd();
            }
            GL11.glLineWidth((float)lineWidth);
            GL11.glBegin((int)1);
            RenderUtils.w(color);
            GL11.glVertex3d((double)startX, (double)startY, (double)startZ);
            GL11.glVertex3d((double)targetX, (double)targetY, (double)targetZ);
            GL11.glEnd();
        }
        if (lightingEnabled) {
            OpenGlBackendHolder.backend.enableCapability(2896);
        }
        if (!blendEnabled) {
            OpenGlBackendHolder.backend.disableCapability(3042);
        }
        OpenGlBackendHolder.backend.enableCapability(3553);
        OpenGlBackendHolder.backend.disableCapability(2848);
    }

    private void applyFocusHighlight(EntityPlayerSP player) {
        if (this.highlightFocusing.getEffectiveValue().booleanValue()) {
            ArrayList<EntityLivingBase> focusedEntities = new ArrayList<EntityLivingBase>();
            for (EntityLivingBase entity : this.sortedEntities) {
                if (!(RotationUtil.M(entity, player) < 5.0)) continue;
                focusedEntities.add(entity);
            }
            if (!focusedEntities.isEmpty()) {
                for (EntityLivingBase entity : this.sortedEntities) {
                    RenderEntityContextEntry entry = this.entries.get(entity);
                    if (!entry.getContext().canViewerSee()) continue;
                    if (focusedEntities.contains(entity)) {
                        entry.setScale(3.0);
                        entry.setFocused(true);
                        continue;
                    }
                    entry.setScale(0.75);
                }
            }
        }
    }

    @EventHandler
    public void onRender(EventRenderTracers3D event) {
        EntityPlayerSP player = event.getThePlayer();
        this.collectEntities(player, event.getWorld());
        if (this.entries.isEmpty()) {
            return;
        }
        RenderUtil.d();
        RenderUtils.g();
        OpenGlBackendHolder.backend.disableCapability(2929);
        event.getEntityRenderer().B(0.0);
        double cameraX = RenderManager.getInterpolatedRenderPosX();
        double cameraY = RenderManager.getInterpolatedRenderPosY();
        double cameraZ = RenderManager.getInterpolatedRenderPosZ();
        double startY = ForgeVersion.MC_1_7_10.Y() ? (double)player.X() : 0.0;
        this.sortedEntities = new ArrayList<EntityLivingBase>(this.entries.keySet());
        this.updateMedianDistance(player);
        this.applyDistanceColors(player);
        this.applyFocusHighlight(player);
        Collections.reverse(this.sortedEntities);
        for (EntityLivingBase entity : this.sortedEntities) {
            MutableColor overrideColor;
            RenderEntityContextEntry entry = this.entries.get(entity);
            Color color = entry.getColor();
            if (entity.isInstance(MappedClasses.Yl) && (overrideColor = entry.getContext().getRenderColor(false)) != null) {
                color = overrideColor;
            }
            this.drawTracer(player, entity, color, (float)entry.getScale(), event.getTicks(), cameraX, cameraY, cameraZ, startY, entry.isFocused());
        }
        OpenGlBackendHolder.backend.setColor(1.0, 1.0f, 1.0f);
        event.getEntityRenderer().O(0.0);
        OpenGlBackendHolder.backend.enableCapability(2929);
        RenderUtils.f();
        OpenGlBackendHolder.backend.popMatrix();
        this.entries.clear();
    }

    private void applyDistanceColors(EntityPlayerSP player) {
        if (this.colorByDistance.getEffectiveValue().booleanValue()) {
            for (EntityLivingBase entity : this.sortedEntities) {
                double distance = RenderEntityContextCache.getOrCreate(entity, player).getDistance();
                float maximumHue = 0.35f;
                double distanceOffset = (double)(Math.round(distance / 3.0) * 3L) - this.medianDistance / 3.0;
                float hue = (float)((double)maximumHue * (distanceOffset / this.medianDistance));
                if (distance > this.medianDistance) {
                    hue = maximumHue;
                }
                Color color = new Color(Color.HSBtoRGB(hue, 1.0f, 1.0f));
                int alpha = 255;
                if (distance > this.medianDistance && (alpha = (int)(255.0 / (distance / this.medianDistance))) < 150) {
                    alpha = 150;
                }
                color = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
                this.entries.get(entity).setColor(color);
            }
        }
    }
}
