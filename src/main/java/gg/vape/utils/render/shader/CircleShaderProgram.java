package gg.vape.utils.render.shader;

import gg.vape.utils.render.shader.ShaderProgram;
import org.lwjgl.opengl.GL20;

public class CircleShaderProgram
extends ShaderProgram {
    private static final String VERTEX_SHADER_SOURCE = "#version 430 compatibility\nout vec2 f_Position;\nvoid main() {\nf_Position = gl_Vertex.xy;\ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\ngl_FrontColor = gl_Color;\n}\n";
    private static final String FRAGMENT_SHADER_SOURCE = "#version 430 compatibility\nprecision highp float;\nlayout(location = 0) uniform float u_Radius;\nlayout(location = 1) uniform float u_Feather;\nlayout(location = 2) uniform vec2 u_CenterPos;\nin vec2 f_Position;\nout vec4 fragColor;\nvoid main() {\nfloat v = length(f_Position - u_CenterPos);\nfloat a = 1.0 - smoothstep(u_Radius - u_Feather, u_Radius, v);\nfragColor = gl_Color * vec4(1.0, 1.0, 1.0, a);\n}\n";

    public CircleShaderProgram(String vertexShaderSource, String fragmentShaderSource) {
        super(vertexShaderSource, fragmentShaderSource);
    }

    public void setRadius(float radius) {
        GL20.glUniform1f((int)0, (float)radius);
    }

    public void setCenter(float x, float y) {
        GL20.glUniform2f((int)2, (float)x, (float)y);
    }

    public CircleShaderProgram() {
        super(VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE);
    }

    public void setFeather(float feather) {
        GL20.glUniform1f((int)1, (float)feather);
    }
}
