package gg.vape.mapping;

import gg.vape.mapping.Mapping;
import gg.vape.module.MinecraftVersionConstraint;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class MappingMemberBuilder<T extends MappingMemberBuilder, C> {
    private MinecraftVersionConstraint skippedVersionConstraint;
    private String defaultMemberName;
    private boolean defaultMappedMember;
    private Class defaultOwnerClass;
    private boolean staticMember;
    private final Map<MinecraftVersionConstraint, Class<?>> versionedTypes;
    private boolean secondaryMember;
    private static int[] obfuscationState;
    private Mapping mappingOwner;
    private final Map<MinecraftVersionConstraint, Boolean> versionedMappedMemberFlags;
    private Class<?> defaultType;
    private final Map<MinecraftVersionConstraint, String> versionedNames;
    private final Map<MinecraftVersionConstraint, Class> versionedOwnerClasses = new LinkedHashMap<MinecraftVersionConstraint, Class>();

    public static void setObfuscationState(int[] state) {
        obfuscationState = state;
    }

    public MappingMemberBuilder() {
        this.versionedNames = new LinkedHashMap<MinecraftVersionConstraint, String>();
        this.versionedMappedMemberFlags = new LinkedHashMap<MinecraftVersionConstraint, Boolean>();
        this.versionedTypes = new LinkedHashMap();
    }

    public T setStaticMember(boolean staticMember) {
        this.staticMember = staticMember;
        return (T)this;
    }

    public String getMemberName() {
        if (!this.versionedNames.isEmpty()) {
            for (Map.Entry<MinecraftVersionConstraint, String> entry : this.versionedNames.entrySet()) {
                if (!entry.getKey().y()) continue;
                return entry.getValue();
            }
        }
        return this.defaultMemberName;
    }

    public abstract C build();

    public T setTypeForVersion(MinecraftVersionConstraint versionConstraint, Class<?> type) {
        this.versionedTypes.put(versionConstraint, type);
        return (T)this;
    }

    public Class<?> getType() {
        if (!this.versionedTypes.isEmpty()) {
            for (Map.Entry<MinecraftVersionConstraint, Class<?>> entry : this.versionedTypes.entrySet()) {
                if (!entry.getKey().y()) continue;
                return entry.getValue();
            }
        }
        return this.defaultType;
    }

    public Mapping getMappingOwner() {
        return this.mappingOwner;
    }

    public static int[] getObfuscationState() {
        return obfuscationState;
    }

    public T setMemberName(String memberName) {
        this.defaultMemberName = memberName;
        return (T)this;
    }


    public T setNameForVersion(MinecraftVersionConstraint versionConstraint, String memberName) {
        this.versionedNames.put(versionConstraint, memberName);
        return (T)this;
    }

    public T setSecondaryMember(boolean secondaryMember) {
        this.secondaryMember = secondaryMember;
        return (T)this;
    }

    public T skipForVersion(MinecraftVersionConstraint versionConstraint) {
        this.skippedVersionConstraint = versionConstraint;
        return (T)this;
    }

    public T setOwnerClassForVersion(MinecraftVersionConstraint versionConstraint, Class ownerClass) {
        this.versionedOwnerClasses.put(versionConstraint, ownerClass);
        return (T)this;
    }

    public boolean shouldSkipBuild() {
        return this.skippedVersionConstraint != null && this.skippedVersionConstraint.y();
    }

    static {
        if (MappingMemberBuilder.getObfuscationState() != null) {
            MappingMemberBuilder.setObfuscationState(new int[3]);
        }
    }

    public boolean isMappedMember() {
        if (!this.versionedMappedMemberFlags.isEmpty()) {
            for (Map.Entry<MinecraftVersionConstraint, Boolean> entry : this.versionedMappedMemberFlags.entrySet()) {
                if (!entry.getKey().y()) continue;
                return entry.getValue();
            }
        }
        return this.defaultMappedMember;
    }

    public Class getOwnerClass() {
        if (!this.versionedOwnerClasses.isEmpty()) {
            for (Map.Entry<MinecraftVersionConstraint, Class> entry : this.versionedOwnerClasses.entrySet()) {
                if (!entry.getKey().y()) continue;
                return entry.getValue();
            }
        }
        return this.defaultOwnerClass;
    }

    public boolean isSecondaryMember() {
        return this.secondaryMember;
    }

    public T setMappedMemberForVersion(MinecraftVersionConstraint versionConstraint, boolean mappedMember) {
        this.versionedMappedMemberFlags.put(versionConstraint, mappedMember);
        return (T)this;
    }

    public T setMappingOwner(Mapping mappingOwner) {
        this.mappingOwner = mappingOwner;
        return (T)this;
    }

    public boolean isStaticMember() {
        return this.staticMember;
    }

    public T setType(Class<?> type) {
        this.defaultType = type;
        return (T)this;
    }

    public T setOwnerClass(Class ownerClass) {
        this.defaultOwnerClass = ownerClass;
        return (T)this;
    }

    public T setMappedMember(boolean mappedMember) {
        this.defaultMappedMember = mappedMember;
        return (T)this;
    }
}

