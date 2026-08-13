package gg.vape.ui.click.frame.impl.profile;

import gg.vape.Vape;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import java.util.ArrayList;
import java.util.List;

public class ProfilesSettingsFrameState {
    public static List<GuiComponent> F(boolean bl) {
        ArrayList<GuiComponent> arrayList = new ArrayList<GuiComponent>();
        arrayList.add(new BooleanToggleComponent(Vape.INSTANCE.getPublicProfileSettings().autoLoadModuleStates));
        if (!bl) {
            arrayList.add(new BooleanToggleComponent(Vape.INSTANCE.getPublicProfileSettings().framePositionsPerProfile));
        }
        return arrayList;
    }

}

