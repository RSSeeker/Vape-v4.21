package gg.vape.utils.render;

import gg.vape.unmap.Stencil;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.wrapper.impl.Framebuffer;
import gg.vape.wrapper.impl.Minecraft;
import java.util.HashMap;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public final class StencilUtil {
    private int currentLayer = 1;
    private final HashMap<Integer, Stencil> stencilStatesByLayer = new HashMap();
    private static final StencilUtil INSTANCE = new StencilUtil();
    private boolean invertedComparison;

    public void configureNotEqualStencilState() {
        this.applyStencilState(new Stencil(this, 517, this.currentLayer, this.getStencilMask(), 7680, 7680, 7680));
    }

    public void reset() {
        GL11.glClearStencil((int)0);
        GL11.glClear((int)1024);
        this.stencilStatesByLayer.clear();
        this.currentLayer = 1;
    }

    public static void attachStencilBuffer(Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT((int)framebuffer.getDepthBuffer());
        int n = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)n);
        EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)Minecraft.J(), (int)Minecraft.h());
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)n);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36096, (int)36161, (int)n);
    }

    public int getStencilMask() {
        return (int)(Math.pow(2.0, this.getStencilBitDepth()) - 1.0);
    }

    public static StencilUtil getInstance() {
        return INSTANCE;
    }

    public void drawRectangle(double left, double top, double right, double bottom) {
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)left, (double)bottom);
        GL11.glVertex2d((double)right, (double)bottom);
        GL11.glVertex2d((double)right, (double)top);
        GL11.glVertex2d((double)left, (double)top);
        GL11.glEnd();
    }

    public static void ensureFramebufferStencilBuffer() {
        Framebuffer framebuffer = Minecraft.getFrameBuffer();
        if (framebuffer.isNotNull() && framebuffer.getDepthBuffer() > -1) {
            StencilUtil.attachStencilBuffer(framebuffer);
            framebuffer.setDepthBuffer(-1);
        }
    }

    public void applyStencilState(Stencil stencil) {
        GL11.glStencilFunc((int)Stencil.function, (int)Stencil.referenceValue, (int)Stencil.mask);
        GL11.glStencilOp((int)Stencil.stencilFailOperation, (int)Stencil.depthFailOperation, (int)Stencil.depthPassOperation);
        this.stencilStatesByLayer.put(this.currentLayer, stencil);
    }

    public int getCurrentLayer() {
        return this.currentLayer;
    }

    public void configureLayerIncrement() {
        this.applyStencilState(new Stencil(this, this.invertedComparison ? 519 : 512, this.currentLayer, this.getStencilMask(), 7681, 7680, 7680));
    }

    public void configureLayerMask(boolean useCurrentLayer) {
        this.applyStencilState(new Stencil(this, this.invertedComparison ? 519 : 512, useCurrentLayer ? this.currentLayer : this.currentLayer - 1, this.getStencilMask(), 7681, 7681, 7681));
    }

    public void setInvertedComparison(boolean invertedComparison) {
        this.invertedComparison = invertedComparison;
    }

    public void configureEqualLayerTest() {
        this.applyStencilState(new Stencil(this, 514, this.currentLayer, this.getStencilMask(), 7680, 7680, 7680));
    }

    public void pushLayer() {
        if (this.currentLayer == 1) {
            GL11.glClearStencil((int)0);
            GL11.glClear((int)1024);
        }
        OpenGlBackendHolder.backend.enableCapability(2960);
        ++this.currentLayer;
        if (this.currentLayer > this.getStencilMask()) {
            System.out.println("StencilUtil: Reached maximum amount of layers!");
            this.currentLayer = 1;
        }
    }

    public Stencil getCurrentStencilState() {
        return this.stencilStatesByLayer.get(this.currentLayer);
    }

    public void popLayer() {
        if (this.currentLayer == 1) {
            System.out.println("StencilUtil: No layers found!");
            return;
        }
        --this.currentLayer;
        if (this.currentLayer == 1) {
            OpenGlBackendHolder.backend.disableCapability(2960);
        } else {
            Stencil stencil = this.stencilStatesByLayer.remove(this.currentLayer);
            if (stencil != null) {
                stencil.apply();
            }
        }
    }


    public void drawCircle(double centerX, double centerY, double radius) {
        GL11.glBegin((int)6);
        for (int angleDegrees = 0; angleDegrees <= 360; ++angleDegrees) {
            double offsetX = Math.sin((double)angleDegrees * Math.PI / 180.0) * radius;
            double offsetY = Math.cos((double)angleDegrees * Math.PI / 180.0) * radius;
            GL11.glVertex2d((double)(centerX + offsetX), (double)(centerY + offsetY));
        }
        GL11.glEnd();
    }

    public int getStencilBitDepth() {
        return GL11.glGetInteger((int)3415);
    }
}

