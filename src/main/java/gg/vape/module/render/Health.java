package gg.vape.module.render;

import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.ModuleDisplayInfo;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.frame.impl.hud.ActiveModuleStackFrame;
import gg.vape.unmap.NumberFormat;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;

public class Health
extends Mod {
    private final NumberFormat healthFormat = new NumberFormat("#.#");


    public Health() {
        super("Health", -21075, Category.RENDER, "Displays your health in the center of your screen.");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ClientSettings.getFrame(ActiveModuleStackFrame.class).addModule(this);
    }

    @Override
    public ModuleDisplayInfo getModuleDisplayInfo() {
        String heartColorCode;
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return null;
        }
        double healthHearts = (double)player.w$src$F$15l9epb() / 2.0;
        if (player.p() > 0.0f) {
            heartColorCode = "§6";
            healthHearts += (double)player.p() / 2.0;
        } else {
            heartColorCode = "§c";
        }
        String heartText = heartColorCode + "❤";
        String healthText = this.healthFormat.format(Math.floor((healthHearts + 0.25) / 0.5) * 0.5).replace(".0", "");
        String displayText = healthText + " " + heartText;
        Color displayColor = new Color(255, 20, 20);
        if (healthHearts >= 7.0) {
            displayColor = new Color(2, 190, 58);
        } else if (healthHearts > 4.0) {
            displayColor = new Color(255, 249, 18);
        }
        return new ModuleDisplayInfo(displayText, displayColor, healthText, null);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ClientSettings.getFrame(ActiveModuleStackFrame.class).removeModule(this);
    }
}

