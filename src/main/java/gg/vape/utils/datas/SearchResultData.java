package gg.vape.utils.datas;

import gg.vape.ui.unmap.SearchBlock;
import gg.vape.utils.datas.HSBData;
import java.awt.Color;
import java.util.concurrent.atomic.AtomicBoolean;

public class SearchResultData
extends HSBData {
    private SearchBlock j;
    private int z;

    public int L() {
        return this.z;
    }

    public void W(int n) {
        this.z = n;
    }

    public boolean n() {
        return this.j.W();
    }

    public Color O() {
        return this.j.B();
    }

    public void t(int n, int n2, int n3, int n4, SearchBlock searchBlock, AtomicBoolean atomicBoolean, int n5) {
        this.F = n;
        this.O = n2;
        this.D = n3;
        this.N = n4;
        this.j = searchBlock;
        this.Q = atomicBoolean;
        this.z = n5;
    }

    public SearchResultData(int n, int n2, int n3, int n4, SearchBlock searchBlock, AtomicBoolean atomicBoolean, int n5) {
        super(n, n2, n3, n4, searchBlock.B(), atomicBoolean);
        this.j = searchBlock;
        this.z = n5;
    }
}

