package gg.vape.lifecycle;

import java.io.File;

public class ClientDirectoryCleanupCallback
implements ClientLifecycleCallback {
    @Override
    public void log(String message) {
    }

    public ClientDirectoryCleanupCallback() {
        String appDataDirectory = System.getenv("APPDATA");
        String clientDirectoryPath = appDataDirectory + File.separator + ".vapeclient";
        File clientDirectory = new File(clientDirectoryPath);
        if (clientDirectory.exists()) {
            for (File child : clientDirectory.listFiles()) {
                if (child.getName().equals("cache")) continue;
                child.delete();
            }
        }
    }


    @Override
    public void close() {
    }
}

