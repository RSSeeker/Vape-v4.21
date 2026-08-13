package gg.vape.utils.render;

import gg.vape.Vape;
import java.nio.charset.StandardCharsets;
import org.lwjgl.opengl.GL20;

public class RenderBatchShaderProgram {
    public int programId;
    public final int projectionUniformLocation;
    private static int[] legacyState;
    public final int modelUniformLocation;
    public final int viewUniformLocation;

    private int compileShader(String resourcePath) {
        int shaderType;
        if (resourcePath.endsWith(".frag")) {
            shaderType = 35632;
        } else if (resourcePath.endsWith(".vert")) {
            shaderType = 35633;
        } else {
            throw new RuntimeException("Unable to set type");
        }
        byte[] shaderBytes = Vape.readResource(resourcePath);
        int shaderId = GL20.glCreateShader((int)shaderType);
        String shaderSource = new String(shaderBytes, StandardCharsets.UTF_8);
        GL20.glShaderSource((int)shaderId, (CharSequence)shaderSource);
        GL20.glCompileShader((int)shaderId);
        String compileError = null;
        if (GL20.glGetShaderi((int)shaderId, (int)35713) == 0) {
            compileError = GL20.glGetShaderInfoLog((int)shaderId, (int)512);
        }
        if (compileError != null) {
            throw new RuntimeException("Unable to compile shader: " + shaderType + " - " + compileError);
        }
        return shaderId;
    }

    public static int[] getLegacyState() {
        return legacyState;
    }

    private static RuntimeException propagateRuntimeException(RuntimeException runtimeException) {
        return runtimeException;
    }

    public static void setLegacyState(int[] legacyState) {
        RenderBatchShaderProgram.legacyState = legacyState;
    }

    static {
        RenderBatchShaderProgram.setLegacyState(new int[3]);
    }

    private void linkProgram(int firstShaderId, int secondShaderId) {
        this.programId = GL20.glCreateProgram();
        GL20.glAttachShader((int)this.programId, (int)firstShaderId);
        GL20.glAttachShader((int)this.programId, (int)secondShaderId);
        GL20.glLinkProgram((int)this.programId);
        if (GL20.glGetProgrami((int)this.programId, (int)35714) == 0) {
            String linkError = GL20.glGetProgramInfoLog((int)this.programId, (int)8224);
            throw new RuntimeException("Unable to link shader: " + linkError);
        }
        GL20.glDeleteShader((int)firstShaderId);
        GL20.glDeleteShader((int)secondShaderId);
    }

    public RenderBatchShaderProgram(String firstShaderPath, String secondShaderPath) {
        int firstShaderId = this.compileShader(firstShaderPath);
        int secondShaderId = this.compileShader(secondShaderPath);
        this.linkProgram(firstShaderId, secondShaderId);
        this.projectionUniformLocation = GL20.glGetUniformLocation((int)this.programId, (CharSequence)"u_Projection");
        this.modelUniformLocation = GL20.glGetUniformLocation((int)this.programId, (CharSequence)"u_Model");
        this.viewUniformLocation = GL20.glGetUniformLocation((int)this.programId, (CharSequence)"u_View");
    }

    public void bind() {
        GL20.glUseProgram((int)this.programId);
    }
}
