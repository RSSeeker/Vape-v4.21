package gg.vape.config;

import java.util.Arrays;
import java.util.List;

public enum PublicProfileSortMode {
    RATED("rated", "Top Rated"),
    DOWNLOADED("downloaded", "Most Downloaded"),
    NEWEST("newest", "Newest");

    private final String queryValue;
    public static final List<PublicProfileSortMode> VALUES;
    private static final /* synthetic */ PublicProfileSortMode[] ENUM_VALUES;
    private final String displayName;

    public String getDisplayName() {
        return this.displayName;
    }

    public String getQueryValue() {
        return this.queryValue;
    }

    private PublicProfileSortMode(String queryValue, String displayName) {
        this.queryValue = queryValue;
        this.displayName = displayName;
    }

    static {
        String[] serializedNames = new String[]{"rated", "Most Downloaded", "newest", "downloaded", "Top Rated", "Newest", "RATED", "NEWEST", "DOWNLOADED"};



        ENUM_VALUES = new PublicProfileSortMode[]{RATED, DOWNLOADED, NEWEST};
        VALUES = Arrays.asList(PublicProfileSortMode.values());
    }

}
