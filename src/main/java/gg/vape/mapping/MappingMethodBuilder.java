package gg.vape.mapping;

import gg.vape.mapping.MappingMemberBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.module.MinecraftVersionConstraint;
import java.util.HashMap;
import java.util.Map;

public class MappingMethodBuilder
extends MappingMemberBuilder<MappingMethodBuilder, MappingMethod> {
    private boolean skipAccessorGeneration;
    private Class<?>[] defaultParameterTypes;
    private final Map<MinecraftVersionConstraint, Class[]> versionedParameterTypes = new HashMap<MinecraftVersionConstraint, Class[]>();

    public MappingMethodBuilder setSkipAccessorGeneration(boolean skipAccessorGeneration) {
        this.skipAccessorGeneration = skipAccessorGeneration;
        return this;
    }

    public boolean isSkipAccessorGeneration() {
        return this.skipAccessorGeneration;
    }


    public Class<?>[] getParameterTypes() {
        if (!this.versionedParameterTypes.isEmpty()) {
            for (Map.Entry<MinecraftVersionConstraint, Class[]> entry : this.versionedParameterTypes.entrySet()) {
                if (!entry.getKey().y()) continue;
                return entry.getValue();
            }
        }
        return this.defaultParameterTypes;
    }

    public MappingMethodBuilder setParameterTypesForVersion(MinecraftVersionConstraint versionConstraint, Class ... parameterTypes) {
        this.versionedParameterTypes.put(versionConstraint, parameterTypes);
        return this;
    }

    public MappingMethod buildMethod() {
        if (this.shouldSkipBuild()) {
            return null;
        }
        return MappingMethod.fromBuilder(this);
    }

    @Override
    public MappingMethod build() {
        return this.buildMethod();
    }

    public MappingMethodBuilder setParameterTypes(Class<?>[] parameterTypes) {
        this.defaultParameterTypes = parameterTypes;
        return this;
    }
}
