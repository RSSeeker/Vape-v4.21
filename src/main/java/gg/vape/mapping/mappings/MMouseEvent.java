package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MEntityRenderer;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMouseEvent
extends Mapping {
    public MappingField Q;
    public MappingField Z;

    public MMouseEvent() {
        this(MEntityRenderer.n());
    }

    private MMouseEvent(int n) {
        super(MappedClasses.U);
        if (n != 0) {
            if (ForgeVersion.MC_1_17.d()) {
                Class<Integer> clazz = Integer.TYPE;
                boolean bl = true;
                String string = "state";
                MMouseEvent mMouseEvent = this;
                this.Q = mMouseEvent.J(string, bl, clazz);
                Class<Boolean> clazz2 = Boolean.TYPE;
                boolean bl2 = true;
                String string2 = "enabled";
                MMouseEvent mMouseEvent2 = this;
                this.Z = this.J(string2, bl2, clazz2);
            } else {
                Class<Integer> clazz = Integer.TYPE;
                boolean bl = true;
                String string = "capability";
                MMouseEvent mMouseEvent = this;
                this.Q = mMouseEvent.J(string, bl, clazz);
                Class<Boolean> clazz3 = Boolean.TYPE;
                boolean bl3 = true;
                String string3 = "currentState";
                MMouseEvent mMouseEvent3 = this;
                this.Z = this.J(string3, bl3, clazz3);
            }
            return;
        }
        Class<Boolean> clazz = Boolean.TYPE;
        boolean bl = true;
        String string = "currentState";
        MMouseEvent mMouseEvent = this;
        this.Z = mMouseEvent.J(string, bl, clazz);
    }

    public int n(Object object) {
        return this.Q.getInt(object);
    }

    public boolean J(Object object) {
        return this.Z.getBoolean(object);
    }

    public void T(Object object, boolean bl) {
        this.Z.setBoolean(object, bl);
    }

}

