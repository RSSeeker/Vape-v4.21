package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.function.Consumer;

public class MLevelEntityGetter
extends Mapping {
    private static final String GET_METHOD_NAME = "m_142232_";
    private final MappingMethod getMethod;

    public void forEachEntityInBounds(Object entityGetter, Object boundingBox, Consumer consumer) {
        this.getMethod.invokeVoid(entityGetter, boundingBox, consumer);
    }

    public MLevelEntityGetter() {
        this(MWorldInfo.getWorldInfoConstructorState());
    }

    private MLevelEntityGetter(String[] controlFlowState) {
        super(MappedClasses.LEVEL_ENTITY_GETTER);
        Class[] parameterTypes = new Class[]{MappedClasses.uk, Consumer.class};
        Class<Void> returnType = Void.TYPE;
        boolean remap = ForgeVersion.MC_1_20_6.d();
        String methodName = GET_METHOD_NAME;
        MLevelEntityGetter mappings = this;
        this.getMethod = mappings.Y(methodName, remap, returnType, parameterTypes);
        if (controlFlowState != null) {
            GuiComponent.setLegacyComponentState(new GuiComponent[2]);
        }
    }
}
