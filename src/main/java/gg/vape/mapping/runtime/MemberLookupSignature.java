package gg.vape.mapping.runtime;

public class MemberLookupSignature {
    public final String runtimeName;
    public final Class<?> resolvedType;
    public final Class<?>[] parameterTypes;
    private final Boolean mappedMemberOverride;

    public MemberLookupSignature(String runtimeName, Boolean mappedMemberOverride, Class<?> resolvedType, Class<?> ... parameterTypes) {
        this.runtimeName = runtimeName;
        this.resolvedType = resolvedType;
        this.parameterTypes = parameterTypes;
        this.mappedMemberOverride = mappedMemberOverride;
    }

    public Boolean getMappedMemberOverride() {
        return this.mappedMemberOverride;
    }
}
