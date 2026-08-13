package gg.vape.module.utility.inventory.cleaner;

import java.util.function.BiPredicate;

public enum StringMatchOperator {
    EQUALS(String::equals),
    STARTS(String::startsWith),
    ENDS(String::endsWith),
    ANY(StringMatchOperator::matchAny);

    private final BiPredicate<String, String> predicate;

    private static boolean matchAny(String value, String pattern) {
        return true;
    }

    private StringMatchOperator(BiPredicate<String, String> predicate) {
        this.predicate = predicate;
    }

    public BiPredicate<String, String> getPredicate() {
        return this.predicate;
    }

}

