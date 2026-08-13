package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MTextureManager;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTextureAtlasSpriteInfo
extends Mapping {
    private MappingMethod getSpriteMethod;
    private MappingField textureLocationField;
    private MappingField blocksAtlasLocationField;

    public static Object getBlocksAtlasLocation(MTextureAtlasSpriteInfo mapping) {
        return mapping.getBlocksAtlasLocation();
    }

    public MTextureAtlasSpriteInfo() {
        this(MTextureManager.getInitialControlFlowState());
    }

    private MTextureAtlasSpriteInfo(int n) {
        super(MappedClasses.L);
        if (n != 0) {
            Class clazz = MappedClasses.zC;
            boolean bl = true;
            String string = "textureLocation";
            MTextureAtlasSpriteInfo mTextureAtlasSpriteInfo = this;
            this.textureLocationField = mTextureAtlasSpriteInfo.J(string, bl, clazz);
            if (ForgeVersion.MC_1_17.d()) {
                Class[] classArray = new Class[]{MappedClasses.zC};
                Class clazz2 = MappedClasses.Db;
                boolean bl2 = true;
                String string2 = "getSprite";
                MTextureAtlasSpriteInfo mTextureAtlasSpriteInfo2 = this;
                this.getSpriteMethod = this.Y(string2, bl2, clazz2, classArray);
            }
            return;
        }
        Class clazz = MappedClasses.zC;
        boolean bl = true;
        String string = "textureLocation";
        MTextureAtlasSpriteInfo mTextureAtlasSpriteInfo = this;
        this.textureLocationField = mTextureAtlasSpriteInfo.J(string, bl, clazz);
        if (ForgeVersion.MC_1_17.d()) {
            Class clazz3 = MappedClasses.zC;
            boolean bl3 = true;
            String string3 = "LOCATION_BLOCKS";
            MTextureAtlasSpriteInfo mTextureAtlasSpriteInfo3 = this;
            this.blocksAtlasLocationField = this.registerStaticField(string3, bl3, clazz3);
        }
        if (ForgeVersion.MC_1_21_10.d()) {
            Class[] classArray = new Class[]{MappedClasses.zC};
            Class clazz4 = MappedClasses.Db;
            boolean bl4 = true;
            String string4 = "getSprite";
            MTextureAtlasSpriteInfo mTextureAtlasSpriteInfo4 = this;
            this.getSpriteMethod = this.Y(string4, bl4, clazz4, classArray);
        }
    }

    public static Object getSprite(MTextureAtlasSpriteInfo mapping, Object textureAtlas, Object location) {
        return mapping.getSprite(textureAtlas, location);
    }

    private Object getBlocksAtlasLocation() {
        return this.blocksAtlasLocationField.getObject(null);
    }


    private Object getTextureLocation(Object textureAtlas) {
        return this.textureLocationField.getObject(textureAtlas);
    }

    private Object getSprite(Object textureAtlas, Object location) {
        return this.getSpriteMethod.invokeObject(textureAtlas, location);
    }

    public static Object getTextureLocation(MTextureAtlasSpriteInfo mapping, Object textureAtlas) {
        return mapping.getTextureLocation(textureAtlas);
    }
}

