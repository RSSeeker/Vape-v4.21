package gg.vape.utils.render;

import gg.vape.ui.font.SmoothFontGlyph;
import gg.vape.utils.render.GlCapabilityState;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GlScissorRect;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.PrimitiveTopology;
import gg.vape.utils.render.RenderBatchBuilder;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.RenderMatrix4f;
import gg.vape.utils.render.RenderMatrixStack;
import gg.vape.utils.render.TextureAtlas;
import gg.vape.utils.render.TextureAtlasRegion;
import gg.vape.utils.render.TextureAtlasRegistry;
import java.awt.Color;

public class BufferedGuiRenderPrimitives {
    public static GlCapabilityState capabilityState;
    public static GlImageTexture fontTexture;
    public static RenderMatrix4f viewMatrix;
    public static RenderMatrix4f projectionMatrix;
    public static GlScissorRect scissorRect;
    public static int fontTextureId;
    public static RenderMatrixStack matrixStack;
    private static boolean bufferedRenderingEnabled;

    public static void drawLine(double startX, double startY, double endX, double endY, float width, Color color) {
        BufferedGuiRenderPrimitives.drawLine((float)startX, (float)startY, (float)endX, (float)endY, width, color);
    }

    public static void setBufferedRenderingEnabled(boolean enabled) {
        bufferedRenderingEnabled = enabled;
    }

