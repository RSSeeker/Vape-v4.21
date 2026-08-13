package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MDirectionAxis;
import gg.vape.wrapper.Wrapper;

public class DirectionAxis
extends Wrapper {
    public static DirectionAxis x() {
        return new DirectionAxis(MDirectionAxis.getX(DirectionAxis.vapeInstance.getMappingsMapperCompat().directionAxis));
    }

    public static DirectionAxis y() {
        return new DirectionAxis(MDirectionAxis.getY(DirectionAxis.vapeInstance.getMappingsMapperCompat().directionAxis));
    }

    public DirectionAxis(Object handle) {
        super(handle);
    }

    public double choose(double x, double y, double z) {
        return DirectionAxis.vapeInstance.getMappingsMapperCompat().directionAxis.choose(this.I, x, y, z);
    }
}
