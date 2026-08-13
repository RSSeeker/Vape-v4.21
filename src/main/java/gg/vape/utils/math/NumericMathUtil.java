package gg.vape.utils.math;

import gg.vape.wrapper.impl.Vec3;
import java.util.function.Consumer;

public class NumericMathUtil {
    private static final double ATAN2_TABLE_BIAS;
    private static final double[] ATAN2_TABLE_A;
    private static final double[] ATAN2_TABLE_B;
    private static final float[] SINE_TABLE;

    public static boolean approximatelyEqual(double left, double right) {
        return Math.abs(right - left) < (double)1.0E-5f;
    }

    private static void lambda$static$0(float[] sineTable) {
        for (int index = 0; index < sineTable.length; ++index) {
            sineTable[index] = (float)Math.sin((double)index * Math.PI * 2.0 / 65536.0);
        }
    }

    public static <T> T applyAndReturn(T value, Consumer<? super T> consumer) {
        consumer.accept(value);
        return value;
    }

    public static Vec3 interpolateVec3(double factor, Vec3 start, Vec3 end) {
        return Vec3.create(NumericMathUtil.interpolate(factor, start.getX(), end.getX()), NumericMathUtil.interpolate(factor, start.getY(), end.getY()), NumericMathUtil.interpolate(factor, start.getZ(), end.getZ()));
    }

    public static int floorFloat(float value) {
        int truncated = (int)value;
        return value < (float)truncated ? truncated - 1 : truncated;
    }

    public static float absFloat(float value) {
        return Math.abs(value);
    }

    public static double interpolate(double factor, double start, double end) {
        return start + factor * (end - start);
    }

    @Deprecated
    public static double fastInverseSqrt(double value) {
        double halfValue = 0.5 * value;
        long bits = Double.doubleToRawLongBits(value);
        bits = 6910469410427058090L - (bits >> 1);
        value = Double.longBitsToDouble(bits);
        return value * (1.5 - halfValue * value * value);
    }

    public static long squareLong(long value) {
        return value * value;
    }

    public static double atan2(double y, double x) {
        double swappedComponent;
        boolean swappedAxes;
        boolean negativeX;
        boolean negativeY;
        double squaredLength = x * x + y * y;
        if (Double.isNaN(squaredLength)) {
            return Double.NaN;
        }
        negativeY = y < 0.0;
        if (negativeY) {
            y = -y;
        }
        negativeX = x < 0.0;
        if (negativeX) {
            x = -x;
        }
        swappedAxes = y > x;
        if (swappedAxes) {
            swappedComponent = x;
            x = y;
            y = swappedComponent;
        }
        double inverseLength = NumericMathUtil.fastInverseSqrt(squaredLength);
        x *= inverseLength;
        double tableIndexValue = ATAN2_TABLE_BIAS + (y *= inverseLength);
        int tableIndex = (int)Double.doubleToRawLongBits(tableIndexValue);
        double baseAngle = ATAN2_TABLE_A[tableIndex];
        double correctionFactor = ATAN2_TABLE_B[tableIndex];
        double tableOffset = tableIndexValue - ATAN2_TABLE_BIAS;
        double error = y * correctionFactor - x * tableOffset;
        double correction = (6.0 + error * error) * error * 0.16666666666666666;
        double angle = baseAngle + correction;
        if (swappedAxes) {
            angle = 1.5707963267948966 - angle;
        }
        if (negativeX) {
            angle = Math.PI - angle;
        }
        if (negativeY) {
            angle = -angle;
        }
        return angle;
    }

    public static float interpolate(float factor, float start, float end) {
        return start + factor * (end - start);
    }

    public static float clamp(float value, float min, float max) {
        return value < min ? min : Math.min(value, max);
    }

    public static float wrapDegrees(long degrees) {
        float wrapped = degrees % 360L;
        if (wrapped >= 180.0f) {
            wrapped -= 360.0f;
        }
        if (wrapped < -180.0f) {
            wrapped += 360.0f;
        }
        return wrapped;
    }

    public static double square(double value) {
        return value * value;
    }

    public static int wrapDegrees(int degrees) {
        int wrapped = degrees % 360;
        if (wrapped >= 180) {
            wrapped -= 360;
        }
        if (wrapped < -180) {
            wrapped += 360;
        }
        return wrapped;
    }


    public static float sqrt(float value) {
        return (float)Math.sqrt(value);
    }

    public static int ceilFloat(float value) {
        int truncated = (int)value;
        return value > (float)truncated ? truncated + 1 : truncated;
    }

    public static int floorDouble(double value) {
        int truncated = (int)value;
        return value < (double)truncated ? truncated - 1 : truncated;
    }

    public static long floorLong(double value) {
        long truncated = (long)value;
        return value < (double)truncated ? truncated - 1L : truncated;
    }

    public static float cos(float radians) {
        return SINE_TABLE[(int)(radians * 10430.378f + 16384.0f) & 0xFFFF];
    }

    public static int ceilDouble(double value) {
        int truncated = (int)value;
        return value > (double)truncated ? truncated + 1 : truncated;
    }

    static {
        SINE_TABLE = NumericMathUtil.applyAndReturn(new float[65536], NumericMathUtil::lambda$static$0);
        ATAN2_TABLE_BIAS = Double.longBitsToDouble(4805340802404319232L);
        ATAN2_TABLE_A = new double[257];
        ATAN2_TABLE_B = new double[257];
    }

    public static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    public static double clamp(double value, double min, double max) {
        return value < min ? min : Math.min(value, max);
    }

    public static float square(float value) {
        return value * value;
    }

    public static boolean approximatelyEqual(float left, float right) {
        return Math.abs(right - left) < 1.0E-5f;
    }

    public static float sin(float radians) {
        return SINE_TABLE[(int)(radians * 10430.378f) & 0xFFFF];
    }

    public static int abs(int value) {
        return Math.abs(value);
    }

    public static double wrapDegrees(double degrees) {
        double wrapped = degrees % 360.0;
        if (wrapped >= 180.0) {
            wrapped -= 360.0;
        }
        if (wrapped < -180.0) {
            wrapped += 360.0;
        }
        return wrapped;
    }

    public static long clamp(long value, long min, long max) {
        return Math.min(Math.max(value, min), max);
    }

    public static int square(int value) {
        return value * value;
    }

    public static float wrapDegrees(float degrees) {
        float wrapped = degrees % 360.0f;
        if (wrapped >= 180.0f) {
            wrapped -= 360.0f;
        }
        if (wrapped < -180.0f) {
            wrapped += 360.0f;
        }
        return wrapped;
    }
}
