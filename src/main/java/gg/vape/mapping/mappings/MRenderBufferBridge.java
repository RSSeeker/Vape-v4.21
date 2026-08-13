package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.GpuTextureView;
import gg.vape.wrapper.impl.BlockStateContainerBridge;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ScaledResolution;
import java.util.Map;

public class MRenderBufferBridge
extends Mapping {
    public MappingMethod z;
    private MappingMethod H;
    private MappingField r;
    private MappingMethod e;
    private MappingField K;

    private Object I(Object object) {
        return this.K.getObject(object);
    }

    public static Map P$src$Ljava_util_Map_$14yo47i(MRenderBufferBridge mRenderBufferBridge, Object object) {
        return mRenderBufferBridge.o(object);
    }

    private Object c(Object object) {
        return this.K.getObject(object);
    }

    public static void v(MRenderBufferBridge mRenderBufferBridge, Object object) {
        mRenderBufferBridge.T(object);
    }

    public static Object P(MRenderBufferBridge mRenderBufferBridge, Object object) {
        return mRenderBufferBridge.I(object);
    }

    private void T(Object object) {
        this.H.invokeVoidNoArgs(object);
    }


    public MRenderBufferBridge() {
        this(ScaledResolution.q());
    }

    private MRenderBufferBridge(int n) {
        super(MappedClasses.w);
        if (n != 0) {
            Class[] classArray = new Class[]{MappedClasses.qk};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "render";
            MRenderBufferBridge mRenderBufferBridge = this;
            this.z = mRenderBufferBridge.Y(string, bl, clazz, classArray);
            if (ForgeVersion.MC_26_1.d()) {
                Class clazz2 = MappedClasses.GPU_TEXTURE;
                boolean bl2 = true;
                String string2 = "itemsAtlas";
                MRenderBufferBridge mRenderBufferBridge2 = this;
                this.K = this.J(string2, bl2, clazz2);
                Class<Map> clazz3 = Map.class;
                boolean bl3 = true;
                String string3 = "atlasPositions";
                MRenderBufferBridge mRenderBufferBridge3 = this;
                this.r = this.J(string3, bl3, clazz3);
                Class[] classArray2 = new Class[]{Integer.TYPE};
                Class<Integer> clazz4 = Integer.TYPE;
                boolean bl4 = true;
                String string4 = "calculateAtlasSizeInPixels";
                MRenderBufferBridge mRenderBufferBridge4 = this;
                this.e = this.Y(string4, bl4, clazz4, classArray2);
                Class[] classArray3 = new Class[]{};
                Class<Void> clazz5 = Void.TYPE;
                boolean bl5 = true;
                String string5 = "invalidateItemAtlas";
                MRenderBufferBridge mRenderBufferBridge5 = this;
                this.H = this.Y(string5, bl5, clazz5, classArray3);
            }
            return;
        }
        if (ForgeVersion.MC_26_2.d()) {
            Class[] classArray = new Class[]{};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "render";
            MRenderBufferBridge mRenderBufferBridge = this;
            this.z = mRenderBufferBridge.Y(string, bl, clazz, classArray);
        } else {
            Class[] classArray = new Class[]{MappedClasses.qk};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "render";
            MRenderBufferBridge mRenderBufferBridge = this;
            this.z = mRenderBufferBridge.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_26_1.d()) {
            Class clazz = MappedClasses.uF;
            boolean bl = true;
            String string = "itemAtlas";
            MRenderBufferBridge mRenderBufferBridge = this;
            this.K = mRenderBufferBridge.J(string, bl, clazz);
            Class[] classArray = new Class[]{};
            Class<Void> clazz6 = Void.TYPE;
            boolean bl6 = true;
            String string6 = "invalidateItemAtlas";
            MRenderBufferBridge mRenderBufferBridge6 = this;
            this.H = this.Y(string6, bl6, clazz6, classArray);
        } else if (ForgeVersion.MC_1_21_6.d()) {
            Class clazz = MappedClasses.GPU_TEXTURE;
            boolean bl = true;
            String string = "itemsAtlas";
            MRenderBufferBridge mRenderBufferBridge = this;
            this.K = mRenderBufferBridge.J(string, bl, clazz);
            Class<Map> clazz7 = Map.class;
            boolean bl7 = true;
            String string7 = "atlasPositions";
            MRenderBufferBridge mRenderBufferBridge7 = this;
            this.r = this.J(string7, bl7, clazz7);
            Class[] classArray = new Class[]{Integer.TYPE};
            Class<Integer> clazz8 = Integer.TYPE;
            boolean bl8 = true;
            String string8 = "calculateAtlasSizeInPixels";
            MRenderBufferBridge mRenderBufferBridge8 = this;
            this.e = this.Y(string8, bl8, clazz8, classArray);
            Class[] classArray4 = new Class[]{};
            Class<Void> clazz9 = Void.TYPE;
            boolean bl9 = true;
            String string9 = "invalidateItemAtlas";
            MRenderBufferBridge mRenderBufferBridge9 = this;
            this.H = this.Y(string9, bl9, clazz9, classArray4);
        }
    }

    public static int J(MRenderBufferBridge mRenderBufferBridge, Object object, int n) {
        return mRenderBufferBridge.a(object, n);
    }

    public void o(Object object, Object object2) {
        if (ForgeVersion.MC_26_2.d()) {
            this.z.invokeVoidNoArgs(object);
            return;
        }
        this.z.invokeVoid(object, object2);
    }

    public static Object V(MRenderBufferBridge mRenderBufferBridge, Object object) {
        return mRenderBufferBridge.c(object);
    }

    private int a(Object object, int n) {
        if (this.e == null) {
            Object object2 = this.c(object);
            if (object2 == null) {
                return 0;
            }
            BlockStateContainerBridge blockStateContainerBridge = new BlockStateContainerBridge(object2);
            GpuTextureView textureView = blockStateContainerBridge.getTextureView();
            return textureView.isNull() ? 0 : textureView.getWidth(0);
        }
        return this.e.invokeInt(object, n);
    }

    private Map o(Object object) {
        if (this.r == null) {
            return null;
        }
        return (Map)this.r.getObject(object);
    }
}

