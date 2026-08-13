package gg.vape.unmap;

import gg.vape.unmap.ImageParser$Format;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.Inflater;

public class ImageParser {
    private int currentChunkType;
    private int bytesPerPixel;
    private final byte zeroByte = 0;
    private int colorType;
    private final int idatChunkType;
    private int currentChunkLength;
    private final int iendChunkType;
    private final byte indexedColorType = 3;
    private byte[] palette;
    private final byte truecolorType = 2;
    private int remainingChunkBytes;
    private final byte[] pngSignature = new byte[]{-119, 80, 78, 71, 13, 10, 26, 10};
    private final byte rgbaColorType = 4;
    private int imageHeight;
    private final int plteChunkType;
    private byte[] transparencyData;
    private byte[] paletteAlpha;
    private final byte[] ioBuffer;
    private final InputStream inputStream;
    private final int trnsChunkType;
    private int bitDepth;
    private final int ihdrChunkType;
    private final byte alphaColorType = 6;
    private int imageWidth;

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    private void writeAbgrPixels(ByteBuffer output, byte[] scanline) {
        int scanlineLength = scanline.length;
        for (int index = 1; index < scanlineLength; index += 4) {
            output.put(scanline[index + 3]).put(scanline[index + 2]).put(scanline[index + 1]).put(scanline[index]);
        }
    }

    private void skipInputBytes(long remainingBytes) throws IOException {
        while (remainingBytes > 0L) {
            long skippedBytes = this.inputStream.skip(remainingBytes);
            if (skippedBytes < 0L) {
                throw new EOFException();
            }
            remainingBytes -= skippedBytes;
        }
    }

    private boolean hasPngSignature(byte[] header) {
        for (int index = 0; index < this.pngSignature.length; ++index) {
            if (header[index] == this.pngSignature[index]) continue;
            return false;
        }
        return true;
    }

    private void writeRgbPixels(ByteBuffer output, byte[] scanline) {
        int scanlineLength = scanline.length;
        for (int index = 1; index < scanlineLength; index += 4) {
            output.put(scanline[index]).put(scanline[index + 1]).put(scanline[index + 2]);
        }
    }

    public int getHeight() {
        return this.imageHeight;
    }

