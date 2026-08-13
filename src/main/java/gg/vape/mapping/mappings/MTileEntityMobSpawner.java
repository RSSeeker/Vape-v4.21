package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTileEntityMobSpawner
extends Mapping {
    private static int[] controlFlowState;
    private final MappingMethod getSpawnerBaseLogicMethod;

    public static int[] getMobSpawnerControlFlowState() {
        return controlFlowState;
    }

    public static void setMobSpawnerControlFlowState(int[] state) {
        controlFlowState = state;
    }

    public Object getSpawnerBaseLogic(Object mobSpawnerTileEntity) {
        return this.getSpawnerBaseLogicMethod.invokeObject(mobSpawnerTileEntity, new Object[0]);
    }

    static {
        MTileEntityMobSpawner.setMobSpawnerControlFlowState(null);
    }


    public MTileEntityMobSpawner() {
        this(MTileEntityMobSpawner.getMobSpawnerControlFlowState());
    }

    private MTileEntityMobSpawner(int[] controlFlowState) {
        super(MappedClasses.MOB_SPAWNER_TILE_ENTITY);
        if (controlFlowState != null) {
            if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                Class[] parameterTypes = new Class[]{};
                Class returnType = MappedClasses.MOB_SPAWNER_LOGIC;
                boolean remap = true;
                String methodName = "getSpawnerBaseLogic";
                this.Y(methodName, remap, returnType, parameterTypes);
            }
            Class[] parameterTypes = new Class[]{};
            Class returnType = MappedClasses.MOB_SPAWNER_LOGIC;
            boolean remap = Wrapper.isNativeAvailable;
            String methodName = "func_145881_a";
            this.getSpawnerBaseLogicMethod = this.Y(methodName, remap, returnType, parameterTypes);
            return;
        }
        if (Vape.INSTANCE.isVanillaMinecraftPresent() && ForgeVersion.MC_1_7_10.Y()) {
            Class[] parameterTypes = new Class[]{};
            Class returnType = MappedClasses.MOB_SPAWNER_LOGIC;
            boolean remap = true;
            String methodName = "getSpawnerBaseLogic";
            this.getSpawnerBaseLogicMethod = this.Y(methodName, remap, returnType, parameterTypes);
        } else {
            Class[] parameterTypes = new Class[]{};
            Class returnType = MappedClasses.MOB_SPAWNER_LOGIC;
            boolean remap = Wrapper.isNativeAvailable;
            String methodName = "func_145881_a";
            this.getSpawnerBaseLogicMethod = this.Y(methodName, remap, returnType, parameterTypes);
        }
    }
}
