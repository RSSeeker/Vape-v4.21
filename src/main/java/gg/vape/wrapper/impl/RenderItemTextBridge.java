package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MAbstractGui;
import gg.vape.wrapper.Wrapper;

public class RenderItemTextBridge
extends Wrapper {
    public static void drawSprite(MatrixStack matrixStack, int x, int y, int blitOffset, int width, int height, TextureAtlasSprite sprite) {
        if (ForgeVersion.MC_1_21_0.d()) {
            return;
        }
        if (ForgeVersion.MC_1_17.d()) {
            ResourceLocation atlasLocation = ForgeVersion.MC_1_20_6.d() ? new SoundEventRegistryName(sprite.getContentsOrAtlasTexture()).getRegistryName() : null;
            float[] textureCoordinates = sprite.getTextureCoordinates();
            RenderItemTextBridge.drawTexturedQuad(atlasLocation, matrixStack, x, x + width, y, y + height, blitOffset, textureCoordinates[0], textureCoordinates[1], textureCoordinates[2], textureCoordinates[3]);
            return;
        }
        MAbstractGui.x(RenderItemTextBridge.vapeInstance.getMappings().qQ, null, matrixStack.getObject(), x, y, blitOffset, width, height, sprite.getObject());
    }


    public void P(FontRenderer fontRenderer, String string, int n, int n2, int n3, boolean bl) {
        RenderItemTextBridge.vapeInstance.getMappings().qQ.Q(this.I, fontRenderer.getObject(), string, n, n2, n3, bl);
    }

    public RenderItemTextBridge(Object object) {
        super(object);
    }

    public static RenderItemTextBridge b(Object object, Matrix4fHandle matrix4fHandle, GlStateManager$FogState glStateManager$FogState) {
        if (ForgeVersion.MC_1_21_11.d()) {
            return new RenderItemTextBridge(MAbstractGui.y(RenderItemTextBridge.vapeInstance.getMappings().qQ, object, matrix4fHandle.getObject(), glStateManager$FogState.getObject(), new Object[]{0, 0}));
        }
        return new RenderItemTextBridge(MAbstractGui.y(RenderItemTextBridge.vapeInstance.getMappings().qQ, object, matrix4fHandle.getObject(), glStateManager$FogState.getObject(), new Object[0]));
    }

    public static RenderItemTextBridge t(MatrixStack matrixStack) {
        if (ForgeVersion.MC_1_21_11.d()) {
            return new RenderItemTextBridge(MAbstractGui.y(RenderItemTextBridge.vapeInstance.getMappings().qQ, Minecraft.i(), Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().y().getObject(), 0, new Object[]{0}));
        }
        if (ForgeVersion.MC_1_21_6.d()) {
            if (matrixStack.getObject() == null) {
                return new RenderItemTextBridge(MAbstractGui.y(RenderItemTextBridge.vapeInstance.getMappings().qQ, Minecraft.i(), Matrix4fHandle.b(16).getObject(), Minecraft.H$src$Lgg_vape_wrapper_impl_VoxelShape_$1dlcquv().getBufferSource().getObject(), new Object[0]));
            }
            if (matrixStack.isInstance(MappedClasses.Dy)) {
                return RenderItemTextBridge.l(new Matrix4fHandle(matrixStack.getObject()));
            }
            Matrix4fHandle matrix4fHandle = Matrix4fHandle.b(16);
            matrix4fHandle.o();
            float[] fArray = matrixStack.F().getMatrix().m$src$Lgg_vape_utils_render_RenderMatrix4f_$1hodrum().elements;
            matrix4fHandle.K(fArray[0]);
            matrix4fHandle.m(fArray[1]);
            matrix4fHandle.z(fArray[4]);
            matrix4fHandle.b(fArray[5]);
            matrix4fHandle.p(fArray[12]);
            matrix4fHandle.T(fArray[13]);
            return RenderItemTextBridge.l(matrix4fHandle);
        }
        return new RenderItemTextBridge(MAbstractGui.y(RenderItemTextBridge.vapeInstance.getMappings().qQ, Minecraft.i(), matrixStack.getObject(), Minecraft.H$src$Lgg_vape_wrapper_impl_VoxelShape_$1dlcquv().getBufferSource().getObject(), new Object[0]));
    }

    public static RenderItemTextBridge l(Matrix4fHandle matrix4fHandle) {
        return RenderItemTextBridge.b(Minecraft.i(), matrix4fHandle, Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().y());
    }

    public void v() {
        RenderItemTextBridge.vapeInstance.getMappings().qQ.j(this.I);
    }

    public Matrix4fHandle F() {
        return new Matrix4fHandle(RenderItemTextBridge.vapeInstance.getMappings().qQ.B(this.I));
    }

    public static void drawTexturedQuad(ResourceLocation atlasLocation, MatrixStack matrixStack, int left, int right, int top, int bottom, int blitOffset, float u0, float u1, float v0, float v1) {
        Object graphicsHandle = null;
        if (ForgeVersion.MC_1_21_0.d()) {
            return;
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            graphicsHandle = RenderItemTextBridge.t(matrixStack).getObject();
            MAbstractGui.e(RenderItemTextBridge.vapeInstance.getMappings().qQ, graphicsHandle, atlasLocation.getObject(), left, right, top, bottom, blitOffset, u0, u1, v0, v1);
            return;
        }
        MAbstractGui.e(RenderItemTextBridge.vapeInstance.getMappings().qQ, graphicsHandle, matrixStack.F().getMatrix().getObject(), left, right, top, bottom, blitOffset, u0, u1, v0, v1);
    }

    public void k() {
        RenderItemTextBridge.vapeInstance.getMappings().qQ.J(this.I);
    }

    public GlStateManager$FogState S() {
        return new GlStateManager$FogState(RenderItemTextBridge.vapeInstance.getMappings().qQ.O(this.I));
    }

    public void a(int n, int n2, int n3, int n4) {
        RenderItemTextBridge.vapeInstance.getMappings().qQ.J(this.I, n, n2, n3, n4);
    }
}

