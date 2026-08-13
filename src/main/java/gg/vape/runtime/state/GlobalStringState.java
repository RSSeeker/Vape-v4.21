package gg.vape.runtime.state;

public class GlobalStringState {
    private static String value;

    public static void setValue(String newValue) {
        value = newValue;
    }

    public static String getValue() {
        return value;
    }

    static {
        if (GlobalStringState.getValue() != null) {
            GlobalStringState.setValue("IfADt");
        }
    }
}
