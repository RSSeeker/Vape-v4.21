package gg.vape.module.render;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.render.entity.RenderEntityContext;
import gg.vape.module.render.entity.RenderEntityContextCache;
import gg.vape.module.render.esp.ESP2D;
import gg.vape.module.render.esp.ESP3D;
import gg.vape.module.render.esp.ESPOutline;
import gg.vape.module.render.esp.ESPSkeleton;
import gg.vape.render.OffscreenRenderContext;
import gg.vape.unmap.ModeOption;
import gg.vape.utils.MutableColor;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.ModeValue;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderManager;
import java.awt.Color;

public class ESP
extends Mod {
    public final BooleanValue healthBar;
    private final ModeOption outlineMode;
    private final BooleanValue showInvisibles;
    public final BooleanValue showNameBackground;
    public final BooleanValue enemyListOnly;
    public final BooleanValue enemyOnly;
    public final BooleanValue priorityOnly;
    private final ESPOutline outlineRenderer;
    public final ModeValue mode;
    public final BooleanValue useDisplayName;
    public final RenderManager renderManager;
    public final ModeOption threeDimensionalMode = new ESP3D(this, "3D").getSelectionValue();
    private final ModeOption skeletonMode;
    public final BooleanValue showName;
    public final ColorValue playerColor;
    public final BooleanValue hideBots;
    private final ModeOption twoDimensionalMode;
    public final BooleanValue showExpandedHitbox;
    public final BooleanValue showNormalHitbox;
    public final BooleanValue showBoundingBox;

    public ESP() {
        super("ESP", -16711936, Category.RENDER, "Extra Sensory Perception\nRenders an ESP on players.");
        ESP2D esp2D = new ESP2D(this, "2D");
        this.twoDimensionalMode = esp2D.getSelectionValue();
        this.outlineRenderer = new ESPOutline(this, "Outline");
        this.outlineMode = this.outlineRenderer.getSelectionValue();
        this.skeletonMode = new ESPSkeleton(this, "Skeleton").getSelectionValue();
        this.playerColor = ColorValue.create(this, "Player Color", new Color(-14368924));
        this.showInvisibles = BooleanValue.create(this, "Invisibles", false, "Show invisibles.");
        this.enemyOnly = BooleanValue.create(this, "Enemy Only", false, "Only render enemies.");
        this.priorityOnly = BooleanValue.create(this, "Priority Only", false, "Only shows the ESP box on friends/enemies.");
        this.enemyListOnly = BooleanValue.create(this, "Enemies List Only", false);
        this.hideBots = BooleanValue.create(this, "Hide Bots", false);
        this.showExpandedHitbox = BooleanValue.create(this, "Hitbox", false, "Shows the current entity hitbox size.\n(HitBoxes expansion visible)\n(3D Only)");
        this.showNormalHitbox = BooleanValue.create(this, "Show Normal", false, "Shows the true entity hitbox size.\n(3D Only)");
        this.showBoundingBox = BooleanValue.create(this, "Bounding Box", true, "Shows an ESP box that wraps around the players BoundingBox.");
        this.healthBar = BooleanValue.create(this, "Health Bar", false, "Shows a healthbar next to the ESP box");
        this.showName = BooleanValue.create(this, "Name", false, "Shows a nametag above the ESP box");
        this.useDisplayName = BooleanValue.create(this, "Use Displayname", false, "Shows the tab list display name.");
        this.showNameBackground = BooleanValue.create(this, "Show Background", false, "Renders a box behind the text.");
        this.renderManager = Minecraft.D();
        this.mode = ForgeVersion.MC_1_17.d() ? ModeValue.create((Object)this, "Mode", this.threeDimensionalMode, this.threeDimensionalMode, this.twoDimensionalMode) : (ForgeVersion.MC_1_12_2.d() ? ModeValue.create((Object)this, "Mode", this.threeDimensionalMode, this.threeDimensionalMode, this.twoDimensionalMode, this.skeletonMode) : ModeValue.create((Object)this, "Mode", this.threeDimensionalMode, this.threeDimensionalMode, this.twoDimensionalMode, this.skeletonMode, this.outlineMode));
        this.showExpandedHitbox.addDependentValues(this.showNormalHitbox);
        this.showBoundingBox.addDependentValues(this.priorityOnly);
        this.showName.addDependentValues(this.useDisplayName, this.showNameBackground);
        this.mode.whenEqualTo(this.twoDimensionalMode).applyTo(this.showBoundingBox, this.priorityOnly, this.healthBar, this.showName, this.useDisplayName, this.showNameBackground);
        this.mode.whenEqualTo(this.threeDimensionalMode).applyTo(this.showExpandedHitbox, this.showNormalHitbox);
        this.addValue(this.playerColor, this.mode, this.showExpandedHitbox, this.showNormalHitbox, this.showBoundingBox, this.priorityOnly, this.healthBar, this.showName, this.useDisplayName, this.showNameBackground, this.showInvisibles, this.hideBots);
    }


    public MutableColor resolveEntityColor(EntityPlayerSP viewer, Object entityHandle) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return null;
        }
        if (entityHandle == null) {
            return null;
        }
        Entity entity = new Entity(entityHandle);
        if (!entity.isInstance(MappedClasses.zm)) {
            return null;
        }
        if (entityHandle.equals(viewer)) {
            return null;
        }
        EntityLivingBase entityLivingBase = new EntityLivingBase(entityHandle);
        RenderEntityContext renderEntityContext = RenderEntityContextCache.getOrCreate(entityLivingBase, viewer);
        if (renderEntityContext.isSyntheticEntity()) {
            return null;
        }
        if (this.hideBots.getEffectiveValue().booleanValue() && renderEntityContext.isBot()) {
            return null;
        }
        if (!this.showInvisibles.getEffectiveValue().booleanValue() && renderEntityContext.isInvisibleWithoutEquipment()) {
            return null;
        }
        if (entityLivingBase.isInstance(MappedClasses.lG)) {
            MutableColor mutableColor = Vape.INSTANCE.getClientSettings().resolveEntityColor(renderEntityContext);
            if (mutableColor == null) {
                mutableColor = this.playerColor.getMutableColor();
            }
            return new MutableColor(((Color)mutableColor).getRGB(), ((Color)mutableColor).getAlpha());
        }
        return null;
    }

    public boolean isOutlineModeActive() {
        return this.mode.getValue() == this.outlineMode && this.outlineRenderer.isRenderingOutline();
    }
}

