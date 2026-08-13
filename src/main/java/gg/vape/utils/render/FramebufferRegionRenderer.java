package gg.vape.utils.render;

import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.Framebuffer;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import org.lwjgl.opengl.GL11;

public class FramebufferRegionRenderer {
    public double legacyDoubleStateA;
    private Framebuffer framebuffer;
    public float legacyFloatStateA;
    private static boolean framebufferBound;
    private int legacyMagicInt;
    private int legacyIntState;
    private boolean sizeReady;
    public double legacyDoubleStateB;
    private final TimerUtil legacyTimer;
    public float legacyFloatStateB;
    private int width;
    private boolean flipHorizontally;
    public double legacyDoubleStateC;
    private static final long LEGACY_MAGIC_VALUE;
    private int height;

    public void beginFramebufferCapture() {
        if (!Minecraft.gameSettings().Y$src$Z$1rxemad()) {
            return;
        }
        this.getFramebuffer().bindFramebufferTexture();
        this.setFramebufferBound(true);
    }

    public int getWidth() {
        return this.width;
    }

    public Framebuffer getFramebuffer() {
        return this.framebuffer;
    }

    public void setHeight(int height) {
        this.height = height;
        this.sizeReady = false;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isSizeReady() {
        return this.sizeReady;
    }

    public void initializeFramebuffer() {
        this.framebuffer = Framebuffer.create(this.width, this.height, true);
        this.resizeFramebuffer();
    }

    public FramebufferRegionRenderer(int width, int height) {
        this.legacyTimer = new TimerUtil();
        this.legacyMagicInt = (int)LEGACY_MAGIC_VALUE;
        this.flipHorizontally = this.flipHorizontally;
        this.width = width;
        this.height = height;
        this.initializeFramebuffer();
    }

    public void drawRegion(boolean drawTexture, double left, double top, double right, double bottom) {
        if (!Minecraft.gameSettings().Y$src$Z$1rxemad()) {
            return;
        }
        OpenGlBackendHolder.backend.enableCapability(2903);
        boolean texture2dWasEnabled = GL11.glIsEnabled((int)3553);
        boolean lightingWasEnabled = GL11.glIsEnabled((int)2896);
        boolean alphaTestWasEnabled = GL11.glIsEnabled((int)3008);
        boolean blendingWasEnabled = GL11.glIsEnabled((int)3042);
        if (!texture2dWasEnabled) {
            GlStateManager.enableTexture2D();
        }
        if (lightingWasEnabled) {
            GlStateManager.disableLighting();
        }
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        if (drawTexture) {
            this.framebuffer.bindFramebufferTexture();
            if (this.flipHorizontally) {
                RenderUtils.J(left, top, right, bottom);
            } else {
                RenderUtils.A(left, top, right, bottom);
            }
            this.framebuffer.unbindFramebufferTexture();
        }
        if (!texture2dWasEnabled) {
            GlStateManager.disableTexture2D();
        }
        if (lightingWasEnabled) {
            GlStateManager.enableLighting();
        }
        if (alphaTestWasEnabled) {
            GlStateManager.enableAlpha();
        }
        if (blendingWasEnabled) {
            GlStateManager.enableBlend();
        }
    }

    static {
        LEGACY_MAGIC_VALUE = 3773695550600249374L;
    }

    public void endFramebufferCapture() {
        if (!Minecraft.gameSettings().Y$src$Z$1rxemad()) {
            return;
        }
        this.setFramebufferBound(false);
        this.getFramebuffer().unbindFramebuffer();
        Minecraft.getFrameBuffer().bindFramebuffer(true);
    }

    protected void setFramebufferBound(boolean bound) {
        if (bound) {
            this.framebuffer.bindFramebuffer(true);
        } else {
            this.framebuffer.unbindFramebuffer();
        }
        framebufferBound = bound;
    }

    public void resizeFramebuffer() {
        this.framebuffer.createFramebuffer(this.width, this.height);
    }


    public void setWidth(int width) {
        this.width = width;
        this.sizeReady = false;
    }
}

