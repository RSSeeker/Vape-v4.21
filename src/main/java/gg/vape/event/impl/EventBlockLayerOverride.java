package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.world.XRay;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.EnumWorldBlockLayer;

public class EventBlockLayerOverride
extends Event {
    private boolean shouldRender;
    private final Block block;
    private static int obfuscationState;
    private static final EventListeners EVENT_LISTENERS;

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }


    public Block getBlock() {
        return this.block;
    }

    @Override
    public boolean fire() {
        XRay xRay = Vape.INSTANCE.getModManager().getXRayModule();
        if (xRay == null || !xRay.boolean_r()) {
            return false;
        }
        xRay.onBlockRenderDecision(this);
        return true;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public EventBlockLayerOverride(Object blockHandle) {
        this.block = new Block(blockHandle);
    }

    public void setShouldRender(boolean shouldRender) {
        this.shouldRender = shouldRender;
    }

    public static int getBlockLayerObfuscationState() {
        return obfuscationState;
    }

    public static void setBlockLayerObfuscationState(int state) {
        obfuscationState = state;
    }

    public static int getObfuscationConstant() {
        int state = EventBlockLayerOverride.getBlockLayerObfuscationState();
        return 2;
    }

    public Object getBlockLayer() {
        return this.shouldRender ? EnumWorldBlockLayer.solid().getObject() : EnumWorldBlockLayer.translucent().getObject();
    }

    static {
        EVENT_LISTENERS = new EventListeners();
        EventBlockLayerOverride.setBlockLayerObfuscationState(0);
    }
}

