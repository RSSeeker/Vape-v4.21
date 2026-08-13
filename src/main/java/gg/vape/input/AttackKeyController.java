package gg.vape.input;

import gg.vape.event.impl.SyntheticAttackRequestEvent;
import gg.vape.input.KeyBindingHelper;
import gg.vape.module.Mod;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;

public class AttackKeyController {
    public static void releaseAttackKey() {
        KeyBinding attackKey = Minecraft.a_w3_0_S().F();
        KeyBindingHelper.updateKeyBinding(attackKey, false, false);
    }


    public static boolean requestSyntheticAttack(Mod module) {
        SyntheticAttackRequestEvent event = new SyntheticAttackRequestEvent(module);
        if (event.fire()) {
            return false;
        }
        AttackKeyController.pressAttackKey();
        return true;
    }

    public static void pressAttackKey() {
        KeyBindingHelper.updateKeyBinding(Minecraft.a_w3_0_S().F(), true, true);
    }
}

