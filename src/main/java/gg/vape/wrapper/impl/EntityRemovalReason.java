package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityRemovalReason;
import gg.vape.wrapper.Wrapper;

public class EntityRemovalReason
extends Wrapper {
    public static EntityRemovalReason U() {
        return new EntityRemovalReason(MEntityRemovalReason.U(EntityRemovalReason.vapeInstance.getMappingsMapperCompat().h));
    }

    public static EntityRemovalReason X() {
        return new EntityRemovalReason(MEntityRemovalReason.N(EntityRemovalReason.vapeInstance.getMappingsMapperCompat().h));
    }

    public static EntityRemovalReason c() {
        return new EntityRemovalReason(MEntityRemovalReason.Z(EntityRemovalReason.vapeInstance.getMappingsMapperCompat().h));
    }

    public EntityRemovalReason(Object object) {
        super(object);
    }

    public static EntityRemovalReason K() {
        return new EntityRemovalReason(MEntityRemovalReason.k(EntityRemovalReason.vapeInstance.getMappingsMapperCompat().h));
    }

    public static EntityRemovalReason P() {
        return new EntityRemovalReason(MEntityRemovalReason.i(EntityRemovalReason.vapeInstance.getMappingsMapperCompat().h));
    }
}

