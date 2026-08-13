package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.InventoryFilterPresetData;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.render.GuiRenderPrimitives;

public class InventoryFilterPresetOptionComponent
extends InteractiveComponent {
    private final ColorAnimation hoverAnimation;
    private GlyphIconComponent editIcon;
    private final TruncatedTextComponent nameText;
    private final InventoryFilterPresetData presetData;
    private boolean blatantMod;

    public boolean isBlatantMod() {
        return this.blatantMod;
    }

    public void setBlatantMod(boolean blatantMod) {
        this.blatantMod = blatantMod;
    }

    public GlyphIconComponent getEditIcon() {
        return this.editIcon;
    }

    InventoryFilterPresetOptionComponent(InventoryFilterPresetData inventoryFilterPresetData, boolean blatantMod) {
        this.hoverAnimation = new ColorAnimation(0.15, InventoryFilterPresetOptionComponent.J.B, InventoryFilterPresetOptionComponent.J.O);
        this.presetData = inventoryFilterPresetData;
        this.blatantMod = blatantMod;
        this.setPropagateMouseEvents(true);
        this.nameText = new TruncatedTextComponent(inventoryFilterPresetData == null ? "No rule" : inventoryFilterPresetData.getName(), "...", 30.0, 0.8, InventoryFilterPresetOptionComponent.J.A, true);
        if (inventoryFilterPresetData != null) {
            this.editIcon = new GlyphIconComponent("newedit", 5.0, 5.0, 8.0, 8.0, InventoryFilterPresetOptionComponent.J.A, InventoryFilterPresetOptionComponent.J.f, null);
        }
        this.addChildren(this.nameText);
        if (inventoryFilterPresetData != null) {
            this.addChildren(this.editIcon);
        }
    }


    @Override
    public void c() {
        this.nameText.K(this.G$src$D$1b2f02a() + 8.0);
        this.nameText.S(this.n());
        this.nameText.o(this.A() - 15.0);
        this.nameText.Y(this.L());
        this.nameText.setMaxWidth(this.nameText.A());
        if (this.editIcon != null) {
            this.editIcon.K(this.G$src$D$1b2f02a() + this.A() - this.editIcon.A() - 5.0);
            this.editIcon.S(this.n() + 3.5);
            this.editIcon.Y(this.L());
            this.editIcon.setVisible(!this.editIcon.getClickListeners().isEmpty() && this.w$src$Z$e457mb());
        }
        this.hoverAnimation.u(this.w$src$Z$e457mb());
        super.c();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (this.editIcon != null && this.editIcon.w$src$Z$e457mb()) {
            return;
        }
        super.g(guiMouseEvent);
    }

    @Override
    public void H() {
        if (this.blatantMod) {
            GuiRenderPrimitives.B(this.G$src$D$1b2f02a() + 4.0, this.n() + 0.5, this.A() - 8.0, this.L() - 1.0, InventoryFilterPresetOptionComponent.J.B, 2.0f);
        }
        if (this.w$src$Z$e457mb()) {
            GuiRenderPrimitives.B(this.G$src$D$1b2f02a() + 4.0, this.n() + 0.5, this.A() - 8.0, this.L() - 1.0, InventoryFilterPresetOptionComponent.J.M, 2.0f);
        }
    }
}

