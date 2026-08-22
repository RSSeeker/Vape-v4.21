package gg.vape.mapping;

import gg.vape.event.impl.EventRender2D;
import gg.vape.mapping.InsertedCallbackMarker;

/** 26.x: called after GuiRenderer.render() to draw the ClickGUI. */
public class EventRender2DGuiCallback
extends InsertedCallbackMarker {
    public static void call() {
        EventRender2D.createGui();
    }
}