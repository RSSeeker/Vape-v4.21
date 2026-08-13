package gg.vape.module.render.animations;

import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.render.Animations;

public abstract class AnimationsMode
extends SubModule<Animations> {
    public boolean shouldBlock() {
        return false;
    }

    public abstract boolean isBlocking();

    public AnimationsMode(Mod parent, String name) {
        super(parent, name);
    }
}
