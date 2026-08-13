package gg.vape.utils.render;

import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GlFramebuffer;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GlScissorRect;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.PotionEffectIconRenderBackend;
import gg.vape.utils.render.RenderBatchBuilder;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.VertexCoordinateMode;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Holder;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.ResourceLocation;
import gg.vape.wrapper.impl.ResourceLocationConstantPair;
import gg.vape.wrapper.impl.Screen;
import gg.vape.wrapper.impl.StatusEffectSpriteUploader;
import gg.vape.wrapper.impl.TextureAtlas;
import gg.vape.wrapper.impl.TextureAtlasSprite;
import gg.vape.wrapper.impl.TextureManager;
import gg.vape.wrapper.impl.TextureObject;
import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class PotionEffectIconTexture
implements PotionEffectIconRenderBackend {
    private GlFramebuffer framebuffer;



    @Override
    public void renderQueued(float x, float y, int width, int height, float opacity, boolean worldSpace) {
        RenderBatchBuilder batchBuilder = new RenderBatchBuilder(VertexCoordinateMode.DEFAULT, worldSpace).setTexture(new GlImageTexture(this.framebuffer.colorTextureId)).addTexturedRect(x, y, width, height, 18.0f, 18.0f, 0.0f, 1.0f, 1.0f, 0.0f, new Color(1.0f, 1.0f, 1.0f, opacity));
        if (worldSpace) {
            RenderBatchManager.getInstance().queueWorldBatch(batchBuilder);
        } else {
            RenderBatchManager.getInstance().queueGuiBatch(batchBuilder);
        }
    }

    private TextureAtlasSprite resolveEffectSprite(PotionEffect effect) {
        TextureAtlasSprite sprite;
        Holder effectHolder = effect.t();
        if (ForgeVersion.MC_1_21_10.d()) {
            TextureAtlas textureAtlas = Minecraft.x().getAtlas(ResourceLocationConstantPair.getGui());
            sprite = textureAtlas.getSprite(Screen.getMobEffectSprite(effectHolder));
        } else if (ForgeVersion.MC_1_21_6.d()) {
            sprite = Minecraft.T().getSprite(Screen.getMobEffectSprite(effectHolder));
        } else {
            StatusEffectSpriteUploader spriteUploader = StatusEffectSpriteUploader.getPotionSprites();
            sprite = spriteUploader.getSprite(effectHolder);
        }
        return sprite;
    }

    private static TextureObject getSpriteTexture(TextureAtlasSprite sprite) {
        Wrapper textureManager;
        ResourceLocation textureLocation;
        if (ForgeVersion.MC_1_20_6.d()) {
            textureLocation = sprite.getAtlasLocation();
        } else {
            Wrapper textureAtlas = new TextureAtlas(sprite.getContentsOrAtlasTexture());
            textureLocation = ((TextureAtlas)textureAtlas).getTextureLocation();
        }
        textureManager = Minecraft.getTextureManager();
        return ((TextureManager)textureManager).getTexture(textureLocation);
    }

    @Override
    public void dispose() {
        this.framebuffer.delete();
        this.framebuffer = null;
    }

    @Override
    public void render(float x, float y, int width, int height, float opacity) {
        this.renderQueued(x, y, width, height, opacity, false);
    }

    @Override
    public void capture(PotionEffect effect) {
        RenderBatchManager.getInstance().flushGuiBatches(0.0f);
        int iconWidth = 18;
        int iconHeight = 18;
        int previousFramebufferId = GL11.glGetInteger((int)36006);
        int previousTextureId = GL11.glGetInteger((int)32873);
        boolean scissorEnabled = GL11.glIsEnabled((int)3089);
        if (scissorEnabled) {
            GL11.glDisable((int)3089);
            ByteBuffer viewportBytes = ByteBuffer.allocateDirect(64);
            viewportBytes.order(ByteOrder.nativeOrder());
            IntBuffer viewport = viewportBytes.asIntBuffer();
            gg.vape.wrapper.impl.GL11.X(2978, viewport);
            this.framebuffer = new GlFramebuffer(iconWidth, iconHeight, true);
            this.framebuffer.bind(true);
            GlStateManager.enableDepth();
            GlStateManager.enableBlend();
            GL11.glClearColor((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
            GL11.glClear((int)16384);
            GL11.glClear((int)256);
            OpenGlBackendHolder.backend.pushMatrix();
            OpenGlBackendHolder.backend.translate(0.0, -2.0, 0.0);
            OpenGlBackendHolder.backend.scale((double)Minecraft.J() / (double)((float)iconWidth * 2.0f), (double)Minecraft.h() / (double)((float)iconWidth * 2.0f), 0.0);
            TextureAtlasSprite sprite = this.resolveEffectSprite(effect);
            TextureObject texture = PotionEffectIconTexture.getSpriteTexture(sprite);
            float[] textureCoordinates = sprite.getTextureCoordinates();
            GlScissorRect previousScissorRect = BufferedGuiRenderPrimitives.scissorRect;
            BufferedGuiRenderPrimitives.scissorRect = null;
            RenderBatchBuilder batchBuilder = new RenderBatchBuilder().setTexture(new GlImageTexture(texture.getId())).addTexturedRect(0.0f, -1.0f, iconWidth, iconHeight, iconWidth, iconHeight, textureCoordinates[0], textureCoordinates[2], textureCoordinates[1], textureCoordinates[3], Color.WHITE);
            RenderBatchManager batchManager = RenderBatchManager.getInstance();
            batchManager.queueGuiBatch(batchBuilder);
            batchManager.setFramebufferOverride(this.framebuffer.framebufferId);
            batchManager.flushGuiBatches(0.0f);
            batchManager.restoreFramebufferOverride();
            BufferedGuiRenderPrimitives.scissorRect = previousScissorRect;
            this.framebuffer.bindColorTexture();
            this.framebuffer.unbind();
            GL11.glViewport((int)viewport.get(0), (int)viewport.get(1), (int)viewport.get(2), (int)viewport.get(3));
            GL30.glBindFramebuffer((int)36160, (int)previousFramebufferId);
            GlStateManager.bindTexture(previousTextureId);
            GL11.glEnable((int)3089);
            OpenGlBackendHolder.backend.popMatrix();
            return;
        }
        ByteBuffer viewportBytes = ByteBuffer.allocateDirect(64);
        viewportBytes.order(ByteOrder.nativeOrder());
        IntBuffer viewport = viewportBytes.asIntBuffer();
        gg.vape.wrapper.impl.GL11.X(2978, viewport);
        this.framebuffer = new GlFramebuffer(iconWidth, iconHeight, true);
        this.framebuffer.bind(true);
        GlStateManager.enableDepth();
        GlStateManager.enableBlend();
        GL11.glClearColor((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glClear((int)16384);
        GL11.glClear((int)256);
        OpenGlBackendHolder.backend.pushMatrix();
        OpenGlBackendHolder.backend.translate(0.0, -2.0, 0.0);
        OpenGlBackendHolder.backend.scale((double)Minecraft.J() / (double)((float)iconWidth * 2.0f), (double)Minecraft.h() / (double)((float)iconWidth * 2.0f), 0.0);
        TextureAtlasSprite sprite = this.resolveEffectSprite(effect);
        TextureObject texture = PotionEffectIconTexture.getSpriteTexture(sprite);
        float[] textureCoordinates = sprite.getTextureCoordinates();
        GlScissorRect previousScissorRect = BufferedGuiRenderPrimitives.scissorRect;
        BufferedGuiRenderPrimitives.scissorRect = null;
        RenderBatchBuilder batchBuilder = new RenderBatchBuilder().setTexture(new GlImageTexture(texture.getId())).addTexturedRect(0.0f, -1.0f, iconWidth, iconHeight, iconWidth, iconHeight, textureCoordinates[0], textureCoordinates[2], textureCoordinates[1], textureCoordinates[3], Color.WHITE);
        RenderBatchManager batchManager = RenderBatchManager.getInstance();
        batchManager.queueGuiBatch(batchBuilder);
        batchManager.setFramebufferOverride(this.framebuffer.framebufferId);
        batchManager.flushGuiBatches(0.0f);
        batchManager.restoreFramebufferOverride();
        BufferedGuiRenderPrimitives.scissorRect = previousScissorRect;
        this.framebuffer.bindColorTexture();
        this.framebuffer.unbind();
        GL11.glViewport((int)viewport.get(0), (int)viewport.get(1), (int)viewport.get(2), (int)viewport.get(3));
        GL30.glBindFramebuffer((int)36160, (int)previousFramebufferId);
        GlStateManager.bindTexture(previousTextureId);
        OpenGlBackendHolder.backend.popMatrix();
    }
}
