package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.asm.helper.Local;
import gg.vape.asm.transform.ClassTransformer;
import gg.vape.event.impl.EventPostLocalPlayerTick;
import gg.vape.event.impl.EventPreLocalPlayerTick;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class LocalPlayerTickClassTransformer
extends ClassTransformer {
    private void w() {
        if (ForgeVersion.MC_1_21_10.v()) {
            return;
        }
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().CC.z;
        this.injectEventAtEntry(mappingMethod, EventPreLocalPlayerTick.class, new Local("this").setDescriptorClass(Object.class));
        this.injectEventAtExit(mappingMethod, EventPostLocalPlayerTick.class, new Local("this").setDescriptorClass(Object.class));
    }

    public LocalPlayerTickClassTransformer() {
        super(MappedClasses.z5);
    }


    @Override
    public void transform() {
        this.w();
    }
}

