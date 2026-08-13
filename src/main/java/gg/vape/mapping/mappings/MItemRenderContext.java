package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;

public class MItemRenderContext
extends Mapping {
    private final MappingField defaultField;
    private final MappingField noneField;
    private final MappingMethod constructor;

    public Object create(boolean wasItemInteraction, Object heldItem) {
        return this.constructor.invokeObject(null, wasItemInteraction, heldItem);
    }

    public Object getNone() {
        return this.noneField.getObject(null);
    }

    public MItemRenderContext() {
        super(MappedClasses.lj);
        Class[] constructorParameterTypes = new Class[]{Boolean.TYPE, MappedClasses.VK};
        MItemRenderContext mapping = this;
        this.constructor = mapping.registerConstructor(constructorParameterTypes);
        Class noneFieldType = MappedClasses.lj;
        boolean noneFieldPublic = true;
        String noneFieldName = "NONE";
        MItemRenderContext noneMapping = this;
        this.noneField = noneMapping.registerStaticField(noneFieldName, noneFieldPublic, noneFieldType);
        Class defaultFieldType = MappedClasses.lj;
        boolean defaultFieldPublic = true;
        String defaultFieldName = "DEFAULT";
        MItemRenderContext defaultMapping = this;
        this.defaultField = defaultMapping.registerStaticField(defaultFieldName, defaultFieldPublic, defaultFieldType);
    }

    public Object getDefault() {
        return this.defaultField.getObject(null);
    }
}

