package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.ui.click.component.GuiComponent;

public class MMappedClassSlotFp
extends Mapping {
    private static GuiComponent[] g;

    public MMappedClassSlotFp() {
        super(MappedClasses.Fp);
    }

    public static void k(GuiComponent[] guiComponentArray) {
        g = guiComponentArray;
    }

    public static GuiComponent[] l() {
        return g;
    }

    static {
        if (MMappedClassSlotFp.l() == null) {
            MMappedClassSlotFp.k(new GuiComponent[1]);
        }
    }
}

