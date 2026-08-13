package gg.vape.module.render.bedplates;

import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import java.util.Objects;

public class BedPlateBlockStateKey {
    private final int metadata;
    private final boolean nullItem;
    private final int itemId;

    public boolean equals(Object candidate) {
        if (this == candidate) {
            return true;
        }
        if (candidate == null || this.getClass() != candidate.getClass()) {
            return false;
        }
        BedPlateBlockStateKey other = (BedPlateBlockStateKey)candidate;
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.itemId == other.itemId;
        }
        return this.itemId == other.itemId && this.metadata == other.metadata;
    }

    public BedPlateBlockStateKey(int itemId, int metadata) {
        this.itemId = itemId;
        this.metadata = metadata;
        this.nullItem = itemId == 0 || Item.T(itemId).isNull();
    }

    public String toString() {
        return "BlockData{id=" + this.itemId + ", meta=" + this.metadata + '}';
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getMetadata() {
        return this.metadata;
    }

    public boolean isNullItem() {
        return this.nullItem;
    }

    public int hashCode() {
        return Objects.hash(this.itemId, this.metadata);
    }
}

