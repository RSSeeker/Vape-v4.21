package gg.vape.click;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.input.AttackKeyController;
import gg.vape.input.KeyBindingHelper;
import gg.vape.input.KeyBindingInputState;
import gg.vape.module.Mod;
import gg.vape.rotation.PlayerMouseRotationApplier;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.MathUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.LimitValue;
import gg.vape.value.ModeValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.WorldClient;
import java.util.Random;

public class ClickEngine {
    private static GuiComponent[] clickStateMarker;

    public final Random random;
    public final float maxSignedShortValue;
    private int slowPhaseLength;
    private double remainingJitterSteps;
    private boolean burstActive;
    private double jitterStepCount;
    private int fastPhaseClickCount;
    private int slowPhaseClickCount;
    private long lastClickDelayMillis;
    private int configuredFastPhaseLength;
    private double horizontalJitterOffset;
    private double verticalJitterOffset;
    private final BooleanValue itemLimitEnabled;
    private final ModeValue randomizationMode;
    private final BooleanValue jitterEnabled;
    private final RandomValue cpsRange;
    private final Mod ownerModule;
    private float pendingYawJitter;
    private final ClickButton button;
    private final AutoClickerTimingState timingState;
    private int burstClickCount;
    private boolean fastPhaseActive;
    private final LimitValue itemWhitelist;
    private float pendingPitchJitter;
    private int burstLength;
    private final BooleanValue holdToClick;
    private int mouseDownMessage;
    private int mouseUpMessage;
    private int mouseButtonCode;

    public ClickEngine(ClickButton button, RandomValue cpsRange, BooleanValue itemLimitEnabled,
            LimitValue itemWhitelist, BooleanValue holdToClick, ModeValue randomizationMode,
            BooleanValue jitterEnabled, Mod ownerModule) {
        this.random = new Random();
        this.maxSignedShortValue = 32767.0f;
        this.fastPhaseActive = true;
        this.button = button;
        this.cpsRange = cpsRange;
        this.itemLimitEnabled = itemLimitEnabled;
        this.itemWhitelist = itemWhitelist;
        this.holdToClick = holdToClick;
        this.randomizationMode = randomizationMode;
        this.ownerModule = ownerModule;
        this.jitterEnabled = jitterEnabled;
        this.itemLimitEnabled.addDependentValues(this.itemWhitelist);

        if (button == ClickButton.LEFT) {
            this.mouseDownMessage = 513;
            this.mouseUpMessage = 514;
            this.mouseButtonCode = 1;
        } else if (button == ClickButton.RIGHT) {
            this.mouseDownMessage = 516;
            this.mouseUpMessage = 517;
            this.mouseButtonCode = 2;
        }
        this.timingState = new AutoClickerTimingState(Vape.INSTANCE.getAccountTier());
    }

    public ClickEngine(ClickButton button, RandomValue cpsRange, BooleanValue itemLimitEnabled,
            LimitValue itemWhitelist, BooleanValue holdToClick, ModeValue randomizationMode,
            BooleanValue jitterEnabled) {
        this(button, cpsRange, itemLimitEnabled, itemWhitelist, holdToClick, randomizationMode,
                jitterEnabled, null);
    }

    public static void setClickStateMarker(GuiComponent[] marker) {
        clickStateMarker = marker;
    }

    public static GuiComponent[] getClickStateMarker() {
        return clickStateMarker;
    }

    public void applyJitterRotation(EventPreRenderTick event) {
        if (!this.jitterEnabled.getEffectiveValue().booleanValue()
                || this.pendingYawJitter == 0.0f && this.pendingPitchJitter == 0.0f) {
            return;
        }
        WorldClient world = event.getWorld();
        EntityPlayerSP player = event.getThePlayer();
        if (world.isNull() || player.isNull() || Minecraft.currentScreen().isNotNull()) {
            return;
        }

        float mouseSensitivity = Minecraft.gameSettings().y();
        int yawDelta = (int)this.pendingYawJitter;
        int pitchDelta = (int)(-this.pendingPitchJitter);
        float sensitivityFactor = mouseSensitivity * 0.6f + 0.2f;
        float mouseScale = sensitivityFactor * sensitivityFactor * sensitivityFactor * 8.0f;
        PlayerMouseRotationApplier.applyTrackedMouseDelta(yawDelta * mouseScale, pitchDelta * mouseScale);
        this.pendingYawJitter = MathUtil.floor(MathUtil.c(this.pendingYawJitter, 1.0f));
        this.pendingPitchJitter = MathUtil.floor(MathUtil.c(this.pendingPitchJitter, 1.0f));
    }

    public ClickButton getButton() {
        return this.button;
    }

    public RandomValue getCpsRange() {
        return this.cpsRange;
    }

    public void pressAttack() {
        if (this.ownerModule != null) {
            AttackKeyController.requestSyntheticAttack(this.ownerModule);
            return;
        }
        KeyBindingInputState.pressAttackKey(true);
    }

    public void pressUseItem() {
        if (this.ownerModule != null) {
            KeyBindingHelper.updateKeyBinding(Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362(),
                    true, true);
            return;
        }
        KeyBindingInputState.pressUseKey(true);
    }

    public void pressClickButton() {
        if (this.button == ClickButton.LEFT) {
            this.pressAttack();
        } else {
            this.pressUseItem();
        }
        this.generateJitter();
    }

