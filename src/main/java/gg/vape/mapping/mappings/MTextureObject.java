package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MTextureManager;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTextureObject
extends Mapping {
    private MappingMethod setFilterMethod;
    private MappingField textureField;
    private MappingMethod getGlTextureIdMethod;
    private MappingField glTextureIdField;


    public static void setFilter(MTextureObject mapping, Object textureObject, boolean blur, boolean mipmap) {
        mapping.setFilter(textureObject, blur, mipmap);
    }

    private void setFilter(Object textureObject, boolean blur, boolean mipmap) {
        this.setFilterMethod.invokeVoid(textureObject, blur, mipmap);
    }

    private Object getTexture(Object textureObject) {
        return this.textureField.getObject(textureObject);
    }

    public static int getGlTextureId(MTextureObject mapping, Object textureObject) {
        return mapping.getGlTextureId(textureObject);
    }

    public static Object getTexture(MTextureObject mapping, Object textureObject) {
        return mapping.getTexture(textureObject);
    }

    private int getGlTextureId(Object textureObject) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return this.glTextureIdField.getInt(textureObject);
        }
        return this.getGlTextureIdMethod.invokeInt(textureObject, new Object[0]);
    }

    public MTextureObject() {
        this(MTextureManager.getInitialControlFlowState());
    }

    private MTextureObject(int n) {
        super(MappedClasses.ut);
        if (n != 0) {
            if (ForgeVersion.MC_1_21_6.d()) {
                Class<Integer> clazz = Integer.TYPE;
                boolean bl = true;
                String string = "glTextureId";
                MTextureObject mTextureObject = this;
                this.glTextureIdField = mTextureObject.J(string, bl, clazz);
                Class[] classArray = new Class[]{Boolean.TYPE, Boolean.TYPE};
                Class<Void> clazz2 = Void.TYPE;
                boolean bl2 = true;
                String string2 = "setFilter";
                MTextureObject mTextureObject2 = this;
                this.setFilterMethod = this.Y(string2, bl2, clazz2, classArray);
            }
            Class[] classArray = new Class[]{};
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "getGlTextureId";
            MTextureObject mTextureObject = this;
            this.getGlTextureIdMethod = mTextureObject.Y(string, bl, clazz, classArray);
            return;
        }
        if (ForgeVersion.MC_1_21_6.d()) {
            Class clazz = MappedClasses.GPU_TEXTURE;
            boolean bl = true;
            String string = "texture";
            MTextureObject mTextureObject = this;
            this.textureField = mTextureObject.J(string, bl, clazz);
        } else if (ForgeVersion.MC_1_20_6.d()) {
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "glTextureId";
            MTextureObject mTextureObject = this;
            this.glTextureIdField = mTextureObject.J(string, bl, clazz);
            Class[] classArray = new Class[]{Boolean.TYPE, Boolean.TYPE};
            Class<Void> clazz3 = Void.TYPE;
            boolean bl3 = true;
            String string3 = "setFilter";
            MTextureObject mTextureObject3 = this;
            this.setFilterMethod = this.Y(string3, bl3, clazz3, classArray);
        } else {
            Class[] classArray = new Class[]{};
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "getGlTextureId";
            MTextureObject mTextureObject = this;
            this.getGlTextureIdMethod = mTextureObject.Y(string, bl, clazz, classArray);
        }
    }
}

