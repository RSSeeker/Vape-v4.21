package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MEnumActionResult
extends Mapping {
    public final MappingField passField;
    public final MappingField successField;
    public final MappingField failField;

    public Object getPass() {
        return this.passField.getObject(null);
    }

    public MEnumActionResult() {
        super(MappedClasses.zr);
        this.successField = this.registerStaticField("SUCCESS", true, MappedClasses.zr);
        this.passField = this.registerStaticField("PASS", true, MappedClasses.zr);
        this.failField = this.registerStaticField("FAIL", true, MappedClasses.zr);
    }

    public Object getFail() {
        return this.failField.getObject(null);
    }

    public Object getSuccess() {
        return this.successField.getObject(null);
    }
}

