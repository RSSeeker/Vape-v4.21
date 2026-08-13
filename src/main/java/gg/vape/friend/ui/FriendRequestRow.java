package gg.vape.friend.ui;

import gg.vape.friend.FriendRequest;
import gg.vape.friend.ui.FriendRequestNameTextComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class FriendRequestRow
extends PanelComponent {
    private static String[] obfuscationData;
    public TruncatedTextComponent nameLabel;
    private final PanelComponent actionPanel;
    private final FriendRequest request;
    private final TextButton addButton;
    private final PanelComponent namePanel;
    private final IconButtonComponent closeButton;
    private final PanelComponent closeButtonPanel;

    private FriendRequestRow(FriendRequest friendRequest) {
        super(100.0, 17.5);
        this.closeButton = new SquareIconButtonComponent("newclose", 1.0, new Color(0, 0, 0, 0), FriendRequestRow.J.l, 6.0, 6.0);
        this.addButton = new TextButton("ADD", 0.6, FriendRequestRow.J.B, FriendRequestRow.J.O, 14.0, 8.0);
        this.namePanel = new PanelComponent(74.0, 16.0);
        this.actionPanel = new PanelComponent(16.0, 16.0);
        this.closeButtonPanel = new PanelComponent(8.0, 16.0);
        this.addButton.setDeriveTextColorFromBackground(false);
        this.addButton.setNormalTextColor(Color.WHITE);
        this.request = friendRequest;
        this.setShowDisabledOverlay(false);
        this.namePanel.setShowDisabledOverlay(false);
        this.actionPanel.setShowDisabledOverlay(false);
        this.closeButtonPanel.setShowDisabledOverlay(false);
        this.actionPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
        this.closeButtonPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
        this.closeButtonPanel.h(this.closeButton, new Object[0]);
        this.nameLabel = new FriendRequestNameTextComponent(this, friendRequest.getFriend().getDisplayName(), "...", 66.0, 0.8, FriendRequestRow.J.Z, false);
        this.namePanel.addChildren(new SpacerComponent(6.0, 1.0), this.nameLabel);
        this.addChildren(this.namePanel, this.actionPanel, this.closeButtonPanel);
    }

    public FriendRequest getRequest() {
        return this.request;
    }

    public IconButtonComponent getCloseButton() {
        return this.closeButton;
    }

    public PanelComponent getActionPanel() {
        return this.actionPanel;
    }

    @Override
    public void H() {
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L() - 1.5, FriendRequestRow.J.m);
        this.nameLabel.S(this.n());
    }

    public static void setObfuscationData(String[] data) {
        obfuscationData = data;
    }

    public static String[] getObfuscationData() {
        return obfuscationData;
    }

    static {
        FriendRequestRow.setObfuscationData(new String[1]);
    }

    @Override
    public double C() {
        return 17.5;
    }

    public FriendRequestRow(FriendRequest friendRequest, FriendRequestNameTextComponent friendRequestNameTextComponent) {
        this(friendRequest);
    }

    public TextButton getAddButton() {
        return this.addButton;
    }
}
