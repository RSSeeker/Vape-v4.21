package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.world.XRay;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Tessellator;

public class EventLegacyXRayBlockRenderBase
extends Event {
    private static boolean obfuscationState;
    private final Tessellator tessellator;
    private static final EventListeners EVENT_LISTENERS;
    private final Block block;

    public Tessellator getTessellator() {
        return this.tessellator;
    }

    @Override
    public boolean fire() {
        XRay xRay = Vape.INSTANCE.getModManager().getXRayModule();
        if (xRay == null || !xRay.boolean_r()) {
            return false;
        }
        if (!xRay.isTargetBlock(this.block)) {
            // 1.7.10 has no Tessellator.isDrawing field; w() returns false there,
            // which would skip the opacity and leave non-target blocks opaque.
            // The face-render injection point is by definition inside an active
            // tessellator, so applying the color is safe without the guard.
            if (this.tessellator.w() || ForgeVersion.MC_1_8_9.v()) {
                this.tessellator.u(255, 255, 255, xRay.getOpacity());
            }
        }
        return this.isCanceled();
    }
    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public Block getBlock() {
        return this.block;
    }

    public static void setXRayObfuscationState(boolean state) {
        obfuscationState = state;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }


    public EventLegacyXRayBlockRenderBase(Object blockHandle) {
        this.block = new Block(blockHandle);
        this.tessellator = Tessellator.getInstance();
    }

    public static boolean getXRayObfuscationState() {
        return obfuscationState;
    }

    public static boolean getObfuscationConstant() {
        boolean state = EventLegacyXRayBlockRenderBase.getXRayObfuscationState();
        return true;
    }

    static {
        EVENT_LISTENERS = new EventListeners();
        EventLegacyXRayBlockRenderBase.setXRayObfuscationState(false);
    }
}

