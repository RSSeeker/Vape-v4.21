package gg.vape.wrapper.impl;

import gg.vape.utils.Vec3d;
import gg.vape.wrapper.Wrapper;

public class Vec3
extends Wrapper {
    private static int u;
    public static Vec3 v;

    public static final Vec3 H() {
        if (v == null) {
            v = new Vec3(Vec3.vapeInstance.getMappings().g.O());
        }
        return v;
    }

    public Vec3 r(double d) {
        return new Vec3(Vec3.vapeInstance.getMappings().g.H(this.I, d));
    }

    public double m$src$D$aa18vd() {
        return this.X(this);
    }

    public double getY() {
        return Vec3.vapeInstance.getMappings().g.z(this.I);
    }

    public void N(double d) {
        Vec3.vapeInstance.getMappings().g.V(this.I, d);
    }

    public static int b() {
        int n = Vec3.v();
        return 0;
    }

    public Vec3 addVector(double d, double d2, double d3) {
        return new Vec3(Vec3.vapeInstance.getMappings().g.Y(this.I, d, d2, d3));
    }

    public static void L(int n) {
        u = n;
    }

    public double X(Vec3 vec3) {
        return Math.abs(vec3.getX()) + Math.abs(vec3.getY()) + Math.abs(vec3.getZ());
    }

    public Vec3d toVec3d() {
        return new Vec3d(this.getX(), this.getY(), this.getZ());
    }

    public Vec3 add(Vec3 vec3) {
        return this.addVector(vec3.getX(), vec3.getY(), vec3.getZ());
    }


    public void Z(double d) {
        Vec3.vapeInstance.getMappings().g.Z(this.I, d);
    }

    public double distanceTo(Vec3 vec3) {
        return Vec3.vapeInstance.getMappings().g.S(this.I, vec3.getObject());
    }

    public double Q() {
        double d = this.getX();
        double d2 = this.getZ();
        return Math.sqrt(d * d + d2 * d2);
    }

    public Vec3 scale(double d) {
        return Vec3.create(this.getX() * d, this.getY() * d, this.getZ() * d);
    }

    public Vec3 G(double d, double d2, double d3) {
        return Vec3.create(this.getX() * d, this.getY() * d2, this.getZ() * d3);
    }

    public double A(Vec3 vec3) {
        double d = vec3.getX() - this.getX();
        double d2 = vec3.getY() - this.getY();
        double d3 = vec3.getZ() - this.getZ();
        return d * d + d2 * d2 + d3 * d3;
    }

    public Vec3 q(Vec3 vec3) {
        return this.C(vec3.getX(), vec3.getY(), vec3.getZ());
    }

    public Vec3(Object object) {
        super(object);
    }

    public void m(double d) {
        Vec3.vapeInstance.getMappings().g.K(this.I, d);
    }

    public Vec3 N(Vec3 vec3) {
        return this.G(vec3.getX(), vec3.getY(), vec3.getZ());
    }

    public Vec3 C(double d, double d2, double d3) {
        return this.addVector(-d, -d2, -d3);
    }

    @Override
    public String toString() {
        return "[" + this.getX() + "," + this.getY() + "," + this.getZ() + "]";
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!(object instanceof Vec3)) {
            return false;
        }
        Vec3 vec3 = (Vec3)object;
        return vec3.getX() == this.getX() && vec3.getY() == this.getY() && vec3.getZ() == this.getZ();
    }

    static {
        if (Vec3.v() == 0) {
            Vec3.L(5);
        }
    }

    public double getZ() {
        return Vec3.vapeInstance.getMappings().g.P(this.I);
    }

    public static int v() {
        return u;
    }

    public Vec3 i(Object object, double d) {
        return new Vec3(Vec3.vapeInstance.getMappings().g.M(this.I, object, d));
    }

    public double j() {
        double d = this.getX();
        double d2 = this.getY();
        double d3 = this.getZ();
        return d * d + d2 * d2 + d3 * d3;
    }

    public double getX() {
        return Vec3.vapeInstance.getMappings().g.O(this.I);
    }

    public double H(Vec3 vec3) {
        double d = vec3.getX() - this.getX();
        double d2 = vec3.getY() - this.getY();
        double d3 = vec3.getZ() - this.getZ();
        return d * d + d2 * d2 + d3 * d3;
    }

    public static Vec3 create(double d, double d2, double d3) {
        return new Vec3(Vec3.vapeInstance.getMappings().g.q(d, d2, d3));
    }
}

