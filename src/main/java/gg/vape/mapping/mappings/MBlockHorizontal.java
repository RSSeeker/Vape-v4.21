package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MBlock;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MBlockHorizontal
extends Mapping {
    private final MappingField facingField;

    public MBlockHorizontal() {
        this(MBlock.m());
    }

    private MBlockHorizontal(GuiComponent[] controlFlowState) {
        super(MappedClasses.FQ);
        Class<?> facingType = controlFlowState == null && ForgeVersion.MC_1_21_4.d() ? MappedClasses.Vh : MappedClasses.lo;
        this.facingField = this.registerStaticField("FACING", true, facingType);
        if (GuiComponent.getLegacyComponentState() == null) {
            MBlock.Y(new GuiComponent[1]);
        }
    }

    private Object readFacing() {
        return this.facingField.getObject(null);
    }

    public static Object getFacing(MBlockHorizontal mapping) {
        return mapping.readFacing();
    }

}
