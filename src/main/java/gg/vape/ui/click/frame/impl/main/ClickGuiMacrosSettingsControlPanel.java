package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosSettingsPanel;
import gg.vape.ui.click.frame.impl.main.ClickGuiMacrosSettingsViewMode;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

class ClickGuiMacrosSettingsControlPanel
extends PanelComponent {
    final ClickGuiMacrosSettingsPanel settingsPanel;

    ClickGuiMacrosSettingsControlPanel(ClickGuiMacrosSettingsPanel clickGuiMacrosSettingsPanel, double d, double d2) {
        super(d, d2);
        this.settingsPanel = clickGuiMacrosSettingsPanel;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (this.settingsPanel.getViewMode() == ClickGuiMacrosSettingsViewMode.FULL_SETTINGS) {
            float f = (float)this.L();
            float f2 = (float)this.n() + f * 0.5632f;
            float f3 = (float)this.n() + f;
            if ((float)guiMouseEvent.getY() >= f2 && (float)guiMouseEvent.getY() <= f3 && (double)guiMouseEvent.getX() >= this.G$src$D$1b2f02a() && (double)guiMouseEvent.getX() <= this.G$src$D$1b2f02a() + this.A()) {
                this.settingsPanel.startPrimaryBindCapture();
            }
        }
    }

    @Override
    public void H() {
        boolean bl;
        ClickGuiMacrosSettingsViewMode clickGuiMacrosSettingsViewMode = this.settingsPanel.getViewMode();
        boolean bl2 = bl = clickGuiMacrosSettingsViewMode == ClickGuiMacrosSettingsViewMode.KEYBIND_INPUT || clickGuiMacrosSettingsViewMode == ClickGuiMacrosSettingsViewMode.FULL_SETTINGS;
        if (bl) {
            Color color;
            String string;
            boolean bl3 = clickGuiMacrosSettingsViewMode != ClickGuiMacrosSettingsViewMode.NAME_INPUT;
            float f = (float)this.G$src$D$1b2f02a();
            float f2 = (float)this.n();
            float f3 = (float)this.A();
            float f4 = (float)this.L();
            float f5 = f2 + 8.0f + 4.0f;
            float f6 = f2 + f4 * 0.5632f;
            float f7 = f4 - f4 * 0.5632f;
            GuiRenderPrimitives.B(f, f6, f3, f7, ClickGuiMacrosSettingsPanel.getBorderColor(), 3.0f);
            SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.8);
            smoothFontRenderer.d("Macro name", f, f2, ClickGuiMacrosSettingsControlPanel.J.A);
            Color color2 = bl3 ? ClickGuiMacrosSettingsPanel.getEnabledInputColor() : ClickGuiMacrosSettingsPanel.getDisabledInputColor();
            GuiRenderPrimitives.B(f, f5, f3, 18.0, color2, 3.0f);
            GuiRenderPrimitives.P(f, f5, f3, 18.0, ClickGuiMacrosSettingsPanel.getBorderColor(), 3.0f, 0.75f, 1.0f);
            float f8 = f2 + f4 * 0.5632f;
            float f9 = f8 + 8.0f + 4.0f;
            float f10 = 4.0f;
            float f11 = 6.0f;
            float f12 = f + f11;
            float f13 = f9 - f10 / 2.0f;
            ImageRenderer.drawImage(ClickGuiMacrosSettingsControlPanel.J.h, f12, f13, "newbind", f10, f10, false);
            if (clickGuiMacrosSettingsViewMode == ClickGuiMacrosSettingsViewMode.KEYBIND_INPUT) {
                string = "Press key to bind";
                color = ClickGuiMacrosSettingsControlPanel.J.A;
            } else {
                string = this.settingsPanel.getMacro().getBindText();
                if (string == null || string.isEmpty()) {
                    string = "No bind";
                }
                color = ClickGuiMacrosSettingsControlPanel.J.A;
            }
            SmoothFontRenderer smoothFontRenderer2 = this.getFontRenderer(0.8);
            float f14 = (float)smoothFontRenderer2.d("A");
            float f15 = f12 + f10 + 4.0f;
            float f16 = f9 - f14 / 2.0f;
            smoothFontRenderer2.d(string, f15, f16, color);
            if (clickGuiMacrosSettingsViewMode == ClickGuiMacrosSettingsViewMode.FULL_SETTINGS) {
                float f17 = 4.0f;
                float f18 = 4.0f;
                float f19 = f + f3 - f18 - f17;
                float f20 = f9 - f17 / 2.0f;
                ImageRenderer.drawImage(ClickGuiMacrosSettingsControlPanel.J.h, f19, f20, "newedit", f17, f17, false);
            }
            return;
        }
        boolean bl4 = clickGuiMacrosSettingsViewMode != ClickGuiMacrosSettingsViewMode.NAME_INPUT;
        float f = (float)this.G$src$D$1b2f02a();
        float f21 = (float)this.n();
        float f22 = (float)this.A();
        float f23 = (float)this.L();
        float f24 = f21 + 8.0f + 4.0f;
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.8);
        smoothFontRenderer.d("Macro name", f, f21, ClickGuiMacrosSettingsControlPanel.J.A);
        Color color = bl4 ? ClickGuiMacrosSettingsPanel.getEnabledInputColor() : ClickGuiMacrosSettingsPanel.getDisabledInputColor();
        GuiRenderPrimitives.B(f, f24, f22, 18.0, color, 3.0f);
        GuiRenderPrimitives.P(f, f24, f22, 18.0, ClickGuiMacrosSettingsPanel.getBorderColor(), 3.0f, 0.75f, 1.0f);
    }

}
