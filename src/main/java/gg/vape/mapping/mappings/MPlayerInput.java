package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MPlayerInput
extends Mapping {
    private MappingField yField;
    private MappingField xField;

    public static float getY(MPlayerInput mapping, Object vectorHandle) {
        return mapping.readY(vectorHandle);
    }

    private float readY(Object vectorHandle) {
        return this.yField.getFloat(vectorHandle);
    }

    public MPlayerInput() {
        super(MappedClasses.YG);
        Class<Float> xFieldType = Float.TYPE;
        boolean xFieldPublic = true;
        String xFieldName = "x";
        MPlayerInput mapping = this;
        this.xField = mapping.J(xFieldName, xFieldPublic, xFieldType);
        Class<Float> yFieldType = Float.TYPE;
        boolean yFieldPublic = true;
        String yFieldName = "y";
        MPlayerInput yMapping = this;
        this.yField = yMapping.J(yFieldName, yFieldPublic, yFieldType);
    }

    private void writeX(Object vectorHandle, float x) {
        this.xField.setFloat(vectorHandle, x);
    }

    private void writeY(Object vectorHandle, float y) {
        this.yField.setFloat(vectorHandle, y);
    }

    public static float getX(MPlayerInput mapping, Object vectorHandle) {
        return mapping.readX(vectorHandle);
    }

    private float readX(Object vectorHandle) {
        return this.xField.getFloat(vectorHandle);
    }

    public static void setX(MPlayerInput mapping, Object vectorHandle, float x) {
        mapping.writeX(vectorHandle, x);
    }

    public static void setY(MPlayerInput mapping, Object vectorHandle, float y) {
        mapping.writeY(vectorHandle, y);
    }
}

