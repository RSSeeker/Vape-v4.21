package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MFoods;
import gg.vape.ui.click.component.GuiComponent;

public class MFoodProperties
extends Mapping {
    private final MappingField saturationField;
    private final MappingField canAlwaysEatField;
    private final MappingField nutritionField;

    public boolean canAlwaysEat(Object foodProperties) {
        return this.canAlwaysEatField.getBoolean(foodProperties);
    }

    public float getSaturation(Object foodProperties) {
        return this.saturationField.getFloat(foodProperties);
    }

    public MFoodProperties() {
        super(MappedClasses.foodPropertiesClass);
        Class<Integer> nutritionType = Integer.TYPE;
        boolean remapNutritionField = true;
        String nutritionFieldName = "nutrition";
        MFoodProperties mappings = this;
        this.nutritionField = mappings.J(nutritionFieldName, remapNutritionField, nutritionType);
        Class<Float> saturationType = Float.TYPE;
        boolean remapSaturationField = true;
        String saturationFieldName = "saturation";
        this.saturationField = this.J(saturationFieldName, remapSaturationField, saturationType);
        Class<Boolean> canAlwaysEatType = Boolean.TYPE;
        boolean remapCanAlwaysEatField = true;
        String canAlwaysEatFieldName = "canAlwaysEat";
        this.canAlwaysEatField = this.J(canAlwaysEatFieldName, remapCanAlwaysEatField, canAlwaysEatType);
        String foodsControlFlowMarker = MFoods.getControlFlowMarker();
        if (GuiComponent.getLegacyComponentState() == null) {
            MFoods.setControlFlowMarker("P9qc6b");
        }
    }

    public int getNutrition(Object foodProperties) {
        return this.nutritionField.getInt(foodProperties);
    }

}

