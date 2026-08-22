package gg.vape.mapping;

import gg.vape.event.impl.EventRender2D;
import gg.vape.mapping.InsertedCallbackMarker;

/** 26.x: called before GuiRenderer.render() to draw the HUD modules. */
public class EventRender2DHudCallback
extends InsertedCallbackMarker {
    public static void call() {
        EventRender2D.createHud();
    }
}