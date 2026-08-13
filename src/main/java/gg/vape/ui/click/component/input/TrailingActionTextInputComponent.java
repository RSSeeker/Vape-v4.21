package gg.vape.ui.click.component.input;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.TextInputComponentBase;
import java.util.ArrayList;
import java.util.List;

public class TrailingActionTextInputComponent
extends TextInputComponentBase {
    private final List<GuiComponent> trailingComponents;
    private String pendingText = null;
    private boolean pendingTextApplied = false;

    public void P(String string) {
        this.pendingText = string;
        this.pendingTextApplied = false;
    }

    @Override
    public double getAvailableTextWidth() {
        double d = 0.0;
        for (GuiComponent guiComponent : this.trailingComponents) {
            d += guiComponent.A() + 4.0;
        }
        return this.A() - d - 12.0 - (double)this.getLeftInset() - (double)this.getRightInset();
    }

    private void applyPendingText() {
        if (this.pendingText != null && !this.pendingTextApplied) {
            this.setText(this.pendingText);
            this.cursorPosition = this.getText() != null ? this.getText().length() : 0;
            this.setText(this.getText());
            this.pendingTextApplied = true;
            this.pendingText = null;
        }
    }

    public TrailingActionTextInputComponent(String string, List<GuiComponent> arrayList) {
        super(string);
        this.trailingComponents = arrayList != null ? arrayList : new ArrayList();
        this.setVerticalInset(0.0f);
        this.setUseExplicitHeight(true);
        this.Y(16.0);
        this.setActionButtonVisible(false);
        for (GuiComponent guiComponent : this.trailingComponents) {
            this.addChildren(guiComponent);
        }
        this.getClass();
        this.Y(18 + 5);
    }

    @Override
    public void c() {
        this.applyPendingText();
        super.c();
        double d = this.getComponentWidth();
        double d2 = 0.0;
        for (GuiComponent guiComponent : this.trailingComponents) {
            d2 += guiComponent.A() + 2.0;
        }
        double d3 = this.G$src$D$1b2f02a() + d - d2 - (double)this.getRightInset();
        double d4 = this.n() + this.L() / 2.0;
        double d5 = d3;
        for (GuiComponent guiComponent : this.trailingComponents) {
            guiComponent.K(d5);
            guiComponent.S(d4 - guiComponent.L() / 2.0);
            d5 += guiComponent.A() + 2.0;
        }
    }


    @Override
    public void submit() {
        if (!this.hasNonBlankText()) {
            return;
        }
    }
}
