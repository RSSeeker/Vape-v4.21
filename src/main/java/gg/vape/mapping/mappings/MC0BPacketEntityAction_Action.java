package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MC0BPacketEntityAction_Action
extends Mapping {
    private MappingField stopSneakingActionField;
    private MappingField startSneakingActionField;

    public Object getStopSneakingAction() {
        return this.stopSneakingActionField.getObject(null);
    }

    public MC0BPacketEntityAction_Action() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MC0BPacketEntityAction_Action(GuiComponent[] controlFlowState) {
        super(MappedClasses.Do);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_21_6.v()) {
                    this.startSneakingActionField = this.registerStaticField("PRESS_SHIFT_KEY", Wrapper.isNativeAvailable, MappedClasses.Do);
                    this.stopSneakingActionField = this.registerStaticField("RELEASE_SHIFT_KEY", Wrapper.isNativeAvailable, MappedClasses.Do);
                }
            } else {
                this.startSneakingActionField = this.registerStaticField("START_SNEAKING", Wrapper.isNativeAvailable, MappedClasses.Do);
                this.stopSneakingActionField = this.registerStaticField("STOP_SNEAKING", Wrapper.isNativeAvailable, MappedClasses.Do);
            }
            return;
        }
        this.stopSneakingActionField = this.registerStaticField("STOP_SNEAKING", Wrapper.isNativeAvailable, MappedClasses.Do);
    }

    public Object getStartSneakingAction() {
        return this.startSneakingActionField.getObject(null);
    }
}

