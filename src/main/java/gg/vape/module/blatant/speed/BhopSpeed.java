package gg.vape.module.blatant.speed;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreMove;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.blatant.Speed;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionRegistry;
import java.util.List;

public class BhopSpeed
extends SubModule<Speed> {
    private final Speed speed = (Speed)this.getParent();

    @EventHandler
    public void onMove(EventPreMove eventPreMove) {
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        if (entityPlayerSP.h$src$Z$ftwoya()) {
            return;
        }
        if (entityPlayerSP.F() == 0.0f && entityPlayerSP.N$src$F$14ypudi() == 0.0f) {
            this.speed.moveSpeed = this.speed.defaultSpeed();
        }
        if (this.speed.stage == 1 && entityPlayerSP.u$src$Z$g120nz() && (entityPlayerSP.F() != 0.0f || entityPlayerSP.N$src$F$14ypudi() != 0.0f)) {
            this.speed.moveSpeed = 0.25 + this.speed.defaultSpeed() - 0.01;
        }
        if (this.speed.stage == 2 && (entityPlayerSP.F() != 0.0f || entityPlayerSP.N$src$F$14ypudi() != 0.0f) && entityPlayerSP.b$src$Z$fqlxe4()) {
            double jumpMotion = 0.42f;
            if (entityPlayerSP.i(PotionRegistry.Z)) {
                jumpMotion += (double)((float)(entityPlayerSP.b(PotionRegistry.Z).L() + 1) * 0.1f);
            }
            eventPreMove.setY(jumpMotion);
            entityPlayerSP.k(jumpMotion);
            this.speed.moveSpeed *= 2.149;
        } else if (this.speed.stage == 3) {
            double reduction = 0.66 * (this.speed.lastHorizontalDistance - this.speed.defaultSpeed());
            this.speed.moveSpeed = this.speed.lastHorizontalDistance - reduction;
        } else {
            AxisAlignedBB axisAlignedBB = null;
            axisAlignedBB = ForgeVersion.MC_1_7_10.L() ? entityPlayerSP.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().copy().A(0.0, entityPlayerSP.q(), 0.0) : entityPlayerSP.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().A(0.0, entityPlayerSP.q(), 0.0);
            List collisions = Minecraft.theWorld().i(entityPlayerSP, axisAlignedBB);
            if ((collisions.size() > 0 || entityPlayerSP.u$src$Z$g120nz()) && this.speed.stage > 0) {
                this.speed.stage = 1.35 * this.speed.defaultSpeed() - 0.01 > this.speed.moveSpeed ? 0 : (entityPlayerSP.F() != 0.0f || entityPlayerSP.N$src$F$14ypudi() != 0.0f ? 1 : 0);
            }
            this.speed.moveSpeed = this.speed.lastHorizontalDistance - this.speed.lastHorizontalDistance / 159.0;
        }
        if (this.speed.stage > 0) {
            this.speed.moveSpeed = Math.max(this.speed.moveSpeed, this.speed.defaultSpeed());
            this.speed.strafe(eventPreMove, this.speed.moveSpeed, entityPlayerSP);
        }
        if (entityPlayerSP.F() != 0.0f || entityPlayerSP.N$src$F$14ypudi() != 0.0f) {
            ++this.speed.stage;
        }
    }


    public BhopSpeed(Mod mod, String string) {
        super(mod, string);
    }
}

