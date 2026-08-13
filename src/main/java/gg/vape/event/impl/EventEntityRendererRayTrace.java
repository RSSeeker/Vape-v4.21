package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.blatant.Clutch;
import gg.vape.module.blatant.Scaffold;
import gg.vape.module.none.MouseDelayFix;
import gg.vape.module.utility.BlockIn;

public class EventEntityRendererRayTrace
extends Event {
    private static BlockIn blockIn;
    private static final EventListeners EVENT_LISTENERS;
    private final float partialTicks;
    private static Clutch clutch;
    private final Object entityHandle;
    private static MouseDelayFix mouseDelayFix;
    private static Scaffold scaffold;

    public EventEntityRendererRayTrace(Object entityHandle, float partialTicks) {
        this.entityHandle = entityHandle;
        this.partialTicks = partialTicks;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        if (mouseDelayFix == null) {
            mouseDelayFix = Vape.INSTANCE.getModManager().getMod(MouseDelayFix.class);
            scaffold = Vape.INSTANCE.getModManager().getMod(Scaffold.class);
            blockIn = Vape.INSTANCE.getModManager().getMod(BlockIn.class);
            clutch = Vape.INSTANCE.getModManager().getMod(Clutch.class);
        }
        if (!mouseDelayFix.boolean_r() && !scaffold.boolean_r()) {
            if (!clutch.boolean_r()) {
                if (!blockIn.boolean_r()) {
                    return false;
                }
            }
        }
        return MappedClasses.z5.isInstance(this.entityHandle);
    }

    public Object getVec() {
        return Vape.INSTANCE.getMappingsMapperCompat().Rr.jL.invokeNativeBridge(this.entityHandle, Float.valueOf(this.partialTicks));
    }


    static {
        EVENT_LISTENERS = new EventListeners();
    }
}

