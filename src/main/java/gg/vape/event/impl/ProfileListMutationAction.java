package gg.vape.event.impl;

public enum ProfileListMutationAction {
    ADD,
    REMOVE;

    private static final ProfileListMutationAction[] VALUES_COPY;

    static {
        String[] declaredNames = new String[]{"REMOVE", "ADD"};


        VALUES_COPY = new ProfileListMutationAction[]{ADD, REMOVE};
    }
}
