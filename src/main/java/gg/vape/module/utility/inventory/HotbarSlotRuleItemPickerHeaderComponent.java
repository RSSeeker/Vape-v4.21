package gg.vape.module.utility.inventory;

import gg.vape.module.utility.inventory.HotbarSlotRuleItemPickerFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrameHeaderActionComponent;

public class HotbarSlotRuleItemPickerHeaderComponent
extends PublicProfilesFrameHeaderActionComponent {
    final HotbarSlotRuleItemPickerFrame pickerFrame;

    @Override
    public double x() {
        return 330.0;
    }

    public HotbarSlotRuleItemPickerHeaderComponent(HotbarSlotRuleItemPickerFrame hotbarSlotRuleItemPickerFrame, Frame frame, String string, String string2) {
        super(frame, string, string2);
        this.pickerFrame = hotbarSlotRuleItemPickerFrame;
    }
}
