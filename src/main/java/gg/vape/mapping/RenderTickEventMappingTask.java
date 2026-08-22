package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.asm.ITramsformNode;
import gg.vape.asm.helper.DescUtils;
import gg.vape.asm.helper.TypedIndexedLocal;
import gg.vape.asm.transform.ClassTransformer;
import gg.vape.event.impl.EventPostRenderTick;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class RenderTickEventMappingTask
extends ClassTransformer {

    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().RY.J;
        if (ForgeVersion.MC_1_21_4.d()) {
            this.injectEventAtEntry(mappingMethod, EventPreRenderTick.class, new TypedIndexedLocal(1, DescUtils.getDescriptor(MappedClasses.uy)).setDescriptorClass(Object.class));
            if (!ForgeVersion.MC_26_2.d()) {
                // 26.2 draws the ClickGUI at the GuiRenderer.render injection
                // point (EventRender2D.create, once per frame); the
                // GameRenderer.update exit is before the frame composite, so
                // anything drawn there would be overwritten anyway.
                this.injectEventAtExit(mappingMethod, EventPostRenderTick.class, new ITramsformNode[0]);
            }
        }
    }

    public RenderTickEventMappingTask() {
        super(MappedClasses.FW);
    }
}

