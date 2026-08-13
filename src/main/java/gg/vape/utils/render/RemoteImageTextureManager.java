package gg.vape.utils.render;

import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RemoteImageTextureCache;
import gg.vape.utils.render.RemoteImageTextureCacheUpdateThread;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteImageTextureManager {
    private ConcurrentHashMap<Integer, RemoteImageTextureCache> cachesBySize = new ConcurrentHashMap();
    private static final String DEFAULT_AVATAR_RESOURCE;
    private static RemoteImageTextureManager instance;

    private static String decodeUtf8(byte[] bytes) {
        int characterCount = 0;
        int byteCount = bytes.length;
        char[] characters = new char[byteCount];
        for (int byteIndex = 0; byteIndex < byteCount; ++byteIndex) {
            char character;
            int currentByte = 0xFF & bytes[byteIndex];
            if (currentByte < 192) {
                characters[characterCount++] = (char)currentByte;
                continue;
            }
            if (currentByte < 224) {
                character = (char)((char)(currentByte & 0x1F) << 6);
                currentByte = bytes[++byteIndex];
                character = (char)(character | (char)(currentByte & 0x3F));
                characters[characterCount++] = character;
                continue;
            }
            if (byteIndex >= byteCount - 2) continue;
            character = (char)((char)(currentByte & 0xF) << 12);
            currentByte = bytes[++byteIndex];
            character = (char)(character | (char)(currentByte & 0x3F) << 6);
            currentByte = bytes[++byteIndex];
            character = (char)(character | (char)(currentByte & 0x3F));
            characters[characterCount++] = character;
        }
        return new String(characters, 0, characterCount);
    }

    static ConcurrentHashMap<Integer, RemoteImageTextureCache> getCaches(RemoteImageTextureManager manager) {
        return manager.cachesBySize;
    }

    public GlImageTexture getTexture(String username, int size) {
        if (this.cachesBySize.containsKey(size)) {
            GlImageTexture texture = this.cachesBySize.get(size).getTexture(username);
            if (texture == null) {
                return ImageRenderer.loadResource(DEFAULT_AVATAR_RESOURCE, false, false);
            }
            return texture;
        }
        return null;
    }

    public static RemoteImageTextureManager getInstance() {
        return instance;
    }


    public RemoteImageTextureManager() {
        new RemoteImageTextureCacheUpdateThread(this).start();
        this.cachesBySize.put(32, new RemoteImageTextureCache(32));
    }

    static {
        try {
            DEFAULT_AVATAR_RESOURCE = "default_user";
            instance = new RemoteImageTextureManager();
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}
