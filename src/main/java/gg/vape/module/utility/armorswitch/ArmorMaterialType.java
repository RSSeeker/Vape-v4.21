package gg.vape.module.utility.armorswitch;


public enum ArmorMaterialType {
    LEATHER("leather"),
    IRON("iron"),
    DIAMOND("diamond"),
    GOLD("gold"),
    NETHERITE("netherite"),
    CHAINMAIL("chainmail");

    private static final /* synthetic */ ArmorMaterialType[] VALUES;
    private static boolean initialized;
    private final String materialName;

    public static boolean isInitialized() {
        return initialized;
    }

    public static boolean checkReady() {
        boolean initializedState = ArmorMaterialType.isInitialized();
        return false;
    }

    private ArmorMaterialType(String value) {
        this.materialName = value;
    }

    public boolean G(String string) {
        return string.toLowerCase().contains(this.materialName);
    }

    public String getMaterialName() {
        return this.materialName;
    }

    static {
        if (!ArmorMaterialType.isInitialized()) {
            ArmorMaterialType.setInitialized(true);
        }
        String[] stringArray = new String[]{"NETHERITE", "CHAINMAIL", "GOLD", "iron", "LEATHER", "chainmail", "IRON", "diamond", "DIAMOND", "netherite", "gold", "leather"};






        VALUES = new ArmorMaterialType[]{LEATHER, IRON, DIAMOND, GOLD, NETHERITE, CHAINMAIL};
    }

    public static void setInitialized(boolean value) {
        initialized = value;
    }

}

