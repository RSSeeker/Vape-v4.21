package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.InsetFrameBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class HudModuleListPanel
extends InsetFrameBase {
    private final HudModuleSelectorFrame selectorFrame;
    private final ConcurrentHashMap<HudModule, HudModuleListEntry> entriesByModule = new ConcurrentHashMap();

    @Override
    public String getName() {
        return "LegitModuleFrame";
    }

    public void refreshModules() {
        this.removeMarkedChildren();
        int moduleIndex = 0;
        ArrayList<Mod> mods = Vape.INSTANCE.getModManager().getActiveModuleList();
        this.setVisible(true);
        for (Mod mod : mods) {
            if (!(mod instanceof HudModule)) continue;
            HudModule hudModule = (HudModule)mod;
            HudModuleGroup selectedGroup = this.selectorFrame.getSelectedGroup();
            String searchQuery = this.selectorFrame.getSearchQuery();
            if (selectedGroup == HudModuleGroup.FAVORITE && !hudModule.isFavorite()
                    || selectedGroup != null && selectedGroup != HudModuleGroup.ALL && selectedGroup != HudModuleGroup.FAVORITE && selectedGroup != hudModule.getGroup()
                    || searchQuery != null && !searchQuery.isEmpty() && !hudModule.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                continue;
            }
            this.entriesByModule.computeIfAbsent(hudModule, HudModuleListEntry::new);
            HudModuleListEntry hudModuleListEntry = this.entriesByModule.get(hudModule);
            if (hudModule.getConfigFrameClass() != null) {
                hudModuleListEntry.setConfigFrame((Frame)ClientSettings.getFrame(hudModule.getConfigFrameClass()));
            }
            hudModuleListEntry.applyConfigFrameState();
            this.h(hudModuleListEntry, moduleIndex > 0 && (moduleIndex + 1) % 4 == 0 ? "wrap" : "");
            ++moduleIndex;
        }
    }


    @Override
    public void Y() {
    }

    @Override
    public void c() {
        super.c();
        if (this.selectorFrame.getSelectedGroup() == HudModuleGroup.FAVORITE && !this.hasFavoriteModule()) {
            SmoothFontRenderer smoothFontRenderer = this.getDefaultFontRenderer();
            GuiRenderPrimitives.F("empty", this.G$src$D$1b2f02a() + this.A() / 2.0 - 4.0, this.n() + this.L() / 2.0 - 15.0, 20.0, 20.0, HudModuleListPanel.J.A);
            smoothFontRenderer.d("No Favorites", this.G$src$D$1b2f02a() + this.A() / 2.0 - smoothFontRenderer.N("No Favorites") / 2.0, this.n() + this.L() / 2.0 + 5.0, HudModuleListPanel.J.h);
        }
    }

    @Override
    public void v() {
    }

    @Override
    public void J() {
        if (ClientSettings.getFrame(HudModuleConfigFrame.class).V$src$Z$1xhop3l()) {
            return;
        }
        super.J();
    }

    public HudModuleListPanel(HudModuleSelectorFrame hudModuleSelectorFrame) {
        this.selectorFrame = hudModuleSelectorFrame;
        this.I2 = false;
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().t(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().I(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().U(false);
        this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().u(false);
        this.L(false, true);
        this.setVisible(true);
        this.t(150.0);
        this.refreshModules();
    }

    @Override
    public void U() {
        this.selectorFrame.queueForDisplay();
    }

    @Override
    public boolean d$src$Z$1lx9d06() {
        return false;
    }

    @Override
    public void t(boolean bl, boolean bl2) {
        super.t(bl, bl2);
    }

    private boolean hasFavoriteModule() {
        for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
            HudModule hudModule;
            if (!(mod instanceof HudModule) || !(hudModule = (HudModule)mod).isFavorite()) continue;
            return true;
        }
        return false;
    }

    @Override
    public void dispatchMouseEvent(GuiMouseEvent guiMouseEvent) {
        if (ClientSettings.getFrame(HudModuleConfigFrame.class).V$src$Z$1xhop3l()) {
            ClientSettings.getFrame(HudModuleConfigFrame.class).g(guiMouseEvent);
            return;
        }
        super.dispatchMouseEvent(guiMouseEvent);
    }
}

