package gg.vape.utils;


public class TimerUtil {
    protected long Q;
    private static String o;

    public long long_B() {
        return this.Q;
    }


    public static void p(String string) {
        o = string;
    }

    public boolean hasTimeElapsed(long l) {
        return this.getLastMS() >= l;
    }

    public void x(long l) {
        this.Q = System.currentTimeMillis() + l;
    }

    public TimerUtil() {
        this.reset();
    }

    public void reset() {
        this.Q = System.currentTimeMillis();
    }

    public static String p() {
        return o;
    }

    public long getLastMS() {
        return System.currentTimeMillis() - this.Q;
    }

    static {
        if (TimerUtil.p() != null) {
            TimerUtil.p("s6ZeSb");
        }
    }
}

