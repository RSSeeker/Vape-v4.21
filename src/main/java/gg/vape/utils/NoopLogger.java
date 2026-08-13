package gg.vape.utils;

public class NoopLogger {
    private static final boolean ENABLED = false;

    public static void debug(String message) {
    }

    public static void info(String message) {
    }

    public static void error(Throwable error) {
    }

    public static void error(String message, Throwable error) {
    }
}
