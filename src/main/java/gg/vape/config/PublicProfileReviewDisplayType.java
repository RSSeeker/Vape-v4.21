package gg.vape.config;

public enum PublicProfileReviewDisplayType {
    OTHER,
    SELF,
    REPLY;

    private static final PublicProfileReviewDisplayType[] ENUM_VALUES;

    static {
        String[] serializedNames = new String[]{"REPLY", "OTHER", "SELF"};



        ENUM_VALUES = new PublicProfileReviewDisplayType[]{OTHER, SELF, REPLY};
    }
}
