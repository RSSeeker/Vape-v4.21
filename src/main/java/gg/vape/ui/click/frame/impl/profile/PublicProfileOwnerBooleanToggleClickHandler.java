package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileOwnerDetailsPanel;
import java.awt.Point;

class PublicProfileOwnerBooleanToggleClickHandler
implements GuiMouseListener {
    private final BooleanToggleComponent dependentToggle;

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        this.dependentToggle.setVisible(!this.dependentToggle.V$src$Z$1xhop3l());
    }

    PublicProfileOwnerBooleanToggleClickHandler(BooleanToggleComponent dependentToggle) {
        this.dependentToggle = dependentToggle;
    }

}

