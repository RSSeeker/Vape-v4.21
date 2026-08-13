package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MDeltaTracker
extends Mapping {
    private MappingMethod gameTimeDeltaPartialTickMethod;
    private MappingMethod gameTimeDeltaTicksMethod;

    public MDeltaTracker() {
        super(MappedClasses.uy);
        Class[] deltaTicksParameterTypes = new Class[]{};
        Class<Float> deltaTicksReturnType = Float.TYPE;
        boolean deltaTicksPublic = true;
        String deltaTicksMethodName = "getGameTimeDeltaTicks";
        MDeltaTracker mapping = this;
        this.gameTimeDeltaTicksMethod = mapping.Y(deltaTicksMethodName, deltaTicksPublic, deltaTicksReturnType, deltaTicksParameterTypes);
        Class[] partialTickParameterTypes = new Class[]{Boolean.TYPE};
        Class<Float> partialTickReturnType = Float.TYPE;
        boolean partialTickPublic = true;
        String partialTickMethodName = "getGameTimeDeltaPartialTick";
        MDeltaTracker partialTickMapping = this;
        this.gameTimeDeltaPartialTickMethod = partialTickMapping.Y(partialTickMethodName, partialTickPublic, partialTickReturnType, partialTickParameterTypes);
    }

    public static float getGameTimeDeltaTicks(MDeltaTracker mapping, Object deltaTrackerHandle) {
        return mapping.readGameTimeDeltaTicks(deltaTrackerHandle);
    }

    private float readGameTimeDeltaPartialTick(Object deltaTrackerHandle, boolean runsNormally) {
        return this.gameTimeDeltaPartialTickMethod.invokeFloat(deltaTrackerHandle, runsNormally);
    }

    public static float getGameTimeDeltaPartialTick(MDeltaTracker mapping, Object deltaTrackerHandle, boolean runsNormally) {
        return mapping.readGameTimeDeltaPartialTick(deltaTrackerHandle, runsNormally);
    }

    private float readGameTimeDeltaTicks(Object deltaTrackerHandle) {
        return this.gameTimeDeltaTicksMethod.invokeFloat(deltaTrackerHandle, new Object[0]);
    }
}

