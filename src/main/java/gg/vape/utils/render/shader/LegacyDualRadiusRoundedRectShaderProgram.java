package gg.vape.utils.render.shader;

import gg.vape.utils.render.shader.ShaderProgram;
import org.lwjgl.opengl.GL20;

public class LegacyDualRadiusRoundedRectShaderProgram
extends ShaderProgram {
    private static final String VERTEX_SHADER_SOURCE = "#version 430 compatibility\nout vec2 f_Position;\nvoid main() {\nf_Position = gl_Vertex.xy;\ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\ngl_FrontColor = gl_Color;\n}\n";
    private static final String FRAGMENT_SHADER_SOURCE = "#version 430 compatibility\nprecision highp float;\nlayout(location = 0) uniform vec2 u_Start;\nlayout(location = 1) uniform vec2 u_End;\nlayout(location = 2) uniform float u_Thickness;\nlayout(location = 3) uniform float u_Spacing;\nin vec2 f_Position;\nout vec4 fragColor;\nvoid main() {\nvec2 lineDir = u_End - u_Start;\nfloat lineLen = length(lineDir);\nif (lineLen < 0.001) { fragColor = vec4(0.0); return; }\nvec2 dir = lineDir / lineLen;\nvec2 perp = vec2(-dir.y, dir.x);\nvec2 toFrag = f_Position - u_Start;\nfloat along = dot(toFrag, dir);\nfloat across = dot(toFrag, perp);\nfloat halfThick = u_Thickness * 0.5;\nfloat perpEdge = fwidth(across) * 0.75;\nfloat perpAlpha = 1.0 - smoothstep(halfThick - perpEdge, halfThick + perpEdge, abs(across));\nfloat freq = 3.14159265 / u_Spacing;\nfloat wave = cos(along * freq * 2.0);\nfloat fw = fwidth(along) * freq * 2.0;\nfloat dotAlpha = smoothstep(-fw, fw, wave);\nfloat endFade = fwidth(along) * 0.75;\nfloat clipAlpha = smoothstep(-endFade, endFade, along) * (1.0 - smoothstep(lineLen - endFade, lineLen + endFade, along));\nfloat a = perpAlpha * dotAlpha * clipAlpha;\nfragColor = gl_Color * vec4(1.0, 1.0, 1.0, a);\n}\n";

    public void setStart(float x, float y) {
        GL20.glUniform2f((int)0, (float)x, (float)y);
    }

    public void setEnd(float x, float y) {
        GL20.glUniform2f((int)1, (float)x, (float)y);
    }

    public LegacyDualRadiusRoundedRectShaderProgram() {
        super(VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE);
    }

    public void setThickness(float thickness) {
        GL20.glUniform1f((int)2, (float)thickness);
    }

    public void setSpacing(float spacing) {
        GL20.glUniform1f((int)3, (float)spacing);
    }
}
