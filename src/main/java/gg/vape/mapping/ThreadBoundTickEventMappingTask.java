package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.InjectionParameterSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.ThreadBoundEventPostTickCallback;
import gg.vape.mapping.ThreadBoundEventPreTickCallback;

public class ThreadBoundTickEventMappingTask
extends JavassistMappingTask {
    public ThreadBoundTickEventMappingTask() {
        super(MappedClasses.zY);
    }

    @Override
    public void transform() {
        ThreadBoundTickEventMappingTask.p(ThreadBoundEventPreTickCallback.class);
        ThreadBoundTickEventMappingTask.p(ThreadBoundEventPostTickCallback.class);
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().itemBucketUse.useMethod;
        MappingMethod mappingMethod2 = Vape.INSTANCE.getMappings().CF.p;
        this.k(mappingMethod, mappingMethod2, ThreadBoundEventPreTickCallback.class.getName() + "#call", true, false, new InjectionParameterSpec[0]);
        this.k(mappingMethod, mappingMethod2, ThreadBoundEventPostTickCallback.class.getName() + "#call", false, false, new InjectionParameterSpec[0]);
    }
}
