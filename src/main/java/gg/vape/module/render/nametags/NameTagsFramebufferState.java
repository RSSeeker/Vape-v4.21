package gg.vape.module.render.nametags;

import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class NameTagsFramebufferState {
    private int depthRenderbuffer;
    private final int height;
    private final IntBuffer previousViewport = BufferUtils.createIntBuffer((int)16);
    private final int offsetX;
    private int previousFramebuffer;
    private final int width;
    private final int offsetY;
    private int colorTexture;
    private int framebuffer;


    public NameTagsFramebufferState(int offsetX, int offsetY, int width, int height) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.initialize();
    }

    public void endRendering() {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.projectionMatrix.setIdentity();
            BufferedGuiRenderPrimitives.matrixStack.pop();
        } else {
            GL11.glMatrixMode((int)5888);
            GL11.glPopMatrix();
            GL11.glMatrixMode((int)5889);
            GL11.glPopMatrix();
        }
        GL30.glBindFramebuffer((int)36160, (int)this.previousFramebuffer);
        this.previousViewport.rewind();
        GL11.glViewport((int)this.previousViewport.get(0), (int)this.previousViewport.get(1), (int)this.previousViewport.get(2), (int)this.previousViewport.get(3));
    }

    public boolean isValid() {
        if (this.colorTexture == 0) {
            return false;
        }
        try {
            return GL11.glIsTexture((int)this.colorTexture);
        }
        catch (Exception exception) {
            return false;
        }
    }

    public void beginRendering() {
        this.previousViewport.clear();
        gg.vape.wrapper.impl.GL11.X(2978, this.previousViewport);
        IntBuffer framebufferBinding = BufferUtils.createIntBuffer((int)16);
        gg.vape.wrapper.impl.GL11.X(36006, framebufferBinding);
        this.previousFramebuffer = framebufferBinding.get(0);
        GL11.glViewport((int)0, (int)0, (int)(this.width * 4), (int)(this.height * 4));
        GL30.glBindFramebuffer((int)36160, (int)this.framebuffer);
        GL11.glClear((int)256);
        GL11.glClear((int)16384);
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.projectionMatrix.setIdentity();
            BufferedGuiRenderPrimitives.projectionMatrix.setOrthographic(0.0f, this.width, this.height, 0.0f, -2000.0f, 1000.0f);
            BufferedGuiRenderPrimitives.matrixStack.pushIdentity();
            BufferedGuiRenderPrimitives.matrixStack.resetCurrent();
            BufferedGuiRenderPrimitives.matrixStack.translate(-this.offsetX, -this.offsetY, 0.0f);
        } else {
            GL11.glMatrixMode((int)5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho((double)0.0, (double)this.width, (double)this.height, (double)0.0, (double)-2000.0, (double)1000.0);
            GL11.glMatrixMode((int)5888);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef((float)(-this.offsetX), (float)(-this.offsetY), (float)0.0f);
        }
    }

    public int getOffsetY() {
        return this.offsetY;
    }

    private void initialize() {
        IntBuffer framebufferBinding = BufferUtils.createIntBuffer((int)16);
        gg.vape.wrapper.impl.GL11.X(36006, framebufferBinding);
        int previousFramebuffer = framebufferBinding.get(0);
        IntBuffer textureBinding = BufferUtils.createIntBuffer((int)16);
        gg.vape.wrapper.impl.GL11.X(32873, textureBinding);
        int previousTexture = textureBinding.get(0);
        IntBuffer renderbufferBinding = BufferUtils.createIntBuffer((int)16);
        gg.vape.wrapper.impl.GL11.X(36007, renderbufferBinding);
        int previousRenderbuffer = renderbufferBinding.get(0);
        this.framebuffer = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer((int)36160, (int)this.framebuffer);
        this.colorTexture = GL11.glGenTextures();
        GL11.glBindTexture((int)3553, (int)this.colorTexture);
        GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)(this.width * 4), (int)(this.height * 4), (int)0, (int)6408, (int)5121, (ByteBuffer)null);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        GL30.glFramebufferTexture2D((int)36160, (int)36064, (int)3553, (int)this.colorTexture, (int)0);
        this.depthRenderbuffer = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer((int)36161, (int)this.depthRenderbuffer);
        GL30.glRenderbufferStorage((int)36161, (int)6402, (int)(this.width * 4), (int)(this.height * 4));
        GL30.glFramebufferRenderbuffer((int)36160, (int)36096, (int)36161, (int)this.depthRenderbuffer);
        GL30.glCheckFramebufferStatus((int)36160);
        GL30.glBindFramebuffer((int)36160, (int)previousFramebuffer);
        GL11.glBindTexture((int)3553, (int)previousTexture);
        GL30.glBindRenderbuffer((int)36161, (int)previousRenderbuffer);
    }

    public void dispose() {
        GL30.glDeleteFramebuffers((int)this.framebuffer);
        GL11.glDeleteTextures((int)this.colorTexture);
        GL30.glDeleteRenderbuffers((int)this.depthRenderbuffer);
    }

    public int getOffsetX() {
        return this.offsetX;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getColorTexture() {
        return this.colorTexture;
    }
}
