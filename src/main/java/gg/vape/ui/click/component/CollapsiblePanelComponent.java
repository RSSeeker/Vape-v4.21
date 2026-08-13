package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.gui.WrappedTextComponent;
import java.awt.Color;
import java.util.List;

public class CollapsiblePanelComponent
extends PanelComponent {
    private TextLabel toggleLabel;
    private FlowLayoutComponent contentLayout;
    private String text;
    private PanelComponent togglePanel;
    private WrappedTextComponent expandedText;
    private boolean collapsed;
    private TruncatedTextComponent collapsedText;

    @Override
    public double L() {
        return this.collapsed ? this.getFontRenderer(0.8).d(this.text) : this.expandedText.L() + 2.0 + this.contentLayout.L();
    }

    private void toggleCollapsed() {
        this.collapsed = !this.collapsed;
        this.toggleLabel.setLabelText(this.collapsed ? "...more" : "...less");
    }

    public CollapsiblePanelComponent(String text, double width) {
        super(width, 0.0);
        this.collapsedText = new TruncatedTextComponent("", "", this.A() - 10.0, 0.8f, CollapsiblePanelComponent.J.A, false);
        this.collapsed = true;
        this.togglePanel = new PanelComponent(this.A(), 0.0);
        this.text = text;
        this.toggleLabel = new TextLabel("...more", 1.0);
        this.expandedText = new WrappedTextComponent(text, 0.8, CollapsiblePanelComponent.J.Z, false);
        this.expandedText.setWrapWidth(width);
        this.contentLayout = new FlowLayoutComponent(width);
        this.setShowDisabledOverlay(false);
        this.togglePanel.setShowDisabledOverlay(false);
        this.contentLayout.setShowDisabledOverlay(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.togglePanel.h(this.toggleLabel, new Object[0]);
        this.addChildren(this.togglePanel, this.contentLayout);
        this.toggleLabel.setFontScale(0.8);
        this.toggleLabel.setUppercase(false);
        this.toggleLabel.setTextColor(Color.WHITE);
        this.toggleLabel.o(30.0);
        this.collapsedText.setTextColor(CollapsiblePanelComponent.J.Z);
        if (text.isEmpty()) {
            this.collapsed = false;
            this.toggleLabel.setVisible(false);
        }
        this.toggleLabel.addClickListener(this::toggleCollapsed);
    }

    public FlowLayoutComponent getContentLayout() {
        return this.contentLayout;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }


    @Override
    public void dispatchMouseEvent(GuiMouseEvent mouseEvent) {
        super.dispatchMouseEvent(mouseEvent);
    }

    @Override
    public void c() {
        this.collapsedText.K(this.G$src$D$1b2f02a());
        this.collapsedText.S(this.n());
        this.contentLayout.setVisible(!this.collapsed);
        this.expandedText.setText(this.text);
        this.expandedText.setWrapWidth(this.A() - 20.0);
        this.expandedText.o(this.A() - 20.0);
        List<String> wrappedLines = this.expandedText.getWrappedLines();
        String firstLine = wrappedLines.get(0);
        this.collapsedText.setText(firstLine);
        this.collapsedText.setMaxWidth(this.A() - 20.0);
        this.collapsedText.o(wrappedLines.size() > 1 ? this.expandedText.getFontRenderer(0.8).N(firstLine) : this.expandedText.A() - 20.0);
        this.toggleLabel.setUseAlternateFont(true);
        if (this.collapsed) {
            this.toggleLabel.K(this.G$src$D$1b2f02a() + this.collapsedText.A() + 2.0);
            this.toggleLabel.S(this.n());
            this.togglePanel.setExplicitHeight(this.collapsedText.L());
        } else {
            String lastLine = wrappedLines.get(wrappedLines.size() - 1);
            double lastLineWidth = this.expandedText.getFontRenderer(0.8).N(lastLine);
            this.toggleLabel.K(this.G$src$D$1b2f02a() + lastLineWidth + 3.0);
            this.toggleLabel.S(this.n() + this.expandedText.L() - this.collapsedText.L());
            this.togglePanel.setExplicitHeight(this.expandedText.L());
        }
        this.toggleLabel.Y(this.collapsedText.L());
        super.c();
        if (this.collapsed) {
            this.collapsedText.c();
        } else {
            this.expandedText.K(this.G$src$D$1b2f02a());
            this.expandedText.S(this.n());
            this.expandedText.c();
        }
    }
}

