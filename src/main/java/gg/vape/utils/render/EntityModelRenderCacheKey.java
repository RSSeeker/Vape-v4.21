package gg.vape.utils.render;

import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import java.util.Objects;

public class EntityModelRenderCacheKey {
    private final int cacheId;
    boolean legacyFlag = false;

    public int getCacheId() {
        return this.cacheId;
    }

    public boolean equals(Object object) {
        if (object instanceof EntityModelRenderCacheKey) {
            EntityModelRenderCacheKey other = (EntityModelRenderCacheKey)object;
            return other.getCacheId() == this.getCacheId();
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.getCacheId());
    }

    public EntityModelRenderCacheKey(EntityLivingBase entity) {
        if (entity == null) {
            this.cacheId = 0;
            return;
        }
        if (entity.isInstance(MappedClasses.lG)) {
            EntityOtherPlayerMP otherPlayer = new EntityOtherPlayerMP(entity);
            this.cacheId = otherPlayer.c$src$Lgg_vape_wrapper_impl_GameProfile_$ir8937().getObject().hashCode();
        } else {
            this.cacheId = entity.getObject().getClass().hashCode();
        }
    }


    public EntityModelRenderCacheKey(String identifier) {
        this.cacheId = identifier != null ? identifier.hashCode() : 0;
    }
}

