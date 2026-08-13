package gg.vape.input;

import java.util.LinkedList;
import java.util.Queue;

public class MouseClickRateTracker {
    private static Queue<Long> clickExpirations = new LinkedList<Long>();

    public static int getClicksPerSecond() {
        long now = System.currentTimeMillis();
        while (!clickExpirations.isEmpty() && clickExpirations.peek() < now) {
            clickExpirations.remove();
        }
        return clickExpirations.size();
    }

    public static String formatMouseButton(int button) {
        if (button < 0) {
            button += 100;
        }
        return "M" + (button + 1);
    }


    public static void recordClick() {
        clickExpirations.add(System.currentTimeMillis() + 1000L);
    }
}

