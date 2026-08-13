package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.EntityClientPlayerMPMotionMappingTask;
import gg.vape.wrapper.impl.ForgeVersion;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

public class EntityClientPlayerMPMotionExprEditor
extends ExprEditor {
    final boolean A;
    final EntityClientPlayerMPMotionMappingTask j;
    final String I;
    final CtClass S;
    final String R;

    @Override
    public void edit(FieldAccess fieldAccess) throws CannotCompileException {
        try {
            if (fieldAccess.getFieldName().equals(EntityClientPlayerMPMotionMappingTask.j(this.j)) && fieldAccess.getSignature().equals("F") && (!this.A || fieldAccess.getField().getDeclaringClass().equals(this.S))) {
                String string = "$_ = " + this.I + "#getRotationYaw();";
                fieldAccess.replace(string);
                return;
            }
            if (fieldAccess.getFieldName().equals(EntityClientPlayerMPMotionMappingTask.z(this.j)) && fieldAccess.getSignature().equals("F") && (!this.A || fieldAccess.getField().getDeclaringClass().equals(this.S))) {
                String string = "$_ = " + this.I + "#getRotationPitch();";
                fieldAccess.replace(string);
                return;
            }
            if (fieldAccess.getFieldName().equals(EntityClientPlayerMPMotionMappingTask.Q(this.j)) && fieldAccess.getSignature().equals("Z") && (!this.A || fieldAccess.getField().getDeclaringClass().equals(this.S))) {
                String string = "$_ = " + this.I + "#isOnGround();";
                fieldAccess.replace(string);
                return;
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (ForgeVersion.MC_1_16_5.d() && methodCall.getMethodName().equals(this.R)) {
            String string = "$_ = " + this.I + "#getY();";
            methodCall.replace(string);
            return;
        }
    }

    public EntityClientPlayerMPMotionExprEditor(EntityClientPlayerMPMotionMappingTask entityClientPlayerMPMotionMappingTask, String string, String string2, boolean bl, CtClass ctClass) {
        this.j = entityClientPlayerMPMotionMappingTask;
        this.R = string;
        this.I = string2;
        this.A = bl;
        this.S = ctClass;
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}
