package gg.vape.ui.font;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.font.SmoothFontRendererStringCache;
import gg.vape.ui.font.SmoothFontRendererWidthCache;
import gg.vape.utils.MutableColor;
import java.awt.Color;
import java.util.Map;

public abstract class SmoothFontRenderer {
    protected Map<String, String> f;
    protected Map<String, Double> T = new SmoothFontRendererWidthCache(this, 16, 0.75f, true);
    private static GuiComponent[] K;
    protected boolean j = true;
    protected int D;
    private static final int y;

    public void v(String string, double d, double d2, int n) {
        this.I(string, d, d2, n, false);
    }

    public abstract void i(String var1, double var2, double var4, Color var6);

    protected void o(char c, float[] fArray) {
        if (c == '4') {
            fArray[0] = 1.0f;
            fArray[1] = 0.0f;
            fArray[2] = 0.0f;
        }
        if (c == 'c') {
            fArray[0] = 1.0f;
            fArray[1] = 0.33f;
            fArray[2] = 0.33f;
        }
        if (c == '6') {
            fArray[0] = 1.0f;
            fArray[1] = 0.66f;
            fArray[2] = 0.0f;
        }
        if (c == 'e') {
            fArray[0] = 1.0f;
            fArray[1] = 1.0f;
            fArray[2] = 0.33f;
        }
        if (c == '2') {
            fArray[0] = 0.0f;
            fArray[1] = 0.66f;
            fArray[2] = 0.0f;
        }
        if (c == 'a') {
            fArray[0] = 0.33f;
            fArray[1] = 1.0f;
            fArray[2] = 0.33f;
        }
        if (c == 'b') {
            fArray[0] = 0.33f;
            fArray[1] = 1.0f;
            fArray[2] = 1.0f;
        }
        if (c == '3') {
            fArray[0] = 0.0f;
            fArray[1] = 0.66f;
            fArray[2] = 0.66f;
        }
        if (c == '1') {
            fArray[0] = 0.0f;
            fArray[1] = 0.0f;
            fArray[2] = 0.66f;
        }
        if (c == '9') {
            fArray[0] = 0.33f;
            fArray[1] = 0.33f;
            fArray[2] = 1.0f;
        }
        if (c == 'd') {
            fArray[0] = 1.0f;
            fArray[1] = 0.33f;
            fArray[2] = 1.0f;
        }
        if (c == '5') {
            fArray[0] = 0.66f;
            fArray[1] = 0.0f;
            fArray[2] = 0.66f;
        }
        if (c == 'f') {
            fArray[0] = 1.0f;
            fArray[1] = 1.0f;
            fArray[2] = 1.0f;
        }
        if (c == '7') {
            fArray[0] = 0.66f;
            fArray[1] = 0.66f;
            fArray[2] = 0.66f;
        }
        if (c == '8') {
            fArray[0] = 0.33f;
            fArray[1] = 0.33f;
            fArray[2] = 0.33f;
        }
        if (c == '0') {
            fArray[0] = 0.0f;
            fArray[1] = 0.0f;
            fArray[2] = 0.0f;
        }
    }

    public void f() {
        this.T.clear();
        this.f.clear();
    }

    public void v(String string, double d, double d2, Color color) {
        this.V(string, d, d2, color, false);
    }

    public int R() {
        return this.f.size();
    }

    public void L(String string, double d, double d2, int n) {
        this.F(string, d - this.N(string) / 2.0, d2, n);
    }

    public void a(boolean bl) {
        this.j = bl;
    }

    public abstract void g(String var1, double var2, double var4, int var6);

    static {
        SmoothFontRenderer.q(new GuiComponent[1]);
        y = 5120;
    }

    public abstract void O(String var1, double var2, double var4, int var6, boolean var7);

    public double N(String string) {
        return this.Y(string, false);
    }

    public void F(String string, double d, double d2, int n) {
        this.O(string, d, d2, n, false);
    }

    public void d(String string, double d, double d2, Color color) {
        int n = color.getRGB();
        if (color instanceof MutableColor) {
            n = ((MutableColor)color).l();
        }
        this.O(string, d, d2, n, false);
    }

    public double d(String string) {
        return this.R(string, false);
    }

    public void E(String string, double d, double d2, Color color, boolean bl) {
        int n = color.getRGB();
        if (color instanceof MutableColor) {
            n = ((MutableColor)color).l();
        }
        this.O(string, d, d2, n, bl);
    }

    public static GuiComponent[] K() {
        return K;
    }

    public abstract double R(String var1, boolean var2);


    public void I(String string, double d, double d2, int n, boolean bl) {
        String string2 = this.f.get(string);
        if (string2 == null) {
            String string3 = "";
            char[] cArray = string.toCharArray();
            for (int i = 0; i < cArray.length; ++i) {
                char c = cArray[i];
                if (c == '\u00a7') {
                    ++i;
                    continue;
                }
                string3 = string3 + c;
            }
            this.f.put(string, string3);
            string2 = string3;
        }
        string = string2;
        this.O(string, d, d2, n, bl);
    }

    public int b() {
        return this.T.size();
    }

    public static void q(GuiComponent[] guiComponentArray) {
        K = guiComponentArray;
    }

    public void f(String string, double d, double d2, Color color) {
        this.L(string, d + 0.5, d2 + 0.5, Integer.MIN_VALUE);
        this.W(string, d, d2, color);
    }

    public abstract void t(String var1, double var2, double var4, Color var6, Color var7, boolean var8);

    public void W(String string, double d, double d2, Color color) {
        this.L(string, d, d2, color.getRGB());
    }

    public abstract void V(String var1, double var2, double var4, Color var6, boolean var7);

    public int M() {
        return this.D;
    }

    public void T(String string, double d, double d2, Color color, Color color2) {
        this.t(string, d, d2, color, color2, false);
    }

    public SmoothFontRenderer() {
        this.f = new SmoothFontRendererStringCache(this, 16, 0.75f, true);
    }

    public abstract double Y(String var1, boolean var2);
}

