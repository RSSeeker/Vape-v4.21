package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.LabeledTextInputComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenSelectorComponent;
import java.util.stream.Collectors;

public class PublicProfileFilterTokenSearchInputComponent
extends LabeledTextInputComponent {
    private final PublicProfileFilterTokenSelectorComponent selector;
    private final Runnable changeCallback;

    public PublicProfileFilterTokenSearchInputComponent(PublicProfileFilterTokenSelectorComponent publicProfileFilterTokenSelectorComponent, String string, boolean bl, boolean bl2, Runnable runnable) {
        super(string, bl, bl2);
        this.selector = publicProfileFilterTokenSelectorComponent;
        this.changeCallback = runnable;
    }

    @Override
    public void beforeBackspace() {
        if (this.getText().isEmpty()) {
            this.selector.removeLastToken();
        }
    }

    @Override
    protected void renderInputDecorations() {
        if (this.selector.isOverflowed()) {
            PublicProfileFilterTokenComponent overflowSummary = this.selector.getOverflowSummary();
            overflowSummary.w(this.selector.getTokens().stream().map(PublicProfileFilterTokenComponent::getText).collect(Collectors.joining(", ")));
            overflowSummary.S(this.selector.getTokens().size() + " tags");
            overflowSummary.K(this.G$src$D$1b2f02a() + (double)super.getRightInset());
            overflowSummary.S(this.n() + this.L() / 2.0 - overflowSummary.L() / 2.0);
            overflowSummary.c();
            if (overflowSummary.t()) {
                overflowSummary.J();
            }
            return;
        }
        double d = 0.0;
        double d2 = this.n() + this.L() / 2.0;
        for (GuiComponent guiComponent : this.selector.getTokens()) {
            guiComponent.K(this.G$src$D$1b2f02a() + (double)super.getRightInset() + d);
            guiComponent.S(d2 - guiComponent.L() / 2.0);
            if (guiComponent.t()) {
                guiComponent.J();
            }
            guiComponent.c();
            d += guiComponent.A() + 2.0;
        }
    }


    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        boolean bl = guiMouseEvent.getAction() == MouseButton.RIGHT_CLICK;
        String string = this.selector.getInput().getText();
        super.g(guiMouseEvent);
        this.selector.g(guiMouseEvent);
        if (bl && !this.selector.getInput().getText().equals(string)) {
            this.changeCallback.run();
        }
    }

    @Override
    public float getRightInset() {
        if (this.selector.isOverflowed()) {
            return (float)((double)super.getRightInset() + (this.selector.getOverflowSummary().A() + 4.0));
        }
        float f = 0.0f;
        for (GuiComponent guiComponent : this.selector.getTokens()) {
            f += (float)guiComponent.A() + 2.0f;
        }
        return super.getRightInset() + (f + 2.0f);
    }
}
