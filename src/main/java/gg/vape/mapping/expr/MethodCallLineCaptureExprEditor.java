package gg.vape.mapping.expr;

import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappingMethod;
import java.util.concurrent.atomic.AtomicInteger;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class MethodCallLineCaptureExprEditor
extends ExprEditor {
    final AtomicInteger M;
    final JavassistMappingTask I;
    final MappingMethod x;

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (methodCall.getMethodName().equals(this.x.getResolvedName()) && methodCall.getSignature().equals(this.x.getDescriptor())) {
            this.M.set(methodCall.getLineNumber());
        }
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    public MethodCallLineCaptureExprEditor(JavassistMappingTask javassistMappingTask, MappingMethod mappingMethod, AtomicInteger atomicInteger) {
        this.I = javassistMappingTask;
        this.x = mappingMethod;
        this.M = atomicInteger;
    }
}
