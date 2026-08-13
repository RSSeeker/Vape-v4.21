package gg.vape.mapping;

import gg.vape.mapping.Mapper;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.ui.click.component.GuiComponent;

public class Mapping {
    private static String[] controlFlowState;
    protected Class mappedClass;

    protected MappingField J(String fieldName, boolean mappedMember, Class fieldType) {
        MappingField mappingField = new MappingField(this, this.mappedClass, fieldName, mappedMember, false, false, fieldType, 0);
        return mappingField.register();
    }

    public Class getMappedClass() {
        return this.mappedClass;
    }

    public static void setControlFlowState(String[] state) {
        controlFlowState = state;
    }

    static {
        Mapping.setControlFlowState(new String[1]);
    }

    protected MappingMethod Y(String string, boolean bl, Class clazz, Class ... classArray) {
        MappingMethod mappingMethod = new MappingMethod(this, this.mappedClass, string, bl, false, false, clazz, classArray);
        return mappingMethod.register();
    }


    protected MappingField registerStaticFieldForOwner(Class ownerClass, String fieldName, boolean mappedMember, Class fieldType) {
        MappingField mappingField = new MappingField(this, ownerClass, fieldName, mappedMember, true, false, fieldType, 0);
        return mappingField.register();
    }

    protected MappingField registerInstanceFieldForOwner(Class ownerClass, String fieldName, boolean mappedMember, Class fieldType) {
        MappingField mappingField = new MappingField(this, ownerClass, fieldName, mappedMember, false, false, fieldType, 0);
        return mappingField.register();
    }

    protected MappingMethod registerStaticMethod(String methodName, boolean mappedMember, Class returnType, Class ... parameterTypes) {
        MappingMethod mappingMethod = new MappingMethod(this, this.mappedClass, methodName, mappedMember, true, false, returnType, parameterTypes);
        return mappingMethod.register();
    }

    protected MappingMethodBuilder methodBuilder(String methodName, Class returnType, Class ... parameterTypes) {
        return ((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)new MappingMethodBuilder().setMappingOwner(this)).setOwnerClass(this.mappedClass)).setMappedMember(true)).setMemberName(methodName)).setType(returnType)).setParameterTypes(parameterTypes);
    }

    public static String[] getControlFlowState() {
        return controlFlowState;
    }

    protected MappingMethod registerConstructor(Class ... classArray) {
        MappingMethod mappingMethod = new MappingMethod(this, this.mappedClass, "<init>", false, false, false, Void.TYPE, classArray);
        return mappingMethod.register();
    }

    public Mapping(Class mappedClass) {
        this.mappedClass = mappedClass;
        Mapper.RF.add(this);
    }

    protected MappingMethodBuilder constructorBuilder(Class ... classArray) {
        String[] state = Mapping.getControlFlowState();
        MappingMethodBuilder mappingMethodBuilder = ((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)new MappingMethodBuilder().setMappingOwner(this)).setOwnerClass(this.mappedClass)).setMappedMember(false)).setMemberName("<init>")).setType(Void.TYPE)).setParameterTypes(classArray);
        if (GuiComponent.getLegacyComponentState() == null) {
            Mapping.setControlFlowState(new String[2]);
        }
        return mappingMethodBuilder;
    }

    protected MappingFieldBuilder fieldBuilder(String fieldName, Class fieldType) {
        return (MappingFieldBuilder)((MappingFieldBuilder)((MappingFieldBuilder)((MappingFieldBuilder)((MappingFieldBuilder)new MappingFieldBuilder().setMappingOwner(this)).setOwnerClass(this.mappedClass)).setMemberName(fieldName)).setMappedMember(true)).setType(fieldType);
    }

    protected MappingMethod registerStaticMethodForOwner(Class ownerClass, String methodName, boolean mappedMember, Class returnType, Class ... parameterTypes) {
        MappingMethod mappingMethod = new MappingMethod(this, ownerClass, methodName, mappedMember, true, false, returnType, parameterTypes);
        return mappingMethod.register();
    }

    protected MappingField registerInstanceFieldWithSecondaryFlag(String fieldName, boolean mappedMember, boolean secondaryMember, Class fieldType) {
        MappingField mappingField = new MappingField(this, this.mappedClass, fieldName, mappedMember, false, secondaryMember, fieldType, 0);
        return mappingField.register();
    }

    protected MappingMethod registerInstanceMethodForOwner(Class ownerClass, String methodName, boolean mappedMember, Class returnType, Class ... parameterTypes) {
        MappingMethod mappingMethod = new MappingMethod(this, ownerClass, methodName, mappedMember, false, false, returnType, parameterTypes);
        return mappingMethod.register();
    }

    protected MappingField registerStaticField(String fieldName, boolean mappedMember, Class fieldType) {
        MappingField mappingField = new MappingField(this, this.mappedClass, fieldName, mappedMember, true, false, fieldType, 0);
        return mappingField.register();
    }
}

