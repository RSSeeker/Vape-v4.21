package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.module.Mod;
import gg.vape.module.ModuleDisplayInfo;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.ScaledResolution;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ActiveModuleStackFrame
extends Frame {
    private final LinkedHashSet<Mod> activeModules = new LinkedHashSet();

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public String getName() {
        return "CenterScreenManager";
    }

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public boolean y$src$Z$1f55jvh() {
        return true;
    }

    @Override
    public void u() {
        boolean shouldBeVisible = (ClientSettings.INSTANCE.isInputEnabled()
                || ClientSettings.INSTANCE.isMainGuiStack()) && !this.activeModules.isEmpty();
        if (shouldBeVisible != this.V$src$Z$1xhop3l()) {
            this.setVisible(shouldBeVisible);
        }
    }

    public ActiveModuleStackFrame() {
        this.setShowDisabledOverlay(false);
        this.c(true);
    }

    public void addModule(Mod module) {
        this.activeModules.add(module);
    }

    @Override
    public void I() {
        this.renderStack();
    }

    @Override
    public void v() {
    }

    @Override
    public void H() {
        this.renderStack();
    }


    @Override
    public void Y() {
    }

    public void removeModule(Mod module) {
        this.activeModules.remove(module);
    }

    public void renderStack() {
        double centerY;
        double centerX;
        ScaledResolution scaledResolution = Minecraft.G();
        // Use Vape's own font renderer on every version: Minecraft's native
        // FontRenderer.drawStringWithShadow has no compatible signature on
        // 1.20.6+ (GuiGraphics-based), which silently draws nothing for the
        // health text. The Vape renderer (SmoothFontRenderer) is used by other
        // HUD frames (e.g. KeystrokesCpsCounterComponent) and works everywhere.
        // 1.5 scale keeps the centered health readout clearly legible.
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().p(1.5);
        // Center of the screen. HUD frames render inside renderHudFrames()
        // which pushes scale(guiScale), and EventRender2D's GuiRenderPrimitives.o()
        // also pushes scale(2.0) - so the effective HUD coordinate space is
        // window/(2*scale), exactly how ClockHudFrame etc. position themselves
        // ((J()/2 - offset) / (2*scale)). The old /4 (== /(4*scale)) landed at
        // the quarter point; /2*scale lands on the center.
        centerX = (float)Minecraft.J() / 2.0f
                / (2.0f * (float)Vape.INSTANCE.getClientSettings().getGuiScaleFactor());
        centerY = (float)Minecraft.h() / 2.0f
                / (2.0f * (float)Vape.INSTANCE.getClientSettings().getGuiScaleFactor());
        centerY += 10.0;
        ArrayList<ActiveModuleStackEntry> entries = new ArrayList<ActiveModuleStackEntry>();
        for (Mod module : this.activeModules) {
            ModuleDisplayInfo moduleDisplayInfo = module.getModuleDisplayInfo();
            if (moduleDisplayInfo == null) continue;
            entries.add(new ActiveModuleStackEntry(module, moduleDisplayInfo));
        }
        boolean showModuleName = entries.size() > 1;
        for (ActiveModuleStackEntry entry : entries) {
            String translatedLabel = Vape.INSTANCE.getFontSelector().W()
                    .s(entry.displayInfo.getLabel());
            String text = translatedLabel;
            String widthText = entry.displayInfo.getDescription() != null
                    ? Vape.INSTANCE.getFontSelector().W()
                            .s(entry.displayInfo.getDescription())
                    : translatedLabel;
            double textWidth = smoothFontRenderer.N(widthText);
            double textX = centerX - (double)MathUtil.ceil(textWidth / 2.0);
            if (showModuleName) {
                String moduleSuffix = entry.displayInfo.getSuffix();
                if (moduleSuffix == null) {
                    moduleSuffix = " \u00a77(" + Vape.INSTANCE.getFontSelector().W()
                            .s(entry.module.getName()) + ")";
                }
                text += moduleSuffix;
            }
            smoothFontRenderer.v(text, textX + 1.0, centerY, entry.displayInfo.getColor());
            centerY += smoothFontRenderer.d(text) + 4.0;
        }
    }
}
