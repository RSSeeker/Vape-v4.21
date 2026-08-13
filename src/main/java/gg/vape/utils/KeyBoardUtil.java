package gg.vape.utils;

import gg.vape.input.InputEventDispatcher;
import gg.vape.runtime.NativeBridge;

public class KeyBoardUtil {
    public static final int Q;
    public static final int h;
    public static final int M;
    public static final int W;
    public static final int N;
    public static final int v;
    public static final int k;
    public static final int e;
    public static final int A;
    public static final int t;
    public static final int g;

    public static boolean m(int n) {
        return (NativeBridge.gks(n) & 0x100) != 0;
    }


    static {
        long[] lArray = new long[]{8077820790617145376L, 297696900683923521L, 7634409973295874135L, -1542298918925107117L, -3486869839207726942L, -6499915239893499644L, -477195949194608480L, -4462240591253602043L, -3345871758006681532L, 350199465673490688L, 2390434692210884865L};
        h = (int)lArray[10];
        g = (int)lArray[7];
        N = (int)lArray[3];
        t = (int)lArray[2];
        e = (int)lArray[1];
        W = (int)lArray[0];
        v = (int)lArray[9];
        M = (int)lArray[8];
        A = (int)lArray[5];
        Q = (int)lArray[4];
        k = (int)lArray[6];
    }

    public static void K(int n) {
        int n2 = n == 18 || n == 164 || n == 165 ? 261 : 257;
        NativeBridge.smpm(false, InputEventDispatcher.getInstance().getWindowHandle(), n2, n, KeyBoardUtil.m(n, true));
    }

    private static long m(int n, boolean bl) {
        int n2 = NativeBridge.mvk(n, 0) & 0xFF;
        long l = 1L | (long)n2 << 16;
        if (KeyBoardUtil.z(n)) {
            l |= 0x1000000L;
        }
        if (bl) {
            l |= 0xC0000000L;
        }
        return l;
    }

    public static void l(int n) {
        int n2 = n == 18 || n == 164 || n == 165 ? 260 : 256;
        NativeBridge.smpm(false, InputEventDispatcher.getInstance().getWindowHandle(), n2, n, KeyBoardUtil.m(n, false));
    }

    public static boolean h(int n) {
        return (NativeBridge.gks(n) & 0x100) != 0;
    }

    private static boolean z(int n) {
        switch (n) {
            case 33: 
            case 34: 
            case 35: 
            case 36: 
            case 37: 
            case 38: 
            case 39: 
            case 40: 
            case 45: 
            case 46: 
            case 111: 
            case 144: 
            case 163: 
            case 165: {
                return true;
            }
        }
        return false;
    }
}

