package gg.vape.input;

import gg.vape.input.InputEventHandler;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiKeyTypedDispatcher;
import java.util.HashSet;
import java.util.Set;

public class CharacterInputHandler
implements InputEventHandler {
    private Set<Integer> allowedControlCharacters = new HashSet<Integer>();
    private static int legacyState;


    @Override
    public boolean handle(long firstArgument, long secondArgument) {
        char typedCharacter = (char)firstArgument;
        char controlCharacter = '\u0000';
        if (typedCharacter == '\u00a7' || typedCharacter < ' ' || typedCharacter == '\u007f') {
            if (this.allowedControlCharacters.contains(Integer.valueOf(typedCharacter))) {
                controlCharacter = typedCharacter;
            }
            if (controlCharacter == '\u0000') {
                return false;
            }
        }
        GuiKeyTypedDispatcher.dispatch(typedCharacter, controlCharacter);
        return !ClientSettings.INSTANCE.inputEnabled;
    }

    public static int getLegacySentinelResult() {
        int state = CharacterInputHandler.getLegacyState();
        if (state == 0) {
            return 63;
        }
        return 0;
    }

    public CharacterInputHandler() {
        this.allowedControlCharacters.add(8);
        this.allowedControlCharacters.add(3);
        this.allowedControlCharacters.add(22);
        this.allowedControlCharacters.add(27);
        this.allowedControlCharacters.add(13);
        this.allowedControlCharacters.add(9);
    }

    public static void setLegacyState(int state) {
        legacyState = state;
    }

    public static int getLegacyState() {
        return legacyState;
    }

    static {
        if (CharacterInputHandler.getLegacySentinelResult() == 0) {
            CharacterInputHandler.setLegacyState(4);
        }
    }
}

