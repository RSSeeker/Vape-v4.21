package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.GuiPlayerTabOverlayBridge;
import gg.vape.wrapper.impl.ITextComponent;
import gg.vape.wrapper.impl.PlayerInfo;
import gg.vape.wrapper.impl.ScorePlayerTeam;

public class EventPlayerTabOverlayDisplayNameLegacy
extends Event {
    private final Object overlayHandle;
    private String displayName = null;
    private String vanillaDisplayName = null;
    private GuiPlayerTabOverlayBridge tabOverlay = null;
    private final Object playerInfoHandle;
    private PlayerInfo playerInfo = null;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public EventListeners getListeners() {
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

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }


    public String getDisplayName() {
        if (this.displayName != null) {
            return this.displayName;
        }
        return this.getVanillaDisplayName();
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        this.setCancelled(true);
    }

    public GuiPlayerTabOverlayBridge getGuiPlayerTabOverlay() {
        if (this.tabOverlay == null) {
            this.tabOverlay = new GuiPlayerTabOverlayBridge(this.overlayHandle);
        }
        return this.tabOverlay;
    }

    private String getVanillaDisplayName() {
        if (this.vanillaDisplayName != null) {
            return this.vanillaDisplayName;
        }
        PlayerInfo playerInfo = this.getNetworkPlayerInfo();
        ITextComponent customDisplayName = playerInfo.R();
        this.vanillaDisplayName = customDisplayName.isNotNull() ? customDisplayName.getFormattedText() : ScorePlayerTeam.formatPlayerName(playerInfo.X(), playerInfo.v().getName());
        return this.vanillaDisplayName;
    }

    public EventPlayerTabOverlayDisplayNameLegacy(Object overlayHandle, Object playerInfoHandle) {
        this.overlayHandle = overlayHandle;
        this.playerInfoHandle = playerInfoHandle;
    }
}

