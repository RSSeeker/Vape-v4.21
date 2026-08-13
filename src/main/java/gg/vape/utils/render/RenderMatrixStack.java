package gg.vape.utils.render;

import gg.vape.utils.render.RenderMatrix4f;
import java.util.ArrayDeque;
import java.util.Deque;

public class RenderMatrixStack {
    private final Deque<RenderMatrix4f> matrices = new ArrayDeque<RenderMatrix4f>();

    public void resetCurrent() {
        this.peek().setIdentity();
    }

    public RenderMatrixStack() {
        this.matrices.push(new RenderMatrix4f().setIdentity());
    }

    public void setOrthographic(float left, float right, float bottom, float top, float nearPlane, float farPlane) {
        this.peek().setOrthographic(left, right, bottom, top, nearPlane, farPlane);
    }

    public void translate(float x, float y, float z) {
        this.peek().translate(x, y, z);
    }

    public RenderMatrix4f peek() {
        return this.matrices.peek();
    }

    public void scale(float x, float y, float z) {
        this.peek().scale(x, y, z);
    }

    public void pop() {
        if (this.matrices.size() > 1) {
            this.matrices.pop();
        }
    }

    public void setXRotation(float angleDegrees) {
        this.peek().setXRotation(angleDegrees);
    }

    public void pushIdentity() {
        this.matrices.push(new RenderMatrix4f().setIdentity());
    }


    public void rotate(float angleDegrees, float axisX, float axisY, float axisZ) {
        this.peek().rotate(angleDegrees, axisX, axisY, axisZ);
    }

    public void multiply(RenderMatrix4f matrix) {
        this.peek().multiply(matrix);
    }
}

