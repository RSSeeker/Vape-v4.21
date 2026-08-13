package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherGroup;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherRegistry;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.unmap.ImageParser$Format;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtils;
import gg.vape.utils.render.TextureAtlas;
import gg.vape.utils.render.TextureAtlasRegion;
import gg.vape.utils.render.TextureAtlasRegistry;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import org.lwjgl.opengl.GL11;

public class ImageRenderer {
    public static HashMap<String, GlImageTexture> textureCache;
    private static boolean texture2dWasEnabled;
    private static boolean batchActive;
    private static boolean blendingWasEnabled;
    private static GuiComponent[] legacyComponents;

    public static double getImageHeight(String resourceName) {
        if (GuiRenderPrimitives.d()) {
            TextureAtlas textureAtlas = TextureAtlasRegistry.getInstance().get("vape_texture");
            if (textureAtlas.getRegion(resourceName) == null) {
                ImageRenderer.loadImage(resourceName);
            }
            TextureAtlasRegion atlasRegion = textureAtlas.getRegion(resourceName);
            return atlasRegion != null ? (double)atlasRegion.height : 0.0;
        }
        GlImageTexture texture = ImageRenderer.loadImage(resourceName);
        return texture != null ? (double)texture.height : 0.0;
    }

    private static GlImageTexture loadImage(String resourceName) {
        return ImageRenderer.loadResource(resourceName, false, false);
    }

    public static void drawRes(Color color, float x, float y, String resourceName, float scale) {
        ImageRenderer.drawResWithShadow(color, x, y, resourceName, scale, true);
    }

