package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Vec3;

public class MSPacketEntityVelocity
extends Mapping {
    private MappingField motionZField;
    private MappingMethod packetConstructor;
    private final MappingField entityIdField;
    private MappingField motionYField;
    private MappingField motionXField;
    private MappingField movementField;
    private static int[] packetMappingControlFlowState;

    public MSPacketEntityVelocity() {
        this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
    }

    private MSPacketEntityVelocity(int[] controlFlowState) {
        super(MappedClasses.YX);
        if (Vape.INSTANCE.isVanillaMinecraftPresent()
                && ForgeVersion.MC_1_7_10.Y()
                && ForgeVersion.MC_1_21_10.v()) {
            this.entityIdField = this.J("entityID", true, Integer.TYPE);
            this.motionXField = this.J("motionX", true, Integer.TYPE);
            this.motionYField = this.J("motionY", true, Integer.TYPE);
            this.motionZField = this.J("motionZ", true, Integer.TYPE);
        } else {
            this.entityIdField = this.J("field_149417_a", Wrapper.isNativeAvailable, Integer.TYPE);
            if (ForgeVersion.MC_1_21_10.d()) {
                this.movementField = this.J("movement", true, MappedClasses.qP);
                this.packetConstructor = this.registerConstructor(new Class[]{Integer.TYPE, MappedClasses.qP});
            } else {
                this.motionXField = this.J("field_149415_b", Wrapper.isNativeAvailable, Integer.TYPE);
                this.motionYField = this.J("field_149416_c", Wrapper.isNativeAvailable, Integer.TYPE);
                this.motionZField = this.J("field_149414_d", Wrapper.isNativeAvailable, Integer.TYPE);
            }
        }
    }

    public int getMotionX(Object packet) {
        return this.motionXField.getInt(packet);
    }

    public int getMotionY(Object packet) {
        return this.motionYField.getInt(packet);
    }

    public void setMotionX(Object packet, int motionX) {
        this.motionXField.setInt(packet, motionX);
    }

    public Object getMovement(Object packet) {
        return this.movementField.getObject(packet);
    }

    public void setMovement(Object packet, Object movement) {
        this.movementField.setObject(packet, movement);
    }

    public Object createPacket(int entityId, double motionX, double motionY, double motionZ) {
        Vec3 movement = Vec3.create(motionX, motionY, motionZ);
        return this.packetConstructor.newInstance(entityId, movement.getObject());
    }

    public int getMotionZ(Object packet) {
        return this.motionZField.getInt(packet);
    }

    public void setMotionZ(Object packet, int motionZ) {
        this.motionZField.setInt(packet, motionZ);
    }

    public void setMotionY(Object packet, int motionY) {
        this.motionYField.setInt(packet, motionY);
    }

    public int getEntityId(Object packet) {
        return this.entityIdField.getInt(packet);
    }

    public static int[] getPacketMappingControlFlowState() {
        return packetMappingControlFlowState;
    }

    public static void setPacketMappingControlFlowState(int[] state) {
        packetMappingControlFlowState = state;
    }

    static {
        MSPacketEntityVelocity.setPacketMappingControlFlowState(new int[3]);
    }
}

