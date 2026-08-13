package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class TextFormatting
extends Wrapper {
    public static TextFormatting fromName(String name) {
        Object formattingHandle = TextFormatting.vapeInstance.getMappingsMapperCompat().textFormatting.getByName(name);
        TextFormatting textFormatting = formattingHandle != null ? new TextFormatting(formattingHandle) : null;
        return textFormatting;
    }

    public TextFormatting(Object object) {
        super(object);
    }

    public Integer getColor() {
        return TextFormatting.vapeInstance.getMappingsMapperCompat().textFormatting.getColor(this.getObject());
    }


    public String getName() {
        return TextFormatting.vapeInstance.getMappingsMapperCompat().textFormatting.getName(this.getObject());
    }
}

