package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.event.impl.EventRender3D;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.EntityAngleComparator;
import gg.vape.utils.EntityArmorValueComparator;
import gg.vape.utils.EntityDistanceComparator;
import gg.vape.utils.EntityEquipmentValueComparator;
import gg.vape.utils.EntityHealthComparator;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.EntityTargetFilterValue;
import gg.vape.value.LimitValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomClickDelayValue;
import gg.vape.value.Value;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Screen;
import gg.vape.wrapper.impl.TitledScreen;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class KillAura
extends Mod {
    public final LimitValue allowedItems;
    public final BooleanValue guiCheck;
    public final ModeOption yawMode;
    public final NumberValue swingRange;
    public final BooleanValue showTarget;
    public final NumberValue attackRange;
    public final BooleanValue disableOnDeath;
    public final NumberValue maxAngle;
    public final NumberValue maxTargets;
    public final ModeOption armorMode;
    private final ColorValue attackColor;
    public final BooleanValue perfectSwing;
    public final RandomClickDelayValue attackRate;
    public final ModeOption healthMode;
    private static final long MODULE_ID = 3138688411160482102L;
    private boolean deathScreenHandled = false;
    public int pauseTicks;
    public final EntityTargetFilterValue targetFilter = EntityTargetFilterValue.createForModule(this);
    public ModeValue targetMode;
    public final BooleanValue requireMouseDown;
    public final ModeOption threatMode;
    private final ColorValue targetColor;
    private final ModeOption distanceMode;
    public final BooleanValue limitToItems;
    public List<EntityLivingBase> targets;

    public boolean hasTargets() {
        return this.isEnabled() && !this.targets.isEmpty();
    }


    private boolean isValidTarget(EntityLivingBase entityLivingBase) {
        if (this.limitToItems.getEffectiveValue()) {
            ItemStack itemStack = Minecraft.thePlayer().getHeldItemHand();
            if (itemStack.isNull()) {
                return false;
            }
            Item item = itemStack.getItem();
            if (item.isNull()) {
                return false;
            }
            return this.allowedItems.isValid(itemStack, false) && this.targetFilter.isValidTarget(entityLivingBase);
        }
        return this.targetFilter.isValidTarget(entityLivingBase);
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onAttackTick(EventPrePlayerTick event) {
        if ((Double)this.swingRange.getValue() < (Double)this.attackRange.getValue()) {
            this.swingRange.setValue((Double)this.attackRange.getValue() + 0.1);
        }
        if (this.pauseTicks > 0) {
            --this.pauseTicks;
            return;
        }
        if (this.guiCheck.getEffectiveValue() && Minecraft.currentScreen().isNotNull()) {
            this.pauseTicks = 1;
            return;
        }
        EntityPlayerSP player = event.getThePlayer();
        if (this.disableOnDeath.getEffectiveValue() && this.handleDeathScreen(player)) {
            return;
        }
        if (this.requireMouseDown.getEffectiveValue() && !ClientSettings.isAttackButtonDown()) {
            this.targets.clear();
            return;
        }
        this.updateTargets(player, event.getWorld());
        if (!this.isAttackReady() || !this.hasTargetInSwingRange(player)) {
            return;
        }

        boolean blockWhileAttacking = ClientSettings.isUseItemButtonDown();
        KeyBinding useItemKey = Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
        if (blockWhileAttacking && useItemKey.isKeyDown()) {
            useItemKey.setPressed(false);
            return;
        }
        boolean attackedAnyTarget = this.attackTargets(
                player, ForgeVersion.MC_1_12_2.v(), blockWhileAttacking);
        if (!attackedAnyTarget && blockWhileAttacking) {
            useItemKey.setPressed(true);
        }
        this.attackRate.resetClickTimer();
    }

    private boolean handleDeathScreen(EntityPlayerSP player) {
        if (player.M$src$Z$ff28xj() || player.w$src$F$15l9epb() <= 0.0f) {
            this.toggle();
            return true;
        }
        Screen screen = ForgeVersion.MC_1_16_5.d() ? Minecraft.currentScreen() : Minecraft.k();
        if (screen.isNull()) {
            return false;
        }
        boolean deathScreen;
        if (ForgeVersion.MC_1_16_5.d()) {
            deathScreen = screen.isInstance(MappedClasses.D2);
            if (!deathScreen) {
                this.deathScreenHandled = false;
            }
        } else {
            String title = ((TitledScreen)screen).getDisplayedTitle();
            deathScreen = title != null
                    && (title.toLowerCase().contains("died") || title.toLowerCase().contains("dead"));
            if (title == null || title.isEmpty()) {
                this.deathScreenHandled = false;
            }
        }
        if (!this.deathScreenHandled && deathScreen) {
            this.deathScreenHandled = true;
            this.toggle();
            return true;
        }
        return false;
    }

    private boolean hasTargetInSwingRange(EntityPlayerSP player) {
        for (EntityLivingBase target : this.targets) {
            if (this.canAttackTarget(player, target)
                    && player.getDistanceToEntity(target) <= (Double)this.swingRange.getValue()) {
                return true;
            }
        }
        return false;
    }

    private boolean attackTargets(EntityPlayerSP player, boolean swingBeforeAttack,
                                  boolean blockWhileAttacking) {
        boolean attackedAnyTarget = false;
        for (EntityLivingBase target : this.targets) {
            if (!this.canAttackTarget(player, target)) {
                continue;
            }
            double distance = player.getDistanceToEntity(target);
            if (distance > (Double)this.swingRange.getValue()) {
                continue;
            }
            if (swingBeforeAttack) {
                player.m$src$V$15frh5h();
                if (distance <= (Double)this.attackRange.getValue()) {
                    Minecraft.playerController().attackEntity(player, target);
                }
            } else if (distance <= (Double)this.attackRange.getValue()) {
                Minecraft.playerController().attackEntity(player, target);
                player.m$src$V$15frh5h();
            } else {
                player.m$src$V$15frh5h();
                player.i$src$V$1imgzyw();
            }
            if (blockWhileAttacking) {
                attackedAnyTarget = true;
                Minecraft.S();
            }
        }
        return attackedAnyTarget;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.targets.clear();
    }

    public void updateTargets(EntityPlayerSP player, WorldClient world) {
        this.targets.clear();
        ArrayList<?> loadedEntities = new ArrayList<>(world.z());
        for (Object underlyingEntity : loadedEntities) {
            Entity entity = new Entity(underlyingEntity);
            EntityLivingBase candidate = new EntityLivingBase(underlyingEntity);
            if (ClientSettings.IS_LEGACY_1_7 && entity.isInstance(MappedClasses.FT)
                    || !entity.isInstance(MappedClasses.zm)
                    || !this.canAttackTarget(player, candidate)) {
                continue;
            }
            this.targets.add(candidate);
        }
        if (this.targetMode.getValue() == this.yawMode) {
            this.targets.sort(new EntityAngleComparator());
        } else if (this.targetMode.getValue() == this.distanceMode) {
            this.targets.sort(new EntityDistanceComparator());
        } else if (this.targetMode.getValue() == this.threatMode) {
            this.targets.sort(new EntityArmorValueComparator());
        } else if (this.targetMode.getValue() == this.armorMode) {
            this.targets.sort(new EntityEquipmentValueComparator());
        } else if (this.targetMode.getValue() == this.healthMode) {
            this.targets.sort(new EntityHealthComparator());
        }
        ArrayList<EntityLivingBase> sortedTargets = new ArrayList<>(this.targets);
        this.targets.clear();
        int targetLimit = ((Double)this.maxTargets.getValue()).intValue();
        for (int index = 0; index < targetLimit && index < sortedTargets.size(); ++index) {
            this.targets.add(sortedTargets.get(index));
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onDeathCheck(EventPrePlayerTick event) {
        if (this.disableOnDeath.getEffectiveValue()
                && (event.getPlayer().M$src$Z$ff28xj() || event.getPlayer().w$src$F$15l9epb() <= 0.0f)
                && this.isEnabled()) {
            this.toggle();
            return;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.targets.clear();
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onRender3D(EventRender3D eventRender3D) {
        RenderUtils.g();
        if (this.showTarget.getEffectiveValue() && !this.targets.isEmpty()) {
            EntityPlayerSP player = eventRender3D.getThePlayer();
            for (EntityLivingBase target : this.targets) {
                if (player.getDistanceToEntity(target) <= (Double)this.attackRange.getValue()) {
                    RenderUtil.k(target, 0.0, null, this.attackColor.getMutableColor(), eventRender3D.getTicks());
                    continue;
                }
                RenderUtil.k(target, 0.0, null, this.targetColor.getMutableColor(), eventRender3D.getTicks());
            }
        }
        RenderUtils.f();
    }

    public boolean isAttackReady() {
        EntityPlayerSP player = Minecraft.thePlayer();
        return ForgeVersion.MC_1_8_9.Y() && this.perfectSwing.getEffectiveValue()
                ? player.getCooledAttackStrength(0.5f) == 1.0f
                : this.attackRate.hasClickDelayElapsed();
    }

    public boolean canAttackTarget(EntityPlayerSP player, EntityLivingBase target) {
        if (target.isNull()) {
            return false;
        }
        if (target.equals(player)) {
            return false;
        }
        if (!this.isValidTarget(target)) {
            return false;
        }
        if (target.w$src$F$15l9epb() <= 0.0f || target.M$src$Z$ff28xj()) {
            return false;
        }
        if (player.getDistanceToEntity(target) >= (Double)this.swingRange.getValue()) {
            return false;
        }
        if (RotationUtil.a(player, target) > ((Double)this.maxAngle.getValue()).intValue() / 2) {
            return false;
        }
        if (Vape.INSTANCE.getFriendManager().isFriend(target)) {
            return false;
        }
        return !target.equals(player.S$src$Lgg_vape_wrapper_impl_Entity_$dgzs12());
    }

    public KillAura() {
        super("Killaura", (int)MODULE_ID, Category.OTHER, "Attack players around you\nwithout aiming at them.");
        this.swingRange = NumberValue.create(this, "Swing range", "#.#", "", 0.0, 4.0, 6.0);
        this.attackRange = NumberValue.create(this, "Attack range", "#.#", "", 0.0, 3.5, 6.0);
        this.requireMouseDown = BooleanValue.create(this, "Require mouse down", false);
        this.disableOnDeath = BooleanValue.create(this, "Disable on death", false);
        this.showTarget = BooleanValue.create(this, "Show target", false);
        this.limitToItems = BooleanValue.create(this, "Limit to items", false, "Killaura functions only while holding selected items");
        this.guiCheck = BooleanValue.create(this, "GUI check", true, "Does not attack when inside of a GUI.");
        this.perfectSwing = BooleanValue.create(this, "Perfect swing", false, "Attacks perfectly on 1.9+ combat servers.\n(1.12.2 Only)");
        this.attackRate = RandomClickDelayValue.create(this, "Attacks per Second", "#.#", "", 1.0, 6.0, 13.0, 20.0);
        this.maxAngle = NumberValue.create((Object)this, "Max angle", "#", "", 1.0, 90.0, 360.0, 5.0);
        this.maxTargets = NumberValue.create((Object)this, "Max targets", "#", "", 1.0, 1.0, 6.0, 1.0);
        this.targetColor = ColorValue.createWithAlpha(this, "Target Color", new Color(255, 200, 112), 50);
        this.attackColor = ColorValue.create(this, "Attack Color", new Color(255, 0, 0, 100));
        this.distanceMode = new ModeOption("Distance");
        this.yawMode = new ModeOption("Yaw");
        this.armorMode = new ModeOption("Armor");
        this.threatMode = new ModeOption("Threat");
        this.healthMode = new ModeOption("Health");
        this.allowedItems = LimitValue.create(this, "killaura-alloweditems", "Allowed Items", LimitValue.ALLOW_LIST_COLOR, Collections.emptyList());
        this.targets = new CopyOnWriteArrayList<EntityLivingBase>();
        this.targetMode = ModeValue.create((Object)this, "Target Mode", "How Killaura should prioritize targets.\nArmor/Threat will default to Distance for non player targets.", (ModeSelection)this.distanceMode, this.distanceMode, this.yawMode, this.armorMode, this.threatMode, this.healthMode);
        this.addValue(this.targetFilter, this.attackRate, this.swingRange, this.attackRange,
                this.maxAngle, this.maxTargets, this.targetMode);
        this.U(this.perfectSwing, ForgeVersion.MC_1_8_9.N());
        this.showTarget.addDependentValues(this.targetColor, this.attackColor);
        this.addValue(new Value[]{this.disableOnDeath, this.requireMouseDown, this.guiCheck,
                this.showTarget, this.targetColor, this.attackColor,
                this.limitToItems.addDependentValues(this.allowedItems), this.allowedItems});
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.swingRange.addChangeListener(numberValue -> {
            if (!atomicBoolean.get() && (Double)numberValue.getValue() < (Double)this.attackRange.getValue()) {
                atomicBoolean.set(true);
                numberValue.setValue((Double)this.attackRange.getValue() + numberValue.getIncrement());
                atomicBoolean.set(false);
            }
        });
        this.attackRange.addChangeListener(numberValue -> {
            if (!atomicBoolean.get() && (Double)numberValue.getValue() > (Double)this.swingRange.getValue()) {
                atomicBoolean.set(true);
                numberValue.setValue((Double)this.swingRange.getValue() - numberValue.getIncrement());
                atomicBoolean.set(false);
            }
        });
    }
}
