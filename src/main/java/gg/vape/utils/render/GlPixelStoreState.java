package gg.vape.utils.render;

import org.lwjgl.opengl.GL11;

/** Captures, resets, and restores OpenGL pixel-store state. */
public final class GlPixelStoreState {
    private static boolean packSwapBytes;
    private static boolean packLsbFirst;
    private static int packRowLength;
    private static int packImageHeight;
    private static int packSkipRows;
    private static int packSkipPixels;
    private static int packSkipImages;
    private static int packAlignment = 4;
    private static boolean unpackSwapBytes;
    private static boolean unpackLsbFirst;
    private static int unpackRowLength;
    private static int unpackImageHeight;
    private static int unpackSkipRows;
    private static int unpackSkipPixels;
    private static int unpackSkipImages;
    private static int unpackAlignment = 4;

    private GlPixelStoreState() {
    }

    public static void reset() {
        GL11.glPixelStorei(3328, 0);
        GL11.glPixelStorei(3329, 0);
        GL11.glPixelStorei(3330, 0);
        GL11.glPixelStorei(32876, 0);
        GL11.glPixelStorei(3331, 0);
        GL11.glPixelStorei(3332, 0);
        GL11.glPixelStorei(32875, 0);
        GL11.glPixelStorei(3333, 4);
        GL11.glPixelStorei(3312, 0);
        GL11.glPixelStorei(3313, 0);
        GL11.glPixelStorei(3314, 0);
        GL11.glPixelStorei(32878, 0);
        GL11.glPixelStorei(3315, 0);
        GL11.glPixelStorei(3316, 0);
        GL11.glPixelStorei(32877, 0);
        GL11.glPixelStorei(3317, 4);
    }

    public static void restore() {
        GL11.glPixelStorei(3328, packSwapBytes ? 1 : 0);
        GL11.glPixelStorei(3329, packLsbFirst ? 1 : 0);
        GL11.glPixelStorei(3330, packRowLength);
        GL11.glPixelStorei(32876, packImageHeight);
        GL11.glPixelStorei(3331, packSkipRows);
        GL11.glPixelStorei(3332, packSkipPixels);
        GL11.glPixelStorei(32875, packSkipImages);
        GL11.glPixelStorei(3333, packAlignment);
        GL11.glPixelStorei(3312, unpackSwapBytes ? 1 : 0);
        GL11.glPixelStorei(3313, unpackLsbFirst ? 1 : 0);
        GL11.glPixelStorei(3314, unpackRowLength);
        GL11.glPixelStorei(32878, unpackImageHeight);
        GL11.glPixelStorei(3315, unpackSkipRows);
        GL11.glPixelStorei(3316, unpackSkipPixels);
        GL11.glPixelStorei(32877, unpackSkipImages);
        GL11.glPixelStorei(3317, unpackAlignment);
    }

    public static void capture() {
        packSwapBytes = GL11.glGetBoolean(3328);
        packLsbFirst = GL11.glGetBoolean(3329);
        packRowLength = GL11.glGetInteger(3330);
        packImageHeight = GL11.glGetInteger(32876);
        packSkipRows = GL11.glGetInteger(3331);
        packSkipPixels = GL11.glGetInteger(3332);
        packSkipImages = GL11.glGetInteger(32875);
        packAlignment = GL11.glGetInteger(3333);
        unpackSwapBytes = GL11.glGetBoolean(3312);
        unpackLsbFirst = GL11.glGetBoolean(3313);
        unpackRowLength = GL11.glGetInteger(3314);
        unpackImageHeight = GL11.glGetInteger(32878);
        unpackSkipRows = GL11.glGetInteger(3315);
        unpackSkipPixels = GL11.glGetInteger(3316);
        unpackSkipImages = GL11.glGetInteger(32877);
        unpackAlignment = GL11.glGetInteger(3317);
    }
}
