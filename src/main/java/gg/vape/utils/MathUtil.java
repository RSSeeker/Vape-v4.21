package gg.vape.utils;

import gg.vape.wrapper.impl.Vec3i;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MathUtil {
    private static final double[] S;
    private static final int[] J;
    private static final Map d;
    private static int s;
    private static final double[] Y;
    private static final long[] b;
    private static final float[] A;
    private static final double Z;
    private static final Integer[] c;

    private static int log2Ceil(int n) {
        n = MathUtil.isPowerOfTwo(n) ? n : MathUtil.nextPowerOfTwo(n);
        return J[(int)((long)n * 125613361L >> 27) & 0x1F];
    }

    public static int t() {
        int n = MathUtil.n();
        return 0;
    }

    public static double pushAwayFromZeroIfWithinThreshold(double d, double d2) {
        double d3 = d;
        if (d3 > 0.0) {
            if (d3 < d2) {
                d3 += d2;
            }
        } else if (d3 > -d2) {
            d3 -= d2;
        }
        return d3;
    }

    public static double H(double d) {
        return d >= 0.0 ? d : -d;
    }

    public static float abs(float f) {
        return f >= 0.0f ? f : -f;
    }

    public static double p(double d, double d2) {
        int n = (int)Math.floor(d / d2);
        return d - (double)n * d2;
    }

    public static double Z(double d, double d2, double d3, double d4, double d5, double d6) {
        return Math.sqrt(Math.pow(d - d4, 2.0) + Math.pow(d2 - d5, 2.0) + Math.pow(d3 - d6, 2.0));
    }

    public static long S(int n, int n2, int n3) {
        long l2 = (long)(n * 3129871) ^ (long)n3 * 116129781L ^ (long)n2;
        l2 = l2 * l2 * 42317861L + l2 * 11L;
        return l2;
    }

    public static int parseInt(String string, int n) {
        int n2 = n;
        try {
            n2 = Integer.parseInt(string);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return n2;
    }

    public static float round(float f, float f2, float f3) {
        return f2 + f * (f3 - f2);
    }

    public static long S(Vec3i vec3i) {
        return MathUtil.S(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public static double g(double ... dArray) {
        double d = 0.0;
        for (double d2 : dArray) {
            d += Math.pow(d2, 2.0);
        }
        return Math.sqrt(d);
    }

    public static int absFloor(double d) {
        return (int)(d >= 0.0 ? d : -d + 1.0);
    }

    public static long floorLong(double d) {
        long l = (long)d;
        return d < (double)l ? l - 1L : l;
    }

    public static double roundToIncrement(double d, double d2) {
        double d3 = 1.0 / d2;
        return (double)Math.round(d * d3) / d3;
    }

    public static float c(float f, float f2) {
        int n = (int)Math.floor(f / f2);
        return f - (float)n * f2;
    }

    public static int floor(double d) {
        int n = (int)d;
        return d < (double)n ? n - 1 : n;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static double clampedLerp(double d, double d2, double d3) {
        return d3 < 0.0 ? d : (d3 > 1.0 ? d2 : d + (d2 - d) * d3);
    }

    public static int clamp(int n, int n2, int n3) {
        return n < n2 ? n2 : (n > n3 ? n3 : n);
    }

    public static int floorDiv(int n, int n2) {
        return n < 0 ? -((-n - 1) / n2) - 1 : n / n2;
    }

    public static float random(Random random, float f, float f2) {
        return f >= f2 ? f : random.nextFloat() * (f2 - f) + f;
    }

    public static int randomExclusiveUpper(Random random, int n, int n2) {
        return random.nextInt(n2 - n) + n;
    }

    public static double D(double d) {
        double d2 = 0.5 * d;
        long l = Double.doubleToRawLongBits(d);
        l = 6910469410427058090L - (l >> 1);
        d = Double.longBitsToDouble(l);
        d *= 1.5 - d2 * d * d;
        return d;
    }

    public static int ceil(float f) {
        int n = (int)f;
        return f > (float)n ? n + 1 : n;
    }

    public static int log2(int n) {
        return MathUtil.log2Ceil(n) - (MathUtil.isPowerOfTwo(n) ? 0 : 1);
    }

    static {
        d = new HashMap(13);
        MathUtil.g(91);
        long[] lArray = new long[]{-5058601735682614043L, -6095211415617468827L, -3400524149349193330L, -334337254439352656L, -3133572606271077933L};
        b = lArray;
        c = new Integer[5];
        Z = Double.longBitsToDouble(4805340802404319232L);
        Y = new double[257];
        S = new double[257];
        A = new float[65536];
        int n = 0;
        while (true) {
            if (n >= 65536) break;
            MathUtil.A[n] = (float)Math.sin((double)n * Math.PI * 2.0 / 65536.0);
            ++n;
        }
        J = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
    }

    public static double stepAwayFromZero(double d, double d2) {
        double d3 = d;
        d3 = d3 > 0.0 ? (d3 += Math.abs(d2)) : (d3 -= Math.abs(d2));
        return d3;
    }

    public static void g(int n) {
        s = n;
    }

    public static final float sin(float f) {
        return A[(int)(f * 10430.378f) & 0xFFFF];
    }

    public static float clamp(float f, float f2, float f3) {
        return f < f2 ? f2 : (f > f3 ? f3 : f);
    }

    public static int parseIntWithMin(String string, int n, int n2) {
        int n3 = n;
        try {
            n3 = Integer.parseInt(string);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        if (n3 < n2) {
            n3 = n2;
        }
        return n3;
    }

    public static double clamp(double d, double d2, double d3) {
        return d < d2 ? d2 : (d > d3 ? d3 : d);
    }

    public static boolean e(double d, double d2, double d3) {
        return d >= d2 && d <= d3;
    }

    public static double roundToScale(double d, int n) {
        if (n < 0) {
            return d;
        }
        BigDecimal bigDecimal = new BigDecimal(d);
        bigDecimal = bigDecimal.setScale(n, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static boolean H(double d, double d2, double d3, double d4, double d5) {
        double d6;
        double d7 = d - d4;
        double d8 = d2 - d5;
        double d9 = d3 * d3;
        return (d6 = (d7 *= d7) + (d8 *= d8)) <= d9;
    }

    public static int stepAwayFromZero(int n, int n2) {
        int n3 = n;
        n3 = n3 > 0 ? (n3 += Math.abs(n2)) : (n3 -= Math.abs(n2));
        return n3;
    }

    public static double average(long[] lArray) {
        long l = 0L;
        long[] lArray2 = lArray;
        int n = lArray.length;
        for (int i = 0; i < n; ++i) {
            long l2 = lArray2[i];
            l += l2;
        }
        return (double)l / (double)lArray.length;
    }

    public static double maxAbs(double d, double d2) {
        if (d < 0.0) {
            d = -d;
        }
        if (d2 < 0.0) {
            d2 = -d2;
        }
        return d > d2 ? d : d2;
    }

    public static float wrapAngleTo180(float f) {
        if ((f %= 360.0f) >= 180.0f) {
            f -= 360.0f;
        }
        if (f < -180.0f) {
            f += 360.0f;
        }
        return f;
    }

    public static int n() {
        return s;
    }

    public static final float sqrt(float f) {
        return (float)Math.sqrt(f);
    }

    public static double parseDoubleWithMin(String string, double d, double d2) {
        double d3 = d;
        try {
            d3 = Double.parseDouble(string);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        if (d3 < d2) {
            d3 = d2;
        }
        return d3;
    }

    public static int floor(float f) {
        int n = (int)f;
        return f < (float)n ? n - 1 : n;
    }

    public static final float cos(float f) {
        return A[(int)(f * 10430.378f + 16384.0f) & 0xFFFF];
    }

    public static int roundAwayFromZeroToMultiple(int n, int n2) {
        int n3;
        if (n2 == 0) {
            return 0;
        }
        if (n < 0) {
            n2 *= -1;
        }
        return (n3 = n % n2) == 0 ? n : n + n2 - n3;
    }

    public static double parseDouble(String string, double d) {
        double d2 = d;
        try {
            d2 = Double.parseDouble(string);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return d2;
    }

    public static final float sqrt(double d) {
        return (float)Math.sqrt(d);
    }

    public static boolean r(int n, int n2, int n3) {
        return n >= n2 && n <= n3;
    }

    public static double wrapAngleTo180(double d) {
        if ((d %= 360.0) >= 180.0) {
            d -= 360.0;
        }
        if (d < -180.0) {
            d += 360.0;
        }
        return d;
    }

    public static boolean O(double d, double d2, double d3, double d4, double d5) {
        return d4 >= d - d3 && d4 <= d + d3 && d5 >= d2 - d3 && d5 <= d2 + d3;
    }

    public static double randomRange(Random random, double d, double d2) {
        return d + (d2 - d) * random.nextDouble();
    }

    public static int ceil(double d) {
        int n = (int)d;
        return d > (double)n ? n + 1 : n;
    }

    public static boolean e(float f, float f2, float f3) {
        return f >= f2 && f <= f3;
    }

    public static int fastFloor(double d) {
        return (int)(d + 1024.0) - 1024;
    }

    public static double V(double d, double d2) {
        double d3;
        boolean bl;
        boolean bl2;
        boolean bl3;
        double d4 = d2 * d2 + d * d;
        if (Double.isNaN(d4)) {
            return Double.NaN;
        }
        boolean bl4 = bl3 = d < 0.0;
        if (bl3) {
            d = -d;
        }
        boolean bl5 = bl2 = d2 < 0.0;
        if (bl2) {
            d2 = -d2;
        }
        boolean bl6 = bl = d > d2;
        if (bl) {
            d3 = d2;
            d2 = d;
            d = d3;
        }
        d3 = MathUtil.D(d4);
        d2 *= d3;
        double d5 = Z + (d *= d3);
        int n = (int)Double.doubleToRawLongBits(d5);
        double d6 = Y[n];
        double d7 = S[n];
        double d8 = d5 - Z;
        double d9 = d * d7 - d2 * d8;
        double d10 = (6.0 + d9 * d9) * d9 * 0.16666666666666666;
        double d11 = d6 + d10;
        if (bl) {
            d11 = 1.5707963267948966 - d11;
        }
        if (bl2) {
            d11 = Math.PI - d11;
        }
        if (bl3) {
            d11 = -d11;
        }
        return d11;
    }


    private static boolean isPowerOfTwo(int n) {
        return n != 0 && (n & n - 1) == 0;
    }

    public static int randomInclusive(Random random, int n, int n2) {
        return n >= n2 ? n : random.nextInt(n2 - n + 1) + n;
    }

    public static int abs(int n) {
        return n >= 0 ? n : -n;
    }

    public static int nextPowerOfTwo(int n) {
        int n2 = n - 1;
        n2 |= n2 >> 1;
        n2 |= n2 >> 2;
        n2 |= n2 >> 4;
        n2 |= n2 >> 8;
        n2 |= n2 >> 16;
        return n2 + 1;
    }

    public static double random(Random random, double d, double d2) {
        return d >= d2 ? d : random.nextDouble() * (d2 - d) + d;
    }
}
