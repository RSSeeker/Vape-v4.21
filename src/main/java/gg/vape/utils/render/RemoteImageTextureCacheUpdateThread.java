package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.utils.SleepUtil;
import gg.vape.utils.render.RemoteImageTextureCache;
import gg.vape.utils.render.RemoteImageTextureManager;

class RemoteImageTextureCacheUpdateThread
extends Thread {
    final RemoteImageTextureManager manager;

    @Override
    public void run() {
        while (!Vape.INSTANCE.isEnabled()) {
            SleepUtil.sleep(50L);
            for (Integer imageSize : RemoteImageTextureManager.getCaches(this.manager).keySet()) {
                ((RemoteImageTextureCache)RemoteImageTextureManager.getCaches(this.manager).get(imageSize)).processPendingDownloads();
            }
        }
    }

    RemoteImageTextureCacheUpdateThread(RemoteImageTextureManager manager) {
        this.manager = manager;
    }
}
