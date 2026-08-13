package gg.vape.ui.click;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.ui.click.MouseButton;

public class GuiMouseEvent
extends Event {
    private MouseButton A;
    private static final String d;
    private int V;
    private int c;
    private static String[] M;
    private static final EventListeners b;
    private boolean h;

    public GuiMouseEvent(int n, int n2, MouseButton mouseButton) {
        this.V = n;
        this.c = n2;
        this.A = mouseButton;
    }

    static {
        GuiMouseEvent.D(new String[3]);
        d = "[%s, %s]: %s";
        b = new EventListeners();
    }

    public int getX() {
        return this.V;
    }

    public static void D(String[] stringArray) {
        M = stringArray;
    }

    public static EventListeners getEventListeners() {
        return b;
    }

    public MouseButton getAction() {
        return this.A;
    }

    @Override
    public void setCancelled(boolean bl) {
        this.h = bl;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    public static String[] d() {
        return M;
    }

    public boolean isCancelled() {
        return this.h;
    }

    public String toString() {
        return String.format(d, new Object[]{this.getX(), this.getY(), this.getAction()});
    }

    @Override
    public EventListeners getListeners() {
        return b;
    }

    public int getY() {
        return this.c;
    }
}

