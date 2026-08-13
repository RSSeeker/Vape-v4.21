package gg.vape.module.macro;

public interface MacroAction {
    boolean isFinished();

    default void cancel() {
    }

    default void tick() {
    }

    default void inheritState(MacroAction previousAction) {
    }
}
