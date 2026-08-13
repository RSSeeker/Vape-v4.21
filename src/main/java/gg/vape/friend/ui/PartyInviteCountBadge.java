package gg.vape.friend.ui;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.friend.ui.PartyInviteCountBadgeToggleInvitesClickHandler;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;

public class PartyInviteCountBadge
extends PanelComponent {
    private int inviteCount;
    private final PanelComponent titlePanel;
    private double contentHeight;
    private final TextLabel viewAllLabel = new TextLabel("view all", 0.8, false, 20.0, 12.0);
    private final TextButton countButton;
    private final String title = "Party Invites";
    private final PanelComponent actionPanel;

    @Override
    public void c() {
        super.c();
    }

    public PartyInviteCountBadge() {
        super(100.0, 12.0);
        this.countButton = new TextButton("", 0.8, PartyInviteCountBadge.J.d, PartyInviteCountBadge.J.c, 8.5, 8.5);
        this.contentHeight = 0.0;
        this.titlePanel = new PanelComponent(80.0, 12.0);
        this.actionPanel = new PanelComponent(26.0, 12.0);
        this.inviteCount = 0;
        this.setShowDisabledOverlay(false);
        this.titlePanel.setShowDisabledOverlay(false);
        this.actionPanel.setShowDisabledOverlay(false);
        this.actionPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().Q(true);
        this.viewAllLabel.addClickListener(new PartyInviteCountBadgeToggleInvitesClickHandler(this));
        this.actionPanel.addChildren(this.viewAllLabel);
        this.addChildren(this.titlePanel, this.actionPanel);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void u() {
        this.inviteCount = Vape.INSTANCE.getOnlineManager().getPartyManager().getInvites().size();
        this.setVisible(this.inviteCount > 1);
    }

    @Override
    public double x() {
        return 100.0;
    }

    public TextLabel getViewAllLabel() {
        return this.viewAllLabel;
    }


    @Override
    public void H() {
        this.countButton.setVisible(false);
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.7);
        smoothFontRenderer.d(this.title, this.G$src$D$1b2f02a(), this.n() + (this.L() - smoothFontRenderer.d(this.title)) / 2.0, PartyInviteCountBadge.J.Z);
        int badgeWidth = 7;
        if (this.inviteCount > 99) {
            badgeWidth = 10;
        }
        RectData rectData = new RectData(this.G$src$D$1b2f02a() + smoothFontRenderer.N(this.title) + 3.0, this.n() + 2.0, badgeWidth, 7.0);
        GuiRenderPrimitives.e(rectData.o(), rectData.W(), rectData.e(), rectData.R(), PartyInviteCountBadge.J.d, false, 1.0f, 1.0f);
        String string = String.valueOf(this.inviteCount);
        smoothFontRenderer.d(string, rectData.o() + rectData.e() / 2.0 - smoothFontRenderer.N(string) / 2.0, this.n() + 2.5, PartyInviteCountBadge.J.A);
    }

    @Override
    public void F() {
    }

    @Override
    public void I() {
    }

    @Override
    public double C() {
        return this.contentHeight;
    }
}

