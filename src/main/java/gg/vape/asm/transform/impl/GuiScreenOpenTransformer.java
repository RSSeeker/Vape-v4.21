package gg.vape.asm.transform.impl;

import gg.vape.Vape;
import gg.vape.asm.helper.DescUtils;
import gg.vape.asm.helper.TypedIndexedLocal;
import gg.vape.asm.transform.ClassTransformer;
import gg.vape.event.impl.EventGuiOpen;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;

public class GuiScreenOpenTransformer
extends ClassTransformer {
    public GuiScreenOpenTransformer() {
        super(MappedClasses.uH);
    }

    @Override
    public void transform() {
        MappingMethod setScreenMethod = Vape.INSTANCE.getMappings().U.getGuiSetScreenMethod();
        if (setScreenMethod == null || setScreenMethod.getOwnerClass() != MappedClasses.uH) {
            throw new IllegalStateException("Gui.setScreen mapping is unavailable for the 26.2 GUI hook");
        }
        this.injectEventAtEntry(
                setScreenMethod,
                EventGuiOpen.class,
                new TypedIndexedLocal(1, DescUtils.getDescriptor(MappedClasses.VW))
                        .setDescriptorClass(Object.class));
    }
}
