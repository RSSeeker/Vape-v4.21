package gg.vape.asm.transform;

import org.objectweb.asm.ClassWriter;

public class TransformClassWriter
extends ClassWriter {
    public TransformClassWriter(int flags) {
        super(flags);
    }

    @Override
    protected String getCommonSuperClass(String firstType, String secondType) {
        return super.getCommonSuperClass(firstType, secondType);
    }
}
