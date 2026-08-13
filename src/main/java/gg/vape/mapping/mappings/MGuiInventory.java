package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MGuiInventory
extends Mapping {
    private MappingMethod drawEntityOnScreenMethod;

    public void drawEntityOnScreen(int screenX, int screenY, int scale, float mouseX, float mouseY, Object entityHandle) {
        this.drawEntityOnScreenMethod.invokeVoid(null, screenX, screenY, scale, mouseX, mouseY, entityHandle);
    }

    public MGuiInventory() {
        this(MGuiContainer.l());
    }

    private MGuiInventory(String[] legacyMappingState) {
        super(MappedClasses.YS);
        if (!ForgeVersion.MC_1_20_6.d()) {
            if (ForgeVersion.MC_1_7_10.L()) {
                if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
                    this.drawEntityOnScreenMethod = this.registerStaticMethod("drawEntityOnScreen", true, Void.TYPE,
                            Integer.TYPE, Integer.TYPE, Integer.TYPE, Float.TYPE, Float.TYPE, MappedClasses.zm);
                } else {
                    this.drawEntityOnScreenMethod = this.registerStaticMethod("func_147046_a", Wrapper.isNativeAvailable, Void.TYPE,
                            Integer.TYPE, Integer.TYPE, Integer.TYPE, Float.TYPE, Float.TYPE, MappedClasses.zm);
                }
            } else {
                this.drawEntityOnScreenMethod = this.registerStaticMethod("drawEntityOnScreen", true, Void.TYPE,
                        Integer.TYPE, Integer.TYPE, Integer.TYPE, Float.TYPE, Float.TYPE, MappedClasses.zm);
            }
        }
    }

}
