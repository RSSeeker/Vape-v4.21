package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MFoods
extends Mapping {
    private static String controlFlowMarker;
    private final MappingField appleField;
    private final MappingField bakedPotatoField;
    private final MappingField beefField;
    private final MappingField beetrootField;
    private final MappingField beetrootSoupField;
    private final MappingField breadField;
    private final MappingField carrotField;
    private final MappingField chickenField;
    private final MappingField chorusFruitField;
    private final MappingField codField;
    private final MappingField cookedBeefField;
    private final MappingField cookedChickenField;
    private final MappingField cookedCodField;
    private final MappingField cookedMuttonField;
    private final MappingField cookedPorkchopField;
    private final MappingField cookedRabbitField;
    private final MappingField cookedSalmonField;
    private final MappingField cookieField;
    private final MappingField driedKelpField;
    private final MappingField enchantedGoldenAppleField;
    private final MappingField goldenAppleField;
    private final MappingField goldenCarrotField;
    private final MappingField honeyBottleField;
    private final MappingField melonSliceField;
    private final MappingField mushroomStewField;
    private final MappingField muttonField;
    private final MappingField poisonousPotatoField;
    private final MappingField porkchopField;
    private final MappingField potatoField;
    private final MappingField pufferfishField;
    private final MappingField pumpkinPieField;
    private final MappingField rabbitField;
    private final MappingField rabbitStewField;
    private final MappingField rottenFleshField;
    private final MappingField salmonField;
    private final MappingField spiderEyeField;
    private final MappingField suspiciousStewField;
    private final MappingField sweetBerriesField;
    private final MappingField glowBerriesField;
    private final MappingField tropicalFishField;

    public static void setControlFlowMarker(String marker) {
        controlFlowMarker = marker;
    }

    public static String getControlFlowMarker() {
        return controlFlowMarker;
    }

    public MFoods() {
        super(MappedClasses.foodsClass);
        this.appleField = this.registerFood("APPLE");
        this.bakedPotatoField = this.registerFood("BAKED_POTATO");
        this.beefField = this.registerFood("BEEF");
        this.beetrootField = this.registerFood("BEETROOT");
        this.beetrootSoupField = this.registerFood("BEETROOT_SOUP");
        this.breadField = this.registerFood("BREAD");
        this.carrotField = this.registerFood("CARROT");
        this.chickenField = this.registerFood("CHICKEN");
        this.chorusFruitField = this.registerFood("CHORUS_FRUIT");
        this.codField = this.registerFood("COD");
        this.cookedBeefField = this.registerFood("COOKED_BEEF");
        this.cookedChickenField = this.registerFood("COOKED_CHICKEN");
        this.cookedCodField = this.registerFood("COOKED_COD");
        this.cookedMuttonField = this.registerFood("COOKED_MUTTON");
        this.cookedPorkchopField = this.registerFood("COOKED_PORKCHOP");
        this.cookedRabbitField = this.registerFood("COOKED_RABBIT");
        this.cookedSalmonField = this.registerFood("COOKED_SALMON");
        this.cookieField = this.registerFood("COOKIE");
        this.driedKelpField = this.registerFood("DRIED_KELP");
        this.enchantedGoldenAppleField = this.registerFood("ENCHANTED_GOLDEN_APPLE");
        this.goldenAppleField = this.registerFood("GOLDEN_APPLE");
        this.goldenCarrotField = this.registerFood("GOLDEN_CARROT");
        this.honeyBottleField = this.registerFood("HONEY_BOTTLE");
        this.melonSliceField = this.registerFood("MELON_SLICE");
        this.mushroomStewField = this.registerFood("MUSHROOM_STEW");
        this.muttonField = this.registerFood("MUTTON");
        this.poisonousPotatoField = this.registerFood("POISONOUS_POTATO");
        this.porkchopField = this.registerFood("PORKCHOP");
        this.potatoField = this.registerFood("POTATO");
        this.pufferfishField = this.registerFood("PUFFERFISH");
        this.pumpkinPieField = this.registerFood("PUMPKIN_PIE");
        this.rabbitField = this.registerFood("RABBIT");
        this.rabbitStewField = this.registerFood("RABBIT_STEW");
        this.rottenFleshField = this.registerFood("ROTTEN_FLESH");
        this.salmonField = this.registerFood("SALMON");
        this.spiderEyeField = this.registerFood("SPIDER_EYE");
        this.suspiciousStewField = this.registerFood("SUSPICIOUS_STEW");
        this.sweetBerriesField = this.registerFood("SWEET_BERRIES");
        this.glowBerriesField = this.registerFood("GLOW_BERRIES");
        this.tropicalFishField = this.registerFood("TROPICAL_FISH");
        if (MFoods.getControlFlowMarker() == null) {
            GuiComponent.setLegacyComponentState(new GuiComponent[5]);
        }
    }

    private MappingField registerFood(String fieldName) {
        return this.registerStaticField(fieldName, true, MappedClasses.foodPropertiesClass);
    }

    public Object apple() {
        return this.appleField.getObject(null);
    }

    public Object bakedPotato() {
        return this.bakedPotatoField.getObject(null);
    }

    public Object beef() {
        return this.beefField.getObject(null);
    }

    public Object beetroot() {
        return this.beetrootField.getObject(null);
    }

    public Object beetrootSoup() {
        return this.beetrootSoupField.getObject(null);
    }

    public Object bread() {
        return this.breadField.getObject(null);
    }

    public Object carrot() {
        return this.carrotField.getObject(null);
    }

    public Object chicken() {
        return this.chickenField.getObject(null);
    }

    public Object chorusFruit() {
        return this.chorusFruitField.getObject(null);
    }

    public Object cod() {
        return this.codField.getObject(null);
    }

    public Object cookedBeef() {
        return this.cookedBeefField.getObject(null);
    }

    public Object cookedChicken() {
        return this.cookedChickenField.getObject(null);
    }

    public Object cookedCod() {
        return this.cookedCodField.getObject(null);
    }

    public Object cookedMutton() {
        return this.cookedMuttonField.getObject(null);
    }

    public Object cookedPorkchop() {
        return this.cookedPorkchopField.getObject(null);
    }

    public Object cookedRabbit() {
        return this.cookedRabbitField.getObject(null);
    }

    public Object cookedSalmon() {
        return this.cookedSalmonField.getObject(null);
    }

    public Object cookie() {
        return this.cookieField.getObject(null);
    }

    public Object driedKelp() {
        return this.driedKelpField.getObject(null);
    }

    public Object enchantedGoldenApple() {
        return this.enchantedGoldenAppleField.getObject(null);
    }

    public Object goldenApple() {
        return this.goldenAppleField.getObject(null);
    }

    public Object goldenCarrot() {
        return this.goldenCarrotField.getObject(null);
    }

    public Object honeyBottle() {
        return this.honeyBottleField.getObject(null);
    }

    public Object melonSlice() {
        return this.melonSliceField.getObject(null);
    }

    public Object mushroomStew() {
        return this.mushroomStewField.getObject(null);
    }

    public Object mutton() {
        return this.muttonField.getObject(null);
    }

    public Object poisonousPotato() {
        return this.poisonousPotatoField.getObject(null);
    }

    public Object porkchop() {
        return this.porkchopField.getObject(null);
    }

    public Object potato() {
        return this.potatoField.getObject(null);
    }

    public Object pufferfish() {
        return this.pufferfishField.getObject(null);
    }

    public Object pumpkinPie() {
        return this.pumpkinPieField.getObject(null);
    }

    public Object rabbit() {
        return this.rabbitField.getObject(null);
    }

    public Object rabbitStew() {
        return this.rabbitStewField.getObject(null);
    }

    public Object rottenFlesh() {
        return this.rottenFleshField.getObject(null);
    }

    public Object salmon() {
        return this.salmonField.getObject(null);
    }

    public Object spiderEye() {
        return this.spiderEyeField.getObject(null);
    }

    public Object suspiciousStew() {
        return this.suspiciousStewField.getObject(null);
    }

    public Object sweetBerries() {
        return this.sweetBerriesField.getObject(null);
    }

    public Object glowBerries() {
        return this.glowBerriesField.getObject(null);
    }

    public Object tropicalFish() {
        return this.tropicalFishField.getObject(null);
    }

    static {
        MFoods.setControlFlowMarker("sLz4v");
    }
}
