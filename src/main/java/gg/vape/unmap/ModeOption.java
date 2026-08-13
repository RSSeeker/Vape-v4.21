package gg.vape.unmap;

import gg.vape.unmap.ModeSelection;

public class ModeOption
extends ModeSelection {
    public ModeOption(String name) {
        this(name, 1.0);
    }

    public boolean isSelected() {
        if (this.getMode() == null) {
            return false;
        }
        return ((ModeSelection)this.getMode().getValue()).equals(this);
    }


    public ModeOption(String name, double displayScale) {
        super(name);
    }
}

