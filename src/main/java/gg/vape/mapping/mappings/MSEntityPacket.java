package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSEntityPacket
extends Mapping {
    private MappingMethod getDeltaZMethod;
    private MappingField deltaZField;
    private MappingMethod getDeltaXMethod;
    private final MappingMethod getEntityMethod;
    private MappingField deltaXField;
    private MappingMethod getDeltaYMethod;
    private MappingField deltaYField;

    public MSEntityPacket() {
        this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
    }

    private MSEntityPacket(int[] controlFlowState) {
        super(MappedClasses.qz);
        if (Vape.INSTANCE.isVanillaMinecraftPresent() && ForgeVersion.MC_1_7_10.Y()) {
            this.getEntityMethod = this.Y("getEntity", true, MappedClasses.zc, new Class[]{MappedClasses.YU});
        } else {
            this.getEntityMethod = this.Y("func_149065_a", Wrapper.isNativeAvailable, MappedClasses.zc, new Class[]{MappedClasses.YU});
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                this.deltaXField = this.J("posX", true, Short.TYPE);
                this.deltaYField = this.J("posY", true, Short.TYPE);
                this.deltaZField = this.J("posZ", true, Short.TYPE);
            } else {
                this.getDeltaXMethod = this.Y("getX", true, Integer.TYPE, new Class[]{});
                this.getDeltaYMethod = this.Y("getY", true, Integer.TYPE, new Class[]{});
                this.getDeltaZMethod = this.Y("getZ", true, Integer.TYPE, new Class[]{});
            }
        } else {
            this.getDeltaXMethod = this.Y("func_149062_c", Wrapper.isNativeAvailable, Byte.TYPE, new Class[]{});
            this.getDeltaYMethod = this.Y("func_149061_d", Wrapper.isNativeAvailable, Byte.TYPE, new Class[]{});
            this.getDeltaZMethod = this.Y("func_149064_e", Wrapper.isNativeAvailable, Byte.TYPE, new Class[]{});
        }
    }

    public int getDeltaX(Object packet) {
        return ForgeVersion.MC_1_16_5.d() ? this.deltaXField.getShort(packet) : this.getDeltaXMethod.invokeByte(packet, new Object[0]);
    }

    public int getDeltaY(Object packet) {
        return ForgeVersion.MC_1_16_5.d() ? this.deltaYField.getShort(packet) : this.getDeltaYMethod.invokeByte(packet, new Object[0]);
    }

    public int getDeltaZ(Object packet) {
        return ForgeVersion.MC_1_16_5.d() ? this.deltaZField.getShort(packet) : this.getDeltaZMethod.invokeByte(packet, new Object[0]);
    }

    public Object getEntity(Object packet, Object world) {
        return this.getEntityMethod.invokeObject(packet, world);
    }
}

