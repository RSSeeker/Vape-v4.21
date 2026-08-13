package gg.vape.ui.click.component;

import gg.vape.Vape;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.click.component.TooltipTextSegment;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;

public class ToolTips
extends SimpleTextLabelComponent {
    private GuiComponent targetComponent;
    private TooltipTextSegment headerSegment;
    private boolean active = false;


    public ToolTips(GuiComponent targetComponent, String text, double fontScale, Color color, boolean bold) {
        super(text, fontScale, color, bold);
        this.targetComponent = targetComponent;
    }

    public GuiComponent getTargetComponent() {
        return this.targetComponent;
    }

    @Override
    public void H() {
        if (!this.targetComponent.w$src$Z$e457mb() || !this.targetComponent.t() || !this.targetComponent.V$src$Z$1xhop3l() || this.targetComponent.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa() != null && !this.targetComponent.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa().i$src$Z$1f7f2w6()) {
            this.targetComponent.hideToolTips();
            return;
        }
        SmoothFontRenderer fontRenderer = this.getFontRenderer(this.fontScale);
        double headerY = this.n() + 2.0;
        double contentHeight = 0.0;
        double contentWidth = 0.0;
        if (this.headerSegment != null) {
            contentHeight = this.headerSegment.getHeight() + 3.0;
            contentWidth = this.headerSegment.getWidth();
        }
        String formattedText = Vape.INSTANCE.getFontSelector().W().s(this.text);
        String[] lines = formattedText.split("\n");
        for (String line : lines) {
            double lineWidth = fontRenderer.N(line);
            if (lineWidth > contentWidth) {
                contentWidth = lineWidth;
            }
            contentHeight += fontRenderer.d(line) + 1.0;
        }
        double tooltipX = this.G$src$D$1b2f02a() + 8.0;
        if (tooltipX + contentWidth + 8.0 > (double)Minecraft.J() / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() / 2.0) {
            tooltipX = tooltipX - contentWidth - 6.0 - 12.0;
        }
        double tooltipY = this.n();
        if (tooltipY + contentHeight + 2.5 + 2.0 > (double)Minecraft.h() / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() / 2.0) {
            tooltipY = tooltipY - contentHeight - 2.5;
        }
        GuiRenderPrimitives.I(tooltipX, tooltipY, contentWidth + 6.0, contentHeight + 2.5, ToolTips.J.r, true, 2.0f, 1.0f, 6.0f, ToolTips.J.i);
        GuiRenderPrimitives.P(tooltipX, tooltipY, contentWidth + 6.0, contentHeight + 2.5, new Color(35, 35, 35), 2.0f, 0.75f, 1.0f);
        if (this.headerSegment != null) {
            this.headerSegment.renderAt(this.G$src$D$1b2f02a() + 11.0, headerY);
            tooltipY += this.headerSegment.getHeight() + 2.0;
        }
        tooltipY += 2.0;
        for (String line : lines) {
            fontRenderer.d(line, tooltipX + 3.0, tooltipY, ToolTips.J.Z);
            tooltipY += fontRenderer.d(line) + 1.0;
        }
        this.active = false;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ToolTips(GuiComponent targetComponent, String text, double fontScale, Color color, boolean bold, String headerText, double headerFontScale, Color headerColor, boolean headerBold) {
        super(text, fontScale, color, bold);
        this.targetComponent = targetComponent;
        this.headerSegment = new TooltipTextSegment(this, headerText, headerFontScale, headerColor, headerBold);
    }

    public ToolTips(GuiComponent targetComponent, String text) {
        super(text, 0.75);
        this.targetComponent = targetComponent;
    }

    public boolean isActive() {
        return this.active;
    }
}

