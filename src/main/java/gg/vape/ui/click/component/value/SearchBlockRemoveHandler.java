package gg.vape.ui.click.component.value;

import gg.vape.Vape;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.value.SearchBlockListDropdownLayer;
import gg.vape.ui.unmap.SearchBlock;

class SearchBlockRemoveHandler
implements GuiClickListener {
    final SearchBlock searchBlock;
    final SearchBlockListDropdownLayer dropdownLayer;

    @Override
    public void onPrimaryClick() {
        Vape.INSTANCE.getSearch().removeSearchBlock(this.searchBlock);
        this.dropdownLayer.refreshContents();
    }

    SearchBlockRemoveHandler(SearchBlockListDropdownLayer searchBlockListDropdownLayer, SearchBlock searchBlock) {
        this.dropdownLayer = searchBlockListDropdownLayer;
        this.searchBlock = searchBlock;
    }
}
