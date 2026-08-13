package gg.vape.mapping.expr;

import gg.vape.event.EventBus;
import gg.vape.event.impl.EventFogDensity;
import gg.vape.mapping.EntityRendererEventMappingTask;
import gg.vape.mapping.MappingMethod;
import java.util.concurrent.atomic.AtomicBoolean;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class EventFogDensityInjectionExprEditor
extends ExprEditor {
    final EntityRendererEventMappingTask u;
    final MappingMethod d;
    final AtomicBoolean o;

    public EventFogDensityInjectionExprEditor(EntityRendererEventMappingTask entityRendererEventMappingTask, MappingMethod mappingMethod, AtomicBoolean atomicBoolean) {
        this.u = entityRendererEventMappingTask;
        this.d = mappingMethod;
        this.o = atomicBoolean;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (methodCall.getMethodName().equals(this.d.getResolvedName()) && methodCall.getSignature().equals(this.d.getDescriptor())) {
            String string = EventFogDensity.class.getName();
            methodCall.replace("$_ = $proceed($$);" + string + " vapeEvent = new " + string + "(0.1f);if (vapeEvent." + EventBus.getFireMethod(EventFogDensity.class).getName() + "()) {return;}");
            this.o.set(true);
        }
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }
}
