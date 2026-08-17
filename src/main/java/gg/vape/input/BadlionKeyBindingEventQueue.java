package gg.vape.input;

import gg.vape.wrapper.impl.KeyBinding;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class BadlionKeyBindingEventQueue {
    private static final ThreadLocal<Boolean> CLICKER_WORKER = new ThreadLocal<Boolean>();
    private static final ConcurrentLinkedQueue<PendingKeyEvent> PENDING_EVENTS =
            new ConcurrentLinkedQueue<PendingKeyEvent>();
    private static final EventDispatcher MINECRAFT_DISPATCHER = new EventDispatcher() {
        @Override
        public void dispatch(int stateKeyCode, boolean pressed,
                boolean triggerTick, int tickKeyCode) {
            KeyBinding.setLegacyKeyBindState(stateKeyCode, pressed);
            if (triggerTick) {
                KeyBinding.onLegacyTick(tickKeyCode);
            }
        }
    };

    private BadlionKeyBindingEventQueue() {
    }

    public static void enterClickerWorker() {
        CLICKER_WORKER.set(Boolean.TRUE);
    }

    public static void leaveClickerWorker() {
        CLICKER_WORKER.remove();
    }

    static boolean isClickerWorkerThread() {
        return Boolean.TRUE.equals(CLICKER_WORKER.get());
    }

    static boolean enqueueIfClickerWorker(int stateKeyCode, boolean pressed,
            boolean triggerTick, int tickKeyCode) {
        if (!isClickerWorkerThread()) {
            return false;
        }
        PENDING_EVENTS.offer(new PendingKeyEvent(stateKeyCode, pressed,
                triggerTick, tickKeyCode));
        return true;
    }

    public static void drain() {
        drain(MINECRAFT_DISPATCHER);
    }

    static void drain(EventDispatcher dispatcher) {
        PendingKeyEvent event;
        while ((event = PENDING_EVENTS.poll()) != null) {
            dispatcher.dispatch(event.stateKeyCode, event.pressed,
                    event.triggerTick, event.tickKeyCode);
        }
    }

    static void clearForTesting() {
        PENDING_EVENTS.clear();
        CLICKER_WORKER.remove();
    }

    interface EventDispatcher {
        void dispatch(int stateKeyCode, boolean pressed, boolean triggerTick,
                int tickKeyCode);
    }

    private static final class PendingKeyEvent {
        private final int stateKeyCode;
        private final boolean pressed;
        private final boolean triggerTick;
        private final int tickKeyCode;

        private PendingKeyEvent(int stateKeyCode, boolean pressed,
                boolean triggerTick, int tickKeyCode) {
            this.stateKeyCode = stateKeyCode;
            this.pressed = pressed;
            this.triggerTick = triggerTick;
            this.tickKeyCode = tickKeyCode;
        }
    }
}
