package gg.vape.utils.render;

public class RenderBatchStateFlags {
    public final boolean cullFaceEnabled;
    public final boolean blendEnabled;
    public final boolean depthTestEnabled;
    public final boolean depthWriteEnabled;

    public RenderBatchStateFlags(boolean cullFaceEnabled, boolean blendEnabled, boolean depthTestEnabled, boolean depthWriteEnabled) {
        this.cullFaceEnabled = cullFaceEnabled;
        this.blendEnabled = blendEnabled;
        this.depthTestEnabled = depthTestEnabled;
        this.depthWriteEnabled = depthWriteEnabled;
    }
}
