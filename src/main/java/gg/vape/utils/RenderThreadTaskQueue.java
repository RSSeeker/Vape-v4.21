package gg.vape.utils;

import gg.vape.Vape;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderThreadTaskQueue {
    private static final String EXECUTION_ERROR_MESSAGE = "Error executing RenderScheduler task";
    private static final long MAX_EXECUTION_NANOS = 5000000L;
    private static final Queue<Runnable> pendingTasks = new ConcurrentLinkedQueue<Runnable>();

    private static Exception propagateException(Exception error) {
        return error;
    }

    public static void enqueue(Runnable task) {
        pendingTasks.add(task);
    }

    public static void runPendingTasks() {
        if (pendingTasks.isEmpty()) {
            return;
        }
        long startTimeNanos = System.nanoTime();
        while (!pendingTasks.isEmpty()) {
            Runnable task = pendingTasks.poll();
            if (task != null) {
                try {
                    task.run();
                }
                catch (Exception error) {
                    Vape.debugLog(EXECUTION_ERROR_MESSAGE);
                    Vape.logThrowable(error);
                }
            }
            if (System.nanoTime() - startTimeNanos <= MAX_EXECUTION_NANOS) continue;
            break;
        }
    }
}
