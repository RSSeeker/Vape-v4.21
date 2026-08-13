package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.unmap.ImageParser;
import gg.vape.unmap.ImageParser$Format;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.TextureAtlasRegion;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;

public class TextureAtlas {
    private int width = 128;
    private final Map<String, TextureAtlasRegion> regions;
    private final int growthStep;
    private int height = 128;
    private final List<Rectangle> occupiedRectangles;
    private BufferedImage image = new BufferedImage(this.width, this.height, 2);
    private GlImageTexture texture;
    private final int padding;

    private BufferedImage addEdgePadding(BufferedImage sourceImage) {
        int paddingOffset;
        int edgeColor;
        int sourceCoordinate;
        int paddedWidth = sourceImage.getWidth() + 20;
        int paddedHeight = sourceImage.getHeight() + 20;
        BufferedImage paddedImage = new BufferedImage(paddedWidth, paddedHeight, 2);
        Graphics2D graphics = paddedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.drawImage((Image)sourceImage, 10, 10, null);
        for (sourceCoordinate = 0; sourceCoordinate < sourceImage.getWidth(); ++sourceCoordinate) {
            edgeColor = sourceImage.getRGB(sourceCoordinate, 0);
            for (paddingOffset = 0; paddingOffset < 10; ++paddingOffset) {
                paddedImage.setRGB(sourceCoordinate + 10, paddingOffset, edgeColor);
            }
            edgeColor = sourceImage.getRGB(sourceCoordinate, sourceImage.getHeight() - 1);
            for (paddingOffset = 0; paddingOffset < 10; ++paddingOffset) {
                paddedImage.setRGB(sourceCoordinate + 10, paddedHeight - 1 - paddingOffset, edgeColor);
            }
        }
        for (sourceCoordinate = 0; sourceCoordinate < sourceImage.getHeight(); ++sourceCoordinate) {
            edgeColor = sourceImage.getRGB(0, sourceCoordinate);
            for (paddingOffset = 0; paddingOffset < 10; ++paddingOffset) {
                paddedImage.setRGB(paddingOffset, sourceCoordinate + 10, edgeColor);
            }
            edgeColor = sourceImage.getRGB(sourceImage.getWidth() - 1, sourceCoordinate);
            for (paddingOffset = 0; paddingOffset < 10; ++paddingOffset) {
                paddedImage.setRGB(paddedWidth - 1 - paddingOffset, sourceCoordinate + 10, edgeColor);
            }
        }
        graphics.dispose();
        return paddedImage;
    }

    private static int nextPowerOfTwo(int value) {
        int result;
        for (result = 1; result < value; result *= 2) {
        }
        return result;
    }

    private int[] findAvailablePosition(int imageWidth, int imageHeight) {
        int allocationWidth = imageWidth + 10;
        int allocationHeight = imageHeight + 10;
        for (int y = 0; y <= this.height - allocationHeight; ++y) {
            for (int x = 0; x <= this.width - allocationWidth; ++x) {
                Rectangle candidate = new Rectangle(x, y, allocationWidth, allocationHeight);
                boolean available = true;
                for (Rectangle occupied : this.occupiedRectangles) {
                    if (!candidate.intersects(occupied)) continue;
                    available = false;
                    break;
                }
                if (!available) continue;
                this.occupiedRectangles.add(candidate);
                return new int[]{x, y};
            }
        }
        return null;
    }

    private void uploadTexture() {
        if (this.texture != null) {
            this.texture.delete();
        }
        int[] pixels = new int[this.image.getHeight() * this.image.getWidth()];
        this.image.getRGB(0, 0, this.image.getWidth(), this.image.getHeight(), pixels, 0, this.image.getWidth());
        ByteBuffer pixelBuffer = BufferUtils.createByteBuffer((int)(this.image.getWidth() * this.image.getHeight() * 4));
        for (int y = 0; y < this.image.getHeight(); ++y) {
            for (int x = 0; x < this.image.getWidth(); ++x) {
                int color = pixels[y * this.image.getWidth() + x];
                pixelBuffer.put((byte)(color >> 16 & 0xFF));
                pixelBuffer.put((byte)(color >> 8 & 0xFF));
                pixelBuffer.put((byte)(color & 0xFF));
                pixelBuffer.put((byte)(color >> 24 & 0xFF));
            }
        }
        pixelBuffer.flip();
        this.texture = GlImageTexture.create(this.width, this.height, pixelBuffer, 6408, 9987, 10496);
        pixelBuffer.clear();
    }

