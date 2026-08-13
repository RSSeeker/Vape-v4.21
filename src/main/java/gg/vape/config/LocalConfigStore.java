package gg.vape.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gg.vape.api.ApiHttpClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Local, offline fallback for the online settings/profile storage. Writes a
 * single JSON document under %APPDATA%\.vapeclient\config.json so module
 * settings, profiles, friends and global preferences persist without the
 * loopback service.
 */
public final class LocalConfigStore {
    private static final String FILE_NAME = "config.json";
    private static final String TMP_NAME = "config.json.tmp";
    private static boolean migrationAttempted = false;

    private LocalConfigStore() {
    }

    public static File baseDirectory() {
        String nativeDirectory = System.getProperty("vape.directory");
        if (nativeDirectory != null && !nativeDirectory.trim().isEmpty()) {
            return new File(nativeDirectory, ".vapeclient");
        }
        String appData = System.getenv("APPDATA");
        if (appData == null || appData.trim().isEmpty()) {
            appData = System.getProperty("user.home");
        }
        return new File(appData, ".vapeclient");
    }

    public static File directory() {
        File directory = baseDirectory();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        migrateLegacyConfig(directory);
        return directory;
    }

    private static void migrateLegacyConfig(File targetDirectory) {
        if (migrationAttempted) {
            return;
        }
        migrationAttempted = true;
        File target = new File(targetDirectory, FILE_NAME);
        if (target.isFile()) {
            return;
        }
        String appData = System.getenv("APPDATA");
        if (appData == null || appData.trim().isEmpty()) {
            appData = System.getProperty("user.home");
        }
        File legacy = new File(new File(appData, ".vapeclient"), FILE_NAME);
        if (!legacy.isFile()) {
            return;
        }
        try {
            Files.copy(legacy.toPath(), target.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception ignored) {
        }
    }

    public static File configFile() {
        return new File(directory(), FILE_NAME);
    }

    private static JsonObject read() {
        File file = configFile();
        if (!file.isFile()) {
            return null;
        }
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
        catch (Exception ignored) {
            return null;
        }
    }

    private static boolean write(JsonObject root) {
        try {
            File target = configFile();
            File temporary = new File(directory(), TMP_NAME);
            try (OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(temporary), StandardCharsets.UTF_8)) {
                ApiHttpClient.GSON.toJson(root, writer);
            }
            if (!temporary.renameTo(target)) {
                Files.move(temporary.toPath(), target.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return true;
        }
        catch (Exception ignored) {
            return false;
        }
    }

    private static JsonObject mutableRoot() {
        JsonObject root = read();
        if (root == null) {
            root = new JsonObject();
            root.addProperty("version", 1);
        }
        return root;
    }

    public static boolean saveSettings(JsonObject settings) {
        if (settings == null) {
            return false;
        }
        JsonObject root = mutableRoot();
        root.add("settings", settings);
        return write(root);
    }

    public static boolean saveProfiles(JsonObject profiles) {
        if (profiles == null) {
            return false;
        }
        JsonObject root = mutableRoot();
        root.add("profiles", profiles);
        return write(root);
    }

    public static boolean saveGlobal(JsonObject global) {
        if (global == null) {
            return false;
        }
        JsonObject root = mutableRoot();
        root.add("global", global);
        return write(root);
    }

    public static JsonObject loadSettings() {
        JsonObject root = read();
        return root != null && root.has("settings")
                ? root.getAsJsonObject("settings") : null;
    }

    public static JsonObject loadProfiles() {
        JsonObject root = read();
        return root != null && root.has("profiles")
                ? root.getAsJsonObject("profiles") : null;
    }

    public static JsonObject loadGlobal() {
        JsonObject root = read();
        return root != null && root.has("global")
                ? root.getAsJsonObject("global") : null;
    }
}
