package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMaterial
extends Mapping {
    public MappingField airField;
    private static int[] constructorState;
    private final MappingMethod isLiquidMethod;
    private MappingField fireField;
    public final MappingMethod isReplaceableMethod;
    public final MappingMethod isSolidMethod;
    private MappingField waterField;
    public final MappingMethod blocksMovementMethod;
    private MappingMethod isToolNotRequiredMethod;
    private MappingField vineField;

    public boolean blocksMovement(Object material) {
        return this.blocksMovementMethod.invokeBoolean(material, new Object[0]);
    }

    public static int[] getConstructorState() {
        return constructorState;
    }

    public boolean isSolid(Object material) {
        return this.isSolidMethod.invokeBoolean(material, new Object[0]);
    }

    public boolean isReplaceable(Object material) {
        return this.isReplaceableMethod.invokeBoolean(material, new Object[0]);
    }

    public boolean isToolNotRequired(Object material) {
        return this.isToolNotRequiredMethod.invokeBoolean(material, new Object[0]);
    }


    public MMaterial() {
        this(MMaterial.getConstructorState());
    }

    private MMaterial(int[] constructorState) {
        super(MappedClasses.ZN);
        int[] unusedConstructorState = constructorState;
        if (ForgeVersion.MC_1_20_6.v()) {
            if (ForgeVersion.MC_1_12_2.d()) {
                Class materialClass = MappedClasses.ZN;
                boolean remapField = true;
                String airFieldName = "AIR";
                MMaterial mappings = this;
                this.airField = mappings.registerStaticField(airFieldName, remapField, materialClass);
                Class waterOwner = MappedClasses.ZN;
                boolean remapWaterField = true;
                String waterFieldName = "WATER";
                MMaterial waterMappings = this;
                this.waterField = waterMappings.registerStaticField(waterFieldName, remapWaterField, waterOwner);
                Class fireOwner = MappedClasses.ZN;
                boolean remapFireField = true;
                String fireFieldName = "FIRE";
                MMaterial fireMappings = this;
                this.fireField = fireMappings.registerStaticField(fireFieldName, remapFireField, fireOwner);
                if (ForgeVersion.MC_1_16_5.d()) {
                    Class vineOwner = MappedClasses.ZN;
                    boolean remapVineField = true;
                    String vineFieldName = "TALL_PLANTS";
                    MMaterial vineMappings = this;
                    this.vineField = vineMappings.registerStaticField(vineFieldName, remapVineField, vineOwner);
                } else {
                    Class vineOwner = MappedClasses.ZN;
                    boolean remapVineField = true;
                    String vineFieldName = "VINE";
                    MMaterial vineMappings = this;
                    this.vineField = vineMappings.registerStaticField(vineFieldName, remapVineField, vineOwner);
                }
            } else {
                Class materialClass = MappedClasses.ZN;
                boolean remapField = true;
                String airFieldName = "air";
                MMaterial mappings = this;
                this.airField = mappings.registerStaticField(airFieldName, remapField, materialClass);
                Class waterOwner = MappedClasses.ZN;
                boolean remapWaterField = true;
                String waterFieldName = "water";
                MMaterial waterMappings = this;
                this.waterField = waterMappings.registerStaticField(waterFieldName, remapWaterField, waterOwner);
                Class vineOwner = MappedClasses.ZN;
                boolean remapVineField = true;
                String vineFieldName = "vine";
                MMaterial vineMappings = this;
                this.vineField = vineMappings.registerStaticField(vineFieldName, remapVineField, vineOwner);
                Class fireOwner = MappedClasses.ZN;
                boolean remapFireField = true;
                String fireFieldName = "fire";
                MMaterial fireMappings = this;
                this.fireField = fireMappings.registerStaticField(fireFieldName, remapFireField, fireOwner);
            }
        }
        if (ForgeVersion.MC_1_16_5.v()) {
            Class[] parameterTypes = new Class[]{};
            Class<Boolean> returnType = Boolean.TYPE;
            boolean remapMethod = true;
            String methodName = "isToolNotRequired";
            MMaterial mappings = this;
            this.isToolNotRequiredMethod = mappings.Y(methodName, remapMethod, returnType, parameterTypes);
        }
        Class[] replaceableParameterTypes = new Class[]{};
        Class<Boolean> replaceableReturnType = Boolean.TYPE;
        boolean remapReplaceableMethod = true;
        String replaceableMethodName = "isReplaceable";
        MMaterial replaceableMappings = this;
        this.isReplaceableMethod = replaceableMappings.Y(replaceableMethodName, remapReplaceableMethod, replaceableReturnType, replaceableParameterTypes);
        Class[] blocksMovementParameterTypes = new Class[]{};
        Class<Boolean> blocksMovementReturnType = Boolean.TYPE;
        boolean remapBlocksMovementMethod = true;
        String blocksMovementMethodName = "blocksMovement";
        MMaterial blocksMovementMappings = this;
        this.blocksMovementMethod = blocksMovementMappings.Y(blocksMovementMethodName, remapBlocksMovementMethod, blocksMovementReturnType, blocksMovementParameterTypes);
        Class[] solidParameterTypes = new Class[]{};
        Class<Boolean> solidReturnType = Boolean.TYPE;
        boolean remapSolidMethod = true;
        String solidMethodName = "isSolid";
        MMaterial solidMappings = this;
        this.isSolidMethod = solidMappings.Y(solidMethodName, remapSolidMethod, solidReturnType, solidParameterTypes);
        Class[] liquidParameterTypes = new Class[]{};
        Class<Boolean> liquidReturnType = Boolean.TYPE;
        boolean remapLiquidMethod = true;
        String liquidMethodName = "isLiquid";
        MMaterial liquidMappings = this;
        this.isLiquidMethod = liquidMappings.Y(liquidMethodName, remapLiquidMethod, liquidReturnType, liquidParameterTypes);
    }

    public boolean isLiquid(Object material) {
        return this.isLiquidMethod.invokeBoolean(material, new Object[0]);
    }

    public static void setConstructorState(int[] state) {
        constructorState = state;
    }

    static {
        MMaterial.setConstructorState(null);
    }

    public Object getAir() {
        return this.airField.getObject(null);
    }

    public Object getFire() {
        return this.fireField.getObject(null);
    }

    public Object getWater() {
        return this.waterField.getObject(null);
    }

    public Object getVine() {
        return this.vineField.getObject(null);
    }
}

