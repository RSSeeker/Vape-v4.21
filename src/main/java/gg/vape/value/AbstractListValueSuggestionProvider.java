package gg.vape.value;

import gg.vape.value.ListValueSuggestionProvider;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public abstract class AbstractListValueSuggestionProvider
implements ListValueSuggestionProvider {
    private static String legacyToken;
    @Nullable
    private Comparator<String> suggestionComparator = AbstractListValueSuggestionProvider::compareIgnoringCase;
    private List<String> filteredSuggestions;
    private boolean includeAllWhenEmpty = false;

    @Override
    public @UnmodifiableView List<String> getSuggestions() {
        return this.filteredSuggestions;
    }

    private static int compareIgnoringCase(String firstSuggestion, String secondSuggestion) {
        return firstSuggestion.compareToIgnoreCase(secondSuggestion);
    }

    @Override
    public boolean isIncludeAllWhenEmpty() {
        return this.includeAllWhenEmpty;
    }

    @Override
    @Nullable
    public Comparator<String> getComparator() {
        return this.suggestionComparator;
    }

    public static String getLegacyToken() {
        return legacyToken;
    }

    @Override
    public void setIncludeAllWhenEmpty(boolean includeAllWhenEmpty) {
        this.includeAllWhenEmpty = includeAllWhenEmpty;
    }


    @Override
    public void setComparator(@Nullable Comparator<String> comparator) {
        this.suggestionComparator = comparator;
    }

    private List<String> filterSuggestions(String query) {
        ArrayList<String> matches = new ArrayList<String>();
        if (!this.isIncludeAllWhenEmpty() && query.isEmpty()) {
            return matches;
        }
        List<String> values = this.getValues();
        String normalizedQuery = query.toLowerCase();
        if (values != null) {
            for (String candidate : values) {
                if ((!this.isIncludeAllWhenEmpty() || !normalizedQuery.isEmpty()) && !candidate.toLowerCase().startsWith(normalizedQuery)) continue;
                matches.add(candidate);
            }
        }
        if (this.suggestionComparator != null) {
            matches.sort(this.suggestionComparator);
        }
        return matches;
    }

    public static void setLegacyToken(String token) {
        legacyToken = token;
    }

    @Override
    public void updateFilter(String query) {
        this.filteredSuggestions = this.filterSuggestions(query);
    }

    public AbstractListValueSuggestionProvider() {
        this.updateFilter("");
    }

    static {
        if (AbstractListValueSuggestionProvider.getLegacyToken() == null) {
            AbstractListValueSuggestionProvider.setLegacyToken("Cvgij");
        }
    }
}

