package gg.vape.wrapper.impl;

public class SPacketSoundEffect
extends Packet {

    public SPacketSoundEffect(Object handle) {
        super(handle);
    }

    public String getParticleName() {
        if (ForgeVersion.MC_1_8_9.d()) {
            return new EnumParticleTypes(SPacketSoundEffect.vapeInstance.getMappings().Cs.getParticleType(this.I)).K();
        }
        return SPacketSoundEffect.vapeInstance.getMappings().Cs.getLegacyParticleName(this.I);
    }

    public double getZ() {
        return SPacketSoundEffect.vapeInstance.getMappings().Cs.getZ(this.I);
    }

    public double getX() {
        return SPacketSoundEffect.vapeInstance.getMappings().Cs.getX(this.I);
    }

    public double getY() {
        return SPacketSoundEffect.vapeInstance.getMappings().Cs.getY(this.I);
    }
}

