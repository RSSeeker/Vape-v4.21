package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventKeyBindingState;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.ForgeVersion;

public class KeyBindingStateEventMappingTask
extends JavassistMappingTask {
    private static final String c;

    public KeyBindingStateEventMappingTask() {
        super(MappedClasses.DR);
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_16_5.d()) {
            this.O(Vape.INSTANCE.getMappings().hJ.s, EventKeyBindingState.class, c, "");
            return;
        }
    }


    static {
        try {
            c = "$0, $1";
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}

