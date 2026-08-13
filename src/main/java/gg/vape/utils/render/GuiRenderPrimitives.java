package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.render.hud.FreeLookHudModule;
import gg.vape.runtime.NativeBridge;
import gg.vape.ui.theme.ThemeColors;
import gg.vape.unmap.GLUtils;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.BufferedRenderPrimitives;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.ItemStackRenderUtils;
import gg.vape.utils.render.LegacyFontShaderProgram;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderBatchBuilder;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.utils.render.VertexCoordinateMode;
import gg.vape.utils.render.shader.ArcShaderProgram;
import gg.vape.utils.render.shader.CircleShaderProgram;
import gg.vape.utils.render.shader.CompositeRoundedRectShaderProgram;
import gg.vape.utils.render.shader.LegacyDualRadiusRoundedRectShaderProgram;
import gg.vape.utils.render.shader.LegacyGradientRoundedRectShaderProgram;
import gg.vape.utils.render.shader.LegacyRoundedRectShaderProgram;
import gg.vape.utils.render.shader.RingShaderProgram;
import gg.vape.utils.render.shader.RoundedCornerMaskShaderProgram;
import gg.vape.utils.render.shader.RoundedRectBorderShaderProgram;
import gg.vape.utils.render.shader.RoundedRectShaderProgram;
import gg.vape.utils.render.shader.RoundedRectShadowShaderProgram;
import gg.vape.utils.render.shader.TexturedCircleShaderProgram;
import gg.vape.utils.render.shader.TexturedQuadShaderProgram;
import gg.vape.utils.render.shader.TexturedRoundedRectShaderProgram;
import gg.vape.utils.render.shader.VignetteShaderProgram;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Matrix4fHandle;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderHelper;
import gg.vape.wrapper.impl.RenderItem;
import gg.vape.wrapper.impl.RenderItemTextBridge;
import gg.vape.wrapper.impl.RenderManager;
import java.awt.Color;
import java.util.function.Supplier;
import org.lwjgl.opengl.GL11;

public class GuiRenderPrimitives {
    private static LegacyRoundedRectShaderProgram t;
    private static boolean f;
    private static LegacyGradientRoundedRectShaderProgram b;
    private static boolean s;
    private static TexturedCircleShaderProgram a;
    private static CircleShaderProgram V;
    private static RingShaderProgram B;
    private static GLUtils borderVertexBuffer;
    private static TexturedRoundedRectShaderProgram h;
    public static CompositeRoundedRectShaderProgram G;
    private static ArcShaderProgram p;
    private static boolean D;
    public static TexturedQuadShaderProgram Y;
    public static boolean U;
    private static RoundedRectShadowShaderProgram l;
    private static VignetteShaderProgram k;
    private static RoundedRectBorderShaderProgram c;
    public static LegacyFontShaderProgram i;
    private static RoundedRectShaderProgram g;
    private static GLUtils quadVertexBuffer;
    private static boolean T;
    private static boolean S;
    private static RoundedCornerMaskShaderProgram y;
    private static LegacyDualRadiusRoundedRectShaderProgram r;
    private static boolean Q;

    public static void W(double d, double d2, double d3, double d4, int n, boolean bl) {
        GlStateManager.bindTexture(n);
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        double d5 = 0.0;
        double d6 = 0.0;
        double d7 = 1.0;
        double d8 = 1.0;
        if (GuiRenderPrimitives.d()) {
            RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(VertexCoordinateMode.DEFAULT, bl).setTexture(new GlImageTexture(n)).addTexturedRect((float)d, (float)d2, (float)d3, (float)d4, (float)d3, (float)d4, (float)d5, (float)d6, (float)d7, (float)d8, Color.WHITE);
            if (bl) {
                RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
            } else {
                RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
            }
            return;
        }
        GuiRenderPrimitives.L();
        GlStateManager.enableTexture2D();
        GL11.glBegin((int)7);
        GL11.glTexCoord2d((double)d7, (double)d6);
        GL11.glVertex2d((double)(d + d3), (double)d2);
        GL11.glTexCoord2d((double)d5, (double)d6);
        GL11.glVertex2d((double)d, (double)d2);
        GL11.glTexCoord2d((double)d5, (double)d8);
        GL11.glVertex2d((double)d, (double)(d2 + d4));
        GL11.glTexCoord2d((double)d7, (double)d8);
        GL11.glVertex2d((double)(d + d3), (double)(d2 + d4));
        GL11.glEnd();
        GuiRenderPrimitives.T();
    }

    public static void Y() {
        GuiRenderPrimitives.o(Minecraft.J(), Minecraft.h());
    }

    public static void a(double d, double d2, double d3, float f, Color color) {
        GuiRenderPrimitives.u(d, d2, d + d3, d2, f, color);
    }

    public static void V(double d, double d2, double d3, double d4, Color color) {
        GuiRenderPrimitives.Y((float)d, (float)d2, (float)d3, (float)d4, color, 0.0, null);
    }

