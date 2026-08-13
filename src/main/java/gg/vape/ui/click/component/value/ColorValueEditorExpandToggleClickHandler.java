package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.value.ClientSettingsColorValueEditorComponent;

class ColorValueEditorExpandToggleClickHandler
implements GuiClickListener {
    final ClientSettingsColorValueEditorComponent editor;

    @Override
    public void onPrimaryClick() {
        ClientSettingsColorValueEditorComponent.setCollapsedCompat(this.editor, !ClientSettingsColorValueEditorComponent.isCollapsedCompat(this.editor));
        this.editor.getParentFrameComponent().l$src$V$1mibm4x();
    }

    ColorValueEditorExpandToggleClickHandler(ClientSettingsColorValueEditorComponent clientSettingsColorValueEditorComponent) {
        this.editor = clientSettingsColorValueEditorComponent;
    }

}

