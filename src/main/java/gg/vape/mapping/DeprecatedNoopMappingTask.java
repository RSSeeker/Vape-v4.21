package gg.vape.mapping;

import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;

@Deprecated
public class DeprecatedNoopMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
    }

    public DeprecatedNoopMappingTask() {
        super(MappedClasses.DC);
    }
}
