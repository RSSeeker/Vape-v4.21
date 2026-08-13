package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MDamageSource;
import gg.vape.wrapper.impl.ForgeVersion;

public class MBlocks
extends Mapping {
    private final MappingField ladderField;
    private final MappingField airField;
    private MappingField powderSnowField;
    private final MappingField stoneField;

    public Object getPowderSnow() {
        return this.powderSnowField.getObject(null);
    }

    public Object getLadder() {
        return this.ladderField.getObject(null);
    }

    public Object getAir() {
        return this.airField.getObject(null);
    }

    public Object getStone() {
        return this.stoneField.getObject(null);
    }

    public MBlocks() {
        this(MDamageSource.r());
    }

    private MBlocks(int[] initializationState) {
        super(MappedClasses.BLOCKS);
        if (initializationState != null) {
            Class blockClass = MappedClasses.Zk;
            boolean remap = true;
            String fieldName = "air";
            MBlocks mappings = this;
            this.airField = mappings.registerStaticField(fieldName, remap, blockClass);
            this.ladderField = null;
            this.stoneField = null;
            return;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            Class ladderOwner = MappedClasses.Zk;
            boolean remapLadderField = true;
            String ladderFieldName = "LADDER";
            MBlocks mappings = this;
            this.ladderField = mappings.registerStaticField(ladderFieldName, remapLadderField, ladderOwner);
            Class stoneOwner = MappedClasses.Zk;
            boolean remapStoneField = true;
            String stoneFieldName = "STONE";
            this.stoneField = this.registerStaticField(stoneFieldName, remapStoneField, stoneOwner);
            Class airOwner = MappedClasses.Zk;
            boolean remapAirField = true;
            String airFieldName = "AIR";
            this.airField = this.registerStaticField(airFieldName, remapAirField, airOwner);
            if (ForgeVersion.MC_1_21_4.d()) {
                Class powderSnowOwner = MappedClasses.Zk;
                boolean remapPowderSnowField = true;
                String powderSnowFieldName = "POWDER_SNOW";
                this.powderSnowField = this.registerStaticField(powderSnowFieldName, remapPowderSnowField, powderSnowOwner);
            }
        } else {
            Class ladderOwner = MappedClasses.Zk;
            boolean remapLadderField = true;
            String ladderFieldName = "ladder";
            MBlocks mappings = this;
            this.ladderField = mappings.registerStaticField(ladderFieldName, remapLadderField, ladderOwner);
            Class stoneOwner = MappedClasses.Zk;
            boolean remapStoneField = true;
            String stoneFieldName = "stone";
            this.stoneField = this.registerStaticField(stoneFieldName, remapStoneField, stoneOwner);
            Class airOwner = MappedClasses.Zk;
            boolean remapAirField = true;
            String airFieldName = "air";
            this.airField = this.registerStaticField(airFieldName, remapAirField, airOwner);
        }
    }
}

