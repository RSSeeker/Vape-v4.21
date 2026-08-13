package gg.vape.utils.render.shader;

import gg.vape.utils.render.shader.RoundedRectShaderProgram;

public class TexturedRoundedRectShaderProgram
extends RoundedRectShaderProgram {
    private static final String FRAGMENT_SHADER_SOURCE = "#version 430 compatibility\nprecision highp float;\nlayout(location = 0) uniform float u_Radius;\nlayout(location = 1) uniform vec4 u_InnerRect;\nlayout(location = 2) uniform float u_Spread;\nlayout(location = 3) uniform sampler2D texture;\nin vec2 f_Position;\nout vec4 fragColor;\nvoid main() {\nvec2 tl = u_InnerRect.xy - f_Position;\nvec2 br = f_Position - u_InnerRect.zw;\nvec2 dis = max(br, tl);\nfloat v = length(max(vec2(0.0), dis)) - u_Radius;\nfloat a = 1.0 - smoothstep(0.0, u_Spread, v);\n\nvec2 coords = gl_TexCoord[0].xy;\nfragColor = gl_Color * texture2D(texture, coords).rgba * vec4(1.0, 1.0, 1.0, a);\n}\n";
    private static final String VERTEX_SHADER_SOURCE = "#version 430 compatibility\nout vec2 f_Position;\nvoid main() {\nf_Position = gl_Vertex.xy;\ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\ngl_FrontColor = gl_Color;\ngl_TexCoord[0] = gl_TextureMatrix[0] * gl_MultiTexCoord0;\n}\n";

    public TexturedRoundedRectShaderProgram() {
        super(VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE);
    }

    public void setTextureUnit(int textureUnit) {
    }
}
