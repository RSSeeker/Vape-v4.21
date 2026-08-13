package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ResourceLocationKey;
import gg.vape.wrapper.impl.ResourceLocationName;

public class EventPreRenderLivingSpecials
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Object resourceLocationHandle;

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public EventPreRenderLivingSpecials(Object resourceLocationHandle) {
        this.resourceLocationHandle = resourceLocationHandle;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        try {
            ResourceLocationName resourceLocationName = new ResourceLocationName(this.resourceLocationHandle);
            String resourceName = ForgeVersion.c() == ForgeVersion.MC_1_8_9.i()
                    ? resourceLocationName.getCompleteReport()
                    : resourceLocationName.getFriendlyReport(ResourceLocationKey.L());
            Vape.logError(resourceName);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return super.fire();
    }
}
