package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MCaughtEntityActionBridge;
import gg.vape.ui.click.component.GuiComponent;

public class MEntityFishHook
extends Mapping {
    private final MappingMethod eventLoopMethod;
    private final MappingMethod isOpenMethod;

    public MEntityFishHook() {
        this(MCaughtEntityActionBridge.isEventLoopControlFlowStateDisabled());
    }

    private MEntityFishHook(boolean bl) {
        super(MappedClasses.q8);
        Class[] classArray = new Class[]{};
        Class<Boolean> clazz = Boolean.TYPE;
        boolean bl2 = false;
        String string = "isOpen";
        MEntityFishHook mEntityFishHook = this;
        this.isOpenMethod = this.Y(string, bl2, clazz, classArray);
        Class[] classArray2 = new Class[]{};
        Class clazz2 = MappedClasses.VA;
        boolean bl3 = false;
        String string2 = "eventLoop";
        MEntityFishHook mEntityFishHook2 = this;
        this.eventLoopMethod = this.Y(string2, bl3, clazz2, classArray2);
        if (bl) {
            if (GuiComponent.getLegacyComponentState() == null) {
                MCaughtEntityActionBridge.setEventLoopControlFlowState(false);
            }
            return;
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MCaughtEntityActionBridge.setEventLoopControlFlowState(true);
        }
    }

    private boolean isOpen(Object channel) {
        return this.isOpenMethod.invokeBoolean(channel, new Object[0]);
    }

    public static Object eventLoop(MEntityFishHook mapping, Object channel) {
        return mapping.eventLoop(channel);
    }

    private Object eventLoop(Object channel) {
        return this.eventLoopMethod.invokeObject(channel, new Object[0]);
    }

    public static boolean isOpen(MEntityFishHook mapping, Object channel) {
        return mapping.isOpen(channel);
    }

}

