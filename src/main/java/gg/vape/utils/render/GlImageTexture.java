package gg.vape.utils.render;

import gg.vape.mapping.MappedClasses;
import gg.vape.unmap.ImageParser;
import gg.vape.unmap.ImageParser$Format;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.GlStateManager;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import gg.vape.utils.render.GlPixelStoreState;

public class GlImageTexture {
    private static final int GL_TEXTURE_2D = 3553;
    private static final int GL_TEXTURE_WRAP_S = 10242;
    private static final int GL_TEXTURE_WRAP_T = 10243;
    private static final int GL_CLAMP = 10496;
    private static final int GL_CLAMP_TO_EDGE = 33071;
    public int width;
    public int minFilter;
    public float maxU;
    public float minU;
    public final int defaultWrapMode;
    public int height;
    public float minV;
    public int textureId;
    public int wrapT;
    public int magFilter;
    public float maxV;
    private static int lastBoundTextureId;
    public int wrapS;
    private final int textureTarget;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public GlImageTexture(InputStream inputStream, int requestedFilter, int wrapMode, ImageParser$Format format) throws IOException {
        this.defaultWrapMode = 10497;
        this.textureTarget = 3553;
        this.minU = 0.0f;
        this.minV = 0.0f;
        this.maxU = 1.0f;
        this.maxV = 1.0f;
        this.minFilter = 9729;
        this.magFilter = 9728;
        this.wrapS = 10496;
        this.wrapT = 33071;
        try {
            int effectiveMinFilter = requestedFilter;
            int effectiveMagFilter = requestedFilter == 9987 ? 9729 : requestedFilter;
            boolean tracksTextureCapability = !GuiRenderPrimitives.d();
            boolean textureEnabled = !tracksTextureCapability
                    || GL11.glIsEnabled(GL_TEXTURE_2D);
            int previousTextureId = GlStateManager.p();
            if (tracksTextureCapability && !textureEnabled) {
                GlStateManager.enableTexture2D();
            }
            if (requestedFilter == 9987) {
                int textureUnitCount = GL11.glGetInteger((int)33307);
                if (textureUnitCount < 3) {
                    effectiveMagFilter = 9729;
                    effectiveMinFilter = 9729;
                }
                if (MappedClasses.Y2 != null) {
                    ContextCapabilities capabilities = GLContext.getCapabilities();
                    if (!capabilities.GL_ARB_framebuffer_object) {
                        effectiveMagFilter = 9729;
                        effectiveMinFilter = 9729;
                    }
                }
            }
            ImageParser parser = new ImageParser(inputStream);
            this.width = parser.getWidth();
            this.height = parser.getHeight();
            int bytesPerPixel = 4;
            ByteBuffer pixels = BufferUtils.createByteBuffer((int)(4 * this.width * this.height));
            parser.decode(pixels, this.width * 4, format);
            pixels.flip();
            this.textureId = GlStateManager.F();
            this.bind();
            GlPixelStoreState.reset();
            GL11.glPixelStorei((int)3317, (int)1);
            GL11.glTexParameteri((int)3553, (int)10241, (int)effectiveMinFilter);
            GL11.glTexParameteri((int)3553, (int)10240, (int)effectiveMagFilter);
            int compatibleWrapMode = normalizeTextureParameterValue(
                    GL_TEXTURE_WRAP_S, wrapMode);
            GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, compatibleWrapMode);
            GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, compatibleWrapMode);
            if (effectiveMinFilter == 9987) {
                GL11.glTexParameteri((int)3553, (int)33084, (int)0);
                GL11.glTexParameteri((int)3553, (int)33085, (int)1);
            }
            GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)this.width, (int)this.height, (int)0, (int)6408, (int)5121, (ByteBuffer)pixels);
            if (effectiveMinFilter == 9987) {
                GL30.glGenerateMipmap((int)3553);
            }
            GlStateManager.bindTexture(previousTextureId);
            if (tracksTextureCapability && !textureEnabled) {
                GlStateManager.disableTexture2D();
            }
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    public void noOp() {
    }

    private static Exception identityException(Exception exception) {
        return exception;
    }

    public GlImageTexture(int textureId) {
        this.defaultWrapMode = 10497;
        this.textureTarget = 3553;
        this.minU = 0.0f;
        this.minV = 0.0f;
        this.maxU = 1.0f;
        this.maxV = 1.0f;
        this.minFilter = 9729;
        this.magFilter = 9728;
        this.wrapS = 10496;
        this.wrapT = 33071;
        this.textureId = textureId;
    }

    public void setParameter(int parameter, int value) {
        GL11.glTexParameteri(GL_TEXTURE_2D, parameter,
                normalizeTextureParameterValue(parameter, value));
    }

    static int normalizeTextureParameterValue(int parameter, int value) {
        if ((parameter == GL_TEXTURE_WRAP_S || parameter == GL_TEXTURE_WRAP_T)
                && value == GL_CLAMP) {
            return GL_CLAMP_TO_EDGE;
        }
        return value;
    }

    public static GlImageTexture create(int width, int height, ByteBuffer pixels, int format, int filter, int wrapMode) {
        boolean usesMipmaps;
        int previousTextureId = GlStateManager.p();
        GlImageTexture texture = new GlImageTexture();
        texture.height = height;
        texture.width = width;
        GL11.glBindTexture((int)3553, (int)texture.textureId);
        GL11.glPixelStorei((int)3312, (int)0);
        GL11.glPixelStorei((int)3313, (int)0);
        GL11.glPixelStorei((int)3314, (int)0);
        GL11.glPixelStorei((int)32878, (int)0);
        GL11.glPixelStorei((int)3315, (int)0);
        GL11.glPixelStorei((int)3316, (int)0);
        GL11.glPixelStorei((int)32877, (int)0);
        GL11.glPixelStorei((int)3317, (int)4);
        boolean mipmapFilter = usesMipmaps = filter == 9987 || filter == 9985 || filter == 9986 || filter == 9984;
        if (usesMipmaps) {
            texture.setParameter(10240, 9729);
            texture.setParameter(10241, filter);
            texture.setParameter(10242, wrapMode);
            texture.setParameter(10243, wrapMode);
            int maximumMipmapLevel = (int)Math.floor(Math.log(Math.max(width, height)) / Math.log(2.0));
            GL11.glTexParameteri((int)3553, (int)33084, (int)0);
            GL11.glTexParameteri((int)3553, (int)33085, (int)maximumMipmapLevel);
            GL11.glTexParameterf((int)3553, (int)34049, (float)-0.4f);
            GL11.glTexParameterf((int)3553, (int)33082, (float)0.0f);
            GL11.glTexParameterf((int)3553, (int)33083, (float)maximumMipmapLevel);
            texture.upload(format, width, height, format, pixels);
            GL30.glGenerateMipmap((int)3553);
            GlStateManager.bindTexture(previousTextureId);
            return texture;
        }
        texture.setParameter(10240, filter);
        texture.setParameter(10241, filter);
        texture.setParameter(10242, wrapMode);
        texture.setParameter(10243, wrapMode);
        texture.upload(format, width, height, format, pixels);
        GlStateManager.bindTexture(previousTextureId);
        return texture;
    }

    public GlImageTexture(int textureId, int width, int height, float minU, float minV, float maxU, float maxV) {
        this.defaultWrapMode = 10497;
        this.textureTarget = 3553;
        this.minU = 0.0f;
        this.minV = 0.0f;
        this.maxU = 1.0f;
        this.maxV = 1.0f;
        this.minFilter = 9729;
        this.magFilter = 9728;
        this.wrapS = 10496;
        this.wrapT = 33071;
        this.textureId = textureId;
        this.width = width;
        this.height = height;
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
    }

    public void bind(int textureUnit) {
        if (!GuiRenderPrimitives.d()) {
            this.bind();
            return;
        }
        GL13.glActiveTexture((int)(33984 + textureUnit));
        GlStateManager.bindTexture(this.textureId);
    }

    public void bind() {
        if (GuiRenderPrimitives.d()) {
            this.bind(0);
        } else {
            GlStateManager.bindTexture(this.textureId);
            lastBoundTextureId = this.textureId;
        }
    }

    public GlImageTexture(InputStream inputStream) throws IOException {
        this(inputStream, 9729, ImageParser$Format.RGBA);
    }

    public GlImageTexture(InputStream inputStream, int filter, ImageParser$Format format) throws IOException {
        this(inputStream, filter, 33071, format);
    }

    public void delete() {
        GL11.glDeleteTextures((int)this.textureId);
    }

    public void upload(int internalFormat, int width, int height, int pixelFormat, ByteBuffer pixels) {
        GL11.glTexImage2D((int)3553, (int)0, (int)internalFormat, (int)width, (int)height, (int)0, (int)pixelFormat, (int)5121, (ByteBuffer)pixels);
    }

    static {
        lastBoundTextureId = 0;
    }

    public GlImageTexture() {
        this.defaultWrapMode = 10497;
        this.textureTarget = 3553;
        this.minU = 0.0f;
        this.minV = 0.0f;
        this.maxU = 1.0f;
        this.maxV = 1.0f;
        this.minFilter = 9729;
        this.magFilter = 9728;
        this.wrapS = 10496;
        this.wrapT = 33071;
        this.textureId = GL11.glGenTextures();
    }
}
