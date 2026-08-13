package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.ui.TextSuggestionInputComponent;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.LabeledTextInputComponent;

class TextSuggestionDecoratedInput
extends LabeledTextInputComponent {
    final TextSuggestionInputComponent owner;

    @Override
    public void beforeBackspace() {
        if (this.getText().isEmpty()) {
            this.owner.removeLastRow();
        }
    }

    @Override
    public float getRightInset() {
        float rowsWidth = 0.0f;
        for (GuiComponent guiComponent : TextSuggestionInputComponent.mutableRows(this.owner)) {
            rowsWidth += (float)guiComponent.A() + 2.0f;
        }
        return super.getRightInset() + (rowsWidth + 2.0f);
    }


    TextSuggestionDecoratedInput(TextSuggestionInputComponent textSuggestionInputComponent, String placeholder, boolean multiline, boolean editable) {
        super(placeholder, multiline, editable);
        this.owner = textSuggestionInputComponent;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        super.g(guiMouseEvent);
        this.owner.g(guiMouseEvent);
    }

    @Override
    protected void renderInputDecorations() {
        double rowOffset = 0.0;
        double centerY = this.n() + this.L() / 2.0;
        for (GuiComponent guiComponent : TextSuggestionInputComponent.mutableRows(this.owner)) {
            guiComponent.K(this.G$src$D$1b2f02a() + (double)super.getRightInset() + rowOffset);
            guiComponent.S(centerY - guiComponent.L() / 2.0);
            if (guiComponent.t()) {
                guiComponent.J();
            }
            guiComponent.c();
            rowOffset += guiComponent.A() + 2.0;
        }
    }
}
