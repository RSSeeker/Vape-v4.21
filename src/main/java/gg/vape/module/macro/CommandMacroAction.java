package gg.vape.module.macro;

import gg.vape.module.macro.CommandMacro;
import gg.vape.module.macro.MacroAction;
import gg.vape.wrapper.impl.Minecraft;

class CommandMacroAction
implements MacroAction {
    private final CommandMacro macro;

    CommandMacroAction(CommandMacro commandMacro) {
        this.macro = commandMacro;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void tick() {
        Minecraft.a_xH_J().sendChatMessage(this.macro.getName());
    }
}

