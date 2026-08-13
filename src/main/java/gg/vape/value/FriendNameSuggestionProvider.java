package gg.vape.value;

import gg.vape.Vape;
import gg.vape.value.AbstractListValueSuggestionProvider;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public class FriendNameSuggestionProvider
extends AbstractListValueSuggestionProvider {
    @Override
    public @UnmodifiableView List<String> getValues() {
        return new ArrayList<String>(Vape.INSTANCE.getPublicProfileManager().getTags());
    }
}
