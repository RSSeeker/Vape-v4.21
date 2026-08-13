package gg.vape.ui.click.component.value;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import java.awt.Point;

class BooleanToggleComponentClickMouseListener
implements GuiMouseListener {
    private final BooleanToggleComponent toggleComponent;

    BooleanToggleComponentClickMouseListener(BooleanToggleComponent toggleComponent) {
        this.toggleComponent = toggleComponent;
    }

    @Override
    public void g(Point clickPoint, MouseClickButton clickButton) {
        this.toggleComponent.toggleIfInteractive();
    }
}