    public static void fillQuad(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addQuad(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, color);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static boolean checkBufferedRenderingState() {
        boolean enabled = BufferedGuiRenderPrimitives.isBufferedRenderingEnabled();
        return false;
    }

    public static void drawMinecraftColorFontGlyph(float x, float y, SmoothFontGlyph glyph, int textureId, Color color, float scale) {
        fontTextureId = textureId;
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addMinecraftColorFontGlyph(x, y, glyph, textureId, color, scale);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawArcStroke(float x, float y, float diameter, float strokeWidth, float feather, float startAngle, float sweepAngle, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addArcStroke(x, y, diameter, strokeWidth, feather, startAngle, sweepAngle, color);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawAtlasTexture(float x, float y, float width, float height, GlImageTexture texture, String regionName, float scale, Color color, Color secondaryColor, boolean drawShadow) {
        float minU = 0.0f;
        float minV = 0.0f;
        float maxU = 1.0f;
        float maxV = 1.0f;
        float textureWidth = texture.width;
        float textureHeight = texture.height;
        TextureAtlas textureAtlas = TextureAtlasRegistry.getInstance().get("vape_texture");
        if (textureAtlas.getTexture() != null && texture.textureId == textureAtlas.getTexture().textureId) {
            TextureAtlasRegion textureAtlasRegion = textureAtlas.getRegion(regionName);
            if (textureAtlasRegion == null) {
                ImageRenderer.getImageHeight("armor");
                return;
            }
            minU = textureAtlasRegion.minU;
            maxU = textureAtlasRegion.maxU;
            minV = textureAtlasRegion.minV;
            maxV = textureAtlasRegion.maxV;
            textureWidth = textureAtlasRegion.width;
            textureHeight = textureAtlasRegion.height;
        }
        BufferedGuiRenderPrimitives.drawTexturedRect(x, y, width, height, textureWidth, textureHeight, minU, minV, maxU, maxV, scale, color, secondaryColor, drawShadow);
    }

    public static void drawCircleStroke(float x, float y, float diameter, float strokeWidth, float feather, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addCircleStroke(x, y, diameter, strokeWidth, feather, color);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static boolean isBufferedRenderingEnabled() {
        return bufferedRenderingEnabled;
    }

    public static void drawQuadOutline(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, Color color) {
        BufferedGuiRenderPrimitives.drawQuadOutline((float)x1, (float)y1, (float)x2, (float)y2, (float)x3, (float)y3, (float)x4, (float)y4, color);
    }

    public static void drawCornerMaskedRoundedRect(float x, float y, float width, float height, Color color, boolean drawShadow, float cornerRadius, float edgeSoftness, float shadowSpread, Color shadowColor, int cornerMask) {
        if (cornerRadius == 0.0f) {
            BufferedGuiRenderPrimitives.fillRect(x, y, width, height, color);
            return;
        }
        if (drawShadow) {
            RenderBatchManager.getInstance().queueGuiBatch(new RenderBatchBuilder().addRoundedRectShadow(x, y + 0.5f, width, height - 1.5f, shadowSpread, cornerRadius, shadowColor));
        }
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addCornerMaskedRoundedRect(x, y, width, height, color, cornerRadius, edgeSoftness, cornerMask);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawFontGlyph(float x, float y, SmoothFontGlyph glyph, GlImageTexture texture, Color color, float scale) {
        fontTexture = texture;
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addFontGlyph(x, y, glyph, color, scale);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawRoundedTexturedRect(float x, float y, float width, float height, float cornerRadius, float borderWidth, Color color, GlImageTexture texture) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().setTexture(texture).addRoundedTexturedRect(x, y, width, height, cornerRadius, borderWidth, color);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawDottedLine(float startX, float startY, float endX, float endY, float thickness, float spacing, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addDottedLine(startX, startY, endX, endY, thickness, spacing, color);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void fillTriangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color) {
        BufferedGuiRenderPrimitives.fillTriangle((float)x1, (float)y1, (float)x2, (float)y2, (float)x3, (float)y3, color);
    }

    public static void drawMinecraftFontGlyph(float x, float y, SmoothFontGlyph glyph, int textureId, Color color, float scale) {
        fontTextureId = textureId;
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addMinecraftFontGlyph(x, y, glyph, textureId, color, scale);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawLine(float startX, float startY, float endX, float endY, float width, Color color) {
        BufferedGuiRenderPrimitives.drawLine3D(startX, startY, 0.0f, endX, endY, 0.0f, width, color);
    }

    static {
        BufferedGuiRenderPrimitives.setBufferedRenderingEnabled(true);
        matrixStack = new RenderMatrixStack();
        viewMatrix = new RenderMatrix4f().setIdentity();
        projectionMatrix = new RenderMatrix4f().setIdentity();
        scissorRect = null;
        fontTexture = null;
        fontTextureId = -1;
        capabilityState = new GlCapabilityState();
    }

    public static void drawTexturedRect(float x, float y, float width, float height, float textureWidth, float textureHeight, float minU, float minV, float maxU, float maxV, float scale, Color color, Color secondaryColor, boolean drawShadow) {
        if (Math.signum(scale) >= 0.0f) {
            OpenGlBackendHolder.backend.scale(scale, scale, scale);
        }
        if (drawShadow) {
            Color shadowColor = new Color(0, 0, 0, 150);
            RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addTexturedRect(x + 0.5f, y + 0.5f, width, height, textureWidth, textureHeight, minU, minV, maxU, maxV, shadowColor);
            RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
        }
        if (secondaryColor == null) {
            RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addTexturedRect(x, y, width, height, textureWidth, textureHeight, minU, minV, maxU, maxV, color);
            RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
        }
        if (Math.signum(scale) >= 0.0f) {
            OpenGlBackendHolder.backend.scale(1.0f / scale, 1.0f / scale, 1.0f / scale);
        }
    }

    public static void drawTexturedCircle(float x, float y, float diameter, float feather, Color color, GlImageTexture texture) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().setTexture(texture).addTexturedCircle(x, y, diameter, feather, color);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawGradientPill(float x, float y, float width, float height, float[] startHsba, float[] endHsba) {
        if (width <= 0.0f || height <= 0.0f) {
            return;
        }
        RenderBatchManager.getInstance().queueGuiBatch(new RenderBatchBuilder().addGradientPill(x, y, width, height, startHsba, endHsba));
    }

    public static void drawRoundedRect(float x, float y, float width, float height, Color color, float cornerRadius, float borderWidth, float edgeSoftness) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addRoundedRect(x, y, width, height, color, cornerRadius, borderWidth, edgeSoftness);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawCompositeRoundedRect(float x, float y, float width, float height, float shadowSpread, float cornerRadius, Color shadowColor, float shadowOffsetX, float shadowOffsetY, float strokeWidth, Color strokeColor, Color fillColor, float circleThickness, float circleYOffset, Color circleColor, boolean texturePassthrough) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addCompositeRoundedRect(x, y, width, height, shadowSpread, cornerRadius, shadowColor, shadowOffsetX, shadowOffsetY, strokeWidth, strokeColor, fillColor, circleThickness, circleYOffset, circleColor, texturePassthrough);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void fillQuad(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, Color color) {
        BufferedGuiRenderPrimitives.fillQuad((float)x1, (float)y1, (float)x2, (float)y2, (float)x3, (float)y3, (float)x4, (float)y4, color);
    }

    public static void fillCapsule(float x, float y, float width, float height, Color color) {
        if (width <= 0.0f || height <= 0.0f) {
            return;
        }
        RenderBatchManager.getInstance().queueGuiBatch(new RenderBatchBuilder().addCapsule(x, y, width, height, color));
    }

    public static void drawBlurPass(double x, double y, double width, double height, float blurRadius, float inset, float feather, float direction, GlImageTexture texture) {
        if (width == 0.0 || height == 0.0) {
            return;
        }
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().setTexture(texture).addBlurPass((float)x, (float)y, (float)width, (float)height, blurRadius, inset, feather, direction);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void fillGradientQuad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Color startColor, Color endColor) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addDefaultShaderGradientQuad(x1, y1, 0.0f, x2, y2, 0.0f, x3, y3, 0.0f, x4, y4, 0.0f, startColor, endColor);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawCircleWithCenterRect(float x, float y, float diameter, float feather, Color circleColor, float centerRectWidth, Color centerRectColor) {
        if (centerRectColor != null) {
            float centerRectX = x + (diameter + feather) / 2.0f - centerRectWidth / 2.0f;
            RenderBatchManager.getInstance().queueGuiBatch(new RenderBatchBuilder().addDefaultRoundedRect(centerRectX, y, centerRectWidth, diameter, centerRectColor));
        }
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addCircle(x, y, diameter, feather, circleColor);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawInvertedRoundedRectCorners(double x, double y, double width, double height, double cornerRadius, double feather, int cornerMask, Color color) {
        BufferedGuiRenderPrimitives.drawInvertedRoundedRectCorners((float)x, (float)y, (float)width, (float)height, (float)cornerRadius, (float)feather, cornerMask, color);
    }

    public static void drawLine3D(float x1, float y1, float z1, float x2, float y2, float z2, float width, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(2).addLine(x1, y1, z1, x2, y2, z2, width, color);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void fillBorderAdjustedRect(double x, double y, double width, double height, double borderWidth, Color fillColor, Color borderColor) {
        if (fillColor.equals(borderColor)) {
            borderWidth = 0.0;
            BufferedGuiRenderPrimitives.fillRect(x, y, width - borderWidth, height - borderWidth, fillColor);
            return;
        }
        BufferedGuiRenderPrimitives.fillRect(x -= borderWidth, y, (width += borderWidth) - borderWidth, (height += borderWidth) - borderWidth, fillColor);
    }

    public static void fillQuad(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, Color color) {
        BufferedGuiRenderPrimitives.fillQuad((float)x1, (float)y1, (float)z1, (float)x2, (float)y2, (float)z2, (float)x3, (float)y3, (float)z3, (float)x4, (float)y4, (float)z4, color);
    }

    public static void fillRect(float x, float y, float width, float height, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addSolidRect(x, y, width, height, color);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawMinecraftIntensityFontGlyph(float x, float y, SmoothFontGlyph glyph, int textureId, Color color, float scale) {
        fontTextureId = textureId;
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addMinecraftIntensityFontGlyph(x, y, glyph, textureId, color, scale);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void fillQuad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Color color) {
        BufferedGuiRenderPrimitives.fillQuad(x1, y1, 0.0f, x2, y2, 0.0f, x3, y3, 0.0f, x4, y4, 0.0f, color);
    }

    public static void fillGradientQuad(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, Color startColor, Color endColor) {
        BufferedGuiRenderPrimitives.fillGradientQuad((float)x1, (float)y1, (float)x2, (float)y2, (float)x3, (float)y3, (float)x4, (float)y4, startColor, endColor);
    }

    public static void fillRect(double x, double y, double width, double height, Color color) {
        BufferedGuiRenderPrimitives.fillRect((float)x, (float)y, (float)width, (float)height, color);
    }


    public static void drawQuadOutline(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().setTopology(PrimitiveTopology.LINES_LOOP).addQuad(x1, y1, 0.0f, x2, y2, 0.0f, x3, y3, 0.0f, x4, y4, 0.0f, color);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawRoundedRectShadow(float x, float y, float width, float height, float spread, float cornerRadius, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addRoundedRectShadow(x, y, width, height, spread, cornerRadius, color);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawLine3D(double x1, double y1, double z1, double x2, double y2, double z2, float width, Color color) {
        BufferedGuiRenderPrimitives.drawLine3D((float)x1, (float)y1, (float)z1, (float)x2, (float)y2, (float)z2, width, color);
    }

    public static void fillTriangle(float x1, float y1, float x2, float y2, float x3, float y3, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(3).addTriangle(x1, y1, 0.0f, x2, y2, 0.0f, x3, y3, 0.0f, color);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }

    public static void drawInvertedRoundedRectCorners(float x, float y, float width, float height, float cornerRadius, float feather, int cornerMask, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder().addInvertedRoundedRectCorners(x, y, width, height, cornerRadius, feather, cornerMask, color);
        RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
    }
}

