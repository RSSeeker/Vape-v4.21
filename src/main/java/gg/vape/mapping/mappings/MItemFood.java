package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.impl.ForgeVersion;

public class MItemFood
extends Mapping {
    private final MappingField nutritionField;
    private final MappingField saturationField;

    private float getSaturationValue(Object itemFood) {
        return this.saturationField.getFloat(itemFood);
    }

    private int getNutritionValue(Object itemFood) {
        return this.nutritionField.getInt(itemFood);
    }

    public MItemFood() {
        this(MItemStack.f());
    }

    private MItemFood(int initializationState) {
        super(MappedClasses.ITEM_FOOD);
        if (initializationState != 0) {
            if (ForgeVersion.MC_1_16_5.d()) {
                Class<Integer> nutritionType = Integer.TYPE;
                boolean remapNutritionField = true;
                String nutritionFieldName = "value";
                MItemFood mappings = this;
                this.nutritionField = mappings.J(nutritionFieldName, remapNutritionField, nutritionType);
                Class<Float> saturationType = Float.TYPE;
                boolean remapSaturationField = true;
                String saturationFieldName = "saturation";
                this.saturationField = this.J(saturationFieldName, remapSaturationField, saturationType);
            } else {
                Class<Integer> nutritionType = Integer.TYPE;
                boolean remapNutritionField = true;
                String nutritionFieldName = "healAmount";
                MItemFood mappings = this;
                this.nutritionField = mappings.J(nutritionFieldName, remapNutritionField, nutritionType);
                Class<Float> saturationType = Float.TYPE;
                boolean remapSaturationField = true;
                String saturationFieldName = "saturationModifier";
                this.saturationField = this.J(saturationFieldName, remapSaturationField, saturationType);
            }
            return;
        }
        Class<Float> saturationType = Float.TYPE;
        boolean remapSaturationField = true;
        String saturationFieldName = "saturationModifier";
        MItemFood mappings = this;
        this.saturationField = mappings.J(saturationFieldName, remapSaturationField, saturationType);
        this.nutritionField = null;
    }

    public int getNutrition(Object itemFood) {
        return this.getNutritionValue(itemFood);
    }

    public float getSaturation(Object itemFood) {
        return this.getSaturationValue(itemFood);
    }
}
