package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.KeystrokesHudFrame;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

public class KeystrokesCpsCounterComponent
extends GuiComponent {
    private final Queue<Long> leftClicks = new LinkedList<Long>();
    private boolean singleButtonMode;
    private final Queue<Long> rightClicks = new LinkedList<Long>();
    private KeystrokesHudFrame frame;
    private static final String CPS_LABEL = "CPS";


    public KeystrokesCpsCounterComponent(KeystrokesHudFrame keystrokesHudFrame) {
        this.frame = keystrokesHudFrame;
    }

    @Override
    public double C() {
        return 0.0;
    }

    public int countActiveClicks(Queue<Long> clicks) {
        long now = System.currentTimeMillis();
        while (!clicks.isEmpty() && clicks.peek() < now) {
            clicks.remove();
        }
        return clicks.size();
    }

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public void F() {
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    public void recordClick(int button) {
        switch (button) {
            case 0: {
                this.leftClicks.add(System.currentTimeMillis() + 1000L);
                break;
            }
            case 1: {
                this.rightClicks.add(System.currentTimeMillis() + 1000L);
            }
        }
    }

    @Override
    public void H() {
        if (this.frame != null) {
            this.renderHudCounter();
        } else {
            this.renderStandaloneCounter();
        }
    }

    public KeystrokesCpsCounterComponent() {
    }

    public double getTextHeight() {
        return this.getFontRenderer(0.65).d(this.countActiveClicks(this.leftClicks) + "");
    }

    @Override
    public void I() {
        this.H();
    }

    public void renderStandaloneCounter() {
        double centerX = this.G$src$D$1b2f02a();
        double centerY = this.n();
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().Y(0.65);
        String leftCps = String.valueOf(this.countActiveClicks(this.leftClicks));
        if (this.singleButtonMode) {
            smoothFontRenderer.v(leftCps, centerX - smoothFontRenderer.N(leftCps) / 2.0,
                    centerY - smoothFontRenderer.d(leftCps) / 2.0,
                    KeystrokesCpsCounterComponent.J.A);
        } else {
            String rightCps = String.valueOf(this.countActiveClicks(this.rightClicks));
            smoothFontRenderer.v("|", centerX - smoothFontRenderer.N("|") / 2.0,
                    centerY + smoothFontRenderer.d("|") / 2.0, KeystrokesCpsCounterComponent.J.A);
            smoothFontRenderer.v(leftCps,
                    centerX - smoothFontRenderer.N("|") / 2.0 - smoothFontRenderer.N(leftCps) - 2.0,
                    centerY + smoothFontRenderer.d(leftCps) / 2.0
                            + (smoothFontRenderer.d("|") - smoothFontRenderer.d(leftCps)) / 2.0,
                    KeystrokesCpsCounterComponent.J.A);
            smoothFontRenderer.v(rightCps, centerX + smoothFontRenderer.N("|") / 2.0 + 2.0,
                    centerY + smoothFontRenderer.d(rightCps) / 2.0
                            + (smoothFontRenderer.d("|") - smoothFontRenderer.d(rightCps)) / 2.0,
                    KeystrokesCpsCounterComponent.J.A);
        }
    }

    private void renderHudCounter() {
        double x = this.G$src$D$1b2f02a();
        double y = this.n();
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().K(1.0, false);
        String leftCps = String.valueOf(this.countActiveClicks(this.leftClicks));
        String rightCps = String.valueOf(this.countActiveClicks(this.rightClicks));
        if (this.singleButtonMode) {
            double cpsWidth = smoothFontRenderer.N(leftCps);
            double labelX = x + 11.0;
            double backgroundWidth = 10.0 + smoothFontRenderer.N(CPS_LABEL) + 1.5 + 8.0;
            if (this.frame.shouldRenderBackground()) {
                GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), backgroundWidth,
                        12.0, this.frame.applyDefaultEditorAlpha(new Color(0, 0, 0, 100)));
                smoothFontRenderer.d(leftCps, labelX - cpsWidth, y + 2.0,
                        this.frame.applyDefaultEditorAlpha(KeystrokesCpsCounterComponent.J.A));
                smoothFontRenderer.d(CPS_LABEL, labelX + 1.5, y + 2.0,
                        this.frame.applyDefaultEditorAlpha(KeystrokesCpsCounterComponent.J.A));
            } else {
                smoothFontRenderer.v(leftCps, labelX - cpsWidth, y + 2.0,
                        this.frame.applyDefaultEditorAlpha(KeystrokesCpsCounterComponent.J.A));
                smoothFontRenderer.v(CPS_LABEL, labelX + 1.5, y + 2.0,
                        this.frame.applyDefaultEditorAlpha(KeystrokesCpsCounterComponent.J.A));
            }
        } else {
            if (!this.frame.shouldRenderHudBackground()) {
                GuiRenderPrimitives.I(this.G$src$D$1b2f02a(), this.n() + 2.0, this.A(), 12.0, this.frame.applyDefaultEditorAlpha(new Color(20, 20, 20, 180)), false, 1.0f, 1.0f, 3.0f, KeystrokesCpsCounterComponent.J.u);
            }
            GuiRenderPrimitives.d(x + this.A() / 2.0, y + 4.0, 1.0, 9.0,
                    this.frame.applyDefaultEditorAlpha(KeystrokesCpsCounterComponent.J.A));
            smoothFontRenderer.d(leftCps, x + 5.0, y + 3.0,
                    this.frame.applyDefaultEditorAlpha(KeystrokesCpsCounterComponent.J.A));
            smoothFontRenderer.d(rightCps,
                    x + this.A() - smoothFontRenderer.N(rightCps) - 5.0, y + 3.0,
                    this.frame.applyDefaultEditorAlpha(KeystrokesCpsCounterComponent.J.A));
        }
    }

    @Override
    public void u() {
    }

    public double getTextWidth() {
        return this.getFontRenderer(0.65).N(this.countActiveClicks(this.leftClicks) + "");
    }

    public int getCps(int button) {
        if (button == 0) {
            return this.countActiveClicks(this.leftClicks);
        }
        if (button == 1) {
            return this.countActiveClicks(this.rightClicks);
        }
        return 0;
    }

    public void setSingleButtonMode(boolean singleButtonMode) {
        this.singleButtonMode = singleButtonMode;
    }
}

