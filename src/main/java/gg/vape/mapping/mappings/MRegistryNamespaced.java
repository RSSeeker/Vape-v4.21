package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRegistryNamespaced
extends Mapping {
    private final MappingMethod getByValueMethod;

    public MRegistryNamespaced() {
        super(MappedClasses.lz);
        this.getByValueMethod = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder("getByValue", Object.class, new Class[]{Integer.TYPE}).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "byId")).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.ua)).buildMethod();
    }

    public Object getByValue(Object registry, int id) {
        return this.getByValueMethod.invokeObject(registry, id);
    }
}

