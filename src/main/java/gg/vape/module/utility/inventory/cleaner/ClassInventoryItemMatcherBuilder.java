package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.ClassInventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.ClassInventoryItemMatcherBuilderConstructorMarker;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherBuilderBase;
import gg.vape.module.utility.inventory.cleaner.InventoryMatcherListMode;
import java.util.ArrayList;
import java.util.List;

public class ClassInventoryItemMatcherBuilder
extends InventoryItemMatcherBuilderBase<ClassInventoryItemMatcherBuilder> {
    private InventoryMatcherListMode listMode;
    private final List<Class<?>> classes = new ArrayList();

    public ClassInventoryItemMatcherBuilder(InventoryItemMatcherBuilderBase qq_12, ClassInventoryItemMatcherBuilderConstructorMarker q0_02) {
        this(qq_12);
    }

    public InventoryMatcherListMode getListMode() {
        return this.listMode;
    }

    public ClassInventoryItemMatcherBuilder withListMode(InventoryMatcherListMode listMode) {
        this.listMode = listMode;
        return this;
    }

    public ClassInventoryItemMatcherBuilder addClass(Class<?> clazz) {
        this.classes.add(clazz);
        return this;
    }

    public ClassInventoryItemMatcher build() {
        return new ClassInventoryItemMatcher(this);
    }

    public List<Class<?>> getClasses() {
        return this.classes;
    }

    private ClassInventoryItemMatcherBuilder(InventoryItemMatcherBuilderBase<?> qq_12) {
        super(qq_12);
    }
}
