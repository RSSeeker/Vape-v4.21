package gg.vape.friend.ui;

import gg.vape.friend.PartyState;
import gg.vape.friend.ui.OnlineChatPanel;
import gg.vape.friend.ui.PartyChatSender;
import gg.vape.friend.ui.PartyDetailsPanel;
import gg.vape.friend.ui.PartyMemberRow;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.utils.render.GuiRenderPrimitives;

public class PartyDetailsAndChatPanel
extends PanelComponent {
    private final PartyDetailsPanel detailsPanel;
    private final PartyState partyState;
    private static int obfuscationSeed;
    private final PanelComponent reservedHeaderPanel = new PanelComponent(100.0, 24.0);
    private static final String WRAP_LAYOUT;
    private final OnlineChatPanel chatPanel = new OnlineChatPanel(new PartyChatSender());

    public static int getReservedZero() {
        int reserved = PartyDetailsAndChatPanel.getObfuscationSeed();
        return 0;
    }

    @Override
    public double C() {
        return this.reservedHeaderPanel.L() + this.chatPanel.L() - 5.0;
    }

    @Override
    public void c() {
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a() - 3.0, this.n() - 3.0, this.A() + 6.0, this.L() + 6.0, PartyDetailsAndChatPanel.J.i);
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), PartyDetailsAndChatPanel.J.m);
        GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), PartyDetailsAndChatPanel.J.l, 2.0f, 1.0f, 1.0f);
        super.c();
    }

    public PartyDetailsAndChatPanel(PartyState partyState) {
        super(99.0, 24.0);
        this.partyState = partyState;
        this.reservedHeaderPanel.setShowDisabledOverlay(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(WRAP_LAYOUT);
        this.setShowDisabledOverlay(false);
        GuiComponent[] guiComponentArray = new GuiComponent[2];
        this.detailsPanel = new PartyDetailsPanel(this.partyState);
        guiComponentArray[0] = new PaddedComponent(4.0, this.detailsPanel);
        guiComponentArray[1] = this.chatPanel;
        this.addChildren(guiComponentArray);
        for (PartyMemberRow partyMemberRow : partyState.getChatRows()) {
            this.chatPanel.getMessageListPanel().addMessageRow(partyMemberRow);
        }
    }

    public static int getObfuscationSeed() {
        return obfuscationSeed;
    }

    static {
        PartyDetailsAndChatPanel.setObfuscationSeed(26);
        WRAP_LAYOUT = "wrap";
    }

    @Override
    public void z(boolean bl) {
    }

    public static void setObfuscationSeed(int seed) {
        obfuscationSeed = seed;
    }

    public IconButtonComponent getCloseButton() {
        return this.detailsPanel.getCloseButton();
    }

    public OnlineChatPanel getChatPanel() {
        return this.chatPanel;
    }


    @Override
    public void u() {
    }
}

