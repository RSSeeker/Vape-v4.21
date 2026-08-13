package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MBlock;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MBlockBed
extends Mapping {
    private MappingMethod getBedDirectionMethod;
    private MappingField partField;

    public static Object getPart(MBlockBed mapping) {
        return mapping.readPart();
    }


    private Object readPart() {
        return this.partField.getObject(null);
    }

    public static Object getBedDirection(MBlockBed mapping, Object world, Object blockPos) {
        return mapping.invokeGetBedDirection(world, blockPos);
    }

    private Object invokeGetBedDirection(Object world, Object blockPos) {
        return this.getBedDirectionMethod.invokeObject(null, world, blockPos);
    }

    public MBlockBed() {
        this(MBlock.m());
    }

    private MBlockBed(GuiComponent[] controlFlowState) {
        super(MappedClasses.YE);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                this.partField = this.registerStaticField("PART", true, MappedClasses.Vh);
            }
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            this.getBedDirectionMethod = this.registerStaticMethod("getBedDirection", true, MappedClasses.q0, new Class[]{MappedClasses.zJ, MappedClasses.lf});
        }
        if (ForgeVersion.MC_1_7_10.Y()) {
            this.partField = this.registerStaticField("PART", true, MappedClasses.Vh);
        }
    }
}
