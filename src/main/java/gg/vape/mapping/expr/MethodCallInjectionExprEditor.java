package gg.vape.mapping.expr;

import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappingMethod;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class MethodCallInjectionExprEditor
extends ExprEditor {
    final MappingMethod M;
    final JavassistMappingTask G;
    final String f;

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    public MethodCallInjectionExprEditor(JavassistMappingTask javassistMappingTask, MappingMethod mappingMethod, String string) {
        this.G = javassistMappingTask;
        this.M = mappingMethod;
        this.f = string;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (methodCall.getMethodName().equals(this.M.getResolvedName()) && methodCall.getSignature().equals(this.M.getDescriptor())) {
            methodCall.replace(this.f);
        }
    }
}
