package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlaySpec;
import gg.vape.ui.click.frame.impl.main.ClickGuiOverlayTransitionMode;
import gg.vape.ui.click.frame.impl.online.OnlineAccountLinkCodePageComponent;

public final class ClickGuiOverlaySpecFactoryB {
    private ClickGuiOverlaySpecFactoryB() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static ClickGuiOverlaySpec F() {
        return ClickGuiOverlaySpec.builder().title("Register").sidecarIcon("register").initializeContent(ClickGuiOverlaySpecFactoryB::x).transitionMode(ClickGuiOverlayTransitionMode.REPLACE).build();
    }

    private static void x(PanelComponent panelComponent) {
        OnlineAccountLinkCodePageComponent onlineAccountLinkCodePageComponent = new OnlineAccountLinkCodePageComponent();
        for (GuiComponent guiComponent : onlineAccountLinkCodePageComponent.f()) {
            panelComponent.h(guiComponent, new Object[0]);
        }
    }
}

