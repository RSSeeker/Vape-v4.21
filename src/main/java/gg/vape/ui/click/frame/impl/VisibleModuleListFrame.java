package gg.vape.ui.click.frame.impl;

import gg.vape.Vape;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.module.ModuleComponent;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrame;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeader;
import java.util.concurrent.CopyOnWriteArrayList;

public class VisibleModuleListFrame
extends ModuleCategoryFrame {
    private static VisibleModuleListFrame U2;


    public VisibleModuleListFrame() {
        super(Category.FAVORITES);
        U2 = this;
    }

    @Override
    public void s$src$V$1a2f6mi() {
        this.HF = new ModuleCategoryFrameHeader(this, "newfavorites", "Favorites");
        this.Y(this.HF);
    }

    public static VisibleModuleListFrame U$src$Lgg_vape_ui_click_frame_impl_VisibleModuleListFr$168oq2s() {
        return U2;
    }

    @Override
    public void v() {
    }

    public static void e() {
        U2.removeMarkedChildren();
        try {
            CopyOnWriteArrayList<Mod> copyOnWriteArrayList = new CopyOnWriteArrayList<Mod>(Vape.INSTANCE.getModuleProfileMetadataCodec().getSelectedModules());
            for (Mod mod : copyOnWriteArrayList) {
                ModuleComponent moduleComponent = new ModuleComponent(U2, mod);
                U2.h(moduleComponent, new Object[0]);
                moduleComponent.buildValueComponents();
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    @Override
    public void Y() {
    }

    public static ModuleComponent A(Mod mod) {
        for (GuiComponent guiComponent : U2.f()) {
            if (!(guiComponent instanceof ModuleComponent) || !((ModuleComponent)guiComponent).getModule().equals(mod)) continue;
            return (ModuleComponent)guiComponent;
        }
        return null;
    }
}

