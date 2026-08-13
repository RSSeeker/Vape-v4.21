package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MDamageSources
extends Mapping {
    private final MappingMethod explosionMethod;
    private static int controlFlowState;
    private final MappingMethod fallMethod;
    private final MappingMethod playerAttackMethod;

    public MDamageSources() {
        super(MappedClasses.ZZ);
        this.playerAttackMethod = this.Y("playerAttack", true, MappedClasses.uB, new Class[]{MappedClasses.Yl});
        this.fallMethod = this.Y("fall", true, MappedClasses.uB, new Class[]{});
        this.explosionMethod = this.Y("explosion", true, MappedClasses.uB, new Class[]{MappedClasses.zc, MappedClasses.zc});
    }

    public Object getFallDamageSource(Object damageSources) {
        return this.fallMethod.invokeObject(damageSources, new Object[0]);
    }

    public Object getPlayerAttackDamageSource(Object damageSources, Object player) {
        return this.playerAttackMethod.invokeObject(damageSources, player);
    }

    public static int getDamageSourcesControlFlowState() {
        return controlFlowState;
    }


    public static void setDamageSourcesControlFlowState(int state) {
        controlFlowState = state;
    }

    static {
        MDamageSources.setDamageSourcesControlFlowState(0);
    }

    public static int getControlFlowConstant() {
        MDamageSources.getDamageSourcesControlFlowState();
        return 112;
    }

    public Object getExplosionDamageSource(Object damageSources) {
        return this.explosionMethod.invokeObject(damageSources, null, null);
    }
}

