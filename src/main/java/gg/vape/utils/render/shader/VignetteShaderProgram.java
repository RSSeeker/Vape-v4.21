package gg.vape.utils.render.shader;

import gg.vape.utils.render.shader.ShaderProgram;
import org.lwjgl.opengl.GL20;

public class VignetteShaderProgram
extends ShaderProgram {
    private static final String FRAGMENT_SHADER_SOURCE;
    private static final String VERTEX_SHADER_SOURCE;

    public void setInnerRadius(float innerRadius) {
        GL20.glUniform1f((int)1, (float)innerRadius);
    }

    public void setIntensity(float intensity) {
        GL20.glUniform1f((int)2, (float)intensity);
    }

    public VignetteShaderProgram(float outerRadius, float innerRadius, float intensity, float resolutionWidth, float resolutionHeight) {
        super(VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE);
        GL20.glUniform1f((int)0, (float)outerRadius);
        GL20.glUniform1f((int)1, (float)innerRadius);
        GL20.glUniform1f((int)2, (float)intensity);
        GL20.glUniform2f((int)3, (float)resolutionWidth, (float)resolutionHeight);
    }

    public void setResolution(float width, float height) {
        GL20.glUniform2f((int)3, (float)width, (float)height);
    }

    public VignetteShaderProgram() {
        super(VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE);
    }

    public void setOuterRadius(float outerRadius) {
        GL20.glUniform1f((int)0, (float)outerRadius);
    }

    static {
        VERTEX_SHADER_SOURCE = "#version 430 compatibility\n\nvoid main(void)\n{\ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
        FRAGMENT_SHADER_SOURCE = "#version 430 compatibility\nlayout(location = 0) uniform float outerRadius;\nlayout(location = 1) uniform float innerRadius;\nlayout(location = 2) uniform float intensity;\nlayout(location = 3) uniform vec2 u_resolution;\nvoid main(void) {\n    vec4 color = vec4(1.0, 1.0, 1.0, 1.0);\n    vec2 relativePosition = gl_FragCoord.xy / u_resolution - .5;\n    float len = length(relativePosition);\n    float vignette = smoothstep(outerRadius, innerRadius, len);\n    color.rbg = mix(color.rgb, color.rgb * vignette, intensity);\n    gl_FragColor = color;}";
    }
}
