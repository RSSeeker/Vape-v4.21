package gg.vape.utils.render;

import gg.vape.utils.render.RenderVector4f;
import gg.vape.wrapper.impl.Matrix4f;
import gg.vape.wrapper.impl.Quaternion;
import java.nio.FloatBuffer;
import java.util.Arrays;
import org.lwjgl.BufferUtils;

public class RenderMatrix4f {
    private static boolean legacyFlag;
    public float[] elements;
    private static final String MATRIX_STRING_PREFIX;

    public void transform(RenderVector4f input, RenderVector4f output) {
        float x = input.x;
        float y = input.y;
        float z = input.z;
        float w = input.w;
        output.x = this.elements[0] * x + this.elements[4] * y + this.elements[8] * z + this.elements[12] * w;
        output.y = this.elements[1] * x + this.elements[5] * y + this.elements[9] * z + this.elements[13] * w;
        output.z = this.elements[2] * x + this.elements[6] * y + this.elements[10] * z + this.elements[14] * w;
        output.w = this.elements[3] * x + this.elements[7] * y + this.elements[11] * z + this.elements[15] * w;
    }

    public RenderMatrix4f setPerspective(float fieldOfViewDegrees, float aspectRatio, float nearPlane, float farPlane) {
        float tangent = (float)Math.tan(Math.toRadians(fieldOfViewDegrees) / 2.0);
        this.elements[0] = 1.0f / (aspectRatio * tangent);
        this.elements[5] = 1.0f / tangent;
        this.elements[10] = -((farPlane + nearPlane) / (farPlane - nearPlane));
        this.elements[11] = -(2.0f * farPlane * nearPlane / (farPlane - nearPlane));
        this.elements[14] = -1.0f;
        return this;
    }

    public RenderMatrix4f shallowCopy() {
        return new RenderMatrix4f(this);
    }

    public RenderMatrix4f setIdentity() {
        for (int elementIndex = 0; elementIndex < 16; ++elementIndex) {
            this.elements[elementIndex] = 0.0f;
        }
        this.elements[0] = 1.0f;
        this.elements[5] = 1.0f;
        this.elements[10] = 1.0f;
        this.elements[15] = 1.0f;
        return this;
    }

    public static boolean getLegacyFlag() {
        return legacyFlag;
    }

    public RenderMatrix4f multiply(RenderMatrix4f right) {
        float[] product = new float[16];
        for (int column = 0; column < 4; ++column) {
            for (int row = 0; row < 4; ++row) {
                float value = 0.0f;
                for (int component = 0; component < 4; ++component) {
                    value += this.elements[row + component * 4] * right.elements[component + column * 4];
                }
                product[row + column * 4] = value;
            }
        }
        this.elements = product;
        return this;
    }

    public FloatBuffer toFloatBuffer() {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer((int)16);
        floatBuffer.put(this.elements).flip();
        return floatBuffer;
    }

    public RenderMatrix4f(Quaternion quaternion) {
        this();
        float quaternionX = quaternion.getX();
        float quaternionY = quaternion.getY();
        float quaternionZ = quaternion.getZ();
        float quaternionW = quaternion.getW();
        float twiceXSquared = 2.0f * quaternionX * quaternionX;
        float twiceYSquared = 2.0f * quaternionY * quaternionY;
        float twiceZSquared = 2.0f * quaternionZ * quaternionZ;
        this.elements[0] = 1.0f - twiceYSquared - twiceZSquared;
        this.elements[5] = 1.0f - twiceZSquared - twiceXSquared;
        this.elements[10] = 1.0f - twiceXSquared - twiceYSquared;
        this.elements[15] = 1.0f;
        float xy = quaternionX * quaternionY;
        float yz = quaternionY * quaternionZ;
        float zx = quaternionZ * quaternionX;
        float xw = quaternionX * quaternionW;
        float yw = quaternionY * quaternionW;
        float zw = quaternionZ * quaternionW;
        this.elements[4] = 2.0f * (xy + zw);
        this.elements[1] = 2.0f * (xy - zw);
        this.elements[8] = 2.0f * (zx - yw);
        this.elements[2] = 2.0f * (zx + yw);
        this.elements[9] = 2.0f * (yz + xw);
        this.elements[6] = 2.0f * (yz - xw);
    }

    static {
        RenderMatrix4f.setLegacyFlag(true);
        MATRIX_STRING_PREFIX = "Matrix4f{elements=";
    }

