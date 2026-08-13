package gg.vape.mapping;

public interface MappingTask {
    void transform();

    void rollback();

    Class getTargetClass();

    void prepare();

    boolean isApplied();

    int commit();

    void serialize();
}
