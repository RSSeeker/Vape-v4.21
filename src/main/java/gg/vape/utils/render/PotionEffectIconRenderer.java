package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.PotionEffectIcon;
import gg.vape.utils.render.PotionEffectIconKey;
import gg.vape.utils.render.PotionEffectIconRenderBackend;
import gg.vape.utils.render.PotionEffectIconTexture;
import gg.vape.wrapper.impl.PotionEffect;
import java.util.HashMap;

public class PotionEffectIconRenderer {
    private static String legacyMarker;
    static HashMap<PotionEffectIconKey, PotionEffectIconRenderBackend> cache;

    public static void render(PotionEffect effect, float x, float y, int width, int height, float opacity, boolean worldSpace) {
        try {
            PotionEffectIconKey cacheKey = new PotionEffectIconKey(effect.C());
            PotionEffectIconRenderer.ensureCached(effect.C());
            PotionEffectIconRenderBackend cachedRenderer = cache.get(cacheKey);
            if (cachedRenderer != null) {
                cachedRenderer.renderQueued(x, y, width, height, opacity, worldSpace);
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }


    public static void ensureCached(int effectId) {
        PotionEffectIconKey cacheKey = new PotionEffectIconKey(effectId);
        if (!cache.containsKey(cacheKey)) {
            PotionEffectIconRenderer.createRenderer(PotionEffect.o(effectId, 100, 0), cacheKey);
        }
    }

    public static void setLegacyMarker(String legacyMarker) {
        PotionEffectIconRenderer.legacyMarker = legacyMarker;
    }

    public static String getLegacyMarker() {
        return legacyMarker;
    }

    public static void clear() {
        for (PotionEffectIconRenderBackend renderer : cache.values()) {
            renderer.dispose();
        }
        cache.clear();
    }

    public static void render(PotionEffect effect, float x, float y, int width, int height, float opacity) {
        PotionEffectIconRenderer.render(effect, x, y, width, height, opacity, false);
    }

    private static void createRenderer(PotionEffect effect, PotionEffectIconKey cacheKey) {
        PotionEffectIconRenderBackend renderer = GuiRenderPrimitives.d() ? new PotionEffectIconTexture() : new PotionEffectIcon();
        // Always cache the renderer even when capture fails (throws or early-returns
        // with a null framebuffer). Otherwise ensureCached() would re-run the expensive
        // offscreen capture EVERY frame and log an exception every frame, which is the
        // per-frame lag. renderQueued() null-guards the framebuffer so a failed capture
        // simply draws nothing instead of crashing/looping.
        try {
            renderer.capture(effect);
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
        cache.put(cacheKey, renderer);
    }

    static {
        cache = new HashMap();
        PotionEffectIconRenderer.setLegacyMarker(null);
    }
}

