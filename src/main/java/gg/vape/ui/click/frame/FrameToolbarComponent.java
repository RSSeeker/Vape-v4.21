package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.FrameToolbarEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FrameToolbarComponent
extends FrameHeaderComponent {
    private static String legacyState;
    private final PanelComponent contentPanel;
    private final SimpleTextLabelComponent titleLabel;
    private boolean defaultCloseActionVisible = false;
    private final String defaultIconResource;
    private boolean showingBackNavigation = false;
    private float defaultIconScale = 1.0f;
    private final SquareIconButtonComponent closeButton = new SquareIconButtonComponent("newclose", 1.5);
    private final PaddedComponent paddedContent;
    private final FlowLayoutComponent actionsLayout;
    private final List<FrameToolbarEntry> entries = new ArrayList<FrameToolbarEntry>();
    private final IconButtonComponent navigationButton;
    private final String defaultTitle;
    private final FlowLayoutComponent closeActionLayout;

    public void addEntry(FrameToolbarEntry entry) {
        this.entries.add(entry);
        Collections.reverse(this.entries);
        FrameToolbarEntry[] reversedEntries = new FrameToolbarEntry[this.entries.size()];
        reversedEntries = this.entries.toArray(reversedEntries);
        Collections.reverse(this.entries);
        this.setEntries(reversedEntries);
    }

    public void setEntries(FrameToolbarEntry ... entries) {
        this.actionsLayout.t$src$V$zbu1jn();
        for (FrameToolbarEntry entry : entries) {
            this.actionsLayout.h(entry.getLayout(), new Object[0]);
        }
        this.actionsLayout.h(this.closeActionLayout, new Object[0]);
    }

    @Override
    public boolean D(int n, int n2) {
        if (this.showingBackNavigation && (this.navigationButton.getBounds().J(n, n2) || this.actionsLayout.getBounds().J(n, n2))) {
            return false;
        }
        return this.getBounds().J(n, n2);
    }

    public FlowLayoutComponent getCloseActionLayout() {
        return this.closeActionLayout;
    }

    public static String getLegacyState() {
        return legacyState;
    }

    public void showSettingsNavigation(boolean hideAllActions) {
        this.showBackNavigation(this.getDefaultTitle() + " settings", hideAllActions);
    }


    public IconButtonComponent getNavigationButton() {
        return this.navigationButton;
    }

    public boolean isShowingBackNavigation() {
        return this.showingBackNavigation;
    }

    public void setDefaultCloseActionVisible(boolean defaultCloseActionVisible) {
        this.defaultCloseActionVisible = defaultCloseActionVisible;
        this.getCloseActionLayout().setVisible(true);
    }

    public FrameToolbarComponent(Frame frame, String defaultIconResource, String defaultTitle) {
        super(frame);
        this.defaultIconResource = defaultIconResource;
        this.defaultTitle = defaultTitle;
        this.contentPanel = new PanelComponent(this.x() - 8.0, this.C() - 8.0);
        this.paddedContent = new PaddedComponent(4.0, 4.0, this.contentPanel);
        FlowLayoutComponent titleLayout = new FlowLayoutComponent(90.0);
        this.navigationButton = new IconButtonComponent(defaultIconResource);
        titleLayout.h(this.navigationButton, new Object[0]);
        this.titleLabel = new SimpleTextLabelComponent(defaultTitle, 0.9, FrameToolbarComponent.J.A);
        this.titleLabel.setOffsetX(0.0f);
        this.titleLabel.setOffsetY(1.0f);
        titleLayout.h(new SpacerComponent(1.0, 1.0), new Object[0]);
        titleLayout.h(this.titleLabel, new Object[0]);
        this.actionsLayout = new FlowLayoutComponent(60.0);
        this.contentPanel.h(titleLayout, new Object[0]);
        this.contentPanel.h(this.actionsLayout, "alignright");
        this.closeActionLayout = new FlowLayoutComponent(10.0);
        this.closeActionLayout.addChildren(new SpacerComponent(10.0, 2.0), this.closeButton);
        this.closeActionLayout.setVisible(false);
        this.addChildren(this.paddedContent);
        this.navigationButton.setOverrideColor(FrameToolbarComponent.J.f);
        titleLayout.setShowDisabledOverlay(false);
        this.actionsLayout.setShowDisabledOverlay(false);
        this.contentPanel.setShowDisabledOverlay(false);
        this.paddedContent.setShowDisabledOverlay(false);
        this.closeButton.setImageDivisor(3.5);
        this.closeButton.setIconScale(1.2);
        this.setEntries(new FrameToolbarEntry[0]);
    }

    public void addAction(GuiComponent component) {
        this.addEntry(new FrameToolbarEntry(component));
    }

    public void setDefaultIconScale(float defaultIconScale) {
        this.defaultIconScale = defaultIconScale;
        if (!this.showingBackNavigation) {
            this.navigationButton.setIconScale(defaultIconScale);
        }
    }

    public void showBackNavigation(String title, boolean hideAllActions) {
        this.navigationButton.setIconResource("moduleback");
        this.navigationButton.setIconScale(0.85f);
        this.titleLabel.setText(title);
        if (hideAllActions) {
            this.closeActionLayout.setVisible(true);
        }
        for (FrameToolbarEntry entry : this.entries) {
            if (!entry.isHiddenInBackMode() && !hideAllActions) continue;
            entry.getLayout().setVisible(false);
        }
        this.showingBackNavigation = true;
    }

    public void updateLayout() {
        this.paddedContent.H(true);
        this.paddedContent.K(this.G$src$D$1b2f02a());
        this.paddedContent.S(this.n());
    }

    public static void setLegacyState(String state) {
        legacyState = state;
    }

    public String getDefaultTitle() {
        return this.defaultTitle;
    }

    public void setToolbarWidth(double width) {
        this.o(width);
        this.setExplicitWidth(width);
        this.contentPanel.setExplicitWidth(width - 8.0);
    }

    static {
        FrameToolbarComponent.setLegacyState("Mcksjb");
    }

    public void restoreDefaultNavigation() {
        this.navigationButton.setIconResource(this.defaultIconResource);
        this.navigationButton.setIconScale(this.defaultIconScale);
        this.titleLabel.setText(this.defaultTitle);
        this.closeActionLayout.setVisible(this.defaultCloseActionVisible);
        for (FrameToolbarEntry entry : this.entries) {
            entry.getLayout().setVisible(true);
        }
        this.showingBackNavigation = false;
    }

    public SquareIconButtonComponent getCloseButton() {
        return this.closeButton;
    }
}
