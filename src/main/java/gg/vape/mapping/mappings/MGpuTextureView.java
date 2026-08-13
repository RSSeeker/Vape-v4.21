package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;

public class MGpuTextureView
extends Mapping {
    private MappingMethod isClosedMethod;
    private MappingField mipLevelsField;
    private MappingMethod getHeightMethod;
    private MappingField baseMipLevelField;
    private static String[] constructorState;
    private MappingField textureField;
    private MappingMethod getWidthMethod;

    public int getWidth(Object textureView, int mipLevel) {
        if (this.getWidthMethod == null || this.getWidthMethod.hasResolutionFailed()) {
            return 0;
        }
        return this.getWidthMethod.invokeInt(textureView, mipLevel);
    }

    public boolean isClosed(Object textureView) {
        if (this.isClosedMethod == null || this.isClosedMethod.hasResolutionFailed()) {
            return false;
        }
        return this.isClosedMethod.invokeBoolean(textureView, new Object[0]);
    }

    public static String[] getGpuTextureViewConstructorState() {
        return constructorState;
    }

    public MGpuTextureView() {
        this(MGpuTextureView.getGpuTextureViewConstructorState());
    }

    private MGpuTextureView(String[] constructorState) {
        super(MappedClasses.GPU_TEXTURE_VIEW);
        String[] unusedConstructorState = constructorState;
        Class textureType = MappedClasses.GPU_TEXTURE;
        boolean remapTextureField = true;
        String textureFieldName = "texture";
        MGpuTextureView mappings = this;
        this.textureField = mappings.J(textureFieldName, remapTextureField, textureType);
        Class<Integer> baseMipLevelType = Integer.TYPE;
        boolean remapBaseMipLevelField = true;
        String baseMipLevelFieldName = "baseMipLevel";
        this.baseMipLevelField = this.J(baseMipLevelFieldName, remapBaseMipLevelField, baseMipLevelType);
        Class<Integer> mipLevelsType = Integer.TYPE;
        boolean remapMipLevelsField = true;
        String mipLevelsFieldName = "mipLevels";
        this.mipLevelsField = this.J(mipLevelsFieldName, remapMipLevelsField, mipLevelsType);
        Class[] widthParameterTypes = new Class[]{Integer.TYPE};
        Class<Integer> widthReturnType = Integer.TYPE;
        boolean remapWidthMethod = true;
        String widthMethodName = "getWidth";
        this.getWidthMethod = this.Y(widthMethodName, remapWidthMethod, widthReturnType, widthParameterTypes);
        Class[] heightParameterTypes = new Class[]{Integer.TYPE};
        Class<Integer> heightReturnType = Integer.TYPE;
        boolean remapHeightMethod = true;
        String heightMethodName = "getHeight";
        this.getHeightMethod = this.Y(heightMethodName, remapHeightMethod, heightReturnType, heightParameterTypes);
        Class[] isClosedParameterTypes = new Class[]{};
        Class<Boolean> isClosedReturnType = Boolean.TYPE;
        boolean remapIsClosedMethod = true;
        String isClosedMethodName = "isClosed";
        this.isClosedMethod = this.Y(isClosedMethodName, remapIsClosedMethod, isClosedReturnType, isClosedParameterTypes);
    }

    public int getMipLevels(Object textureView) {
        if (this.mipLevelsField == null) {
            return 1;
        }
        return this.mipLevelsField.getInt(textureView);
    }

    public int getBaseMipLevel(Object textureView) {
        if (this.baseMipLevelField == null) {
            return 0;
        }
        return this.baseMipLevelField.getInt(textureView);
    }

    static {
        MGpuTextureView.setGpuTextureViewConstructorState(null);
    }

    public static void setGpuTextureViewConstructorState(String[] state) {
        constructorState = state;
    }

    public Object getTexture(Object textureView) {
        if (this.textureField == null) {
            return null;
        }
        return this.textureField.getObject(textureView);
    }

    public int getHeight(Object textureView, int mipLevel) {
        if (this.getHeightMethod == null || this.getHeightMethod.hasResolutionFailed()) {
            return 0;
        }
        return this.getHeightMethod.invokeInt(textureView, mipLevel);
    }
}
