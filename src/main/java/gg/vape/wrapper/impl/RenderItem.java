package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderItem;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.wrapper.Wrapper;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class RenderItem
extends Wrapper {
    private static final Integer[] d;
    private static final Map e;
    private static Object h;
    private static final long[] b;

    public static RenderItem d() {
        if (h == null && MRenderItem.V(RenderItem.vapeInstance.getMappings().DI) == null && ForgeVersion.MC_1_7_10.L() && isNativeAvailable) {
            h = MRenderItem.F(RenderItem.vapeInstance.getMappings().DI).newInstance(new Object[0]);
        }
        return new RenderItem(h != null ? h : MRenderItem.a(RenderItem.vapeInstance.getMappings().DI));
    }

    public void z(float f) {
        MRenderItem.l(RenderItem.vapeInstance.getMappings().DI, this.I, f);
    }

    private static int a(MethodHandles.Lookup lookup, MutableCallSite mutableCallSite, String string, Object[] objectArray) {
        int n = (Integer)objectArray[0];
        long l = (Long)objectArray[1];
        int n2 = RenderItem.a(n, l);
        MethodHandle methodHandle = MethodHandles.constant(Integer.TYPE, n2);
        mutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, Integer.TYPE, Long.TYPE));
        return n2;
    }


    public RenderItem(Object object) {
        super(object);
    }

    public void a(ItemStack itemStack, int n, int n2, MatrixStack matrixStack) {
        boolean bl;
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        Channel bakedModel = this.getBakedModel(itemStack, null, entityPlayerSP.getObject(), 0);
        this.e(this.T() + 50.0f);
        TextureManager textureManager = Minecraft.getTextureManager();
        textureManager.getTexture(TextureAtlas.getBlocksAtlasLocation()).setFilter(false, false);
        RenderSystem.k(0, textureManager.getTexture(TextureAtlas.getBlocksAtlasLocation()).getId());
        RenderSystem.x();
        RenderSystem.s(770, 771);
        RenderSystem.U(1.0f, 1.0f, 1.0f, 1.0f);
        MatrixStack matrixStack2 = RenderSystem.p();
        matrixStack2.H();
        matrixStack2.V(n, n2, 100.0f + this.T());
        matrixStack2.S(1.0f, -1.0f, 1.0f);
        matrixStack2.S(16.0f, 16.0f, 16.0f);
        RenderSystem.v();
        RenderItemFontBridge renderItemFontBridge = Minecraft.H$src$Lgg_vape_wrapper_impl_VoxelShape_$1dlcquv().getBufferSource();
        boolean bl2 = bl = !bakedModel.usesBlockLight();
        if (bl) {
            RenderHelper.l();
            this.I(itemStack, RenderItemContext.gui(), false, matrixStack, renderItemFontBridge, 15728880, 0, bakedModel);
            renderItemFontBridge.X();
            RenderSystem.n();
            RenderHelper.e();
            matrixStack2 = RenderSystem.p();
            matrixStack2.U();
            RenderSystem.v();
            this.e(this.T() - 50.0f);
            return;
        }
        this.I(itemStack, RenderItemContext.gui(), false, matrixStack, renderItemFontBridge, 15728880, 0, bakedModel);
        renderItemFontBridge.X();
        RenderSystem.n();
        matrixStack2 = RenderSystem.p();
        matrixStack2.U();
        RenderSystem.v();
        this.e(this.T() - 50.0f);
    }

    public float b() {
        return MRenderItem.I(RenderItem.vapeInstance.getMappings().DI, this.I);
    }

    static {
        e = new HashMap(13);
        long[] lArray = new long[]{6381959226204642166L, 4564068455244414596L};
        b = lArray;
        d = new Integer[2];
    }

    public void I(ItemStack itemStack, RenderItemContext renderItemContext, boolean bl, MatrixStack matrixStack, RenderItemFontBridge renderItemFontBridge, int n, int n2, Channel bakedModel) {
        MRenderItem.x(RenderItem.vapeInstance.getMappings().DI, this.I, itemStack.getObject(), renderItemContext.getObject(), bl, matrixStack.getObject(), renderItemFontBridge.getObject(), n, n2, bakedModel.getObject());
    }

    public void e(float f) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return;
        }
        MRenderItem.G(RenderItem.vapeInstance.getMappings().DI, this.I, f);
    }

    public Channel getBakedModel(ItemStack itemStack, World world, Object entityHandle, int seed) {
        Object worldHandle = world == null ? null : world.getObject();
        return new Channel(MRenderItem.A(RenderItem.vapeInstance.getMappings().DI, this.I, itemStack.getObject(), worldHandle, entityHandle, seed));
    }

    public void c(FontRenderer fontRenderer, TextureManager textureManager, ItemStack itemStack, int n, int n2) {
        if (ForgeVersion.MC_1_7_10.L()) {
            MRenderItem.z(RenderItem.vapeInstance.getMappings().DI, this.I, fontRenderer.getObject(), textureManager.getObject(), itemStack.getObject(), n, n2);
        } else if (ForgeVersion.MC_1_20_6.d()) {
            MatrixStack matrixStack = MatrixStack.A();
            matrixStack.H();
            matrixStack.i(BufferedGuiRenderPrimitives.matrixStack.peek().toMinecraftMatrix());
            this.o(RenderItemTextBridge.t(matrixStack), itemStack, n, n2);
        } else {
            RenderItem.vapeInstance.getMappings().DI.M(this.I, itemStack.getObject(), n, n2);
        }
    }

    private static int a(int n, long l) {
        int n2 = n ^ (int)(l & 0x7FFFL) ^ 0x695D;
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
                throw new RuntimeException("a/w_", exception);
            }
            int n3 = (byArray[4] & 0xFF) << 24 | (byArray[5] & 0xFF) << 16 | (byArray[6] & 0xFF) << 8 | byArray[7] & 0xFF;
            RenderItem.d[n2] = n3;
        }
        return d[n2];
    }

    private static CallSite a(MethodHandles.Lookup lookup, String string, MethodType methodType) {
        MutableCallSite mutableCallSite = new MutableCallSite(methodType);
        try {
            mutableCallSite.setTarget(MethodHandles.explicitCastArguments(MethodHandles.insertArguments(cfr_ldc_0().asCollector(Object[].class, methodType.parameterCount()), 0, lookup, mutableCallSite, string), methodType));
        }
        catch (Exception exception) {
            throw new RuntimeException("a/w_" + " : " + string + " : " + methodType.toString(), exception);
        }
        return mutableCallSite;
    }

    public float T() {
        if (ForgeVersion.MC_1_20_6.d()) {
            return 100.0f;
        }
        return MRenderItem.x(RenderItem.vapeInstance.getMappings().DI, this.I);
    }

    public void o(RenderItemTextBridge renderItemTextBridge, ItemStack itemStack, int n, int n2) {
        RenderItem.vapeInstance.getMappings().DI.M(renderItemTextBridge.getObject(), itemStack.getObject(), n, n2);
    }

    public void P(FontRenderer fontRenderer, TextureManager textureManager, ItemStack itemStack, int n, int n2) {
        if (ForgeVersion.MC_1_7_10.L()) {
            MRenderItem.P(RenderItem.vapeInstance.getMappings().DI, this.I, fontRenderer.getObject(), textureManager.getObject(), itemStack.getObject(), n, n2);
        } else {
            MRenderItem.Y(RenderItem.vapeInstance.getMappings().DI, this.I, fontRenderer.getObject(), itemStack.getObject(), n, n2);
        }
    }

    /*
     * Works around MethodHandle LDC.
     */
    static MethodHandle cfr_ldc_0() {
        try {
            return MethodHandles.lookup().findStatic(RenderItem.class, "a", MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)I", null));
        }
        catch (NoSuchMethodException | IllegalAccessException except) {
            throw new IllegalArgumentException(except);
        }
    }
}
