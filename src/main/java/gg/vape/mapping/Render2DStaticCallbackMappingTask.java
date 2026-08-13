package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.EventRender2DStaticCallback;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class Render2DStaticCallbackMappingTask
extends JavassistMappingTask {
    private static final String c = "#call();}";

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_26_1.d()) {
            return;
        }
        Render2DStaticCallbackMappingTask.p(EventRender2DStaticCallback.class);
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().titledScreen.renderHudMethod;
        if (mappingMethod == null) {
            return;
        }
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            ctBehavior.insertAfter("{" + EventRender2DStaticCallback.class.getName() + c);
        }
        catch (CannotCompileException cannotCompileException) {
            Vape.logThrowable(cannotCompileException);
        }
    }

    public Render2DStaticCallbackMappingTask() {
        super(MappedClasses.Zj);
    }
}
