package gg.vape.module.render.proj;

import gg.vape.mapping.MappedClasses;
import gg.vape.module.render.proj.Projectile;
import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;

public class ArrowProjectile
extends Projectile {
    public ArrowProjectile() {
        super(new HashSet<Class>(Collections.singletonList(MappedClasses.F)), new Color(255, 0, 0));
    }

    @Override
    public float getCollisionHeight() {
        return 0.5f;
    }

    @Override
    public float getCollisionRadius() {
        return 0.25f;
    }
}

