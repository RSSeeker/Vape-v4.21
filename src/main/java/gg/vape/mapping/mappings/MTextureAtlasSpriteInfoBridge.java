package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MTextureAtlasSpriteInfoBridge
extends Mapping {
    private final MappingMethod texturePathMethod;

    public MTextureAtlasSpriteInfoBridge() {
        super(MappedClasses.zI);
        this.texturePathMethod = this.Y("texturePath", true, MappedClasses.zC, new Class[]{});
    }

    public Object getTexturePath(Object spriteInfo) {
        return this.texturePathMethod.invokeObject(spriteInfo, new Object[0]);
    }
}

