package gg.vape.utils.render;

import gg.vape.utils.render.BufferedOpenGlBackend;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.LegacyOpenGlBackend;
import gg.vape.utils.render.OpenGlBackend;

public class OpenGlBackendHolder {
    public static OpenGlBackend backend = GuiRenderPrimitives.d() ? new BufferedOpenGlBackend() : new LegacyOpenGlBackend();

}

