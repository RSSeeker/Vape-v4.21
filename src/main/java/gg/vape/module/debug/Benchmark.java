package gg.vape.module.debug;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPostRenderTick;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.Minecraft;
import java.util.ArrayList;
import java.util.List;

public class Benchmark
extends Mod {
    private long lastTimestamp;
    private int highestFps;
    private final List<BenchmarkSample> samples = new ArrayList<>();
    private int lowestFps;
    private final NumberValue testAmount;
    private final NumberValue benchmarkTime = NumberValue.create((Object)this, "Benchmark Time", "#", "s", 10.0, 30.0, 60.0, 1.0);
    private boolean running;
    private int captureCount;
    private int fpsSum;

    private void logResults() {
        Vape.debugLog("----------------------------");
        Vape.debugLog("Amount of Tests: " + this.testAmount.getValue());
        Vape.debugLog("Time Taken for each Tests: " + this.benchmarkTime.getValue() + "s");
        int totalAverageFps = 0;
        long totalCaptures = 0L;
        Vape.debugLog("Avg:");
        for (int sampleIndex = 0; sampleIndex < this.samples.size(); ++sampleIndex) {
            BenchmarkSample sample = this.samples.get(sampleIndex);
            Vape.debugLog(sampleIndex + 1 + ": fps - " + sample.getAverageFps() + " captures - " + sample.getCaptureCount());
            Vape.debugLog("Highest FPS: " + sample.getHighestFps() + " Lowest FPS: " + sample.getLowestFps());
            totalAverageFps = (int)((double)totalAverageFps + sample.getAverageFps());
            totalCaptures += sample.getCaptureCount();
        }
        Vape.debugLog("Avg for all tests: fps - " + totalAverageFps / this.samples.size() + " captures - " + totalCaptures / this.samples.size());
        Vape.debugLog("----------------------------");
    }

    @EventHandler
    public void onPostRenderTick(EventPostRenderTick event) {
        if (!this.running) {
            if (System.currentTimeMillis() - this.lastTimestamp <= 5000L) {
                return;
            }
            Vape.debugLog("Benchmark Started");
            this.running = true;
            this.lastTimestamp = System.currentTimeMillis();
            this.captureCount = 0;
            this.lowestFps = Integer.MAX_VALUE;
            this.highestFps = Integer.MIN_VALUE;
        }
        if ((double)this.samples.size() >= (Double)this.testAmount.getValue()) {
            this.logResults();
            this.toggle();
            return;
        }
        long elapsedMillis = System.currentTimeMillis() - this.lastTimestamp;
        if ((double)elapsedMillis >= this.testDurationMillis()) {
            this.samples.add(new BenchmarkSample(this.captureCount, this.fpsSum, this.highestFps, this.lowestFps));
            Vape.debugLog("Test " + this.samples.size() + " completed");
            this.fpsSum = 0;
            this.captureCount = 0;
            this.lowestFps = Integer.MAX_VALUE;
            this.highestFps = Integer.MIN_VALUE;
            this.lastTimestamp = System.currentTimeMillis();
            return;
        }
        ++this.captureCount;
        int currentFps = Minecraft.l();
        this.fpsSum += currentFps;
        this.highestFps = Math.max(this.highestFps, currentFps);
        this.lowestFps = Math.min(this.lowestFps, currentFps);
    }

    public Benchmark() {
        super("Benchmark", -1, Category.UTILITY);
        this.testAmount = NumberValue.create(this, "Test Amount", "#", "", 1.0, 5.0, 10.0, 1.0, "Amount of times it should benchmark to give an average");
        this.highestFps = Integer.MIN_VALUE;
        this.lowestFps = Integer.MAX_VALUE;
        this.addValue(this.benchmarkTime, this.testAmount);
    }


    private double testDurationMillis() {
        return (Double)this.benchmarkTime.getValue() * 1000.0;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.samples.clear();
        this.running = false;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Vape.debugLog("Starting Benchmark test in 5 seconds");
        this.lastTimestamp = System.currentTimeMillis();
    }
}

