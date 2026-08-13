package gg.vape.module.combat.crystalaura;

import gg.vape.module.combat.crystalaura.CrystalAuraAction;
import gg.vape.module.combat.crystalaura.ExplosionType;
import gg.vape.utils.datas.DirectionalPosition;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.Vec3;

public class CrystalAuraActionCandidate {
    private Entity targetEntity;
    private CrystalAuraAction action;
    private final Vec3 position;
    private final ExplosionType explosionType;
    public boolean requiresObsidianPlacement;
    private final double secondaryValue;
    private final double damage;
    public DirectionalPosition directionalPosition;

    public void setTargetEntity(Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public double getSecondaryValue() {
        return this.secondaryValue;
    }

    public ExplosionType getExplosionType() {
        return this.explosionType;
    }

    public CrystalAuraAction getAction() {
        return this.action;
    }

    public CrystalAuraActionCandidate(ExplosionType explosionType,
                                      DirectionalPosition directionalPosition,
                                      Vec3 position, double damage, double secondaryValue) {
        this.explosionType = explosionType;
        this.directionalPosition = directionalPosition;
        this.position = position;
        this.damage = damage;
        this.secondaryValue = secondaryValue;
        this.action = CrystalAuraAction.PLACING_CRYSTAL;
    }

    public Entity getTargetEntity() {
        return this.targetEntity;
    }

    public void setAction(CrystalAuraAction action) {
        this.action = action;
    }

    public DirectionalPosition getDirectionalPosition() {
        return this.directionalPosition;
    }

    public double getDamage() {
        return this.damage;
    }

    public Vec3 getPosition() {
        return this.position;
    }
}
