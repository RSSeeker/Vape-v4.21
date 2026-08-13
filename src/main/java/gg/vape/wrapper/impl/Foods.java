package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class Foods
extends Wrapper {
    public Foods(Object handle) {
        super(handle);
    }

    public static FoodProperties apple() {
        return wrap(Foods.vapeInstance.getMappings().foods.apple());
    }

    public static FoodProperties bakedPotato() {
        return wrap(Foods.vapeInstance.getMappings().foods.bakedPotato());
    }

    public static FoodProperties beef() {
        return wrap(Foods.vapeInstance.getMappings().foods.beef());
    }

    public static FoodProperties beetroot() {
        return wrap(Foods.vapeInstance.getMappings().foods.beetroot());
    }

    public static FoodProperties beetrootSoup() {
        return wrap(Foods.vapeInstance.getMappings().foods.beetrootSoup());
    }

    public static FoodProperties bread() {
        return wrap(Foods.vapeInstance.getMappings().foods.bread());
    }

    public static FoodProperties carrot() {
        return wrap(Foods.vapeInstance.getMappings().foods.carrot());
    }

    public static FoodProperties chicken() {
        return wrap(Foods.vapeInstance.getMappings().foods.chicken());
    }

    public static FoodProperties chorusFruit() {
        return wrap(Foods.vapeInstance.getMappings().foods.chorusFruit());
    }

    public static FoodProperties cod() {
        return wrap(Foods.vapeInstance.getMappings().foods.cod());
    }

    public static FoodProperties cookedBeef() {
        return wrap(Foods.vapeInstance.getMappings().foods.cookedBeef());
    }

    public static FoodProperties cookedChicken() {
        return wrap(Foods.vapeInstance.getMappings().foods.cookedChicken());
    }

    public static FoodProperties cookedCod() {
        return wrap(Foods.vapeInstance.getMappings().foods.cookedCod());
    }

    public static FoodProperties cookedMutton() {
        return wrap(Foods.vapeInstance.getMappings().foods.cookedMutton());
    }

    public static FoodProperties cookedPorkchop() {
        return wrap(Foods.vapeInstance.getMappings().foods.cookedPorkchop());
    }

    public static FoodProperties cookedRabbit() {
        return wrap(Foods.vapeInstance.getMappings().foods.cookedRabbit());
    }

    public static FoodProperties cookedSalmon() {
        return wrap(Foods.vapeInstance.getMappings().foods.cookedSalmon());
    }

    public static FoodProperties cookie() {
        return wrap(Foods.vapeInstance.getMappings().foods.cookie());
    }

    public static FoodProperties driedKelp() {
        return wrap(Foods.vapeInstance.getMappings().foods.driedKelp());
    }

    public static FoodProperties enchantedGoldenApple() {
        return wrap(Foods.vapeInstance.getMappings().foods.enchantedGoldenApple());
    }

    public static FoodProperties goldenApple() {
        return wrap(Foods.vapeInstance.getMappings().foods.goldenApple());
    }

    public static FoodProperties goldenCarrot() {
        return wrap(Foods.vapeInstance.getMappings().foods.goldenCarrot());
    }

    public static FoodProperties honeyBottle() {
        return wrap(Foods.vapeInstance.getMappings().foods.honeyBottle());
    }

    public static FoodProperties melonSlice() {
        return wrap(Foods.vapeInstance.getMappings().foods.melonSlice());
    }

    public static FoodProperties mushroomStew() {
        return wrap(Foods.vapeInstance.getMappings().foods.mushroomStew());
    }

    public static FoodProperties mutton() {
        return wrap(Foods.vapeInstance.getMappings().foods.mutton());
    }

    public static FoodProperties poisonousPotato() {
        return wrap(Foods.vapeInstance.getMappings().foods.poisonousPotato());
    }

    public static FoodProperties porkchop() {
        return wrap(Foods.vapeInstance.getMappings().foods.porkchop());
    }

    public static FoodProperties potato() {
        return wrap(Foods.vapeInstance.getMappings().foods.potato());
    }

    public static FoodProperties pufferfish() {
        return wrap(Foods.vapeInstance.getMappings().foods.pufferfish());
    }

    public static FoodProperties pumpkinPie() {
        return wrap(Foods.vapeInstance.getMappings().foods.pumpkinPie());
    }

    public static FoodProperties rabbit() {
        return wrap(Foods.vapeInstance.getMappings().foods.rabbit());
    }

    public static FoodProperties rabbitStew() {
        return wrap(Foods.vapeInstance.getMappings().foods.rabbitStew());
    }

    public static FoodProperties rottenFlesh() {
        return wrap(Foods.vapeInstance.getMappings().foods.rottenFlesh());
    }

    public static FoodProperties salmon() {
        return wrap(Foods.vapeInstance.getMappings().foods.salmon());
    }

    public static FoodProperties spiderEye() {
        return wrap(Foods.vapeInstance.getMappings().foods.spiderEye());
    }

    public static FoodProperties suspiciousStew() {
        return wrap(Foods.vapeInstance.getMappings().foods.suspiciousStew());
    }

    public static FoodProperties sweetBerries() {
        return wrap(Foods.vapeInstance.getMappings().foods.sweetBerries());
    }

    public static FoodProperties glowBerries() {
        return wrap(Foods.vapeInstance.getMappings().foods.glowBerries());
    }

    public static FoodProperties tropicalFish() {
        return wrap(Foods.vapeInstance.getMappings().foods.tropicalFish());
    }

    private static FoodProperties wrap(Object handle) {
        return new FoodProperties(handle);
    }
}
