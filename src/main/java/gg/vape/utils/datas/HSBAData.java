package gg.vape.utils.datas;

import gg.vape.utils.datas.HSBData;
import java.awt.Color;

public class HSBAData
extends HSBData {
    private final float I;

    public HSBAData(int n, int n2, int n3, int n4, Color color, float f) {
        super(n, n2, n3, n4, color);
        this.I = f;
    }

    public float n() {
        return this.I;
    }
}

