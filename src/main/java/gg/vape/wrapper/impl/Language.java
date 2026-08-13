package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.Wrapper;

public class Language
extends Wrapper {
    public Language(Object languageHandle) {
        super(languageHandle);
    }


    public boolean isUnicode() {
        if (ForgeVersion.MC_1_16_5.d() && !MappedClasses.Vi.isInstance(this.getObject())) {
            return false;
        }
        return Language.vapeInstance.getMappingsMapperCompat().language.isUnicode(this.I);
    }
}

