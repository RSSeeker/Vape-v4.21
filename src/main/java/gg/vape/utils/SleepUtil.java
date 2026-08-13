package gg.vape.utils;

public class SleepUtil {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }
}

