package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.none.ClientSettings;
import gg.vape.module.utility.inventory.cleaner.ui.TextSuggestionInputComponent;
import gg.vape.module.utility.inventory.cleaner.ui.TextSuggestionRow;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

class TextSuggestionClickListener
implements GuiMouseListener {
    final TextSuggestionInputComponent input;
    final AtomicBoolean handlingClick;
    final Consumer<TextSuggestionRow> onRemove;

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        for (TextSuggestionRow textSuggestionRow : TextSuggestionInputComponent.mutableRows(this.input)) {
            if (!textSuggestionRow.w$src$Z$e457mb()) continue;
            if (this.handlingClick.get()) {
                return;
            }
            this.handlingClick.set(true);
            ClientSettings.UI_EXECUTOR.execute(() -> this.handleRowClick(textSuggestionRow, this.onRemove, this.handlingClick));
            return;
        }
    }

    TextSuggestionClickListener(TextSuggestionInputComponent textSuggestionInputComponent, AtomicBoolean atomicBoolean, Consumer<TextSuggestionRow> consumer) {
        this.input = textSuggestionInputComponent;
        this.handlingClick = atomicBoolean;
        this.onRemove = consumer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void handleRowClick(TextSuggestionRow textSuggestionRow, Consumer<TextSuggestionRow> consumer, AtomicBoolean atomicBoolean) {
        try {
            this.input.removeRow(textSuggestionRow);
            consumer.accept(textSuggestionRow);
        }
        finally {
            atomicBoolean.set(false);
        }
    }

}

