package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.EntityModelRenderBackend;
import gg.vape.utils.render.EntityModelRenderCache;
import gg.vape.utils.render.GlFramebuffer;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.RenderBatch;
import gg.vape.utils.render.RenderBatchBuffer;
import gg.vape.utils.render.RenderBatchBuilder;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.RenderMatrix4f;
import gg.vape.utils.render.RenderMatrixStack;
import gg.vape.wrapper.impl.AbstractClientPlayer;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Render;
import gg.vape.wrapper.impl.ResourceLocation;
import gg.vape.wrapper.impl.TextureManager;
import gg.vape.wrapper.impl.TextureObject;
import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Post117EntityModelFramebufferRenderer
implements EntityModelRenderBackend {
    private static final String CAPTURE_ERROR_PREFIX = "[CachedFace] Exception during capture: ";
    GlFramebuffer framebuffer;
    private boolean fallbackAttempted = false;

    private void captureDefaultTextureFallback() {
        if (this.framebuffer != null && this.framebuffer.colorTextureId > 0 || this.fallbackAttempted) {
            return;
        }
        this.fallbackAttempted = true;
        this.captureTextureInternal(EntityModelRenderCache.getDefaultSkinTexture());
    }

    private void captureTextureInternal(ResourceLocation texture) {
        int textureWidth = 32;
        int textureHeight = 32;
        try {
            RenderBatchManager batchManager = RenderBatchManager.getInstance();
            batchManager.flushGuiBatches(0.0f);
            int previousVertexArrayId = GL11.glGetInteger((int)34229);
            int previousShaderProgramId = GL11.glGetInteger((int)35725);
            int previousTextureId = GL11.glGetInteger((int)32873);
            int previousFramebufferId = GL11.glGetInteger((int)36006);
            int previousArrayBufferId = GL11.glGetInteger((int)34964);
            int previousElementArrayBufferId = GL11.glGetInteger((int)34965);
            boolean scissorEnabled = GL11.glIsEnabled((int)3089);
            if (scissorEnabled) {
                GL11.glDisable((int)3089);
            }
            ByteBuffer viewportBytes = ByteBuffer.allocateDirect(64);
            viewportBytes.order(ByteOrder.nativeOrder());
            IntBuffer viewport = viewportBytes.asIntBuffer();
            gg.vape.wrapper.impl.GL11.X(2978, viewport);
            this.framebuffer = new GlFramebuffer(textureWidth, textureHeight, true);
            this.framebuffer.bind(true);
            GlStateManager.enableBlend();
            GlStateManager.Y(770, 771);
            GL11.glClearColor((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
            GL11.glClear((int)16640);
            TextureManager textureManager = Minecraft.getTextureManager();
            TextureObject textureObject = textureManager.getTexture(texture);
            if (textureObject == null || textureObject.isNull() || textureObject.getId() <= 0) {
                this.framebuffer.unbind();
                GL11.glViewport((int)viewport.get(0), (int)viewport.get(1), (int)viewport.get(2), (int)viewport.get(3));
                GL30.glBindFramebuffer((int)36160, (int)previousFramebufferId);
                GlStateManager.bindTexture(previousTextureId);
                if (scissorEnabled) {
                    GL11.glEnable((int)3089);
                }
                this.captureDefaultTextureFallback();
                return;
            }
            float textureScaleU = 0.00390625f;
            float textureScaleV = 0.00390625f;
            RenderBatchBuilder batchBuilder = new RenderBatchBuilder().setTexture(new GlImageTexture(textureObject.getId())).addTexturedRect(0.0f, 0.0f, 32.0f, 32.0f, textureWidth, textureHeight, 32.0f * textureScaleU, (float)(32 + textureHeight) * textureScaleV, (float)(32 + textureWidth) * textureScaleU, 32.0f * textureScaleV, Color.WHITE);
            RenderMatrix4f previousProjectionMatrix = BufferedGuiRenderPrimitives.projectionMatrix;
            RenderMatrix4f previousViewMatrix = BufferedGuiRenderPrimitives.viewMatrix;
            RenderMatrixStack previousMatrixStack = BufferedGuiRenderPrimitives.matrixStack;
            BufferedGuiRenderPrimitives.projectionMatrix = new RenderMatrix4f().setIdentity().setOrthographic(0.0f, textureWidth, textureHeight, 0.0f, -21000.0f, 21000.0f);
            BufferedGuiRenderPrimitives.viewMatrix = new RenderMatrix4f().setIdentity();
            BufferedGuiRenderPrimitives.matrixStack = new RenderMatrixStack();
            int textureId = textureObject.getId();
            GL11.glBindTexture((int)3553, (int)textureId);
            int previousMinFilter = GL11.glGetTexParameteri((int)3553, (int)10241);
            int previousMagFilter = GL11.glGetTexParameteri((int)3553, (int)10240);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
            RenderBatchBuffer batchBuffer = batchManager.getBatchBuffer();
            RenderBatch batch = new RenderBatch(batchBuilder);
            batchBuffer.bindResources();
            batchBuffer.stageBatch(batch);
            batchBuffer.draw();
            GL30.glBindVertexArray((int)0);
            GL11.glBindTexture((int)3553, (int)textureId);
            GL11.glTexParameteri((int)3553, (int)10241, (int)previousMinFilter);
            GL11.glTexParameteri((int)3553, (int)10240, (int)previousMagFilter);
            BufferedGuiRenderPrimitives.projectionMatrix = previousProjectionMatrix;
            BufferedGuiRenderPrimitives.viewMatrix = previousViewMatrix;
            BufferedGuiRenderPrimitives.matrixStack = previousMatrixStack;
            this.framebuffer.unbind();
            GL11.glViewport((int)viewport.get(0), (int)viewport.get(1), (int)viewport.get(2), (int)viewport.get(3));
            GL30.glBindVertexArray((int)previousVertexArrayId);
            GL20.glUseProgram((int)previousShaderProgramId);
            GL11.glBindTexture((int)3553, (int)previousTextureId);
            GL30.glBindFramebuffer((int)36160, (int)previousFramebufferId);
            GL15.glBindBuffer((int)34962, (int)previousArrayBufferId);
            GL15.glBindBuffer((int)34963, (int)previousElementArrayBufferId);
            if (scissorEnabled) {
                GL11.glEnable((int)3089);
            }
        }
        catch (Exception exception) {
            Vape.debugLog(CAPTURE_ERROR_PREFIX + exception.getMessage());
            Vape.logThrowable(exception);
        }
    }

    @Override
    public void render(float x, float y, int width, int height, Color color, float cornerRadius) {
        if (this.framebuffer == null || this.framebuffer.colorTextureId <= 0) {
            return;
        }
        BufferedGuiRenderPrimitives.drawRoundedTexturedRect(x, y, (float)width, (float)height, cornerRadius, 1.0f, color, new GlImageTexture(this.framebuffer.colorTextureId));
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    @Override
    public void captureTexture(ResourceLocation texture) {
        this.captureTextureInternal(texture);
    }

    @Override
    public void dispose() {
        this.framebuffer.delete();
        this.framebuffer = null;
    }

    @Override
    public void captureEntity(EntityLivingBase entity) {
        ResourceLocation texture = EntityModelRenderCache.getDefaultSkinTexture();
        if (entity != null) {
            if (entity.isInstance(MappedClasses.zt)) {
                AbstractClientPlayer clientPlayer = new AbstractClientPlayer(entity);
                texture = clientPlayer.O();
            } else {
                Render entityRenderer = Minecraft.D().getEntityRenderObject(entity);
                texture = entityRenderer.getEntityTexture(entity);
            }
        }
        this.captureTextureInternal(texture);
    }
}
