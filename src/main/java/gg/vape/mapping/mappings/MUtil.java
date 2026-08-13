package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MUtil
extends Mapping {
    private final MappingMethod glfwPollEventsMethod;
    private final MappingMethod glfwGetCurrentContextMethod;

    public long glfwGetCurrentContext() {
        return this.glfwGetCurrentContextMethod.invokeLong(null, new Object[0]);
    }

    public void glfwPollEvents() {
        this.glfwPollEventsMethod.invokeVoidNoArgs(null);
    }

    public MUtil() {
        super(MappedClasses.zb);
        this.glfwGetCurrentContextMethod = this.registerStaticMethod("glfwGetCurrentContext", false, Long.TYPE, new Class[]{});
        this.glfwPollEventsMethod = this.registerStaticMethod("glfwPollEvents", false, Void.TYPE, new Class[]{});
    }
}

