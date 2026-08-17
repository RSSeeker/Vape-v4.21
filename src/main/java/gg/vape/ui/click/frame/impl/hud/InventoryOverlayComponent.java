package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.BlurRegionRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ItemIconRenderer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.InventoryPlayer;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;

public class InventoryOverlayComponent
extends GuiComponent {
    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 120);
    private static final Color SLOT_COLOR = new Color(0, 0, 0, 55);
    private static final int COLUMN_COUNT = 9;
    private static final int INVENTORY_ROW_COUNT = 3;
    private static final int SLOT_SIZE = 16;
    private static final int SLOT_SPACING = 18;
    private static final int CONTENT_INSET = 2;
    private static final double WIDTH = 164.0;

    private final InventoryOverlaySettingsFrame settingsFrame;
    private final BlurRegionRenderer backgroundBlur = new BlurRegionRenderer(0, 0);

    public InventoryOverlayComponent(InventoryOverlaySettingsFrame settingsFrame) {
        this.settingsFrame = settingsFrame;
        this.o(WIDTH);
        this.Y(this.calculateHeight(INVENTORY_ROW_COUNT));
        this.setShowDisabledOverlay(false);
    }

    private double calculateHeight(int rowCount) {
        return 4.0 + (double)(rowCount - 1) * SLOT_SPACING + SLOT_SIZE;
    }

    @Override
    public double L() {
        return this.calculateHeight(this.settingsFrame.shouldShowHotbar() ? 4 : INVENTORY_ROW_COUNT);
    }

    private void renderInventory() {
        if (this.settingsFrame.shouldRenderBackground()) {
            this.backgroundBlur.setDimensions((int)this.A() * 2, (int)this.L() * 2);
            this.backgroundBlur.renderBlur((int)this.G$src$D$1b2f02a(), (int)this.n(), 6.0f, 4.0f);
            GuiRenderPrimitives.B(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(),
                    this.settingsFrame.applyDefaultEditorAlpha(BACKGROUND_COLOR), 3.0f);
        }

        EntityPlayerSP player = Minecraft.thePlayer();
        InventoryPlayer inventory = player.isNotNull()
                ? player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6()
                : null;
        int rowCount = this.settingsFrame.shouldShowHotbar() ? 4 : INVENTORY_ROW_COUNT;
        for (int row = 0; row < rowCount; ++row) {
            for (int column = 0; column < COLUMN_COUNT; ++column) {
                double slotX = this.G$src$D$1b2f02a() + CONTENT_INSET + column * SLOT_SPACING;
                double slotY = this.n() + CONTENT_INSET + row * SLOT_SPACING;
                if (this.settingsFrame.shouldRenderBackground()) {
                    GuiRenderPrimitives.B(slotX, slotY, SLOT_SIZE, SLOT_SIZE,
                            this.settingsFrame.applyDefaultEditorAlpha(SLOT_COLOR), 1.5f);
                }
                if (inventory == null || inventory.isNull()) {
                    continue;
                }
                int slotIndex = row < INVENTORY_ROW_COUNT
                        ? 9 + row * COLUMN_COUNT + column
                        : column;
                ItemStack itemStack = inventory.c(slotIndex);
                if (itemStack.isNull()) {
                    continue;
                }
                ItemIconRenderer.renderItemStack(itemStack, (float)slotX, (float)slotY,
                        SLOT_SIZE, SLOT_SIZE, this.settingsFrame.getEditorOpacity());
                this.renderStackCount(itemStack, slotX, slotY);
            }
        }
    }

    private void renderStackCount(ItemStack itemStack, double slotX, double slotY) {
        if (itemStack.t() <= 1) {
            return;
        }
        String countText = String.valueOf(itemStack.t());
        SmoothFontRenderer fontRenderer = this.getAlternateFontRenderer(0.65);
        double countX = slotX + SLOT_SIZE - fontRenderer.N(countText) - 1.0;
        double countY = slotY + SLOT_SIZE - fontRenderer.d(countText);
        fontRenderer.v(countText, countX, countY, this.settingsFrame.getEditorForegroundColor());
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public void I() {
        this.renderInventory();
    }

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public void u() {
    }

    @Override
    public void F() {
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void H() {
        this.renderInventory();
    }
}
