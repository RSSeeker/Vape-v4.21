package gg.vape.config;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import gg.vape.Vape;
import gg.vape.config.RefreshableSettingsPayload;
import gg.vape.friend.OnlineFriend;
import gg.vape.manager.client.OnlineConnectionManager;
import java.util.LinkedHashMap;
import java.util.Map;

public class OnlineSettingsPayload
implements RefreshableSettingsPayload {
    @SerializedName(value="inventorySwitchMode")
    private Integer inventorySwitchMode;
    @SerializedName(value="partyShowTarget")
    private Boolean partyShowTarget;
    @SerializedName(value="autoLogin")
    private Boolean autoLogin;
    @SerializedName(value="showSelf")
    private Boolean showSelf;
    @SerializedName(value="showUsername")
    private Boolean showUsername;
    @SerializedName(value="showInventoryKeybind")
    private JsonArray showInventoryKeybind;
    @SerializedName(value="friendStates")
    private Map<Long, Boolean> friendStates;
    @SerializedName(value="shareInventory")
    private Boolean shareInventory;
    public static final OnlineSettingsPayload DEFAULTS = new OnlineSettingsPayload();
    @SerializedName(value="showServer")
    private Boolean showServer;
    @SerializedName(value="pingKeybind")
    private JsonArray pingKeybind;

    public Boolean getShowUsername() {
        return this.showUsername;
    }

    public Boolean getShowSelf() {
        if (this.showSelf == null) {
            this.showSelf = OnlineSettingsPayload.DEFAULTS.showSelf;
        }
        return this.showSelf;
    }

    public JsonArray getShowInventoryKeybind() {
        return this.showInventoryKeybind;
    }

    public Boolean getAutoLogin() {
        return this.autoLogin;
    }

    public Boolean getPartyShowTarget() {
        return this.partyShowTarget;
    }

    static {
        DEFAULTS.initializeDefaults();
    }

    public Boolean getShareInventory() {
        if (this.shareInventory == null) {
            this.shareInventory = OnlineSettingsPayload.DEFAULTS.shareInventory;
        }
        return this.shareInventory;
    }

    @Override
    public void initializeDefaults() {
        if (this.autoLogin == null) {
            this.autoLogin = (Boolean)OnlineConnectionManager.INSTANCE.getSettings().getAutoLogin().getDefaultValue();
        }
        if (this.friendStates == null) {
            this.friendStates = new LinkedHashMap<Long, Boolean>();
        }
        if (this.showServer == null) {
            this.showServer = (Boolean)OnlineConnectionManager.INSTANCE.getSettings().getShareServer().getDefaultValue();
        }
        if (this.showUsername == null) {
            this.showUsername = (Boolean)OnlineConnectionManager.INSTANCE.getSettings().getShareUsername().getDefaultValue();
        }
        if (this.partyShowTarget == null) {
            this.partyShowTarget = (Boolean)OnlineConnectionManager.INSTANCE.getSettings().getTargetIndicators().getDefaultValue();
        }
        if (this.shareInventory == null) {
            this.shareInventory = (Boolean)OnlineConnectionManager.INSTANCE.getSettings().getShareInventory().getDefaultValue();
        }
        if (this.pingKeybind == null) {
            this.pingKeybind = OnlineConnectionManager.INSTANCE.getSettings().getPingBind().serializeBoundInputs();
        }
        if (this.showInventoryKeybind == null) {
            this.showInventoryKeybind = OnlineConnectionManager.INSTANCE.getSettings().getInventoryDisplayBind().serializeBoundInputs();
        }
        if (this.inventorySwitchMode == null) {
            this.inventorySwitchMode = OnlineConnectionManager.INSTANCE.getSettings().getInventorySwitchMode().getSelectedIndex();
        }
        if (this.showSelf == null) {
            this.showSelf = (Boolean)OnlineConnectionManager.INSTANCE.getSettings().getShowSelf().getDefaultValue();
        }
    }

    public Boolean getShowServer() {
        return this.showServer;
    }

    @Override
    public void refreshFromCurrentSettings() {
        this.autoLogin = OnlineConnectionManager.INSTANCE.getSettings().getAutoLogin().getEffectiveValue();
        this.friendStates.clear();
        for (OnlineFriend onlineFriend : Vape.INSTANCE.getOnlineFriendManager().getFriends()) {
            if (!onlineFriend.isSyncWithFriends()) continue;
            this.friendStates.put(onlineFriend.getUser().getId(), true);
        }
        this.showServer = OnlineConnectionManager.INSTANCE.getSettings().getShareServer().getEffectiveValue();
        this.showUsername = OnlineConnectionManager.INSTANCE.getSettings().getShareUsername().getEffectiveValue();
        this.partyShowTarget = OnlineConnectionManager.INSTANCE.getSettings().getTargetIndicators().getEffectiveValue();
        this.shareInventory = OnlineConnectionManager.INSTANCE.getSettings().getShareInventory().getEffectiveValue();
        this.pingKeybind = OnlineConnectionManager.INSTANCE.getSettings().getPingBind().serializeBoundInputs();
        this.inventorySwitchMode = OnlineConnectionManager.INSTANCE.getSettings().getInventorySwitchMode().getSelectedIndex();
        this.showSelf = OnlineConnectionManager.INSTANCE.getSettings().getShowSelf().getEffectiveValue();
        this.showInventoryKeybind = OnlineConnectionManager.INSTANCE.getSettings().getInventoryDisplayBind().serializeBoundInputs();
    }


    public JsonArray getPingKeybind() {
        return this.pingKeybind;
    }

    public Integer getInventorySwitchMode() {
        return this.inventorySwitchMode;
    }

    public Map<Long, Boolean> getFriendStates() {
        return this.friendStates;
    }
}

