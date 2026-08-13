package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.wrapper.impl.GlStateManager;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class GlFramebuffer {
    public int framebufferHeight;
    public static boolean skipTextureStateTracking;
    public boolean depthAttachmentEnabled;
    public int textureHeight;
    public int framebufferWidth;
    public int colorTextureId;
    public int depthRenderbufferId;
    private static String[] legacyMarkers;
    public int textureFilter;
    int previousFramebufferId = -1;
    int previousTextureId = -1;
    public int framebufferId;
    final boolean legacyFlag = true;
    public int textureWidth;
    public float[] clearColor;
    private static final String FRAMEBUFFER_ERROR_PREFIX;

    public void clear() {
        this.bind(true);
        FloatBuffer previousClearColor = BufferUtils.createFloatBuffer((int)16);
        gg.vape.wrapper.impl.GL11.G(3106, previousClearColor);
        GL11.glClearColor((float)this.clearColor[0], (float)this.clearColor[1], (float)this.clearColor[2], (float)this.clearColor[3]);
        int clearMask = 16384;
        if (this.depthAttachmentEnabled) {
            GL11.glClearDepth((double)1.0);
            clearMask |= 0x100;
        }
        GL11.glClear((int)clearMask);
        GL11.glClearColor((float)previousClearColor.get(0), (float)previousClearColor.get(1), (float)previousClearColor.get(2), (float)previousClearColor.get(3));
        this.unbind();
    }

    public static void setLegacyMarkers(String[] legacyMarkers) {
        GlFramebuffer.legacyMarkers = legacyMarkers;
    }

    static {
        GlFramebuffer.setLegacyMarkers(null);
        FRAMEBUFFER_ERROR_PREFIX = "Error creating fbo ";
        skipTextureStateTracking = false;
    }

    public GlFramebuffer(int width, int height, boolean depthAttachmentEnabled) {
        this.depthAttachmentEnabled = depthAttachmentEnabled;
        this.framebufferId = -1;
        this.colorTextureId = -1;
        this.depthRenderbufferId = -1;
        this.clearColor = new float[4];
        this.clearColor[0] = 1.0f;
        this.clearColor[1] = 1.0f;
        this.clearColor[2] = 1.0f;
        this.clearColor[3] = 0.0f;
        this.initialize(width, height);
    }

    public void setTextureFilter(int textureFilter) {
        this.textureFilter = textureFilter;
        this.bindColorTexture();
        GL11.glTexParameterf((int)3553, (int)10241, (float)textureFilter);
        GL11.glTexParameterf((int)3553, (int)10240, (float)textureFilter);
        GL11.glTexParameteri((int)3553, (int)10242, (int)33071);
        GL11.glTexParameteri((int)3553, (int)10243, (int)33071);
        this.restorePreviousTexture();
    }

    public void unbind() {
        GL30.glBindFramebuffer((int)36160, (int)this.previousFramebufferId);
    }

    public void delete() {
        this.restorePreviousTexture();
        this.unbind();
        if (this.depthRenderbufferId > -1) {
            GL30.glDeleteRenderbuffers((int)this.depthRenderbufferId);
            this.depthRenderbufferId = -1;
        }
        if (this.colorTextureId > -1) {
            GL11.glDeleteTextures((int)this.colorTextureId);
            this.colorTextureId = -1;
        }
        if (this.framebufferId > -1) {
            GL30.glDeleteFramebuffers((int)this.framebufferId);
            this.framebufferId = -1;
        }
    }

    public void checkStatus() {
        int status = GL30.glCheckFramebufferStatus((int)36160);
        if (status != 36053) {
            Vape.debugLog(FRAMEBUFFER_ERROR_PREFIX + status);
        }
    }

    public static String[] getLegacyMarkers() {
        return legacyMarkers;
    }

    public void bind(boolean updateViewport) {
        int boundFramebufferId = GL11.glGetInteger((int)36006);
        if (boundFramebufferId != this.framebufferId) {
            this.previousFramebufferId = boundFramebufferId;
        }
        GL30.glBindFramebuffer((int)36160, (int)this.framebufferId);
        if (updateViewport) {
            GL11.glViewport((int)0, (int)0, (int)this.framebufferWidth, (int)this.framebufferHeight);
        }
    }

    public void initialize(int width, int height) {
        boolean depthTestEnabled = GL11.glIsEnabled((int)2929);
        GlStateManager.enableDepth();
        this.createAttachments(width, height);
        this.checkStatus();
        if (!depthTestEnabled) {
            GlStateManager.disableDepth();
        }
    }

    public void restorePreviousTexture() {
        if (!skipTextureStateTracking) {
            GlStateManager.bindTexture(this.previousTextureId);
        }
    }


    public void bindColorTexture() {
        int boundTextureId;
        if (!skipTextureStateTracking && (boundTextureId = GL11.glGetInteger((int)32873)) != this.colorTextureId) {
            this.previousTextureId = boundTextureId;
        }
        GlStateManager.bindTexture(this.colorTextureId);
    }

    public void createAttachments(int width, int height) {
        this.framebufferWidth = width;
        this.framebufferHeight = height;
        this.textureWidth = width;
        this.textureHeight = height;
        int previousRenderbufferId = GL11.glGetInteger((int)36007);
        this.framebufferId = GL30.glGenFramebuffers();
        this.colorTextureId = GL11.glGenTextures();
        if (this.depthAttachmentEnabled) {
            this.depthRenderbufferId = GL30.glGenRenderbuffers();
        }
        this.setTextureFilter(9728);
        this.bindColorTexture();
        GL11.glTexImage2D((int)3553, (int)0, (int)32856, (int)this.textureWidth, (int)this.textureHeight, (int)0, (int)6408, (int)5121, (ByteBuffer)null);
        this.bind(false);
        GL30.glFramebufferTexture2D((int)36160, (int)36064, (int)3553, (int)this.colorTextureId, (int)0);
        if (this.depthAttachmentEnabled) {
            GL30.glBindRenderbuffer((int)36161, (int)this.depthRenderbufferId);
            GL30.glRenderbufferStorage((int)36161, (int)33190, (int)this.textureWidth, (int)this.textureHeight);
            GL30.glFramebufferRenderbuffer((int)36160, (int)36096, (int)36161, (int)this.depthRenderbufferId);
        }
        this.clear();
        this.unbind();
        this.restorePreviousTexture();
        GL30.glBindRenderbuffer((int)36161, (int)previousRenderbufferId);
    }
}

