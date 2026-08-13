package gg.vape.config;

import gg.vape.Vape;
import gg.vape.config.ModuleProfileMetadataCodec;
import gg.vape.config.Profile;
import gg.vape.manager.ModManager;
import gg.vape.module.Mod;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;

public abstract class BuiltinProfile
extends Profile {
    private static final String CLIENT_VERSION;
    private static GuiComponent[] sharedGuiComponents;

    private static void clearSelectedModules() {
        ModuleProfileMetadataCodec metadataCodec = Vape.INSTANCE.getModuleProfileMetadataCodec();
        for (Mod module : new ArrayList<Mod>(metadataCodec.getSelectedModules())) {
            metadataCodec.removeModule(module);
        }
    }

    public static GuiComponent[] getSharedGuiComponents() {
        return sharedGuiComponents;
    }

    public final BuiltinProfile applyPreset() {
        BuiltinProfile.clearSelectedModules();
        this.configureModules();
        this.captureCurrentState();
        return this;
    }

    protected final void selectModule(Class<? extends Mod> moduleClass) {
        ModManager modManager = Vape.INSTANCE.getModManager();
        Mod module = modManager.getMod(moduleClass);
        if (module == null) {
            return;
        }
        Vape.INSTANCE.getModuleProfileMetadataCodec().addModule(module);
    }

    protected abstract void configureModules();

    protected BuiltinProfile(String name) {
        super(name, CLIENT_VERSION);
    }

    public abstract boolean isApplicable();


    static {
        BuiltinProfile.setSharedGuiComponents(null);
        CLIENT_VERSION = "4.21";
    }

    public static void setSharedGuiComponents(GuiComponent[] components) {
        sharedGuiComponents = components;
    }
}

