package gg.vape.input;

import gg.vape.runtime.NativeBridge;
import gg.vape.wrapper.impl.ForgeVersion;
import org.lwjgl.input.Keyboard;

public class KeyboardCodeUtil {
    static int[] legacyToVirtualKeyMap;
    private static final String GRAVE_KEY_NAME;

    static {
        GRAVE_KEY_NAME = "Grave";
        legacyToVirtualKeyMap = new int[10000];
        KeyboardCodeUtil.legacyToVirtualKeyMap[1] = 27;
        KeyboardCodeUtil.legacyToVirtualKeyMap[2] = 49;
        KeyboardCodeUtil.legacyToVirtualKeyMap[3] = 50;
        KeyboardCodeUtil.legacyToVirtualKeyMap[4] = 51;
        KeyboardCodeUtil.legacyToVirtualKeyMap[5] = 52;
        KeyboardCodeUtil.legacyToVirtualKeyMap[6] = 53;
        KeyboardCodeUtil.legacyToVirtualKeyMap[7] = 54;
        KeyboardCodeUtil.legacyToVirtualKeyMap[8] = 55;
        KeyboardCodeUtil.legacyToVirtualKeyMap[9] = 56;
        KeyboardCodeUtil.legacyToVirtualKeyMap[10] = 57;
        KeyboardCodeUtil.legacyToVirtualKeyMap[11] = 48;
        KeyboardCodeUtil.legacyToVirtualKeyMap[12] = 189;
        KeyboardCodeUtil.legacyToVirtualKeyMap[13] = 187;
        KeyboardCodeUtil.legacyToVirtualKeyMap[14] = 8;
        KeyboardCodeUtil.legacyToVirtualKeyMap[15] = 9;
        KeyboardCodeUtil.legacyToVirtualKeyMap[16] = 81;
        KeyboardCodeUtil.legacyToVirtualKeyMap[17] = 87;
        KeyboardCodeUtil.legacyToVirtualKeyMap[18] = 69;
        KeyboardCodeUtil.legacyToVirtualKeyMap[19] = 82;
        KeyboardCodeUtil.legacyToVirtualKeyMap[20] = 84;
        KeyboardCodeUtil.legacyToVirtualKeyMap[21] = 89;
        KeyboardCodeUtil.legacyToVirtualKeyMap[22] = 85;
        KeyboardCodeUtil.legacyToVirtualKeyMap[23] = 73;
        KeyboardCodeUtil.legacyToVirtualKeyMap[24] = 79;
        KeyboardCodeUtil.legacyToVirtualKeyMap[25] = 80;
        KeyboardCodeUtil.legacyToVirtualKeyMap[26] = 219;
        KeyboardCodeUtil.legacyToVirtualKeyMap[27] = 221;
        KeyboardCodeUtil.legacyToVirtualKeyMap[28] = 13;
        KeyboardCodeUtil.legacyToVirtualKeyMap[29] = 162;
        KeyboardCodeUtil.legacyToVirtualKeyMap[30] = 65;
        KeyboardCodeUtil.legacyToVirtualKeyMap[31] = 83;
        KeyboardCodeUtil.legacyToVirtualKeyMap[32] = 68;
        KeyboardCodeUtil.legacyToVirtualKeyMap[33] = 70;
        KeyboardCodeUtil.legacyToVirtualKeyMap[34] = 71;
        KeyboardCodeUtil.legacyToVirtualKeyMap[35] = 72;
        KeyboardCodeUtil.legacyToVirtualKeyMap[36] = 74;
        KeyboardCodeUtil.legacyToVirtualKeyMap[37] = 75;
        KeyboardCodeUtil.legacyToVirtualKeyMap[38] = 76;
        KeyboardCodeUtil.legacyToVirtualKeyMap[39] = 186;
        KeyboardCodeUtil.legacyToVirtualKeyMap[40] = 222;
        KeyboardCodeUtil.legacyToVirtualKeyMap[41] = 192;
        KeyboardCodeUtil.legacyToVirtualKeyMap[42] = 160;
        KeyboardCodeUtil.legacyToVirtualKeyMap[43] = 220;
        KeyboardCodeUtil.legacyToVirtualKeyMap[44] = 90;
        KeyboardCodeUtil.legacyToVirtualKeyMap[45] = 88;
        KeyboardCodeUtil.legacyToVirtualKeyMap[46] = 67;
        KeyboardCodeUtil.legacyToVirtualKeyMap[47] = 86;
        KeyboardCodeUtil.legacyToVirtualKeyMap[48] = 66;
        KeyboardCodeUtil.legacyToVirtualKeyMap[49] = 78;
        KeyboardCodeUtil.legacyToVirtualKeyMap[50] = 77;
        KeyboardCodeUtil.legacyToVirtualKeyMap[51] = 188;
        KeyboardCodeUtil.legacyToVirtualKeyMap[52] = 190;
        KeyboardCodeUtil.legacyToVirtualKeyMap[53] = 191;
        KeyboardCodeUtil.legacyToVirtualKeyMap[54] = 161;
        KeyboardCodeUtil.legacyToVirtualKeyMap[55] = 106;
        KeyboardCodeUtil.legacyToVirtualKeyMap[56] = 164;
        KeyboardCodeUtil.legacyToVirtualKeyMap[57] = 32;
        KeyboardCodeUtil.legacyToVirtualKeyMap[58] = 20;
        KeyboardCodeUtil.legacyToVirtualKeyMap[59] = 112;
        KeyboardCodeUtil.legacyToVirtualKeyMap[60] = 113;
        KeyboardCodeUtil.legacyToVirtualKeyMap[61] = 114;
        KeyboardCodeUtil.legacyToVirtualKeyMap[62] = 115;
        KeyboardCodeUtil.legacyToVirtualKeyMap[63] = 116;
        KeyboardCodeUtil.legacyToVirtualKeyMap[64] = 117;
        KeyboardCodeUtil.legacyToVirtualKeyMap[65] = 118;
        KeyboardCodeUtil.legacyToVirtualKeyMap[66] = 119;
        KeyboardCodeUtil.legacyToVirtualKeyMap[67] = 120;
        KeyboardCodeUtil.legacyToVirtualKeyMap[68] = 121;
        KeyboardCodeUtil.legacyToVirtualKeyMap[69] = 144;
        KeyboardCodeUtil.legacyToVirtualKeyMap[70] = 145;
        KeyboardCodeUtil.legacyToVirtualKeyMap[71] = 103;
        KeyboardCodeUtil.legacyToVirtualKeyMap[72] = 104;
        KeyboardCodeUtil.legacyToVirtualKeyMap[73] = 105;
        KeyboardCodeUtil.legacyToVirtualKeyMap[74] = 109;
        KeyboardCodeUtil.legacyToVirtualKeyMap[75] = 100;
        KeyboardCodeUtil.legacyToVirtualKeyMap[76] = 101;
        KeyboardCodeUtil.legacyToVirtualKeyMap[77] = 102;
        KeyboardCodeUtil.legacyToVirtualKeyMap[78] = 107;
        KeyboardCodeUtil.legacyToVirtualKeyMap[79] = 97;
        KeyboardCodeUtil.legacyToVirtualKeyMap[80] = 98;
        KeyboardCodeUtil.legacyToVirtualKeyMap[81] = 99;
        KeyboardCodeUtil.legacyToVirtualKeyMap[82] = 96;
        KeyboardCodeUtil.legacyToVirtualKeyMap[83] = 110;
        KeyboardCodeUtil.legacyToVirtualKeyMap[87] = 122;
        KeyboardCodeUtil.legacyToVirtualKeyMap[88] = 123;
        KeyboardCodeUtil.legacyToVirtualKeyMap[100] = 124;
        KeyboardCodeUtil.legacyToVirtualKeyMap[101] = 125;
        KeyboardCodeUtil.legacyToVirtualKeyMap[102] = 126;
        KeyboardCodeUtil.legacyToVirtualKeyMap[103] = 127;
        KeyboardCodeUtil.legacyToVirtualKeyMap[104] = 128;
        KeyboardCodeUtil.legacyToVirtualKeyMap[105] = 129;
        KeyboardCodeUtil.legacyToVirtualKeyMap[112] = 21;
        KeyboardCodeUtil.legacyToVirtualKeyMap[113] = 130;
        KeyboardCodeUtil.legacyToVirtualKeyMap[121] = 28;
        KeyboardCodeUtil.legacyToVirtualKeyMap[123] = 29;
        KeyboardCodeUtil.legacyToVirtualKeyMap[141] = 146;
        KeyboardCodeUtil.legacyToVirtualKeyMap[146] = 186;
        KeyboardCodeUtil.legacyToVirtualKeyMap[156] = 13;
        KeyboardCodeUtil.legacyToVirtualKeyMap[157] = 163;
        KeyboardCodeUtil.legacyToVirtualKeyMap[179] = 188;
        KeyboardCodeUtil.legacyToVirtualKeyMap[184] = 165;
        KeyboardCodeUtil.legacyToVirtualKeyMap[197] = 19;
        KeyboardCodeUtil.legacyToVirtualKeyMap[199] = 36;
        KeyboardCodeUtil.legacyToVirtualKeyMap[200] = 38;
        KeyboardCodeUtil.legacyToVirtualKeyMap[201] = 33;
        KeyboardCodeUtil.legacyToVirtualKeyMap[203] = 37;
        KeyboardCodeUtil.legacyToVirtualKeyMap[205] = 39;
        KeyboardCodeUtil.legacyToVirtualKeyMap[207] = 35;
        KeyboardCodeUtil.legacyToVirtualKeyMap[208] = 40;
        KeyboardCodeUtil.legacyToVirtualKeyMap[209] = 34;
        KeyboardCodeUtil.legacyToVirtualKeyMap[210] = 45;
        KeyboardCodeUtil.legacyToVirtualKeyMap[211] = 46;
        KeyboardCodeUtil.legacyToVirtualKeyMap[218] = 12;
    }

