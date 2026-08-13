package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.friend.ui.OnlinePlayerPreviewRenderContext;
import gg.vape.friend.ui.OnlinePlayerPreviewSettingsFrame;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class OnlinePlayerPreviewComponent
extends GuiComponent {
    private OnlinePlayerPreviewRenderContext renderContext;
    private final OnlinePlayerPreviewSettingsFrame settingsFrame;
    public static OnlinePlayerPreviewComponent instance;

    public void onPreRenderTick(EventPreRenderTick eventPreRenderTick) {
        if (!this.settingsFrame.y$src$Z$1f55jvh() || !this.settingsFrame.V$src$Z$1xhop3l()) {
            return;
        }
        if (eventPreRenderTick.getThePlayer().isNull() || eventPreRenderTick.getWorld().isNull()) {
            return;
        }
        if (this.renderContext != null) {
            OnlinePlayerPreviewSettings settings = this.settingsFrame.getSettings();
            if (((Double)settings.framesPerSecond.getValue()).intValue() <= 0) {
                settings.framesPerSecond.setValue(1.0);
            }
            this.renderContext.setFrameIntervalMillis(1000 / ((Double)settings.framesPerSecond.getValue()).intValue());
            this.renderContext.setLevelView(settings.levelView.getEffectiveValue());
            this.renderContext.setFieldOfView(((Double)settings.fieldOfView.getValue()).intValue());
            this.renderContext.renderOffscreenFrame();
        } else {
            this.renderContext = new OnlinePlayerPreviewRenderContext();
        }
    }

    @Override
    public void F() {
    }


    @Override
    public void H() {
        this.renderPreview();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public double x() {
        if (this.settingsFrame.isPublicProfilePreviewActive()) {
            return 110.0 * (Double)this.settingsFrame.getSettings().scale.getValue();
        }
        return 110.0;
    }

    @Override
    public void I() {
        this.renderPreview();
    }

    @Override
    public void u() {
    }

    public OnlinePlayerPreviewComponent(OnlinePlayerPreviewSettingsFrame onlinePlayerPreviewSettingsFrame) {
        instance = this;
        this.settingsFrame = onlinePlayerPreviewSettingsFrame;
    }

    @Override
    public double C() {
        if (this.settingsFrame.isPublicProfilePreviewActive()) {
            return 61.0 * (Double)this.settingsFrame.getSettings().scale.getValue();
        }
        return 61.0;
    }

    private void renderPreview() {
        float opacity = this.settingsFrame.getEditorOpacity();
        if (this.renderContext == null || !this.settingsFrame.y$src$Z$1f55jvh()) {
            double height = this.L();
            double width = this.A();
            OpenGlBackendHolder.backend.setColor(0.1f, 0.1f, 0.1f, opacity);
            GuiRenderPrimitives.d((double)((float)this.G$src$D$1b2f02a()), (double)((float)this.n()), width, height, this.settingsFrame.applyDefaultEditorAlpha(OnlinePlayerPreviewComponent.J.r));
            ImageRenderer.drawImage(this.settingsFrame.applyDefaultEditorAlpha(Color.WHITE), (float)(this.G$src$D$1b2f02a() + width / 2.0 - 10.0), (float)(this.n() + height / 2.0 - 10.0), "camera", 16.0f, 16.0f, false);
            return;
        }
        if (Minecraft.gameSettings().d() > 0) {
            Vape.INSTANCE.getFontManager().w().F("Disable Optifine AA to use this feature", this.G$src$D$1b2f02a() + 4.0, this.n() + 4.0, -65536);
            return;
        }
        if (!Minecraft.gameSettings().Y$src$Z$1rxemad()) {
            Vape.INSTANCE.getFontManager().w().F("Enable FBO to use this feature", this.G$src$D$1b2f02a() + 4.0, this.n() + 4.0, -65536);
            return;
        }
        if (Minecraft.gameSettings().M()) {
            Vape.INSTANCE.getFontManager().w().F("Disable fast render to use this feature", this.G$src$D$1b2f02a() + 4.0, this.n() + 4.0, -65536);
            return;
        }
        if (this.renderContext.hasFrame()) {
            double height = this.L();
            double width = this.A();
            if (!this.settingsFrame.isPublicProfilePreviewActive()) {
                double scale = (Double)this.settingsFrame.getSettings().scale.getValue();
                height *= scale;
                width *= scale;
            }
            if (ForgeVersion.MC_1_21_4.d()) {
                Color backgroundColor = this.settingsFrame.applyDefaultEditorAlpha(new Color(0.1f, 0.1f, 0.1f, 0.2f));
                GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), width, height, backgroundColor);
                Color framebufferColor = new Color(1.0f, 1.0f, 1.0f, opacity);
                this.renderContext.drawFramebuffer(this.settingsFrame.y$src$Z$1f55jvh(), this.G$src$D$1b2f02a() + 0.5, this.n() + 0.5, this.G$src$D$1b2f02a() + width - 0.5, this.n() + height - 0.5, framebufferColor);
                return;
            }
            boolean textureEnabled = GL11.glIsEnabled((int)3553);
            boolean alphaEnabled = GL11.glIsEnabled((int)3008);
            boolean blendEnabled = GL11.glIsEnabled((int)3042);
            if (textureEnabled) {
                GlStateManager.disableTexture2D();
            }
            if (!alphaEnabled) {
                GlStateManager.enableAlpha();
            }
            if (!blendEnabled) {
                GlStateManager.enableBlend();
            }
            Color backgroundColor = this.settingsFrame.applyDefaultEditorAlpha(new Color(0.1f, 0.1f, 0.1f, 0.2f));
            OpenGlBackendHolder.backend.setColor(0.1f, 0.1f, 0.1f, (float)backgroundColor.getAlpha() / 255.0f);
            GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), width, height, backgroundColor);
            Color framebufferColor = new Color(1.0f, 1.0f, 1.0f, opacity);
            this.renderContext.drawFramebuffer(this.settingsFrame.y$src$Z$1f55jvh(), this.G$src$D$1b2f02a() + 0.5, this.n() + 0.5, this.G$src$D$1b2f02a() + width - 0.5, this.n() + height - 0.5, framebufferColor);
            if (textureEnabled) {
                GlStateManager.enableTexture2D();
            }
            if (!alphaEnabled) {
                GlStateManager.disableAlpha();
            }
            if (!blendEnabled) {
                GlStateManager.disableBlend();
            }
        }
    }
}

