package gg.vape.lifecycle;

public interface ClientLifecycleCallback {
    void log(String message);

    void close();
}
