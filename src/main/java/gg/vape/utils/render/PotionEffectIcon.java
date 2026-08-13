package gg.vape.utils.render;

import gg.vape.utils.render.GlFramebuffer;
import gg.vape.utils.render.PotionEffectIconRenderBackend;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.GuiContainer;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Potion;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.RenderHelper;
import gg.vape.wrapper.impl.RenderItemTextBridge;
import gg.vape.wrapper.impl.StatusEffect;
import gg.vape.wrapper.impl.StatusEffectSpriteUploader;
import gg.vape.wrapper.impl.TextureAtlas;
import gg.vape.wrapper.impl.TextureAtlasSprite;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class PotionEffectIcon
implements PotionEffectIconRenderBackend {
    GlFramebuffer framebuffer;

    @Override
    public void render(float x, float y, int width, int height, float opacity) {
        GL11.glEnable(2903);
        boolean textureEnabled = GL11.glIsEnabled(3553);
        boolean lightingEnabled = GL11.glIsEnabled(2896);
        boolean alphaTestEnabled = GL11.glIsEnabled(3008);
        boolean blendEnabled = GL11.glIsEnabled(3042);
        if (!textureEnabled) {
            GlStateManager.enableTexture2D();
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
        int previousTextureId = GL11.glGetInteger(32873);
        this.framebuffer.bindColorTexture();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, opacity);
        GL11.glPushMatrix();
        float minU = 0.0f;
        float maxV = 1.0f;
        float maxU = 1.0f;
        float minV = 0.0f;
        GL11.glBegin(7);
        GL11.glTexCoord2f(maxU, maxV);
        GL11.glVertex2f(x + (float)width, y);
        GL11.glTexCoord2f(minU, maxV);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(minU, minV);
        GL11.glVertex2f(x, y + (float)height);
        GL11.glTexCoord2f(maxU, minV);
        GL11.glVertex2f(x + (float)width, y + (float)height);
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
        if (!alphaTestEnabled) {
            GlStateManager.disableAlpha();
        }
        if (!blendEnabled) {
            GlStateManager.disableBlend();
        }
    }

    private void drawEffectSprite(PotionEffect effect, int width, int height) {
        if (ForgeVersion.MC_1_16_5.d()) {
            StatusEffectSpriteUploader spriteUploader = StatusEffectSpriteUploader.getPotionSprites();
            StatusEffect statusEffect = effect.i();
            TextureAtlasSprite sprite = spriteUploader.getSprite(statusEffect);
            TextureAtlas textureAtlas = new TextureAtlas(sprite.getContentsOrAtlasTexture());
            Minecraft.getTextureManager().bindTexture(textureAtlas.getTextureLocation());
            GL11.glPushMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableDepth();
            RenderItemTextBridge.drawSprite(MatrixStack.A(), 0, 0, 0, width, height, sprite);
            GL11.glPopMatrix();
            return;
        }
        Minecraft.getTextureManager().bindTexture(GuiContainer.m$src$Lgg_vape_wrapper_impl_ResourceLocation_$1fc62cj());
        Potion potion = Potion.getPotionById(effect.C());
        if (potion.isBadEffect()) {
            int iconIndex = potion.y();
            GL11.glPushMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableDepth();
            RenderUtils.R(0, 0, iconIndex % 8 * 18, 198 + iconIndex / 8 * 18, width, height);
            GL11.glPopMatrix();
        }
    }


    @Override
    public void capture(PotionEffect effect) {
        int iconWidth = 18;
        int iconHeight = 18;
        int previousFramebufferId = GL11.glGetInteger(36006);
        int previousTextureId = GL11.glGetInteger(32873);
        boolean scissorEnabled = GL11.glIsEnabled(3089);
        boolean textureEnabled = GL11.glIsEnabled(3553);
        if (scissorEnabled) {
            GL11.glDisable(3089);
        }
        if (!textureEnabled) {
            GlStateManager.enableTexture2D();
        }
        ByteBuffer viewportBytes = ByteBuffer.allocateDirect(64);
        viewportBytes.order(ByteOrder.nativeOrder());
        IntBuffer viewport = viewportBytes.asIntBuffer();
        gg.vape.wrapper.impl.GL11.X(2978, viewport);
        GL11.glPushMatrix();
        this.framebuffer = new GlFramebuffer(iconWidth, iconHeight, true);
        this.framebuffer.bind(true);
        GlStateManager.enableDepth();
        GlStateManager.enableBlend();
        GL11.glEnable(2929);
        GlStateManager.enableBlend();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear(16384);
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, 18.0, 18.0, 0.0, -1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        RenderHelper.e();
        GlStateManager.disableLighting();
        GL11.glEnable(32826);
        GL11.glPushMatrix();
        GlStateManager.disableBlend();
        this.drawEffectSprite(effect, iconWidth, iconHeight);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        this.framebuffer.bindColorTexture();
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        RenderHelper.s();
        GL11.glDisable(32826);
        this.framebuffer.bindColorTexture();
        this.framebuffer.unbind();
        GL11.glPopMatrix();
        GL11.glViewport(viewport.get(0), viewport.get(1), viewport.get(2), viewport.get(3));
        GL30.glBindFramebuffer(36160, previousFramebufferId);
        GlStateManager.bindTexture(previousTextureId);
        if (scissorEnabled) {
            GL11.glEnable(3089);
        }
        if (!textureEnabled) {
            GlStateManager.disableTexture2D();
        }
    }

    @Override
    public void dispose() {
        this.framebuffer.delete();
        this.framebuffer = null;
    }
}
