package gg.vape.module;

import gg.vape.module.Category;
import gg.vape.module.Mod;

public class UtilityMod
extends Mod {
    public UtilityMod(String name) {
        super(name, 0, 0, Category.UTILITY, "");
    }

    public UtilityMod(String name, String description) {
        super(name, 0, 0, Category.UTILITY, description);
    }

    @Override
    public boolean isRequiresBind() {
        return true;
    }

    public UtilityMod(String name, Category category, String description) {
        super(name, 0, 0, category, description);
    }
}

