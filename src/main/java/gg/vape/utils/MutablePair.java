package gg.vape.utils;

public class MutablePair<First, Second> {
    private First first;
    private Second second;

    public First getFirst() {
        return this.first;
    }

    public MutablePair(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    public MutablePair<First, Second> setFirst(First first) {
        this.first = first;
        return this;
    }

    public Second getSecond() {
        return this.second;
    }

    public MutablePair<First, Second> setSecond(Second second) {
        this.second = second;
        return this;
    }
}
