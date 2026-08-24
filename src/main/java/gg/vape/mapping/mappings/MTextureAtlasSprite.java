package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MTextureManager;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTextureAtlasSprite
extends Mapping {
    private MappingField atlasLocationField;
    private MappingField u0Field;
    private MappingField u1Field;
    private MappingField v0Field;
    private MappingField primaryPositionField;
    private MappingField v1Field;
    private MappingField contentsOrAtlasTextureField;
    private MappingField secondaryPositionField;
    private MappingMethod getU0Method;
    private MappingMethod getU1Method;
    private MappingMethod getV0Method;
    private MappingMethod getV1Method;

    public static void setPrimaryPosition(MTextureAtlasSprite mapping, Object sprite, int position) {
        mapping.setPrimaryPosition(sprite, position);
    }

    public static void setV0(MTextureAtlasSprite mapping, Object sprite, float v0) {
        mapping.setV0(sprite, v0);
    }

    private void setPrimaryPosition(Object sprite, int position) {
        this.primaryPositionField.setInt(sprite, position);
    }

    private void setV0(Object sprite, float v0) {
        this.v0Field.setFloat(sprite, v0);
    }

    private void setSecondaryPosition(Object sprite, int position) {
        this.secondaryPositionField.setInt(sprite, position);
    }


    private Object getContentsOrAtlasTexture(Object sprite) {
        return this.contentsOrAtlasTextureField.getObject(sprite);
    }

    private void setU1(Object sprite, float u1) {
        this.u1Field.setFloat(sprite, u1);
    }

    private void setV1(Object sprite, float v1) {
        this.v1Field.setFloat(sprite, v1);
    }

    public static Object getContentsOrAtlasTexture(MTextureAtlasSprite mapping, Object sprite) {
        return mapping.getContentsOrAtlasTexture(sprite);
    }

    public MTextureAtlasSprite() {
        this(MTextureManager.getInitialControlFlowState());
    }

    private MTextureAtlasSprite(int n) {
        super(MappedClasses.Db);
        Class<Float> clazz = Float.TYPE;
        boolean bl = true;
        String string = "u0";
        MTextureAtlasSprite mTextureAtlasSprite = this;
        this.u0Field = this.J(string, bl, clazz);
        Class<Float> clazz2 = Float.TYPE;
        boolean bl2 = true;
        String string2 = "u1";
        MTextureAtlasSprite mTextureAtlasSprite2 = this;
        this.u1Field = this.J(string2, bl2, clazz2);
        if (n != 0) {
            Class<Float> clazz3 = Float.TYPE;
            boolean bl3 = true;
            String string3 = "v0";
            MTextureAtlasSprite mTextureAtlasSprite3 = this;
            this.v0Field = this.J(string3, bl3, clazz3);
            Class<Float> clazz4 = Float.TYPE;
            boolean bl4 = true;
            String string4 = "v1";
            MTextureAtlasSprite mTextureAtlasSprite4 = this;
            this.v1Field = this.J(string4, bl4, clazz4);
            Class clazz5 = MappedClasses.L;
            boolean bl5 = true;
            String string5 = "atlasTexture";
            MTextureAtlasSprite mTextureAtlasSprite5 = this;
            this.contentsOrAtlasTextureField = this.J(string5, bl5, clazz5);
            return;
        }
        Class<Float> clazz6 = Float.TYPE;
        boolean bl6 = true;
        String string6 = "v0";
        MTextureAtlasSprite mTextureAtlasSprite6 = this;
        this.v0Field = this.J(string6, bl6, clazz6);
        Class<Float> clazz7 = Float.TYPE;
        boolean bl7 = true;
        String string7 = "v1";
        MTextureAtlasSprite mTextureAtlasSprite7 = this;
        this.v1Field = this.J(string7, bl7, clazz7);
        if (ForgeVersion.MC_1_20_6.d()) {
            Class<Integer> clazz8 = Integer.TYPE;
            boolean bl8 = true;
            String string8 = "y";
            MTextureAtlasSprite mTextureAtlasSprite8 = this;
            this.primaryPositionField = this.J(string8, bl8, clazz8);
            Class<Integer> clazz9 = Integer.TYPE;
            boolean bl9 = true;
            String string9 = "x";
            MTextureAtlasSprite mTextureAtlasSprite9 = this;
            this.secondaryPositionField = this.J(string9, bl9, clazz9);
            Class clazz10 = MappedClasses.V4;
            boolean bl10 = true;
            String string10 = "contents";
            MTextureAtlasSprite mTextureAtlasSprite10 = this;
            this.contentsOrAtlasTextureField = this.J(string10, bl10, clazz10);
            Class clazz11 = MappedClasses.zC;
            boolean bl11 = true;
            String string11 = "atlasLocation";
            MTextureAtlasSprite mTextureAtlasSprite11 = this;
            this.atlasLocationField = this.J(string11, bl11, clazz11);
            // 1.20.6+ TextureAtlasSprite 提供 getU0()/getU1()/getV0()/getV1() 取坐标，
            // 比直接读 u0/u1/v0/v1 字段更可靠（字段走 native-mapped-member 在部分
            // 版本上解析不到）。getTextureCoordinates 在字段为 null 时回退到这些方法。
            this.getU0Method = this.Y("getU0", true, Float.TYPE);
            this.getU1Method = this.Y("getU1", true, Float.TYPE);
            this.getV0Method = this.Y("getV0", true, Float.TYPE);
            this.getV1Method = this.Y("getV1", true, Float.TYPE);
        } else {
            Class<Integer> clazz12 = Integer.TYPE;
            boolean bl12 = true;
            String string12 = "x";
            MTextureAtlasSprite mTextureAtlasSprite12 = this;
            this.primaryPositionField = this.J(string12, bl12, clazz12);
            Class<Integer> clazz13 = Integer.TYPE;
            boolean bl13 = true;
            String string13 = "y";
            MTextureAtlasSprite mTextureAtlasSprite13 = this;
            this.secondaryPositionField = this.J(string13, bl13, clazz13);
            Class clazz14 = MappedClasses.L;
            boolean bl14 = true;
            String string14 = "atlasTexture";
            MTextureAtlasSprite mTextureAtlasSprite14 = this;
            this.contentsOrAtlasTextureField = this.J(string14, bl14, clazz14);
        }
    }

    private float[] getTextureCoordinates(Object sprite) {
        if (this.u0Field == null || this.u1Field == null || this.v0Field == null || this.v1Field == null) {
            // 1.20.6+：u0/u1/v0/v1 字段走 native-mapped-member 解析不到，回退到
            // getU0()/getU1()/getV0()/getV1() 方法。全部失败则返回默认 UV，避免 NPE。
            float u0 = this.getU0Method != null ? this.getU0Method.invokeFloat(sprite) : 0.0f;
            float u1 = this.getU1Method != null ? this.getU1Method.invokeFloat(sprite) : 1.0f;
            float v0 = this.getV0Method != null ? this.getV0Method.invokeFloat(sprite) : 1.0f;
            float v1 = this.getV1Method != null ? this.getV1Method.invokeFloat(sprite) : 0.0f;
            return new float[]{u0, u1, v0, v1};
        }
        float[] textureCoordinates = new float[]{this.u0Field.getFloat(sprite), this.u1Field.getFloat(sprite), this.v0Field.getFloat(sprite), this.v1Field.getFloat(sprite)};
        return textureCoordinates;
    }

    private Object getAtlasLocation(Object sprite) {
        return this.atlasLocationField.getObject(sprite);
    }

    public static float[] getTextureCoordinates(MTextureAtlasSprite mapping, Object sprite) {
        return mapping.getTextureCoordinates(sprite);
    }

    private void setU0(Object sprite, float u0) {
        this.u0Field.setFloat(sprite, u0);
    }

    public static Object getAtlasLocation(MTextureAtlasSprite mapping, Object sprite) {
        return mapping.getAtlasLocation(sprite);
    }

    public static void setU1(MTextureAtlasSprite mapping, Object sprite, float u1) {
        mapping.setU1(sprite, u1);
    }

    public static void setV1(MTextureAtlasSprite mapping, Object sprite, float v1) {
        mapping.setV1(sprite, v1);
    }

    public static void setU0(MTextureAtlasSprite mapping, Object sprite, float u0) {
        mapping.setU0(sprite, u0);
    }

    public static void setSecondaryPosition(MTextureAtlasSprite mapping, Object sprite, int position) {
        mapping.setSecondaryPosition(sprite, position);
    }
}

