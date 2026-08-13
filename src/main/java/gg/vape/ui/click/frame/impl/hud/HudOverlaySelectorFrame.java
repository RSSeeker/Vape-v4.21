package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.friend.ui.OnlineActivitySettingsFrame;
import gg.vape.friend.ui.OnlineCombatStatsSettingsFrame;
import gg.vape.friend.ui.OnlinePlayerPreviewSettingsFrame;
import gg.vape.friend.ui.OnlineRadarSettingsFrame;
import gg.vape.manager.ModManager;
import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.none.TextGuiSettingsFrame;
import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.main.ClickGuiLayer;
import gg.vape.ui.click.frame.impl.target.TargetInfoSettingsFrame;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HudOverlaySelectorFrame
extends Frame {
    private final List<HudOverlayEntryInteractiveComponent> entryComponents = new ArrayList<HudOverlayEntryInteractiveComponent>();
    private final HudOverlayEntryPanel headerPanel;
    private final List<HudOverlayEntrySpec> entrySpecs = new ArrayList<HudOverlayEntrySpec>();
    private static final int MAX_COLUMNS = calculateMaxColumns();

    private static double calculateContentWidth(int entryCount) {
        int n2 = Math.min(entryCount, MAX_COLUMNS);
        if (n2 <= 0) {
            n2 = 1;
        }
        return 12.0 + (double)n2 * 22.0 + (double)(n2 - 1) * 2.0;
    }

    @Override
    public void z(boolean bl) {
        double d = bl ? this.K : this.L();
        double d2 = this.G$src$D$1b2f02a();
        double d3 = this.n();
        double d4 = this.A();
        GuiRenderPrimitives.I(d2, d3, d4, d, HudOverlaySelectorFrame.J.m, true, 4.0f, 1.0f, 8.0f, HudOverlaySelectorFrame.J.u);
        GuiRenderPrimitives.g(d2, d3, d4, d, 12.0f, 4.0f, new Color(0, 0, 0, 70));
    }

    private int getRowCount() {
        if (this.entryComponents.isEmpty()) {
            return 0;
        }
        int n = Math.min(this.entryComponents.size(), MAX_COLUMNS);
        return Math.max(1, (int)Math.ceil((double)this.entryComponents.size() / (double)n));
    }

    private static void lambda$getLegitModuleOverlays$2(ModManager modManager, HudModule hudModule, Class clazz) {
        Object obj = modManager.getMod(hudModule.getClass());
        if (obj != null) {
            ((Mod)obj).toggle();
            Object t = ClientSettings.getFrame(clazz);
            if (t != null) {
                boolean enabled = ((Mod)obj).isEnabled();
                ((Frame)t).setVisible(enabled);
                ((Frame)t).c(enabled);
            }
        }
    }

    private void layoutEntries() {
        if (this.entryComponents.isEmpty()) {
            return;
        }
        int n = this.entryComponents.size();
        int n2 = Math.min(n, MAX_COLUMNS);
        int n3 = Math.max(1, (int)Math.ceil((double)n / (double)n2));
        double d = HudOverlaySelectorFrame.calculateContentWidth(n);
        this.o(d);
        this.setRowCount(n3);
        double d2 = 22.0;
        double d3 = this.n() + 18.0;
        double d4 = this.G$src$D$1b2f02a() + 6.0;
        for (int i = 0; i < n; ++i) {
            HudOverlayEntryInteractiveComponent hudOverlayEntryInteractiveComponent = this.entryComponents.get(i);
            int n4 = i % n2;
            int n5 = i / n2;
            double d5 = d4 + (double)n4 * (d2 + 2.0);
            double d6 = d3 + (double)n5 * (d2 + 2.0);
            hudOverlayEntryInteractiveComponent.o(d2);
            hudOverlayEntryInteractiveComponent.Y(d2);
            hudOverlayEntryInteractiveComponent.K(d5);
            hudOverlayEntryInteractiveComponent.S(d6);
        }
    }

    public void addEntry(HudOverlayEntrySpec hudOverlayEntrySpec) {
        ArrayList<HudOverlayEntrySpec> arrayList = new ArrayList<HudOverlayEntrySpec>(this.entrySpecs);
        arrayList.add(hudOverlayEntrySpec);
        this.setEntries(arrayList);
    }

    private void setRowCount(int n) {
        if (n <= 0) {
            n = 1;
        }
        double d = 18.0 + (double)n * 22.0 + (double)(n - 1) * 2.0 + 6.0;
        this.Y(d);
        this.K = d;
    }

    private void layoutHeader() {
        this.headerPanel.o(this.A() - 12.0);
        this.headerPanel.Y(18.0);
        this.headerPanel.K(this.G$src$D$1b2f02a() + 6.0);
        this.headerPanel.S(this.n());
    }

    private static void lambda$new$0() {
        ClientSettings.clickGuiFrameManager.showLayer(ClickGuiLayer.MAIN);
    }

    private static List<HudOverlayEntrySpec> getHudModuleEntries() {
        ArrayList<HudOverlayEntrySpec> arrayList = new ArrayList<HudOverlayEntrySpec>();
        try {
            ModManager modManager = Vape.INSTANCE.getModManager();
            for (Mod mod : new ArrayList<Mod>(modManager.getActiveModuleList())) {
                Class clazz;
                HudModule hudModule;
                if (!(mod instanceof HudModule) || (hudModule = (HudModule)mod).getGroup() != HudModuleGroup.HUD || (clazz = hudModule.getConfigFrameClass()) == null) continue;
                HudOverlayEntrySpec hudOverlayEntrySpec = HudOverlayEntrySpec
                        .forFrame(hudModule.getName(), hudModule.getKey(), clazz)
                        .withSelectedSupplier(() -> HudOverlaySelectorFrame.lambda$getLegitModuleOverlays$1(modManager, hudModule))
                        .withAction(() -> HudOverlaySelectorFrame.lambda$getLegitModuleOverlays$2(modManager, hudModule, clazz));
                arrayList.add(hudOverlayEntrySpec);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return arrayList;
    }

    public HudOverlaySelectorFrame() {
        this.o(370.0);
        this.Y(46.0);
        this.setDisabledOverlayColor(HudOverlaySelectorFrame.J.m);
        this.setShowDisabledOverlay(true);
        this.D(false);
        this.Y(false);
        this.L(false, false);
        this.headerPanel = new HudOverlayEntryPanel(HudOverlaySelectorFrame::lambda$new$0);
        this.headerPanel.setTitle("Overlays");
        this.h(this.headerPanel, new Object[0]);
        this.setEntries(this.getDefaultEntries());
    }

    @Override
    public void Y() {
        this.layoutHeader();
        this.layoutEntries();
    }

    public void setEntries(List<HudOverlayEntrySpec> list) {
        for (HudOverlayEntryInteractiveComponent object : this.entryComponents) {
            this.removeChild(object);
        }
        this.entryComponents.clear();
        this.entrySpecs.clear();
        if (list == null || list.isEmpty()) {
            this.entrySpecs.clear();
            this.setRowCount(0);
            return;
        }
        for (HudOverlayEntrySpec hudOverlayEntrySpec : list) {
            HudOverlayEntryInteractiveComponent hudOverlayEntryInteractiveComponent = new HudOverlayEntryInteractiveComponent(hudOverlayEntrySpec.getIconName(), hudOverlayEntrySpec.getLabel());
            if (hudOverlayEntrySpec.getFrameClass() != null) {
                hudOverlayEntryInteractiveComponent.setFrameClass(hudOverlayEntrySpec.getFrameClass());
            }
            if (hudOverlayEntrySpec.getSelectedSupplier() != null) {
                hudOverlayEntryInteractiveComponent.setSelectedSupplier(hudOverlayEntrySpec.getSelectedSupplier());
            }
            if (hudOverlayEntrySpec.getAction() != null) {
                hudOverlayEntryInteractiveComponent.setAction(hudOverlayEntrySpec.getAction());
            }
            this.entryComponents.add(hudOverlayEntryInteractiveComponent);
            this.h(hudOverlayEntryInteractiveComponent, new Object[0]);
        }
        this.entrySpecs.addAll(list);
        this.o(HudOverlaySelectorFrame.calculateContentWidth(this.entryComponents.size()));
        this.setRowCount(this.getRowCount());
    }

    @Override
    public String getName() {
        return "Overlays";
    }

    private List<HudOverlayEntrySpec> getDefaultEntries() {
        ArrayList<HudOverlayEntrySpec> arrayList = new ArrayList<HudOverlayEntrySpec>();
        arrayList.addAll(Arrays.asList(
                HudOverlayEntrySpec.forFrame("Text GUI", "newtextgui", TextGuiSettingsFrame.class),
                HudOverlayEntrySpec.forFrame("Rearview", "newrearview", OnlinePlayerPreviewSettingsFrame.class),
                HudOverlayEntrySpec.forFrame("Duel Info", "newduelinfo", OnlineCombatStatsSettingsFrame.class),
                HudOverlayEntrySpec.forFrame("Target Info", "newtargetinfo", TargetInfoSettingsFrame.class),
                HudOverlayEntrySpec.forFrame("Radar", "newradar", OnlineRadarSettingsFrame.class),
                HudOverlayEntrySpec.forFrame("Party Overlay", "party hover@2x", OnlineActivitySettingsFrame.class)));
        arrayList.addAll(HudOverlaySelectorFrame.getHudModuleEntries());
        return arrayList;
    }

    private static boolean lambda$getLegitModuleOverlays$1(ModManager modManager, HudModule hudModule) {
        return ((HudModule)modManager.getMod(hudModule.getClass())).isEnabled();
    }

    private static int calculateMaxColumns() {
        double d = 358.0;
        return Math.max(1, (int)Math.floor((d + 2.0) / 24.0));
    }
}
