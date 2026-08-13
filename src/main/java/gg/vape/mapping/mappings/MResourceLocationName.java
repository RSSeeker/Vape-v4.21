package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MResourceLocationKey;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MResourceLocationName
extends Mapping {
    private final MappingMethod completeReportMethod;
    private final MappingMethod friendlyReportMethod;

    public String getCompleteReport(Object reportHandle) {
        return (String)this.completeReportMethod.invokeObject(reportHandle);
    }

    public String getFriendlyReport(Object reportHandle, Object resourceKeyHandle) {
        return (String)this.friendlyReportMethod.invokeObject(reportHandle, resourceKeyHandle);
    }


    public MResourceLocationName() {
        this(MResourceLocationKey.A());
    }

    private MResourceLocationName(boolean controlFlowState) {
        super(MappedClasses.qA);
        if (controlFlowState) {
            GuiComponent.setLegacyComponentState(new GuiComponent[1]);
            this.friendlyReportMethod = null;
            if (ForgeVersion.MC_1_8_9.L()) {
                this.Y("getCompleteReport", true, String.class);
            }
            this.completeReportMethod = null;
            return;
        }
        if (MappedClasses.qJ != null) {
            this.friendlyReportMethod = this.Y(
                    "getFriendlyReport", true, String.class, MappedClasses.qJ);
        } else {
            this.friendlyReportMethod = null;
        }
        if (ForgeVersion.MC_1_8_9.L()) {
            this.completeReportMethod = this.Y("getCompleteReport", true, String.class);
        } else {
            this.completeReportMethod = null;
        }
    }
}
