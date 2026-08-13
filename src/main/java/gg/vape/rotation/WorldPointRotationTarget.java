package gg.vape.rotation;

import gg.vape.wrapper.impl.Vec3;

public interface WorldPointRotationTarget {
    default void setTarget(float x, float y, float z) {
        this.setTarget(Vec3.create(x, y, z));
    }

    void setTarget(Vec3 target);

    Vec3 getTarget();

    default void setTarget(double x, double y, double z) {
        this.setTarget(Vec3.create(x, y, z));
    }

    default void setTarget(int x, int y, int z) {
        this.setTarget(Vec3.create(x, y, z));
    }
}
