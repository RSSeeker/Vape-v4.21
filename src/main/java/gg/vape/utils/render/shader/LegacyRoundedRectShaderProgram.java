package gg.vape.utils.render.shader;

import gg.vape.utils.render.shader.ShaderProgram;
import org.lwjgl.opengl.GL20;

public class LegacyRoundedRectShaderProgram
extends ShaderProgram {
    private static final String FRAGMENT_SHADER_SOURCE = "#version 430 compatibility\nprecision highp float;\nlayout(location = 0) uniform vec2 u_A;\nlayout(location = 1) uniform vec2 u_B;\nlayout(location = 2) uniform float u_Radius;\nin vec2 f_Position;\nout vec4 fragColor;\nvoid main() {\nvec2 pa = f_Position - u_A;\nvec2 ba = u_B - u_A;\nfloat h = clamp(dot(pa, ba) / dot(ba, ba), 0.0, 1.0);\nfloat d = length(pa - ba * h) - u_Radius;\nfloat w = fwidth(d);\nfloat a = 1.0 - smoothstep(-0.5 * w, 0.5 * w, d);\nfragColor = gl_Color * vec4(1.0, 1.0, 1.0, a);\n}\n";
    private static final String VERTEX_SHADER_SOURCE = "#version 430 compatibility\nout vec2 f_Position;\nvoid main() {\nf_Position = gl_Vertex.xy;\ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\ngl_FrontColor = gl_Color;\n}\n";

    public void setStart(float x, float y) {
        GL20.glUniform2f((int)0, (float)x, (float)y);
    }

    public void setRadius(float radius) {
        GL20.glUniform1f((int)2, (float)radius);
    }

    public void setEnd(float x, float y) {
        GL20.glUniform2f((int)1, (float)x, (float)y);
    }

    public LegacyRoundedRectShaderProgram() {
        super(VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE);
    }
}
