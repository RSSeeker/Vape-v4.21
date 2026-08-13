package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MTileEntityMobSpawner;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMobSpawnerBaseLogic
extends Mapping {
    private MappingMethod entityNameToSpawnMethod;
    private MappingMethod cachedDisplayEntityMethod;

    public Object getCachedEntity(Object spawnerLogic, Object world) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return this.cachedDisplayEntityMethod.invokeObject(spawnerLogic, world, null);
        }
        if (ForgeVersion.MC_1_17.d()) {
            return this.cachedDisplayEntityMethod.invokeObject(spawnerLogic, world);
        }
        return this.cachedDisplayEntityMethod.invokeObject(spawnerLogic, new Object[0]);
    }

    public String getEntityNameToSpawn(Object spawnerLogic) {
        return (String)this.entityNameToSpawnMethod.invokeObject(spawnerLogic, new Object[0]);
    }

    public MMobSpawnerBaseLogic() {
        this(MTileEntityMobSpawner.getMobSpawnerControlFlowState());
    }

    private MMobSpawnerBaseLogic(int[] controlFlowState) {
        super(MappedClasses.MOB_SPAWNER_LOGIC);
        int[] unusedControlFlowState = controlFlowState;
        if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_20_6.d()) {
                Class[] parameterTypes = new Class[]{MappedClasses.YU, MappedClasses.lf};
                Class returnType = MappedClasses.zc;
                boolean remap = true;
                String methodName = "getOrCreateDisplayEntity";
                this.cachedDisplayEntityMethod = this.Y(methodName, remap, returnType, parameterTypes);
            } else if (ForgeVersion.MC_1_17.d()) {
                Class[] parameterTypes = new Class[]{MappedClasses.YU};
                Class returnType = MappedClasses.zc;
                boolean remap = true;
                String methodName = "getOrCreateDisplayEntity";
                this.cachedDisplayEntityMethod = this.Y(methodName, remap, returnType, parameterTypes);
            } else {
                Class[] parameterTypes = new Class[]{};
                Class returnType = MappedClasses.zc;
                boolean remap = true;
                String methodName = "getCachedEntity";
                this.cachedDisplayEntityMethod = this.Y(methodName, remap, returnType, parameterTypes);
            }
        } else {
            Class[] parameterTypes = new Class[]{};
            Class<String> returnType = String.class;
            boolean remap = true;
            String methodName = "getEntityNameToSpawn";
            this.entityNameToSpawnMethod = this.Y(methodName, remap, returnType, parameterTypes);
        }
    }
}

