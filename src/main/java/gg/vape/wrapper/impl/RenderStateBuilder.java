package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class RenderStateBuilder
extends Wrapper {
    public RenderStateBuilder(Object builderHandle) {
        super(builderHandle);
    }

    public static void drawWithShader(RenderState renderState) {
        RenderStateBuilder.vapeInstance.getMappingsMapperCompat().renderStateBuilder
                .drawWithShader(renderState.getObject());
    }
}
