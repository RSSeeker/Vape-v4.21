package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleSearchKeyHandler;
import gg.vape.ui.click.frame.impl.hud.HudModuleSelectorFrame;

public class HudModuleSearchBox
extends GuiComponent {
    private static final String PLACEHOLDER = "Search mods";
    private final LabeledTextInputComponent input = new LabeledTextInputComponent(PLACEHOLDER);

    @Override
    public void I() {
    }

    @Override
    public double C() {
        return 20.0;
    }

    public LabeledTextInputComponent getInput() {
        return this.input;
    }

    @Override
    public double x() {
        return 132.0;
    }

    @Override
    public void F() {
    }

    @Override
    public void u() {
    }

    @Override
    public void H() {
        this.input.K(this.G$src$D$1b2f02a());
        this.input.S(this.n());
        this.input.o(this.x());
        this.input.Y(this.L());
    }

    public HudModuleSearchBox(HudModuleSelectorFrame hudModuleSelectorFrame) {
        this.input.addKeyTypedListener(new HudModuleSearchKeyHandler(this, hudModuleSelectorFrame));
        this.addChildren(this.input);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }
}
