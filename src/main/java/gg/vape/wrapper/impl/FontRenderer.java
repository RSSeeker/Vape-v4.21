package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MFontRenderer;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.ui.font.StbSmoothFontRenderer;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GlScissorRect;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderBatchBuilder;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.RenderMatrix4f;
import gg.vape.utils.render.VertexCoordinateMode;
import gg.vape.wrapper.Wrapper;

import java.awt.Color;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class FontRenderer
extends Wrapper {
    private static final long[] b;
    private static final Map e;
    private static final Integer[] d;
    private HashMap<String, Integer> H = new HashMap();

    public int B(String string, double d, double d2, int n, boolean bl, RenderMatrix4f renderMatrix4f, SharedMonsterAttributes sharedMonsterAttributes, GlScissorRect glScissorRect) {
        if (renderMatrix4f == null) {
            renderMatrix4f = new RenderMatrix4f().setIdentity();
            renderMatrix4f.multiply(BufferedGuiRenderPrimitives.viewMatrix);
            renderMatrix4f.multiply(BufferedGuiRenderPrimitives.matrixStack.peek());
        }
        if (ForgeVersion.MC_1_21_6.d()) {
            RenderItemTextBridge renderItemTextBridge = RenderItemTextBridge.l(Matrix4fHandle.b(16));
            Matrix4fHandle matrix4fHandle = renderItemTextBridge.F();
            matrix4fHandle.o();
            float[] fArray = renderMatrix4f.elements;
            matrix4fHandle.K(fArray[0]);
            matrix4fHandle.m(fArray[1]);
            matrix4fHandle.z(fArray[4]);
            matrix4fHandle.b(fArray[5]);
            matrix4fHandle.p(fArray[12]);
            matrix4fHandle.T(fArray[13]);
            if (glScissorRect != null) {
                renderItemTextBridge.a(glScissorRect.x, glScissorRect.y, glScissorRect.x + glScissorRect.width, glScissorRect.y + glScissorRect.height);
            }
            renderItemTextBridge.P(Minecraft.getFontRenderer(), string, (int)d, (int)d2, n, bl);
            if (glScissorRect != null) {
                renderItemTextBridge.k();
            }
            matrix4fHandle.N();
            return 0;
        }
        RenderItemFontBridge renderItemFontBridge = Minecraft.H$src$Lgg_vape_wrapper_impl_VoxelShape_$1dlcquv().getBufferSource();
        int n2 = this.p(string, (float)d, (float)d2, n, bl, renderMatrix4f.toMinecraftMatrix(), renderItemFontBridge, sharedMonsterAttributes, 0, 15728880);
        renderItemFontBridge.q();
        return n2;
    }

    public int getFontHeight() {
        return FontRenderer.vapeInstance.getMappings().R7.m(this.I);
    }

    public int H(MatrixStack matrixStack, String string, int n, int n2, int n3, boolean bl) {
        RenderItemTextBridge renderItemTextBridge = RenderItemTextBridge.t(matrixStack);
        return FontRenderer.vapeInstance.getMappings().R7.C(renderItemTextBridge.getObject(), this.I, string, n, n2, n3, bl);
    }

    public int i(String string, double d, double d2, Color color, MatrixStack matrixStack) {
        return this.V(string, d, d2, color.getRGB(), matrixStack);
    }

    public int V(String string, double d, double d2, int n, MatrixStack matrixStack) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return this.s(string, d, d2, n, true, matrixStack, null);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            int n2 = FontRenderer.vapeInstance.getMappings().R7.M(this.I, matrixStack.getObject(), string, (float)d, (float)d2, n);
            return n2;
        }
        if (ForgeVersion.MC_1_7_10.Y()) {
            return FontRenderer.vapeInstance.getMappings().R7.drawStringWithShadow(this.I, string, (float)d, (float)d2, n);
        }
        return FontRenderer.vapeInstance.getMappings().R7.d(this.I, string, (int)d, (int)d2, n);
    }

    public int Z(ITextComponent iTextComponent, float f, float f2, int n, boolean bl, Matrix4f matrix4f, RenderItemFontBridge renderItemFontBridge, boolean bl2, int n2, int n3) {
        return FontRenderer.vapeInstance.getMappings().R7.C(this.I, iTextComponent.getObject(), f, f2, n, bl, matrix4f.getObject(), renderItemFontBridge.getObject(), bl2, n2, n3);
    }

    public int drawCenteredString(String string, double d, double d2, int n) {
        return this.P(string, d, d2, n, ForgeVersion.MC_1_16_5.d() ? MatrixStack.A() : null);
    }

    public int drawString(String string, double d, double d2, Color color) {
        return this.Q(string, d, d2, color, ForgeVersion.MC_1_16_5.d() ? MatrixStack.A() : null);
    }

    public int getHalfFontHeight() {
        return this.getFontHeight() / 2;
    }

    private static CallSite a(MethodHandles.Lookup lookup, String string, MethodType methodType) {
        MutableCallSite mutableCallSite = new MutableCallSite(methodType);
        try {
            mutableCallSite.setTarget(MethodHandles.explicitCastArguments(MethodHandles.insertArguments(cfr_ldc_0().asCollector(Object[].class, methodType.parameterCount()), 0, lookup, mutableCallSite, string), methodType));
        }
        catch (Exception exception) {
            throw new RuntimeException("a/p9" + " : " + string + " : " + methodType.toString(), exception);
        }
        return mutableCallSite;
    }

    private static int a(int n, long l) {
        int n2 = n ^ (int)(l & 0x7FFFL) ^ 0x7257;
        if (d[n2] == null) {
            byte[] byArray;
            byte[] byArray2 = new byte[]{(byte)(l >>> 56), (byte)(l >>> 48), (byte)(l >>> 40), (byte)(l >>> 32), (byte)(l >>> 24), (byte)(l >>> 16), (byte)(l >>> 8), (byte)l};
            long l2 = b[n2];
            byte[] byArray3 = new byte[]{(byte)(l2 >>> 56), (byte)(l2 >>> 48), (byte)(l2 >>> 40), (byte)(l2 >>> 32), (byte)(l2 >>> 24), (byte)(l2 >>> 16), (byte)(l2 >>> 8), (byte)l2};
            Long l3 = Thread.currentThread().getId();
            Object[] objectArray = (Object[])e.get(l3);
            try {
                if (objectArray == null) {
                    objectArray = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    e.put(l3, objectArray);
                }
                DESKeySpec dESKeySpec = new DESKeySpec(byArray2);
                SecretKey secretKey = ((SecretKeyFactory)objectArray[1]).generateSecret(dESKeySpec);
                Cipher cipher = (Cipher)objectArray[0];
                cipher.init(2, (Key)secretKey, (IvParameterSpec)objectArray[2]);
                byArray = cipher.doFinal(byArray3);
            }
            catch (Exception exception) {
                throw new RuntimeException("a/p9", exception);
            }
            int n3 = (byArray[4] & 0xFF) << 24 | (byArray[5] & 0xFF) << 16 | (byArray[6] & 0xFF) << 8 | byArray[7] & 0xFF;
            FontRenderer.d[n2] = n3;
        }
        return d[n2];
    }

    public int p(String string, float f, float f2, int n, boolean bl, Matrix4f matrix4f, RenderItemFontBridge renderItemFontBridge, SharedMonsterAttributes sharedMonsterAttributes, int n2, int n3) {
        return MFontRenderer.i(FontRenderer.vapeInstance.getMappings().R7, this.I, string, f, f2, n, bl, matrix4f.getObject(), renderItemFontBridge.getObject(), sharedMonsterAttributes.getObject(), n2, n3);
    }

    public int A(String string, float f, float f2, int n, boolean bl, MatrixStack matrixStack, GlScissorRect glScissorRect) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.s(string, f, f2, n, bl, matrixStack, glScissorRect);
        }
        if (ForgeVersion.MC_1_7_10.Y()) {
            this.r();
        }
        return FontRenderer.vapeInstance.getMappings().R7.Y(this.I, string, f, f2, n, bl);
    }

    public int s(String string, double d, double d2, int n, MatrixStack matrixStack) {
        return this.s(string, d, d2, n, false, matrixStack, null);
    }

    public int E(String string, float f, float f2, int n, boolean bl, MatrixStack matrixStack) {
        return this.A(string, f, f2, n, bl, matrixStack, null);
    }

    public int FONT_HEIGHT(String string) {
        return this.getFontHeight();
    }

    private Void lambda$drawString3D$0(String string, double d, double d2, int n, boolean bl, RenderMatrix4f renderMatrix4f, SharedMonsterAttributes sharedMonsterAttributes) {
        RenderItemFontBridge renderItemFontBridge = Minecraft.H$src$Lgg_vape_wrapper_impl_VoxelShape_$1dlcquv().getBufferSource();
        GlStateManager.disableDepth();
        this.p(string, (float)d, (float)d2, n, bl, renderMatrix4f.toMinecraftMatrix(), renderItemFontBridge, sharedMonsterAttributes, 0, 15728880);
        renderItemFontBridge.q();
        GlStateManager.enableDepth();
        return null;
    }

    public int s(String string, double d, double d2, int n, boolean bl, MatrixStack matrixStack, GlScissorRect glScissorRect) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return this.B(string, d, d2, n, bl, matrixStack.F().getMatrix().m$src$Lgg_vape_utils_render_RenderMatrix4f_$1hodrum(), SharedMonsterAttributes.V(), glScissorRect);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            if (bl) {
                return FontRenderer.vapeInstance.getMappings().R7.M(this.I, matrixStack.getObject(), string, (float)d, (float)d2, n);
            }
            return FontRenderer.vapeInstance.getMappings().R7.S(this.I, matrixStack.getObject(), string, (float)d, (float)d2, n);
        }
        return FontRenderer.vapeInstance.getMappings().R7.u(this.I, string, (int)d, (int)d2, n);
    }

    public int getStringWidth(String string) {
        if (this.H.containsKey(string)) {
            return this.H.get(string);
        }
        int n = FontRenderer.vapeInstance.getMappings().R7.e(this.I, string);
        this.H.put(string, n);
        return n;
    }

    public int X(String string, double d, double d2, int n, double d3, MatrixStack matrixStack) {
        double d4 = 1.0 / d3;
        OpenGlBackendHolder.backend.scale(d3, d3, d3);
        int n2 = this.s(string, d * d4, d2 * d4, n, matrixStack);
        OpenGlBackendHolder.backend.scale(d4, d4, d4);
        return n2;
    }

    public int drawStringWithShadow(String string, double d, double d2, int n) {
        return this.V(string, d, d2, n, ForgeVersion.MC_1_16_5.d() ? MatrixStack.A() : null);
    }

    static {
        e = new HashMap(13);
        long[] lArray = new long[]{-3835351116449554658L, -5182186393326454707L};
        b = lArray;
        d = new Integer[2];
    }


    public int drawStringWithShadow(String string, double d, double d2, Color color) {
        return this.i(string, d, d2, color, ForgeVersion.MC_1_16_5.d() ? MatrixStack.A() : null);
    }

    public double getHalfFontHeight(String string) {
        return this.getHalfFontHeight();
    }

    public int drawString(String string, double d, double d2, int n) {
        return this.s(string, d, d2, n, ForgeVersion.MC_1_16_5.d() ? MatrixStack.A() : null);
    }

    public int drawCenteredStringWithShadow(String string, double d, double d2, Color color) {
        return this.drawCenteredStringWithShadow(string, d, d2, color.getRGB());
    }

    public int h(String string, double d, double d2, int n, boolean bl, RenderMatrix4f renderMatrix4f, SharedMonsterAttributes sharedMonsterAttributes) {
        // Font.drawInBatch was removed in 26.2, so preserve the world transform in Vape's glyph batch.
        if (ForgeVersion.MC_26_2.d()) {
            SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().p(1.0);
            if (smoothFontRenderer instanceof StbSmoothFontRenderer) {
                ((StbSmoothFontRenderer)smoothFontRenderer).c(string, d, d2, n, bl, true);
                return 0;
            }
        }
        if (renderMatrix4f == null) {
            renderMatrix4f = new RenderMatrix4f().setIdentity();
            renderMatrix4f.multiply(BufferedGuiRenderPrimitives.viewMatrix);
            renderMatrix4f.multiply(BufferedGuiRenderPrimitives.matrixStack.peek());
        }
        RenderMatrix4f renderMatrix4f2 = renderMatrix4f;
        Supplier<Void> supplier = () -> this.lambda$drawString3D$0(string, d, d2, n, bl, renderMatrix4f2, sharedMonsterAttributes);
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.MINECRAFT, true).setStandaloneRenderCallback(supplier);
        RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
        return 0;
    }

    public void J(MatrixStack matrixStack, ITextComponent iTextComponent, float f, float f2, int n) {
        MFontRenderer.q(FontRenderer.vapeInstance.getMappings().R7, this.I, matrixStack.getObject(), iTextComponent.getObject(), f, f2, n);
    }

    public int n(String string, double d, double d2, Color color, MatrixStack matrixStack) {
        return this.P(string, d, d2, color.getRGB(), matrixStack);
    }

    public int P(String string, double d, double d2, int n, MatrixStack matrixStack) {
        return this.s(string, (int)d - this.getStringWidth(string) / 2, (int)d2, n, matrixStack);
    }

    public int L(String string, double d, double d2, int n, MatrixStack matrixStack) {
        return this.V(string, d - (double)(this.getStringWidth(string) / 2), d2, n, matrixStack);
    }

    public void r() {
        FontRenderer.vapeInstance.getMappings().R7.J(this.I);
    }

    public int drawCenteredStringWithShadow(String string, double d, double d2, int n) {
        return this.L(string, d, d2, n, ForgeVersion.MC_1_16_5.d() ? MatrixStack.A() : null);
    }

    public int Q(String string, double d, double d2, Color color, MatrixStack matrixStack) {
        return this.s(string, d, d2, color.getRGB(), matrixStack);
    }

    private static int a(MethodHandles.Lookup lookup, MutableCallSite mutableCallSite, String string, Object[] objectArray) {
        int n = (Integer)objectArray[0];
        long l = (Long)objectArray[1];
        int n2 = FontRenderer.a(n, l);
        MethodHandle methodHandle = MethodHandles.constant(Integer.TYPE, n2);
        mutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, Integer.TYPE, Long.TYPE));
        return n2;
    }

    public FontRenderer(Object object) {
        super(object);
    }

    public int drawStringScaled(String string, double d, double d2, int n, double d3) {
        return this.X(string, d, d2, n, d3, ForgeVersion.MC_1_16_5.d() ? MatrixStack.A() : null);
    }

    public void U(MatrixStack matrixStack, ITextComponent iTextComponent, float f, float f2, int n) {
        MFontRenderer.A(FontRenderer.vapeInstance.getMappings().R7, this.I, matrixStack.getObject(), iTextComponent.getObject(), f, f2, n);
    }

    /*
     * Works around MethodHandle LDC.
     */
    static MethodHandle cfr_ldc_0() {
        try {
            return MethodHandles.lookup().findStatic(FontRenderer.class, "a", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)I", null));
        }
        catch (NoSuchMethodException | IllegalAccessException except) {
            throw new IllegalArgumentException(except);
        }
    }
}
