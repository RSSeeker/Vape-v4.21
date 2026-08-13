package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineActivityPanelOptions;
import gg.vape.friend.ui.OnlineActivityPanelRefreshClickHandler;
import gg.vape.friend.ui.PartyActivityListPanel;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.DropdownSelectComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.input.BindValueRowComponent;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.frame.impl.hud.HudSettingsFrameBase;
import gg.vape.ui.click.layout.ComponentLayout;
import gg.vape.unmap.Bendable;
import gg.vape.unmap.ModeOption;
import gg.vape.utils.KeyBoardUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.Minecraft;

public class OnlineActivitySettingsFrame
extends HudSettingsFrameBase {
    private final DropdownSelectComponent<ModeOption> displayModeDropdown = new DropdownSelectComponent(OnlineConnectionManager.INSTANCE.getSettings().getInventorySwitchMode());
    private boolean inventoryVisible;
    private final PartyActivityListPanel activityListPanel;
    private static GuiComponent[] obfuscationComponents;
    private final OnlineActivityPanelOptions options;
    private boolean toggleBindingWasPressed;

    public static GuiComponent[] getObfuscationComponents() {
        return obfuscationComponents;
    }

    @Override
    public void Y() {
        if (this.isManagedByClickGui()) {
            return;
        }
        GuiRenderPrimitives.e(this.G$src$D$1b2f02a(), this.n(), this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().A(), this.q() ? 18.0 : 109.0, this.getDisabledOverlayColor(), this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() != null, 2.0f, 1.0f);
    }

    @Override
    protected void renderHudModeBorder() {
    }

    @Override
    public String getName() {
        return "Party Overlay";
    }

    static {
        OnlineActivitySettingsFrame.setObfuscationComponents(new GuiComponent[3]);
    }


    private boolean areAllBoundInputsPressed(Bendable binding) {
        if (binding.getBoundInputs().isEmpty()) {
            return false;
        }
        int pressedCount = 0;
        for (int inputCode : binding.getBoundInputs()) {
            if (!KeyBoardUtil.m(inputCode)) continue;
            ++pressedCount;
        }
        return pressedCount == binding.getBoundInputs().size();
    }

    @Override
    public void u() {
        Bendable inventoryBinding = OnlineConnectionManager.INSTANCE.getSettings().getInventoryDisplayBind();
        boolean screenAcceptsInput = Minecraft.currentScreen().isNotNull() && ClientSettings.INSTANCE.isInputEnabled();
        boolean bindingPressed = !screenAcceptsInput && !(ClientSettings.activeComponent instanceof TextInputComponentBase) && this.areAllBoundInputsPressed(inventoryBinding);
        boolean holdMode = this.displayModeDropdown.getSelectedValue() == null || this.displayModeDropdown.getSelectedValue().equals(OnlineConnectionManager.INSTANCE.getSettings().getHoldModeOption());
        if (holdMode) {
            this.getActivityListPanel().setInventoryVisible(bindingPressed);
        } else {
            if (bindingPressed && !this.toggleBindingWasPressed) {
                this.inventoryVisible = !this.inventoryVisible;
            }
            this.getActivityListPanel().setInventoryVisible(this.inventoryVisible);
        }
        this.toggleBindingWasPressed = bindingPressed;
    }

    public PartyActivityListPanel getActivityListPanel() {
        return this.activityListPanel;
    }

    public static PartyActivityListPanel getActivityListPanel(OnlineActivitySettingsFrame frame) {
        return frame.activityListPanel;
    }

    public static void setObfuscationComponents(GuiComponent[] components) {
        obfuscationComponents = components;
    }

    public OnlineActivitySettingsFrame() {
        super("party@2x", "Party Overlay");
        ComponentLayout componentLayout = this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij();
        componentLayout.t(false);
        componentLayout.U(false);
        componentLayout.M(false);
        componentLayout.I(false);
        componentLayout.u(false);
        componentLayout.M("wrap");
        this.setShowDisabledOverlay(false);
        BindValueRowComponent bindValueRowComponent = new BindValueRowComponent("Show inventory bind", OnlineConnectionManager.INSTANCE.getSettings().getInventoryDisplayBind(), OnlineActivitySettingsFrame.J.Z);
        bindValueRowComponent.w("Keybind to show inventory of party members");
        bindValueRowComponent.getBindInput().setActiveAlpha(20);
        this.options = OnlineActivityPanelOptions.INSTANCE;
        BooleanToggleComponent backgroundToggle = new BooleanToggleComponent(this.options.getRenderBackground());
        BooleanToggleComponent syncActivityToggle = new BooleanToggleComponent(OnlineConnectionManager.INSTANCE.getSettings().getShowSelf());
        syncActivityToggle.addMouseListener(new OnlineActivityPanelRefreshClickHandler(this));
        BooleanToggleComponent cpsToggle = new BooleanToggleComponent(this.options.getCpsDisplay());
        syncActivityToggle.o(110.0);
        backgroundToggle.o(110.0);
        cpsToggle.o(110.0);
        this.displayModeDropdown.o(110.0);
        bindValueRowComponent.o(110.0);
        this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().o(110.0);
        this.addSettings(bindValueRowComponent, this.displayModeDropdown, syncActivityToggle,
                backgroundToggle, cpsToggle);
        this.h(new SpacerComponent(1.0, 4.0), new Object[0]);
        this.activityListPanel = new PartyActivityListPanel();
        this.addChildren(this.activityListPanel);
    }
}

