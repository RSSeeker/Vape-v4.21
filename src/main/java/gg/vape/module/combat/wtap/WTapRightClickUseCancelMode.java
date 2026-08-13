package gg.vape.module.combat.wtap;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventClickMouse;
import gg.vape.event.impl.EventPreTick;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.combat.HitSelect;
import gg.vape.rotation.RotationManager;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;

public class WTapRightClickUseCancelMode
extends SubModule<HitSelect> {
    private int cooldownTicks = -1;
    private boolean cancelActive = false;
    private static final long COOLDOWN_TICKS;

    static {
        COOLDOWN_TICKS = -5882419382201614328L;
    }

    public WTapRightClickUseCancelMode(Mod mod, String string) {
        super(mod, string);
    }

    @EventHandler
    public void onClickMouse(EventClickMouse eventClickMouse) {
        if (this.isCancelActive()) {
            eventClickMouse.setCancelled(true);
        }
    }


    @EventHandler
    public void onTick(EventPreTick eventPreTick) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            this.cancelActive = false;
            return;
        }
        if (this.cooldownTicks > 0) {
            --this.cooldownTicks;
            this.cancelActive = false;
            return;
        }
        RayTraceResult rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
        EntityLivingBase target = rayTrace.isNotNull() && rayTrace.getEntity().isInstance(MappedClasses.zm)
                ? new EntityLivingBase(rayTrace.getEntity()) : new EntityLivingBase(null);
        GuiScreen screen = Minecraft.currentScreen();
        if (target.isNull() && screen.isNull()) {
            this.cancelActive = false;
            return;
        }
        if (target.isNotNull() && player.b$src$Z$fqlxe4() && target.V$src$I$fk0dv5() > 12) {
            if (!((HitSelect)this.getParent()).shouldTrigger()) {
                this.cooldownTicks = (int)COOLDOWN_TICKS;
            }
            this.cancelActive = true;
            return;
        }
        this.cancelActive = false;
    }

    public boolean isCancelActive() {
        return this.cancelActive;
    }
}
