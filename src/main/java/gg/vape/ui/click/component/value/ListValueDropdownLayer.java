package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.value.FloatingValueDropdownLayer;
import gg.vape.ui.click.component.value.ListValueComponent;
import gg.vape.ui.click.component.value.ListValueDropdownCloseClickHandler;
import gg.vape.ui.click.component.value.ListValueOptionsPanel;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrameHeaderActionComponent;

public class ListValueDropdownLayer
extends FloatingValueDropdownLayer<ListValueComponent> {
    private final ListValueOptionsPanel optionsPanel;

    public ListValueOptionsPanel getOptionsPanel() {
        return this.optionsPanel;
    }


    @Override
    public void refreshContents() {
        this.optionsPanel.refreshEntries();
    }

    @Override
    public void u() {
        Frame frame;
        FrameComponent frameComponent;
        super.u();
        ListValueComponent listValueComponent = (ListValueComponent)this.getSourceComponent();
        if (listValueComponent != null && (frameComponent = listValueComponent.getParentFrameComponent()).equals(frame = frameComponent.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa()) && !frame.f().contains(listValueComponent)) {
            ((ListValueComponent)this.getSourceComponent()).setExpanded(false);
        }
    }

    public ListValueDropdownLayer(ListValueComponent listValueComponent) {
        super(listValueComponent);
        this.Y(new PublicProfilesFrameHeaderActionComponent(this, listValueComponent.isBlockedList() ? "blockedicon" : "allowedicon", listValueComponent.getDisplayTitle()).Q(new ListValueDropdownCloseClickHandler(this, listValueComponent)));
        this.optionsPanel = new ListValueOptionsPanel(listValueComponent.getListValue(), listValueComponent.isBlockedList());
        this.addChildren(this.optionsPanel);
    }
}

