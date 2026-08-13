package gg.vape.ui.click.component.gui;

import gg.vape.Vape;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.component.ClickCooldownState;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.GuiComponent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public abstract class InteractiveComponent
extends GuiComponent {
    private static boolean legacyState;
    private Color hoverColor;
    private ClickCooldownState clickCooldown = new ClickCooldownState();
    private Color normalColor;
    private boolean interactionDisabled = false;
    private List<GuiClickListener> clickListeners = new ArrayList<GuiClickListener>();

    public static boolean isLegacyStateEnabled() {
        return legacyState;
    }

    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
    }

    public void dispatchSecondaryClick() {
        for (GuiClickListener clickListener : new ArrayList<GuiClickListener>(this.clickListeners)) {
            clickListener.onSecondaryClick();
        }
    }

    public void clearClickListeners() {
        this.clickListeners.clear();
    }

    public Color getNormalColor() {
        return this.normalColor;
    }

    public InteractiveComponent setSingleFutureClickListener(Supplier<@Nullable CompletableFuture<?>> futureSupplier) {
        AtomicBoolean ready = new AtomicBoolean(true);
        this.setClickListener(() -> InteractiveComponent.runSingleFutureClick(ready, futureSupplier));
        return this;
    }

    public List<GuiClickListener> getClickListeners() {
        return this.clickListeners;
    }

    public void removeClickListener(GuiClickListener clickListener) {
        this.clickListeners.remove(clickListener);
    }

    public void dispatchPrimaryClick() {
        for (GuiClickListener clickListener : new ArrayList<GuiClickListener>(this.clickListeners)) {
            clickListener.onPrimaryClick();
        }
    }

    private static void runSingleFutureClick(AtomicBoolean ready, Supplier<@Nullable CompletableFuture<?>> futureSupplier) {
        if (!ready.get()) {
            return;
        }
        CompletableFuture<?> future = futureSupplier.get();
        if (future == null) {
            return;
        }
        ready.set(false);
        future.whenCompleteAsync((result, throwable) -> InteractiveComponent.handleFutureCompletion(ready, result, throwable));
    }

    public Color getHoverColor() {
        return this.hoverColor;
    }

    public InteractiveComponent addClickListener(GuiClickListener clickListener) {
        this.clickListeners.add(clickListener);
        return this;
    }

    private static void handleFutureCompletion(AtomicBoolean ready, Object result, Throwable throwable) {
        ready.set(true);
        if (throwable != null) {
            Vape.logThrowable(throwable);
        }
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public static void setLegacyStateEnabled(boolean enabled) {
        legacyState = enabled;
    }

    public ClickCooldownState getClickCooldown() {
        return this.clickCooldown;
    }


    public static boolean isLegacyStateDisabled() {
        return !InteractiveComponent.isLegacyStateEnabled();
    }

    public void setInteractionDisabled(boolean interactionDisabled) {
        this.interactionDisabled = interactionDisabled;
    }

    static {
        if (InteractiveComponent.isLegacyStateDisabled()) {
            InteractiveComponent.setLegacyStateEnabled(true);
        }
    }

    @Override
    public boolean w$src$Z$e457mb() {
        if (this.interactionDisabled) {
            return false;
        }
        return super.w$src$Z$e457mb();
    }

    public void setClickListener(@Nullable GuiClickListener clickListener) {
        this.clickListeners = new ArrayList<GuiClickListener>();
        if (clickListener != null) {
            this.clickListeners.add(clickListener);
        }
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
        if (this.clickCooldown.isCoolingDown()) {
            return;
        }
        if (this.interactionDisabled) {
            return;
        }
        if (this.V$src$Z$1xhop3l()) {
            if (mouseEvent.getAction().equals((Object)MouseButton.LEFT_CLICK)) {
                this.dispatchPrimaryClick();
            }
            if (mouseEvent.getAction().equals((Object)MouseButton.RIGHT_CLICK)) {
                this.dispatchSecondaryClick();
            }
        }
        this.clickCooldown.setActive(true);
    }
}
