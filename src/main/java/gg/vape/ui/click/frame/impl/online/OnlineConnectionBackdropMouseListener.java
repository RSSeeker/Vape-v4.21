package gg.vape.ui.click.frame.impl.online;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.frame.impl.online.OnlineConnectionSettingsFrame;
import java.awt.Point;

public class OnlineConnectionBackdropMouseListener
implements GuiMouseListener {
    final OnlineConnectionSettingsFrame D;


    @Override
    public boolean Q(Point point) {
        return !this.D.getBounds().R(point);
    }

    public OnlineConnectionBackdropMouseListener(OnlineConnectionSettingsFrame onlineConnectionSettingsFrame) {
        this.D = onlineConnectionSettingsFrame;
    }
}

