package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderBatchFlushTarget
extends Mapping {
    private static int glCommandEncoderControlFlowState;
    private static final String DRAW_FROM_BUFFERS_METHOD_NAME;
    public MappingMethod drawFromBuffersMethod;

    public static int getInitialControlFlowState() {
        int n = MRenderBatchFlushTarget.getGlCommandEncoderControlFlowState();
        if (n == 0) {
            return 64;
        }
        return 0;
    }

    public static void setGlCommandEncoderControlFlowState(int state) {
        glCommandEncoderControlFlowState = state;
    }

    public MRenderBatchFlushTarget() {
        this(MRenderBatchFlushTarget.getInitialControlFlowState());
    }

    private MRenderBatchFlushTarget(int n) {
        super(MappedClasses.zg);
        if (n != 0) {
            Class[] classArray = ForgeVersion.MC_26_2.d()
                    ? new Class[]{MappedClasses.uq, Integer.TYPE, Integer.TYPE, Integer.TYPE,
                            MappedClasses.Yz, MappedClasses.FA, Integer.TYPE, Integer.TYPE}
                    : new Class[]{MappedClasses.uq, Integer.TYPE, Integer.TYPE, Integer.TYPE,
                            MappedClasses.Yz, MappedClasses.FA, Integer.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = DRAW_FROM_BUFFERS_METHOD_NAME;
            MRenderBatchFlushTarget mRenderBatchFlushTarget = this;
            this.drawFromBuffersMethod = mRenderBatchFlushTarget.Y(string, bl, clazz, classArray);
            return;
        }
        Class[] classArray = ForgeVersion.MC_26_2.d()
                ? new Class[]{MappedClasses.uq, Integer.TYPE, Integer.TYPE, Integer.TYPE,
                        MappedClasses.Yz, MappedClasses.FA, Integer.TYPE, Integer.TYPE}
                : new Class[]{MappedClasses.uq, Integer.TYPE, Integer.TYPE, Integer.TYPE,
                        MappedClasses.Yz, MappedClasses.FA, Integer.TYPE};
        Class<Void> clazz = Void.TYPE;
        boolean bl = true;
        String string = DRAW_FROM_BUFFERS_METHOD_NAME;
        MRenderBatchFlushTarget mRenderBatchFlushTarget = this;
        this.drawFromBuffersMethod = mRenderBatchFlushTarget.Y(string, bl, clazz, classArray);
        GuiComponent.setLegacyComponentState(new GuiComponent[3]);
    }

    static {
        MRenderBatchFlushTarget.setGlCommandEncoderControlFlowState(0);
        DRAW_FROM_BUFFERS_METHOD_NAME = "drawFromBuffers";
    }

    public static int getGlCommandEncoderControlFlowState() {
        return glCommandEncoderControlFlowState;
    }

}

