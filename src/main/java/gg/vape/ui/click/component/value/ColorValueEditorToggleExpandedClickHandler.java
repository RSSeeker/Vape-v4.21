package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.value.ColorValueEditorComponent;

public class ColorValueEditorToggleExpandedClickHandler
implements GuiClickListener {
    final ColorValueEditorComponent editor;

    public ColorValueEditorToggleExpandedClickHandler(ColorValueEditorComponent colorValueEditorComponent) {
        this.editor = colorValueEditorComponent;
    }

    @Override
    public void onPrimaryClick() {
        ColorValueEditorComponent.setCollapsedCompat(this.editor, !ColorValueEditorComponent.isCollapsedCompat(this.editor));
        this.editor.getParentFrameComponent().l$src$V$1mibm4x();
    }

}