    public static boolean probeLegacyFlag() {
        boolean ignoredLegacyFlag = RenderMatrix4f.getLegacyFlag();
        return false;
    }

    public RenderMatrix4f scale(float x, float y, float z) {
        RenderMatrix4f scaleMatrix = new RenderMatrix4f().setIdentity();
        scaleMatrix.elements[0] = x;
        scaleMatrix.elements[5] = y;
        scaleMatrix.elements[10] = z;
        return this.multiply(scaleMatrix);
    }

    public RenderMatrix4f translate(float x, float y, float z) {
        RenderMatrix4f translationMatrix = new RenderMatrix4f().setIdentity();
        translationMatrix.elements[12] = x;
        translationMatrix.elements[13] = y;
        translationMatrix.elements[14] = z;
        return this.multiply(translationMatrix);
    }

    public String toString() {
        return MATRIX_STRING_PREFIX + Arrays.toString(this.elements) + '}';
    }

    public static void setLegacyFlag(boolean legacyFlag) {
        RenderMatrix4f.legacyFlag = legacyFlag;
    }

    public RenderMatrix4f(RenderVector4f renderVector4f) {
        this();
        this.elements[0] = renderVector4f.x;
        this.elements[5] = renderVector4f.y;
        this.elements[10] = renderVector4f.z;
        this.elements[15] = renderVector4f.w;
    }


    public RenderMatrix4f() {
        this.elements = new float[16];
    }

    public boolean contentEquals(RenderMatrix4f other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            for (int elementIndex = 0; elementIndex < 16; ++elementIndex) {
                if (Float.compare(this.elements[elementIndex], other.elements[elementIndex]) == 0) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public RenderMatrix4f setXRotation(float angleDegrees) {
        this.setIdentity();
        float angleRadians = (float)Math.toRadians(angleDegrees);
        this.elements[5] = (float)Math.cos(angleRadians);
        this.elements[6] = -((float)Math.sin(angleRadians));
        this.elements[9] = (float)Math.sin(angleRadians);
        this.elements[10] = (float)Math.cos(angleRadians);
        return this;
    }

    public RenderMatrix4f(RenderMatrix4f renderMatrix4f) {
        this.elements = renderMatrix4f.elements;
    }

    public RenderMatrix4f setOrthographic(float left, float right, float bottom, float top, float nearPlane, float farPlane) {
        this.elements[0] = 2.0f / (right - left);
        this.elements[5] = 2.0f / (top - bottom);
        this.elements[10] = -2.0f / (farPlane - nearPlane);
        this.elements[12] = -((right + left) / (right - left));
        this.elements[13] = -((top + bottom) / (top - bottom));
        this.elements[14] = -((farPlane + nearPlane) / (farPlane - nearPlane));
        return this;
    }

    public Matrix4f toMinecraftMatrix() {
        Matrix4f minecraftMatrix = Matrix4f.G();
        Matrix4f loadedMatrix = minecraftMatrix.c(this.toFloatBuffer());
        return loadedMatrix == null ? minecraftMatrix : loadedMatrix;
    }

    public RenderMatrix4f rotate(float angleDegrees, float axisX, float axisY, float axisZ) {
        float angleRadians = (float)Math.toRadians(angleDegrees);
        float cosine = (float)Math.cos(angleRadians);
        float sine = (float)Math.sin(angleRadians);
        float oneMinusCosine = 1.0f - cosine;
        RenderMatrix4f rotationMatrix = new RenderMatrix4f().setIdentity();
        rotationMatrix.elements[0] = axisX * axisX * oneMinusCosine + cosine;
        rotationMatrix.elements[1] = axisY * axisX * oneMinusCosine + axisZ * sine;
        rotationMatrix.elements[2] = axisX * axisZ * oneMinusCosine - axisY * sine;
        rotationMatrix.elements[4] = axisX * axisY * oneMinusCosine - axisZ * sine;
        rotationMatrix.elements[5] = axisY * axisY * oneMinusCosine + cosine;
        rotationMatrix.elements[6] = axisY * axisZ * oneMinusCosine + axisX * sine;
        rotationMatrix.elements[8] = axisX * axisZ * oneMinusCosine + axisY * sine;
        rotationMatrix.elements[9] = axisY * axisZ * oneMinusCosine - axisX * sine;
        rotationMatrix.elements[10] = axisZ * axisZ * oneMinusCosine + cosine;
        return this.multiply(rotationMatrix);
    }

    public RenderMatrix4f(float[] elements) {
        this.elements = elements;
    }
}

