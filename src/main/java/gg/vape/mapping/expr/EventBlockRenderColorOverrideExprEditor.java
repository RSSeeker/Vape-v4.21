package gg.vape.mapping.expr;

import gg.vape.mapping.RenderLivingBaseEventMappingTask;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class EventBlockRenderColorOverrideExprEditor
extends ExprEditor {
    final RenderLivingBaseEventMappingTask W;
    final String a;

    public EventBlockRenderColorOverrideExprEditor(RenderLivingBaseEventMappingTask renderLivingBaseEventMappingTask, String string) {
        this.W = renderLivingBaseEventMappingTask;
        this.a = string;
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (methodCall.getMethodName().equals("flip")) {
            methodCall.replace("{" + this.a + "#flip($0);$_ = $proceed($$);}");
        }
    }
}

