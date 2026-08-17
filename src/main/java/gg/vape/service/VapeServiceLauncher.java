package gg.vape.service;

import gg.vape.service.http.LegacyHttpServer;
import gg.vape.service.store.FileStore;
import gg.vape.service.zeus.ZeusServer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Embedded launcher that starts the Vape experimental service (HTTP + Zeus)
 * inside the injected Minecraft process. Never throws into the game: any
 * failure is logged and the service is simply not available.
 */
public final class VapeServiceLauncher {
    private static final int DEFAULT_HTTP_PORT = 8080;
    private static final int DEFAULT_ZEUS_PORT = 8091;
    private static final int PORT_PROBE_LIMIT = 20;

    private static volatile LegacyHttpServer httpServer;
    private static volatile ZeusServer zeusServer;
    private static volatile boolean started;

    private VapeServiceLauncher() {
    }

    public static synchronized void start() {
        if (started) {
            return;
        }
        started = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startInternal();
            }
        }, "vape-service");
        thread.setDaemon(true);
        thread.start();
    }

    public static boolean isRunning() {
        return httpServer != null || zeusServer != null;
    }

    public static synchronized void stop() {
        LegacyHttpServer http = httpServer;
        ZeusServer zeus = zeusServer;
        httpServer = null;
        zeusServer = null;
        if (http != null) {
            try {
                http.close();
            } catch (Throwable ignored) {
                // best-effort shutdown
            }
        }
        if (zeus != null) {
            try {
                zeus.close();
            } catch (Throwable ignored) {
                // best-effort shutdown
            }
        }
    }

    private static void startInternal() {
        try {
            Path dataFile = dataFile();
            FileStore store = new FileStore(dataFile);

            int httpPort = findFreePort(DEFAULT_HTTP_PORT);
            int zeusPort = findFreePort(DEFAULT_ZEUS_PORT);

            LegacyHttpServer http = new LegacyHttpServer("127.0.0.1", httpPort, store);
            ZeusServer zeus = new ZeusServer("127.0.0.1", zeusPort, store);

            http.start();
            try {
                zeus.start();
            } catch (Throwable failure) {
                try {
                    http.close();
                } catch (Throwable ignored) {
                    // best-effort rollback
                }
                throw failure;
            }

            httpServer = http;
            zeusServer = zeus;
            System.out.println("[Vape] experimental service started: http://127.0.0.1:" + http.port()
                    + ", zeus://127.0.0.1:" + zeus.port() + ", data=" + dataFile.toAbsolutePath());
        } catch (Throwable failure) {
            httpServer = null;
            zeusServer = null;
            System.out.println("[Vape] experimental service unavailable: " + failure.getMessage());
        }
    }

    private static Path dataFile() {
        String home = System.getProperty("user.home", ".");
        return Paths.get(home, ".vapeclient", "vape-service.json");
    }

    private static int findFreePort(int preferred) {
        for (int offset = 0; offset < PORT_PROBE_LIMIT; offset++) {
            int candidate = preferred + offset;
            if (candidate > 65535) {
                break;
            }
            if (canBind(candidate)) {
                return candidate;
            }
        }
        // Fall back to the preferred port; the server startup will surface the error.
        return preferred;
    }

    private static boolean canBind(int port) {
        try (java.net.ServerSocket socket = new java.net.ServerSocket()) {
            socket.setReuseAddress(false);
            socket.bind(new java.net.InetSocketAddress("127.0.0.1", port));
            return true;
        } catch (IOException exception) {
            return false;
        }
    }
}
