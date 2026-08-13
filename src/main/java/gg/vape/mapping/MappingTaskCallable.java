package gg.vape.mapping;

import javassist.CannotCompileException;
import javassist.NotFoundException;

public interface MappingTaskCallable<T> {
    public T c() throws CannotCompileException, NotFoundException;
}

