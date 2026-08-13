package gg.vape.module.render.proj;

import gg.vape.module.render.proj.IProjectile;
import gg.vape.wrapper.impl.EntityEnderPearl;
import java.awt.Color;
import java.util.Set;

public class Projectile
implements IProjectile {
    private final Color color;
    private final Set<Class> entityClasses;

    public Projectile(Set<Class> entityClasses) {
        this(entityClasses, new Color(255, 255, 255));
    }

    public Projectile(Set<Class> entityClasses, Color color) {
        this.entityClasses = entityClasses;
        this.color = color;
    }

    @Override
    public boolean matches(EntityEnderPearl entityEnderPearl) {
        for (Class entityClass : this.entityClasses) {
            if (!entityEnderPearl.isInstance(entityClass)) continue;
            return true;
        }
        return false;
    }

    @Override
    public Color getColor() {
        return this.color;
    }
}
