package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.AnimatedRingIconButtonComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.profile.ProfileSnapshotGuiBuilder;
import gg.vape.value.ValueSnapshot;
import java.awt.Color;

class ProfileSnapshotValueResetButtonComponent
extends AnimatedRingIconButtonComponent {
    private final GuiComponent valueComponent;
    private final ValueSnapshot<?, ?> valueSnapshot;

    ProfileSnapshotValueResetButtonComponent(String icon, Color color, double scale, double width, double height, GuiComponent valueComponent, ValueSnapshot<?, ?> valueSnapshot) {
        super(icon, color, scale, width, height);
        this.valueComponent = valueComponent;
        this.valueSnapshot = valueSnapshot;
    }

    @Override
    public void u() {
        super.u();
        this.setVisible(this.valueComponent.V$src$Z$1xhop3l() && !this.valueSnapshot.isDefault());
    }

}
