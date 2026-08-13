package gg.vape.utils.render;

import gg.vape.ui.font.SmoothFontGlyph;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderBatchBuilder;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.VertexCoordinateMode;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderManager;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class BufferedRenderPrimitives {
    public static void drawRectOutline(float left, float top, float right, float bottom, float lineWidth, Color color) {
        BufferedRenderPrimitives.drawLine3D(left, top, 0.0f, right, top, 0.0f, lineWidth, color);
        BufferedRenderPrimitives.drawLine3D(right, top, 0.0f, right, bottom, 0.0f, lineWidth, color);
        BufferedRenderPrimitives.drawLine3D(right, bottom, 0.0f, left, bottom, 0.0f, lineWidth, color);
        BufferedRenderPrimitives.drawLine3D(left, bottom, 0.0f, left, top, 0.0f, lineWidth, color);
    }

    public static void drawLine(double startX, double startY, double endX, double endY, float width, Color color) {
        BufferedRenderPrimitives.drawLine((float)startX, (float)startY, (float)endX, (float)endY, width, color);
    }

    public static void drawLine3D(double x1, double y1, double z1, double x2, double y2, double z2, float width, Color color) {
        BufferedRenderPrimitives.drawLine3D((float)x1, (float)y1, (float)z1, (float)x2, (float)y2, (float)z2, width, color);
    }

    public static void fillQuad(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true).addQuad(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, color);
        RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
    }

    public static void drawFontGlyph(float x, float y, SmoothFontGlyph glyph, GlImageTexture texture, Color color, float scale) {
        BufferedGuiRenderPrimitives.fontTexture = texture;
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true).addFontGlyph(x, y, glyph, color, scale);
        RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
    }

    public static void drawMinecraftColorFontGlyph(float x, float y, SmoothFontGlyph glyph, int textureId, Color color, float scale) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true)
                .addMinecraftColorFontGlyph(x, y, glyph, textureId, color, scale);
        RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
    }

    public static void drawMinecraftIntensityFontGlyph(float x, float y, SmoothFontGlyph glyph, int textureId, Color color, float scale) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true)
                .addMinecraftIntensityFontGlyph(x, y, glyph, textureId, color, scale);
        RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
    }

    public static void fillRect(double x, double y, double width, double height, Color color) {
        BufferedRenderPrimitives.fillRect((float)x, (float)y, (float)width, (float)height, color);
    }

    public static void drawLine3D(float x1, float y1, float z1, float x2, float y2, float z2, float width, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(2, VertexCoordinateMode.DEFAULT, true).addLine(x1, y1, z1, x2, y2, z2, width, color);
        RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
    }

    public static void fillQuad(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, Color color) {
        BufferedRenderPrimitives.fillQuad((float)x1, (float)y1, (float)z1, (float)x2, (float)y2, (float)z2, (float)x3, (float)y3, (float)z3, (float)x4, (float)y4, (float)z4, color);
    }

    public static void fillBorderAdjustedRect(float x, float y, float width, float height, float borderWidth, Color fillColor, Color borderColor) {
        if (fillColor.equals(borderColor)) {
            borderWidth = 0.0f;
            RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true).addSolidRect(x, y, width - borderWidth, height - borderWidth, fillColor);
            RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
            return;
        }
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true).addSolidRect(x -= borderWidth, y, (width += borderWidth) - borderWidth, (height += borderWidth) - borderWidth, fillColor);
        RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
        float right = x + width;
        float bottom = y + height;
    }

    public static void fillRect(float x, float y, float width, float height, Color color) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true).addSolidRect(x, y, width, height, color);
        RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
    }

    public static void drawBoxOutline(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Color color) {
        BufferedRenderPrimitives.drawLine3D(minX, minY, minZ, maxX, minY, minZ, 1.0f, color);
        BufferedRenderPrimitives.drawLine3D(maxX, minY, minZ, maxX, minY, maxZ, 1.0f, color);
        BufferedRenderPrimitives.drawLine3D(maxX, minY, minZ, maxX, minY, maxZ, 1.0f, color);
        BufferedRenderPrimitives.drawLine3D(minX, minY, minZ, minX, minY, maxZ, 1.0f, color);
        BufferedRenderPrimitives.drawLine3D(minX, minY, maxZ, maxX, minY, maxZ, 1.0f, color);
        BufferedRenderPrimitives.drawLine3D(minX, minY, minZ, minX, maxY, minZ, 1.0f, color);
        BufferedRenderPrimitives.drawLine3D(maxX, minY, maxZ, maxX, maxY, maxZ, 1.0f, color);
        BufferedRenderPrimitives.drawLine3D(minX, minY, maxZ, minX, maxY, maxZ, 1.0f, color);
        BufferedRenderPrimitives.drawLine3D(maxX, minY, minZ, maxX, maxY, minZ, 1.0f, color);
        BufferedRenderPrimitives.drawLine3D(minX, maxY, minZ, maxX, maxY, minZ, 1.0f, color);
        BufferedRenderPrimitives.drawLine3D(maxX, maxY, minZ, maxX, maxY, maxZ, 1.0f, color);
        BufferedRenderPrimitives.drawLine3D(minX, maxY, minZ, minX, maxY, maxZ, 1.0f, color);
        BufferedRenderPrimitives.drawLine3D(minX, maxY, maxZ, maxX, maxY, maxZ, 1.0f, color);
    }

    public static void drawBoxOutline(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color color) {
        BufferedRenderPrimitives.drawBoxOutline((float)minX, (float)minY, (float)minZ, (float)maxX, (float)maxY, (float)maxZ, color);
    }

    public static void drawCylinderSides(double worldX, double worldY, double worldZ, float segments, float radius, float height, Color color) {
        double cameraX = RenderManager.getInterpolatedRenderPosX();
        double cameraY = RenderManager.getInterpolatedRenderPosY();
        double cameraZ = RenderManager.getInterpolatedRenderPosZ();
        float centerX = (float)(worldX - cameraX);
        float baseY = (float)(worldY - cameraY);
        float centerZ = (float)(worldZ - cameraZ);
        RenderUtil.d();
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().B(1.0);
        OpenGlBackendHolder.backend.enableCapability(3042);
        GL11.glBlendFunc((int)770, (int)771);
        OpenGlBackendHolder.backend.setDepthMask(false);
        OpenGlBackendHolder.backend.disableCapability(2884);
        float bottomY = baseY;
        float topY = baseY + height;
        MutableColor transparentColor = new MutableColor(color).withAlpha(0);
        Color opaqueColor = color;
        float previousX = 0.0f;
        float previousZ = 0.0f;
        boolean hasPreviousPoint = false;
        int segmentIndex = 0;
        while ((float)segmentIndex <= segments) {
            float angle = (float)(Math.PI * 2 * (double)segmentIndex / (double)segments);
            float currentX = (float)((double)radius * Math.cos(angle) + (double)centerX);
            float currentZ = (float)((double)radius * Math.sin(angle) + (double)centerZ);
            if (!hasPreviousPoint) {
                hasPreviousPoint = true;
                previousX = currentX;
                previousZ = currentZ;
            } else {
                BufferedRenderPrimitives.fillGradientQuad(currentX, topY, currentZ, previousX, topY, previousZ, previousX, bottomY, previousZ, currentX, bottomY, currentZ, transparentColor, opaqueColor);
                previousX = currentX;
                previousZ = currentZ;
            }
            ++segmentIndex;
        }
        OpenGlBackendHolder.backend.setDepthMask(true);
        OpenGlBackendHolder.backend.disableCapability(3042);
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
        RenderUtil.Y();
    }

    public static void drawLine(float startX, float startY, float endX, float endY, float width, Color color) {
        BufferedRenderPrimitives.drawLine3D(startX, startY, 0.0f, endX, endY, 0.0f, width, color);
    }

    public static void fillBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color color) {
        BufferedRenderPrimitives.fillBox((float)minX, (float)minY, (float)minZ, (float)maxX, (float)maxY, (float)maxZ, color);
    }

    public static void fillBorderAdjustedRect(double x, double y, double width, double height, float borderWidth, Color fillColor, Color borderColor) {
        BufferedRenderPrimitives.fillBorderAdjustedRect((float)x, (float)y, (float)width, (float)height, borderWidth, fillColor, borderColor);
    }

    public static void fillBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Color color) {
        BufferedRenderPrimitives.fillQuad(minX, minY, minZ, minX, maxY, minZ, maxX, maxY, minZ, maxX, minY, minZ, color);
        BufferedRenderPrimitives.fillQuad(maxX, minY, maxZ, maxX, maxY, maxZ, minX, maxY, maxZ, minX, minY, maxZ, color);
        BufferedRenderPrimitives.fillQuad(maxX, minY, minZ, maxX, maxY, minZ, maxX, maxY, maxZ, maxX, minY, maxZ, color);
        BufferedRenderPrimitives.fillQuad(minX, minY, maxZ, minX, maxY, maxZ, minX, maxY, minZ, minX, minY, minZ, color);
        BufferedRenderPrimitives.fillQuad(minX, maxY, minZ, minX, maxY, maxZ, maxX, maxY, maxZ, maxX, maxY, minZ, color);
        BufferedRenderPrimitives.fillQuad(minX, minY, minZ, maxX, minY, minZ, maxX, minY, maxZ, minX, minY, maxZ, color);
    }

    public static void drawCompositeRoundedRect(float x, float y, float width, float height, float shadowSpread, float cornerRadius, Color shadowColor, float shadowOffsetX, float shadowOffsetY, float strokeWidth, Color strokeColor, Color fillColor, float circleThickness, float circleYOffset, Color circleColor, boolean texturePassthrough) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true).addCompositeRoundedRect(x, y, width, height, shadowSpread, cornerRadius, shadowColor, shadowOffsetX, shadowOffsetY, strokeWidth, strokeColor, fillColor, circleThickness, circleYOffset, circleColor, texturePassthrough);
        RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
    }

    public static void fillGradientQuad(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, Color startColor, Color endColor) {
        RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true).addGradientQuad(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, startColor, endColor);
        RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
    }

    public static void drawRectOutline(double left, double top, double right, double bottom, float lineWidth, Color color) {
        BufferedRenderPrimitives.drawRectOutline((float)left, (float)top, (float)right, (float)bottom, lineWidth, color);
    }
}
