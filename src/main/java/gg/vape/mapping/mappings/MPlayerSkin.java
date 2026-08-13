package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MPlayerSkin
extends Mapping {
    private MappingField bodyField;
    private MappingField textureField;

    public Object getTexture(Object playerSkinHandle) {
        return this.textureField.getObject(playerSkinHandle);
    }

    public Object getBody(Object playerSkinHandle) {
        return this.bodyField.getObject(playerSkinHandle);
    }


    public MPlayerSkin() {
        this(BlockData.W());
    }

    private MPlayerSkin(String[] playerSkinMappingState) {
        super(MappedClasses.uZ);
        if (playerSkinMappingState != null) {
            if (ForgeVersion.MC_1_21_10.d()) {
                this.bodyField = this.J("body", true, MappedClasses.zI);
            } else {
                this.textureField = this.J("texture", true, MappedClasses.zC);
            }
            return;
        }
        this.textureField = this.J("texture", true, MappedClasses.zC);
    }
}

