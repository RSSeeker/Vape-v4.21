package gg.vape.friend.ui;

import gg.vape.friend.ui.PartyDetailsPanel;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.protocol.packet.GroupOption;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.value.BooleanValue;
import gg.vape.value.Value;
import java.awt.Point;

public class PartyOverviewGroupOptionSyncMouseListener
implements GuiMouseListener {
    private final GroupOption option;
    private boolean previousValue;
    private final PartyDetailsPanel detailsPanel;
    private final BooleanValue booleanValue;
    private final Value value;

    public PartyOverviewGroupOptionSyncMouseListener(PartyDetailsPanel partyDetailsPanel, BooleanValue booleanValue, GroupOption groupOption, Value value) {
        this.detailsPanel = partyDetailsPanel;
        this.booleanValue = booleanValue;
        this.option = groupOption;
        this.value = value;
        this.previousValue = this.booleanValue.getEffectiveValue();
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (this.previousValue != this.booleanValue.getEffectiveValue()) {
            this.previousValue = this.booleanValue.getEffectiveValue();
            ZeusConnectionManager.T().u().Y(this.option, this.value.getValue());
        }
    }

}

