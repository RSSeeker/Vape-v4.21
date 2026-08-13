package gg.vape.asm.transform.impl;

import gg.vape.Vape;
import gg.vape.asm.ITramsformNode;
import gg.vape.asm.helper.DescUtils;
import gg.vape.asm.helper.TypedIndexedLocal;
import gg.vape.asm.transform.ClassTransformer;
import gg.vape.event.impl.EventClickMouse;
import gg.vape.event.impl.EventGuiOpen;
import gg.vape.event.impl.EventRightClickMouse;
import gg.vape.event.impl.EventSendClickBlockToController;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.ForgeVersion;

public class MinecraftMouseActionTransformer
extends ClassTransformer {
    public MinecraftMouseActionTransformer() {
        super(MappedClasses.uP);
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_20_6.v()) {
            this.injectEventAtEntry(Vape.INSTANCE.getMappings().U.q, EventClickMouse.class, new ITramsformNode[0]);
        }
        if (ForgeVersion.MC_26_2.v()) {
            this.injectEventAtEntry(
                    Vape.INSTANCE.getMappings().U.OS,
                    EventGuiOpen.class,
                    new TypedIndexedLocal(1, DescUtils.getDescriptor(MappedClasses.VW))
                            .setDescriptorClass(Object.class));
        }
        this.injectEventAtEntry(Vape.INSTANCE.getMappings().U.g, EventRightClickMouse.class, new ITramsformNode[0]);
        this.injectEventAtEntry(Vape.INSTANCE.getMappings().U.Of, EventSendClickBlockToController.class, new ITramsformNode[0]);
    }

}

