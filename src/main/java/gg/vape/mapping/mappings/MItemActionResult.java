package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MItemActionResult
extends Mapping {
    private final MappingMethod withoutItemMethod;
    private final MappingMethod heldItemTransformedToGetter;
    private final MappingMethod wasItemInteractionMethod;
    private final MappingMethod constructor;
    private final MappingMethod heldItemTransformedToMethod;
    private final MappingMethod consumesActionMethod;

    public boolean consumesAction(Object resultHandle) {
        if (this.consumesActionMethod == null) {
            return true;
        }
        return this.consumesActionMethod.invokeBoolean(resultHandle, new Object[0]);
    }

    public MItemActionResult() {
        this(BlockData.W());
    }

    private MItemActionResult(String[] controlFlowState) {
        super(MappedClasses.zO);
        if (controlFlowState != null) {
            Class[] constructorParameterTypes = new Class[]{MappedClasses.Zi, MappedClasses.lj};
            MItemActionResult mapping = this;
            this.constructor = mapping.registerConstructor(constructorParameterTypes);
            if (ForgeVersion.MC_1_21_10.v()) {
                Class[] consumesActionParameterTypes = new Class[]{};
                Class<Boolean> consumesActionReturnType = Boolean.TYPE;
                boolean consumesActionPublic = true;
                String consumesActionMethodName = "consumesAction";
                MItemActionResult consumesActionMapping = this;
                this.consumesActionMethod = consumesActionMapping.Y(consumesActionMethodName, consumesActionPublic, consumesActionReturnType, consumesActionParameterTypes);
            } else {
                this.consumesActionMethod = null;
            }
            Class[] transformParameterTypes = new Class[]{MappedClasses.VK};
            Class transformReturnType = MappedClasses.zO;
            boolean transformPublic = true;
            String transformMethodName = "heldItemTransformedTo";
            MItemActionResult transformMapping = this;
            this.heldItemTransformedToMethod = transformMapping.Y(transformMethodName, transformPublic, transformReturnType, transformParameterTypes);
            Class[] transformedItemGetterParameterTypes = new Class[]{};
            Class transformedItemGetterReturnType = MappedClasses.VK;
            boolean transformedItemGetterPublic = true;
            String transformedItemGetterName = "heldItemTransformedTo";
            MItemActionResult transformedItemGetterMapping = this;
            this.heldItemTransformedToGetter = transformedItemGetterMapping.Y(transformedItemGetterName, transformedItemGetterPublic, transformedItemGetterReturnType, transformedItemGetterParameterTypes);
            Class[] withoutItemParameterTypes = new Class[]{};
            Class withoutItemReturnType = MappedClasses.zO;
            boolean withoutItemPublic = true;
            String withoutItemMethodName = "withoutItem";
            MItemActionResult withoutItemMapping = this;
            this.withoutItemMethod = withoutItemMapping.Y(withoutItemMethodName, withoutItemPublic, withoutItemReturnType, withoutItemParameterTypes);
            Class[] wasItemInteractionParameterTypes = new Class[]{};
            Class<Boolean> wasItemInteractionReturnType = Boolean.TYPE;
            boolean wasItemInteractionPublic = true;
            String wasItemInteractionMethodName = "wasItemInteraction";
            MItemActionResult wasItemInteractionMapping = this;
            this.wasItemInteractionMethod = wasItemInteractionMapping.Y(wasItemInteractionMethodName, wasItemInteractionPublic, wasItemInteractionReturnType, wasItemInteractionParameterTypes);
            return;
        }
        this.consumesActionMethod = null;
        Class[] transformParameterTypes = new Class[]{MappedClasses.VK};
        Class transformReturnType = MappedClasses.zO;
        boolean transformPublic = true;
        String transformMethodName = "heldItemTransformedTo";
        MItemActionResult mapping = this;
        this.heldItemTransformedToMethod = mapping.Y(transformMethodName, transformPublic, transformReturnType, transformParameterTypes);
        Class[] transformedItemGetterParameterTypes = new Class[]{};
        Class transformedItemGetterReturnType = MappedClasses.VK;
        boolean transformedItemGetterPublic = true;
        String transformedItemGetterName = "heldItemTransformedTo";
        MItemActionResult transformedItemGetterMapping = this;
        this.heldItemTransformedToGetter = transformedItemGetterMapping.Y(transformedItemGetterName, transformedItemGetterPublic, transformedItemGetterReturnType, transformedItemGetterParameterTypes);
        Class[] withoutItemParameterTypes = new Class[]{};
        Class withoutItemReturnType = MappedClasses.zO;
        boolean withoutItemPublic = true;
        String withoutItemMethodName = "withoutItem";
        MItemActionResult withoutItemMapping = this;
        this.withoutItemMethod = withoutItemMapping.Y(withoutItemMethodName, withoutItemPublic, withoutItemReturnType, withoutItemParameterTypes);
        Class[] wasItemInteractionParameterTypes = new Class[]{};
        Class<Boolean> wasItemInteractionReturnType = Boolean.TYPE;
        boolean wasItemInteractionPublic = true;
        String wasItemInteractionMethodName = "wasItemInteraction";
        MItemActionResult wasItemInteractionMapping = this;
        this.wasItemInteractionMethod = wasItemInteractionMapping.Y(wasItemInteractionMethodName, wasItemInteractionPublic, wasItemInteractionReturnType, wasItemInteractionParameterTypes);
        this.constructor = null;
    }

    public Object getHeldItemTransformedTo(Object resultHandle) {
        return this.heldItemTransformedToGetter.invokeObject(resultHandle, new Object[0]);
    }

    public Object create(Object swingSource, Object itemContext) {
        return this.constructor.invokeObject(null, swingSource, itemContext);
    }

    public boolean wasItemInteraction(Object resultHandle) {
        return this.wasItemInteractionMethod.invokeBoolean(resultHandle, new Object[0]);
    }

    public Object heldItemTransformedTo(Object resultHandle, Object itemStack) {
        return this.heldItemTransformedToMethod.invokeObject(resultHandle, itemStack);
    }

    public Object withoutItem(Object resultHandle) {
        return this.withoutItemMethod.invokeObject(resultHandle, new Object[0]);
    }

}

