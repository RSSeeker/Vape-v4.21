package gg.vape.module.render.trajectories;

import gg.vape.module.render.proj.ArrowProjectile;

public class WideArrowProjectile extends ArrowProjectile {
    @Override
    public float getCollisionRadius() {
        return 0.5f;
    }
}
