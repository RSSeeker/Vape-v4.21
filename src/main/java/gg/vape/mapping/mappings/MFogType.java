package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.impl.ForgeVersion;

public class MFogType
extends Mapping {
    private final MappingField terrainOrWorldField;
    private final MappingField noneOrSkyField;

    public MFogType() {
        this(MEntityRenderer.X());
    }

    private MFogType(int initializationState) {
        super(MappedClasses.FOG_TYPE);
        if (initializationState != 0) {
            Class fogTypeClass = MappedClasses.FOG_TYPE;
            boolean remap = true;
            String fieldName = "FOG_TERRAIN";
            MFogType mappings = this;
            this.terrainOrWorldField = mappings.registerStaticField(fieldName, remap, fogTypeClass);
            this.noneOrSkyField = null;
            return;
        }
        if (ForgeVersion.MC_1_21_6.d()) {
            Class fogTypeClass = MappedClasses.FOG_TYPE;
            boolean remapNoneField = true;
            String noneFieldName = "NONE";
            MFogType mappings = this;
            this.noneOrSkyField = mappings.registerStaticField(noneFieldName, remapNoneField, fogTypeClass);
            Class worldFieldOwner = MappedClasses.FOG_TYPE;
            boolean remapWorldField = true;
            String worldFieldName = "WORLD";
            this.terrainOrWorldField = this.registerStaticField(worldFieldName, remapWorldField, worldFieldOwner);
        } else {
            Class fogTypeClass = MappedClasses.FOG_TYPE;
            boolean remapSkyField = true;
            String skyFieldName = "FOG_SKY";
            MFogType mappings = this;
            this.noneOrSkyField = mappings.registerStaticField(skyFieldName, remapSkyField, fogTypeClass);
            Class terrainFieldOwner = MappedClasses.FOG_TYPE;
            boolean remapTerrainField = true;
            String terrainFieldName = "FOG_TERRAIN";
            this.terrainOrWorldField = this.registerStaticField(terrainFieldName, remapTerrainField, terrainFieldOwner);
        }
    }

    public Object getNoneOrSky() {
        return this.noneOrSkyField.getObject(null);
    }

    public Object getTerrainOrWorld() {
        return this.terrainOrWorldField.getObject(null);
    }
}
