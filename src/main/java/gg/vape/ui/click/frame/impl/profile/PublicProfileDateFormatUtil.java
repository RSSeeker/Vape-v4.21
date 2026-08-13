package gg.vape.ui.click.frame.impl.profile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublicProfileDateFormatUtil {
    private static final DateFormat p = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
    private static final DateFormat e = new SimpleDateFormat("MMM dd, yyyy");

    public static String T(Date date) {
        return p.format(date);
    }


    public static String H(Date date) {
        return e.format(date);
    }

    public static String i(Date date) {
        long l = System.currentTimeMillis() - date.getTime();
        if (l < 1000L) {
            return "just now";
        }
        if (l < 60000L) {
            long l2 = l / 1000L;
            return l2 + " second" + (l2 == 1L ? "" : "s") + " ago";
        }
        if (l < 3600000L) {
            long l3 = l / 60000L;
            return l3 + " minute" + (l3 == 1L ? "" : "s") + " ago";
        }
        if (l < 86400000L) {
            long l4 = l / 3600000L;
            return l4 + " hour" + (l4 == 1L ? "" : "s") + " ago";
        }
        long l5 = l / 86400000L;
        return l5 + " day" + (l5 == 1L ? "" : "s") + " ago";
    }
}

