package gg.vape.tutorial;

public enum TutorialState {
    WELCOME,
    INDEX,
    IN_TUTORIAL,
    COMPLETED_TUTORIAL,
    COMPLETED_ALL,
    FINISHED;

    private static final TutorialState[] p;

    static {
        String[] stringArray = new String[]{"WELCOME", "INDEX", "COMPLETED_TUTORIAL", "IN_TUTORIAL", "COMPLETED_ALL", "FINISHED"};






        p = new TutorialState[]{WELCOME, INDEX, IN_TUTORIAL, COMPLETED_TUTORIAL, COMPLETED_ALL, FINISHED};
    }

}

