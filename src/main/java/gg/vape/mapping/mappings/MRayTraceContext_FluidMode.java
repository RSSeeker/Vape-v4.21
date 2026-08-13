package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MRayTraceContext_FluidMode
extends Mapping {
    private MappingField anyField;
    private MappingField noneField;
    private MappingField sourceOnlyField;

    private Object readSourceOnly() {
        return this.sourceOnlyField.getObject(null);
    }

    public static Object getAny(MRayTraceContext_FluidMode mapping) {
        return mapping.readAny();
    }

    public static Object getSourceOnly(MRayTraceContext_FluidMode mapping) {
        return mapping.readSourceOnly();
    }

    private Object readNone() {
        return this.noneField.getObject(null);
    }

    private Object readAny() {
        return this.anyField.getObject(null);
    }

    public static Object getNone(MRayTraceContext_FluidMode mapping) {
        return mapping.readNone();
    }

    public MRayTraceContext_FluidMode() {
        super(MappedClasses.Dm);
        Class noneFieldType = MappedClasses.Dm;
        boolean noneFieldPublic = true;
        String noneFieldName = "NONE";
        MRayTraceContext_FluidMode mapping = this;
        this.noneField = mapping.registerStaticField(noneFieldName, noneFieldPublic, noneFieldType);
        Class sourceOnlyFieldType = MappedClasses.Dm;
        boolean sourceOnlyFieldPublic = true;
        String sourceOnlyFieldName = "SOURCE_ONLY";
        MRayTraceContext_FluidMode sourceOnlyMapping = this;
        this.sourceOnlyField = sourceOnlyMapping.registerStaticField(sourceOnlyFieldName, sourceOnlyFieldPublic, sourceOnlyFieldType);
        Class anyFieldType = MappedClasses.Dm;
        boolean anyFieldPublic = true;
        String anyFieldName = "ANY";
        MRayTraceContext_FluidMode anyMapping = this;
        this.anyField = anyMapping.registerStaticField(anyFieldName, anyFieldPublic, anyFieldType);
    }
}

