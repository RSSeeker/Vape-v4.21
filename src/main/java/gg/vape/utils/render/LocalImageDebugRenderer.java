package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;

public class LocalImageDebugRenderer {
    private static String legacyStatus;
    static final String DEBUG_IMAGE_PATH;

    public static String getLegacyStatus() {
        return legacyStatus;
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    public static GlImageTexture loadDebugTexture() {
        GlImageTexture texture = null;
        try {
            texture = new GlImageTexture(new FileInputStream(DEBUG_IMAGE_PATH));
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        return texture;
    }

    public static void renderDebugImage() {
        try {
            GlImageTexture texture = LocalImageDebugRenderer.loadDebugTexture();
            if (texture != null) {
                Vape.debugLog("drawing");
                ImageRenderer.drawTexture(Color.BLACK, 10.0f, 10.0f, texture, 20.0f, 20.0f, true);
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    static {
        LocalImageDebugRenderer.setLegacyStatus(null);
        DEBUG_IMAGE_PATH = "C:\\Users\\Moham\\Desktop\\pngs\\Group 5709.png";
    }

    public static void setLegacyStatus(String status) {
        legacyStatus = status;
    }
}
