package gg.vape.lifecycle;

import gg.vape.config.LocalConfigStore;
import java.io.File;

public class ClientDirectoryCleanupCallback
implements ClientLifecycleCallback {
    @Override
    public void log(String message) {
    }

    public ClientDirectoryCleanupCallback() {
        File clientDirectory = LocalConfigStore.baseDirectory();
        if (clientDirectory.exists()) {
            for (File child : clientDirectory.listFiles()) {
                if (child.getName().equals("cache")
                        || child.getName().equals("config.json")
                        || child.getName().equals("log")) {
                    continue;
                }
                child.delete();
            }
        }
    }


    @Override
    public void close() {
    }
}
