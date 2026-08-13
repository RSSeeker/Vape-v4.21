package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ScaledResolution;

public class MScreen
extends Mapping {
    private MappingMethod getMobEffectSpriteMethod;

    public Object getMobEffectSprite(Object effectHolderHandle) {
        return this.getMobEffectSpriteMethod.invokeObject(null, effectHolderHandle);
    }

    public MScreen() {
        this(ScaledResolution.W());
    }

    private MScreen(int screenControlFlowState) {
        super(MappedClasses.uH);
        if (screenControlFlowState != 0) {
            if (ForgeVersion.MC_1_21_6.d()) {
                if (ForgeVersion.MC_26_2.d()) {
                    this.getMobEffectSpriteMethod = this.registerStaticMethodForOwner(
                            MappedClasses.zK, "getMobEffectSprite", true, MappedClasses.zC, MappedClasses.Vo);
                } else {
                    this.getMobEffectSpriteMethod = this.registerStaticMethod(
                            "getMobEffectSprite", true, MappedClasses.zC, MappedClasses.Vo);
                }
            }
            return;
        }
        if (ForgeVersion.MC_1_21_6.d()) {
            this.getMobEffectSpriteMethod = this.registerStaticMethodForOwner(
                    MappedClasses.zK, "getMobEffectSprite", true, MappedClasses.zC, MappedClasses.Vo);
        }
        this.getMobEffectSpriteMethod = this.registerStaticMethod(
                "getMobEffectSprite", true, MappedClasses.zC, MappedClasses.Vo);
    }
}
