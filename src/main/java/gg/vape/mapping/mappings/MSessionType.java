package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MSessionType
extends Mapping {
    private MappingField mojangField;
    private MappingField legacyField;
    private MappingField msaField;

    public MSessionType() {
        super(MappedClasses.SESSION_TYPE);
        Class sessionTypeClass = MappedClasses.SESSION_TYPE;
        boolean remapLegacyField = true;
        String legacyFieldName = "LEGACY";
        MSessionType mappings = this;
        this.legacyField = mappings.registerStaticField(legacyFieldName, remapLegacyField, sessionTypeClass);
        Class mojangOwner = MappedClasses.SESSION_TYPE;
        boolean remapMojangField = true;
        String mojangFieldName = "MOJANG";
        this.mojangField = this.registerStaticField(mojangFieldName, remapMojangField, mojangOwner);
        Class msaOwner = MappedClasses.SESSION_TYPE;
        boolean remapMsaField = true;
        String msaFieldName = "MSA";
        this.msaField = this.registerStaticField(msaFieldName, remapMsaField, msaOwner);
    }

    public Object getMojang() {
        return this.mojangField.getObject(null);
    }

    public Object getLegacy() {
        return this.legacyField.getObject(null);
    }

    public Object getMsa() {
        return this.msaField.getObject(null);
    }
}
