package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
public class UseEntityPacketBridge
extends Packet {
    private static final Vec3 EMPTY_HIT_LOCATION = new Vec3(null);

    public boolean isInteract() {
        if (ForgeVersion.MC_26_1.d()) {
            return !this.isAttackPacketVariant() && !this.isInteractAt();
        }
        return this.getAction().equals(CPacketUseEntity.interact());
    }

    public boolean isAttack() {
        if (ForgeVersion.MC_26_1.d()) {
            return this.isAttackPacketVariant();
        }
        return this.getAction().equals(CPacketUseEntity.attack());
    }

    public int getEntityId() {
        return UseEntityPacketBridge.vapeInstance.getMappings().Co.getEntityId(this.I);
    }

    public String getActionName() {
        if (this.isAttack()) {
            return "ATTACK";
        }
        if (this.isInteractAt()) {
            return "INTERACT_AT";
        }
        if (this.isInteract()) {
            return "INTERACT";
        }
        return "UNKNOWN";
    }

    public Vec3 getHitLocation() {
        if (ForgeVersion.MC_26_1.d()) {
            if (this.isAttackPacketVariant()) {
                return EMPTY_HIT_LOCATION;
            }
            Object hitLocation = UseEntityPacketBridge.vapeInstance.getMappings().Co.getHitLocation(this.I);
            return hitLocation == null ? EMPTY_HIT_LOCATION : new Vec3(hitLocation);
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            CPacketUseEntityAction action = new CPacketUseEntityAction(UseEntityPacketBridge.vapeInstance.getMappings().Co.getAction(this.I), null);
            if (action.getType().equals(CPacketUseEntity.interactAt())) {
                CPacketUseEntityActionPacket actionPacket = new CPacketUseEntityActionPacket(action.getObject());
                return actionPacket.getLocation();
            }
            return EMPTY_HIT_LOCATION;
        }
        return new Vec3(UseEntityPacketBridge.vapeInstance.getMappings().Co.getHitLocation(this.I));
    }

    public void setEntityId(int entityId) {
        UseEntityPacketBridge.vapeInstance.getMappings().Co.setEntityId(this.I, entityId);
    }

    public UseEntityPacketBridge(Object handle) {
        super(handle);
    }

    public Entity getEntity(World world) {
        return world.V(this.getEntityId());
    }

    public CPacketUseEntity getAction() {
        if (ForgeVersion.MC_26_1.d()) {
            throw new IllegalStateException("Use helper methods for 26.1 packet actions");
        }
        if (ForgeVersion.MC_1_17.d()) {
            CPacketUseEntityAction action = new CPacketUseEntityAction(UseEntityPacketBridge.vapeInstance.getMappings().Co.getAction(this.I), null);
            return action.getType();
        }
        return new CPacketUseEntity(UseEntityPacketBridge.vapeInstance.getMappings().Co.getAction(this.I), null);
    }

    public static boolean isUseEntityPacket(Packet packet) {
        return packet != null && (packet.isInstance(MappedClasses.Fa) || ForgeVersion.MC_26_1.d() && packet.isInstance(MappedClasses.ZW));
    }

    public boolean isInteractAt() {
        if (ForgeVersion.MC_26_1.d()) {
            if (this.isAttackPacketVariant()) {
                return false;
            }
            Object hitLocation = UseEntityPacketBridge.vapeInstance.getMappings().Co.getHitLocation(this.I);
            return hitLocation != null;
        }
        return this.getAction().equals(CPacketUseEntity.interactAt());
    }

    private static IllegalStateException propagateException(IllegalStateException exception) {
        return exception;
    }

    private boolean isAttackPacketVariant() {
        return ForgeVersion.MC_26_1.d() && this.isInstance(MappedClasses.ZW);
    }
}
