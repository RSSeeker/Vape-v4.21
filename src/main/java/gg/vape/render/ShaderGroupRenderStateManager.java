package gg.vape.render;

import gg.vape.Vape;
import gg.vape.runtime.NativeBridge;
import gg.vape.wrapper.impl.EntityRenderer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.ResourceLocation;
import gg.vape.wrapper.impl.ShaderGroup;

public class ShaderGroupRenderStateManager {
    private boolean active;
    private ShaderGroup previousShaderGroup;
    private static final ShaderGroupRenderStateManager INSTANCE;
    public ShaderGroup activeShaderGroup;
    public boolean shadersPreviouslyEnabled;
    private static final int CONTROL_FLOW_SEED;

    public void enable() {
        if (this.active) {
            return;
        }
        if (ForgeVersion.MC_1_16_5.d() && ForgeVersion.MC_1_16_5_ACTUAL.B() && Vape.INSTANCE.isForgeAbsent() && !Vape.INSTANCE.isVanillaMinecraftPresent()) {
            return;
        }
        GameSettings gameSettings = Minecraft.gameSettings();
        if (gameSettings.d() > 0 || !gameSettings.Y$src$Z$1rxemad()) {
            return;
        }
        boolean shadersEnabled = gameSettings.M();
        if (shadersEnabled && NativeBridge.isForgeAbsent() && !Vape.INSTANCE.isVanillaMinecraftPresent()) {
            return;
        }
        EntityRenderer entityRenderer = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf();
        if (entityRenderer.isNull()) {
            return;
        }
        this.previousShaderGroup = entityRenderer.L();
        this.shadersPreviouslyEnabled = shadersEnabled;
        int displayWidth = Minecraft.J();
        int displayHeight = Minecraft.h();
        if (this.shadersPreviouslyEnabled) {
            gameSettings.m(false);
            Minecraft.getFrameBuffer().createBindFramebuffer(displayWidth, displayHeight);
            entityRenderer.updateShaderGroupSize(displayWidth, displayHeight);
            Minecraft.O().loadRenderers();
        }
        this.createShaderGroup(displayWidth, displayHeight);
        this.active = true;
    }


    public boolean isActive() {
        return this.active;
    }

    public void disable() {
        this.disableInternal();
    }

    private void disableInternal() {
        if (!this.active) {
            return;
        }
        GameSettings gameSettings = Minecraft.gameSettings();
        if (gameSettings.d() > 0 || !gameSettings.Y$src$Z$1rxemad()) {
            return;
        }
        EntityRenderer entityRenderer = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf();
        if (entityRenderer.isNull()) {
            return;
        }
        if (this.shadersPreviouslyEnabled) {
            int displayWidth = Minecraft.J();
            int displayHeight = Minecraft.h();
            Minecraft.getFrameBuffer().createBindFramebuffer(displayWidth, displayHeight);
            entityRenderer.updateShaderGroupSize(displayWidth, displayHeight);
            Minecraft.O().loadRenderers();
            gameSettings.m(true);
        }
        entityRenderer.setUseShader(false);
        entityRenderer.J(new ShaderGroup(null));
        this.activeShaderGroup = null;
        this.previousShaderGroup = null;
        this.active = false;
    }

    public static ShaderGroupRenderStateManager getInstance() {
        return INSTANCE;
    }

    static {
        long seed = 6018231015514832914L;
        CONTROL_FLOW_SEED = (int)seed;
        INSTANCE = new ShaderGroupRenderStateManager();
    }

    private void createShaderGroup(int displayWidth, int displayHeight) {
        this.activeShaderGroup = ShaderGroup.create(Minecraft.getTextureManager(), Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().K(), Minecraft.getFrameBuffer(), new ResourceLocation(EntityRenderer.getShaderResourceLocations()[18]));
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().J(this.activeShaderGroup);
        this.activeShaderGroup.resize(displayWidth, displayHeight);
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().setUseShader(true);
    }
}

