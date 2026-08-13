package gg.vape.ui.click.component;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiKeyTypedListener;

public class GuiKeyTypedDispatcher {
    public static void dispatch(char character, int keyCode) {
        ClientSettings clientSettings = Vape.INSTANCE.getModManager().getMod(ClientSettings.class);
        if (ClientSettings.activeComponent != null) {
            for (GuiKeyTypedListener keyTypedListener : ClientSettings.activeComponent.getKeyTypedListeners()) {
                keyTypedListener.onKeyTyped(character, keyCode);
            }
        }
    }
}
