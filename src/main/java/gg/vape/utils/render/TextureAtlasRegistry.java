package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.utils.render.TextureAtlas;
import java.util.HashMap;
import java.util.Map;

public class TextureAtlasRegistry {
    private static final String MISSING_ATLAS_MESSAGE = "Unable to retrieve Texture Atlas id: ";
    private final Map<String, TextureAtlas> atlases = new HashMap<String, TextureAtlas>();
    private TextureAtlas activeAtlas;
    private static TextureAtlasRegistry instance;

    private TextureAtlasRegistry() {
    }

    public TextureAtlas getActiveAtlas() {
        return this.activeAtlas;
    }


    public void remove(String atlasId) {
        if (!this.atlases.containsKey(atlasId)) {
            return;
        }
        this.atlases.get(atlasId).getTexture().delete();
        this.atlases.remove(atlasId);
    }

    public static TextureAtlasRegistry getInstance() {
        if (instance == null) {
            instance = new TextureAtlasRegistry();
        }
        return instance;
    }

    public TextureAtlas getOrCreate(String atlasId) {
        if (!this.atlases.containsKey(atlasId)) {
            this.atlases.put(atlasId, new TextureAtlas());
        }
        return this.atlases.get(atlasId);
    }

    public void setActiveAtlas(TextureAtlas activeAtlas) {
        this.activeAtlas = activeAtlas;
    }

    public TextureAtlas get(String atlasId) {
        if (!this.atlases.containsKey(atlasId)) {
            Vape.debugLog(MISSING_ATLAS_MESSAGE + atlasId);
            return null;
        }
        return this.atlases.get(atlasId);
    }
}

