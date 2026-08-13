package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MGlStateManagerTexGenState
extends Mapping {
    private static final long e;
    private static boolean p;
    private MappingMethod T;
    private MappingMethod S;

    static {
        MGlStateManagerTexGenState.d(true);
        e = 8525806735951169817L;
    }

    public int F(int n) {
        if (this.S != null) {
            return this.S.invokeInt(null, (int)e, n);
        }
        return 0;
    }


    public static boolean z() {
        return p;
    }

    public static void d(boolean bl) {
        p = bl;
    }

    public static boolean F() {
        boolean bl = MGlStateManagerTexGenState.z();
        return !bl;
    }

    public MGlStateManagerTexGenState() {
        super(MappedClasses.ZC);
        Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE};
        Class<Void> clazz = Void.TYPE;
        boolean bl = false;
        String string = "glBindSampler";
        MGlStateManagerTexGenState mGlStateManagerTexGenState = this;
        this.T = this.registerStaticMethod(string, bl, clazz, classArray);
        Class[] classArray2 = new Class[]{Integer.TYPE, Integer.TYPE};
        Class<Integer> clazz2 = Integer.TYPE;
        boolean bl2 = false;
        String string2 = "glGetIntegeri";
        MGlStateManagerTexGenState mGlStateManagerTexGenState2 = this;
        this.S = this.registerStaticMethod(string2, bl2, clazz2, classArray2);
    }

    public void K(int n, int n2) {
        if (this.T != null) {
            this.T.invokeVoid(null, n, n2);
        }
    }
}

