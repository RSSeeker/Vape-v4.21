package gg.vape.module.utility.inventory.cleaner;

public enum InventoryMatcherListMode {
    WHITELIST,
    BLACKLIST;

    private static final InventoryMatcherListMode[] VALUES;

    static {
        String[] enumNames = new String[]{"WHITELIST", "BLACKLIST"};


        VALUES = new InventoryMatcherListMode[]{WHITELIST, BLACKLIST};
    }

}

