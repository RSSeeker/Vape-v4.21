package gg.vape.ui.click.frame.impl.hud;

import com.google.gson.JsonObject;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.frame.impl.quickactions.QuickActionsFrame;
import gg.vape.value.BooleanValue;

public class InventoryOverlaySettingsFrame
extends HudSettingsFrameBase {
    private final BooleanValue showHotbar = BooleanValue.create(this, "Show Hotbar", false,
            "Renders the hotbar slots below the inventory.");
    private final BooleanValue renderBackground = BooleanValue.createFull(this,
            "InventoryRenderBackground", "Render Background", true,
            "Renders and blurs the background behind the inventory.");
    private boolean previousShowHotbar;

    public InventoryOverlaySettingsFrame() {
        super("inventory", "Inventory");
        this.addSettings(new BooleanToggleComponent(this.renderBackground),
                new BooleanToggleComponent(this.showHotbar));
        this.h(new InventoryOverlayComponent(this), new Object[0]);
        this.previousShowHotbar = this.showHotbar.getEffectiveValue();
    }

    public boolean shouldShowHotbar() {
        return this.showHotbar.getEffectiveValue();
    }

    public boolean shouldRenderBackground() {
        return this.renderBackground.getEffectiveValue();
    }

    @Override
    public String getName() {
        return "Inventory Overlay";
    }

    @Override
    public void Y() {
        if (this.previousShowHotbar != this.showHotbar.getEffectiveValue()) {
            this.previousShowHotbar = this.showHotbar.getEffectiveValue();
            this.H(true);
        }
    }

    @Override
    public void t(JsonObject jsonObject) {
        super.t(jsonObject);
        ClientSettings.getFrame(QuickActionsFrame.class).getInventoryOverlayRow()
                .setValue(this.V$src$Z$1xhop3l());
    }

    @Override
    public void v() {
    }

    @Override
    protected void renderHudModeBorder() {
    }
}
