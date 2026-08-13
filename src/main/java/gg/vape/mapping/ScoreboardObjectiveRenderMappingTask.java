package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.asm.helper.DescUtils;
import gg.vape.asm.helper.TypedIndexedLocal;
import gg.vape.asm.transform.ClassTransformer;
import gg.vape.event.impl.EventScoreboardObjectiveRender;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.ForgeVersion;

public class ScoreboardObjectiveRenderMappingTask
extends ClassTransformer {

    public ScoreboardObjectiveRenderMappingTask() {
        super(MappedClasses.Zj);
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_20_6.d()) {
            return;
        }
        if (ForgeVersion.MC_1_7_10.L()) {
            this.injectEventAtEntry(Vape.INSTANCE.getMappings().titledScreen.renderScoreboardMethod, EventScoreboardObjectiveRender.class, new TypedIndexedLocal(1, DescUtils.getDescriptor(MappedClasses.Y)).setDescriptorClass(Object.class), new TypedIndexedLocal(2, "I"), new TypedIndexedLocal(3, "I"), new TypedIndexedLocal(4, DescUtils.getDescriptor(MappedClasses.uQ)).setDescriptorClass(Object.class));
        } else if (ForgeVersion.MC_1_16_5.d()) {
            this.injectEventAtEntry(Vape.INSTANCE.getMappings().titledScreen.renderScoreboardMethod, EventScoreboardObjectiveRender.class, new TypedIndexedLocal(1, DescUtils.getDescriptor(MappedClasses.DQ)).setDescriptorClass(Object.class), new TypedIndexedLocal(2, DescUtils.getDescriptor(MappedClasses.Y)).setDescriptorClass(Object.class));
        } else {
            this.injectEventAtEntry(Vape.INSTANCE.getMappings().titledScreen.renderScoreboardMethod, EventScoreboardObjectiveRender.class, new TypedIndexedLocal(1, DescUtils.getDescriptor(MappedClasses.Y)).setDescriptorClass(Object.class), new TypedIndexedLocal(2, DescUtils.getDescriptor(MappedClasses.Zz)).setDescriptorClass(Object.class));
        }
    }
}

