package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMutableBoundingBox
extends Mapping {
    public MappingMethod constructor;
    public MappingMethod intersectsMethod;

    public boolean intersects(Object boundingBoxHandle, Object otherBoundingBoxHandle) {
        return this.intersectsMethod.invokeBoolean(boundingBoxHandle, otherBoundingBoxHandle);
    }

    public MMutableBoundingBox() {
        this(BlockData.W());
    }

    private MMutableBoundingBox(String[] boundingBoxMappingState) {
        super(MappedClasses.f);
        if (boundingBoxMappingState != null) {
            this.constructor = this.Y("<init>", false, Void.TYPE,
                    Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            if (ForgeVersion.MC_1_17.d()) {
                this.intersectsMethod = this.Y(
                        "m_71049_", ForgeVersion.MC_1_20_6.d(), Boolean.TYPE, MappedClasses.f);
            } else {
                this.intersectsMethod = this.Y("intersectsWith", true, Boolean.TYPE, MappedClasses.f);
            }
            return;
        }
        this.intersectsMethod = this.Y("<init>", false, Void.TYPE,
                Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        this.constructor = null;
    }


    public Object create(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        return this.constructor.newInstance(minX, minY, minZ, maxX, maxY, maxZ);
    }
}

