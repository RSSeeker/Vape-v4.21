package gg.vape.module.blatant.phase;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreMotion;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.wrapper.impl.CPacketPlayerPosition;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;

public class HypixelPhase
extends SubModule {
    public HypixelPhase(Mod mod, String string) {
        super(mod, string);
    }

    @EventHandler
    public void onMotionUpdate(EventPreMotion eventPreMotion) {
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        if (entityPlayerSP.isNull()) {
            return;
        }
        double speed = 0.163;
        double cosYaw = Math.cos(Math.toRadians(entityPlayerSP.J() + 90.0f));
        double sinYaw = Math.sin(Math.toRadians(entityPlayerSP.J() + 90.0f));
        double offsetX = (double)entityPlayerSP.movementInput().D() * speed * cosYaw + (double)entityPlayerSP.movementInput().T() * speed * sinYaw;
        double offsetZ = (double)entityPlayerSP.movementInput().D() * speed * sinYaw - (double)entityPlayerSP.movementInput().T() * speed * cosYaw;
        if (entityPlayerSP.r() && !entityPlayerSP.S$src$Z$151gttj()) {
            if (ForgeVersion.MC_1_7_10.Y()) {
                entityPlayerSP.sendQueue().addToSendQueue(CPacketPlayerPosition.newInstance(entityPlayerSP.z() + offsetX, entityPlayerSP.N(), entityPlayerSP.h() + offsetZ, false));
                entityPlayerSP.sendQueue().addToSendQueue(CPacketPlayerPosition.newInstance(entityPlayerSP.z(), entityPlayerSP.N() - 0.4982374987, entityPlayerSP.h(), false));
            } else {
                entityPlayerSP.sendQueue().addToSendQueue(CPacketPlayerPosition.newInstance(entityPlayerSP.z() + offsetX, entityPlayerSP.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMinY(), entityPlayerSP.N(), entityPlayerSP.h() + offsetZ, false));
                entityPlayerSP.sendQueue().addToSendQueue(CPacketPlayerPosition.newInstance(entityPlayerSP.z(), entityPlayerSP.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMinY() - 0.4982374987, entityPlayerSP.N() - 0.4982374987, entityPlayerSP.h(), false));
            }
        }
    }

}

