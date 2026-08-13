package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MRenderBatchFlushTarget;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.lang.reflect.Method;

public class MTextureObjectHandle
extends Mapping {
    private MappingField textureIdField;
    private MappingField framebufferCacheOrTextureIdField;
    private MappingField firstFramebufferDepthIdField;
    private MappingField firstFramebufferIdField;

    public MTextureObjectHandle() {
        this(MRenderBatchFlushTarget.getGlCommandEncoderControlFlowState());
    }

    private MTextureObjectHandle(int n) {
        super(MappedClasses.uA);
        int n2 = n;
        if (n2 != 0) {
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "id";
            MTextureObjectHandle mTextureObjectHandle = this;
            this.framebufferCacheOrTextureIdField = mTextureObjectHandle.J(string, bl, clazz);
            if (GuiComponent.getLegacyComponentState() == null) {
                MRenderBatchFlushTarget.setGlCommandEncoderControlFlowState(++n2);
            }
            return;
        }
        Class<Integer> clazz = Integer.TYPE;
        boolean bl = true;
        String string = "id";
        MTextureObjectHandle mTextureObjectHandle = this;
        this.textureIdField = mTextureObjectHandle.J(string, bl, clazz);
        if (ForgeVersion.MC_1_21_11.d()) {
            if (ForgeVersion.MC_26_2.v()) {
                Class<Integer> clazz2 = Integer.TYPE;
                boolean bl2 = true;
                String string2 = "firstFboId";
                MTextureObjectHandle mTextureObjectHandle2 = this;
                this.firstFramebufferIdField = this.J(string2, bl2, clazz2);
                Class<Integer> clazz3 = Integer.TYPE;
                boolean bl3 = true;
                String string3 = "firstFboDepthId";
                MTextureObjectHandle mTextureObjectHandle3 = this;
                this.firstFramebufferDepthIdField = this.J(string3, bl3, clazz3);
                Class clazz4 = MappedClasses.FG;
                boolean bl4 = true;
                String string4 = "fboCache";
                MTextureObjectHandle mTextureObjectHandle4 = this;
                this.framebufferCacheOrTextureIdField = this.J(string4, bl4, clazz4);
            }
        } else {
            Class clazz5 = MappedClasses.FG;
            boolean bl5 = true;
            String string5 = "fboCache";
            MTextureObjectHandle mTextureObjectHandle5 = this;
            this.framebufferCacheOrTextureIdField = this.J(string5, bl5, clazz5);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MRenderBatchFlushTarget.setGlCommandEncoderControlFlowState(++n2);
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public int resolveFramebufferId(Object texture, int depthTextureId) {
        try {
            if (ForgeVersion.MC_1_21_11.d()) {
                if (this.firstFramebufferIdField != null && this.firstFramebufferDepthIdField != null) {
                    Method method;
                    Object object2;
                    int n2;
                    Object object3;
                    int n3;
                    int n4 = this.firstFramebufferDepthIdField.getInt(texture);
                    if (n4 == depthTextureId && (n3 = this.firstFramebufferIdField.getInt(texture)) != -1) {
                        return n3;
                    }
                    if (this.framebufferCacheOrTextureIdField != null && (object3 = this.framebufferCacheOrTextureIdField.getObject(texture)) != null && (n2 = ((Integer)(object2 = (method = object3.getClass().getMethod("get", Integer.TYPE)).invoke(object3, depthTextureId))).intValue()) != 0) {
                        return n2;
                    }
                }
                return -1;
            }
            if (this.framebufferCacheOrTextureIdField == null) {
                return -1;
            }
            Object object4 = this.framebufferCacheOrTextureIdField.getObject(texture);
            if (object4 == null) {
                return -1;
            }
            Method method = object4.getClass().getMethod("get", Integer.TYPE);
            Object object5 = method.invoke(object4, depthTextureId);
            return (Integer)object5;
        }
        catch (Exception exception) {
            return -1;
        }
    }

    public int getTextureId(Object texture) {
        return this.textureIdField.getInt(texture);
    }
}

