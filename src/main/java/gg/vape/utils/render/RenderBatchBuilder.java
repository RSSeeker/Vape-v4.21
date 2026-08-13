package gg.vape.utils.render;

import gg.vape.ui.font.SmoothFontGlyph;
import gg.vape.utils.datas.FloatPair;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GlCapabilityState;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GlScissorRect;
import gg.vape.utils.render.PrimitiveTopology;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.RenderMatrix4f;
import gg.vape.utils.render.RenderVector3f;
import gg.vape.utils.render.RenderVector4f;
import gg.vape.utils.render.VertexCoordinateMode;
import java.awt.Color;
import java.util.function.Supplier;

public class RenderBatchBuilder {
    public int baseVertexIndex = 0;
    private boolean worldSpace;
    private final RenderVector3f zeroVector3;
    private GlImageTexture texture;
    private final RenderVector4f zeroVector4;
    public RenderMatrix4f modelMatrix;
    private VertexCoordinateMode coordinateMode;
    private int[] indices;
    private float[] vertexData;
    private float lineWidth = 1.0f;
    private Supplier<Void> standaloneRenderCallback = null;
    private GlScissorRect scissorRect = null;
    private PrimitiveTopology topology = null;
    private final FloatPair zeroPair = new FloatPair(0.0f, 0.0f);
    private int vertexCapacity;
    private Supplier<Void> drawSetupCallback = null;
    public int vertexCount = 0;
    private GlCapabilityState capabilityState;

    private void appendSolidColorVertexData(float shaderMode, float x, float y, float z, RenderVector4f innerRect, float spread, RenderVector4f color) {
        this.appendRoundedShapeVertex(shaderMode, x, y, z, innerRect, spread, 0.0f, color);
    }

