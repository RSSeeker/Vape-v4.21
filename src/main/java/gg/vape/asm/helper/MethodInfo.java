package gg.vape.asm.helper;


public class MethodInfo {
    private String methodName = null;
    private String descriptor = null;

    public MethodInfo(String methodName) {
        this.methodName = methodName;
    }

    public MethodInfo(String methodName, String descriptor) {
        this.methodName = methodName;
        this.descriptor = descriptor;
    }

    public boolean matches(String methodName, String descriptor) {
        return this.methodName.equals(methodName) && (this.descriptor == null || this.descriptor.equals(descriptor));
    }

}

