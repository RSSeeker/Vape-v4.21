package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GuiPlayerTabOverlayBridge;
import gg.vape.wrapper.impl.ITextComponent;
import gg.vape.wrapper.impl.PlayerInfo;
import gg.vape.wrapper.impl.ScorePlayerTeam;
import gg.vape.wrapper.impl.ScorePlayerTeamTextComponent;
import gg.vape.wrapper.impl.TextComponent;
import gg.vape.wrapper.impl.TextComponentBaseBridge;

public class EventPlayerTabOverlayDisplayName
extends Event {
    private final Object overlayHandle;
    private final Object playerInfoHandle;
    private PlayerInfo playerInfo = null;
    private GuiPlayerTabOverlayBridge tabOverlay = null;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private ITextComponent displayName = null;
    private ITextComponent vanillaDisplayName = null;

    public ITextComponent getDisplayName() {
        if (this.displayName != null) {
            return this.displayName;
        }
        return this.getVanillaDisplayName();
    }


    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    public PlayerInfo getNetworkPlayerInfo() {
        if (this.playerInfo == null) {
            this.playerInfo = new PlayerInfo(this.playerInfoHandle);
        }
        return this.playerInfo;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public void setDisplayName(ITextComponent displayName) {
        this.displayName = displayName;
        this.setCancelled(true);
    }

    private ITextComponent getVanillaDisplayName() {
        if (this.vanillaDisplayName != null) {
            return this.vanillaDisplayName;
        }
        PlayerInfo playerInfo = this.getNetworkPlayerInfo();
        ITextComponent customDisplayName = playerInfo.R();
        GuiPlayerTabOverlayBridge guiPlayerTabOverlayBridge = this.getGuiPlayerTabOverlay();
        if (customDisplayName.isNotNull()) {
            TextComponent textComponent = customDisplayName.h();
            this.vanillaDisplayName = ForgeVersion.MC_1_20_6.d() ? guiPlayerTabOverlayBridge.L(playerInfo, new TextComponentBaseBridge(textComponent.getObject())) : guiPlayerTabOverlayBridge.U(playerInfo, textComponent);
        } else {
            ScorePlayerTeam scorePlayerTeam = playerInfo.X();
            String playerName = playerInfo.v().getName();
            if (ForgeVersion.MC_1_20_6.d()) {
                TextComponentBaseBridge textComponentBaseBridge = ITextComponent.a(playerName);
                TextComponentBaseBridge textComponentBaseBridge2 = ScorePlayerTeam.formatNameForTeamMutable(scorePlayerTeam, textComponentBaseBridge);
                this.vanillaDisplayName = guiPlayerTabOverlayBridge.L(playerInfo, textComponentBaseBridge2);
            } else {
                ScorePlayerTeamTextComponent scorePlayerTeamTextComponent = ScorePlayerTeamTextComponent.B(playerName);
                TextComponent textComponent = ScorePlayerTeam.formatNameForTeamText(scorePlayerTeam, scorePlayerTeamTextComponent);
                this.vanillaDisplayName = guiPlayerTabOverlayBridge.U(playerInfo, textComponent);
            }
        }
        return this.vanillaDisplayName;
    }

    public Object getDisplayNameInstance() {
        return this.getDisplayName().getObject();
    }

    public EventPlayerTabOverlayDisplayName(Object overlayHandle, Object playerInfoHandle) {
        this.overlayHandle = overlayHandle;
        this.playerInfoHandle = playerInfoHandle;
    }

    public GuiPlayerTabOverlayBridge getGuiPlayerTabOverlay() {
        if (this.tabOverlay == null) {
            this.tabOverlay = new GuiPlayerTabOverlayBridge(this.overlayHandle);
        }
        return this.tabOverlay;
    }
}

