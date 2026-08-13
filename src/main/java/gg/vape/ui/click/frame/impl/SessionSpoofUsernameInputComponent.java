package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.frame.impl.SessionSpoofFrame;

class SessionSpoofUsernameInputComponent
extends TextInputComponentBase {
    final SessionSpoofFrame rf;

    @Override
    public double C() {
        return 20.0;
    }

    SessionSpoofUsernameInputComponent(SessionSpoofFrame sessionSpoofFrame, String string) {
        super(string);
        this.rf = sessionSpoofFrame;
    }

    @Override
    public void submit() {
        SessionSpoofFrame.W(this.rf);
        this.setText("");
    }

    @Override
    public double x() {
        return 150.0;
    }
}