    public static void j(double d, double d2, double d3, double d4, Color color) {
        if (d3 <= 0.0 || d4 <= 0.0) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.fillCapsule((float)d, (float)d2, (float)d3, (float)d4, color);
        } else {
            if (!t.isLinked()) {
                return;
            }
            float f = Math.max((float)d4 / 2.0f - 0.5f, 0.25f);
            float f2 = (float)d2 + (float)d4 / 2.0f;
            float f3 = (float)d + (float)d4 / 2.0f;
            float f4 = (float)d + (float)d3 - (float)d4 / 2.0f;
            float f5 = 0.5f;
            float f6 = (float)d - f5;
            float f7 = (float)d2 - f5;
            float f8 = (float)d + (float)d3 + f5;
            float f9 = (float)d2 + (float)d4 + f5;
            t.bind();
            GuiRenderPrimitives.L();
            t.setStart(f3, f2);
            t.setEnd(f4, f2);
            t.setRadius(f);
            GuiRenderPrimitives.Z(color);
            quadVertexBuffer.reset();
            quadVertexBuffer.addVertex2D(f6, f7);
            quadVertexBuffer.addVertex2D(f8, f7);
            quadVertexBuffer.addVertex2D(f8, f9);
            quadVertexBuffer.addVertex2D(f6, f9);
            quadVertexBuffer.uploadAndDraw();
            GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
            GuiRenderPrimitives.T();
            t.restorePreviousProgram();
        }
    }

    public static void S(double d, double d2, double d3, double d4, int n, boolean bl) {
        GlStateManager.bindTexture(n);
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        double d5 = 0.0;
        double d6 = 1.0;
        double d7 = 1.0;
        double d8 = 0.0;
        if (GuiRenderPrimitives.d()) {
            RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(VertexCoordinateMode.DEFAULT, bl).setTexture(new GlImageTexture(n)).addItemTextureRect((float)d, (float)d2, (float)d3, (float)d4, (float)d3, (float)d4, (float)d5, (float)d6, (float)d7, (float)d8, Color.WHITE);
            if (bl) {
                RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
            } else {
                RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
            }
            return;
        }
        GuiRenderPrimitives.L();
        GlStateManager.enableTexture2D();
        GL11.glBegin((int)7);
        GL11.glTexCoord2d((double)d7, (double)d6);
        GL11.glVertex2d((double)(d + d3), (double)d2);
        GL11.glTexCoord2d((double)d5, (double)d6);
        GL11.glVertex2d((double)d, (double)d2);
        GL11.glTexCoord2d((double)d5, (double)d8);
        GL11.glVertex2d((double)d, (double)(d2 + d4));
        GL11.glTexCoord2d((double)d7, (double)d8);
        GL11.glVertex2d((double)(d + d3), (double)(d2 + d4));
        GL11.glEnd();
        GuiRenderPrimitives.T();
    }

    public static void F(double d, double d2, double d3, double d4, float[] fArray, float[] fArray2) {
        if (d3 <= 0.0 || d4 <= 0.0) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawGradientPill((float)d, (float)d2, (float)d3, (float)d4, fArray, fArray2);
        } else {
            if (!b.isLinked()) {
                return;
            }
            float f = Math.max((float)d4 / 2.0f - 0.5f, 0.25f);
            float f2 = (float)d2 + (float)d4 / 2.0f;
            float f3 = (float)d + (float)d4 / 2.0f;
            float f4 = (float)d + (float)d3 - (float)d4 / 2.0f;
            float f5 = 0.5f;
            float f6 = (float)d - f5;
            float f7 = (float)d2 - f5;
            float f8 = (float)d + (float)d3 + f5;
            float f9 = (float)d2 + (float)d4 + f5;
            b.bind();
            GuiRenderPrimitives.L();
            b.setStart(f3, f2);
            b.setEnd(f4, f2);
            b.setRadius(f);
            b.setStartHsba(fArray[0], fArray[1], fArray[2], fArray[3]);
            b.setEndHsba(fArray2[0], fArray2[1], fArray2[2], fArray2[3]);
            quadVertexBuffer.reset();
            quadVertexBuffer.addVertex2D(f6, f7);
            quadVertexBuffer.addVertex2D(f8, f7);
            quadVertexBuffer.addVertex2D(f8, f9);
            quadVertexBuffer.addVertex2D(f6, f9);
            quadVertexBuffer.uploadAndDraw();
            GuiRenderPrimitives.T();
            b.restorePreviousProgram();
        }
    }

    public static void L(double d, double d2, double d3, Color color) {
        for (double d4 = 0.0; d4 < d3; d4 += 2.0) {
            GuiRenderPrimitives.a(d + d4, d2, 1.0, 1.0f, color);
        }
    }

    public static boolean V() {
        if (!S) {
            D = MappedClasses.zb != null && MappedClasses.Y2 == null;
            S = true;
        }
        return D;
    }

    public static void o(int n, int n2) {
        if (!GuiRenderPrimitives.d()) {
            NativeBridge.rs(0, n, n2);
            GuiRenderPrimitives.prepareLegacyHudRendering();
        }
        OpenGlBackendHolder.backend.scale(2.0f, 2.0f, 2.0f);
    }

    private static void prepareLegacyHudRendering() {
        if (!ForgeVersion.MC_1_8_9.L()) {
            return;
        }
        // rs() uses raw GL, so normalize the actual state at every GUI scope.
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_LIGHTING);
    }

    static {
        quadVertexBuffer = new GLUtils();
        GuiRenderPrimitives.E(false);
        borderVertexBuffer = new GLUtils();
        U = false;
    }

    public static void z(double d, double d2, double d3, double d4, double d5, double d6, Color color) {
        GuiRenderPrimitives.a((float)d, (float)d2, (float)d3, (float)d4, (float)d5, (float)d6, color);
    }

    public static void t(double d, double d2, double d3, double d4, Color color) {
        double d5 = 0.0;
        double d6 = 0.0;
        int n = (int)((d3 + d4) * 2.0 / 2.0);
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            double d7;
            GuiRenderPrimitives.a(d + d5, d2 + d6, 1.0, 1.0f, color);
            if (n2 == 0) {
                if (!((d5 += 2.0) >= d3)) continue;
                d7 = 0.0;
                if (d5 > d3) {
                    d7 = d5 - d3;
                }
                d5 = d3;
                d6 += d7;
                n2 = 1;
                continue;
            }
            if (n2 == 1) {
                if (!((d6 += 2.0) >= d4)) continue;
                d7 = 0.0;
                if (d6 > d4) {
                    d7 = d6 - d4;
                }
                d6 = d4;
                d5 -= d7;
                n2 = 2;
                continue;
            }
            if (n2 == 2) {
                if (!((d5 -= 2.0) <= 0.0)) continue;
                d7 = 0.0;
                if (d5 < d3) {
                    d7 = 0.0 - d5;
                }
                d5 = 0.0;
                d6 -= d7;
                n2 = 3;
                continue;
            }
            if (n2 != 3 || !((d6 -= 2.0) <= 0.0)) continue;
            d7 = 0.0;
            if (d6 < d4) {
                d7 = 0.0 - d6;
            }
            d6 = 0.0;
            d5 += d7;
            n2 = 0;
        }
    }

    public static void h(String string, float f, float f2, float f3, float f4, Color color) {
        ImageRenderer.drawImage(color, f - f3 / 2.0f, f2 - f4 / 2.0f, string, f3, f4, false);
    }

    public static void a(double d, double d2, double d3, double d4, float f, float f2) {
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            return;
        }
        float f3 = 0.5f;
        if (f == 0.0f) {
            f3 = 0.0f;
        }
        float f4 = f2;
        float f5 = Math.max(0.0f, (f += f3 * 2.0f) - f4);
        if (f != 0.0f) {
            d -= (double)f4 - 0.5;
            d2 -= (double)f4;
            d4 += (double)f4 * 1.5;
            d3 += (double)(f4 * 1.0f);
        }
        h.bind();
        h.setRadius(f5);
        h.setInnerRect((float)d + f, (float)d2 + f, (float)(d + d3) - f, (float)(d2 + d4) - f);
        h.setSpread(f4);
        GuiRenderPrimitives.L();
        GlStateManager.enableTexture2D();
        float f6 = 0.0f;
        float f7 = 1.0f;
        float f8 = 1.0f;
        float f9 = 0.0f;
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)f8, (float)f7);
        GL11.glVertex2d((double)(d + d3), (double)d2);
        GL11.glTexCoord2f((float)f6, (float)f7);
        GL11.glVertex2d((double)d, (double)d2);
        GL11.glTexCoord2f((float)f6, (float)f9);
        GL11.glVertex2d((double)d, (double)(d2 + d4));
        GL11.glTexCoord2f((float)f8, (float)f9);
        GL11.glVertex2d((double)(d + d3), (double)(d2 + d4));
        GL11.glEnd();
        GuiRenderPrimitives.T();
        h.restorePreviousProgram();
    }

    public static void A(double d, double d2, double d3, double d4, Color color, float f, float f2, int n) {
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawInvertedRoundedRectCorners(d, d2, d3, d4, f, f2, n, color);
            return;
        }
        if (!y.isLinked()) {
            return;
        }
        float f3 = 0.5f;
        if (f <= 0.0f) {
            f3 = 0.0f;
        }
        float f4 = f2;
        float f5 = Math.max(0.0f, (f += f3 * 2.0f) - f4);
        if (f > 0.0f) {
            d -= (double)f4 - 0.5;
            d2 -= (double)f4;
            d4 += (double)f4 * 1.5;
            d3 += (double)(f4 * 1.0f);
        }
        y.bind();
        y.setRadius(f5);
        y.setInnerRect((float)d + f, (float)d2 + f, (float)(d + d3) - f, (float)(d2 + d4) - f);
        y.setSpread(f4);
        y.setCornerMask(n);
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D((float)d + f3, (float)d2 + f3);
        quadVertexBuffer.addVertex2D((float)(d + d3 - (double)f3), (float)d2 + f3);
        quadVertexBuffer.addVertex2D((float)(d + d3 - (double)f3), (float)(d2 + d4 - (double)f3));
        quadVertexBuffer.addVertex2D((float)d + f3, (float)(d2 + d4 - (double)f3));
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GuiRenderPrimitives.T();
        y.restorePreviousProgram();
    }

    public static void a(float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawDottedLine(f, f2, f3, f4, f5, f6, color);
            return;
        }
        if (!r.isLinked()) {
            return;
        }
        float f7 = f5 + f6;
        float f8 = Math.min(f, f3) - f7;
        float f9 = Math.max(f, f3) + f7;
        float f10 = Math.min(f2, f4) - f7;
        float f11 = Math.max(f2, f4) + f7;
        r.bind();
        GuiRenderPrimitives.L();
        r.setStart(f, f2);
        r.setEnd(f3, f4);
        r.setThickness(f5);
        r.setSpacing(f6);
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D(f8, f10);
        quadVertexBuffer.addVertex2D(f9, f10);
        quadVertexBuffer.addVertex2D(f9, f11);
        quadVertexBuffer.addVertex2D(f8, f11);
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GuiRenderPrimitives.T();
        r.restorePreviousProgram();
    }

    public static void B(double d, double d2, double d3, double d4, Color color, float f) {
        GuiRenderPrimitives.e(d, d2, d3, d4, color, false, f, 1.0f);
    }

    public static void q(double d, double d2, double d3, double d4, double d5, Color color, Color color2) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.fillBorderAdjustedRect(d, d2, d3, d4, d5, color, color2);
            return;
        }
        if (color.equals(color2)) {
            d5 = 0.0;
            GuiRenderPrimitives.C(d, d2, d3 - d5, d4 - d5, color);
            return;
        }
        GuiRenderPrimitives.C(d -= d5, d2, (d3 += d5) - d5, (d4 += d5) - d5, color);
        GuiRenderPrimitives.L();
        borderVertexBuffer.reset();
        GuiRenderPrimitives.Z(color2);
        borderVertexBuffer.addVertex2D((float)(d - d5), (float)(d2 - d5));
        borderVertexBuffer.addVertex2D((float)(d + d3), (float)(d2 - d5));
        borderVertexBuffer.addVertex2D((float)(d + d3), (float)d2);
        borderVertexBuffer.addVertex2D((float)(d - d5), (float)d2);
        borderVertexBuffer.addVertex2D((float)(d - d5), (float)(d2 + d4 - d5));
        borderVertexBuffer.addVertex2D((float)(d + d3), (float)(d2 + d4 - d5));
        borderVertexBuffer.addVertex2D((float)(d + d3), (float)(d2 + d4));
        borderVertexBuffer.addVertex2D((float)(d - d5), (float)(d2 + d4));
        borderVertexBuffer.addVertex2D((float)(d - d5), (float)d2);
        borderVertexBuffer.addVertex2D((float)d, (float)d2);
        borderVertexBuffer.addVertex2D((float)d, (float)(d2 + d4 - d5));
        borderVertexBuffer.addVertex2D((float)(d - d5), (float)(d2 + d4 - d5));
        borderVertexBuffer.addVertex2D((float)(d + d3 - d5), (float)d2);
        borderVertexBuffer.addVertex2D((float)(d + d3), (float)d2);
        borderVertexBuffer.addVertex2D((float)(d + d3), (float)(d2 + d4 - d5));
        borderVertexBuffer.addVertex2D((float)(d + d3 - d5), (float)(d2 + d4 - d5));
        borderVertexBuffer.uploadAndDraw();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GuiRenderPrimitives.T();
    }

    public static void F(double d, double d2, double d3) {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        k.bind();
        k.setOuterRadius((float)d);
        k.setInnerRadius((float)d2);
        k.setIntensity((float)d3);
        k.setResolution((float)Minecraft.J() / 2.0f, (float)Minecraft.h() / 2.0f);
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.T();
        k.restorePreviousProgram();
    }

    public static void p(float f, float f2, float f3, float f4, float f5, float f6, float f7, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawArcStroke(f, f2, f3, f4, f5, f6, f7, color);
            return;
        }
        float f8 = (f3 += f5 * 4.0f) / 2.0f;
        p.bind();
        p.setRadiusAndThickness(f8 - f4 / 2.0f, f4 / 2.0f);
        p.setCenter((f -= f5 * 2.0f) + f8, (f2 -= f5 * 2.0f) + f8);
        p.setFeather(f5);
        p.setAngleRange(f6, f7);
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D(f, f2);
        quadVertexBuffer.addVertex2D(f + f3, f2);
        quadVertexBuffer.addVertex2D(f + f3, f2 + f3);
        quadVertexBuffer.addVertex2D(f, f2 + f3);
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GuiRenderPrimitives.T();
        p.restorePreviousProgram();
    }

    public static void g(double d, double d2, double d3, double d4, float f, float f2, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawRoundedRectShadow((float)d, (float)d2, (float)d3, (float)d4, f, f2, color);
            return;
        }
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        if (!l.bind()) {
            return;
        }
        l.setInnerRect((float)d, (float)(d2 -= 1.0), (float)d3, (float)(d4 += 1.0));
        l.setSpread(f);
        l.setShadowColor(color);
        l.setCornerRadius(f2);
        GuiRenderPrimitives.L();
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D((float)d - f, (float)d2 - f);
        quadVertexBuffer.addVertex2D((float)(d + d3 + (double)f), (float)d2 - f);
        quadVertexBuffer.addVertex2D((float)(d + d3 + (double)f), (float)(d2 + d4 + (double)f));
        quadVertexBuffer.addVertex2D((float)d - f, (float)(d2 + d4 + (double)f));
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GuiRenderPrimitives.T();
        l.restorePreviousProgram();
    }

    private static void T() {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        if (U) {
            return;
        }
        GL11.glPopMatrix();
        if (T) {
            GlStateManager.enableTexture2D();
        }
        if (!Q) {
            GlStateManager.disableBlend();
        }
        if (f) {
            GlStateManager.L();
        }
    }

    public static void C(double d, double d2, double d3, double d4, Color color) {
        GuiRenderPrimitives.y((float)d, (float)d2, (float)d3, (float)d4, color);
    }

    private static Void lambda$drawItemSprite$0(double d, ItemStack itemStack, double d2, double d3) {
        RenderItemTextBridge renderItemTextBridge;
        Wrapper wrapper;
        double d4 = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        double d5 = d * d4;
        float f = (float)Minecraft.p().k(Minecraft.gameSettings().T(), false) / 2.0f;
        float f2 = 1.0f / f;
        MatrixStack matrixStack = MatrixStack.A();
        matrixStack.H();
        matrixStack.S(f2, f2, f2);
        matrixStack.s(d5, d5, d5);
        matrixStack.V(0.5, -0.5, 0.0);
        if (ForgeVersion.MC_1_21_6.d()) {
            wrapper = Matrix4fHandle.b(16);
            ((Matrix4fHandle)wrapper).o();
            ((Matrix4fHandle)wrapper).a(f2, f2);
            ((Matrix4fHandle)wrapper).j(d5, d5);
            ((Matrix4fHandle)wrapper).z(0.5, -0.5);
            renderItemTextBridge = RenderItemTextBridge.l((Matrix4fHandle)wrapper);
        } else {
            renderItemTextBridge = RenderItemTextBridge.t(matrixStack);
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            ItemStackRenderUtils.renderItemOverlay(renderItemTextBridge, itemStack, (int)(d2 / d5 * d4), (int)(d3 / d5 * d4));
        } else {
            wrapper = Minecraft.v();
            ((RenderItem)wrapper).a(itemStack, (int)(d2 * (double)f2), (int)(d3 * (double)f2), matrixStack);
        }
        return null;
    }

    public static void l(String string) {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        int n = GL11.glGetError();
    }

    public static void B() {
        if (!GuiRenderPrimitives.d()) {
            quadVertexBuffer.configureVertexBuffer(8, 7, 2);
            borderVertexBuffer.configureVertexBuffer(40, 7, 2);
            g = new RoundedRectShaderProgram();
            l = new RoundedRectShadowShaderProgram();
            V = new CircleShaderProgram();
            B = new RingShaderProgram();
            p = new ArcShaderProgram();
            c = new RoundedRectBorderShaderProgram();
            Y = new TexturedQuadShaderProgram();
            h = new TexturedRoundedRectShaderProgram();
            k = new VignetteShaderProgram();
            a = new TexturedCircleShaderProgram();
            y = new RoundedCornerMaskShaderProgram();
            G = new CompositeRoundedRectShaderProgram();
            r = new LegacyDualRadiusRoundedRectShaderProgram();
            t = new LegacyRoundedRectShaderProgram();
            b = new LegacyGradientRoundedRectShaderProgram();
            i = new LegacyFontShaderProgram();
        }
    }

    public static boolean y() {
        boolean bl = GuiRenderPrimitives.j();
        return true;
    }

    public static void I(double d, double d2, double d3, double d4, Color color, boolean bl, float f, float f2, float f3, Color color2) {
        GuiRenderPrimitives.p(d, d2, d3, d4, color, bl, f, f2, f3, color2, 15);
    }

    public static void D() {
        GuiRenderPrimitives.L(Minecraft.J(), Minecraft.h());
    }

    public static void g(ItemStack itemStack, double d, double d2, double d3, boolean bl) {
        if (GuiRenderPrimitives.d()) {
            Supplier<Void> supplier = () -> GuiRenderPrimitives.lambda$drawItemSprite$0(d, itemStack, d2, d3);
            RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(VertexCoordinateMode.MINECRAFT, false).setStandaloneRenderCallback(supplier);
            RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
            return;
        }
        GL11.glPushMatrix();
        boolean bl2 = GL11.glIsEnabled((int)2929);
        boolean bl3 = GL11.glIsEnabled((int)3042);
        if (bl2 != bl) {
            if (bl) {
                GlStateManager.enableDepth();
            } else {
                GlStateManager.disableDepth();
            }
        }
        if (!bl3) {
            GlStateManager.enableBlend();
        }
        RenderItem renderItem = Minecraft.v();
        float f = renderItem.b();
        renderItem.z(110.0f);
        RenderHelper.s();
        RenderHelper.l();
        double d4 = 1.0 / d;
        GL11.glScaled((double)d, (double)d, (double)d);
        renderItem.c(Minecraft.getFontRenderer(), Minecraft.getTextureManager(), itemStack, (int)(d2 * d4), (int)(d3 * d4));
        GL11.glScaled((double)d4, (double)d4, (double)d4);
        RenderHelper.s();
        renderItem.z(f);
        if (bl2 != bl) {
            if (bl2) {
                GlStateManager.enableDepth();
            } else {
                GlStateManager.disableDepth();
            }
        }
        if (!bl3) {
            GlStateManager.disableBlend();
        }
        GL11.glPopMatrix();
    }

    public static void d(double d, double d2, double d3, double d4, Color color) {
        GuiRenderPrimitives.e(d, d2, d3, d4, color, false, 1.5f, 1.0f);
    }

    public static void y(float f, float f2, float f3, float f4, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.fillRect(f, f2, f3, f4, color);
            return;
        }
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D(f, f2);
        quadVertexBuffer.addVertex2D(f + f3, f2);
        quadVertexBuffer.addVertex2D(f + f3, f2 + f4);
        quadVertexBuffer.addVertex2D(f, f2 + f4);
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GuiRenderPrimitives.T();
    }

    public static void v(double d, double d2, double d3, double d4, float f, float f2, float f3, int n) {
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        if (!Y.isLinked()) {
            return;
        }
        Y.bind();
        Y.setBlurRadius(f);
        Y.configureTexture((float)d3, (float)d4);
        Y.setBlurDirection(n);
        GuiRenderPrimitives.L();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        float f4 = 0.0f;
        float f5 = 1.0f;
        float f6 = 1.0f;
        float f7 = 0.0f;
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)f6, (float)f5);
        GL11.glVertex2d((double)(d + d3), (double)d2);
        GL11.glTexCoord2f((float)f4, (float)f5);
        GL11.glVertex2d((double)d, (double)d2);
        GL11.glTexCoord2f((float)f4, (float)f7);
        GL11.glVertex2d((double)d, (double)(d2 + d4));
        GL11.glTexCoord2f((float)f6, (float)f7);
        GL11.glVertex2d((double)(d + d3), (double)(d2 + d4));
        GL11.glEnd();
        GuiRenderPrimitives.T();
        Y.restorePreviousProgram();
    }

    public static void u(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.fillQuad(d, d2, d3, d4, d5, d6, d7, d8, color);
            return;
        }
        boolean bl = GL11.glIsEnabled((int)3042);
        boolean bl2 = GL11.glIsEnabled((int)3553);
        if (!bl) {
            GlStateManager.enableBlend();
        }
        if (bl2) {
            GlStateManager.disableTexture2D();
        }
        GL11.glBlendFunc((int)770, (int)771);
        OpenGlBackendHolder.backend.enableCapability(2881);
        GL11.glPushMatrix();
        GL11.glColor4d((double)((double)color.getRed() / 255.0), (double)((double)color.getGreen() / 255.0), (double)((double)color.getBlue() / 255.0), (double)((double)color.getAlpha() / 255.0));
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)d3, (double)d4);
        GL11.glVertex2d((double)d, (double)d2);
        GL11.glVertex2d((double)d7, (double)d8);
        GL11.glVertex2d((double)d5, (double)d6);
        GL11.glEnd();
        GL11.glPopMatrix();
        OpenGlBackendHolder.backend.disableCapability(2881);
        if (bl2) {
            GlStateManager.enableTexture2D();
        }
        if (!bl) {
            GlStateManager.disableBlend();
        }
    }

    public static void n(double d, double d2, double d3, double d4) {
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            float f = (float)d;
            float f2 = (float)d2 - 1.0f;
            float f3 = (float)d3;
            float f4 = (float)d4 + 1.0f;
            BufferedGuiRenderPrimitives.drawCompositeRoundedRect(f, f2, f3, f4, 12.0f, 6.0f, new Color(0, 0, 0, 150), 0.0f, 0.0f, 1.0f, new Color(45, 45, 45, 255), new Color(0, 0, 0, 170), 0.0f, 0.0f, new Color(51, 51, 51, 255), false);
            return;
        }
        float f = 12.0f;
        G.setInnerRect((float)d, (float)(d2 -= 1.0), (float)d3, (float)(d4 += 1.0));
        GuiRenderPrimitives.L();
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D((float)d - f, (float)d2 - f);
        quadVertexBuffer.addVertex2D((float)(d + d3 + (double)f), (float)d2 - f);
        quadVertexBuffer.addVertex2D((float)(d + d3 + (double)f), (float)(d2 + d4 + (double)f));
        quadVertexBuffer.addVertex2D((float)d - f, (float)(d2 + d4 + (double)f));
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GuiRenderPrimitives.T();
    }

    public static void F(String string, double d, double d2, double d3, double d4, Color color) {
        GuiRenderPrimitives.h(string, (float)d, (float)d2, (float)d3, (float)d4, color);
    }

    public static void U(double d, double d2, double d3, double d4, double d5, double d6, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.fillTriangle(d, d2, d3, d4, d5, d6, color);
            return;
        }
        GuiRenderPrimitives.L();
        GL11.glBlendFunc((int)770, (int)771);
        OpenGlBackendHolder.backend.enableCapability(2881);
        GL11.glPushMatrix();
        GL11.glColor4d((double)((double)color.getRed() / 255.0), (double)((double)color.getGreen() / 255.0), (double)((double)color.getBlue() / 255.0), (double)((double)color.getAlpha() / 255.0));
        GL11.glBegin((int)6);
        GL11.glVertex2d((double)d3, (double)d4);
        GL11.glVertex2d((double)d, (double)d2);
        GL11.glVertex2d((double)d5, (double)d6);
        GL11.glEnd();
        GL11.glPopMatrix();
        OpenGlBackendHolder.backend.disableCapability(2881);
        GuiRenderPrimitives.T();
    }

    public static void e(double d, double d2, double d3, double d4, Color color, boolean bl, float f, float f2) {
        GuiRenderPrimitives.I(d, d2, d3, d4, color, bl, f, f2, 8.0f, ThemeColors.J.u);
    }

    public static void u(double d, double d2, double d3, double d4, float f, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawLine((float)d, (float)d2, (float)d3, (float)d4, f, color);
            return;
        }
        boolean bl = GL11.glIsEnabled((int)3042);
        boolean bl2 = GL11.glIsEnabled((int)3553);
        if (!bl) {
            GlStateManager.enableBlend();
        }
        if (bl2) {
            GlStateManager.disableTexture2D();
        }
        GL11.glColor4d((double)((double)color.getRed() / 255.0), (double)((double)color.getGreen() / 255.0), (double)((double)color.getBlue() / 255.0), (double)((double)color.getAlpha() / 255.0));
        GL11.glLineWidth((float)f);
        OpenGlBackendHolder.backend.enableCapability(2848);
        GL11.glBegin((int)3);
        GL11.glVertex2d((double)d, (double)d2);
        GL11.glVertex2d((double)d3, (double)d4);
        GL11.glEnd();
        if (bl2) {
            GlStateManager.enableTexture2D();
        }
        if (!bl) {
            GlStateManager.disableBlend();
        }
    }


    public static void R(double d, double d2, double d3, float f, float f2, float f3, Color color) {
        float f4;
        if (GuiRenderPrimitives.d()) {
            BufferedRenderPrimitives.drawCylinderSides(d, d2, d3, f, f2, f3, color);
            return;
        }
        double d4 = RenderManager.getInterpolatedRenderPosX();
        double d5 = RenderManager.getInterpolatedRenderPosY();
        double d6 = RenderManager.getInterpolatedRenderPosZ();
        d -= d4;
        d2 -= d5;
        d3 -= d6;
        float f5 = f4 = FreeLookHudModule.isActive() ? FreeLookHudModule.getRenderPitch() - Minecraft.thePlayer().J() : 0.0f;
        if (!ForgeVersion.MC_1_16_5.d()) {
            OpenGlBackendHolder.backend.translate(d, d2, d3);
            OpenGlBackendHolder.backend.rotate(-f4, 0.0f, 1.0f, 0.0f);
        }
        RenderUtil.d();
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().B(1.0);
        RenderUtils.g();
        OpenGlBackendHolder.backend.enableCapability(3042);
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.disableTexture2D();
        GL11.glDepthMask((boolean)false);
        GlStateManager.Y();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)8);
        int n = 0;
        while ((float)n <= f) {
            float f6 = (float)(Math.PI * 2 * (double)n / (double)f);
            float f7 = (float)((double)f2 * Math.cos(f6));
            float f8 = (float)((double)f2 * Math.sin(f6));
            RenderUtils.w(new MutableColor(color).withAlpha(0));
            GL11.glVertex3f((float)f7, (float)f3, (float)f8);
            RenderUtils.w(color);
            GL11.glVertex3f((float)f7, (float)0.0f, (float)f8);
            ++n;
        }
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        GL11.glDepthMask((boolean)true);
        GlStateManager.enableTexture2D();
        OpenGlBackendHolder.backend.disableCapability(3042);
        RenderUtils.f();
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
        RenderUtil.Y();
        RenderUtils.w(Color.WHITE);
    }

    public static boolean j() {
        return s;
    }

    public static boolean d() {
        return ForgeVersion.MC_1_17.d();
    }

    public static void p(double d, double d2, double d3, double d4, Color color, boolean bl, float f, float f2, float f3, Color color2, int n) {
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawCornerMaskedRoundedRect((float)d, (float)d2, (float)d3, (float)d4, color, bl, f, f2, f3, color2, n);
            return;
        }
        if (!g.isLinked() || f == 0.0f) {
            GuiRenderPrimitives.C(d, d2, d3, d4, color);
            return;
        }
        if (bl) {
            GuiRenderPrimitives.g(d, d2 + 0.5, d3, d4 - 1.5, f3, f, color2);
        }
        float f4 = 0.5f;
        if (f <= 0.0f) {
            f4 = 0.0f;
        }
        float f5 = f2;
        float f6 = Math.max(0.0f, (f += f4 * 2.0f) - f5);
        if (f > 0.0f) {
            d -= (double)f5 - 0.5;
            d2 -= (double)f5;
            d4 += (double)(f5 * 1.0f);
            d3 += (double)(f5 * 1.0f);
        }
        g.bind();
        g.setRadius(f6);
        g.setInnerRect((float)d + f, (float)d2 + f, (float)(d + d3) - f, (float)(d2 + d4) - f);
        g.setSpread(f5);
        g.setCornerMask(n);
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D((float)d + f4, (float)d2 + f4);
        quadVertexBuffer.addVertex2D((float)(d + d3 - (double)f4), (float)d2 + f4);
        quadVertexBuffer.addVertex2D((float)(d + d3 - (double)f4), (float)(d2 + d4 - (double)f4));
        quadVertexBuffer.addVertex2D((float)d + f4, (float)(d2 + d4 - (double)f4));
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GuiRenderPrimitives.T();
        g.restorePreviousProgram();
    }

    public static void d(double d, double d2, double d3, float f, Color color) {
        GuiRenderPrimitives.u(d, d2, d, d2 + d3, f, color);
    }

    public static void E(boolean bl) {
        s = bl;
    }

    public static void Y(float f, float f2, float f3, float f4, Color color, double d, Color color2) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawCircleWithCenterRect(f, f2, f3, f4, color, (float)d, color2);
            return;
        }
        f -= f4 / 2.0f;
        f2 -= f4 / 2.0f;
        float f5 = f3 += f4;
        float f6 = f3;
        if (color2 != null) {
            GuiRenderPrimitives.Z(color2);
            double d2 = (double)(f + f5 / 2.0f) - d / 2.0;
            GuiRenderPrimitives.d(d2, (double)f2, d, (double)f3, color2);
        }
        V.bind();
        GuiRenderPrimitives.L();
        V.setRadius(f3 / 2.0f);
        V.setFeather(f4);
        V.setCenter(f + f5 / 2.0f, f2 + f6 / 2.0f);
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D(f, f2);
        quadVertexBuffer.addVertex2D(f + f5, f2);
        quadVertexBuffer.addVertex2D(f + f5, f2 + f6);
        quadVertexBuffer.addVertex2D(f, f2 + f6);
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GuiRenderPrimitives.T();
        V.restorePreviousProgram();
    }

    public static void n(ItemStack itemStack, double d, double d2, double d3) {
        GuiRenderPrimitives.g(itemStack, d, d2, d3, false);
    }

    public static void d(int n, double d, double d2, double d3) {
        if (n < 1) {
            return;
        }
        GuiRenderPrimitives.n(ItemStack.S(Item.T(n)), d, d2, d3);
    }

    public static void m(float f, float f2, float f3, float f4, float f5, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawCircleStroke(f, f2, f3, f4, f5, color);
            return;
        }
        f -= f5 / 2.0f;
        f2 -= f5 / 2.0f;
        float f6 = f3 += f5;
        float f7 = f3;
        B.bind();
        B.setRadiusAndThickness(f3 / 2.0f, f4);
        B.setFeather(f5);
        B.setCenter(f + f6 / 2.0f, f2 + f7 / 2.0f);
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D(f, f2);
        quadVertexBuffer.addVertex2D(f + f6, f2);
        quadVertexBuffer.addVertex2D(f + f6, f2 + f7);
        quadVertexBuffer.addVertex2D(f, f2 + f7);
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GuiRenderPrimitives.T();
        B.restorePreviousProgram();
    }

    public static void u(float f, float f2, float f3, float f4, Color color, GlImageTexture glImageTexture) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawTexturedCircle(f, f2, f3, f4, color, glImageTexture);
            return;
        }
        glImageTexture.bind();
        f -= f4 / 2.0f;
        f2 -= f4 / 2.0f;
        float f5 = f3 += f4;
        float f6 = f3;
        if (color != null) {
            GuiRenderPrimitives.Z(color);
        }
        a.bind();
        GuiRenderPrimitives.L();
        a.setRadius(f3 / 2.0f);
        a.setFeather(f4);
        a.setCenter(f + f5 / 2.0f, f2 + f6 / 2.0f);
        GlStateManager.enableTexture2D();
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = 1.0f;
        float f10 = 1.0f;
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)f9, (float)f8);
        GL11.glVertex2f((float)(f + f5), (float)f2);
        GL11.glTexCoord2f((float)f7, (float)f8);
        GL11.glVertex2f((float)f, (float)f2);
        GL11.glTexCoord2f((float)f7, (float)f10);
        GL11.glVertex2f((float)f, (float)(f2 + f6));
        GL11.glTexCoord2f((float)f9, (float)f10);
        GL11.glVertex2f((float)(f + f5), (float)(f2 + f6));
        GL11.glEnd();
        GuiRenderPrimitives.T();
        a.restorePreviousProgram();
    }

    public static void P(double d, double d2, double d3, double d4, Color color, float f, float f2, float f3) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawRoundedRect((float)d, (float)d2, (float)d3, (float)d4, color, f, f2, f3);
            return;
        }
        float f4 = 0.5f;
        float f5 = f3;
        c.bind();
        c.setInnerRect((float)d + f + f2, (float)(d2 -= (double)f5) + f + f2, (float)((d -= (double)f5 - 0.5) + (d3 += (double)(f5 * 1.0f))) - (f + f2), (float)(d2 + (d4 += (double)f5 * 1.5)) - (f + f2));
        c.setRadiusParameters(f, f3, f2);
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D((float)d + f4, (float)d2 + f4);
        quadVertexBuffer.addVertex2D((float)(d + d3 - (double)f4), (float)d2 + f4);
        quadVertexBuffer.addVertex2D((float)(d + d3 - (double)f4), (float)(d2 + d4 - (double)f4));
        quadVertexBuffer.addVertex2D((float)d + f4, (float)(d2 + d4 - (double)f4));
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GuiRenderPrimitives.T();
        c.restorePreviousProgram();
    }

    public static void L(int n, int n2) {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        NativeBridge.rs(1, n, n2);
    }

    private static void L() {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        if (U) {
            return;
        }
        f = GL11.glIsEnabled((int)2884);
        Q = GL11.glIsEnabled((int)3042);
        T = GL11.glIsEnabled((int)3553);
        if (f) {
            GlStateManager.Y();
        }
        if (!Q) {
            GlStateManager.enableBlend();
        }
        if (T) {
            GlStateManager.disableTexture2D();
        }
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glPushMatrix();
    }

    public static void Z(Color color) {
        OpenGlBackendHolder.backend.setColor((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
    }
}

