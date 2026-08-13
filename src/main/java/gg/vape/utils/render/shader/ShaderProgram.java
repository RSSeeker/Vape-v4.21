package gg.vape.utils.render.shader;

import gg.vape.ui.click.component.GuiComponent;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderProgram {
    private static int currentProgramId;
    int previousProgramId;
    int geometryShaderId = 0;
    int vertexShaderId = 0;
    boolean linked;
    int programId = GL20.glCreateProgram();
    int fragmentShaderId = 0;
    private static GuiComponent[] legacyComponents;

    public void compileAndLink(String vertexShaderSource, String fragmentShaderSource, String geometryShaderSource) {
        this.programId = GL20.glCreateProgram();
        this.vertexShaderId = GL20.glCreateShader((int)35633);
        this.fragmentShaderId = GL20.glCreateShader((int)35632);
        if (geometryShaderSource != null) {
            this.geometryShaderId = GL20.glCreateShader((int)36313);
        }
        GL20.glShaderSource((int)this.vertexShaderId, (CharSequence)vertexShaderSource);
        GL20.glShaderSource((int)this.fragmentShaderId, (CharSequence)fragmentShaderSource);
        if (geometryShaderSource != null) {
            GL20.glShaderSource((int)this.geometryShaderId, (CharSequence)geometryShaderSource);
        }
        GL20.glCompileShader((int)this.vertexShaderId);
        GL20.glCompileShader((int)this.fragmentShaderId);
        if (geometryShaderSource != null) {
            GL20.glCompileShader((int)this.geometryShaderId);
        }
        GL20.glAttachShader((int)this.programId, (int)this.vertexShaderId);
        GL20.glAttachShader((int)this.programId, (int)this.fragmentShaderId);
        if (geometryShaderSource != null) {
            GL20.glAttachShader((int)this.programId, (int)this.geometryShaderId);
        }
        GL20.glLinkProgram((int)this.programId);
        IntBuffer linkStatusBuffer = BufferUtils.createIntBuffer((int)1);
        gg.vape.wrapper.impl.GL20.x(this.programId, 35714, linkStatusBuffer);
        this.linked = linkStatusBuffer.get(0) == 1;
    }

    public ShaderProgram(String vertexShaderSource, String fragmentShaderSource, String geometryShaderSource) {
        this.vertexShaderId = GL20.glCreateShader((int)35633);
        this.fragmentShaderId = GL20.glCreateShader((int)35632);
        if (geometryShaderSource != null) {
            this.geometryShaderId = GL20.glCreateShader((int)36313);
        }
        GL20.glShaderSource((int)this.vertexShaderId, (CharSequence)vertexShaderSource);
        GL20.glShaderSource((int)this.fragmentShaderId, (CharSequence)fragmentShaderSource);
        if (this.geometryShaderId != 0) {
            GL20.glShaderSource((int)this.geometryShaderId, (CharSequence)geometryShaderSource);
        }
        GL20.glCompileShader((int)this.vertexShaderId);
        GL20.glCompileShader((int)this.fragmentShaderId);
        if (this.geometryShaderId != 0) {
            GL20.glCompileShader((int)this.geometryShaderId);
        }
        GL20.glAttachShader((int)this.programId, (int)this.vertexShaderId);
        GL20.glAttachShader((int)this.programId, (int)this.fragmentShaderId);
        if (this.geometryShaderId != 0) {
            GL20.glAttachShader((int)this.programId, (int)this.geometryShaderId);
        }
        GL20.glLinkProgram((int)this.programId);
        IntBuffer linkStatusBuffer = BufferUtils.createIntBuffer((int)1);
        gg.vape.wrapper.impl.GL20.x(this.programId, 35714, linkStatusBuffer);
        this.linked = linkStatusBuffer.get(0) == 1;
    }

    public static GuiComponent[] getLegacyComponents() {
        return legacyComponents;
    }

    public static int getCurrentProgramId() {
        if (currentProgramId == -1) {
            currentProgramId = GL11.glGetInteger((int)35725);
        }
        return currentProgramId;
    }

    public boolean isLinked() {
        return this.linked;
    }

    public int getProgramId() {
        return this.programId;
    }

    public void restorePreviousProgram() {
        ShaderProgram.useProgram(this.previousProgramId);
    }


    public static void setCurrentProgramId(int programId) {
        currentProgramId = programId;
    }

    public void compileAndLink(String vertexShaderSource, String fragmentShaderSource) {
        this.compileAndLink(vertexShaderSource, fragmentShaderSource, null);
    }

    public static void useProgram(int programId) {
        GL20.glUseProgram((int)programId);
        ShaderProgram.setCurrentProgramId(programId);
    }

    public ShaderProgram(String vertexShaderSource, String fragmentShaderSource) {
        this(vertexShaderSource, fragmentShaderSource, null);
    }

    static {
        ShaderProgram.setLegacyComponents(new GuiComponent[4]);
        currentProgramId = -1;
    }

    public boolean bind() {
        if (!this.linked) {
            return false;
        }
        this.previousProgramId = ShaderProgram.getCurrentProgramId();
        ShaderProgram.useProgram(this.getProgramId());
        return true;
    }

    public static void setLegacyComponents(GuiComponent[] components) {
        legacyComponents = components;
    }
}

