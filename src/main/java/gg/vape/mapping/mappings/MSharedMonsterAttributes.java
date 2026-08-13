package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MEntity;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSharedMonsterAttributes
extends Mapping {
    private MappingField G;
    private MappingField j;
    private MappingField y;
    private final MappingField e;
    private final MappingField W;
    private MappingField F;


    public Object J() {
        return this.j.getObject(null);
    }

    public Object w() {
        return this.e.getObject(null);
    }

    public Object o() {
        return this.F.getObject(null);
    }

    public Object W() {
        return this.y.getObject(null);
    }

    public MSharedMonsterAttributes() {
        this(MEntity.P());
    }

    private MSharedMonsterAttributes(int n) {
        super(MappedClasses.l);
        int n2 = n;
        if (ForgeVersion.MC_1_20_6.d()) {
            Class clazz = MappedClasses.Vo;
            boolean bl = true;
            String string = "MOVEMENT_SPEED";
            MSharedMonsterAttributes mSharedMonsterAttributes = this;
            this.W = mSharedMonsterAttributes.registerStaticField(string, bl, clazz);
            Class clazz2 = MappedClasses.Vo;
            boolean bl2 = true;
            String string2 = "ATTACK_DAMAGE";
            MSharedMonsterAttributes mSharedMonsterAttributes2 = this;
            this.e = this.registerStaticField(string2, bl2, clazz2);
            Class clazz3 = MappedClasses.Vo;
            boolean bl3 = true;
            String string3 = "ARMOR";
            MSharedMonsterAttributes mSharedMonsterAttributes3 = this;
            this.y = this.registerStaticField(string3, bl3, clazz3);
            Class clazz4 = MappedClasses.Vo;
            boolean bl4 = true;
            String string4 = "ARMOR_TOUGHNESS";
            MSharedMonsterAttributes mSharedMonsterAttributes4 = this;
            this.j = this.registerStaticField(string4, bl4, clazz4);
            Class clazz5 = MappedClasses.Vo;
            boolean bl5 = true;
            String string5 = "SNEAKING_SPEED";
            MSharedMonsterAttributes mSharedMonsterAttributes5 = this;
            this.G = this.registerStaticField(string5, bl5, clazz5);
            Class clazz6 = MappedClasses.Vo;
            boolean bl6 = true;
            String string6 = "WATER_MOVEMENT_EFFICIENCY";
            MSharedMonsterAttributes mSharedMonsterAttributes6 = this;
            this.F = this.registerStaticField(string6, bl6, clazz6);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            Class clazz = MappedClasses.Fe;
            boolean bl = true;
            String string = "MOVEMENT_SPEED";
            MSharedMonsterAttributes mSharedMonsterAttributes = this;
            this.W = mSharedMonsterAttributes.registerStaticField(string, bl, clazz);
            Class clazz7 = MappedClasses.Fe;
            boolean bl7 = true;
            String string7 = "ATTACK_DAMAGE";
            MSharedMonsterAttributes mSharedMonsterAttributes7 = this;
            this.e = this.registerStaticField(string7, bl7, clazz7);
            Class clazz8 = MappedClasses.Fe;
            boolean bl8 = true;
            String string8 = "ARMOR";
            MSharedMonsterAttributes mSharedMonsterAttributes8 = this;
            this.y = this.registerStaticField(string8, bl8, clazz8);
            Class clazz9 = MappedClasses.Fe;
            boolean bl9 = true;
            String string9 = "ARMOR_TOUGHNESS";
            MSharedMonsterAttributes mSharedMonsterAttributes9 = this;
            this.j = this.registerStaticField(string9, bl9, clazz9);
        } else if (ForgeVersion.MC_1_12_2.d()) {
            Class clazz = MappedClasses.Fe;
            boolean bl = true;
            String string = "MOVEMENT_SPEED";
            MSharedMonsterAttributes mSharedMonsterAttributes = this;
            this.W = mSharedMonsterAttributes.registerStaticField(string, bl, clazz);
            Class clazz10 = MappedClasses.Fe;
            boolean bl10 = true;
            String string10 = "ATTACK_DAMAGE";
            MSharedMonsterAttributes mSharedMonsterAttributes10 = this;
            this.e = this.registerStaticField(string10, bl10, clazz10);
        } else {
            Class clazz = MappedClasses.Fe;
            boolean bl = true;
            String string = "movementSpeed";
            MSharedMonsterAttributes mSharedMonsterAttributes = this;
            this.W = mSharedMonsterAttributes.registerStaticField(string, bl, clazz);
            Class clazz11 = MappedClasses.Fe;
            boolean bl11 = true;
            String string11 = "attackDamage";
            MSharedMonsterAttributes mSharedMonsterAttributes11 = this;
            this.e = this.registerStaticField(string11, bl11, clazz11);
        }
    }

    public Object y() {
        return this.G.getObject(null);
    }

    public Object N() {
        return this.W.getObject(null);
    }
}