    public GlImageTexture getTexture() {
        return this.texture;
    }

    public TextureAtlas() {
        this.growthStep = 128;
        this.padding = 10;
        this.regions = new HashMap<String, TextureAtlasRegion>();
        this.occupiedRectangles = new ArrayList<Rectangle>();
    }

    private void expand() {
        int expandedWidth = TextureAtlas.nextPowerOfTwo(this.width + 128);
        int expandedHeight = TextureAtlas.nextPowerOfTwo(this.height + 128);
        BufferedImage expandedImage = new BufferedImage(expandedWidth, expandedHeight, 2);
        Graphics2D graphics = expandedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage((Image)this.image, 0, 0, null);
        graphics.dispose();
        this.image = expandedImage;
        this.width = expandedWidth;
        this.height = expandedHeight;
        for (TextureAtlasRegion region : this.regions.values()) {
            region.minU = (float)region.x / (float)this.width;
            region.minV = (float)region.y / (float)this.height;
            region.maxU = (float)(region.x + region.width) / (float)this.width;
            region.maxV = (float)(region.y + region.height) / (float)this.height;
        }
    }

    public void saveDebugImage(String path) {
        try {
            File file = new File(path);
            ImageIO.write((RenderedImage)this.image, "PNG", file);
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    public void addImage(String regionName, byte[] imageData, boolean whiteFormat) {
        try {
            if (this.regions.containsKey(regionName)) {
                return;
            }
            ImageParser parser = new ImageParser(new ByteArrayInputStream(imageData));
            int imageHeight = parser.getHeight();
            int imageWidth = parser.getWidth();
            int bytesPerPixel = 4;
            ByteBuffer pixelBuffer = BufferUtils.createByteBuffer((int)(4 * imageHeight * imageWidth));
            parser.decode(pixelBuffer, imageWidth * 4, whiteFormat ? ImageParser$Format.WHITE : ImageParser$Format.RGBA);
            pixelBuffer.flip();
            byte[] rgbaBytes = new byte[pixelBuffer.remaining()];
            pixelBuffer.get(rgbaBytes);
            BufferedImage decodedImage = this.decodeRgba(rgbaBytes, imageWidth, imageHeight);
            decodedImage = this.addEdgePadding(decodedImage);
            int[] position = this.findAvailablePosition(imageWidth += 20, imageHeight += 20);
            while (position == null) {
                this.expand();
                position = this.findAvailablePosition(imageWidth, imageHeight);
            }
            Graphics2D graphics = this.image.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.drawImage((Image)decodedImage, position[0], position[1], null);
            graphics.dispose();
            this.registerRegion(regionName, position[0] + 10, position[1] + 10, imageWidth - 20, imageHeight - 20);
            this.uploadTexture();
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    private void registerRegion(String regionName, int x, int y, int width, int height) {
        float minU = (float)x / (float)this.width;
        float minV = (float)y / (float)this.height;
        float maxU = (float)(x + width) / (float)this.width;
        float maxV = (float)(y + height) / (float)this.height;
        this.regions.put(regionName, new TextureAtlasRegion(x, y, width, height, minU, minV, maxU, maxV));
    }

    public TextureAtlasRegion getRegion(String regionName) {
        return this.regions.get(regionName);
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    private BufferedImage decodeRgba(byte[] rgbaBytes, int width, int height) {
        if (rgbaBytes.length != width * height * 4) {
            throw new IllegalArgumentException("Unexpected image data length for decoded texture");
        }
        BufferedImage decodedImage = new BufferedImage(width, height, 2);
        int byteIndex = 0;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int red = rgbaBytes[byteIndex++] & 0xFF;
                int green = rgbaBytes[byteIndex++] & 0xFF;
                int blue = rgbaBytes[byteIndex++] & 0xFF;
                int alpha = rgbaBytes[byteIndex++] & 0xFF;
                decodedImage.setRGB(x, y, alpha << 24 | red << 16 | green << 8 | blue);
            }
        }
        return decodedImage;
    }
}
