package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MObjectToIntMapEntry
extends Mapping {
    private MappingMethod getIntValueMethod;
    private static String constructorStateMarker;
    private MappingMethod getKeyMethod;

    public static String getConstructorStateMarker() {
        return constructorStateMarker;
    }

    static {
        MObjectToIntMapEntry.setConstructorStateMarker(null);
    }

    public MObjectToIntMapEntry() {
        this(MObjectToIntMapEntry.getConstructorStateMarker());
    }

    private MObjectToIntMapEntry(String constructorStateMarker) {
        super(MappedClasses.OBJECT_TO_INT_MAP_ENTRY);
        Class[] keyParameterTypes = new Class[]{};
        Class<Object> keyReturnType = Object.class;
        boolean remapKeyMethod = false;
        String keyMethodName = "getKey";
        MObjectToIntMapEntry mappings = this;
        this.getKeyMethod = mappings.Y(keyMethodName, remapKeyMethod, keyReturnType, keyParameterTypes);
        Class[] valueParameterTypes = new Class[]{};
        Class<Integer> valueReturnType = Integer.TYPE;
        boolean remapValueMethod = false;
        String valueMethodName = "getIntValue";
        this.getIntValueMethod = this.Y(valueMethodName, remapValueMethod, valueReturnType, valueParameterTypes);
        String unusedConstructorStateMarker = constructorStateMarker;
    }

    public static void setConstructorStateMarker(String marker) {
        constructorStateMarker = marker;
    }

    public int getIntValue(Object entry) {
        return this.getIntValueMethod.invokeInt(entry, new Object[0]);
    }

    public Object getKey(Object entry) {
        return this.getKeyMethod.invokeObject(entry, new Object[0]);
    }
}
