package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventVisGraphComputeVisibility;
import gg.vape.event.impl.EventVisGraphSetOpaqueCube;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;

public class VisGraphXRayVisibilityMappingTask
extends JavassistMappingTask {
    private static final String VISIBILITY_RETURN_EXPRESSION = "($r) $event.getVisibility()";

    @Override
    public void transform() {
        MappingMethod computeVisibilityMethod = Vape.INSTANCE.getMappings().visGraph.computeVisibilityMethod;
        EventInjectionSpec computeVisibilityInjection = new EventInjectionSpec(computeVisibilityMethod, EventVisGraphComputeVisibility.class);
        computeVisibilityInjection.setReturnExpression(VISIBILITY_RETURN_EXPRESSION);
        this.registerEventInjection(computeVisibilityInjection);
        MappingMethod setOpaqueCubeMethod = Vape.INSTANCE.getMappings().visGraph.setOpaqueCubeMethod;
        EventInjectionSpec setOpaqueCubeInjection = new EventInjectionSpec(setOpaqueCubeMethod, EventVisGraphSetOpaqueCube.class);
        setOpaqueCubeInjection.setInsertBefore(true);
        this.registerEventInjection(setOpaqueCubeInjection);
    }

    public VisGraphXRayVisibilityMappingTask() {
        super(MappedClasses.VIS_GRAPH);
    }
}
