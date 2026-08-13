package gg.vape.utils;

import java.util.TreeMap;

public class RomanNumeralUtil {
    private static final TreeMap<Integer, String> ROMAN_NUMERALS;

    private static int romanDigitValue(char digit) {
        if (digit == 'I') {
            return 1;
        }
        if (digit == 'V') {
            return 5;
        }
        if (digit == 'X') {
            return 10;
        }
        if (digit == 'L') {
            return 50;
        }
        if (digit == 'C') {
            return 100;
        }
        if (digit == 'D') {
            return 500;
        }
        return digit == 'M' ? 1000 : -1;
    }


    private static String decodeUtf8(byte[] bytes) {
        int outputLength = 0;
        int inputLength = bytes.length;
        char[] characters = new char[inputLength];
        for (int index = 0; index < inputLength; ++index) {
            byte continuationByte;
            char character;
            int firstByte = 0xFF & bytes[index];
            if (firstByte < 192) {
                characters[outputLength++] = (char)firstByte;
                continue;
            }
            if (firstByte < 224) {
                character = (char)((char)(firstByte & 0x1F) << 6);
                continuationByte = bytes[++index];
                character = (char)(character | (char)(continuationByte & 0x3F));
                characters[outputLength++] = character;
                continue;
            }
            if (index >= inputLength - 2) continue;
            character = (char)((char)(firstByte & 0xF) << 12);
            continuationByte = bytes[++index];
            character = (char)(character | (char)(continuationByte & 0x3F) << 6);
            continuationByte = bytes[++index];
            character = (char)(character | (char)(continuationByte & 0x3F));
            characters[outputLength++] = character;
        }
        return new String(characters, 0, outputLength);
    }

    public static String toRoman(int value) {
        if (value < 0) {
            return String.valueOf(value);
        }
        int numeralValue = ROMAN_NUMERALS.floorKey(value);
        if (value == numeralValue) {
            return ROMAN_NUMERALS.get(value);
        }
        return ROMAN_NUMERALS.get(numeralValue) + RomanNumeralUtil.toRoman(value - numeralValue);
    }

    public static int fromRoman(String string) {
        int value = 0;
        for (int index = 0; index < string.length(); ++index) {
            int currentDigit = RomanNumeralUtil.romanDigitValue(string.charAt(index));
            if (index + 1 < string.length()) {
                int nextDigit = RomanNumeralUtil.romanDigitValue(string.charAt(index + 1));
                if (currentDigit >= nextDigit) {
                    value += currentDigit;
                    continue;
                }
                value = value + nextDigit - currentDigit;
                ++index;
                continue;
            }
            value += currentDigit;
            ++index;
        }
        return value;
    }

    static {
        try {
            String[] stringArray = new String[]{"IV", "CM", "XC", "XL", "IX", "CD"};
            ROMAN_NUMERALS = new TreeMap();
            ROMAN_NUMERALS.put(1000, "M");
            ROMAN_NUMERALS.put(900, stringArray[1]);
            ROMAN_NUMERALS.put(500, "D");
            ROMAN_NUMERALS.put(400, stringArray[5]);
            ROMAN_NUMERALS.put(100, "C");
            ROMAN_NUMERALS.put(90, stringArray[2]);
            ROMAN_NUMERALS.put(50, "L");
            ROMAN_NUMERALS.put(40, stringArray[3]);
            ROMAN_NUMERALS.put(10, "X");
            ROMAN_NUMERALS.put(9, stringArray[4]);
            ROMAN_NUMERALS.put(5, "V");
            ROMAN_NUMERALS.put(4, stringArray[0]);
            ROMAN_NUMERALS.put(1, "I");
            ROMAN_NUMERALS.put(0, "");
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}

