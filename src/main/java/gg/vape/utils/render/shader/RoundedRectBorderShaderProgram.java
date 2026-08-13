package gg.vape.utils.render.shader;

import gg.vape.utils.render.shader.ShaderProgram;
import org.lwjgl.opengl.GL20;

public class RoundedRectBorderShaderProgram
extends ShaderProgram {
    private static final String VERTEX_SHADER_SOURCE;
    private static final String FRAGMENT_SHADER_SOURCE;

    static {
        FRAGMENT_SHADER_SOURCE = "#version 430 compatibility\nprecision highp float;\nlayout(location = 0) uniform vec3 u_Radius;\nlayout(location = 1) uniform vec4 u_InnerRect;\nin vec2 f_Position;\nout vec4 fragColor;\nvoid main() {\nvec2 tl = u_InnerRect.xy - f_Position;\nvec2 br = f_Position - u_InnerRect.zw;\nvec2 dis = max(br, tl);\nfloat v = length(max(vec2(0.0), dis)) - u_Radius.x;\nfloat a = 1.0 - smoothstep(-u_Radius.y, 0.0, abs(v) - u_Radius.z);\nfragColor = gl_Color * vec4(1.0, 1.0, 1.0, a);\n}\n";
        VERTEX_SHADER_SOURCE = "#version 430 compatibility\nout vec2 f_Position;\nvoid main() {\nf_Position = gl_Vertex.xy;\ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\ngl_FrontColor = gl_Color;\n}\n";
    }

    public void setInnerRect(float left, float top, float right, float bottom) {
        GL20.glUniform4f((int)1, (float)left, (float)top, (float)right, (float)bottom);
    }

    public RoundedRectBorderShaderProgram() {
        super(VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE);
    }

    public void setRadiusParameters(float radius, float feather, float thickness) {
        GL20.glUniform3f((int)0, (float)radius, (float)feather, (float)thickness);
    }
}
