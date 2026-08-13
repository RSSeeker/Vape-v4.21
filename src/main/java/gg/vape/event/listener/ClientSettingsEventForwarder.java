package gg.vape.event.listener;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.friend.ui.OnlinePlayerPreviewComponent;
import gg.vape.module.none.ClientSettings;
import gg.vape.utils.render.RenderUtils;

public class ClientSettingsEventForwarder
implements EventListener {
    private ClientSettings clientSettings;


    @EventHandler
    public void onPreRenderTick(EventPreRenderTick eventPreRenderTick) {
        ClientSettings clientSettings = this.getClientSettings();
        if (clientSettings == null) {
            return;
        }
        OnlinePlayerPreviewComponent.instance.onPreRenderTick(eventPreRenderTick);
        RenderUtils.C();
        clientSettings.updateGuiScale();
    }

    private ClientSettings getClientSettings() {
        if (this.clientSettings == null) {
            this.clientSettings = Vape.INSTANCE.getModManager().getMod(ClientSettings.class);
        }
        return this.clientSettings;
    }

    @EventHandler
    public void onTick(EventPreTick eventPreTick) {
        ClientSettings clientSettings = this.getClientSettings();
        if (clientSettings == null) {
            return;
        }
        clientSettings.onTick();
    }
}

