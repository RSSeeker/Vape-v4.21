package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.LegacyRenderStringEventRender3DCallback;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class LegacyRenderStringHookMappingTask
extends JavassistMappingTask {
    private static final String c = "#call($1);}";

    public LegacyRenderStringHookMappingTask() {
        super(MappedClasses.Vk);
    }

    @Override
    public void transform() {
        LegacyRenderStringHookMappingTask.p(LegacyRenderStringEventRender3DCallback.class);
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().C5.A;
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            ctBehavior.insertBefore("{" + LegacyRenderStringEventRender3DCallback.class.getName() + c);
        }
        catch (CannotCompileException cannotCompileException) {
            Vape.logThrowable(cannotCompileException);
        }
    }
}
