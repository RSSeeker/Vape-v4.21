package gg.vape.mapping.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Mojmap member name -> obfuscated member name lookup for the vanilla
 * (obfuscated) 1.20.1 / 1.21.1 runtimes. The V50/V51 remap tables carry
 * mojmap values (e.g. "render", "level"); an obfuscated runtime needs the
 * obfuscated names (e.g. "a") instead, so the translated value is looked up
 * here.
 *
 * Map file lines: "M <mojmapName>|<ownerDots>|<paramDescSrg> <obfuscatedName>"
 *                 "F <mojmapName>|<ownerDots> <obfuscatedName>"
 */
public final class NeoForgeObfMap {
    private static volatile Map<String, String> methods1201;
    private static volatile Map<String, String> fields1201;
    private static volatile Map<String, String> methods1211;
    private static volatile Map<String, String> fields1211;

    private NeoForgeObfMap() {
    }

    public static String lookupMethod1201(Class<?> ownerClass, String mojmapName, String paramDesc) {
        Map<String, String> table = methods1201;
        if (table == null) {
            methods1201 = load("M", "/mappings/neoforge1201/obfmembers.map");
            table = methods1201;
        }
        return table.get(mojmapName + "|" + ownerName(ownerClass) + "|" + paramDesc);
    }

    public static String lookupField1201(Class<?> ownerClass, String mojmapName) {
        Map<String, String> table = fields1201;
        if (table == null) {
            fields1201 = load("F", "/mappings/neoforge1201/obfmembers.map");
            table = fields1201;
        }
        return table.get(mojmapName + "|" + ownerName(ownerClass));
    }

    public static String lookupMethod1211(Class<?> ownerClass, String mojmapName, String paramDesc) {
        Map<String, String> table = methods1211;
        if (table == null) {
            methods1211 = load("M", "/mappings/neoforge1211/obfmembers.map");
            table = methods1211;
        }
        return table.get(mojmapName + "|" + ownerName(ownerClass) + "|" + paramDesc);
    }

    public static String lookupField1211(Class<?> ownerClass, String mojmapName) {
        Map<String, String> table = fields1211;
        if (table == null) {
            fields1211 = load("F", "/mappings/neoforge1211/obfmembers.map");
            table = fields1211;
        }
        return table.get(mojmapName + "|" + ownerName(ownerClass));
    }

    private static String ownerName(Class<?> ownerClass) {
        return ownerClass == null ? "" : ownerClass.getName();
    }

    private static Map<String, String> load(char kind, String resource) {
        Map<String, String> table = new HashMap<String, String>();
        try (InputStream stream = NeoForgeObfMap.class.getResourceAsStream(resource)) {
            if (stream == null) {
                return table;
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.charAt(0) != kind) {
                        continue;
                    }
                    int first = line.indexOf(' ');
                    int second = line.indexOf(' ', first + 1);
                    if (first < 0 || second < 0) {
                        continue;
                    }
                    table.put(line.substring(first + 1, second), line.substring(second + 1));
                }
            }
        }
        catch (IOException ignored) {
            // empty tables on failure; callers fall back to the source name
        }
        return table;
    }
}
