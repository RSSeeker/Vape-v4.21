package gg.vape.utils;

import gg.vape.value.RandomValue;
import java.util.Random;

public class RandomUtil {
    private static final Random Y = new Random();

    public static int Y(int n, int n2) {
        int n3 = n2 - n;
        int n4 = n3 <= 0 ? n : Y.nextInt(n3) + n + 1;
        return n4;
    }

    public static int i(RandomValue randomValue) {
        return RandomUtil.Y(randomValue.getMinimumIntCompat(), randomValue.getMaximumIntCompat());
    }

}

