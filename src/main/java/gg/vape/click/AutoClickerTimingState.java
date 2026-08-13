package gg.vape.click;

import java.util.SplittableRandom;

public class AutoClickerTimingState {
    private static final long MIN_DRIFT_INTERVAL_MILLIS = 1200L;
    private static final long MAX_DRIFT_INTERVAL_MILLIS = 3200L;
    private static final long MIN_TARGET_INTERVAL_MILLIS = 30000L;
    private static final long MAX_TARGET_INTERVAL_MILLIS = 90000L;
    private static final long IDLE_RESET_THRESHOLD_MILLIS = 1200L;
    private static final double MAX_FATIGUE = 0.4;

    private double currentCps;
    private long previousDelayMillis;
    private final long timingSalt;
    private double targetCps;
    private boolean initialized;
    private double targetOffset;
    private double pauseChanceOffset;
    private double repeatDelayChance = 0.06;
    private final SplittableRandom random;
    private double minimumCps;
    private double maximumCps;
    private long nextDriftTimestamp;
    private double fatigue;
    private int clicksSinceVariation;
    private int burstClicksRemaining;
    private long lastClickTimestamp;
    private long nextTargetTimestamp;

    public AutoClickerTimingState(long seed) {
        long randomSeed = mixSeed(seed ^ 0x9E3779B97F4A7C15L);
        this.random = new SplittableRandom(randomSeed);
        this.timingSalt = mixSeed(randomSeed + -3335678366873096957L);
    }

    private void scheduleTargetChange(long now) {
        this.nextTargetTimestamp = now + this.randomDuration(
                now, MIN_TARGET_INTERVAL_MILLIS, MAX_TARGET_INTERVAL_MILLIS);
    }

    private static double midpoint(double first, double second) {
        return (first + second) * 0.5;
    }

    private void scheduleDrift(long now) {
        this.nextDriftTimestamp = now + this.randomDuration(
                now, MIN_DRIFT_INTERVAL_MILLIS, MAX_DRIFT_INTERVAL_MILLIS);
    }

    private double sampleNormalDistribution() {
        double sampleSum = 0.0;
        for (int sampleIndex = 0; sampleIndex < 6; ++sampleIndex) {
            sampleSum += this.random.nextDouble();
        }
        return (sampleSum - 3.0) * 1.22474487139;
    }

    private void initializeTiming(long now) {
        this.scheduleDrift(now);
        this.scheduleTargetChange(now);
        this.targetOffset = (this.random.nextDouble() * 2.0 - 1.0) * 0.75;
        this.targetCps = clamp(midpoint(this.minimumCps, this.maximumCps) + this.targetOffset,
                this.minimumCps, this.maximumCps);
    }

    private long randomDuration(long now, long minimumDuration, long maximumDuration) {
        long timeComponent = (now ^ this.timingSalt) & 0xFFFFL;
        long durationRange = maximumDuration - minimumDuration + 1L;
        return minimumDuration
                + (Math.abs((int)timeComponent) + this.random.nextInt((int)durationRange)) % durationRange;
    }

    private double sampleClickNoise() {
        double normalSample = this.sampleNormalDistribution();
        double variationRoll = this.random.nextDouble();
        if (variationRoll < 0.12) {
            return (this.random.nextDouble() - 0.5) * 3.0;
        }
        if (variationRoll < 0.17) {
            return normalSample * (1.4 + this.random.nextDouble() * 0.6);
        }
        return normalSample;
    }

    private static long mixSeed(long seed) {
        seed = (seed ^ seed >>> 30) * -4658895280553007687L;
        seed = (seed ^ seed >>> 27) * -7723592293110705685L;
        return seed ^ seed >>> 31;
    }

    public void configureCpsRange(int minimumCps, int maximumCps) {
        double rawMinimumCps = Math.max(0, minimumCps);
        double rawMaximumCps = Math.max(rawMinimumCps, (double)maximumCps);
        double scaledMinimumCps = rawMinimumCps * 1.5;
        double scaledMaximumCps = rawMaximumCps * 1.5;
        scaledMinimumCps = Math.max(0.5, scaledMinimumCps);
        scaledMaximumCps = Math.max(0.5, scaledMaximumCps);

        this.minimumCps = scaledMinimumCps;
        this.maximumCps = Math.max(scaledMinimumCps + 0.1, scaledMaximumCps);
        if (!this.initialized) {
            this.targetCps = midpoint(this.minimumCps, this.maximumCps);
            this.currentCps = clamp(this.targetCps + this.sampleNormalDistribution() * 0.25,
                    this.minimumCps, this.maximumCps);
        }
    }

