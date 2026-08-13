package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.InsertedCallbackLockMarker;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class GlStateManagerBlendFuncMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Dt.O;
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            String string = "{if(" + InsertedCallbackLockMarker.class.getName() + "#check($1, $2)) {   return;}}";
            ctBehavior.insertBefore(string);
        }
        catch (CannotCompileException cannotCompileException) {
            Vape.logThrowable(cannotCompileException);
        }
    }

    public GlStateManagerBlendFuncMappingTask() {
        super(MappedClasses.K);
    }
}
