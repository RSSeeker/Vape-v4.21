package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenSearchInputComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfileFilterTokenSelectorClickHandler;
import gg.vape.value.FixedStringListSuggestionProvider;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PublicProfileFilterTokenSelectorComponent
extends FlowLayoutComponent {
    private final SquareIconButtonComponent clearButton;
    private final Runnable changeCallback;
    private boolean overflowed;
    private final List<PublicProfileFilterTokenComponent> tokens = new ArrayList<>();
    private final TextInputComponentBase input;
    private final PublicProfileFilterTokenComponent overflowSummary;

    public boolean containsToken(String value) {
        for (PublicProfileFilterTokenComponent token : this.tokens) {
            if (!token.getText().equalsIgnoreCase(value)) continue;
            return true;
        }
        return false;
    }

    public TextInputComponentBase getInput() {
        return this.input;
    }

    public void clearTokens() {
        if (!this.input.getText().isEmpty() || !this.tokens.isEmpty()) {
            this.input.setText("");
            this.tokens.clear();
            this.changeCallback.run();
        }
    }

    @Override
    public void H() {
        super.H();
        double d = 0.0;
        for (PublicProfileFilterTokenComponent publicProfileFilterTokenComponent : this.tokens) {
            if (!((d += publicProfileFilterTokenComponent.A() + 2.0) > this.A() / 2.0)) continue;
            this.overflowed = true;
            return;
        }
        this.overflowed = false;
    }

    public void addToken(PublicProfileFilterTokenComponent token) {
        this.tokens.add(token);
        this.changeCallback.run();
    }

    @Override
    public double x() {
        return super.x();
    }

    public List<PublicProfileFilterTokenComponent> getTokens() {
        return this.tokens;
    }

    @Override
    public double C() {
        return super.C();
    }

    PublicProfileFilterTokenComponent getOverflowSummary() {
        return this.overflowSummary;
    }

    public List<String> getTokenValues() {
        ArrayList<String> values = new ArrayList<>();
        for (PublicProfileFilterTokenComponent token : this.tokens) {
            values.add(token.getText());
        }
        return values;
    }

    public void removeToken(PublicProfileFilterTokenComponent token) {
        this.tokens.remove(token);
        this.changeCallback.run();
    }

    boolean isOverflowed() {
        return this.overflowed;
    }

    public PublicProfileFilterTokenSelectorComponent(String string, Runnable runnable, double d, double d2, boolean bl, boolean bl2) {
        super(d);
        this.changeCallback = runnable;
        this.overflowSummary = new PublicProfileFilterTokenComponent("...");
        FixedStringListSuggestionProvider fixedStringListSuggestionProvider = new FixedStringListSuggestionProvider();
        fixedStringListSuggestionProvider.setComparator(null);
        this.input = new PublicProfileFilterTokenSearchInputComponent(this, string, bl, bl2, runnable);
        this.input.setSuggestionProvider(fixedStringListSuggestionProvider);
        this.clearButton = new SquareIconButtonComponent("newclose", 1.0);
        this.clearButton.setVisible(false);
        this.clearButton.o(10.0);
        this.clearButton.Y(10.0);
        PaddedComponent paddedComponent = new PaddedComponent(5.0, 0.0, 1.0, 4.0, this.clearButton);
        paddedComponent.o(15.0);
        paddedComponent.Y(10.0);
        this.input.o(d - 16.0);
        this.input.Y(d2);
        this.input.setShowDisabledOverlay(false);
        this.input.setBackgroundVisible(false);
        this.input.setHorizontalInset(0.0);
        this.input.setLeftInset(0.0f);
        this.input.setVerticalInset(0.0f);
        this.input.setShowDisabledOverlay(false);
        this.input.setBackgroundVisible(false);
        this.input.setDisabledOverlayColor(Color.RED);
        this.input.setBackgroundColorOrNull(Color.BLUE);
        this.setShowDisabledOverlay(false);
        this.input.getActionButton().setVisible(false);
        this.input.setPlaceholderColor(PublicProfileFilterTokenSelectorComponent.J.h);
        this.h(this.input, new Object[0]);
        this.h(paddedComponent, new Object[0]);
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.input.addMouseListener(new PublicProfileFilterTokenSelectorClickHandler(this, atomicBoolean));
    }

    public SquareIconButtonComponent getClearButton() {
        return this.clearButton;
    }

    public void removeLastToken() {
        if (!this.tokens.isEmpty()) {
            this.tokens.remove(this.tokens.size() - 1);
            this.changeCallback.run();
        }
    }

}
