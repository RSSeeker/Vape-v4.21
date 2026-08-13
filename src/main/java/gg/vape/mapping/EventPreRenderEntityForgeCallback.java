package gg.vape.mapping;

import gg.vape.event.impl.EventPreRenderLiving;
import gg.vape.mapping.InsertedEventCallback;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.PreRenderEntityForgeEvent;

public class EventPreRenderEntityForgeCallback
implements InsertedEventCallback {
    private final double v;
    private final Object L;
    private final EntityLivingBase a;
    private final double U;
    private final double T;

    @Override
    public boolean fire() {
        boolean bl = new EventPreRenderLiving(this.a, this.U, this.v, this.T, null).fire();
        if (this.L != null) {
            PreRenderEntityForgeEvent preRenderEntityForgeEvent = new PreRenderEntityForgeEvent(this.L);
            preRenderEntityForgeEvent.setCancelled(bl);
        }
        return bl;
    }

    public EventPreRenderEntityForgeCallback(Object object, double d, double d2, double d3) {
        this(null, object, d, d2, d3);
    }

    public double getPosZ() {
        return this.T;
    }

    public EventPreRenderEntityForgeCallback(Object object, Object object2, double d, double d2, double d3) {
        this.L = object;
        this.a = new EntityLivingBase(object2);
        this.U = d;
        this.v = d2;
        this.T = d3;
    }

    public double getPosX() {
        return this.U;
    }

    public double getPosY() {
        return this.v;
    }

    public Object getForgeEvent() {
        return this.L;
    }

    public EntityLivingBase getClientPlayer() {
        return this.a;
    }
}