    public long calculateNextClickDelay() {
        int minimumCps = this.cpsRange.getMinimumInt();
        int maximumCps = this.cpsRange.getMaximumInt();
        int randomizationModeIndex = this.randomizationMode == null
                ? 2
                : this.randomizationMode.getSelectedIndex();
        int cpsRangeSize = maximumCps - minimumCps;
        int selectedCps = cpsRangeSize <= 0
                ? minimumCps
                : this.random.nextInt(cpsRangeSize) + minimumCps + 1;

        if (randomizationModeIndex == 0) {
            this.lastClickDelayMillis = 1000 / selectedCps;
            return this.lastClickDelayMillis;
        }
        if (selectedCps == 0) {
            selectedCps = 1;
        }
        if (randomizationModeIndex == 1) {
            return this.calculateLegacyRandomizedDelay(selectedCps);
        }

        this.timingState.configureCpsRange(minimumCps, maximumCps);
        return this.timingState.getNextDelayMillis();
    }

    private long calculateLegacyRandomizedDelay(int selectedCps) {
        if (!this.burstActive) {
            this.lastClickDelayMillis = 1000 / selectedCps;
            if (this.random.nextInt(4) == 1) {
                this.burstActive = true;
                this.burstLength = 1 + this.random.nextInt(5);
            } else if (this.random.nextInt(10) != 1 && this.random.nextInt(10) == 1) {
                this.burstActive = true;
                this.burstLength = 5 + this.random.nextInt(10);
            }
        }
        if (this.burstActive && ++this.burstClickCount >= this.burstLength) {
            this.burstClickCount = 0;
            this.burstActive = false;
        }
        if (this.random.nextInt(48) % (this.fastPhaseActive ? 6 : 10) == 0 && !this.burstActive) {
            this.lastClickDelayMillis += this.random.nextInt(45) + 40L;
        }
        if (this.fastPhaseActive) {
            if (++this.fastPhaseClickCount >= this.configuredFastPhaseLength) {
                this.slowPhaseLength = 75 + this.random.nextInt(125);
                this.fastPhaseActive = false;
                this.fastPhaseClickCount = 0;
            }
            long phaseDelay = this.random.nextInt(5) == 3 ? 50L : 25L;
            return this.lastClickDelayMillis + phaseDelay;
        }
        if (++this.slowPhaseClickCount >= this.slowPhaseLength) {
            this.fastPhaseActive = true;
            this.configuredFastPhaseLength = 7 + this.random.nextInt(8);
            this.slowPhaseClickCount = 0;
        }
        return this.lastClickDelayMillis;
    }

    public void releaseClickButton() {
        if (this.button == ClickButton.LEFT) {
            this.releaseAttack();
        } else {
            this.releaseUseItem();
        }
    }

    public void releaseUseItem() {
        if (this.ownerModule != null) {
            KeyBindingHelper.updateKeyBinding(Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362(),
                    false, false);
            return;
        }
        KeyBindingInputState.releaseUseKey(true);
    }

    private KeyBinding getConfiguredKeyBinding() {
        if (this.button == ClickButton.LEFT) {
            return Minecraft.gameSettings().F();
        }
        return Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
    }

    private boolean isItemRestrictionSatisfied() {
        if (Minecraft.currentScreen().isNotNull()) {
            return true;
        }
        if (!this.itemLimitEnabled.getEffectiveValue().booleanValue()) {
            return true;
        }
        return this.itemWhitelist.matchesHeldItem();
    }

    public void releaseAttack() {
        if (this.ownerModule != null) {
            AttackKeyController.releaseAttackKey();
            return;
        }
        KeyBindingInputState.releaseAttackKey(true);
    }

    public int countOccupiedHotbarSlots(EntityPlayerSP player) {
        Object[] hotbarContents = Minecraft.thePlayer()
                .V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().M();
        int occupiedSlotCount = 0;
        for (int slotIndex = 0; slotIndex < 9; ++slotIndex) {
            ItemStack itemStack = new ItemStack(hotbarContents[slotIndex]);
            if (!itemStack.isNull()) {
                ++occupiedSlotCount;
            }
        }
        return occupiedSlotCount;
    }

    public boolean canClick(EntityPlayerSP player) {
        if (Minecraft.thePlayer().isNull()) {
            return false;
        }
        return this.isItemRestrictionSatisfied();
    }

    public boolean isActivationHeld() {
        if (!this.holdToClick.getEffectiveValue().booleanValue()) {
            return Minecraft.a();
        }
        return ClientSettings.isKeyBindingDown(this.getConfiguredKeyBinding());
    }

    public void generateJitter() {
        if (!this.jitterEnabled.getEffectiveValue().booleanValue()) {
            return;
        }
        float maximumOffset = 7.0f;
        this.horizontalJitterOffset = MathUtil.random(this.random, -maximumOffset, maximumOffset);
        this.verticalJitterOffset = MathUtil.random(this.random, -maximumOffset, maximumOffset);
        this.remainingJitterSteps = this.jitterStepCount
                = (MathUtil.H(this.horizontalJitterOffset) + MathUtil.H(this.verticalJitterOffset)) * 0.45;
    }

    public void updateJitter(EventPreTick event) {
        if (!this.jitterEnabled.getEffectiveValue().booleanValue()) {
            return;
        }
        if (this.remainingJitterSteps > 0.0) {
            this.pendingYawJitter += (float)(this.horizontalJitterOffset / this.jitterStepCount);
            this.pendingPitchJitter += (float)(this.verticalJitterOffset / this.jitterStepCount);
            this.remainingJitterSteps -= 1.0;
        } else {
            this.pendingYawJitter = MathUtil.floor(MathUtil.c(this.pendingYawJitter, 1.0f));
            this.pendingPitchJitter = MathUtil.floor(MathUtil.c(this.pendingPitchJitter, 1.0f));
        }
    }
}
