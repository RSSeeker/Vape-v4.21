package gg.vape.utils.render.shader;

import gg.vape.utils.render.shader.CircleShaderProgram;

public class TexturedCircleShaderProgram
extends CircleShaderProgram {
    private static final String VERTEX_SHADER_SOURCE;
    private static final String FRAGMENT_SHADER_SOURCE;

    static {
        FRAGMENT_SHADER_SOURCE = "#version 430 compatibility\nprecision highp float;\nlayout(location = 0) uniform float u_Radius;\nlayout(location = 1) uniform float u_Feather;\nlayout(location = 2) uniform vec2 u_CenterPos;\nlayout(location = 3) uniform sampler2D texture;\nin vec2 f_Position;\nout vec4 fragColor;\nvoid main() {\nfloat v = length(f_Position - u_CenterPos);\nfloat a = 1.0 - smoothstep(u_Radius - u_Feather, u_Radius, v);\nvec2 coords = gl_TexCoord[0].xy;\nfragColor = gl_Color * texture2D(texture, coords).rgba * vec4(1.0, 1.0, 1.0, a);\n}\n";
        VERTEX_SHADER_SOURCE = "#version 430 compatibility\nout vec2 f_Position;\nvoid main() {\nf_Position = gl_Vertex.xy;\ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\ngl_FrontColor = gl_Color;\ngl_TexCoord[0] = gl_TextureMatrix[0] * gl_MultiTexCoord0;\n}\n";
    }

    public TexturedCircleShaderProgram() {
        super(VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE);
    }
}
