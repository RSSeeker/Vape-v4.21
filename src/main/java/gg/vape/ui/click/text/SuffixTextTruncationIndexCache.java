package gg.vape.ui.click.text;

import gg.vape.Vape;
import gg.vape.ui.click.text.SuffixTextTruncationIndexLruCache;
import gg.vape.ui.click.text.TruncatedTextSpec;
import gg.vape.ui.font.SmoothFontRenderer;
import java.util.LinkedHashMap;

public class SuffixTextTruncationIndexCache {
    public static SuffixTextTruncationIndexCache INSTANCE;
    private static final int LEGACY_CACHE_MARKER;
    private final LinkedHashMap<Integer, Integer> indicesBySpecHash = new SuffixTextTruncationIndexLruCache(this, 16, 0.75f, true);


    public int getTruncationIndex(TruncatedTextSpec textSpec) {
        Integer cachedIndex = this.indicesBySpecHash.get(textSpec.hashCode());
        if (cachedIndex != null) {
            return cachedIndex;
        }
        SmoothFontRenderer fontRenderer = textSpec.isBold() ? Vape.INSTANCE.getFontManager().W(textSpec.getFontScale(), false) : Vape.INSTANCE.getFontManager().Y(textSpec.getFontScale());
        int truncationIndex = -1;
        if (fontRenderer.N(textSpec.getText()) <= textSpec.getMaxWidth()) {
            truncationIndex = textSpec.getText().length() - 1;
        } else {
            int candidateIndex = (int)Math.ceil(textSpec.getText().length() / 2) - 1;
            boolean exceededMaxWidth = false;
            while (candidateIndex >= 0 && candidateIndex < textSpec.getText().length()) {
                double candidateWidth = fontRenderer.N(textSpec.getText().substring(0, candidateIndex) + textSpec.getTruncationSuffix());
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
        if (truncationIndex == -1 && fontRenderer.N(textSpec.getTruncationSuffix()) > textSpec.getMaxWidth()) {
            --truncationIndex;
        }
        this.indicesBySpecHash.put(textSpec.hashCode(), truncationIndex);
        return truncationIndex;
    }

    public int getCacheSize() {
        return this.indicesBySpecHash.size();
    }

    static {
        long l2 = -5362564765556145664L;
        LEGACY_CACHE_MARKER = (int)l2;
        INSTANCE = new SuffixTextTruncationIndexCache();
    }
}

