package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MLanguage
extends Mapping {
    private final MappingField unicodeField;

    public boolean isUnicode(Object languageHandle) {
        return this.unicodeField.getBoolean(languageHandle);
    }

    public MLanguage() {
        this(MMappedClassSlotFp.l());
    }

    private MLanguage(GuiComponent[] legacyComponentState) {
        super(MappedClasses.Fn);
        if (ForgeVersion.MC_1_16_5.d()) {
            this.unicodeField = this.registerInstanceFieldForOwner(
                    MappedClasses.Vi, "field_239496_d_", Wrapper.isNativeAvailable, Boolean.TYPE);
        } else if (ForgeVersion.MC_1_7_10.L()) {
            this.unicodeField = this.J("field_135029_d", Wrapper.isNativeAvailable, Boolean.TYPE);
        } else {
            this.unicodeField = this.J("unicode", true, Boolean.TYPE);
        }
    }

}

