package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MClientWorldInfo
extends Mapping {
    private final MappingMethod setDayTimeMethod;
    private final MappingField gameTimeField;
    private final MappingMethod setGameTimeMethod;

    public MClientWorldInfo() {
        this(MPlayerControllerMP.V());
    }

    private MClientWorldInfo(int initializationState) {
        super(MappedClasses.CLIENT_WORLD_INFO);
        if (initializationState != 0) {
            if (ForgeVersion.MC_26_1.d()) {
                this.setGameTimeMethod = null;
                this.setDayTimeMethod = null;
                Class<Long> fieldType = Long.TYPE;
                boolean remap = true;
                String fieldName = "gameTime";
                MClientWorldInfo mappings = this;
                this.gameTimeField = mappings.J(fieldName, remap, fieldType);
            } else {
                Class[] gameTimeParameterTypes = new Class[]{Long.TYPE};
                Class<Void> gameTimeReturnType = Void.TYPE;
                boolean remapGameTimeMethod = true;
                String gameTimeMethodName = "setGameTime";
                MClientWorldInfo mappings = this;
                this.setGameTimeMethod = mappings.Y(gameTimeMethodName, remapGameTimeMethod, gameTimeReturnType, gameTimeParameterTypes);
                Class[] dayTimeParameterTypes = new Class[]{Long.TYPE};
                Class<Void> dayTimeReturnType = Void.TYPE;
                boolean remapDayTimeMethod = true;
                String dayTimeMethodName = "setDayTime";
                this.setDayTimeMethod = this.Y(dayTimeMethodName, remapDayTimeMethod, dayTimeReturnType, dayTimeParameterTypes);
                this.gameTimeField = null;
            }
            return;
        }
        this.gameTimeField = null;
        this.setGameTimeMethod = null;
        this.setDayTimeMethod = null;
    }

    public void setGameTime(Object worldInfo, long gameTime) {
        if (this.gameTimeField != null) {
            this.gameTimeField.setLong(worldInfo, gameTime);
            return;
        }
        this.setGameTimeMethod.invokeVoid(worldInfo, gameTime);
    }

    public void setDayTime(Object worldInfo, long dayTime) {
        if (this.gameTimeField != null) {
            this.gameTimeField.setLong(worldInfo, dayTime);
            return;
        }
        this.setDayTimeMethod.invokeVoid(worldInfo, dayTime);
    }
}