    public static void beginBatch() {
        batchActive = true;
        OpenGlBackendHolder.backend.pushMatrix();
        blendingWasEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
        texture2dWasEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3553);
        if (!blendingWasEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3042);
        }
        if (!texture2dWasEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3553);
        }
    }

    public static void endBatch() {
        RenderUtils.w(Color.white);
        if (!blendingWasEnabled) {
            OpenGlBackendHolder.backend.disableCapability(3042);
        }
        if (!texture2dWasEnabled) {
            OpenGlBackendHolder.backend.disableCapability(3553);
        }
        OpenGlBackendHolder.backend.popMatrix();
        batchActive = false;
    }

    public static GlImageTexture loadResource(String resourceName, boolean useMipmaps, boolean whiteMask) {
        TextureAtlas textureAtlas = GuiRenderPrimitives.d() ? TextureAtlasRegistry.getInstance().getActiveAtlas() : null;
        if (textureAtlas != null) {
            TextureAtlasRegion atlasRegion = textureAtlas.getRegion(resourceName);
            if (atlasRegion != null) {
                return ImageRenderer.createRegionTexture(textureAtlas, atlasRegion);
            }
        } else {
            GlImageTexture cachedTexture = textureCache.get(resourceName);
            if (cachedTexture != null) {
                return cachedTexture;
            }
        }
        try {
            String resourcePath = "textures/" + resourceName + ".png";
            byte[] resourceData = Vape.readResource(resourcePath);
            if (resourceData == null || resourceData.length == 0) {
                if ("world".equals(resourceName)) {
                    return null;
                }
                GlImageTexture fallbackTexture = ImageRenderer.loadImage("world");
                if (fallbackTexture != null) {
                    textureCache.put(resourceName, fallbackTexture);
                }
                return fallbackTexture;
            }
            if (textureAtlas != null) {
                textureAtlas.addImage(resourceName, resourceData, whiteMask);
                return ImageRenderer.createRegionTexture(textureAtlas, textureAtlas.getRegion(resourceName));
            }
            GlImageTexture texture = new GlImageTexture(new ByteArrayInputStream(resourceData), useMipmaps ? 9987 : 9729, whiteMask ? ImageParser$Format.WHITE : ImageParser$Format.RGBA);
            textureCache.put(resourceName, texture);
            return texture;
        }
        catch (IOException exception) {
            Vape.logThrowable(exception);
            return null;
        }
    }

    static {
        ImageRenderer.setLegacyComponents(new GuiComponent[5]);
        textureCache = new HashMap();
        blendingWasEnabled = false;
        texture2dWasEnabled = false;
    }

    public static String xorString(String input, int key) {
        String decoded = "";
        for (int index = 0; index < input.length(); ++index) {
            char character = input.charAt(index);
            decoded = decoded + String.valueOf((char)(character ^ key));
        }
        return decoded;
    }

    public static void drawVerticalGradientTexture(GlImageTexture texture, float x, float y, float width, float height, Color topColor, Color bottomColor) {
        float aspectRatio = (float)texture.width / (float)texture.height;
        width *= aspectRatio;
        texture.bind();
        float minU = 0.0f;
        float minV = 0.0f;
        float maxU = 1.0f;
        float maxV = 1.0f;
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        GL11.glColor4d((double)((double)topColor.getRed() / 255.0), (double)((double)topColor.getGreen() / 255.0), (double)((double)topColor.getBlue() / 255.0), (double)((double)topColor.getAlpha() / 255.0));
        GL11.glTexCoord2f((float)maxU, (float)minV);
        GL11.glVertex2f((float)(x + width), (float)y);
        GL11.glTexCoord2f((float)minU, (float)minV);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glColor4d((double)((double)bottomColor.getRed() / 255.0), (double)((double)bottomColor.getGreen() / 255.0), (double)((double)bottomColor.getBlue() / 255.0), (double)((double)bottomColor.getAlpha() / 255.0));
        GL11.glTexCoord2f((float)minU, (float)maxV);
        GL11.glVertex2f((float)x, (float)(y + height));
        GL11.glTexCoord2f((float)maxU, (float)maxV);
        GL11.glVertex2f((float)(x + width), (float)(y + height));
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
    }

    public static void preloadResources() {
        ImageRenderer.loadResource("vapelogo", true, false);
        ImageRenderer.loadResource("v4", true, false);
        ImageRenderer.loadResource("lmb", true, false);
        ImageRenderer.loadResource("rmb", true, false);
        ImageRenderer.loadResource("mmb", true, false);
        ImageRenderer.loadResource("up", false, true);
        ImageRenderer.loadResource("up2", false, true);
        ImageRenderer.loadResource("down", false, true);
        ImageRenderer.loadResource("left", false, true);
        ImageRenderer.loadResource("right", false, true);
        ImageRenderer.loadResource("party1@2x", false, true);
        ImageRenderer.loadResource("synced@2x", false, true);
        ImageRenderer.loadResource("party@2x", false, true);
        ImageRenderer.loadResource("default_user", false, false);
        ImageRenderer.loadResource("chat@2x", false, true);
        ImageRenderer.loadResource("triangle", true, false);
        ImageRenderer.loadResource("ping_location", true, false);
        ImageRenderer.loadResource("icons8_downloading_updates", false, true);
        ImageRenderer.loadResource("submit@2x", false, true);
        ImageRenderer.loadResource("legit_primary", true, true);
        for (InventoryItemMatcherGroup matcherGroup : InventoryItemMatcherGroup.VALUES) {
            if (matcherGroup.getIconName() == null) continue;
            ImageRenderer.loadResource(matcherGroup.getIconName(), false, true);
        }
        for (InventoryItemMatcher inventoryItemMatcher : InventoryItemMatcherRegistry.getAll()) {
            if (inventoryItemMatcher.getIconName() == null) continue;
            ImageRenderer.loadResource(inventoryItemMatcher.getIconName(), false, true);
        }
        ImageRenderer.loadResource("other@2x", false, true);
    }

    public static double getImageWidth(String resourceName) {
        if (GuiRenderPrimitives.d()) {
            TextureAtlas textureAtlas = TextureAtlasRegistry.getInstance().get("vape_texture");
            if (textureAtlas.getRegion(resourceName) == null) {
                ImageRenderer.loadImage(resourceName);
            }
            TextureAtlasRegion atlasRegion = textureAtlas.getRegion(resourceName);
            return atlasRegion != null ? (double)atlasRegion.width : 0.0;
        }
        GlImageTexture texture = ImageRenderer.loadImage(resourceName);
        return texture != null ? (double)texture.width : 0.0;
    }

    public static void drawImage(Color topColor, Color bottomColor, float x, float y, String resourceName, float width, float height, boolean shadow) {
        GlImageTexture texture = ImageRenderer.loadImage(resourceName);
        ImageRenderer.drawImageInternal(topColor, bottomColor, x, y, texture, resourceName, width, height, shadow, -1.0f);
    }

    public static void drawResWithShadow(Color color, float x, float y, String resourceName, float scale, boolean shadow) {
        GlImageTexture texture = ImageRenderer.loadImage(resourceName);
        double inverseScale = 1.0 / (double)scale;
        x = (float)((double)x * inverseScale);
        y = (float)((double)y * inverseScale);
        ImageRenderer.drawImageInternal(color, null, x, y, texture, resourceName, 32.0f, 32.0f, shadow, scale);
    }

    public static void drawImage(Color color, float x, float y, String resourceName, float width, float height, boolean shadow) {
        GlImageTexture texture = ImageRenderer.loadImage(resourceName);
        ImageRenderer.drawImageInternal(color, null, x, y, texture, resourceName, width, height, shadow, -1.0f);
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    public static GuiComponent[] getLegacyComponents() {
        return legacyComponents;
    }

    public static void drawImageInternal(Color color, Color bottomColor, float x, float y, GlImageTexture texture, String resourceName, float width, float height, boolean shadow, float scale) {
        if (texture == null) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawAtlasTexture(x, y, width, height, texture, resourceName, scale, color, bottomColor, shadow);
            return;
        }
        boolean blendingWasEnabled = false;
        boolean texture2dWasEnabled = false;
        if (!batchActive) {
            GL11.glPushMatrix();
            blendingWasEnabled = GL11.glIsEnabled((int)3042);
            texture2dWasEnabled = GL11.glIsEnabled((int)3553);
            if (!blendingWasEnabled) {
                OpenGlBackendHolder.backend.enableCapability(3042);
            }
            if (!texture2dWasEnabled) {
                OpenGlBackendHolder.backend.enableCapability(3553);
            }
        }
        if (Math.signum(scale) >= 0.0f) {
            GL11.glScaled((double)scale, (double)scale, (double)scale);
        }
        if (shadow) {
            RenderUtils.w(new Color(0, 0, 0, 150));
            ImageRenderer.drawTextureQuad(texture, x + 0.5f, y + 0.5f, width, height);
        }
        RenderUtils.w(color);
        if (bottomColor == null) {
            ImageRenderer.drawTextureQuad(texture, x, y, width, height);
        } else {
            ImageRenderer.drawVerticalGradientTexture(texture, x, y, width, height, color, bottomColor);
        }
        if (Math.signum(scale) >= 0.0f) {
            GL11.glScaled((double)1.0, (double)1.0, (double)1.0);
        }
        if (!batchActive) {
            RenderUtils.w(Color.white);
            if (!blendingWasEnabled) {
                OpenGlBackendHolder.backend.disableCapability(3042);
            }
            if (!texture2dWasEnabled) {
                OpenGlBackendHolder.backend.disableCapability(3553);
            }
            GL11.glPopMatrix();
        }
    }

    public static void drawTexture(Color color, float x, float y, GlImageTexture texture, float width, float height, boolean shadow) {
        ImageRenderer.drawImageInternal(color, null, x, y, texture, null, width, height, shadow, -1.0f);
    }

    private static GlImageTexture createRegionTexture(TextureAtlas textureAtlas, TextureAtlasRegion atlasRegion) {
        GlImageTexture atlasTexture = textureAtlas.getTexture();
        if (atlasTexture == null || atlasRegion == null) {
            return atlasTexture;
        }
        return new GlImageTexture(atlasTexture.textureId, atlasRegion.width, atlasRegion.height, atlasRegion.minU, atlasRegion.minV, atlasRegion.maxU, atlasRegion.maxV);
    }

    public static void drawTextureQuad(GlImageTexture texture, float x, float y, float width, float height) {
        if (width == height) {
            float aspectRatio = (float)texture.width / (float)texture.height;
            width *= aspectRatio;
        }
        texture.bind();
        float minU = 0.0f;
        float minV = 0.0f;
        float maxU = 1.0f;
        float maxV = 1.0f;
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)maxU, (float)minV);
        GL11.glVertex2f((float)(x + width), (float)y);
        GL11.glTexCoord2f((float)minU, (float)minV);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glTexCoord2f((float)minU, (float)maxV);
        GL11.glVertex2f((float)x, (float)(y + height));
        GL11.glTexCoord2f((float)maxU, (float)maxV);
        GL11.glVertex2f((float)(x + width), (float)(y + height));
        GL11.glEnd();
    }

    public static void setLegacyComponents(GuiComponent[] components) {
        legacyComponents = components;
    }
}
