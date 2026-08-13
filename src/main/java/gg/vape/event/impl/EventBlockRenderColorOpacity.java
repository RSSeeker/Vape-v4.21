package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.world.XRay;
import gg.vape.wrapper.impl.WorldRenderer;
import java.nio.ByteOrder;

public class EventBlockRenderColorOpacity
extends Event {
    private int opacity;
    private static final long DEFAULT_OPACITY_BITS;
    private final WorldRenderer worldRenderer;
    private final int vertexIndex;
    private static final EventListeners EVENT_LISTENERS;
    private final float redMultiplier;
    private final float greenMultiplier;
    private final float blueMultiplier;

    static {
        DEFAULT_OPACITY_BITS = 22123904822673663L;
        EVENT_LISTENERS = new EventListeners();
    }


    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        XRay xRay = Vape.INSTANCE.getModManager().getXRayModule();
        if (xRay != null && xRay.isEnabled()) {
            int blue;
            int green;
            int red;
            xRay.onBlockRenderColorOpacity(this);
            int bufferIndex = this.worldRenderer.o(this.vertexIndex);
            int packedColor = this.worldRenderer.O().get(bufferIndex);
            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                red = (int)((float)(packedColor & 0xFF) * this.redMultiplier);
                green = (int)((float)(packedColor >> 8 & 0xFF) * this.greenMultiplier);
                blue = (int)((float)(packedColor >> 16 & 0xFF) * this.blueMultiplier);
            } else {
                red = (int)((float)(bufferIndex >> 24 & 0xFF) * this.redMultiplier);
                green = (int)((float)(bufferIndex >> 16 & 0xFF) * this.greenMultiplier);
                blue = (int)((float)(bufferIndex >> 8 & 0xFF) * this.blueMultiplier);
            }
            int outputColor = 0;
            outputColor |= this.opacity << 24;
            outputColor |= red << 16;
            outputColor |= green << 8;
            this.worldRenderer.O().put(bufferIndex, outputColor |= blue);
        }
        return this.isCanceled();
    }

    public EventBlockRenderColorOpacity(Object worldRendererHandle, float redMultiplier, float greenMultiplier, float blueMultiplier, int vertexIndex) {
        this.worldRenderer = new WorldRenderer(worldRendererHandle);
        this.redMultiplier = redMultiplier;
        this.greenMultiplier = greenMultiplier;
        this.blueMultiplier = blueMultiplier;
        this.vertexIndex = vertexIndex;
        this.opacity = (int)DEFAULT_OPACITY_BITS;
    }
}

