package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MWorldInfo
extends Mapping {
    private MappingField worldTimeField;
    private static String[] constructorState;
    public MappingMethod getWorldTimeMethod;

    public long getWorldTime(Object worldInfo) {
        return this.worldTimeField.getLong(worldInfo);
    }

    static {
        MWorldInfo.setWorldInfoConstructorState(null);
    }

    public static void setWorldInfoConstructorState(String[] state) {
        constructorState = state;
    }


    public static String[] getWorldInfoConstructorState() {
        return constructorState;
    }

    public MWorldInfo() {
        this(MWorldInfo.getWorldInfoConstructorState());
    }

    private MWorldInfo(String[] constructorState) {
        super(MappedClasses.WORLD_INFO);
        String[] unusedConstructorState = constructorState;
        Class<Long> fieldType = Long.TYPE;
        boolean remapField = true;
        String fieldName = "worldTime";
        MWorldInfo mappings = this;
        this.worldTimeField = mappings.J(fieldName, remapField, fieldType);
        Class[] parameterTypes = new Class[]{};
        Class<Long> returnType = Long.TYPE;
        boolean remapMethod = true;
        String methodName = "getWorldTime";
        this.getWorldTimeMethod = this.Y(methodName, remapMethod, returnType, parameterTypes);
        if (GuiComponent.getLegacyComponentState() == null) {
            MWorldInfo.setWorldInfoConstructorState(new String[1]);
        }
    }
}

