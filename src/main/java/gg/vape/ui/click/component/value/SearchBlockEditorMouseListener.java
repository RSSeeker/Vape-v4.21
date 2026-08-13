package gg.vape.ui.click.component.value;

import gg.vape.Vape;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.component.value.SearchBlockEditorComponent;
import gg.vape.ui.unmap.SearchBlock;
import java.awt.Point;

public class SearchBlockEditorMouseListener
implements GuiMouseListener {
    final SearchBlockEditorComponent editor;
    final SearchBlock searchBlock;
    private static final String DEBUG_MESSAGE = "Clicked tracers";

    public SearchBlockEditorMouseListener(SearchBlockEditorComponent searchBlockEditorComponent, SearchBlock searchBlock) {
        this.editor = searchBlockEditorComponent;
        this.searchBlock = searchBlock;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        this.searchBlock.H(SearchBlockEditorComponent.getTracersValueCompat(this.editor).getEffectiveValue());
        Vape.debugLog(DEBUG_MESSAGE);
    }
}