    public RenderBatchBuilder addRoundedTexturedRect(float x, float y, float width, float height, float cornerRadius, float borderWidth, Color color) {
        if (width == 0.0f || height == 0.0f) {
            return null;
        }
        float antialiasPadding = 0.5f;
        if (cornerRadius == 0.0f) {
            antialiasPadding = 0.0f;
        }
        float insetCornerRadius = Math.max(0.0f, (cornerRadius += antialiasPadding * 2.0f) - borderWidth);
        if (cornerRadius != 0.0f) {
            x = (float)((double)x - ((double)borderWidth - 0.5));
            y -= borderWidth;
            height = (float)((double)height + (double)borderWidth * 1.5);
            width += borderWidth;
        }
        float right = x + width;
        float bottom = y + height;
        float textureMinU = this.texture != null ? this.texture.minU : 0.0f;
        float textureMinV = this.texture != null ? this.texture.minV : 0.0f;
        float textureMaxU = this.texture != null ? this.texture.maxU : 1.0f;
        float textureMaxV = this.texture != null ? this.texture.maxV : 1.0f;
        RenderVector4f roundedRectBounds = new RenderVector4f(x + cornerRadius, y + cornerRadius, x + width - cornerRadius, y + height - cornerRadius);
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendTexturedShapeVertex(7.0f, x, y, 0.0f, textureMinU, textureMinV, insetCornerRadius, roundedRectBounds, borderWidth, colorVector);
        this.appendTexturedShapeVertex(7.0f, x, bottom, 0.0f, textureMinU, textureMaxV, insetCornerRadius, roundedRectBounds, borderWidth, colorVector);
        this.appendTexturedShapeVertex(7.0f, right, y, 0.0f, textureMaxU, textureMinV, insetCornerRadius, roundedRectBounds, borderWidth, colorVector);
        this.appendTexturedShapeVertex(7.0f, right, bottom, 0.0f, textureMaxU, textureMaxV, insetCornerRadius, roundedRectBounds, borderWidth, colorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    private void appendRoundedShapeVertex(float shaderMode, float x, float y, float z, RenderVector4f innerRect, float spread, float cornerRadius, RenderVector4f color) {
        RenderVector3f position = new RenderVector3f(x, y, z);
        RenderVector3f radius = new RenderVector3f(cornerRadius, 0.0f, 0.0f);
        this.appendVertex(shaderMode, position, this.zeroPair, color, 0.0f, 0.0f, this.zeroPair, 0.0f, 0.0f, this.zeroPair, this.zeroPair, innerRect, radius, spread, this.zeroVector4);
    }

    public RenderBatchBuilder setTopology(PrimitiveTopology topology) {
        return this.initializeTopology(topology, RenderBatchManager.getInstance().getBatchBuffer().getVertexStride());
    }

    public float[] getVertexData() {
        return this.vertexData;
    }

    public RenderBatchBuilder addCircle(float x, float y, float diameter, float feather, Color color) {
        float boundsWidth = diameter += feather;
        float boundsHeight = diameter;
        float right = (x -= feather / 2.0f) + boundsWidth;
        float bottom = (y -= feather / 2.0f) + boundsHeight;
        float radius = diameter / 2.0f;
        FloatPair center = new FloatPair(x + boundsWidth / 2.0f, y + boundsHeight / 2.0f);
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendCircleVertexData(2.0f, x, y, 0.0f, radius, feather, center, colorVector);
        this.appendCircleVertexData(2.0f, x, bottom, 0.0f, radius, feather, center, colorVector);
        this.appendCircleVertexData(2.0f, right, y, 0.0f, radius, feather, center, colorVector);
        this.appendCircleVertexData(2.0f, right, bottom, 0.0f, radius, feather, center, colorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public RenderBatchBuilder addTriangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, Color color) {
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.TRIANGLES);
        this.appendSolidColorVertex(9.0f, x2, y2, z2, colorVector);
        this.appendSolidColorVertex(9.0f, x1, y1, z1, colorVector);
        this.appendSolidColorVertex(9.0f, x3, y3, z3, colorVector);
        this.generateIndicesForCurrentBatch("triangle");
        return this;
    }

    public RenderBatchBuilder setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    private static IllegalArgumentException propagateIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException;
    }

    public RenderBatchBuilder(VertexCoordinateMode coordinateMode, boolean worldSpace) {
        this(4, coordinateMode, worldSpace);
    }

    private void appendCircleStrokeVertex(float shaderMode, float x, float y, float z, float innerRadius, float outerRadius, float feather, FloatPair center, RenderVector4f color) {
        RenderVector3f position = new RenderVector3f(x, y, z);
        this.appendVertex(shaderMode, position, this.zeroPair, color, innerRadius, feather, center, 0.0f, 0.0f, this.zeroPair, this.zeroPair, this.zeroVector4, new RenderVector3f(outerRadius, 0.0f, 0.0f), 0.0f, this.zeroVector4);
    }

    public float getLineWidth() {
        return this.lineWidth;
    }

    public RenderBatchBuilder addRoundedRect(float x, float y, float width, float height, Color color, float cornerRadius, float borderWidth, float edgeSoftness) {
        float antialiasPadding = 0.5f;
        x = (float)((double)x - ((double)edgeSoftness - 0.5));
        height = (float)((double)height + (double)edgeSoftness * 1.5);
        float left = x + antialiasPadding;
        float right = x + (width += edgeSoftness) - antialiasPadding;
        float top = (y -= edgeSoftness) + antialiasPadding;
        float bottom = y + height - antialiasPadding;
        RenderVector3f roundedRectParameters = new RenderVector3f(cornerRadius, edgeSoftness, borderWidth);
        RenderVector4f roundedRectBounds = new RenderVector4f(x + cornerRadius + borderWidth, y + cornerRadius + borderWidth, x + width - (cornerRadius + borderWidth), y + height - (cornerRadius + borderWidth));
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendRoundedShapeBoundsVertex(6.0f, left, top, 0.0f, roundedRectParameters, roundedRectBounds, colorVector);
        this.appendRoundedShapeBoundsVertex(6.0f, left, bottom, 0.0f, roundedRectParameters, roundedRectBounds, colorVector);
        this.appendRoundedShapeBoundsVertex(6.0f, right, top, 0.0f, roundedRectParameters, roundedRectBounds, colorVector);
        this.appendRoundedShapeBoundsVertex(6.0f, right, bottom, 0.0f, roundedRectParameters, roundedRectBounds, colorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    private void appendTexturedShapeVertex(float shaderMode, float x, float y, float z, float u, float v, float cornerRadius, RenderVector4f innerRect, float spread, RenderVector4f color) {
        RenderVector3f position = new RenderVector3f(x, y, z);
        FloatPair textureCoordinates = new FloatPair(u, v);
        this.appendVertex(shaderMode, position, textureCoordinates, color, 0.0f, 0.0f, this.zeroPair, 0.0f, 0.0f, this.zeroPair, this.zeroPair, innerRect, new RenderVector3f(cornerRadius, 0.0f, 0.0f), spread, this.zeroVector4);
    }

    private void appendTexturedCircleVertexData(float shaderMode, float x, float y, float z, float u, float v, float radius, float feather, FloatPair center, RenderVector4f color) {
        RenderVector3f position = new RenderVector3f(x, y, z);
        FloatPair textureCoordinates = new FloatPair(u, v);
        this.appendVertex(shaderMode, position, textureCoordinates, color, 0.0f, feather, center, 0.0f, 0.0f, this.zeroPair, this.zeroPair, this.zeroVector4, new RenderVector3f(radius, 0.0f, 0.0f), 0.0f, this.zeroVector4);
    }

    private RenderBatchBuilder addGlyphQuad(float shaderMode, float x, float y, SmoothFontGlyph glyph, GlImageTexture glyphTexture, Color color, float scale) {
        float left = x + glyph.X * scale;
        float right = x + glyph.g * scale;
        float top = y + glyph.a * scale;
        float bottom = y + glyph.G * scale;
        this.setTexture(glyphTexture);
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendTexturedVertex(shaderMode, left, top, 0.0f, glyph.t, glyph.J, colorVector);
        this.appendTexturedVertex(shaderMode, left, bottom, 0.0f, glyph.t, glyph.B, colorVector);
        this.appendTexturedVertex(shaderMode, right, top, 0.0f, glyph.w, glyph.J, colorVector);
        this.appendTexturedVertex(shaderMode, right, bottom, 0.0f, glyph.w, glyph.B, colorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public RenderBatchBuilder addFontGlyph(float x, float y, SmoothFontGlyph glyph, Color color, float scale) {
        return this.addGlyphQuad(11.0f, x, y, glyph, BufferedGuiRenderPrimitives.fontTexture, color, scale);
    }

    public RenderBatchBuilder addCornerMaskedRoundedRect(float x, float y, float width, float height, Color color, float cornerRadius, float edgeSoftness, int cornerMask) {
        float antialiasPadding = 0.5f;
        if (cornerRadius <= 0.0f) {
            antialiasPadding = 0.0f;
        }
        float shaderRadius = Math.max(0.0f, (cornerRadius += antialiasPadding * 2.0f) - edgeSoftness);
        if (cornerRadius > 0.0f) {
            x -= edgeSoftness - 0.5f;
            y -= edgeSoftness;
            height += edgeSoftness;
            width += edgeSoftness;
        }
        float left = x + antialiasPadding;
        float right = x + width - antialiasPadding;
        float top = y + antialiasPadding;
        float bottom = y + height - antialiasPadding;
        boolean topLeft = (cornerMask & 1) != 0;
        boolean topRight = (cornerMask & 2) != 0;
        boolean bottomRight = (cornerMask & 4) != 0;
        boolean bottomLeft = (cornerMask & 8) != 0;
        RenderVector4f enabledCorners = new RenderVector4f(topLeft ? 1.0f : 0.0f, topRight ? 1.0f : 0.0f, bottomRight ? 1.0f : 0.0f, bottomLeft ? 1.0f : 0.0f);
        RenderVector4f innerRect = new RenderVector4f(x + cornerRadius, y + cornerRadius, x + width - cornerRadius, y + height - cornerRadius);
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendParameterizedShapeVertex(5.0f, left, top, 0.0f, shaderRadius, innerRect, edgeSoftness, colorVector, enabledCorners);
        this.appendParameterizedShapeVertex(5.0f, left, bottom, 0.0f, shaderRadius, innerRect, edgeSoftness, colorVector, enabledCorners);
        this.appendParameterizedShapeVertex(5.0f, right, top, 0.0f, shaderRadius, innerRect, edgeSoftness, colorVector, enabledCorners);
        this.appendParameterizedShapeVertex(5.0f, right, bottom, 0.0f, shaderRadius, innerRect, edgeSoftness, colorVector, enabledCorners);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public RenderBatchBuilder addTexturedRect(float x, float y, float width, float height, float textureWidth, float textureHeight, float minU, float minV, float maxU, float maxV, Color color) {
        float right;
        if (width == height) {
            float textureAspectRatio = textureWidth / textureHeight;
            width *= textureAspectRatio;
        }
        right = x + width;
        float bottom = y + height;
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendTexturedVertex(10.0f, x, y, 0.0f, minU, minV, colorVector);
        this.appendTexturedVertex(10.0f, x, bottom, 0.0f, minU, maxV, colorVector);
        this.appendTexturedVertex(10.0f, right, y, 0.0f, maxU, minV, colorVector);
        this.appendTexturedVertex(10.0f, right, bottom, 0.0f, maxU, maxV, colorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    private void appendBlurVertex(float shaderMode, float x, float y, float z, float u, float v, float feather, float blurRadius, float radiusX, float direction, RenderVector4f innerRect, FloatPair screenDimensions) {
        RenderVector3f position = new RenderVector3f(x, y, z);
        FloatPair textureCoordinates = new FloatPair(u, v);
        this.appendVertex(shaderMode, position, textureCoordinates, this.zeroVector4, 0.0f, feather, this.zeroPair, 0.0f, 0.0f, screenDimensions, this.zeroPair, innerRect, new RenderVector3f(radiusX, blurRadius, 0.0f), direction, this.zeroVector4);
    }

    private void appendArcStrokeVertex(float shaderMode, float x, float y, float z, float innerRadius, float outerRadius, float feather, FloatPair center, float middleAngle, float sweepAngle, RenderVector4f color) {
        RenderVector3f position = new RenderVector3f(x, y, z);
        this.appendVertex(shaderMode, position, this.zeroPair, color, innerRadius, feather, center, middleAngle, sweepAngle, this.zeroPair, this.zeroPair, this.zeroVector4, new RenderVector3f(outerRadius, 0.0f, 0.0f), 0.0f, this.zeroVector4);
    }

    public int[] getIndices() {
        return this.indices;
    }

    public RenderBatchBuilder addGradientPill(float x, float y, float width, float height, float[] startHsba, float[] endHsba) {
        float radius = Math.max(height / 2.0f - 0.5f, 0.25f);
        float centerY = y + height / 2.0f;
        float leftCenterX = x + height / 2.0f;
        float rightCenterX = x + width - height / 2.0f;
        float antialiasPadding = 0.5f;
        float left = x - antialiasPadding;
        float top = y - antialiasPadding;
        float right = x + width + antialiasPadding;
        float bottom = y + height + antialiasPadding;
        RenderVector4f centerLine = new RenderVector4f(leftCenterX, centerY, rightCenterX, centerY);
        RenderVector4f startHsbaVector = new RenderVector4f(startHsba[0] * 255.0f, startHsba[1] * 255.0f, startHsba[2] * 255.0f, startHsba[3] * 255.0f);
        RenderVector4f endHsbaVector = new RenderVector4f(endHsba[0], endHsba[1], endHsba[2], endHsba[3]);
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendParameterizedShapeVertex(20.0f, left, top, 0.0f, radius, centerLine, 0.0f, startHsbaVector, endHsbaVector);
        this.appendParameterizedShapeVertex(20.0f, left, bottom, 0.0f, radius, centerLine, 0.0f, startHsbaVector, endHsbaVector);
        this.appendParameterizedShapeVertex(20.0f, right, top, 0.0f, radius, centerLine, 0.0f, startHsbaVector, endHsbaVector);
        this.appendParameterizedShapeVertex(20.0f, right, bottom, 0.0f, radius, centerLine, 0.0f, startHsbaVector, endHsbaVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public RenderBatchBuilder setStandaloneRenderCallback(Supplier<Void> callback) {
        this.standaloneRenderCallback = callback;
        this.setTopology(PrimitiveTopology.QUADS);
        return this;
    }

    private void generateIndicesForCurrentBatch(String primitiveName) {
        this.generateIndices(primitiveName, RenderBatchManager.getInstance().getLastBatchMergeCount(this.worldSpace));
    }

    public RenderBatchBuilder addMinecraftIntensityFontGlyph(float x, float y, SmoothFontGlyph glyph, int textureId, Color color, float scale) {
        return this.addGlyphQuad(17.0f, x, y, glyph, new GlImageTexture(textureId), color, scale);
    }

    public RenderBatchBuilder addCircleStroke(float x, float y, float diameter, float strokeWidth, float feather, Color color) {
        float boundsWidth = diameter += feather;
        float boundsHeight = diameter;
        float right = (x -= feather / 2.0f) + boundsWidth;
        float bottom = (y -= feather / 2.0f) + boundsHeight;
        float outerRadius = diameter / 2.0f;
        float innerRadius = outerRadius - strokeWidth;
        FloatPair center = new FloatPair(x + boundsWidth / 2.0f, y + boundsHeight / 2.0f);
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendCircleStrokeVertex(3.0f, x, y, 0.0f, innerRadius, outerRadius, feather, center, colorVector);
        this.appendCircleStrokeVertex(3.0f, x, bottom, 0.0f, innerRadius, outerRadius, feather, center, colorVector);
        this.appendCircleStrokeVertex(3.0f, right, y, 0.0f, innerRadius, outerRadius, feather, center, colorVector);
        this.appendCircleStrokeVertex(3.0f, right, bottom, 0.0f, innerRadius, outerRadius, feather, center, colorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public RenderBatchBuilder() {
        this(4);
    }

    public RenderBatchBuilder addGradientQuad(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, Color startColor, Color endColor) {
        RenderVector4f startColorVector = new RenderVector4f(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), startColor.getAlpha());
        RenderVector4f endColorVector = new RenderVector4f(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), endColor.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendSolidColorVertex(9.0f, x2, y2, z2, startColorVector);
        this.appendSolidColorVertex(9.0f, x1, y1, z1, startColorVector);
        this.appendSolidColorVertex(9.0f, x3, y3, z3, endColorVector);
        this.appendSolidColorVertex(9.0f, x4, y4, z4, endColorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public RenderBatchBuilder addDottedLine(float startX, float startY, float endX, float endY, float thickness, float spacing, Color color) {
        float boundsPadding = thickness + spacing;
        float left = Math.min(startX, endX) - boundsPadding;
        float right = Math.max(startX, endX) + boundsPadding;
        float top = Math.min(startY, endY) - boundsPadding;
        float bottom = Math.max(startY, endY) + boundsPadding;
        FloatPair start = new FloatPair(startX, startY);
        FloatPair end = new FloatPair(endX, endY);
        RenderVector3f lineParameters = new RenderVector3f(thickness, spacing, 0.0f);
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendVertex(18.0f, new RenderVector3f(left, top, 0.0f), this.zeroPair, colorVector, 0.0f, 0.0f, start, 0.0f, 0.0f, this.zeroPair, end, this.zeroVector4, lineParameters, 0.0f, this.zeroVector4);
        this.appendVertex(18.0f, new RenderVector3f(left, bottom, 0.0f), this.zeroPair, colorVector, 0.0f, 0.0f, start, 0.0f, 0.0f, this.zeroPair, end, this.zeroVector4, lineParameters, 0.0f, this.zeroVector4);
        this.appendVertex(18.0f, new RenderVector3f(right, top, 0.0f), this.zeroPair, colorVector, 0.0f, 0.0f, start, 0.0f, 0.0f, this.zeroPair, end, this.zeroVector4, lineParameters, 0.0f, this.zeroVector4);
        this.appendVertex(18.0f, new RenderVector3f(right, bottom, 0.0f), this.zeroPair, colorVector, 0.0f, 0.0f, start, 0.0f, 0.0f, this.zeroPair, end, this.zeroVector4, lineParameters, 0.0f, this.zeroVector4);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public VertexCoordinateMode getCoordinateMode() {
        return this.coordinateMode;
    }

    public void setVertexValue(int index, float value) {
        this.vertexData[index] = value;
    }

    public PrimitiveTopology getTopology() {
        return this.topology;
    }

    private void appendParameterizedShapeVertex(float shaderMode, float x, float y, float z, float radius, RenderVector4f innerRect, float spread, RenderVector4f color, RenderVector4f corners) {
        RenderVector3f position = new RenderVector3f(x, y, z);
        this.appendVertex(shaderMode, position, this.zeroPair, color, 0.0f, 0.0f, this.zeroPair, 0.0f, 0.0f, this.zeroPair, this.zeroPair, innerRect, new RenderVector3f(radius, 0.0f, 0.0f), spread, corners);
    }

    private void appendVertex(float shaderMode, RenderVector3f position, FloatPair textureCoordinates, RenderVector4f color, float inner, float feather, FloatPair center, float middleAngle, float sweepAngle, FloatPair screenDimensions, FloatPair origin, RenderVector4f innerRect, RenderVector3f radius, float spread, RenderVector4f corners) {
        float colorChannelScale = 0.003921569f;
        float[] encodedVertex = new float[]{shaderMode, position.x, position.y, position.z, textureCoordinates.first, textureCoordinates.second, color.x * colorChannelScale, color.y * colorChannelScale, color.z * colorChannelScale, color.w * colorChannelScale, inner, feather, center.first, center.second, middleAngle, sweepAngle, screenDimensions.first, screenDimensions.second, origin.first, origin.second, innerRect.x, innerRect.y, innerRect.z, innerRect.w, radius.x, radius.y, radius.z, spread, corners.x, corners.y, corners.z, corners.w};
        int destinationOffset = this.vertexCount * RenderBatchManager.getInstance().getBatchBuffer().getVertexStride();
        System.arraycopy(encodedVertex, 0, this.vertexData, destinationOffset, encodedVertex.length);
        ++this.vertexCount;
    }

    public Supplier<Void> getDrawSetupCallback() {
        return this.drawSetupCallback;
    }

    public RenderBatchBuilder setDrawSetupCallback(Supplier<Void> callback) {
        this.drawSetupCallback = callback;
        return this;
    }

    private void appendRoundedShapeBoundsVertex(float shaderMode, float x, float y, float z, RenderVector3f radius, RenderVector4f innerRect, RenderVector4f color) {
        RenderVector3f position = new RenderVector3f(x, y, z);
        this.appendVertex(shaderMode, position, this.zeroPair, color, 0.0f, 0.0f, this.zeroPair, 0.0f, 0.0f, this.zeroPair, this.zeroPair, innerRect, radius, 0.0f, this.zeroVector4);
    }

    public RenderBatchBuilder addSolidRect(float x, float y, float width, float height, Color color) {
        float right = x + width;
        float bottom = y + height;
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendSolidColorVertex(9.0f, x, y, 0.0f, colorVector);
        this.appendSolidColorVertex(9.0f, x, bottom, 0.0f, colorVector);
        this.appendSolidColorVertex(9.0f, right, y, 0.0f, colorVector);
        this.appendSolidColorVertex(9.0f, right, bottom, 0.0f, colorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public RenderBatchBuilder addLine(float x1, float y1, float z1, float x2, float y2, float z2, float width, Color color) {
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.LINES);
        this.setLineWidth(width);
        this.appendSolidColorVertex(9.0f, x1, y1, z1, colorVector);
        this.appendSolidColorVertex(9.0f, x2, y2, z2, colorVector);
        this.generateIndicesForCurrentBatch("line");
        return this;
    }

    public RenderBatchBuilder addCapsule(float x, float y, float width, float height, Color color) {
        float radius = Math.max(height / 2.0f - 0.5f, 0.25f);
        float centerY = y + height / 2.0f;
        float leftCenterX = x + height / 2.0f;
        float rightCenterX = x + width - height / 2.0f;
        float antialiasPadding = 0.5f;
        float left = x - antialiasPadding;
        float top = y - antialiasPadding;
        float right = x + width + antialiasPadding;
        float bottom = y + height + antialiasPadding;
        RenderVector4f centerLine = new RenderVector4f(leftCenterX, centerY, rightCenterX, centerY);
        RenderVector3f capsuleParameters = new RenderVector3f(radius, 0.0f, 0.0f);
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendRoundedShapeBoundsVertex(19.0f, left, top, 0.0f, capsuleParameters, centerLine, colorVector);
        this.appendRoundedShapeBoundsVertex(19.0f, left, bottom, 0.0f, capsuleParameters, centerLine, colorVector);
        this.appendRoundedShapeBoundsVertex(19.0f, right, top, 0.0f, capsuleParameters, centerLine, colorVector);
        this.appendRoundedShapeBoundsVertex(19.0f, right, bottom, 0.0f, capsuleParameters, centerLine, colorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public RenderBatchBuilder addArcStroke(float x, float y, float diameter, float strokeWidth, float feather, float startAngle, float sweepAngle, Color color) {
        float boundsSize = diameter += feather * 4.0f;
        float halfBoundsSize = boundsSize / 2.0f;
        FloatPair center = new FloatPair((x -= feather * 2.0f) + halfBoundsSize, (y -= feather * 2.0f) + halfBoundsSize);
        float halfStrokeWidth = strokeWidth /= 2.0f;
        float outerRadius = halfBoundsSize - halfStrokeWidth - 1.0f;
        float innerRadius = outerRadius - halfStrokeWidth;
        float right = x + boundsSize;
        float bottom = y + boundsSize;
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        if (sweepAngle != -360.0f) {
            sweepAngle %= 360.0f;
        }
        float middleAngle = startAngle % 360.0f + sweepAngle * 0.5f;
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendArcStrokeVertex(0.0f, x, y, 0.0f, innerRadius, outerRadius, feather, center, middleAngle, sweepAngle, colorVector);
        this.appendArcStrokeVertex(0.0f, x, bottom, 0.0f, innerRadius, outerRadius, feather, center, middleAngle, sweepAngle, colorVector);
        this.appendArcStrokeVertex(0.0f, right, y, 0.0f, innerRadius, outerRadius, feather, center, middleAngle, sweepAngle, colorVector);
        this.appendArcStrokeVertex(0.0f, right, bottom, 0.0f, innerRadius, outerRadius, feather, center, middleAngle, sweepAngle, colorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public RenderBatchBuilder(int vertexCapacity) {
        this(vertexCapacity, VertexCoordinateMode.DEFAULT, false);
    }

    public GlImageTexture getTexture() {
        return this.texture;
    }

    public GlScissorRect getScissorRect() {
        return this.scissorRect;
    }

    public RenderBatchBuilder addItemTextureRect(float x, float y, float width, float height, float textureWidth, float textureHeight, float minU, float minV, float maxU, float maxV, Color color) {
        float right;
        if (width == height) {
            float textureAspectRatio = textureWidth / textureHeight;
            width *= textureAspectRatio;
        }
        right = x + width;
        float bottom = y + height;
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendTexturedVertex(14.0f, x, y, 0.0f, minU, minV, colorVector);
        this.appendTexturedVertex(14.0f, x, bottom, 0.0f, minU, maxV, colorVector);
        this.appendTexturedVertex(14.0f, right, y, 0.0f, maxU, minV, colorVector);
        this.appendTexturedVertex(14.0f, right, bottom, 0.0f, maxU, maxV, colorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public RenderBatchBuilder addMinecraftFontGlyph(float x, float y, SmoothFontGlyph glyph, int textureId, Color color, float scale) {
        return this.addGlyphQuad(15.0f, x, y, glyph, new GlImageTexture(textureId), color, scale);
    }

    public RenderBatchBuilder addQuad(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, Color color) {
        return this.addGradientQuad(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, color, color);
    }

    public RenderBatchBuilder addCompositeRoundedRect(float x, float y, float width, float height, float shadowSpread, float cornerRadius, Color shadowColor, float shadowOffsetX, float shadowOffsetY, float strokeWidth, Color strokeColor, Color fillColor, float circleThickness, float circleYOffset, Color circleColor, boolean texturePassthrough) {
        if (width == 0.0f || height == 0.0f) {
            return this;
        }
        float left = x - shadowSpread;
        float top = y - shadowSpread;
        float right = x + width + shadowSpread;
        float bottom = y + height + shadowSpread;
        float channelScale = 0.003921569f;
        RenderVector4f shadowColorVector = new RenderVector4f(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), shadowColor.getAlpha());
        RenderVector4f strokeColorVector = new RenderVector4f(strokeColor.getRed() * channelScale, strokeColor.getGreen() * channelScale, strokeColor.getBlue() * channelScale, strokeColor.getAlpha() * channelScale);
        FloatPair fillRedGreen = new FloatPair(fillColor.getRed() * channelScale, fillColor.getGreen() * channelScale);
        FloatPair fillBlueAlpha = new FloatPair(fillColor.getBlue() * channelScale, fillColor.getAlpha() * channelScale);
        FloatPair circleBlueAlpha = new FloatPair(circleColor.getBlue() * channelScale, circleColor.getAlpha() * channelScale);
        float circleRed = circleColor.getRed() * channelScale;
        float circleGreen = circleColor.getGreen() * channelScale;
        RenderVector4f rect = new RenderVector4f(x, y, width, height);
        RenderVector3f radiusAndCircleRedGreen = new RenderVector3f(cornerRadius, circleRed, circleGreen);
        FloatPair shadowOffset = new FloatPair(shadowOffsetX, shadowOffsetY);
        float texturePassthroughFlag = texturePassthrough ? 1.0f : 0.0f;
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendVertex(13.0f, new RenderVector3f(left, top, 0.0f), circleBlueAlpha, shadowColorVector, strokeWidth, circleYOffset, shadowOffset, circleThickness, texturePassthroughFlag, fillRedGreen, fillBlueAlpha, rect, radiusAndCircleRedGreen, shadowSpread, strokeColorVector);
        this.appendVertex(13.0f, new RenderVector3f(left, bottom, 0.0f), circleBlueAlpha, shadowColorVector, strokeWidth, circleYOffset, shadowOffset, circleThickness, texturePassthroughFlag, fillRedGreen, fillBlueAlpha, rect, radiusAndCircleRedGreen, shadowSpread, strokeColorVector);
        this.appendVertex(13.0f, new RenderVector3f(right, top, 0.0f), circleBlueAlpha, shadowColorVector, strokeWidth, circleYOffset, shadowOffset, circleThickness, texturePassthroughFlag, fillRedGreen, fillBlueAlpha, rect, radiusAndCircleRedGreen, shadowSpread, strokeColorVector);
        this.appendVertex(13.0f, new RenderVector3f(right, bottom, 0.0f), circleBlueAlpha, shadowColorVector, strokeWidth, circleYOffset, shadowOffset, circleThickness, texturePassthroughFlag, fillRedGreen, fillBlueAlpha, rect, radiusAndCircleRedGreen, shadowSpread, strokeColorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public RenderBatchBuilder(int vertexCapacity, VertexCoordinateMode coordinateMode, boolean worldSpace) {
        this.zeroVector3 = new RenderVector3f(0.0f, 0.0f, 0.0f);
        this.zeroVector4 = new RenderVector4f(0.0f, 0.0f, 0.0f, 0.0f);
        this.coordinateMode = coordinateMode;
        this.modelMatrix = BufferedGuiRenderPrimitives.matrixStack.peek().shallowCopy();
        if (BufferedGuiRenderPrimitives.scissorRect != null) {
            this.scissorRect = new GlScissorRect(BufferedGuiRenderPrimitives.scissorRect.x, BufferedGuiRenderPrimitives.scissorRect.y, BufferedGuiRenderPrimitives.scissorRect.width, BufferedGuiRenderPrimitives.scissorRect.height);
        }
        this.worldSpace = worldSpace;
        this.vertexCapacity = vertexCapacity;
        this.capabilityState = BufferedGuiRenderPrimitives.capabilityState.copy();
    }

    public Supplier<Void> getStandaloneRenderCallback() {
        return this.standaloneRenderCallback;
    }

    public void generateIndices(String primitiveName, int precedingBatchCount) {
        this.baseVertexIndex += this.topology.verticesCount * precedingBatchCount;
        switch (primitiveName) {
            case "quad": {
                this.indices[0] = this.baseVertexIndex;
                this.indices[1] = this.baseVertexIndex + 1;
                this.indices[2] = this.baseVertexIndex + 2;
                this.indices[3] = this.baseVertexIndex + 1;
                this.indices[4] = this.baseVertexIndex + 3;
                this.indices[5] = this.baseVertexIndex + 2;
                break;
            }
            case "line": {
                this.indices[0] = this.baseVertexIndex;
                this.indices[1] = this.baseVertexIndex + 1;
                break;
            }
            case "triangle": {
                this.indices[0] = this.baseVertexIndex;
                this.indices[1] = this.baseVertexIndex + 2;
                this.indices[2] = this.baseVertexIndex + 1;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown mode: " + primitiveName);
            }
        }
    }

    public RenderBatchBuilder initializeTopology(PrimitiveTopology topology, int vertexStride) {
        if (this.topology != null) {
            return this;
        }
        this.topology = topology;
        this.vertexData = new float[vertexStride * this.vertexCapacity];
        this.indices = new int[topology.indicesCount];
        return this;
    }

    public GlCapabilityState getCapabilityState() {
        return this.capabilityState;
    }

    public RenderBatchBuilder setTexture(GlImageTexture texture) {
        this.texture = texture;
        return this;
    }

    public RenderBatchBuilder addDefaultRoundedRect(float x, float y, float width, float height, Color color) {
        return this.addCornerMaskedRoundedRect(x, y, width, height, color, 1.5f, 1.0f, 0);
    }

    public RenderBatchBuilder addTexturedCircle(float x, float y, float diameter, float feather, Color color) {
        x -= feather / 2.0f;
        y -= feather / 2.0f;
        float boundsWidth = diameter += feather;
        float boundsHeight = diameter;
        if (color == null) {
            color = Color.WHITE;
        }
        float right = x + boundsWidth;
        float bottom = y + boundsHeight;
        float radius = diameter / 2.0f;
        FloatPair center = new FloatPair(x + boundsWidth / 2.0f, y + boundsHeight / 2.0f);
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        float minU = this.texture != null ? this.texture.minU : 0.0f;
        float minV = this.texture != null ? this.texture.minV : 0.0f;
        float maxU = this.texture != null ? this.texture.maxU : 1.0f;
        float maxV = this.texture != null ? this.texture.maxV : 1.0f;
        this.appendTexturedCircleVertexData(4.0f, x, y, 0.0f, minU, minV, radius, feather, center, colorVector);
        this.appendTexturedCircleVertexData(4.0f, x, bottom, 0.0f, minU, maxV, radius, feather, center, colorVector);
        this.appendTexturedCircleVertexData(4.0f, right, y, 0.0f, maxU, minV, radius, feather, center, colorVector);
        this.appendTexturedCircleVertexData(4.0f, right, bottom, 0.0f, maxU, maxV, radius, feather, center, colorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    private void appendInvertedRoundedRectVertex(float shaderMode, float x, float y, float z, RenderVector4f innerRect, float radius, float feather, RenderVector4f corners, RenderVector4f color) {
        RenderVector3f position = new RenderVector3f(x, y, z);
        RenderVector3f radiusVector = new RenderVector3f(radius, 0.0f, 0.0f);
        this.appendVertex(shaderMode, position, this.zeroPair, color, 0.0f, feather, this.zeroPair, 0.0f, 0.0f, this.zeroPair, this.zeroPair, innerRect, radiusVector, 0.0f, corners);
    }

    public RenderBatchBuilder addInvertedRoundedRectCorners(float x, float y, float width, float height, float cornerRadius, float feather, int cornerMask, Color color) {
        float antialiasPadding = 0.5f;
        if (cornerRadius <= 0.0f) {
            antialiasPadding = 0.0f;
        }
        float edgeFeather = feather;
        float shaderRadius = Math.max(0.0f, (cornerRadius += antialiasPadding * 2.0f) - edgeFeather);
        if (cornerRadius > 0.0f) {
            x = (float)((double)x - ((double)edgeFeather - 0.5));
            y -= edgeFeather;
            height = (float)((double)height + (double)edgeFeather * 1.5);
            width += edgeFeather;
        }
        float left = x + antialiasPadding;
        float right = x + width - antialiasPadding;
        float top = y + antialiasPadding;
        float bottom = y + height - antialiasPadding;
        boolean topLeft = (cornerMask & 1) != 0;
        boolean topRight = (cornerMask & 2) != 0;
        boolean bottomRight = (cornerMask & 4) != 0;
        boolean bottomLeft = (cornerMask & 8) != 0;
        RenderVector4f enabledCorners = new RenderVector4f(topLeft ? 1.0f : 0.0f, topRight ? 1.0f : 0.0f, bottomRight ? 1.0f : 0.0f, bottomLeft ? 1.0f : 0.0f);
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        RenderVector4f innerRect = new RenderVector4f(x + cornerRadius, y + cornerRadius, x + width - cornerRadius, y + height - cornerRadius);
        this.appendInvertedRoundedRectVertex(12.0f, left, top, 0.0f, innerRect, shaderRadius, edgeFeather, enabledCorners, colorVector);
        this.appendInvertedRoundedRectVertex(12.0f, left, bottom, 0.0f, innerRect, shaderRadius, edgeFeather, enabledCorners, colorVector);
        this.appendInvertedRoundedRectVertex(12.0f, right, top, 0.0f, innerRect, shaderRadius, edgeFeather, enabledCorners, colorVector);
        this.appendInvertedRoundedRectVertex(12.0f, right, bottom, 0.0f, innerRect, shaderRadius, edgeFeather, enabledCorners, colorVector);
        return this;
    }

    private void appendSolidColorVertex(float shaderMode, float x, float y, float z, RenderVector4f color) {
        this.appendSolidColorVertexData(shaderMode, x, y, z, this.zeroVector4, 0.0f, color);
    }

    public RenderBatchBuilder addRoundedRectWithCornerRadius(float x, float y, float width, float height, Color color, float cornerRadius) {
        return this.addCornerMaskedRoundedRect(x, y, width, height, color, cornerRadius, 1.0f, 0);
    }

    public RenderBatchBuilder addRoundedRectShadow(float x, float y, float width, float height, float spread, float cornerRadius, Color color) {
        float left = x - spread;
        float right = x + width + spread;
        float top = y - spread;
        float bottom = y + height + spread;
        RenderVector4f rect = new RenderVector4f(x, y, width, height);
        RenderVector4f colorVector = new RenderVector4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendRoundedShapeVertex(8.0f, left, top, 0.0f, rect, spread, cornerRadius, colorVector);
        this.appendRoundedShapeVertex(8.0f, left, bottom, 0.0f, rect, spread, cornerRadius, colorVector);
        this.appendRoundedShapeVertex(8.0f, right, top, 0.0f, rect, spread, cornerRadius, colorVector);
        this.appendRoundedShapeVertex(8.0f, right, bottom, 0.0f, rect, spread, cornerRadius, colorVector);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    private void appendCircleVertexData(float shaderMode, float x, float y, float z, float radius, float feather, FloatPair center, RenderVector4f color) {
        this.appendTexturedCircleVertexData(shaderMode, x, y, z, 0.0f, 0.0f, radius, feather, center, color);
    }

    private void appendTexturedVertex(float shaderMode, float x, float y, float z, float u, float v, RenderVector4f color) {
        this.appendTexturedShapeVertex(shaderMode, x, y, z, u, v, 0.0f, this.zeroVector4, 0.0f, color);
    }

    public RenderBatchBuilder addBlurPass(float x, float y, float width, float height, float blurRadius, float inset, float feather, float direction) {
        float minU = 0.0f;
        float maxV = 1.0f;
        float maxU = 1.0f;
        float minV = 0.0f;
        float right = x + width;
        float bottom = y + height;
        RenderVector4f innerRect = new RenderVector4f(x + inset, y + inset, x + width - inset, y + height - inset);
        FloatPair screenDimensions = new FloatPair(width * 2.0f, height * 2.0f);
        this.setTopology(PrimitiveTopology.QUADS);
        this.appendBlurVertex(1.0f, x, y, 0.0f, minU, maxV, feather, blurRadius, inset, direction, innerRect, screenDimensions);
        this.appendBlurVertex(1.0f, x, bottom, 0.0f, minU, minV, feather, blurRadius, inset, direction, innerRect, screenDimensions);
        this.appendBlurVertex(1.0f, right, y, 0.0f, maxU, maxV, feather, blurRadius, inset, direction, innerRect, screenDimensions);
        this.appendBlurVertex(1.0f, right, bottom, 0.0f, maxU, minV, feather, blurRadius, inset, direction, innerRect, screenDimensions);
        this.generateIndicesForCurrentBatch("quad");
        return this;
    }

    public RenderBatchBuilder addMinecraftColorFontGlyph(float x, float y, SmoothFontGlyph glyph, int textureId, Color color, float scale) {
        return this.addGlyphQuad(16.0f, x, y, glyph, new GlImageTexture(textureId), color, scale);
    }

    public RenderBatchBuilder addDefaultShaderGradientQuad(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, Color startColor, Color endColor) {
        return this.addGradientQuad(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, startColor, endColor);
    }
}
