package gg.vape.utils.render;

import gg.vape.utils.render.GlCapabilityState;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GlScissorRect;
import gg.vape.utils.render.PrimitiveTopology;
import gg.vape.utils.render.RenderBatchBuilder;
import gg.vape.utils.render.RenderMatrix4f;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RenderBatch {
    private RenderMatrix4f modelMatrix;
    private GlCapabilityState capabilityState;
    private float lineWidth;
    private PrimitiveTopology topology;
    private GlImageTexture texture;
    private List<Supplier<Void>> standaloneRenderCallbacks = new ArrayList<Supplier<Void>>();
    private GlScissorRect scissorRect;
    private int mergedBuilderCount;
    private ArrayList<RenderBatchBuilder> builders = new ArrayList();
    private Supplier<Void> drawSetupCallback;

    public boolean hasScissorRect() {
        return this.scissorRect != null;
    }

    public void incrementMergedBuilderCount() {
        ++this.mergedBuilderCount;
    }

    public float getLineWidth() {
        return this.lineWidth;
    }

    public List<Supplier<Void>> getStandaloneRenderCallbacks() {
        return this.standaloneRenderCallbacks;
    }

    public void setScissorRect(GlScissorRect scissorRect) {
        this.scissorRect = scissorRect;
    }

    public RenderBatch(RenderBatchBuilder builder) {
        this.texture = builder.getTexture();
        this.modelMatrix = builder.modelMatrix;
        this.topology = builder.getTopology();
        this.lineWidth = builder.getLineWidth();
        this.scissorRect = builder.getScissorRect();
        this.capabilityState = builder.getCapabilityState();
        this.drawSetupCallback = builder.getDrawSetupCallback();
        this.mergedBuilderCount = 1;
        if (builder.getStandaloneRenderCallback() != null) {
            this.standaloneRenderCallbacks.add(builder.getStandaloneRenderCallback());
        } else {
            builder.baseVertexIndex = 0;
            builder.generateIndices(builder.getTopology().name, 0);
        }
        this.builders.add(builder);
    }

    public void addBuilder(RenderBatchBuilder builder) {
        this.builders.add(builder);
        this.addStandaloneRenderCallback(builder.getStandaloneRenderCallback());
    }

    public PrimitiveTopology getTopology() {
        return this.topology;
    }

    public RenderMatrix4f getModelMatrix() {
        return this.modelMatrix;
    }

    public int getMergedBuilderCount() {
        return this.mergedBuilderCount;
    }

    public GlScissorRect getScissorRect() {
        return this.scissorRect;
    }

    public GlImageTexture getTexture() {
        return this.texture;
    }

    public void addStandaloneRenderCallback(Supplier<Void> callback) {
        if (callback != null) {
            this.standaloneRenderCallbacks.add(callback);
        }
    }


    public GlCapabilityState getCapabilityState() {
        return this.capabilityState;
    }

    public Supplier<Void> getDrawSetupCallback() {
        return this.drawSetupCallback;
    }

    public ArrayList<RenderBatchBuilder> getBuilders() {
        return this.builders;
    }
}

