package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class Quaternion
extends Wrapper {
    public static Quaternion fromAxisAngle(Vector3f axis, float angle, boolean degrees) {
        if (degrees) {
            angle *= (float)Math.PI / 180;
        }
        float halfAngleSin = (float)Math.sin(angle / 2.0f);
        float x = axis.getX() * halfAngleSin;
        float y = axis.getY() * halfAngleSin;
        float z = axis.getZ() * halfAngleSin;
        float w = (float)Math.cos(angle / 2.0f);
        return new Quaternion(Quaternion.vapeInstance.getMappingsMapperCompat().quaternion.newFromComponents(x, y, z, w));
    }

    public float getZ() {
        return Quaternion.vapeInstance.getMappingsMapperCompat().quaternion.getZ(this.I);
    }

    public static Quaternion fromEulerAngles(float xAngle, float yAngle, float zAngle, boolean degrees) {
        if (ForgeVersion.MC_1_20_6.d()) {
            if (degrees) {
                xAngle *= (float)Math.PI / 180;
                yAngle *= (float)Math.PI / 180;
                zAngle *= (float)Math.PI / 180;
            }
            float sinHalfX = (float)Math.sin(0.5f * xAngle);
            float cosHalfX = (float)Math.cos(0.5f * xAngle);
            float sinHalfY = (float)Math.sin(0.5f * yAngle);
            float cosHalfY = (float)Math.cos(0.5f * yAngle);
            float sinHalfZ = (float)Math.sin(0.5f * zAngle);
            float cosHalfZ = (float)Math.cos(0.5f * zAngle);
            float x = sinHalfX * cosHalfY * cosHalfZ + cosHalfX * sinHalfY * sinHalfZ;
            float y = cosHalfX * sinHalfY * cosHalfZ - sinHalfX * cosHalfY * sinHalfZ;
            float z = sinHalfX * sinHalfY * cosHalfZ + cosHalfX * cosHalfY * sinHalfZ;
            float w = cosHalfX * cosHalfY * cosHalfZ - sinHalfX * sinHalfY * sinHalfZ;
            return new Quaternion(Quaternion.vapeInstance.getMappingsMapperCompat().quaternion.newFromComponents(x, y, z, w));
        }
        return new Quaternion(Quaternion.vapeInstance.getMappingsMapperCompat().quaternion.newFromEulerAngles(xAngle, yAngle, zAngle, degrees));
    }

    public float getX() {
        return Quaternion.vapeInstance.getMappingsMapperCompat().quaternion.getX(this.I);
    }

    public float getW() {
        return Quaternion.vapeInstance.getMappingsMapperCompat().quaternion.getW(this.I);
    }

    public Quaternion(Object handle) {
        super(handle);
    }

    public float getY() {
        return Quaternion.vapeInstance.getMappingsMapperCompat().quaternion.getY(this.I);
    }
}

