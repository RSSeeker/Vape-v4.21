package gg.vape.module;

import java.util.HashMap;
import java.util.Map;

public class VisibleModuleList<T> {
    private final HashMap<T, Long> lastSeenTimestamps = new HashMap();
    private final long expiryMillis;

    private void cleanUp() {
        long now = System.currentTimeMillis();
        this.lastSeenTimestamps.entrySet().removeIf(entry -> this.isExpired(now, (Map.Entry)entry));
    }


    public void N(T t) {
        this.cleanUp();
        this.lastSeenTimestamps.put(t, System.currentTimeMillis());
    }

    public VisibleModuleList(long expiry) {
        this.expiryMillis = expiry;
    }

    public void m(T t) {
        this.cleanUp();
        this.lastSeenTimestamps.remove(t);
    }

    public boolean Y(T t) {
        this.cleanUp();
        return this.lastSeenTimestamps.containsKey(t);
    }

    public void R() {
        this.lastSeenTimestamps.clear();
    }

    public int w() {
        this.cleanUp();
        return this.lastSeenTimestamps.size();
    }

    private boolean isExpired(long now, Map.Entry entry) {
        boolean expired = now - (Long)entry.getValue() > this.expiryMillis;
        return expired;
    }
}

