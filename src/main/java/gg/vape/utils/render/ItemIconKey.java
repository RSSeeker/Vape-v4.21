package gg.vape.utils.render;

import java.util.Objects;

public class ItemIconKey {
    private int armorColor;
    private int itemId;
    private int metadata;
    private float scale = 1.0f;


    public boolean equals(Object object) {
        if (object instanceof ItemIconKey) {
            ItemIconKey other = (ItemIconKey)object;
            return other.getItemId() == this.getItemId() && other.getMetadata() == this.getMetadata() && other.getScale() == this.getScale() && other.getArmorColor() == this.getArmorColor();
        }
        return false;
    }

    public int getItemId() {
        return this.itemId;
    }

    public ItemIconKey(int itemId, int metadata, float scale) {
        this.itemId = itemId;
        this.metadata = metadata;
        this.scale = scale;
    }

    public int hashCode() {
        return Objects.hash(this.getItemId(), this.getMetadata(), this.getArmorColor());
    }

    public float getScale() {
        return this.scale;
    }

    public int getArmorColor() {
        return this.armorColor;
    }

    public int getMetadata() {
        return this.metadata;
    }

    public void setArmorColor(int armorColor) {
        this.armorColor = armorColor;
    }
}

