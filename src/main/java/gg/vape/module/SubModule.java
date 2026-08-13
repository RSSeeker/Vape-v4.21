package gg.vape.module;

import gg.vape.event.IEvent;
import gg.vape.value.SubModuleValue;
import java.util.function.Predicate;

public class SubModule<T extends Mod>
extends Mod {
    private static boolean opaqueState;
    private final SubModuleValue selectionValue;
    private final Mod parentModule;
    private boolean enabled;

    public boolean isSubModuleEnabled() {
        return this.enabled;
    }

    @Override
    protected Predicate<IEvent> w() {
        return this::lambda$getEventPredicate$0;
    }

    static {
        if (!SubModule.y$src$Z$h2l5ue()) {
            SubModule.W(true);
        }
    }

    public SubModule(Mod mod, String string) {
        this(mod, string, true);
    }

    private boolean lambda$getEventPredicate$0(IEvent iEvent) {
        return this.isEnabled() && this.isSelectedSubModule();
    }

    public static boolean y$src$Z$h2l5ue() {
        return opaqueState;
    }


    public T getParent() {
        return (T)this.parentModule;
    }

    public SubModule(Mod mod, String string, boolean enabled) {
        super(string);
        this.parentModule = mod;
        this.enabled = enabled;
        this.selectionValue = new SubModuleValue<SubModule>(this);
    }

    public static boolean g$src$Z$gsov5w() {
        boolean bl = SubModule.y$src$Z$h2l5ue();
        return false;
    }

    public SubModuleValue getSelectionValue() {
        return this.selectionValue;
    }

    public boolean isSelectedSubModule() {
        return this.selectionValue.isSelected();
    }

    public static void W(boolean bl) {
        opaqueState = bl;
    }

    public boolean isParentEnabled() {
        return super.isEnabled();
    }
}

