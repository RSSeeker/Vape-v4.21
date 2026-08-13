package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class MChunk
extends Mapping {
    private MappingField k;
    private MappingMethod c;
    private MappingField O;
    private final MappingField p;
    private MappingField r;
    private MappingMethod N;
    private MappingField q;
    private MappingMethod o;

    public int M(Object object) {
        return this.k.getInt(object);
    }

    public int A(Object object) {
        return this.O.getInt(object);
    }

    public Object[] D(Object object) {
        return this.r.getObjectArray(object);
    }

    public Object N(Object object, Object object2, Object object3) {
        return this.N.invokeObject(object, object2, object3);
    }


    public boolean c(Object object) {
        return this.p.getBoolean(object);
    }

    public Object D(Object object, int n, int n2, Object object2) {
        return this.N.invokeObject(object, n, n2, object2);
    }

    public Object j(Object object) {
        return this.q.getObject(object);
    }

    public int I(Object object, int n) {
        return this.o.invokeInt(object, n);
    }

    public MChunk() {
        this(MTickingBlockEntity.getTickingBlockEntityControlFlowState());
    }

    private MChunk(int[] nArray) {
        super(MappedClasses.VJ);
        int[] nArray2 = nArray;
        if (ForgeVersion.MC_1_8_9.L()) {
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "xPosition";
            MChunk mChunk = this;
            this.k = mChunk.J(string, bl, clazz);
            Class<Integer> clazz2 = Integer.TYPE;
            boolean bl2 = true;
            String string2 = "zPosition";
            MChunk mChunk2 = this;
            this.O = this.J(string2, bl2, clazz2);
        }
        if (ForgeVersion.MC_1_7_10.L()) {
            Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE, MappedClasses.FU};
            Class clazz = MappedClasses.uK;
            boolean bl = true;
            String string = "getBiomeGenForWorldCoords";
            MChunk mChunk = this;
            this.N = mChunk.Y(string, bl, clazz, classArray);
        } else {
            if (ForgeVersion.MC_1_16_5.v()) {
                Class[] classArray = new Class[]{MappedClasses.lf, MappedClasses.FU};
                Class clazz = MappedClasses.uK;
                boolean bl = true;
                String string = "getBiome";
                MChunk mChunk = this;
                this.N = mChunk.Y(string, bl, clazz, classArray);
            }
            Class clazz = MappedClasses.F3;
            String string = "sections";
            MChunk mChunk = this;
            this.r = ((MappingFieldBuilder)((MappingFieldBuilder)mChunk.fieldBuilder(string, clazz).setOwnerClassForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.V)).setNameForVersion(ForgeVersion.MC_1_16_5.b(), "storageArrays")).buildField();
        }
        if (ForgeVersion.MC_1_20_6.v()) {
            Class[] classArray = new Class[]{MappedClasses.zc, MappedClasses.uk, List.class, MappedClasses.lH};
            Class<Void> clazz = Void.TYPE;
            String string = "getEntitiesWithinAABBForEntity";
            MChunk mChunk = this;
            this.c = ((MappingMethodBuilder)mChunk.methodBuilder(string, clazz, classArray).setParameterTypesForVersion(ForgeVersion.MC_1_7_10.S(), MappedClasses.uk, List.class, MappedClasses.Zt).setParameterTypesForVersion(ForgeVersion.MC_1_16_5.n(), MappedClasses.zc, MappedClasses.uk, List.class, Predicate.class).setMappedMemberForVersion(ForgeVersion.MC_1_16_5.n(), Wrapper.isNativeAvailable)).buildMethod();
        } else {
            Class[] classArray = new Class[]{Integer.TYPE};
            Class<Integer> clazz = Integer.TYPE;
            String string = "getSectionIndex";
            MChunk mChunk = this;
        this.o = ((MappingMethodBuilder)mChunk.methodBuilder(string, clazz, classArray).setOwnerClass(MappedClasses.VS)).buildMethod();
        }
        Class<Boolean> clazz = Boolean.TYPE;
        String string = ForgeVersion.c() >= 23 ? "loaded" : "isChunkLoaded";
        MChunk mChunk = this;
        this.p = mChunk.fieldBuilder(string, clazz).buildField();
        if (ForgeVersion.MC_1_17.d()) {
            Class<Map> clazz3 = Map.class;
            String string3 = "blockEntities";
            MChunk mChunk3 = this;
            this.q = ((MappingFieldBuilder)this.fieldBuilder(string3, clazz3).setOwnerClassForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.V)).buildField();
        }
    }

    public void j(Object object, Object object2, Object object3, List list, Object object4) {
        this.c.invokeVoid(object, object2, object3, list, object4);
    }
}

