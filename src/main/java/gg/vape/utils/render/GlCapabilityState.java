package gg.vape.utils.render;

import gg.vape.wrapper.impl.GlStateManager;
import org.lwjgl.opengl.GL11;

public class GlCapabilityState {
    private static String legacyMarker;
    public boolean depthTestEnabled;
    public boolean depthWriteEnabled;
    public boolean blendEnabled;
    public boolean cullFaceEnabled;

    public static String getLegacyMarker() {
        return legacyMarker;
    }

    public static void setLegacyMarker(String legacyMarker) {
        GlCapabilityState.legacyMarker = legacyMarker;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof GlCapabilityState)) {
            return false;
        }
        GlCapabilityState other = (GlCapabilityState)object;
        return this.toBitMask() == other.toBitMask();
    }

    public void setDepthWriteEnabled(boolean depthWriteEnabled) {
        this.depthWriteEnabled = depthWriteEnabled;
    }

    public GlCapabilityState copy() {
        return new GlCapabilityState(this);
    }

    public boolean disableCapability(int capability) {
        switch (capability) {
            case GL11.GL_DEPTH_TEST:
                this.depthTestEnabled = false;
                return true;
            case GL11.GL_CULL_FACE:
                this.cullFaceEnabled = false;
                return true;
            case GL11.GL_BLEND:
                this.blendEnabled = false;
                return true;
            default:
                return false;
        }
    }

    public void apply() {
        if (this.depthTestEnabled) {
            GlStateManager.enableDepth();
        } else {
            GlStateManager.disableDepth();
        }
        if (this.blendEnabled) {
            GlStateManager.enableBlend();
        } else {
            GlStateManager.disableBlend();
        }
        GL11.glDepthMask((boolean)this.depthWriteEnabled);
    }

    public long toBitMask() {
        long state = 0L;
        state |= (this.cullFaceEnabled ? 1L : 0L) << 0;
        state |= (this.depthTestEnabled ? 1L : 0L) << 1;
        state |= (this.blendEnabled ? 1L : 0L) << 2;
        state |= (this.depthWriteEnabled ? 1L : 0L) << 3;
        return state;
    }

    public GlCapabilityState(GlCapabilityState source) {
        this.cullFaceEnabled = source.cullFaceEnabled;
        this.depthTestEnabled = source.depthTestEnabled;
        this.blendEnabled = source.blendEnabled;
        this.depthWriteEnabled = source.depthWriteEnabled;
    }

    public boolean enableCapability(int capability) {
        switch (capability) {
            case GL11.GL_DEPTH_TEST:
                this.depthTestEnabled = true;
                return true;
            case GL11.GL_CULL_FACE:
                this.cullFaceEnabled = true;
                return true;
            case GL11.GL_BLEND:
                this.blendEnabled = true;
                return true;
            default:
                return false;
        }
    }

    public GlCapabilityState() {
    }


    static {
        if (GlCapabilityState.getLegacyMarker() != null) {
            GlCapabilityState.setLegacyMarker("DrzNk");
        }
    }
}
