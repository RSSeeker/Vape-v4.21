package gg.vape.module.render;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPostRenderTick;
import gg.vape.event.impl.EventPotionEffectCheck;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.value.BooleanValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Potion;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionEntry;
import gg.vape.wrapper.impl.PotionRegistry;

public class AntiDebuff
extends Mod {
    private final BooleanValue removeEffects;
    private boolean duringRenderTick;
    private final BooleanValue removeNausea = BooleanValue.create(this, "Remove Nausea", true);
    private final BooleanValue removeBlindness = BooleanValue.create(this, "Remove Blindness", true);
    private final BooleanValue removeSlowness = BooleanValue.create(this, "Remove Slowness", true);

    @EventHandler
    public void onPostRenderTick(EventPostRenderTick event) {
        this.duringRenderTick = false;
    }

    @EventHandler
    public void onPreRenderTick(EventPreRenderTick event) {
        this.duringRenderTick = true;
    }

    public AntiDebuff() {
        super("AntiDebuff", -256, Category.RENDER, "Removes negative visual potion effects");
        this.removeEffects = BooleanValue.create(this, "Remove Effects", false, "Removes non-visual effects\nCan be detected by anti-cheat");
        this.setDefaultVisibility(false);
        this.addValue(this.removeNausea, this.removeBlindness, this.removeSlowness, this.removeEffects);
    }


    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        EntityPlayerSP player = event.getThePlayer();
        if (this.removeNausea.getEffectiveValue().booleanValue()) {
            player.q(PotionRegistry.X.getResolvedId());
        }
        if (this.removeBlindness.getEffectiveValue().booleanValue() && this.removeEffects.getEffectiveValue().booleanValue()) {
            player.q(PotionRegistry.K.getResolvedId());
        }
        if (this.removeSlowness.getEffectiveValue().booleanValue() && this.removeEffects.getEffectiveValue().booleanValue()) {
            PotionEntry slowness = PotionRegistry.o;
            if (ForgeVersion.MC_1_16_5.v()) {
                PotionEffect slownessEffect = player.b(slowness);
                if (slownessEffect.isNotNull()) {
                    slowness.t(player, player.z$src$Ljava_lang_Object_$1k68ls2(), slownessEffect.L());
                }
            } else {
                slowness.t(player, player.z$src$Ljava_lang_Object_$1k68ls2(), 0);
            }
            player.q(slowness.getResolvedId());
        }
    }

    @EventHandler
    public void onPotionEffectCheck(EventPotionEffectCheck event) {
        if (!this.duringRenderTick) {
            return;
        }
        if (!event.getEntity().equals(event.getThePlayer())) {
            return;
        }
        Potion potion = event.getPotion();
        boolean suppressBlindness = this.removeBlindness.getEffectiveValue().booleanValue() && PotionRegistry.K.matchesLegacyPotion(potion);
        if (suppressBlindness) {
            event.setActive(false);
            event.setCancelled(true);
        }
    }
}

