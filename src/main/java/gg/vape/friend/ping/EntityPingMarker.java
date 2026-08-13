package gg.vape.friend.ping;

import gg.vape.friend.OnlineFriend;
import gg.vape.friend.ping.OnlineFriendPingMarker;
import gg.vape.protocol.packet.PingTargetData;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.World;
import org.jetbrains.annotations.Nullable;

public class EntityPingMarker
extends OnlineFriendPingMarker {
    @Nullable
    private Entity entity;
    @Nullable
    private Long entityOwnerUserId;
    private final int entityId;

    public static double[] getEntityPosition(Entity entity) {
        return new double[]{entity.c(), entity.A() + ((double)entity.Y() + 0.15), entity.Z()};
    }

    public EntityPingMarker(OnlineFriend onlineFriend, @Nullable Long entityOwnerUserId, int entityId, double[] position) {
        super(onlineFriend, position);
        this.entityOwnerUserId = entityOwnerUserId;
        this.entityId = entityId;
        this.setWidth(18.0);
        this.setHeight(18.0);
    }


    public EntityPingMarker(OnlineFriend onlineFriend, @Nullable Long entityOwnerUserId, Entity entity) {
        super(onlineFriend, EntityPingMarker.getEntityPosition(entity));
        this.entityOwnerUserId = entityOwnerUserId;
        this.entityId = entity.S();
        this.entity = entity;
        this.setWidth(18.0);
        this.setHeight(18.0);
    }

    @Override
    public void update(World world) {
        Entity resolvedEntity;
        if (this.entity != null && (this.entity.isNull() || this.entity.M$src$Z$ff28xj())) {
            this.entity = null;
        }
        if (this.entity == null && (resolvedEntity = world.V(this.entityId)) != null && resolvedEntity.isNotNull() && !resolvedEntity.M$src$Z$ff28xj()) {
            this.entity = resolvedEntity;
        }
        if (this.entity != null && this.entity.isNotNull()) {
            this.setWorldPosition(EntityPingMarker.getEntityPosition(this.entity));
        }
    }

    @Override
    public PingTargetData toTargetData() {
        return PingTargetData.a(this.entityOwnerUserId, this.entityId, this.getX(), this.getY(), this.getZ());
    }
}

