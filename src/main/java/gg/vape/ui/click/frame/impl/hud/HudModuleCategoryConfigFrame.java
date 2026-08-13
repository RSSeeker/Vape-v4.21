package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.render.hud.HudModuleCategoryEntry;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;

public class HudModuleCategoryConfigFrame
extends HudModuleConfigFrameBase {
    private static final String FRAME_NAME = "CooldownFrame";

    @Override
    public double L() {
        return 55.0;
    }

    @Override
    public void renderHudContent() {
    }

    public HudModuleCategoryConfigFrame() {
        super(HudModuleCategoryEntry.class);
    }


    @Override
    public String getName() {
        return FRAME_NAME;
    }

    @Override
    public double A() {
        return 55.0;
    }
}

