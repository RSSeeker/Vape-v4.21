package gg.vape.utils;

import gg.vape.wrapper.impl.Vec3;

public class Vec3d {
    private double z;
    private static String legacyState;
    private double y;
    private double x;

    public void setZ(double z) {
        this.z = z;
    }

    public Vec3d(double d, double d2, double d3) {
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    public Vec3 toVec3() {
        return Vec3.create(this.x, this.y, this.z);
    }

    public double getZ() {
        return this.z;
    }

    public void rotateAroundYAxis(float f) {
        float f2 = (float)Math.cos(f);
        float f3 = (float)Math.sin(f);
        double d = this.x * (double)f2 + this.z * (double)f3;
        double d2 = this.y;
        double d3 = this.z * (double)f2 - this.x * (double)f3;
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public static void setLegacyState(String state) {
        legacyState = state;
    }

    public void scale(double factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
    }

    public void rotateAroundXAxis(float f) {
        float f2 = (float)Math.cos(f);
        float f3 = (float)Math.sin(f);
        double d = this.x;
        double d2 = this.y * (double)f2 + this.z * (double)f3;
        double d3 = this.z * (double)f2 - this.y * (double)f3;
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    public Vec3d(Vec3 vec3) {
        this.x = vec3.getX();
        this.y = vec3.getY();
        this.z = vec3.getZ();
    }

    public static String getLegacyState() {
        return legacyState;
    }

    public double getY() {
        return this.y;
    }

    public void translate(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void crossProduct(Vec3d first, Vec3d second) {
        double x = first.y * second.z - first.z * second.y;
        double y = second.x * first.z - second.z * first.x;
        this.z = first.x * second.y - first.y * second.x;
        this.x = x;
        this.y = y;
    }

    public void subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public void add(Vec3d vector) {
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;
    }

    public double distanceTo(Vec3d vector) {
        double d = this.x - vector.x;
        double d2 = this.y - vector.y;
        double d3 = this.z - vector.z;
        return Math.sqrt(d * d + d2 * d2 + d3 * d3);
    }

    public void subtract(Vec3d vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setCoordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void normalize() {
        double d = 1.0 / this.length();
        this.x *= d;
        this.y *= d;
        this.z *= d;
    }

    public Vec3d() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public double dotProduct(Vec3d vector) {
        return this.x * vector.x + this.y * vector.y + this.z * vector.z;
    }

    public double getX() {
        return this.x;
    }

    static {
        if (Vec3d.getLegacyState() == null) {
            Vec3d.setLegacyState("hgFcEc");
        }
    }
}