    public ImageParser$Format resolveOutputFormat(ImageParser$Format requestedFormat) {
        switch (this.colorType) {
            case 2: {
                switch (requestedFormat) {
                    case ABGR: 
                    case RGBA: 
                    case BGRA: 
                    case RGB: {
                        return requestedFormat;
                    }
                }
                return ImageParser$Format.RGB;
            }
            case 6: {
                switch (requestedFormat) {
                    case ABGR: 
                    case RGBA: 
                    case BGRA: 
                    case RGB: {
                        return requestedFormat;
                    }
                }
                return ImageParser$Format.RGBA;
            }
            case 0: {
                switch (requestedFormat) {
                    case LUMINANCE: 
                    case ALPHA: {
                        return requestedFormat;
                    }
                }
                return ImageParser$Format.LUMINANCE;
            }
            case 4: {
                return ImageParser$Format.LUMINANCE_ALPHA;
            }
            case 3: {
                switch (requestedFormat) {
                    case ABGR: 
                    case RGBA: 
                    case BGRA: {
                        return requestedFormat;
                    }
                }
                return ImageParser$Format.RGBA;
            }
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void unfilterScanline(byte[] scanline, byte[] previousScanline) throws IOException {
        switch (scanline[0]) {
            case 0: {
                break;
            }
            case 1: {
                this.unfilterSub(scanline);
                break;
            }
            case 2: {
                this.unfilterUp(scanline, previousScanline);
                break;
            }
            case 3: {
                this.unfilterAverage(scanline, previousScanline);
                break;
            }
            case 4: {
                this.unfilterPaeth(scanline, previousScanline);
                break;
            }
            default: {
                throw new IOException("invalide filter type in scanline: " + scanline[0]);
            }
        }
    }

    private void unfilterSub(byte[] scanline) {
        int pixelStride = this.bytesPerPixel;
        int scanlineLength = scanline.length;
        for (int index = pixelStride + 1; index < scanlineLength; ++index) {
            scanline[index] = (byte)(scanline[index] + scanline[index - pixelStride]);
        }
    }

    private int readChunkBytes(byte[] destination, int offset, int length) throws IOException {
        if (length > this.remainingChunkBytes) {
            length = this.remainingChunkBytes;
        }
        this.readFully(destination, offset, length);
        this.remainingChunkBytes -= length;
        return length;
    }

    private void refillInflaterInput(Inflater inflater) throws IOException {
        while (this.remainingChunkBytes == 0) {
            this.skipToNextChunk();
            this.expectChunk(1229209940);
        }
        int bytesRead = this.readChunkBytes(this.ioBuffer, 0, this.ioBuffer.length);
        inflater.setInput(this.ioBuffer, 0, bytesRead);
    }

    public void decodeFlipped(ByteBuffer byteBuffer, int stride, ImageParser$Format outputFormat) throws IOException {
        if (stride <= 0) {
            throw new IllegalArgumentException("stride");
        }
        int initialPosition = byteBuffer.position();
        int rowOffset = (this.imageHeight - 1) * stride;
        byteBuffer.position(initialPosition + rowOffset);
        this.decode(byteBuffer, -stride, outputFormat);
        byteBuffer.position(byteBuffer.position() + rowOffset);
    }

    private void unfilterUp(byte[] scanline, byte[] previousScanline) {
        int scanlineLength = scanline.length;
        for (int index = 1; index < scanlineLength; ++index) {
            scanline[index] = (byte)(scanline[index] + previousScanline[index]);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public ImageParser(InputStream inputStream) throws IOException {
        this.ihdrChunkType = 1229472850;
        this.plteChunkType = 1347179589;
        this.trnsChunkType = 1951551059;
        this.idatChunkType = 1229209940;
        this.iendChunkType = 1229278788;
        this.inputStream = inputStream;
        this.ioBuffer = new byte[4096];
        this.readFully(this.ioBuffer, 0, this.pngSignature.length);
        if (!this.hasPngSignature(this.ioBuffer)) {
            throw new IOException("Not a valid PNG file");
        }
        this.expectChunk(1229472850);
        this.readIhdrChunk();
        this.skipToNextChunk();
        block5: while (true) {
            this.readChunkHeader();
            switch (this.currentChunkType) {
                case 1229209940: {
                    break block5;
                }
                case 1347179589: {
                    this.readPaletteChunk();
                    break;
                }
                case 1951551059: {
                    this.readTransparencyChunk();
                }
            }
            this.skipToNextChunk();
        }
        if (this.colorType == 3 && this.palette == null) {
            throw new IOException("Missing PLTE chunk");
        }
    }

    public boolean hasTransparency() {
        return this.hasAlphaChannel() || this.paletteAlpha != null || this.transparencyData != null;
    }

    public boolean hasAlphaChannel() {
        return this.colorType == 6 || this.colorType == 4;
    }

    public int getWidth() {
        return this.imageWidth;
    }

    private void inflateFully(Inflater inflater, byte[] destination, int offset, int length) throws IOException {
        try {
            do {
                int inflatedBytes;
                if ((inflatedBytes = inflater.inflate(destination, offset, length)) <= 0) {
                    if (inflater.finished()) {
                        throw new EOFException();
                    }
                    if (inflater.needsInput()) {
                        this.refillInflaterInput(inflater);
                        continue;
                    }
                    throw new IOException("Can't inflate " + length + " bytes");
                }
                offset += inflatedBytes;
                length -= inflatedBytes;
            } while (length > 0);
        }
        catch (Exception exception) {
            throw new IOException("inflate error", exception);
        }
    }

    private void writeBgraPalettePixels(ByteBuffer output, byte[] scanline) {
        if (this.paletteAlpha != null) {
            int scanlineLength = scanline.length;
            for (int index = 1; index < scanlineLength; ++index) {
                int paletteIndex = scanline[index] & 0xFF;
                byte red = this.palette[paletteIndex * 3];
                byte green = this.palette[paletteIndex * 3 + 1];
                byte blue = this.palette[paletteIndex * 3 + 2];
                byte alpha = this.paletteAlpha[paletteIndex];
                output.put(blue).put(green).put(red).put(alpha);
            }
        } else {
            int scanlineLength = scanline.length;
            for (int index = 1; index < scanlineLength; ++index) {
                int paletteIndex = scanline[index] & 0xFF;
                byte red = this.palette[paletteIndex * 3];
                byte green = this.palette[paletteIndex * 3 + 1];
                byte blue = this.palette[paletteIndex * 3 + 2];
                byte alpha = -1;
                output.put(blue).put(green).put(red).put(alpha);
            }
        }
    }

    private void requireChunkLength(int expectedLength) throws IOException {
        if (this.currentChunkLength != expectedLength) {
            throw new IOException("Chunk has wrong size");
        }
    }

    private void writeAbgrPalettePixels(ByteBuffer output, byte[] scanline) {
        if (this.paletteAlpha != null) {
            int scanlineLength = scanline.length;
            for (int index = 1; index < scanlineLength; ++index) {
                int paletteIndex = scanline[index] & 0xFF;
                byte red = this.palette[paletteIndex * 3];
                byte green = this.palette[paletteIndex * 3 + 1];
                byte blue = this.palette[paletteIndex * 3 + 2];
                byte alpha = this.paletteAlpha[paletteIndex];
                output.put(alpha).put(blue).put(green).put(red);
            }
        } else {
            int scanlineLength = scanline.length;
            for (int index = 1; index < scanlineLength; ++index) {
                int paletteIndex = scanline[index] & 0xFF;
                byte red = this.palette[paletteIndex * 3];
                byte green = this.palette[paletteIndex * 3 + 1];
                byte blue = this.palette[paletteIndex * 3 + 2];
                byte alpha = -1;
                output.put(alpha).put(blue).put(green).put(red);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void decode(ByteBuffer output, int stride, ImageParser$Format outputFormat) throws IOException {
        int initialPosition = output.position();
        int scanlineByteCount = (this.imageWidth * this.bitDepth + 7) / 8 * this.bytesPerPixel;
        byte[] currentScanline = new byte[scanlineByteCount + 1];
        byte[] previousScanline = new byte[scanlineByteCount + 1];
        byte[] unpackedScanline = this.bitDepth < 8 ? new byte[this.imageWidth + 1] : null;
        Inflater inflater = new Inflater();
        try {
            for (int row = 0; row < this.imageHeight; ++row) {
                this.inflateFully(inflater, currentScanline, 0, currentScanline.length);
                this.unfilterScanline(currentScanline, previousScanline);
                output.position(initialPosition + row * stride);
                block1 : switch (this.colorType) {
                    case 2: {
                        switch (outputFormat) {
                            case ABGR: {
                                this.writeAbgrPixelsWithTransparency(output, currentScanline);
                                break block1;
                            }
                            case RGBA: {
                                this.writeRgbaPixelsWithTransparency(output, currentScanline);
                                break block1;
                            }
                            case BGRA: {
                                this.writeBgraPixelsWithTransparency(output, currentScanline);
                                break block1;
                            }
                            case RGB: {
                                this.writeRawPixels(output, currentScanline);
                                break block1;
                            }
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    case 6: {
                        switch (outputFormat) {
                            case ABGR: {
                                this.writeAbgrPixels(output, currentScanline);
                                break block1;
                            }
                            case RGBA: {
                                this.writeRawPixels(output, currentScanline);
                                break block1;
                            }
                            case WHITE: {
                                this.writeWhitePixels(output, currentScanline);
                                break block1;
                            }
                            case BGRA: {
                                this.writeBgraPixels(output, currentScanline);
                                break block1;
                            }
                            case RGB: {
                                this.writeRgbPixels(output, currentScanline);
                                break block1;
                            }
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    case 0: {
                        switch (outputFormat) {
                            case LUMINANCE: 
                            case ALPHA: {
                                this.writeRawPixels(output, currentScanline);
                                break block1;
                            }
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    case 4: {
                        switch (outputFormat) {
                            case LUMINANCE_ALPHA: {
                                this.writeRawPixels(output, currentScanline);
                                break block1;
                            }
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    case 3: {
                        switch (this.bitDepth) {
                            case 8: {
                                unpackedScanline = currentScanline;
                                break;
                            }
                            case 4: {
                                this.unpack4BitSamples(currentScanline, unpackedScanline);
                                break;
                            }
                            case 2: {
                                this.unpack2BitSamples(currentScanline, unpackedScanline);
                                break;
                            }
                            case 1: {
                                this.unpack1BitSamples(currentScanline, unpackedScanline);
                                break;
                            }
                            default: {
                                throw new UnsupportedOperationException("Unsupported bitdepth for this image");
                            }
                        }
                        switch (outputFormat) {
                            case ABGR: {
                                this.writeAbgrPalettePixels(output, unpackedScanline);
                                break block1;
                            }
                            case RGBA: {
                                this.writeRgbaPalettePixels(output, unpackedScanline);
                                break block1;
                            }
                            case BGRA: {
                                this.writeBgraPalettePixels(output, unpackedScanline);
                                break block1;
                            }
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    default: {
                        throw new UnsupportedOperationException("Not yet implemented");
                    }
                }
                byte[] completedScanline = currentScanline;
                currentScanline = previousScanline;
                previousScanline = completedScanline;
            }
        }
        finally {
            inflater.end();
        }
    }

    private void writeWhitePixels(ByteBuffer output, byte[] scanline) {
        int scanlineLength = scanline.length;
        for (int index = 1; index < scanlineLength; index += 4) {
            output.put((byte)-1).put((byte)-1).put((byte)-1).put(scanline[index + 3]);
        }
    }

    private void readIhdrChunk() throws IOException {
        this.requireChunkLength(13);
        this.readChunkBytes(this.ioBuffer, 0, 13);
        this.imageWidth = this.readInt32(this.ioBuffer, 0);
        this.imageHeight = this.readInt32(this.ioBuffer, 4);
        this.bitDepth = this.ioBuffer[8] & 0xFF;
        this.colorType = this.ioBuffer[9] & 0xFF;
        block0 : switch (this.colorType) {
            case 0: {
                if (this.bitDepth != 8) {
                    throw new IOException("Unsupported bit depth: " + this.bitDepth);
                }
                this.bytesPerPixel = 1;
                break;
            }
            case 4: {
                if (this.bitDepth != 8) {
                    throw new IOException("Unsupported bit depth: " + this.bitDepth);
                }
                this.bytesPerPixel = 2;
                break;
            }
            case 2: {
                if (this.bitDepth != 8) {
                    throw new IOException("Unsupported bit depth: " + this.bitDepth);
                }
                this.bytesPerPixel = 3;
                break;
            }
            case 6: {
                if (this.bitDepth != 8) {
                    throw new IOException("Unsupported bit depth: " + this.bitDepth);
                }
                this.bytesPerPixel = 4;
                break;
            }
            case 3: {
                switch (this.bitDepth) {
                    case 1: 
                    case 2: 
                    case 4: 
                    case 8: {
                        this.bytesPerPixel = 1;
                        break block0;
                    }
                }
                throw new IOException("Unsupported bit depth: " + this.bitDepth);
            }
            default: {
                throw new IOException("unsupported color format: " + this.colorType);
            }
        }
        if (this.ioBuffer[10] != 0) {
            throw new IOException("unsupported compression method");
        }
        if (this.ioBuffer[11] != 0) {
            throw new IOException("unsupported filtering method");
        }
        if (this.ioBuffer[12] != 0) {
            throw new IOException("unsupported interlace method");
        }
    }

    private void writeRgbaPalettePixels(ByteBuffer output, byte[] scanline) {
        if (this.paletteAlpha != null) {
            int scanlineLength = scanline.length;
            for (int index = 1; index < scanlineLength; ++index) {
                int paletteIndex = scanline[index] & 0xFF;
                byte red = this.palette[paletteIndex * 3];
                byte green = this.palette[paletteIndex * 3 + 1];
                byte blue = this.palette[paletteIndex * 3 + 2];
                byte alpha = this.paletteAlpha[paletteIndex];
                output.put(red).put(green).put(blue).put(alpha);
            }
        } else {
            int scanlineLength = scanline.length;
            for (int index = 1; index < scanlineLength; ++index) {
                int paletteIndex = scanline[index] & 0xFF;
                byte red = this.palette[paletteIndex * 3];
                byte green = this.palette[paletteIndex * 3 + 1];
                byte blue = this.palette[paletteIndex * 3 + 2];
                byte alpha = -1;
                output.put(red).put(green).put(blue).put(alpha);
            }
        }
    }

    private void unpack4BitSamples(byte[] packedScanline, byte[] unpackedScanline) {
        int outputLength = unpackedScanline.length;
        for (int outputIndex = 1; outputIndex < outputLength; outputIndex += 2) {
            int packedByte = packedScanline[1 + (outputIndex >> 1)] & 0xFF;
            switch (outputLength - outputIndex) {
                default: {
                    unpackedScanline[outputIndex + 1] = (byte)(packedByte & 0xF);
                }
                case 1: 
            }
            unpackedScanline[outputIndex] = (byte)(packedByte >> 4);
        }
    }

    private void unfilterAverage(byte[] scanline, byte[] previousScanline) {
        int index;
        int pixelStride = this.bytesPerPixel;
        for (index = 1; index <= pixelStride; ++index) {
            scanline[index] = (byte)(scanline[index] + (byte)((previousScanline[index] & 0xFF) >>> 1));
        }
        int scanlineLength = scanline.length;
        while (index < scanlineLength) {
            scanline[index] = (byte)(scanline[index] + (byte)((previousScanline[index] & 0xFF) + (scanline[index - pixelStride] & 0xFF) >>> 1));
            ++index;
        }
    }

    private void writeAbgrPixelsWithTransparency(ByteBuffer output, byte[] scanline) {
        if (this.transparencyData != null) {
            byte transparentRed = this.transparencyData[1];
            byte transparentGreen = this.transparencyData[3];
            byte transparentBlue = this.transparencyData[5];
            int scanlineLength = scanline.length;
            for (int index = 1; index < scanlineLength; index += 3) {
                byte red = scanline[index];
                byte green = scanline[index + 1];
                byte blue = scanline[index + 2];
                byte alpha = -1;
                if (red == transparentRed && green == transparentGreen && blue == transparentBlue) {
                    alpha = 0;
                }
                output.put(alpha).put(blue).put(green).put(red);
            }
        } else {
            int scanlineLength = scanline.length;
            for (int index = 1; index < scanlineLength; index += 3) {
                output.put((byte)-1).put(scanline[index + 2]).put(scanline[index + 1]).put(scanline[index]);
            }
        }
    }

    private void expectChunk(int expectedChunkType) throws IOException {
        this.readChunkHeader();
        if (this.currentChunkType != expectedChunkType) {
            throw new IOException("Expected chunk: " + Integer.toHexString(expectedChunkType));
        }
    }

    private void unpack1BitSamples(byte[] packedScanline, byte[] unpackedScanline) {
        int outputLength = unpackedScanline.length;
        for (int outputIndex = 1; outputIndex < outputLength; outputIndex += 8) {
            int packedByte = packedScanline[1 + (outputIndex >> 3)] & 0xFF;
            switch (outputLength - outputIndex) {
                default: {
                    unpackedScanline[outputIndex + 7] = (byte)(packedByte & 1);
                }
                case 7: {
                    unpackedScanline[outputIndex + 6] = (byte)(packedByte >> 1 & 1);
                }
                case 6: {
                    unpackedScanline[outputIndex + 5] = (byte)(packedByte >> 2 & 1);
                }
                case 5: {
                    unpackedScanline[outputIndex + 4] = (byte)(packedByte >> 3 & 1);
                }
                case 4: {
                    unpackedScanline[outputIndex + 3] = (byte)(packedByte >> 4 & 1);
                }
                case 3: {
                    unpackedScanline[outputIndex + 2] = (byte)(packedByte >> 5 & 1);
                }
                case 2: {
                    unpackedScanline[outputIndex + 1] = (byte)(packedByte >> 6 & 1);
                }
                case 1: 
            }
            unpackedScanline[outputIndex] = (byte)(packedByte >> 7);
        }
    }

    public boolean isColorTypeSupported() {
        return this.colorType == 6 || this.colorType == 2 || this.colorType == 3;
    }

    private void writeRawPixels(ByteBuffer output, byte[] scanline) {
        output.put(scanline, 1, scanline.length - 1);
    }

    private void writeBgraPixelsWithTransparency(ByteBuffer output, byte[] scanline) {
        if (this.transparencyData != null) {
            byte transparentRed = this.transparencyData[1];
            byte transparentGreen = this.transparencyData[3];
            byte transparentBlue = this.transparencyData[5];
            int scanlineLength = scanline.length;
            for (int index = 1; index < scanlineLength; index += 3) {
                byte red = scanline[index];
                byte green = scanline[index + 1];
                byte blue = scanline[index + 2];
                byte alpha = -1;
                if (red == transparentRed && green == transparentGreen && blue == transparentBlue) {
                    alpha = 0;
                }
                output.put(blue).put(green).put(red).put(alpha);
            }
        } else {
            int scanlineLength = scanline.length;
            for (int index = 1; index < scanlineLength; index += 3) {
                output.put(scanline[index + 2]).put(scanline[index + 1]).put(scanline[index]).put((byte)-1);
            }
        }
    }

    private void readFully(byte[] destination, int offset, int length) throws IOException {
        int bytesRead;
        do {
            if ((bytesRead = this.inputStream.read(destination, offset, length)) < 0) {
                throw new EOFException();
            }
            offset += bytesRead;
        } while ((length -= bytesRead) > 0);
    }

    private void writeBgraPixels(ByteBuffer output, byte[] scanline) {
        int scanlineLength = scanline.length;
        for (int index = 1; index < scanlineLength; index += 4) {
            output.put(scanline[index + 2]).put(scanline[index + 1]).put(scanline[index]).put(scanline[index + 3]);
        }
    }

    private void skipToNextChunk() throws IOException {
        if (this.remainingChunkBytes > 0) {
            this.skipInputBytes(this.remainingChunkBytes + 4);
        } else {
            this.readFully(this.ioBuffer, 0, 4);
        }
        this.remainingChunkBytes = 0;
        this.currentChunkLength = 0;
        this.currentChunkType = 0;
    }

    private int readInt32(byte[] bytes, int offset) {
        return bytes[offset] << 24 | (bytes[offset + 1] & 0xFF) << 16 | (bytes[offset + 2] & 0xFF) << 8 | bytes[offset + 3] & 0xFF;
    }

    private void unpack2BitSamples(byte[] packedScanline, byte[] unpackedScanline) {
        int outputLength = unpackedScanline.length;
        for (int outputIndex = 1; outputIndex < outputLength; outputIndex += 4) {
            int packedByte = packedScanline[1 + (outputIndex >> 2)] & 0xFF;
            switch (outputLength - outputIndex) {
                default: {
                    unpackedScanline[outputIndex + 3] = (byte)(packedByte & 3);
                }
                case 3: {
                    unpackedScanline[outputIndex + 2] = (byte)(packedByte >> 2 & 3);
                }
                case 2: {
                    unpackedScanline[outputIndex + 1] = (byte)(packedByte >> 4 & 3);
                }
                case 1: 
            }
            unpackedScanline[outputIndex] = (byte)(packedByte >> 6);
        }
    }

    private void readChunkHeader() throws IOException {
        this.readFully(this.ioBuffer, 0, 8);
        this.currentChunkLength = this.readInt32(this.ioBuffer, 0);
        this.currentChunkType = this.readInt32(this.ioBuffer, 4);
        this.remainingChunkBytes = this.currentChunkLength;
    }

    public void setTransparentColor(byte red, byte green, byte blue) {
        if (this.hasAlphaChannel()) {
            throw new UnsupportedOperationException("image has an alpha channel");
        }
        byte[] paletteData = this.palette;
        if (paletteData == null) {
            this.transparencyData = new byte[]{0, red, 0, green, 0, blue};
        } else {
            this.paletteAlpha = new byte[paletteData.length / 3];
            int paletteOffset = 0;
            int paletteIndex = 0;
            while (paletteOffset < paletteData.length) {
                if (paletteData[paletteOffset] != red || paletteData[paletteOffset + 1] != green || paletteData[paletteOffset + 2] != blue) {
                    this.paletteAlpha[paletteIndex] = -1;
                }
                paletteOffset += 3;
                ++paletteIndex;
            }
        }
    }

    private void writeRgbaPixelsWithTransparency(ByteBuffer output, byte[] scanline) {
        if (this.transparencyData != null) {
            byte transparentRed = this.transparencyData[1];
            byte transparentGreen = this.transparencyData[3];
            byte transparentBlue = this.transparencyData[5];
            int scanlineLength = scanline.length;
            for (int index = 1; index < scanlineLength; index += 3) {
                byte red = scanline[index];
                byte green = scanline[index + 1];
                byte blue = scanline[index + 2];
                byte alpha = -1;
                if (red == transparentRed && green == transparentGreen && blue == transparentBlue) {
                    alpha = 0;
                }
                output.put(red).put(green).put(blue).put(alpha);
            }
        } else {
            int scanlineLength = scanline.length;
            for (int index = 1; index < scanlineLength; index += 3) {
                output.put(scanline[index]).put(scanline[index + 1]).put(scanline[index + 2]).put((byte)-1);
            }
        }
    }

    private void readPaletteChunk() throws IOException {
        int colorCount = this.currentChunkLength / 3;
        if (colorCount < 1 || colorCount > 256 || this.currentChunkLength % 3 != 0) {
            throw new IOException("PLTE chunk has wrong length");
        }
        this.palette = new byte[colorCount * 3];
        this.readChunkBytes(this.palette, 0, this.palette.length);
    }

    private void readTransparencyChunk() throws IOException {
        switch (this.colorType) {
            case 0: {
                this.requireChunkLength(2);
                this.transparencyData = new byte[2];
                this.readChunkBytes(this.transparencyData, 0, 2);
                break;
            }
            case 2: {
                this.requireChunkLength(6);
                this.transparencyData = new byte[6];
                this.readChunkBytes(this.transparencyData, 0, 6);
                break;
            }
            case 3: {
                if (this.palette == null) {
                    throw new IOException("tRNS chunk without PLTE chunk");
                }
                this.paletteAlpha = new byte[this.palette.length / 3];
                Arrays.fill(this.paletteAlpha, (byte)-1);
                this.readChunkBytes(this.paletteAlpha, 0, this.paletteAlpha.length);
            }
        }
    }

    private void unfilterPaeth(byte[] scanline, byte[] previousScanline) {
        int index;
        int pixelStride = this.bytesPerPixel;
        for (index = 1; index <= pixelStride; ++index) {
            scanline[index] = (byte)(scanline[index] + previousScanline[index]);
        }
        int scanlineLength = scanline.length;
        while (index < scanlineLength) {
            int left = scanline[index - pixelStride] & 0xFF;
            int above = previousScanline[index] & 0xFF;
            int upperLeft = previousScanline[index - pixelStride] & 0xFF;
            int predictor = left + above - upperLeft;
            int distanceFromLeft = predictor - left;
            if (distanceFromLeft < 0) {
                distanceFromLeft = -distanceFromLeft;
            }
            int distanceFromAbove = predictor - above;
            if (distanceFromAbove < 0) {
                distanceFromAbove = -distanceFromAbove;
            }
            int distanceFromUpperLeft = predictor - upperLeft;
            if (distanceFromUpperLeft < 0) {
                distanceFromUpperLeft = -distanceFromUpperLeft;
            }
            int selectedPredictor = upperLeft;
            if (distanceFromLeft <= distanceFromAbove && distanceFromLeft <= distanceFromUpperLeft) {
                selectedPredictor = left;
            } else if (distanceFromAbove <= distanceFromUpperLeft) {
                selectedPredictor = above;
            }
            scanline[index] = (byte)(scanline[index] + (byte)selectedPredictor);
            ++index;
        }
    }
}
