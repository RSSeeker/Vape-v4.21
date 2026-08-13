package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MGlyphProvider
extends Mapping {
    private static int[] glyphSourceControlFlowState;
    private static final String GET_GLYPH_METHOD_NAME;
    public MappingMethod getGlyphMethod;

    public static int[] getGlyphSourceControlFlowState() {
        return glyphSourceControlFlowState;
    }

    static {
        MGlyphProvider.setGlyphSourceControlFlowState(null);
        GET_GLYPH_METHOD_NAME = "getGlyph";
    }

    private Object invokeGetGlyph(Object glyphSourceHandle, int codePoint) {
        return this.getGlyphMethod.invokeObject(glyphSourceHandle, codePoint);
    }

    public MGlyphProvider() {
        this(MGlyphProvider.getGlyphSourceControlFlowState());
    }

    private MGlyphProvider(int[] controlFlowState) {
        super(MappedClasses.Yx);
        int[] currentControlFlowState = controlFlowState;
        Class[] parameterTypes = new Class[]{Integer.TYPE};
        Class returnType = MappedClasses.qd;
        boolean methodPublic = true;
        String methodName = GET_GLYPH_METHOD_NAME;
        MGlyphProvider mapping = this;
        this.getGlyphMethod = mapping.Y(methodName, methodPublic, returnType, parameterTypes);
        if (GuiComponent.getLegacyComponentState() == null) {
            MGlyphProvider.setGlyphSourceControlFlowState(new int[3]);
        }
    }


    public static Object getGlyph(MGlyphProvider mapping, Object glyphSourceHandle, int codePoint) {
        return mapping.invokeGetGlyph(glyphSourceHandle, codePoint);
    }

    public static void setGlyphSourceControlFlowState(int[] controlFlowState) {
        glyphSourceControlFlowState = controlFlowState;
    }
}

