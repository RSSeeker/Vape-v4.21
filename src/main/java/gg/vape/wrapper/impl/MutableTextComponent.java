package gg.vape.wrapper.impl;

import gg.vape.unmap.TextComponentBase;

public class MutableTextComponent
extends TextComponent {
    public MutableTextComponent(Object object) {
        super(object);
    }

    public TextComponent f(ITextComponent t3_02) {
        return new TextComponent(MutableTextComponent.vapeInstance.getMappingsMapperCompat().RJ.p(this.getObject(), t3_02.getObject()));
    }

    public TextComponent C(TextComponentBase t7_02) {
        return new TextComponent(MutableTextComponent.vapeInstance.getMappingsMapperCompat().RJ.v(this.getObject(), t7_02.getObject()));
    }
}

