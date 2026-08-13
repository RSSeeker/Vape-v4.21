package gg.vape.ui.font;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.font.FontManager;
import gg.vape.ui.font.SmoothFontGlyph;
import gg.vape.ui.font.SmoothFontRasterState;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.ui.font.stb.StbTrueTypeFontInfo;
import gg.vape.ui.font.stb.StbTrueTypePackContext;
import gg.vape.ui.font.stb.StbTrueTypePackRange;
import gg.vape.ui.font.stb.StbTrueTypePackedChar;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class LegacySmoothFontRenderer
extends SmoothFontRenderer {
    private float F;
    private final float G = 1.0f;
    private Map<Integer, SmoothFontGlyph> v = new HashMap<Integer, SmoothFontGlyph>();
    private GlImageTexture C;
    private float w;

    @Override
    public double d(String string) {
        return this.R(string, false);
    }

    private static boolean lambda$new$0(File file) {
        return file.isFile() && file.getName().endsWith(".ttf") || file.isDirectory();
    }

    public LegacySmoothFontRenderer(String string, int n) {
        this.D = n;
        if (FontManager.y.size() == 0) {
            File[] fileArray;
            File[] localFontFiles = new File(System.getProperty("user.home") + "\\AppData\\Local\\Microsoft\\Windows\\Fonts").listFiles(LegacySmoothFontRenderer::lambda$new$0);
            if (localFontFiles != null) {
                fileArray = localFontFiles;
                int n2 = fileArray.length;
                for (int i = 0; i < n2; ++i) {
                    Object object = fileArray[i];
                    if (!((File)object).exists()) continue;
                    FontManager.y.add(((File)object).getAbsolutePath());
                }
            }
            if ((fileArray = new File(System.getenv("SystemRoot") + "\\Fonts").listFiles(LegacySmoothFontRenderer::lambda$new$1)) != null) {
                for (File file : fileArray) {
                    if (!file.exists()) continue;
                    FontManager.y.add(file.getAbsolutePath());
                }
            }
        }
        byte[] fontData = new byte[]{};
        for (String string2 : FontManager.y) {
            if (!string2.contains(string)) continue;
            try {
                Vape.debugLog("Found file attempting to read: " + string);
                File file = new File(string2);
                FileInputStream fileInputStream = new FileInputStream(file);
                fontData = new byte[(int)file.length()];
                fileInputStream.read(fontData);
                fileInputStream.close();
                break;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
                break;
            }
        }
        this.B(fontData, n, string);
    }

    @Override
    public void i(String string, double d, double d2, Color color) {
        char[] cArray = string.toCharArray();
        int n = cArray.length;
        for (int i = 0; i < n; ++i) {
            Character c = Character.valueOf(cArray[i]);
            this.f(c.toString(), d, d2, color);
            d2 += this.d(c.toString()) * 0.9;
        }
    }

    @Override
    public void T(String string, double d, double d2, Color color, Color color2) {
        this.t(string, d, d2, color, color2, false);
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
    }

    @Override
    public void O(String string, double d, double d2, int n, boolean bl) {
        if (this.j) {
            string = Vape.INSTANCE.getFontSelector().W().s(string);
        }
        if (!bl) {
            if (string == null || string.length() == 0) {
                return;
            }
            if (this.C == null) {
                return;
            }
            int n2 = (n & 0xFF0000) >> 16;
            int n3 = (n & 0xFF00) >> 8;
            int n4 = n & 0xFF;
            int n5 = (n & 0xFF000000) >> 24;
            if (n5 < 0) {
                n5 += 256;
            }
            if (n5 == 0) {
                n5 = 255;
            }
            Color color = new Color(n2, n3, n4, n5);
            char[] cArray = new char[string.length()];
            Arrays.fill(cArray, '\u0000');
            int n6 = 0;
            String string2 = "";
            char[] cArray2 = string.toCharArray();
            for (int i = 0; i < cArray2.length; ++i) {
                char c = cArray2[i];
                if (c == '\u00a7') {
                    if (++i >= cArray2.length) continue;
                    cArray[n6] = cArray2[i];
                    continue;
                }
                string2 = string2 + c;
                ++n6;
            }
            string = string2;
            double d3 = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
            float f = 0.6f;
            if (d3 != 1.0) {
                f *= (float)(1.0 / d3);
            }
            int n7 = GL11.glGetInteger((int)32873);
            boolean bl2 = GL11.glIsEnabled((int)3042);
            boolean bl3 = GL11.glIsEnabled((int)3553);
            boolean bl4 = GL11.glIsEnabled((int)2896);
            boolean bl5 = GL11.glIsEnabled((int)3008);
            boolean bl6 = GL11.glIsEnabled((int)2884);
            if (!bl2) {
                GlStateManager.enableBlend();
            }
            if (!bl3) {
                GlStateManager.enableTexture2D();
            }
            if (bl4) {
                GlStateManager.disableLighting();
            }
            if (!bl5) {
                GlStateManager.enableAlpha();
            }
            if (bl6) {
                GlStateManager.Y();
            }
            GL11.glBlendFunc((int)770, (int)771);
            GlStateManager.bindTexture(this.C.textureId);
            GuiRenderPrimitives.i.bind();
            d2 += (double)(this.F * this.w * f);
            Color color2 = color;
            GL11.glBegin((int)7);
            for (int i = 0; i < string.length(); ++i) {
                char c = string.charAt(i);
                SmoothFontGlyph smoothFontGlyph = this.v.get((int)c);
                if (smoothFontGlyph == null) {
                    smoothFontGlyph = this.v.get(32);
                }
                if (smoothFontGlyph == null) continue;
                char c2 = cArray[i];
                if (c2 != '\u0000') {
                    float[] fArray = new float[3];
                    this.o(c2, fArray);
                    color2 = new Color(fArray[0], fArray[1], fArray[2]);
                    if (c2 == 'r') {
                        color2 = color;
                    }
                }
                float f2 = (float)d + smoothFontGlyph.X * f;
                float f3 = (float)d2 + smoothFontGlyph.a * f;
                float f4 = (float)d + smoothFontGlyph.g * f;
                float f5 = (float)d2 + smoothFontGlyph.G * f;
                float f6 = smoothFontGlyph.t;
                float f7 = smoothFontGlyph.J;
                float f8 = smoothFontGlyph.w;
                float f9 = smoothFontGlyph.B;
                GL11.glColor4f((float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
                GL11.glTexCoord2f((float)f6, (float)f7);
                GL11.glVertex2f((float)f2, (float)f3);
                GL11.glTexCoord2f((float)f6, (float)f9);
                GL11.glVertex2f((float)f2, (float)f5);
                GL11.glTexCoord2f((float)f8, (float)f9);
                GL11.glVertex2f((float)f4, (float)f5);
                GL11.glTexCoord2f((float)f8, (float)f7);
                GL11.glVertex2f((float)f4, (float)f3);
                d += (double)(smoothFontGlyph.S * f);
            }
            GL11.glEnd();
            GuiRenderPrimitives.i.restorePreviousProgram();
            GlStateManager.bindTexture(n7);
            if (!bl5) {
                GlStateManager.disableAlpha();
            }
            if (bl4) {
                GlStateManager.enableLighting();
            }
            if (!bl2) {
                GlStateManager.disableBlend();
            }
            if (!bl3) {
                GlStateManager.disableTexture2D();
            }
            if (bl6) {
                GlStateManager.L();
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        } else {
            GuiRenderPrimitives.Z(new Color(n));
            float f = (float)this.D / 16.0f;
            float f10 = 1.0f / f;
            d = Math.ceil(d);
            d2 = Math.ceil(d2);
            d *= (double)f10;
            d2 *= (double)f10;
            GL11.glPushMatrix();
            GL11.glScalef((float)f, (float)f, (float)f);
            if (ForgeVersion.MC_1_16_5.d()) {
                MatrixStack matrixStack = MatrixStack.A();
                Minecraft.getFontRenderer().E(string, (float)d, (float)d2, n, false, matrixStack);
            } else {
                Minecraft.getFontRenderer().E(string, (float)d, (float)d2, n, false, new MatrixStack(null));
            }
            GL11.glPopMatrix();
        }
    }

    private static boolean lambda$new$1(File file) {
        return file.isFile() && file.getName().endsWith(".ttf") || file.isDirectory();
    }

    @Override
    public void t(String string, double d, double d2, Color color, Color color2, boolean bl) {
        int n = color.getRGB();
        if (color instanceof MutableColor) {
            n = ((MutableColor)color).l();
        }
        int n2 = color2.getRGB();
        if (color2 instanceof MutableColor) {
            n2 = ((MutableColor)color2).l();
        }
        if (!bl) {
            this.v(string, d + 0.5, d2 + 0.5, n2);
            this.d(string, d, d2, color);
        } else {
            if (this.j) {
                string = Vape.INSTANCE.getFontSelector().W().s(string);
            }
            float f = (float)this.D / 16.0f;
            float f2 = 1.0f / f;
            d = Math.ceil(d);
            d2 = Math.ceil(d2);
            d *= (double)f2;
            d2 *= (double)f2;
            GL11.glPushMatrix();
            GL11.glScalef((float)f, (float)f, (float)f);
            if (ForgeVersion.MC_1_16_5.d()) {
                MatrixStack matrixStack = MatrixStack.A();
                Minecraft.getFontRenderer().E(string, (float)d, (float)d2, n, true, matrixStack);
            } else {
                Minecraft.getFontRenderer().drawStringWithShadow(string, d, d2, n);
            }
            GL11.glPopMatrix();
        }
    }

    public LegacySmoothFontRenderer(byte[] byArray, int n, String string) {
        this.B(byArray, n, string);
    }

    private void B(byte[] byArray, int n, String string) {
        if (string.contains("Proxima")) {
            --n;
        }
        HashSet<Integer> hashSet = new HashSet<Integer>();
        for (int i = 32; i <= 126; ++i) {
            hashSet.add(i);
        }
        hashSet.add(0);
        byte[] byArray2 = Vape.readResource("chinese.properties.txt");
        if (byArray2 != null) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray2);
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)byteArrayInputStream, StandardCharsets.UTF_8))) {
                    int n2;
                    while ((n2 = bufferedReader.read()) != -1) {
                        if (n2 < 32) continue;
                        hashSet.add(n2);
                    }
                }
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        int[] nArray = hashSet.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(nArray);
        this.D = n;
        int n3 = 4096;
        StbTrueTypeFontInfo stbTrueTypeFontInfo = new StbTrueTypeFontInfo();
        if (SmoothFontRasterState.z(stbTrueTypeFontInfo, byArray, 0) == 0) {
            Vape.debugLog("Failed to init font information");
            return;
        }
        int[] nArray2 = new int[1];
        int[] nArray3 = new int[1];
        int[] nArray4 = new int[1];
        SmoothFontRasterState.x(stbTrueTypeFontInfo, nArray2, nArray3, nArray4);
        this.F = nArray2[0];
        int[] nArray5 = new int[1];
        int[] nArray6 = new int[1];
        int[] nArray7 = new int[1];
        int[] nArray8 = new int[1];
        SmoothFontRasterState.s(stbTrueTypeFontInfo, 72, nArray5, nArray6, nArray7, nArray8);
        float f = nArray8[0] - nArray6[0];
        float f2 = nArray2[0] - nArray3[0];
        float f3 = f / f2;
        float f4 = 0.6f;
        float f5 = (float)this.D * (f4 / f3);
        byte[] byArray3 = new byte[n3 * n3];
        StbTrueTypePackContext stbTrueTypePackContext = new StbTrueTypePackContext();
        if (SmoothFontRasterState.C(stbTrueTypePackContext, byArray3, n3, n3, 0, 1) == 0) {
            Vape.debugLog("Failed to begin packing");
            return;
        }
        int n4 = 2;
        int n5 = 2;
        SmoothFontRasterState.n(stbTrueTypePackContext, n5, n4);
        try {
            StbTrueTypePackedChar[] stbTrueTypePackedCharArray = new StbTrueTypePackedChar[nArray.length];
            for (int i = 0; i < stbTrueTypePackedCharArray.length; ++i) {
                stbTrueTypePackedCharArray[i] = new StbTrueTypePackedChar();
            }
            StbTrueTypePackRange stbTrueTypePackRange = new StbTrueTypePackRange();
            stbTrueTypePackRange.E = f5;
            stbTrueTypePackRange.R = 0;
            stbTrueTypePackRange.N = nArray;
            stbTrueTypePackRange.b = nArray.length;
            stbTrueTypePackRange.M = stbTrueTypePackedCharArray;
            stbTrueTypePackRange.i = (byte)n5;
            stbTrueTypePackRange.y = (byte)n4;
            StbTrueTypePackRange[] stbTrueTypePackRangeArray = new StbTrueTypePackRange[]{stbTrueTypePackRange};
            SmoothFontRasterState.c(stbTrueTypePackContext, byArray, 0, stbTrueTypePackRangeArray, stbTrueTypePackRangeArray.length);
            SmoothFontRasterState.S(stbTrueTypePackContext);
            this.w = SmoothFontRasterState.V(stbTrueTypeFontInfo, f5);
            for (int i = 0; i < nArray.length; ++i) {
                int n6 = nArray[i];
                StbTrueTypePackedChar stbTrueTypePackedChar = stbTrueTypePackedCharArray[i];
                f4 = 1.0f / (float)n3;
                float f6 = 1.0f / (float)n3;
                this.v.put(n6, new SmoothFontGlyph(stbTrueTypePackedChar.s, stbTrueTypePackedChar.G, stbTrueTypePackedChar.t, stbTrueTypePackedChar.R, (float)stbTrueTypePackedChar.b * f4, (float)stbTrueTypePackedChar.k * f6, (float)stbTrueTypePackedChar.q * f4, (float)stbTrueTypePackedChar.p * f6, stbTrueTypePackedChar.A));
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
        // Intel's OpenGL upload path may vector-read past an exact page-aligned buffer.
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer((int)byArray3.length + 128);
        byteBuffer.put(byArray3);
        byteBuffer.flip();
        int n6 = GlStateManager.p();
        this.C = new GlImageTexture();
        GlStateManager.bindTexture(this.C.textureId);
        GL11.glPixelStorei((int)3317, (int)1);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10242, (int)10497);
        GL11.glTexParameteri((int)3553, (int)10243, (int)10497);
        GL11.glTexImage2D((int)3553, (int)0, (int)6403, (int)n3, (int)n3, (int)0, (int)6403, (int)5121, byteBuffer);
        this.C.width = n3;
        this.C.height = n3;
        GlStateManager.bindTexture(n6);
    }

    @Override
    public double Y(String string, boolean bl) {
        String string2;
        string = Vape.INSTANCE.getFontSelector().W().s(string);
        double d = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        String string3 = (String)this.f.get(string);
        if (string3 == null) {
            string2 = "";
            char[] charArray = string.toCharArray();
            for (int i = 0; i < charArray.length; ++i) {
                char c = charArray[i];
                if (c == '\u00a7') {
                    ++i;
                    continue;
                }
                string2 = string2 + c;
            }
            this.f.put(string, string2);
            string3 = string2;
        }
        string = string3;
        if (!bl) {
            string2 = string;
            Double cachedWidth = (Double)this.T.get(string2);
            if (cachedWidth != null) {
                return cachedWidth;
            }
            float f = 0.6f;
            if (d != 1.0) {
                f *= (float)(1.0 / d);
            }
            double d2 = 0.0;
            for (int i = 0; i < string.length(); ++i) {
                char c = string.charAt(i);
                SmoothFontGlyph smoothFontGlyph = this.v.get((int)c);
                if (smoothFontGlyph == null) {
                    smoothFontGlyph = this.v.get(32);
                }
                if (smoothFontGlyph == null) continue;
                d2 += (double)(smoothFontGlyph.S * f);
            }
            this.T.put(string2, d2);
            return d2;
        }
        float f = (float)this.D / 16.0f;
        int n = (int)((float)Minecraft.getFontRenderer().getStringWidth(string) * f);
        return n;
    }

    @Override
    public void g(String string, double d, double d2, int n) {
        this.v(string, d + 0.5, d2 + 0.5, Integer.MIN_VALUE);
        this.F(string, d, d2, n);
    }

    @Override
    public void V(String string, double d, double d2, Color color, boolean bl) {
        if (!bl) {
            this.v(string, d + 0.5, d2 + 0.5, Integer.MIN_VALUE);
            this.d(string, d, d2, color);
        } else {
            float f = (float)this.D / 16.0f;
            float f2 = 1.0f / f;
            d = Math.ceil(d);
            d2 = Math.ceil(d2);
            GL11.glPushMatrix();
            GL11.glScalef((float)f, (float)f, (float)f);
            Minecraft.getFontRenderer().drawStringWithShadow(string, d *= (double)f2, d2 *= (double)f2, color);
            GL11.glPopMatrix();
        }
    }

    @Override
    public double R(String string, boolean bl) {
        if (!bl) {
            float f = 0.6f;
            double d = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
            if (d != 1.0) {
                f *= (float)(1.0 / d);
            }
            return (float)this.D * f;
        }
        float f = (float)this.D / 16.0f;
        return 8.0f * f;
    }
}
