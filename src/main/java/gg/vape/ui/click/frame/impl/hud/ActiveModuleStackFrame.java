package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.module.Mod;
import gg.vape.module.ModuleDisplayInfo;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.FontRenderer;
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
        SmoothFontRenderer smoothFontRenderer = null;
        FontRenderer fontRenderer = null;
        if (ForgeVersion.MC_26_1.d()) {
            smoothFontRenderer = Vape.INSTANCE.getFontManager().p(1.0);
        } else {
            fontRenderer = Minecraft.getFontRenderer();
        }
        if (ForgeVersion.MC_26_1.d() || ForgeVersion.MC_1_21_4.v()) {
            centerX = (float)Minecraft.J() / 4.0f;
            centerY = Minecraft.h() / 4;
            centerX /= Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
            centerY /= Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
            centerY += 10.0;
        } else {
            centerX = (float)scaledResolution.getScaledWidth() / 2.0f;
            centerY = (double)(scaledResolution.getScaledHeight() / 2) + 10.0;
        }
        ArrayList<ActiveModuleStackEntry> entries = new ArrayList<ActiveModuleStackEntry>();
        for (Mod module : this.activeModules) {
            ModuleDisplayInfo moduleDisplayInfo = module.getModuleDisplayInfo();
            if (moduleDisplayInfo == null) continue;
            entries.add(new ActiveModuleStackEntry(module, moduleDisplayInfo));
        }
        boolean showModuleName = entries.size() > 1;
        for (ActiveModuleStackEntry entry : entries) {
            String text = entry.displayInfo.getLabel();
            String widthText = entry.displayInfo.getDescription() != null ? entry.displayInfo.getDescription() : text;
            double textWidth = smoothFontRenderer != null
                    ? smoothFontRenderer.N(widthText) : (double)fontRenderer.getStringWidth(widthText);
            double textX = centerX - (double)MathUtil.ceil(textWidth / 2.0);
            if (showModuleName) {
                String moduleSuffix = entry.displayInfo.getSuffix();
                if (moduleSuffix == null) {
                    moduleSuffix = " \u00a77(" + entry.module.getName() + ")";
                }
                text += moduleSuffix;
            }
            if (smoothFontRenderer != null) {
                smoothFontRenderer.v(text, textX + 1.0, centerY, entry.displayInfo.getColor());
                centerY += smoothFontRenderer.d(text) + 4.0;
                continue;
            }
            fontRenderer.drawStringWithShadow(text, textX + 1.0, centerY, entry.displayInfo.getColor());
            centerY += (double)(fontRenderer.FONT_HEIGHT(text) + 4);
        }
    }
}

