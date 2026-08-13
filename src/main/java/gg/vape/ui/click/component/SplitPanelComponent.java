package gg.vape.ui.click.component;

import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;

public class SplitPanelComponent
extends PanelComponent {
    private final PanelComponent rightPanel;
    private static final String WIDTH_WRAP_LAYOUT = "widthwrap";
    private final PanelComponent leftPanel;

    @Override
    public void o(double width) {
        super.o(width);
        this.updatePanelWidths();
    }

    private void updatePanelWidths() {
        if (this.leftPanel == null || this.rightPanel == null) {
            return;
        }
        this.leftPanel.o(this.A() / 3.0);
        this.leftPanel.setExplicitWidth(this.A() / 3.0);
        this.rightPanel.setExplicitWidth(this.A() - this.leftPanel.A() - 10.0);
    }

    @Override
    public void Y(double height) {
        super.Y(height);
        this.updatePanelHeights();
    }

    public PanelComponent getRightPanel() {
        return this.rightPanel;
    }

    private void updatePanelHeights() {
        if (this.leftPanel == null || this.rightPanel == null) {
            return;
        }
        this.leftPanel.Y(this.L());
        this.leftPanel.setExplicitHeight(this.L());
        this.rightPanel.Y(this.L());
        this.rightPanel.setExplicitHeight(this.L());
    }


    public SplitPanelComponent(double width, double height, PanelComponent leftPanel, PanelComponent rightPanel) {
        super(width, height);
        this.leftPanel = leftPanel;
        this.rightPanel = rightPanel;
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(WIDTH_WRAP_LAYOUT);
        this.addChildren(this.leftPanel, new SpacerComponent(10.0, 1.0), this.rightPanel);
    }

    public PanelComponent getLeftPanel() {
        return this.leftPanel;
    }

    @Override
    public void H() {
        super.H();
    }
}

