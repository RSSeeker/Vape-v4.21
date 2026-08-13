package gg.vape.mapping;

public class InjectionParameterSpec {
    public int parameterIndex;
    public Class parameterType;

    public InjectionParameterSpec(int parameterIndex, Class parameterType) {
        this.parameterIndex = parameterIndex;
        this.parameterType = parameterType;
    }
}
