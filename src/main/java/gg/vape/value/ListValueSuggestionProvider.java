package gg.vape.value;

import java.util.Comparator;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public interface ListValueSuggestionProvider {
    public void setComparator(@Nullable Comparator<String> comparator);

    public @UnmodifiableView List<String> getValues();

    public @UnmodifiableView List<String> getSuggestions();

    @Nullable
    public Comparator<String> getComparator();

    public void updateFilter(String query);

    public void setIncludeAllWhenEmpty(boolean includeAllWhenEmpty);

    public boolean isIncludeAllWhenEmpty();
}
