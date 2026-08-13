package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;

public class MDirectionAxis
extends Mapping {
    private final MappingMethod chooseMethod;
    private final MappingField xField;
    private final MappingField yField;
    private static final String CHOOSE_METHOD_NAME = "choose";

    public MDirectionAxis() {
        super(MappedClasses.u9);
        Class[] chooseParameterTypes = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
        Class<Double> chooseReturnType = Double.TYPE;
        boolean choosePublic = true;
        String chooseMethodName = CHOOSE_METHOD_NAME;
        MDirectionAxis mapping = this;
        this.chooseMethod = mapping.Y(chooseMethodName, choosePublic, chooseReturnType, chooseParameterTypes);
        Class xFieldType = MappedClasses.u9;
        boolean xFieldPublic = true;
        String xFieldName = "X";
        MDirectionAxis xMapping = this;
        this.xField = xMapping.registerStaticField(xFieldName, xFieldPublic, xFieldType);
        Class yFieldType = MappedClasses.u9;
        boolean yFieldPublic = true;
        String yFieldName = "Y";
        MDirectionAxis yMapping = this;
        this.yField = yMapping.registerStaticField(yFieldName, yFieldPublic, yFieldType);
    }

    private Object readY() {
        return this.yField.getObject(null);
    }

    private Object readX() {
        return this.xField.getObject(null);
    }

    public static Object getX(MDirectionAxis mapping) {
        return mapping.readX();
    }

    public static Object getY(MDirectionAxis mapping) {
        return mapping.readY();
    }

    public double choose(Object axisHandle, double x, double y, double z) {
        return this.chooseMethod.invokeDouble(axisHandle, x, y, z);
    }
}

