package gg.vape.module;

public enum ModuleDisplayScope {
    ALL,
    STANDALONE_ONLY,
    FRAMES_ONLY;

    private static final ModuleDisplayScope[] VALUES;

    static {
        String[] names = new String[]{"STANDALONE_ONLY", "ALL", "FRAMES_ONLY"};



        VALUES = new ModuleDisplayScope[]{ALL, STANDALONE_ONLY, FRAMES_ONLY};
    }

}

