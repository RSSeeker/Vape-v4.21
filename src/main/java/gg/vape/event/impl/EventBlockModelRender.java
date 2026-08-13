package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.world.XRay;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockState;
import gg.vape.wrapper.impl.ForgeVersion;

public class EventBlockModelRender
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Object rendererHandle;
    private final boolean checkSides;
    private final Object bufferHandle;
    private final Object blockAccessHandle;
    private final Object modelHandle;
    private boolean result;
    private final Object blockPosHandle;
    private final Object blockStateHandle;

    public EventBlockModelRender(Object rendererHandle, Object blockAccessHandle, Object modelHandle, Object blockStateHandle, Object blockPosHandle, Object bufferHandle, boolean checkSides) {
        this.rendererHandle = rendererHandle;
        this.blockAccessHandle = blockAccessHandle;
        this.modelHandle = modelHandle;
        this.blockStateHandle = blockStateHandle;
        this.blockPosHandle = blockPosHandle;
        this.bufferHandle = bufferHandle;
        this.checkSides = checkSides;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    public boolean getResult() {
        return this.result;
    }

    @Override
    public boolean fire() {
        XRay xRay = Vape.INSTANCE.getModManager().getXRayModule();
        if (xRay == null || !xRay.isEnabled()) {
            return false;
        }
        xRay.onBlockModelRender(this);
        try {
            long positionSeed = ForgeVersion.MC_1_12_2.d() ? MathUtil.S(new BlockPos(this.blockPosHandle)) : 0L;
            this.result = Vape.INSTANCE.getMappings().hE.renderModelAmbientOcclusion(this.rendererHandle, this.blockAccessHandle, this.modelHandle, new BlockState(this.blockStateHandle).getBlock().getObject(), this.blockStateHandle, this.blockPosHandle, this.bufferHandle, this.checkSides, positionSeed);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return this.isCanceled();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}
