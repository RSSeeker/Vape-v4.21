package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;

public class MMappedClassSlotNRegistration
extends Mapping {
    private static int g;

    public MMappedClassSlotNRegistration() {
        super(MappedClasses.N);
    }

    public static void A(int n) {
        g = n;
    }

    public static int W() {
        int n = MMappedClassSlotNRegistration.r();
        return 0;
    }

    public static int r() {
        return g;
    }


    static {
        if (MMappedClassSlotNRegistration.r() == 0) {
            MMappedClassSlotNRegistration.A(63);
        }
    }
}

