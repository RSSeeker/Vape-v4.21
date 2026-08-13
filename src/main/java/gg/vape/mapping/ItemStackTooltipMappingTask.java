package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.ItemStackTooltipCallback;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class ItemStackTooltipMappingTask
extends JavassistMappingTask {

    public ItemStackTooltipMappingTask() {
        super(MappedClasses.VK);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().q8.J;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, ItemStackTooltipCallback.class);
        eventInjectionSpec.setConstructorArguments("$0, $1, $2");
        eventInjectionSpec.setReturnExpression("($r) $event.getTooltip()");
        if (ForgeVersion.MC_1_17.v()) {
            this.registerEventInjection(eventInjectionSpec);
            try {
                this.F(mappingMethod).getMethodInfo().rebuildStackMap(e);
            }
            catch (Exception exception) {
                throw new IllegalStateException("Could not rebuild ItemStack tooltip stack map", exception);
            }
        }
    }
}