    private static double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    public long getNextDelayMillis() {
        long now = System.currentTimeMillis();
        if (!this.initialized) {
            this.initialized = true;
            this.initializeTiming(now);
            this.lastClickTimestamp = now;
        }

        long idleDuration = this.lastClickTimestamp == 0L ? 0L : now - this.lastClickTimestamp;
        if (idleDuration >= IDLE_RESET_THRESHOLD_MILLIS) {
            double recoveryAmount = (double)Math.min(5000L, idleDuration) / 10.0;
            this.fatigue = Math.max(0.0, this.fatigue - 0.004 * recoveryAmount);
            this.burstClicksRemaining = this.random.nextDouble() < 0.7
                    ? 2 + this.random.nextInt(4)
                    : 0;
            this.repeatDelayChance = 0.03 + this.random.nextDouble() * 0.06;
            this.pauseChanceOffset = this.random.nextDouble() * 0.04;
        }

        if (++this.clicksSinceVariation > 80 + this.random.nextInt(120)) {
            this.repeatDelayChance = 0.03 + this.random.nextDouble() * 0.06;
            this.pauseChanceOffset = this.random.nextDouble() * 0.04;
            this.clicksSinceVariation = 0;
        }

        if (now >= this.nextDriftTimestamp) {
            double drift = this.sampleNormalDistribution();
            this.currentCps += 0.25 * (this.targetCps - this.currentCps) + 0.45 * drift;
            if (this.random.nextDouble() < 0.03) {
                this.currentCps += (this.random.nextDouble() - 0.5) * 1.2;
            }
            this.currentCps = clamp(this.currentCps, this.minimumCps, this.maximumCps);
            this.scheduleDrift(now);
        }

        if (now >= this.nextTargetTimestamp) {
            this.targetOffset = (this.random.nextDouble() * 2.0 - 1.0) * 0.75;
            this.targetCps = clamp(midpoint(this.minimumCps, this.maximumCps) + this.targetOffset,
                    this.minimumCps, this.maximumCps);
            this.scheduleTargetChange(now);
        }

        boolean isBurstClick = this.burstClicksRemaining > 0;
        double effectiveCps = this.currentCps;
        if (isBurstClick) {
            effectiveCps *= 1.0 + 0.05 * (0.4 + 0.8 * this.random.nextDouble());
            --this.burstClicksRemaining;
        }
        effectiveCps *= 1.0 - Math.min(MAX_FATIGUE, this.fatigue);
        effectiveCps = clamp(effectiveCps, this.minimumCps, this.maximumCps);

        double baseDelay = 1000.0 / effectiveCps;
        double timingMultiplier = Math.exp(0.24 * this.sampleClickNoise());
        int maximumMicroJitter = 35 + (this.random.nextInt(11) - 5);
        double delayMillis = baseDelay * timingMultiplier
                + this.random.nextInt(Math.max(1, maximumMicroJitter) + 1);
        if (isBurstClick) {
            delayMillis += this.random.nextInt(15);
        }
        if (this.previousDelayMillis > 0L && this.random.nextDouble() < this.repeatDelayChance) {
            delayMillis = this.previousDelayMillis + this.random.nextInt(7) - 3L;
        }
        if (this.random.nextDouble() < 0.07) {
            delayMillis *= 0.7 + this.random.nextDouble() * 0.2;
        }
        double pauseChance = Math.min(0.04 + 0.12 * this.fatigue + this.pauseChanceOffset, 0.18);
        if (this.random.nextDouble() < pauseChance) {
            delayMillis += 50 + this.random.nextInt(101);
        }

        this.fatigue = Math.min(MAX_FATIGUE, this.fatigue + 0.015);
        this.lastClickTimestamp = now;
        this.previousDelayMillis = (long)Math.max(1.0, Math.min(delayMillis, Integer.MAX_VALUE));
        return this.previousDelayMillis;
    }
}
