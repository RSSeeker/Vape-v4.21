package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MMinecraft;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMouseHelper
extends Mapping {
    private MappingField H;
    private MappingMethod f;
    private MappingField o;
    private MappingField q;
    private MappingField L;
    private MappingField A;
    private MappingField E;
    private MappingMethod J;
    private MappingField Y;
    private MappingField d;
    private MappingField x;

    public static int M(MMouseHelper mMouseHelper, Object object) {
        return mMouseHelper.Z(object);
    }

    public static long T(MMouseHelper mMouseHelper, Object object) {
        return mMouseHelper.N(object);
    }

    public MMouseHelper() {
        super(MappedClasses.Fi);
        Class<Integer> clazz = Integer.TYPE;
        boolean bl = true;
        String string = "width";
        MMouseHelper mMouseHelper = this;
        this.x = this.J(string, bl, clazz);
        Class<Integer> clazz2 = Integer.TYPE;
        boolean bl2 = true;
        String string2 = "height";
        MMouseHelper mMouseHelper2 = this;
        this.d = this.J(string2, bl2, clazz2);
        Class<Boolean> clazz3 = Boolean.TYPE;
        boolean bl3 = true;
        String string3 = "vsync";
        MMouseHelper mMouseHelper3 = this;
        this.A = this.J(string3, bl3, clazz3);
        if (MMinecraft.Q() != null) {
            Class<Boolean> clazz4 = Boolean.TYPE;
            boolean bl4 = true;
            String string4 = "fullscreen";
            MMouseHelper mMouseHelper4 = this;
            this.L = this.J(string4, bl4, clazz4);
            if (ForgeVersion.MC_1_17.d()) {
                Class<Integer> clazz5 = Integer.TYPE;
                boolean bl5 = true;
                String string5 = "guiScale";
                MMouseHelper mMouseHelper5 = this;
                this.E = this.J(string5, bl5, clazz5);
                Class<Integer> clazz6 = Integer.TYPE;
                boolean bl6 = true;
                String string6 = "guiScaledWidth";
                MMouseHelper mMouseHelper6 = this;
                this.o = this.J(string6, bl6, clazz6);
                Class<Integer> clazz7 = Integer.TYPE;
                boolean bl7 = true;
                String string7 = "guiScaledHeight";
                MMouseHelper mMouseHelper7 = this;
                this.H = this.J(string7, bl7, clazz7);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MMinecraft.u(new String[1]);
            }
            return;
        }
        Class<Boolean> clazz8 = Boolean.TYPE;
        boolean bl8 = true;
        String string8 = "fullscreen";
        MMouseHelper mMouseHelper8 = this;
        this.L = this.J(string8, bl8, clazz8);
        if (ForgeVersion.MC_1_17.d()) {
            Class<Integer> clazz9 = Integer.TYPE;
            boolean bl9 = true;
            String string9 = "framebufferHeight";
            MMouseHelper mMouseHelper9 = this;
            this.q = this.J(string9, bl9, clazz9);
            Class<Integer> clazz10 = Integer.TYPE;
            boolean bl10 = true;
            String string10 = "framebufferWidth";
            MMouseHelper mMouseHelper10 = this;
            this.Y = this.J(string10, bl10, clazz10);
            Class[] classArray = new Class[]{Integer.TYPE, Boolean.TYPE};
            Class<Integer> clazz11 = Integer.TYPE;
            boolean bl11 = true;
            String string11 = "calculateScale";
            MMouseHelper mMouseHelper11 = this;
            this.J = this.Y(string11, bl11, clazz11, classArray);
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            Class[] classArray = new Class[]{};
            Class<Long> clazz12 = Long.TYPE;
            boolean bl12 = true;
            String string12 = "handle";
            MMouseHelper mMouseHelper12 = this;
            this.f = this.Y(string12, bl12, clazz12, classArray);
        }
        if (ForgeVersion.MC_1_21_6.d()) {
            Class<Integer> clazz13 = Integer.TYPE;
            boolean bl13 = true;
            String string13 = "guiScale";
            MMouseHelper mMouseHelper13 = this;
            this.E = this.J(string13, bl13, clazz13);
            Class<Integer> clazz14 = Integer.TYPE;
            boolean bl14 = true;
            String string14 = "guiScaledWidth";
            MMouseHelper mMouseHelper14 = this;
            this.o = this.J(string14, bl14, clazz14);
            Class<Integer> clazz15 = Integer.TYPE;
            boolean bl15 = true;
            String string15 = "guiScaledHeight";
            MMouseHelper mMouseHelper15 = this;
            this.H = this.J(string15, bl15, clazz15);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MMinecraft.u(new String[1]);
        }
    }

    public static void w(MMouseHelper mMouseHelper, Object object, boolean bl) {
        mMouseHelper.b(object, bl);
    }


    private boolean B(Object object) {
        return this.L.getBoolean(object);
    }

    private boolean u(Object object) {
        return this.A.getBoolean(object);
    }

    public static int x(MMouseHelper mMouseHelper, Object object) {
        return mMouseHelper.M(object);
    }

    public static int z(MMouseHelper mMouseHelper, Object object) {
        return mMouseHelper.K(object);
    }

    private int V(Object object) {
        return this.Y.getInt(object);
    }

    private void j(Object object, boolean bl) {
        this.L.setBoolean(object, bl);
    }

    private int M(Object object) {
        return this.o.getInt(object);
    }

    public static int m(MMouseHelper mMouseHelper, Object object, int n, boolean bl) {
        return mMouseHelper.y(object, n, bl);
    }

    private void b(Object object, boolean bl) {
        this.L.setBoolean(object, bl);
    }

    public static int H(MMouseHelper mMouseHelper, Object object) {
        return mMouseHelper.q(object);
    }

    public static int N(MMouseHelper mMouseHelper, Object object) {
        return mMouseHelper.V(object);
    }

    private int Q(Object object) {
        return this.d.getInt(object);
    }

    public static int J(MMouseHelper mMouseHelper, Object object) {
        return mMouseHelper.k(object);
    }

    public static void r(MMouseHelper mMouseHelper, Object object, int n) {
        mMouseHelper.S(object, n);
    }

    private int k(Object object) {
        return this.H.getInt(object);
    }

    private int q(Object object) {
        return this.q.getInt(object);
    }

    public static int t(MMouseHelper mMouseHelper, Object object) {
        return mMouseHelper.Q(object);
    }

    private int K(Object object) {
        return this.x.getInt(object);
    }

    public static void i(MMouseHelper mMouseHelper, Object object, int n) {
        mMouseHelper.i(object, n);
    }

    private void S(Object object, int n) {
        this.x.setInt(object, n);
    }

    private int Z(Object object) {
        return this.E.getInt(object);
    }

    public static boolean H$src$Z$1w8ze45(MMouseHelper mMouseHelper, Object object) {
        return mMouseHelper.B(object);
    }

    private long N(Object object) {
        return this.f.invokeLong(object, new Object[0]);
    }

    private int y(Object object, int n, boolean bl) {
        return this.J.invokeInt(object, n, bl);
    }

    public static boolean E(MMouseHelper mMouseHelper, Object object) {
        return mMouseHelper.u(object);
    }

    private void i(Object object, int n) {
        this.d.setInt(object, n);
    }
}

