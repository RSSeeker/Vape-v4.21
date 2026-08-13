package gg.vape.mapping.expr;

import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappingMethod;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class MethodCallReplacementExprEditor
extends ExprEditor {
    final JavassistMappingTask u;
    final String V;
    final MappingMethod w;

    public MethodCallReplacementExprEditor(JavassistMappingTask javassistMappingTask, MappingMethod mappingMethod, String string) {
        this.u = javassistMappingTask;
        this.w = mappingMethod;
        this.V = string;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (methodCall.getMethodName().equals(this.w.getResolvedName()) && methodCall.getSignature().equals(this.w.getDescriptor())) {
            methodCall.replace(this.V);
        }
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }
}
