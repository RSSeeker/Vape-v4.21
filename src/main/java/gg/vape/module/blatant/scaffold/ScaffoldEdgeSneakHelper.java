package gg.vape.module.blatant.scaffold;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPostTick;
import gg.vape.event.impl.EventPreEntityUpdate;
import gg.vape.input.KeyBindingHelper;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.blatant.Scaffold;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.utils.TimerUtil;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;

public class ScaffoldEdgeSneakHelper
extends SubModule<Scaffold> {
    private final TimerUtil sneakTimer;
    private long sneakDelayMs;
    private final Scaffold scaffold;
    private final RandomValue sneakDelay = RandomValue.createWithDescription(this, "Sneak delay", "#", "", 0.0, 100.0, 200.0, 500.0, 1.0, "Delay until standing after sneaking");
    private boolean sneakKeyWasDown;

    @Override
    public void onEnable() {
        this.sneakTimer.reset();
        this.sneakDelayMs = (long)this.sneakDelay.getRandomValue();
    }

    @EventHandler
    public void onPreEntityUpdate(EventPreEntityUpdate eventPreEntityUpdate) {
        if (Minecraft.currentScreen().isNotNull()) {
            return;
        }
        if (!((Scaffold)this.getParent()).canActivate()) {
            return;
        }
        if (!eventPreEntityUpdate.getEntity().equals(Minecraft.thePlayer())) {
            return;
        }
        String threadName = "Client thread";
        if (ForgeVersion.MC_1_16_5.d()) {
            threadName = "Render thread";
        }
        if (!Thread.currentThread().getName().equals(threadName)) {
            return;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        GameSettings gameSettings = Minecraft.gameSettings();
        KeyBinding sneakKey = gameSettings.d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0();
        if (player.S$src$Z$151gttj()) {
            SharedModuleControlClaims.rotation.release(this.scaffold);
            return;
        }
        if (this.scaffold.isPitchCheckEnabled() && (double)player.V() < this.scaffold.getPitchThreshold()) {
            SharedModuleControlClaims.rotation.release(this.scaffold);
            return;
        }
        this.sneakKeyWasDown = sneakKey.isKeyDown();
        boolean shouldSneak = false;
        float forwardInput = 0.0f;
        KeyBinding forwardKey = gameSettings.s();
        KeyBinding backKey = gameSettings.Y();
        if (ClientSettings.isPhysicalKeyDown(forwardKey)) {
            forwardInput += -1.0f;
        }
        if (ClientSettings.isPhysicalKeyDown(backKey)) {
            forwardInput += 1.0f;
        }
        boolean notMovingForward = forwardInput <= 0.0f;
        if (notMovingForward && player.b$src$Z$fqlxe4()) {
            AxisAlignedBB boundingBox;
            if (ForgeVersion.MC_1_8_9.d()) {
                boundingBox = player.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
            } else {
                AxisAlignedBB currentBox = player.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
                boundingBox = currentBox.copy();
            }
            double motionX = player.t();
            double offsetY = ForgeVersion.MC_1_20_6.d() ? 1.0 : -1.0;
            double motionZ = player.T();
            AxisAlignedBB checkBox = boundingBox.expand(-0.2, 0.0, -0.2).k(motionX, offsetY, motionZ);
            int collisionCount = Minecraft.theWorld().i(player, checkBox).size();
            if (collisionCount == 0) {
                shouldSneak = true;
                SharedModuleControlClaims.rotation.acquire(this.scaffold);
            }
        }
        boolean skipTimerReset = false;
        if (SharedModuleControlClaims.rotation.isOwnedBy(this.scaffold) && (forwardInput > 0.0f || !shouldSneak && this.sneakTimer.hasTimeElapsed(500L))) {
            SharedModuleControlClaims.rotation.release(this.scaffold);
        }
        if (!shouldSneak && !this.sneakTimer.hasTimeElapsed(this.sneakDelayMs) && this.sneakDelayMs > 30L) {
            shouldSneak = true;
            skipTimerReset = true;
        }
        if (shouldSneak && player.b$src$Z$fqlxe4()) {
            if (!player.P()) {
                this.sneakDelayMs = (long)this.sneakDelay.getRandomValue();
            }
            KeyBindingHelper.setPressedAndTick(sneakKey, true);
            if (!skipTimerReset) {
                this.sneakTimer.reset();
            }
        } else if (!this.sneakKeyWasDown) {
            KeyBindingHelper.setPressedAndTick(sneakKey, false);
        }
    }

    @EventHandler
    public void onPostTick(EventPostTick eventPostTick) {
        if (Minecraft.currentScreen().isNotNull()) {
            return;
        }
        if (!((Scaffold)this.getParent()).canActivate()) {
            return;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.S$src$Z$151gttj()) {
            return;
        }
        if (this.scaffold.isPitchCheckEnabled() && (double)player.V() < this.scaffold.getPitchThreshold()) {
            return;
        }
        GameSettings gameSettings = Minecraft.gameSettings();
        KeyBinding sneakKey = gameSettings.d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0();
        KeyBindingHelper.setPressedAndTick(sneakKey, this.sneakKeyWasDown);
    }

    public ScaffoldEdgeSneakHelper(Mod parent, String name) {
        super(parent, name);
        this.scaffold = (Scaffold)this.getParent();
        this.sneakTimer = new TimerUtil();
        this.addValue(this.sneakDelay);
    }
}
