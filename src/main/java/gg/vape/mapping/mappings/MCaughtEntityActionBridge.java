package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MCaughtEntityActionBridge
extends Mapping {
    private final MappingMethod executeMethod;
    private static boolean eventLoopControlFlowState;
    private final MappingMethod inEventLoopMethod;

    public MCaughtEntityActionBridge() {
        this(MCaughtEntityActionBridge.isEventLoopControlFlowStateDisabled());
    }

    private MCaughtEntityActionBridge(boolean bl) {
        super(MappedClasses.VA);
        if (bl) {
            Class[] classArray = new Class[]{};
            Class<Boolean> clazz = Boolean.TYPE;
            boolean bl2 = false;
            String string = "inEventLoop";
            MCaughtEntityActionBridge mCaughtEntityActionBridge = this;
            this.inEventLoopMethod = mCaughtEntityActionBridge.Y(string, bl2, clazz, classArray);
            Class[] classArray2 = new Class[]{Runnable.class};
            Class<Void> clazz2 = Void.TYPE;
            boolean bl3 = false;
            String string2 = "execute";
            MCaughtEntityActionBridge mCaughtEntityActionBridge2 = this;
            this.executeMethod = this.Y(string2, bl3, clazz2, classArray2);
            return;
        }
        Class[] classArray = new Class[]{};
        Class<Boolean> clazz = Boolean.TYPE;
        boolean bl4 = false;
        String string = "inEventLoop";
        MCaughtEntityActionBridge mCaughtEntityActionBridge = this;
        this.inEventLoopMethod = mCaughtEntityActionBridge.Y(string, bl4, clazz, classArray);
        Class[] classArray3 = new Class[]{Runnable.class};
        Class<Void> clazz3 = Void.TYPE;
        boolean bl5 = false;
        String string3 = "execute";
        MCaughtEntityActionBridge mCaughtEntityActionBridge3 = this;
        this.executeMethod = this.Y(string3, bl5, clazz3, classArray3);
        GuiComponent.setLegacyComponentState(new GuiComponent[3]);
    }

    private boolean inEventLoop(Object eventLoop) {
        return this.inEventLoopMethod.invokeBoolean(eventLoop, new Object[0]);
    }

    public static boolean inEventLoop(MCaughtEntityActionBridge mapping, Object eventLoop) {
        return mapping.inEventLoop(eventLoop);
    }

    static {
        MCaughtEntityActionBridge.setEventLoopControlFlowState(false);
    }

    public static boolean getEventLoopControlFlowState() {
        return eventLoopControlFlowState;
    }

    public static boolean isEventLoopControlFlowStateDisabled() {
        boolean bl = MCaughtEntityActionBridge.getEventLoopControlFlowState();
        return !bl;
    }

    public static void execute(MCaughtEntityActionBridge mapping, Object eventLoop, Runnable runnable) {
        mapping.execute(eventLoop, runnable);
    }


    public static void setEventLoopControlFlowState(boolean state) {
        eventLoopControlFlowState = state;
    }

    private void execute(Object eventLoop, Runnable runnable) {
        this.executeMethod.invokeVoid(eventLoop, runnable);
    }
}

