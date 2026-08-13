package gg.vape.utils.render;

import gg.vape.utils.render.shader.ShaderProgram;

public class LegacyFontShaderProgram
extends ShaderProgram {
    private static final String VERTEX_SHADER_SOURCE;
    private static final String FRAGMENT_SHADER_SOURCE;

    public LegacyFontShaderProgram() {
        super(VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE);
    }

    static {
        FRAGMENT_SHADER_SOURCE = "#version 430 compatibility\nlayout(location = 0) uniform sampler2D texture;\n\nin vec2 f_Position;\nout vec4 fragColor;\n\nvoid main() {\n\tvec2 coords = gl_TexCoord[0].xy;\n    float alpha = texture2D(texture, coords).x;\n\n    alpha = max(0.0, alpha + 0.25 * (1.0 - alpha));\n    float smoothenAlpha = smoothstep(0.2, 0.8, alpha);\n    alpha = mix(alpha, 0.0, 1.0 - smoothenAlpha);\n    fragColor = vec4(gl_Color.rgb, alpha * gl_Color.a);\n}";
        VERTEX_SHADER_SOURCE = "#version 430 compatibility\n\nout vec2 f_Position;\nvoid main(void)\n{\nf_Position = gl_Vertex.xy;\ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\ngl_TexCoord[0] = gl_TextureMatrix[0] * gl_MultiTexCoord0;\ngl_FrontColor = gl_Color;\n}";
    }
}

