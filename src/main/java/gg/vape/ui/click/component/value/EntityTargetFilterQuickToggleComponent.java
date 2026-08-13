package gg.vape.ui.click.component.value;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.AnimatedIconButtonComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.value.BooleanValue;
import gg.vape.value.EntityTargetFilterValue;

public class EntityTargetFilterQuickToggleComponent
extends GuiComponent {
    private AnimatedIconButtonComponent playersButton = new AnimatedIconButtonComponent("newplayers");
    private EntityTargetFilterValue targetFilterValue;
    private AnimatedIconButtonComponent peacefulButton;
    private AnimatedIconButtonComponent mobsButton = new AnimatedIconButtonComponent("newmobs");

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public void I() {
    }

    @Override
    public void F() {
    }

    @Override
    public double C() {
        return 20.0;
    }


    @Override
    public void H() {
        double buttonWidth = (this.A() - 10.0 - 6.0) / 3.0;
        double buttonX = this.G$src$D$1b2f02a() + 5.0;
        this.layoutToggleButton(this.playersButton, this.targetFilterValue.getPlayersValue(), buttonWidth, buttonX);
        this.layoutToggleButton(this.mobsButton, this.targetFilterValue.getMobsValue(), buttonWidth, buttonX += buttonWidth + 2.0);
        this.layoutToggleButton(this.peacefulButton, this.targetFilterValue.getPeacefulValue(), buttonWidth, buttonX += buttonWidth + 2.0);
    }

    @Override
    public void u() {
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
    }

    private void layoutToggleButton(AnimatedIconButtonComponent toggleButton, BooleanValue targetEnabledValue, double width, double x) {
        toggleButton.K(x);
        toggleButton.S(this.n() + 2.5);
        toggleButton.o(width);
        toggleButton.Y(this.L() - 5.0);
        toggleButton.setSelected(targetEnabledValue.getEffectiveValue());
        toggleButton.setOverrideColor(targetEnabledValue.getEffectiveValue() != false || toggleButton.w$src$Z$e457mb() ? J.B() : null);
        if (!toggleButton.getBackgroundAnimation().N() && !toggleButton.getBackgroundAnimation().l() && targetEnabledValue.getEffectiveValue().booleanValue()) {
            toggleButton.getBackgroundAnimation().J();
        }
    }

    public EntityTargetFilterQuickToggleComponent(EntityTargetFilterValue targetFilterValue) {
        this.peacefulButton = new AnimatedIconButtonComponent("newpeaceful");
        this.targetFilterValue = targetFilterValue;
        this.playersButton.addClickListener(() -> targetFilterValue.getPlayersValue().toggleIfValid());
        this.playersButton.setDimOnHover(true);
        this.playersButton.w("Target players");
        this.mobsButton.addClickListener(() -> targetFilterValue.getMobsValue().toggleIfValid());
        this.mobsButton.setDimOnHover(true);
        this.mobsButton.w("Target mobs");
        this.peacefulButton.addClickListener(() -> targetFilterValue.getPeacefulValue().toggleIfValid());
        this.peacefulButton.setDimOnHover(true);
        this.peacefulButton.w("Target peaceful");
        this.addChildren(this.playersButton, this.mobsButton, this.peacefulButton);
    }

}
