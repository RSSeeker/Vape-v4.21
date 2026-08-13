package gg.vape.module.combat.silentaura;

import gg.vape.module.combat.SilentAura;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class SilentAuraRotationController
extends AdaptiveRotationController {
    private final SilentAura silentAura;

    @Override
    public float getSpeed() {
        double distance;
        EntityPlayerSP player;
        double meanLog = 0.65;
        double stdDevLog = 0.25;
        double uniform1 = Math.random();
        double uniform2 = Math.random();
        if (uniform1 < 1.0E-4) {
            uniform1 = 1.0E-4;
        }
        double gaussian = Math.sqrt(-2.0 * Math.log(uniform1)) * Math.cos(Math.PI * 2 * uniform2);
        double multiplier = Math.exp(meanLog + stdDevLog * gaussian);
        multiplier = Math.max(1.4, Math.min(3.0, multiplier));
        double reach = (double)((Double)this.silentAura.getAimSpeed().getValue()).floatValue() * multiplier;
        EntityLivingBase target = this.silentAura.getTarget();
        if (this.silentAura.getRotationClaim().isOwnedBy(this.silentAura) && (player = Minecraft.thePlayer()).isNotNull() && target != null && (distance = player.i(target.z(), target.N(), target.h())) < 0.8) {
            double distanceFactor = distance / 0.8;
            reach *= distanceFactor;
        }
        return (float)reach;
    }


    public SilentAuraRotationController(SilentAura owner) {
        this.silentAura = owner;
    }
}

