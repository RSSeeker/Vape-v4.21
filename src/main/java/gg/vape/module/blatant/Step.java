package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventStepHeightOverride;
import gg.vape.event.impl.EventStepSnapshot;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.threads.ResetTimerThread;
import gg.vape.wrapper.impl.CPacketPlayerPosition;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;

public class Step
extends Mod {
    private static final String NAME = "Step";


    @EventHandler
    public void onStepHeightOverride(EventStepHeightOverride eventStepHeightOverride) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (eventStepHeightOverride.getRealHeight() > 0.5 && eventStepHeightOverride.getStepHeight() > 0.0 && !localPlayer.movementInput().G() && localPlayer.u$src$Z$g120nz()) {
            Vape.INSTANCE.getModManager().getMod(Speed.class).setStage(-4);
            if (eventStepHeightOverride.getRealHeight() >= 0.87) {
                double stepHeight = eventStepHeightOverride.getRealHeight();
                double firstOffset = stepHeight * 0.42;
                double secondOffset = stepHeight * 0.75;
                localPlayer.sendQueue().addToSendQueue(CPacketPlayerPosition.newInstance(localPlayer.z(), localPlayer.N() + firstOffset, localPlayer.h(), localPlayer.b$src$Z$fqlxe4()));
                localPlayer.sendQueue().addToSendQueue(CPacketPlayerPosition.newInstance(localPlayer.z(), localPlayer.N() + secondOffset, localPlayer.h(), localPlayer.b$src$Z$fqlxe4()));
            }
            Minecraft.getTimer().setTimerSpeed(0.45f);
            ResetTimerThread resetTimerThread = new ResetTimerThread(this);
            resetTimerThread.start();
        }
    }

    public Step() {
        super(NAME, new Color(42, 175, 224).getRGB(), Category.OTHER);
    }

    @Override
    public boolean isBlatantMod() {
        return true;
    }

    @EventHandler
    public void onStepSnapshot(EventStepSnapshot eventStepSnapshot) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (!localPlayer.movementInput().G() && localPlayer.u$src$Z$g120nz()) {
            eventStepSnapshot.setStepHeight(1.0);
        }
    }
}

