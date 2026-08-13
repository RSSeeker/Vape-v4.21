package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.EntityPlayerSPEventMappingTask;
import gg.vape.wrapper.impl.ForgeVersion;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

public class EntityPlayerSPMotionExprEditor
extends ExprEditor {
    final String B;
    final boolean c;
    final EntityPlayerSPEventMappingTask p;
    final CtClass f;

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (ForgeVersion.MC_1_20_6.v()) {
            return;
        }
        try {
            if (methodCall.getMethodName().equals(EntityPlayerSPEventMappingTask.N(this.p)) && methodCall.getSignature().equals("()F") && methodCall.getMethod().getDeclaringClass().equals(this.f)) {
                String string = "$_ = " + this.B + "#getRotationYaw();";
                methodCall.replace(string);
                return;
            }
            if (methodCall.getMethodName().equals(EntityPlayerSPEventMappingTask.B(this.p)) && methodCall.getSignature().equals("()F") && methodCall.getMethod().getDeclaringClass().equals(this.f)) {
                String string = "$_ = " + this.B + "#getRotationPitch();";
                methodCall.replace(string);
                return;
            }
            if (methodCall.getMethodName().equals(EntityPlayerSPEventMappingTask.T(this.p)) && methodCall.getSignature().equals("()Z") && methodCall.getMethod().getDeclaringClass().equals(this.f)) {
                String string = "$_ = " + this.B + "#isOnGround();";
                methodCall.replace(string);
                return;
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    public EntityPlayerSPMotionExprEditor(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask, CtClass ctClass, String string, boolean bl) {
        this.p = entityPlayerSPEventMappingTask;
        this.f = ctClass;
        this.B = string;
        this.c = bl;
    }

    @Override
    public void edit(FieldAccess fieldAccess) throws CannotCompileException {
        try {
            if (ForgeVersion.MC_1_20_6.d()) {
                return;
            }
            if (fieldAccess.getFieldName().equals(EntityPlayerSPEventMappingTask.O(this.p)) && fieldAccess.getSignature().equals("F") && (!this.c || fieldAccess.getField().getDeclaringClass().equals(this.f))) {
                String string = "$_ = " + this.B + "#getRotationYaw();";
                fieldAccess.replace(string);
                return;
            }
            if (fieldAccess.getFieldName().equals(EntityPlayerSPEventMappingTask.S(this.p)) && fieldAccess.getSignature().equals("F") && (!this.c || fieldAccess.getField().getDeclaringClass().equals(this.f))) {
                String string = "$_ = " + this.B + "#getRotationPitch();";
                fieldAccess.replace(string);
                return;
            }
            if (fieldAccess.getFieldName().equals(EntityPlayerSPEventMappingTask.L(this.p)) && fieldAccess.getSignature().equals("Z") && (!this.c || fieldAccess.getField().getDeclaringClass().equals(this.f))) {
                String string = "$_ = " + this.B + "#isOnGround();";
                fieldAccess.replace(string);
                return;
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}
