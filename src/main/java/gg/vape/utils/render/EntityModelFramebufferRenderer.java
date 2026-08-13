package gg.vape.utils.render;

import gg.vape.mapping.MappedClasses;
import gg.vape.utils.render.EntityModelRenderBackend;
import gg.vape.utils.render.EntityModelRenderCache;
import gg.vape.utils.render.GlFramebuffer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.AbstractClientPlayer;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Render;
import gg.vape.wrapper.impl.RenderHelper;
import gg.vape.wrapper.impl.ResourceLocation;
import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class EntityModelFramebufferRenderer
implements EntityModelRenderBackend {
    GlFramebuffer framebuffer;

    @Override
    public void dispose() {
        this.framebuffer.delete();
        this.framebuffer = null;
    }

    private void captureTextureInternal(ResourceLocation texture) {
        int textureWidth = 32;
        int textureHeight = 32;
        int legacyVerticalOffset = ForgeVersion.MC_1_7_10.L() ? 32 : 0;
        int previousFramebufferId = GL11.glGetInteger((int)36006);
        int previousTextureId = GL11.glGetInteger((int)32873);
        boolean scissorEnabled = GL11.glIsEnabled((int)3089);
        boolean textureEnabled = GL11.glIsEnabled((int)3553);
        if (scissorEnabled) {
            GL11.glDisable((int)3089);
        }
        if (!textureEnabled) {
            GlStateManager.enableTexture2D();
        }
        ByteBuffer viewportBytes = ByteBuffer.allocateDirect(64);
        viewportBytes.order(ByteOrder.nativeOrder());
        IntBuffer viewport = viewportBytes.asIntBuffer();
        gg.vape.wrapper.impl.GL11.X(2978, viewport);
        OpenGlBackendHolder.backend.pushMatrix();
        this.framebuffer = new GlFramebuffer(textureWidth, textureHeight, true);
        this.framebuffer.bind(true);
        GlStateManager.enableDepth();
        GlStateManager.enableBlend();
        GL11.glEnable((int)2929);
        GlStateManager.enableBlend();
        GL11.glClearColor((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glClear((int)16384);
        GL11.glClear((int)256);
        GL11.glMatrixMode((int)5889);
        OpenGlBackendHolder.backend.pushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)32.0, (double)(32 + legacyVerticalOffset), (double)0.0, (double)-1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        OpenGlBackendHolder.backend.pushMatrix();
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
        RenderHelper.e();
        GlStateManager.disableLighting();
        GL11.glEnable((int)32826);
        GL11.glPushMatrix();
        GlStateManager.disableBlend();
        int faceWidth = 32;
        int faceHeight = 32 + legacyVerticalOffset;
        Minecraft.getTextureManager().getTexture(texture);
        Minecraft.getTextureManager().bindTexture(texture);
        RenderUtils.R(0, 0, faceWidth, faceHeight, 32, 32 + legacyVerticalOffset);
        GL11.glPopMatrix();
        GL11.glMatrixMode((int)5888);
        GL11.glPopMatrix();
        this.framebuffer.bindColorTexture();
        GL11.glMatrixMode((int)5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode((int)5888);
        RenderHelper.s();
        GL11.glDisable((int)32826);
        this.framebuffer.bindColorTexture();
        this.framebuffer.unbind();
        GL11.glPopMatrix();
        GL11.glViewport((int)viewport.get(0), (int)viewport.get(1), (int)viewport.get(2), (int)viewport.get(3));
        GL30.glBindFramebuffer((int)36160, (int)previousFramebufferId);
        GlStateManager.bindTexture(previousTextureId);
        if (scissorEnabled) {
            GL11.glEnable((int)3089);
        }
        if (!textureEnabled) {
            GlStateManager.disableTexture2D();
        }
    }

    @Override
    public void captureTexture(ResourceLocation texture) {
        this.captureTextureInternal(texture);
    }

    @Override
    public void render(float x, float y, int width, int height, Color color, float cornerRadius) {
        GL11.glEnable((int)2903);
        boolean textureEnabled = GL11.glIsEnabled((int)3553);
        boolean lightingEnabled = GL11.glIsEnabled((int)2896);
        boolean alphaTestEnabled = GL11.glIsEnabled((int)3008);
        boolean blendEnabled = GL11.glIsEnabled((int)3042);
        if (!textureEnabled) {
            GlStateManager.enableTexture2D();
        }
        if (lightingEnabled) {
            GlStateManager.disableLighting();
        }
        if (lightingEnabled) {
            GlStateManager.disableLighting();
        }
        if (!alphaTestEnabled) {
            GlStateManager.enableAlpha();
        }
        if (!blendEnabled) {
            GlStateManager.enableBlend();
        }
        int previousTextureId = GL11.glGetInteger((int)32873);
        this.framebuffer.bindColorTexture();
        RenderUtils.w(color);
        GuiRenderPrimitives.a(x, y, width, height, cornerRadius, 1.0f);
        this.framebuffer.restorePreviousTexture();
        GlStateManager.bindTexture(previousTextureId);
        if (!textureEnabled) {
            GlStateManager.disableTexture2D();
        }
        if (lightingEnabled) {
            GlStateManager.enableLighting();
        }
        if (alphaTestEnabled) {
            GlStateManager.enableAlpha();
        }
        if (blendEnabled) {
            GlStateManager.enableBlend();
        }
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
