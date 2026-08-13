package gg.vape.module.render.search;

public class SearchBlockRenderEntry {
    private int blockId;
    private int metadata;
    private int worldX;
    private int worldY;
    private int worldZ;

    public int getWorldY() {
        return this.worldY;
    }

    public int getMetadata() {
        return this.metadata;
    }

    public int getWorldX() {
        return this.worldX;
    }

    public SearchBlockRenderEntry(int blockId, int metadata, int worldX, int worldY, int worldZ) {
        this.reset(blockId, metadata, worldX, worldY, worldZ);
    }

    public void reset(int blockId, int metadata, int worldX, int worldY, int worldZ) {
        this.blockId = blockId;
        this.metadata = metadata;
        this.worldX = worldX;
        this.worldY = worldY;
        this.worldZ = worldZ;
    }

    public int getBlockId() {
        return this.blockId;
    }

    public int getWorldZ() {
        return this.worldZ;
    }
}

