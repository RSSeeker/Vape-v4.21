package gg.vape.ui.click.component.value;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.component.value.SearchBlockEditorComponent;
import gg.vape.ui.unmap.SearchBlock;
import java.awt.Point;

public class SearchBlockEditorEnabledSyncMouseListener
implements GuiMouseListener {
    final SearchBlock searchBlock;
    final SearchBlockEditorComponent editor;

    public SearchBlockEditorEnabledSyncMouseListener(SearchBlockEditorComponent searchBlockEditorComponent, SearchBlock searchBlock) {
        this.editor = searchBlockEditorComponent;
        this.searchBlock = searchBlock;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        this.searchBlock.M(SearchBlockEditorComponent.getEnabledValueCompat(this.editor).getEffectiveValueCompat());
    }
}
