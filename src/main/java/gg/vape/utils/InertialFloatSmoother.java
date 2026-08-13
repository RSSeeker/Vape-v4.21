package gg.vape.utils;


public class InertialFloatSmoother {
    private float accumulatedInput;
    private float currentValue;
    private float velocity;


    public float update(float inputDelta, float responseFactor) {
        this.accumulatedInput += inputDelta;
        inputDelta = (this.accumulatedInput - this.currentValue) * responseFactor;
        this.velocity += (inputDelta - this.velocity) * 0.5f;
        if (inputDelta > 0.0f && inputDelta > this.velocity || inputDelta < 0.0f && inputDelta < this.velocity) {
            inputDelta = this.velocity;
        }
        this.currentValue += inputDelta;
        return inputDelta;
    }
}

