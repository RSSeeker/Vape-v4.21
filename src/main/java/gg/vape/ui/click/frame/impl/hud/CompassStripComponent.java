package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.CompassHudFrame;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;

public class CompassStripComponent
extends GuiComponent {
    private final CompassHudFrame frame;
    private static final String MAJOR_TICK_RESOURCE = "compassbigpoint";
    private static final String CENTER_ARROW_RESOURCE = "compassarrow";
    private static final String MINOR_TICK_RESOURCE = "compasssmallpoint";

    @Override
    public void I() {
        this.H();
    }


    private float getHeading() {
        EntityPlayerSP player = Minecraft.thePlayer();
        float heading = player.J() % 360.0f;
        if (heading < -180.0f) {
            heading += 360.0f;
        }
        if (heading > 180.0f) {
            heading -= 360.0f;
        }
        return heading + 180.0f;
    }

    private void renderCompass() {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        RenderUtils.m(this.frame.G$src$D$1b2f02a(), this.frame.n() - 10.0,
                this.frame.A(), this.frame.L() + 10.0);
        float heading = this.getHeading();
        float startHeading = heading - 70.0f;
        float endHeading = heading + 70.0f;
        ImageRenderer.beginBatch();
        double tickSpacing = (this.frame.A() + 4.0) / 1400.0
                * Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        double tickX = this.frame.G$src$D$1b2f02a() * Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        float currentHeading = startHeading;
        while (currentHeading < endHeading) {
            float firstTickHeading = (float)((int)(currentHeading * 10.0f)) / 10.0f;
            this.drawTick(tickX += tickSpacing, firstTickHeading);
            float secondTickHeading = firstTickHeading + 0.1f;
            this.drawTick(tickX += tickSpacing, secondTickHeading);
            currentHeading = (float)((double)currentHeading + 0.2);
        }
        ImageRenderer.endBatch();
        this.drawImage(CENTER_ARROW_RESOURCE,
                this.frame.G$src$D$1b2f02a() - 5.0 + 2.3 + this.frame.A() / 2.0,
                this.frame.n() - 2.5, this.frame.getEditorForegroundColor(), 0.5);
        String headingText = String.valueOf((int)heading);
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().W(0.8, false);
        smoothFontRenderer.T(headingText,
                this.frame.G$src$D$1b2f02a() + this.frame.A() / 2.0 + 2.3
                        - smoothFontRenderer.N(headingText) / 2.0,
                this.frame.n() - 8.0, this.frame.applyDefaultEditorAlpha(new Color(230, 230, 230)),
                this.frame.applyDefaultEditorAlpha(new Color(0, 0, 0, 180)));
        RenderUtils.T();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public void J() {
        Vape.debugLog("Hovering");
    }

    @Override
    public double x() {
        return 0.0;
    }

    private void drawTick(double x, float heading) {
        double frameY = this.frame.n() * Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        double frameHeight = this.frame.L() * Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        if (heading < 0.0f) {
            heading = 360.0f + heading;
        } else if (heading > 359.0f) {
            heading = 360.0f - heading;
        }
        heading = Math.abs(heading);
        if (heading % 45.0f == 0.0f) {
            if (!this.frame.shouldRenderHudBackground()) {
                GuiRenderPrimitives.F(MAJOR_TICK_RESOURCE, (float)x,
                        (float)frameY + frameHeight / 2.0 - 6.0, 7.0, 10.0,
                        this.frame.applyDefaultEditorAlpha(new Color(0, 0, 0, 100)));
            }
            GuiRenderPrimitives.F(MAJOR_TICK_RESOURCE, (float)x,
                    (float)frameY + frameHeight / 2.0 - 6.0, 7.0, 10.0,
                    this.frame.applyDefaultEditorAlpha(Color.WHITE));
        } else if (heading % 5.0f == 0.0f) {
            if (!this.frame.shouldRenderHudBackground()) {
                GuiRenderPrimitives.h(MINOR_TICK_RESOURCE, (float)x,
                        (float)(frameY + frameHeight / 2.0 - 6.0), 7.0f, 8.0f,
                        this.frame.applyDefaultEditorAlpha(new Color(0, 0, 0, 50)));
            }
            GuiRenderPrimitives.h(MINOR_TICK_RESOURCE, (float)x,
                    (float)(frameY + frameHeight / 2.0 - 6.0), 7.0f, 8.0f,
                    this.frame.applyDefaultEditorAlpha(Color.WHITE));
        }
        if (heading % 45.0f == 0.0f) {
            String direction = this.getCardinalDirection(heading);
            if (direction != null) {
                double halfTextWidth = this.getFontRenderer(0.7).N(direction) / 2.0;
                this.drawLabel(direction, x - halfTextWidth, frameY + frameHeight / 2.0,
                        this.frame.applyDefaultEditorAlpha(Color.WHITE), true);
            }
        } else if (heading % 15.0f == 0.0f) {
            String headingText = String.valueOf((int)heading);
            double halfTextWidth = this.getFontRenderer(0.7).N(headingText) / 2.0;
            this.drawLabel(headingText, x - halfTextWidth, frameY + frameHeight / 2.0,
                    this.frame.applyDefaultEditorAlpha(CompassStripComponent.J.Z), false);
        }
    }

    public CompassStripComponent(CompassHudFrame compassHudFrame) {
        this.frame = compassHudFrame;
    }

    private void drawImage(String resourceName, double x, double y, Color color, double scale) {
        ImageRenderer.drawRes(color, (float)x, (float)y, resourceName, (float)scale);
    }

    private void drawLabel(String text, double x, double y, Color color, boolean emphasized) {
        if (!this.frame.shouldRenderHudBackground()) {
            emphasized = true;
        }
        SmoothFontRenderer smoothFontRenderer = emphasized
                ? Vape.INSTANCE.getFontManager().W(0.7, false)
                : Vape.INSTANCE.getFontManager().Y(0.7);
        if (this.frame.shouldRenderHudBackground()) {
            smoothFontRenderer.T(text, x, y, this.frame.applyDefaultEditorAlpha(color),
                    this.frame.applyDefaultEditorAlpha(new Color(0, 0, 0, 30)));
        } else {
            smoothFontRenderer.T(text, x, y, this.frame.applyDefaultEditorAlpha(new Color(230, 230, 230)),
                    this.frame.applyDefaultEditorAlpha(new Color(0, 0, 0, 150)));
        }
    }

    @Override
    public void u() {
    }

    @Override
    public void F() {
    }

    @Override
    public void H() {
        this.renderCompass();
    }

    private String getCardinalDirection(float heading) {
        if ((double)heading == 0.0) {
            return "N";
        }
        if ((double)heading == 45.0) {
            return "NE";
        }
        if ((double)heading == 90.0) {
            return "E";
        }
        if ((double)heading == 135.0) {
            return "SE";
        }
        if ((double)heading == 180.0) {
            return "S";
        }
        if ((double)heading == 225.0) {
            return "SW";
        }
        if ((double)heading == 270.0) {
            return "W";
        }
        if ((double)heading == 315.0) {
            return "NW";
        }
        return null;
    }
}

