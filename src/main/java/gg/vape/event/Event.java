package gg.vape.event;

import gg.vape.event.EventBus;
import gg.vape.event.ICancelableEvent;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EntityRenderer;
import gg.vape.wrapper.impl.FontRenderer;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.WorldClient;

public abstract class Event
implements ICancelableEvent {
    private WorldClient world;
    private boolean canceled;
    private RenderManager renderManager;
    private GuiScreen currentScreen;
    private EntityRenderer entityRenderer;
    private FontRenderer fontRenderer;
    private GameSettings gameSettings;
    private static String obfuscationState;
    private EntityPlayerSP player;

    static {
        if (Event.getObfuscationState() == null) {
            Event.setObfuscationState("Wfjubc");
        }
    }

    @Override
    public void setCancelled(boolean canceled) {
        this.canceled = canceled;
    }

    public static String getObfuscationState() {
        return obfuscationState;
    }

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    public final EntityPlayerSP getThePlayer() {
        if (this.player == null) {
            this.player = Minecraft.thePlayer();
        }
        return this.player;
    }

    public final RenderManager getRenderManager() {
        if (this.renderManager == null) {
            this.renderManager = Minecraft.D();
        }
        return this.renderManager;
    }

    public final GameSettings getGameSettings() {
        if (this.gameSettings == null) {
            this.gameSettings = Minecraft.gameSettings();
        }
        return this.gameSettings;
    }

    public final EntityRenderer getEntityRenderer() {
        if (this.entityRenderer == null) {
            this.entityRenderer = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf();
        }
        return this.entityRenderer;
    }

    public final FontRenderer getFontRenderer() {
        if (this.fontRenderer == null) {
            this.fontRenderer = Minecraft.getFontRenderer();
        }
        return this.fontRenderer;
    }

    @Override
    public boolean fire() {
        EventBus.getInstance().post(this);
        return this.isCanceled();
    }


    public final WorldClient getWorld() {
        if (this.world == null) {
            this.world = Minecraft.theWorld();
        }
        return this.world;
    }

    public final GuiScreen getCurrentScreen() {
        if (this.currentScreen == null) {
            this.currentScreen = Minecraft.currentScreen();
        }
        return this.currentScreen;
    }

    public static void setObfuscationState(String state) {
        obfuscationState = state;
    }
}

