package gg.vape.mapping;

public class InsertedCallbackMarker {
    private static String[] obfuscationTable;

    public static void initializeMarker() {
    }

    public static void setObfuscationTable(String[] table) {
        obfuscationTable = table;
    }

    public static String[] getObfuscationTable() {
        return obfuscationTable;
    }

    static {
        if (InsertedCallbackMarker.getObfuscationTable() != null) {
            InsertedCallbackMarker.setObfuscationTable(new String[3]);
        }
    }
}
