package gg.vape.reflect;

import gg.vape.reflect.MappingRegistry;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class Mappings {
    public static final Map<String, Set<String>> METHODS = MappingRegistry.METHODS;
    public static final Map<String, Set<String>> METHODS_REVERSED = MappingRegistry.METHODS_REVERSED;
    public static final Map<String, Set<String>> FIELDS = MappingRegistry.FIELDS;
    public static final Map<String, Set<String>> FIELDS_REVERSED = MappingRegistry.FIELDS_REVERSED;

    public static void parse(InputStream stream, Map<String, Set<String>> namesToSrg, Map<String, Set<String>> srgToNames) throws IOException {
        MappingRegistry.parseCsv(stream, namesToSrg, srgToNames);
    }

    public static boolean match(Method method, String name) {
        return MappingRegistry.matches(method, name);
    }

    public static boolean match(Field field, String name) {
        return MappingRegistry.matches(field, name);
    }
}
