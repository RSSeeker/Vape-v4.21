package gg.vape.unmap;

import gg.vape.unmap.Bendable;
import java.util.concurrent.CopyOnWriteArrayList;

class BendableBindList
extends CopyOnWriteArrayList<Integer> {
    final Bendable owner;

    BendableBindList(Bendable owner) {
        this.owner = owner;
    }

    @Override
    public boolean add(Integer bindId) {
        return super.add(bindId);
    }
}
