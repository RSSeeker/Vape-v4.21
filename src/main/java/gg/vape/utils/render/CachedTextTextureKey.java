package gg.vape.utils.render;

import java.util.Objects;

class CachedTextTextureKey {
    String text;
    int color;

    public String getText() {
        return this.text;
    }

    public CachedTextTextureKey(String text, int color) {
        this.text = text;
        this.color = color;
    }


    public int getColor() {
        return this.color;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (!(object instanceof CachedTextTextureKey)) return false;
        CachedTextTextureKey other = (CachedTextTextureKey)object;
        if (!other.getText().equals(this.getText())) return false;
        if (other.getColor() != this.getColor()) return false;
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.getText(), this.getColor());
    }
}

