package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingProfileSnapshotRegistry;
import gg.vape.mapping.NativeMappedMemberInvoker;
import gg.vape.mapping.runtime.MemberLookupSignature;
import gg.vape.mapping.runtime.RuntimeNameMappingRegistry;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.lang.reflect.Field;

public class MappingField {
    private final String originalName;
    private boolean resolutionFailed;
    private final Class<?> ownerClass;
    private final String ownerInternalName;
    private final Class<?> resolvedFieldType;
    private final boolean staticField;
    private static int[] controlFlowState;
    private final Mapping mappingOwner;
    private final String mappedDescriptor;
    private final Class<?> declaredFieldType;
    private final String descriptor;
    private static int nextFieldId;
    private Field reflectedField;
    private final String runtimeName;
    private final int fieldId;
    private final boolean mappedMember;
    private boolean secondaryMember;

    public void setBoolean(Object object, boolean bl) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.setBoolean(object, bl);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setBooleanField(this.fieldId, object, bl));
    }

    public float getFloat(Object object) {
        if (this.reflectedField != null) {
            try {
                return this.reflectedField.getFloat(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return MappingField.unchecked(() -> NativeMappedMemberInvoker.getFloatField(this.fieldId, object));
    }

    public char[] getCharArray(Object object) {
        if (this.reflectedField != null) {
            try {
                return (char[])this.reflectedField.get(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return NativeMappedMemberInvoker.getCharArrayField(this.fieldId, object);
    }

    public double[] getDoubleArray(Object object) {
        if (this.reflectedField != null) {
            try {
                return (double[])this.reflectedField.get(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return MappingField.unchecked(() -> NativeMappedMemberInvoker.getDoubleArrayField(this.fieldId, object));
    }

    public void setFloatArray(Object object, float[] fArray) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.set(object, fArray);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setFloatArrayField(this.fieldId, object, fArray));
    }

    @FunctionalInterface
    private interface CheckedSupplier<T> {
        T get() throws Exception;
    }

    @FunctionalInterface
    private interface CheckedRunnable {
        void run() throws Exception;
    }

    private static <T> T unchecked(CheckedSupplier<T> supplier) {
        try {
            return supplier.get();
        }
        catch (Exception exception) {
            return MappingField.<RuntimeException, T>sneakyThrow(exception);
        }
    }

    private static void unchecked(CheckedRunnable runnable) {
        try {
            runnable.run();
        }
        catch (Exception exception) {
            MappingField.<RuntimeException, Void>sneakyThrow(exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable, T> T sneakyThrow(Throwable throwable) throws E {
        throw (E)throwable;
    }

    public void setInt(Object object, int n) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.setInt(object, n);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setIntField(this.fieldId, object, n));
    }

    public Mapping getMappingOwner() {
        return this.mappingOwner;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public byte[] getByteArray(Object object) {
        if (this.reflectedField != null) {
            try {
                return (byte[])this.reflectedField.get(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return NativeMappedMemberInvoker.getByteArrayField(this.fieldId, object);
    }

    public void setChar(Object object, char c) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.setChar(object, c);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setCharField(this.fieldId, object, c));
    }

    public MappingField resolveReflectedField() {
        if ((Vape.INSTANCE.isMappingsRemapped() || Vape.INSTANCE.isForgeRemapInactive()) && !this.resolutionFailed) {
            this.reflectedField = this.findReflectedField();
        }
        return this;
    }

    public boolean getBoolean(Object object) {
        if (this.reflectedField != null) {
            try {
                return this.reflectedField.getBoolean(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return MappingField.unchecked(() -> NativeMappedMemberInvoker.getBooleanField(this.fieldId, object));
    }

    public void setLong(Object object, long l) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.setLong(object, l);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setLongField(this.fieldId, object, l));
    }

    public Field findReflectedField() {
        try {
            String string = this.getResolvedName();
            for (Class<?> clazz = this.ownerClass; clazz != null; clazz = clazz.getSuperclass()) {
                try {
                    Field field = clazz.getDeclaredField(string);
                    field.setAccessible(true);
                    return field;
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    continue;
                }
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
        return null;
    }

    public static void setControlFlowState(int[] state) {
        controlFlowState = state;
    }

    public void setShort(Object object, short s) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.setShort(object, s);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setShortField(this.fieldId, object, s));
    }

    public void setDouble(Object object, double d) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.setDouble(object, d);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        NativeMappedMemberInvoker.setDoubleField(this.fieldId, object, d);
    }

    public Object getObject(Object object) {
        if (this.reflectedField != null) {
            try {
                return this.reflectedField.get(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return NativeMappedMemberInvoker.getObjectField(this.fieldId, object);
    }

    public boolean isStaticField() {
        return this.staticField;
    }

    public void setFloat(Object object, float f) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.setFloat(object, f);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setFloatField(this.fieldId, object, f));
    }

    public short getShort(Object object) {
        if (this.reflectedField != null) {
            try {
                return this.reflectedField.getShort(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return MappingField.unchecked(() -> NativeMappedMemberInvoker.getShortField(this.fieldId, object));
    }

    public void setDoubleArray(Object object, double[] dArray) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.set(object, dArray);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setDoubleArrayField(this.fieldId, object, dArray));
    }

    public String getRuntimeName() {
        return this.runtimeName;
    }

    public MappingField register() {
        try {
            if (Vape.INSTANCE.isForgeRemapInactive()) {
                this.resolveReflectedField();
                if (this.reflectedField == null) {
                    throw new NoSuchFieldException(this.ownerClass.getName() + "#" + this.runtimeName);
                }
            } else if (this.mappedMember) {
                NativeMappedMemberInvoker.registerFieldWithMetadata(this.fieldId, this.ownerClass, this.ownerInternalName, this.runtimeName, this.descriptor, this.mappedDescriptor, this.staticField);
                this.resolveReflectedField();
            } else {
                NativeMappedMemberInvoker.registerField(this.fieldId, this.ownerClass, this.runtimeName, this.descriptor, this.staticField);
                this.resolveReflectedField();
            }
        }
        catch (Throwable throwable) {
            if (this.secondaryMember) {
                MappingProfileSnapshotRegistry.j();
            } else {
                MappingProfileSnapshotRegistry.n(this);
            }
            this.resolutionFailed = true;
        }
        return this;
    }

    public MappingField(Mapping mappingOwner, Class<?> ownerClass, String fieldName, boolean mappedMember, boolean staticField, boolean secondaryMember, Class<?> fieldType, int arrayDimensions) {
        String currentControlFlowMarker = MappingMethod.getControlFlowMarker();
        if (currentControlFlowMarker != null) {
            if (ForgeVersion.MC_26_1.d()) {
                mappedMember = false;
            }
            this.mappingOwner = mappingOwner;
            this.originalName = fieldName;
            this.declaredFieldType = fieldType;
            MemberLookupSignature memberLookupSignature = RuntimeNameMappingRegistry.lookupFieldMapping(ownerClass, fieldName);
            if (memberLookupSignature != null) {
                this.runtimeName = memberLookupSignature.runtimeName;
                this.resolvedFieldType = memberLookupSignature.resolvedType != null ? memberLookupSignature.resolvedType : fieldType;
            } else {
                this.runtimeName = fieldName;
                this.resolvedFieldType = fieldType;
            }
            this.descriptor = MappingField.buildFieldDescriptor(this.resolvedFieldType, false, arrayDimensions);
            this.mappedDescriptor = MappingField.buildFieldDescriptor(this.resolvedFieldType, true, arrayDimensions);
            this.mappedMember = mappedMember;
            this.ownerClass = ownerClass;
            this.ownerInternalName = MappedClasses.b(ownerClass).replace(".", "/");
            this.staticField = staticField;
            this.fieldId = ++nextFieldId;
            this.secondaryMember = secondaryMember;
            if (GuiComponent.getLegacyComponentState() == null) {
                MappingMethod.setControlFlowMarker("E1SOdc");
            }
            return;
        }
        this.mappingOwner = null;
        this.originalName = null;
        this.runtimeName = null;
        this.declaredFieldType = null;
        this.resolvedFieldType = fieldType;
        this.descriptor = MappingField.buildFieldDescriptor(this.resolvedFieldType, false, arrayDimensions);
        this.mappedDescriptor = MappingField.buildFieldDescriptor(this.resolvedFieldType, true, arrayDimensions);
        this.mappedMember = mappedMember;
        this.ownerClass = ownerClass;
        this.ownerInternalName = MappedClasses.b(ownerClass).replace(".", "/");
        this.staticField = staticField;
        this.fieldId = ++nextFieldId;
        this.secondaryMember = secondaryMember;
        if (GuiComponent.getLegacyComponentState() == null) {
            MappingMethod.setControlFlowMarker("E1SOdc");
        }
    }

    public String getResolvedName() {
        if (Vape.INSTANCE.isForgeRemapInactive()) {
            return this.runtimeName;
        }
        return NativeMappedMemberInvoker.getFieldName(this.fieldId);
    }

    public void setIntArray(Object object, int[] nArray) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.set(object, nArray);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setIntArrayField(this.fieldId, object, nArray));
    }

    public void setShortArray(Object object, short[] sArray) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.set(object, sArray);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setShortArrayField(this.fieldId, object, sArray));
    }

    public char getChar(Object object) {
        if (this.reflectedField != null) {
            try {
                return this.reflectedField.getChar(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return MappingField.unchecked(() -> NativeMappedMemberInvoker.getCharField(this.fieldId, object));
    }

    public Class<?> getDeclaredFieldType() {
        return this.declaredFieldType;
    }

    public int getInt(Object object) {
        if (this.reflectedField != null) {
            try {
                return this.reflectedField.getInt(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return NativeMappedMemberInvoker.getIntField(this.fieldId, object);
    }

    public short[] getShortArray(Object object) {
        if (this.reflectedField != null) {
            try {
                return (short[])this.reflectedField.get(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return MappingField.unchecked(() -> NativeMappedMemberInvoker.getShortArrayField(this.fieldId, object));
    }

    public float[] getFloatArray(Object object) {
        if (this.reflectedField != null) {
            try {
                return (float[])this.reflectedField.get(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return MappingField.unchecked(() -> NativeMappedMemberInvoker.getFloatArrayField(this.fieldId, object));
    }

    public void setObjectArray(Object object, Object[] objectArray) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.set(object, objectArray);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setObjectArrayField(this.fieldId, object, objectArray));
    }

    public boolean hasResolutionFailed() {
        return this.resolutionFailed;
    }

    public boolean isMappedMember() {
        return this.mappedMember;
    }

    public void setBooleanArray(Object object, boolean[] blArray) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.set(object, blArray);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setBooleanArrayField(this.fieldId, object, blArray));
    }

    public int[] getIntArray(Object object) {
        if (this.reflectedField != null) {
            try {
                return (int[])this.reflectedField.get(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return MappingField.unchecked(() -> NativeMappedMemberInvoker.getIntArrayField(this.fieldId, object));
    }

    public double getDouble(Object object) {
        if (this.reflectedField != null) {
            try {
                return this.reflectedField.getDouble(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return MappingField.unchecked(() -> NativeMappedMemberInvoker.getDoubleField(this.fieldId, object));
    }

    public long getLong(Object object) {
        if (this.reflectedField != null) {
            try {
                return this.reflectedField.getLong(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return MappingField.unchecked(() -> NativeMappedMemberInvoker.getLongField(this.fieldId, object));
    }

    public boolean[] getBooleanArray(Object object) {
        if (this.reflectedField != null) {
            try {
                return (boolean[])this.reflectedField.get(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return MappingField.unchecked(() -> NativeMappedMemberInvoker.getBooleanArrayField(this.fieldId, object));
    }

    public long[] getLongArray(Object object) {
        if (this.reflectedField != null) {
            try {
                return (long[])this.reflectedField.get(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return MappingField.unchecked(() -> NativeMappedMemberInvoker.getLongArrayField(this.fieldId, object));
    }

    public static int[] getControlFlowState() {
        return controlFlowState;
    }

    private static String buildFieldDescriptor(Class<?> fieldType, boolean mapped, int arrayDimensions) {
        String descriptor = DescUtils.getDescriptor(fieldType, mapped);
        if (arrayDimensions > 0) {
            StringBuilder arrayPrefix = new StringBuilder();
            for (int dimension = 0; dimension < arrayDimensions; ++dimension) {
                arrayPrefix.append("[");
            }
            descriptor = arrayPrefix + descriptor;
        }
        return descriptor;
    }

    public void setObject(Object object, Object object2) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.set(object, object2);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setObjectField(this.fieldId, object, object2));
    }

    public static MappingField fromBuilder(MappingFieldBuilder builder) {
        MappingField mappingField = new MappingField(builder.getMappingOwner(), builder.getOwnerClass(), builder.getMemberName(), builder.isMappedMember(), builder.isStaticMember(), builder.isSecondaryMember(), builder.getType(), builder.getArrayDimensions());
        return mappingField.register();
    }

    public void setCharArray(Object object, char[] cArray) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.set(object, cArray);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setCharArrayField(this.fieldId, object, cArray));
    }

    public Object[] getObjectArray(Object object) {
        if (this.reflectedField != null) {
            try {
                return (Object[])this.reflectedField.get(object);
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        return NativeMappedMemberInvoker.getObjectArrayField(this.fieldId, object);
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public void setLongArray(Object object, long[] lArray) {
        if (this.reflectedField != null) {
            try {
                this.reflectedField.set(object, lArray);
                return;
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
        MappingField.unchecked(() -> NativeMappedMemberInvoker.setLongArrayField(this.fieldId, object, lArray));
    }

    static {
        nextFieldId = 0;
        MappingField.setControlFlowState(new int[3]);
    }

    public Class<?> getResolvedFieldType() {
        return this.resolvedFieldType;
    }

    public Class<?> getOwnerClass() {
        return this.ownerClass;
    }
}
