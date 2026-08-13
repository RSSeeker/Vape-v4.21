package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineModeToggleComponent;
import gg.vape.ui.click.component.GuiClickListener;

public class OnlineModeToggleInactiveClickHandler
implements GuiClickListener {
    private final OnlineModeToggleComponent toggle;


    @Override
    public void onPrimaryClick() {
        if (!this.toggle.isLeftSelected().booleanValue()) {
            OnlineModeToggleComponent.toggleSelection(this.toggle);
        }
    }

    public OnlineModeToggleInactiveClickHandler(OnlineModeToggleComponent onlineModeToggleComponent) {
        this.toggle = onlineModeToggleComponent;
    }
}

