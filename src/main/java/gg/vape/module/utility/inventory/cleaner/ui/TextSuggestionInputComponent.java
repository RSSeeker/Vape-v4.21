package gg.vape.module.utility.inventory.cleaner.ui;

import gg.vape.module.utility.inventory.cleaner.ui.TextSuggestionClickListener;
import gg.vape.module.utility.inventory.cleaner.ui.TextSuggestionDecoratedInput;
import gg.vape.module.utility.inventory.cleaner.ui.TextSuggestionRow;
import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.value.FixedStringListSuggestionProvider;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class TextSuggestionInputComponent
extends FlowLayoutComponent {
    private static final String CLOSE_ICON = "newclose";
    private SquareIconButtonComponent removeButton;
    private final LabeledTextInputComponent input;
    private final List<TextSuggestionRow> rows = new ArrayList<TextSuggestionRow>();
    private final Consumer<TextSuggestionRow> onRemove;

    public TextSuggestionInputComponent(String placeholder, Consumer<TextSuggestionRow> onRemove, double width, double height, boolean multiline, boolean editable) {
        super(width);
        this.onRemove = onRemove;
        FixedStringListSuggestionProvider fixedStringListSuggestionProvider = new FixedStringListSuggestionProvider();
        fixedStringListSuggestionProvider.setComparator(null);
        this.input = new TextSuggestionDecoratedInput(this, placeholder, multiline, editable);
        this.input.setSuggestionProvider(fixedStringListSuggestionProvider);
        this.input.getSearchIcon().setVisible(false);
        this.removeButton = new SquareIconButtonComponent(CLOSE_ICON, 1.0);
        this.removeButton.setVisible(false);
        this.removeButton.o(10.0);
        this.removeButton.Y(10.0);
        this.input.o(width);
        this.input.Y(height);
        this.input.setShowDisabledOverlay(false);
        this.input.setBackgroundVisible(false);
        this.input.setHorizontalInset(0.0);
        this.input.setLeftInset(0.0f);
        this.input.setVerticalInset(0.0f);
        this.input.setShowDisabledOverlay(false);
        this.input.setBackgroundVisible(false);
        this.input.setDisabledOverlayColor(Color.RED);
        this.input.setBackgroundColorOrNull(Color.BLUE);
        this.setShowDisabledOverlay(false);
        this.input.getActionButton().setVisible(false);
        this.input.setPlaceholderColor(TextSuggestionInputComponent.J.h);
        this.h(this.input, new Object[0]);
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.input.addMouseListener(new TextSuggestionClickListener(this, atomicBoolean, onRemove));
    }

    public TextInputComponentBase getInput() {
        return this.input;
    }

    @Override
    public double x() {
        return super.x();
    }

    public List<TextSuggestionRow> getRows() {
        return this.rows;
    }

    public void removeLastRow() {
        TextSuggestionRow textSuggestionRow;
        if (!this.rows.isEmpty() && (textSuggestionRow = this.rows.remove(this.rows.size() - 1)) != null) {
            this.onRemove.accept(textSuggestionRow);
        }
    }

    static List<TextSuggestionRow> mutableRows(TextSuggestionInputComponent component) {
        return component.rows;
    }

    @Override
    public double C() {
        return super.C();
    }

    public void clear() {
        if (!this.input.getText().isEmpty() || !this.rows.isEmpty()) {
            this.input.setText("");
            this.rows.clear();
        }
    }

    public boolean contains(String text) {
        for (TextSuggestionRow textSuggestionRow : this.rows) {
            if (!textSuggestionRow.getText().equalsIgnoreCase(text)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void H() {
        super.H();
    }

    public SquareIconButtonComponent getRemoveButton() {
        return this.removeButton;
    }

    public List<String> getValues() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (TextSuggestionRow textSuggestionRow : this.rows) {
            arrayList.add(textSuggestionRow.getText());
        }
        return arrayList;
    }

    public void addRow(TextSuggestionRow row) {
        this.rows.add(row);
    }

    public void removeRow(TextSuggestionRow row) {
        this.rows.remove(row);
    }

}
