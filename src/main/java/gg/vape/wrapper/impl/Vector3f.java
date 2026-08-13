package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MVector3f;
import gg.vape.wrapper.Wrapper;

public class Vector3f
extends Wrapper {
    public float getZ() {
        return Vector3f.vapeInstance.getMappingsMapperCompat().vector3f.getZ(this.I);
    }

    public static Vector3f create(float x, float y, float z) {
        return new Vector3f(Vector3f.vapeInstance.getMappingsMapperCompat().vector3f.newInstance(x, y, z));
    }

    public Quaternion rotationDegrees(float degrees) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return Quaternion.fromAxisAngle(this, degrees, true);
        }
        return new Quaternion(Vector3f.vapeInstance.getMappingsMapperCompat().vector3f.rotationDegrees(this.I, degrees));
    }

    public float getX() {
        return Vector3f.vapeInstance.getMappingsMapperCompat().vector3f.getX(this.I);
    }

    public Vector3f(Object handle) {
        super(handle);
    }

    public float getY() {
        return Vector3f.vapeInstance.getMappingsMapperCompat().vector3f.getY(this.I);
    }

}