    public static int resolveModifierKey(int virtualKey, int keyMetadata) {
        int resolvedKey = virtualKey;
        int scanCode = (keyMetadata & 0xFF0000) >> 16;
        boolean extendedKey = (keyMetadata & 0x1000000) != 0;
        if (extendedKey) {
            switch (virtualKey) {
                case 16: {
                    resolvedKey = NativeBridge.mvk(scanCode, 3);
                    break;
                }
                case 17: {
                    resolvedKey = 163;
                    break;
                }
                case 18: {
                    resolvedKey = 165;
                    break;
                }
                default: {
                    resolvedKey = virtualKey;
                }
            }
            return resolvedKey;
        }
        switch (virtualKey) {
            case 16: {
                resolvedKey = NativeBridge.mvk(scanCode, 3);
                break;
            }
            case 17: {
                resolvedKey = 162;
                break;
            }
            case 18: {
                resolvedKey = 164;
                break;
            }
            default: {
                resolvedKey = virtualKey;
            }
        }
        return resolvedKey;
    }

    public static int convertLegacyKeyCode(int legacyKeyCode) {
        if (legacyKeyCode > legacyToVirtualKeyMap.length - 1) {
            return 0;
        }
        return legacyToVirtualKeyMap[legacyKeyCode];
    }

    public static String getVirtualKeyName(int virtualKey) {
        if (virtualKey == 192) {
            return GRAVE_KEY_NAME;
        }
        int scanCode = NativeBridge.mvk(virtualKey, 0);
        switch (virtualKey) {
            case 33: 
            case 34: 
            case 35: 
            case 36: 
            case 37: 
            case 38: 
            case 39: 
            case 40: 
            case 45: 
            case 46: 
            case 111: 
            case 144: {
                scanCode |= 0x100;
            }
        }
        return NativeBridge.gkn(scanCode << 16);
    }

    public static int decodeExtendedMouseButton(long buttonMetadata) {
        long buttonMask = buttonMetadata & 0xFFFFFFFFFFFF0000L;
        if (buttonMask == 131072L) {
            return 3;
        }
        if (buttonMask == 65536L) {
            return 4;
        }
        return 5;
    }


    public static void disableLegacyRepeatEvents() {
        if (ForgeVersion.MC_1_16_5.v() && Keyboard.areRepeatEventsEnabled()) {
            Keyboard.enableRepeatEvents((boolean)false);
        }
    }
}

