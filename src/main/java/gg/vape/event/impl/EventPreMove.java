package gg.vape.event.impl;

import gg.vape.event.impl.EventMove;
import gg.vape.wrapper.impl.Vec3;

public class EventPreMove
extends EventMove {
    @Override
    public boolean fire() {
        return super.fire();
    }

    @Override
    public double getY() {
        return super.getY();
    }

    public Object getVector() {
        return Vec3.create(this.getX(), this.getY(), this.getZ()).getObject();
    }

    @Override
    public double getX() {
        return super.getX();
    }

    @Override
    public double getZ() {
        return super.getZ();
    }

    public EventPreMove(double x, double y, double z) {
        super(x, y, z);
    }

    public EventPreMove(Object vectorHandle) {
        this(new Vec3(vectorHandle).getX(), new Vec3(vectorHandle).getY(), new Vec3(vectorHandle).getZ());
    }
}
