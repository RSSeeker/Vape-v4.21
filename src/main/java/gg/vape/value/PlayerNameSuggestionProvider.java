package gg.vape.value;

import gg.vape.mapping.MappedClasses;
import gg.vape.value.AbstractListValueSuggestionProvider;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.WorldClient;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public class PlayerNameSuggestionProvider
extends AbstractListValueSuggestionProvider {

    @Override
    public @UnmodifiableView List<String> getValues() {
        ArrayList<String> playerNames = new ArrayList<String>();
        WorldClient world = Minecraft.theWorld();
        if (world.isNull()) {
            return playerNames;
        }
        for (Object entity : world.X()) {
            if (!MappedClasses.Yl.isInstance(entity)) continue;
            EntityPlayer player = new EntityPlayer(entity);
            playerNames.add(player.getName());
        }
        return playerNames;
    }
}

