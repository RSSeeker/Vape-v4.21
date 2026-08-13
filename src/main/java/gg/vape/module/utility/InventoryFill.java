package gg.vape.module.utility;

import gg.vape.Vape;
import gg.vape.click.AutoClickerTimingState;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.input.KeyBindingInputState;
import gg.vape.input.KeyboardInput;
import gg.vape.input.MouseInput;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.GuiContainer;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Slot;

public class InventoryFill
extends Mod {
    private final AutoClickerTimingState timingState;
    private int lastClickedSlot = -1;
    private final TimerUtil clickTimer = new TimerUtil();
    private final NumberValue cpsValue = NumberValue.create(this, "CPS", "#.#", "", 1.0, 15.0, 20.0);

    private boolean rollChance(double chance) {
        double clampedChance = Math.max(Math.min(chance, 1.0), 0.0);
        return Math.random() <= clampedChance;
    }

    @EventHandler
    public final void onPreRenderTick(EventPreRenderTick eventPreRenderTick) {
        if (!KeyBindingInputState.isLeftButtonDown()) {
            return;
        }
        this.timingState.configureCpsRange((int)((Double)this.cpsValue.getValue() - 1.0), (int)((Double)this.cpsValue.getValue() + 1.0));
        if (!this.clickTimer.hasTimeElapsed(this.timingState.getNextDelayMillis()) && (Double)this.cpsValue.getValue() < 20.0) {
            return;
        }
        GuiScreen guiScreen = Minecraft.currentScreen();
        boolean isContainerScreen = guiScreen.isInstance(MappedClasses.Ft);
        if (isContainerScreen) {
            boolean isShiftHeld = KeyboardInput.isKeyDown(160) || KeyboardInput.isKeyDown(161);
            if (isShiftHeld && guiScreen.isNotNull()) {
                GuiContainer guiContainer = new GuiContainer(guiScreen);
                this.clickHoveredSlot(guiContainer);
            }
        }
    }


    public InventoryFill() {
        super("InventoryFill", -12288, Category.INVENTORY, "Clicks items in inventory while holding shift");
        this.timingState = new AutoClickerTimingState(Vape.INSTANCE.getAccountTier());
        this.addValue(this.cpsValue);
    }

    public void clickHoveredSlot(GuiContainer guiContainer) {
        ItemStack heldItem;
        boolean outsideBounds;
        int hoveredSlotIndex = -1;
        int mouseX = MouseInput.getMouseX() * guiContainer.g() / Minecraft.J();
        int mouseY = guiContainer.k() - MouseInput.getInvertedMouseY() * guiContainer.k() / Minecraft.h() - 1;
        Slot slot = guiContainer.getSlotAtPosition(mouseX, mouseY);
        int guiLeft = guiContainer.p();
        int guiTop = guiContainer.v();
        boolean outside = outsideBounds = mouseX < guiLeft || mouseY < guiTop || mouseX >= guiLeft + guiContainer.x() || mouseY >= guiTop + guiContainer.b();
        if (slot.isNotNull()) {
            hoveredSlotIndex = slot.getSlotNumber();
        }
        if (outsideBounds) {
            hoveredSlotIndex = -1;
        }
        if (hoveredSlotIndex >= 0 && (heldItem = RotationUtil.Z()).isNull() && this.lastClickedSlot != hoveredSlotIndex) {
            KeyBindingInputState.sendLeftButtonDown();
            KeyBindingInputState.sendLeftButtonUp();
            if (this.rollChance(0.8)) {
                this.lastClickedSlot = hoveredSlotIndex;
            }
            this.clickTimer.reset();
        }
    }
}

