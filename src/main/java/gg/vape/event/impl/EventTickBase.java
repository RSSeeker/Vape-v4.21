package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.input.KeyboardInput;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.ThreadBoundExecutor;
import gg.vape.utils.render.shader.ShaderProgram;
import gg.vape.wrapper.impl.Minecraft;

public class EventTickBase
extends Event {
    private static final EventListeners EVENT_LISTENERS;
    private static GuiComponent[] obfuscationState;
    public static final ThreadBoundExecutor POST_TICK_EXECUTOR;
    public static final ThreadBoundExecutor PRE_TICK_EXECUTOR;

    public static void setTickObfuscationState(GuiComponent[] state) {
        obfuscationState = state;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    static {
        PRE_TICK_EXECUTOR = new ThreadBoundExecutor();
        POST_TICK_EXECUTOR = new ThreadBoundExecutor();
        EventTickBase.setTickObfuscationState(null);
        EVENT_LISTENERS = new EventListeners();
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    @Override
    public boolean fire() {
        try {
            if (Vape.INSTANCE.isTickActionPending()) {
                gg.vape.module.none.ClientSettings.INSTANCE.openGui();
                Vape.INSTANCE.setPendingTickAction(false);
            }
            if (Minecraft.theWorld().isNotNull()) {
                ClientSettings.pendingSanityReset = true;
            }
            if ((KeyboardInput.isKeyDown(163) || KeyboardInput.isKeyDown(162) || KeyboardInput.isKeyDown(161)) && KeyboardInput.isKeyDown(36) && this instanceof EventPostTick && Minecraft.currentScreen().isNull()) {
                Vape.INSTANCE.getModManager().getMod(gg.vape.module.none.ClientSettings.class).toggle();
            }
            ShaderProgram.setCurrentProgramId(-1);
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
        return super.fire();
    }

    public static GuiComponent[] getTickObfuscationState() {
        return obfuscationState;
    }
}
