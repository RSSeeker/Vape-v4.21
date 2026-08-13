package gg.vape.mapping;

import gg.vape.mapping.EventPostTickCallback;
import gg.vape.mapping.EventPreTickCallback;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MinecraftTickEventMappingTask;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class MinecraftTickCallbackExprEditor
extends ExprEditor {
    final MinecraftTickEventMappingTask a;
    final MappingMethod m;
    final boolean[] W;

    public MinecraftTickCallbackExprEditor(MinecraftTickEventMappingTask minecraftTickEventMappingTask, MappingMethod mappingMethod, boolean[] blArray) {
        this.a = minecraftTickEventMappingTask;
        this.m = mappingMethod;
        this.W = blArray;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (MinecraftTickEventMappingTask.y(methodCall, this.m)) {
            this.W[0] = true;
            methodCall.replace("{" + EventPreTickCallback.class.getName() + "#call();$_ = $proceed($$);" + EventPostTickCallback.class.getName() + "#call();}");
        }
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }
}

