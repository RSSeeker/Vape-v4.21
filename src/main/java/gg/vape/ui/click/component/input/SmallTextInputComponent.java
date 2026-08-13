package gg.vape.ui.click.component.input;

import gg.vape.ui.click.component.TextInputComponentBase;

public class SmallTextInputComponent
extends TextInputComponentBase {
    private static int[] cu;

    public static void T(int[] nArray) {
        cu = nArray;
    }

    @Override
    public void submit() {
    }

    @Override
    public double x() {
        return 110.0;
    }

    static {
        if (SmallTextInputComponent.r$src$AI$f3mb4q() != null) {
            SmallTextInputComponent.T(new int[2]);
        }
    }

    @Override
    public double C() {
        return 20.0;
    }

    @Override
    public float getTextVerticalOffset() {
        return -2.0f;
    }

    public SmallTextInputComponent(String string) {
        super(string);
        this.setBackgroundVisible(false);
        this.getActionButton().setVisible(false);
        this.setPlaceholderColor(SmallTextInputComponent.J.h);
    }

    public static int[] r$src$AI$f3mb4q() {
        return cu;
    }
}
