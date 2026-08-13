package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.RenderBatchFlushCallbackMarker;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class ThreadBoundCallbackMappingTask
extends JavassistMappingTask {
    private static final String c = "#call();}";

    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().glCommandEncoder.drawFromBuffersMethod;
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            ctBehavior.insertBefore("{" + RenderBatchFlushCallbackMarker.class.getName() + c);
        }
        catch (CannotCompileException cannotCompileException) {
            Vape.logThrowable(cannotCompileException);
        }
    }

    public ThreadBoundCallbackMappingTask() {
        super(MappedClasses.zg);
    }
}
