package gg.vape.mapping.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Obfuscated -> mojmap class-name lookup for the mojmap runtimes (Forge
 * 1.20.1 / NeoForge 1.21.1). The remap tables (V50/V51) only cover legacy MCP
 * names; some module code still references obfuscated class names (e.g.
 * com/mojang/blaze3d/platform/GlStateManager$b), which must be translated to
 * their mojmap counterparts on these runtimes.
 */
public final class NeoForgeClassMap {
    private static volatile Map<String, String> map1201;
    private static volatile Map<String, String> map1211;

    private NeoForgeClassMap() {
    }

    public static String lookupObfuscated1201(String obfuscatedName) {
        Map<String, String> table = map1201;
        if (table == null) {
            table = load("/mappings/vanilla1201/joined.srg");
            map1201 = table;
        }
        return table.get(obfuscatedName);
    }

    public static String lookupObfuscated1211(String obfuscatedName) {
        Map<String, String> table = map1211;
        if (table == null) {
            table = load("/mappings/vanilla1211/joined.srg");
            map1211 = table;
        }
        return table.get(obfuscatedName);
    }

    private static Map<String, String> load(String resource) {
        Map<String, String> table = new HashMap<String, String>();
        try (InputStream stream = NeoForgeClassMap.class.getResourceAsStream(resource)) {
            if (stream == null) {
                return table;
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith("CL: ")) {
                        continue;
                    }
                    String[] columns = line.split("\\s+");
                    if (columns.length == 3) {
                        table.put(columns[1], columns[2]);
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
