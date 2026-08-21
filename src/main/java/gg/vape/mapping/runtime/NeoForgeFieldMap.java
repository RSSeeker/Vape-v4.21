package gg.vape.mapping.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Obfuscated field name -> mojmap field name lookup for the mojmap runtimes
 * (Forge 1.20.1 / NeoForge 1.21.1). The V50/V51 member remap tables carry a
 * mix of mojmap values (e.g. "level") and obfuscated ones (e.g. "a"); on a
 * mojmap runtime the obfuscated values must be translated to the mojmap field
 * names that actually exist.
 */
public final class NeoForgeFieldMap {
    private static volatile Map<String, String> map1201;
    private static volatile Map<String, String> map1211;

    private NeoForgeFieldMap() {
    }

    public static String lookup1201(Class<?> ownerClass, String obfuscatedField) {
        Map<String, String> table = map1201;
        if (table == null) {
            table = load("/mappings/neoforge1201/fields.map");
            map1201 = table;
        }
        return table.get(key(ownerClass, obfuscatedField));
    }

    public static String lookup1211(Class<?> ownerClass, String obfuscatedField) {
        Map<String, String> table = map1211;
        if (table == null) {
            table = load("/mappings/neoforge1211/fields.map");
            map1211 = table;
        }
        return table.get(key(ownerClass, obfuscatedField));
    }

    private static String key(Class<?> ownerClass, String obfuscatedField) {
        return obfuscatedField + "|" + (ownerClass == null ? "" : ownerClass.getName());
    }

    private static Map<String, String> load(String resource) {
        Map<String, String> table = new HashMap<String, String>();
        try (InputStream stream = NeoForgeFieldMap.class.getResourceAsStream(resource)) {
            if (stream == null) {
                return table;
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    int space = line.indexOf(' ');
                    if (space > 0) {
                        table.put(line.substring(0, space), line.substring(space + 1));
                    }
                }
            }
        }
        catch (IOException ignored) {
            // empty table on failure; callers fall back to the source name
        }
        return table;
    }
}
