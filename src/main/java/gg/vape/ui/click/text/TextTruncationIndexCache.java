package gg.vape.ui.click.text;

import gg.vape.Vape;
import gg.vape.ui.click.text.TextTruncationIndexLruCache;
import gg.vape.ui.click.text.TruncatedTextSpec;
import gg.vape.ui.font.SmoothFontRenderer;
import java.util.LinkedHashMap;

public class TextTruncationIndexCache {
    private final LinkedHashMap<Integer, Integer> indicesBySpecHash = new TextTruncationIndexLruCache(this, 16, 0.75f, true);
    private static final int LEGACY_CACHE_MARKER;
    public static TextTruncationIndexCache INSTANCE;


    public int getCacheSize() {
        return this.indicesBySpecHash.size();
    }

    static {
        long l2 = -8931204442707983872L;
        LEGACY_CACHE_MARKER = (int)l2;
        INSTANCE = new TextTruncationIndexCache();
    }

    public int getTruncationIndex(TruncatedTextSpec textSpec) {
        Integer cachedIndex = this.indicesBySpecHash.get(textSpec.hashCode());
        if (cachedIndex != null) {
            return cachedIndex;
        }
        SmoothFontRenderer fontRenderer = textSpec.isBold() ? Vape.INSTANCE.getFontManager().W(textSpec.getFontScale(), false) : Vape.INSTANCE.getFontManager().Y(textSpec.getFontScale());
        int truncationIndex = -2;
        if (fontRenderer.N(textSpec.getText()) <= textSpec.getMaxWidth()) {
            truncationIndex = textSpec.getText().length() - 1;
        } else {
            int candidateIndex = (int)Math.ceil(textSpec.getText().length() / 2) - 1;
            boolean exceededMaxWidth = false;
            while (candidateIndex >= 0 && candidateIndex < textSpec.getText().length()) {
                double candidateWidth = fontRenderer.N(textSpec.getText().substring(0, candidateIndex));
                if (candidateWidth > textSpec.getMaxWidth()) {
                    exceededMaxWidth = true;
                    --candidateIndex;
                    continue;
                }
                if (exceededMaxWidth || candidateIndex == textSpec.getText().length() - 1) break;
                ++candidateIndex;
            }
            truncationIndex = candidateIndex;
        }
        if (truncationIndex == -1) {
            --truncationIndex;
        }
        this.indicesBySpecHash.put(textSpec.hashCode(), truncationIndex);
        return truncationIndex;
    }
}

