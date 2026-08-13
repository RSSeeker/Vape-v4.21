package gg.vape.utils;


public class FastAtanMath {
    private static final float[] w;
    private static final int U;
    private static final float[] R;
    private static final float P = 1.5707964f;
    private static final float[] G;
    private static final float[] S;
    private static final int a;
    private static final float[] c;
    private static final float A = (float)Math.PI;
    private static final float[] d;
    private static final float[] v;
    private static final float[] O;
    private static final float[] M;

    static {
        long[] lArray = new long[]{-4778158233560709471L, -4211190230859807071L, 3095591377875076769L, -5764595647449561440L, 8562453793512457888L};
        a = (int)lArray[3];
        U = (int)lArray[2];
        w = new float[(int)lArray[0]];
        M = new float[(int)lArray[1]];
        d = new float[(int)lArray[1]];
        G = new float[(int)lArray[1]];
        v = new float[(int)lArray[1]];
        c = new float[(int)lArray[1]];
        O = new float[(int)lArray[1]];
        S = new float[(int)lArray[1]];
        for (int i = 0; i <= (int)lArray[4]; ++i) {
            float f;
            double d = (double)i / 100000.0;
            double d2 = 1.0;
            double d3 = d2 * d;
            FastAtanMath.w[i] = f = (float)Math.atan2(d3, d2);
            FastAtanMath.M[i] = (float)Math.PI - f;
            FastAtanMath.d[i] = -f;
            FastAtanMath.G[i] = (float)(-Math.PI) + f;
            FastAtanMath.v[i] = 1.5707964f - f;
            FastAtanMath.c[i] = 1.5707964f + f;
            FastAtanMath.O[i] = -1.5707964f + f;
            FastAtanMath.S[i] = -1.5707964f - f;
        }
        R = new float[]{-0.33333147f, 0.19993551f, -0.142089f, 0.10656264f, 1.5707964f};
    }


    public static float atan2Approximation(float f, float f2) {
        if (f < 0.0f) {
            if (f2 < 0.0f) {
                if (f < f2) {
                    return S[(int)(f2 / f * 100000.0f)];
                }
                return G[(int)(f / f2 * 100000.0f)];
            }
            if ((f = -f) > f2) {
                return O[(int)(f2 / f * 100000.0f)];
            }
            return d[(int)(f / f2 * 100000.0f)];
        }
        if (f2 < 0.0f) {
            if (f > (f2 = -f2)) {
                return c[(int)(f2 / f * 100000.0f)];
            }
            return M[(int)(f / f2 * 100000.0f)];
        }
        if (f > f2) {
            return v[(int)(f2 / f * 100000.0f)];
        }
        return w[(int)(f / f2 * 100000.0f)];
    }

    public static float atanApproximation(float f) {
        float f2;
        float f3 = Math.abs(f);
        if (f3 < 1.0f) {
            float f4 = f * f;
            f2 = f * (1.0f + f4 * (R[0] + f4 * (R[1] + f4 * (R[2] + f4 * R[3]))));
        } else {
            float f5 = 1.0f / f3;
            float f6 = f5 * f5;
            float f7 = f5 * (1.0f + f6 * (R[0] + f6 * (R[1] + f6 * (R[2] + f6 * R[3]))));
            f2 = Math.copySign(R[4], f) - f7;
        }
        return f2;
    }
}

