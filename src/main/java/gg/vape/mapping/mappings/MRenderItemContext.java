package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MRenderItemContext
extends Mapping {
    private static final String GUI_FIELD_NAME = "GUI";
    private final MappingField guiField;

    public static Object getGui(MRenderItemContext mapping) {
        return mapping.readGui();
    }

    public MRenderItemContext() {
        super(MappedClasses.YK);
        this.guiField = this.registerStaticField(GUI_FIELD_NAME, true, MappedClasses.YK);
    }

    private Object readGui() {
        return this.guiField.getObject(null);
    }
}

