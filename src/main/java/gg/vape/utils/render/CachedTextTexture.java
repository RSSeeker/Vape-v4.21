package gg.vape.utils.render;

import gg.vape.utils.render.GlFramebuffer;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

class CachedTextTexture {
    GlFramebuffer framebuffer;



    void render(float x, float y, int width, int height) {
        OpenGlBackendHolder.backend.enableCapability(2903);
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
        int previousFramebufferId = GL11.glGetInteger((int)36006);
        int previousTextureId = GL11.glGetInteger((int)32873);
        this.framebuffer.bindColorTexture();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPushMatrix();
        float minU = 0.0f;
        float maxV = 1.0f;
        float maxU = 1.0f;
        float minV = 0.0f;
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)maxU, (float)maxV);
        GL11.glVertex2f((float)(x + (float)width), (float)y);
        GL11.glTexCoord2f((float)minU, (float)maxV);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glTexCoord2f((float)minU, (float)minV);
        GL11.glVertex2f((float)x, (float)(y + (float)height));
        GL11.glTexCoord2f((float)maxU, (float)minV);
        GL11.glVertex2f((float)(x + (float)width), (float)(y + (float)height));
        GL11.glEnd();
        GL11.glPopMatrix();
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

    CachedTextTexture() {
    }

    void build(String text, int color) {
        int textWidth = Minecraft.getFontRenderer().getStringWidth(text);
        int textHeight = Minecraft.getFontRenderer().FONT_HEIGHT(text);
        FloatBuffer previousProjectionMatrix = BufferUtils.createFloatBuffer((int)16);
        GL11.glGetFloat((int)2983, (FloatBuffer)previousProjectionMatrix);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glPushMatrix();
        GL11.glOrtho((double)0.0, (double)textWidth, (double)textHeight, (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
        int previousTextureId = GL11.glGetInteger((int)32873);
        this.framebuffer = new GlFramebuffer(textWidth, textHeight, true);
        this.framebuffer.createAttachments(textWidth, textHeight);
        this.framebuffer.bind(true);
        Minecraft.getFontRenderer().drawString(text, 0.0, 0.0, color);
        this.framebuffer.bindColorTexture();
        this.framebuffer.unbind();
        GlStateManager.bindTexture(previousTextureId);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glPopMatrix();
        GL11.glLoadMatrix((FloatBuffer)previousProjectionMatrix);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glPopMatrix();
    }
}
