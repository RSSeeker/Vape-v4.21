package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MRayTraceContext_BlockMode
extends Mapping {
    private MappingField visualField;
    private MappingField outlineField;
    private MappingField colliderField;

    public static Object getVisual(MRayTraceContext_BlockMode mapping) {
        return mapping.readVisual();
    }

    private Object readCollider() {
        return this.colliderField.getObject(null);
    }

    public static Object getCollider(MRayTraceContext_BlockMode mapping) {
        return mapping.readCollider();
    }

    public MRayTraceContext_BlockMode() {
        super(MappedClasses.DS);
        Class colliderFieldType = MappedClasses.DS;
        boolean colliderFieldPublic = true;
        String colliderFieldName = "COLLIDER";
        MRayTraceContext_BlockMode mapping = this;
        this.colliderField = mapping.registerStaticField(colliderFieldName, colliderFieldPublic, colliderFieldType);
        Class outlineFieldType = MappedClasses.DS;
        boolean outlineFieldPublic = true;
        String outlineFieldName = "OUTLINE";
        MRayTraceContext_BlockMode outlineMapping = this;
        this.outlineField = outlineMapping.registerStaticField(outlineFieldName, outlineFieldPublic, outlineFieldType);
        Class visualFieldType = MappedClasses.DS;
        boolean visualFieldPublic = true;
        String visualFieldName = "VISUAL";
        MRayTraceContext_BlockMode visualMapping = this;
        this.visualField = visualMapping.registerStaticField(visualFieldName, visualFieldPublic, visualFieldType);
    }

    private Object readOutline() {
        return this.outlineField.getObject(null);
    }

    private Object readVisual() {
        return this.visualField.getObject(null);
    }

    public static Object getOutline(MRayTraceContext_BlockMode mapping) {
        return mapping.readOutline();
    }
}

