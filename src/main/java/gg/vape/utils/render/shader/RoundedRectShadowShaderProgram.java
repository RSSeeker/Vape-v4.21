package gg.vape.utils.render.shader;

import gg.vape.utils.render.shader.ShaderProgram;
import java.awt.Color;
import org.lwjgl.opengl.GL20;

public class RoundedRectShadowShaderProgram
extends ShaderProgram {
    private static final String VERTEX_SHADER_SOURCE = "#version 430 compatibility\nout vec2 f_Position;\nvoid main() {\nf_Position = gl_Vertex.xy;\ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\ngl_FrontColor = gl_Color;\n}\n";
    private static final String FRAGMENT_SHADER_SOURCE = "#version 430 compatibility\nprecision highp float;\nlayout(location = 0) uniform vec4 u_InnerRect;\nlayout(location = 1) uniform float u_Spread;\nlayout(location = 2) uniform vec4 u_Color;\nlayout(location = 3) uniform float u_CornerRadius; // Added uniform for corner radius\nlayout(location = 4) uniform vec2 u_ShadowOffset; // New uniform for shadow offset\n// blend two color by alpha\nvec4 blend(vec4 src, vec4 append) {\n  \treturn vec4(src.rgb + append.rgb,\n              1.0 - (1.0 - (src.a)) * (1.0 - append.a));\n}\n\n// approximation to the gaussian integral [x, infty)\nfloat gi(float x) {\n\tfloat i6 = 1.0 / 6.0;\n\tfloat i4 = 1.0 / 4.0;\n\tfloat i3 = 1.0 / 3.0;\n\n    if (x > 1.5) return 0.0;\n    if (x < -1.5) return 1.0;\n\n    float x2 = x * x;\n    float x3 = x2 * x;\n    \n    if (x >  0.5) return .5625  - ( x3 * i6 - 3. * x2 * i4 + 1.125 * x);\n    if (x > -0.5) return 0.5    - (0.75 * x - x3 * i3);\n    return 0.4375 + (-x3 * i6 - 3. * x2 * i4 - 1.125 * x);\n}\n\n// create a line shadow mask\nfloat lineShadow(vec2 border, float pos , float sigma) {\n    float t = (border.y - border.x) / sigma;\n    \n    float pos1 = ((border.x - pos) / sigma) * 1.5;\n    float pos2 = ((pos - border.y) / sigma) * 1.5;\n    \n  \treturn 1.0 - abs(gi(pos1) - gi(pos2));\n}\nfloat distToRoundedRect(vec2 p, vec4 rect, float r) {\n    vec2 d = abs(p - rect.xy - rect.zw * 0.5) - rect.zw * 0.5 + vec2(r);\n    return length(max(d, 0.0)) - r;\n}// create a rect shadow by two line shadow\nfloat rectShadow(vec4 rect, vec2 point, float sigma, float cornerRadius) {\n    float lineV = lineShadow(vec2(rect.x, rect.x + rect.z), point.x, sigma);\n    float lineH = lineShadow(vec2(rect.y, rect.y + rect.w), point.y, sigma);\n    return lineV * lineH;\n}// draw shadow\nvec4 drawRectShadow(vec2 pos, vec4 rect, vec4 color, float sigma, float cornerRadius) {\n    vec4 result = color;\n    float shadowMask = rectShadow(rect, pos, sigma, cornerRadius);\n    result.a *= shadowMask;\n    return result;\n}\nin vec2 f_Position;\nout vec4 fragColor;\nvoid main() {\n    float sigma = u_Spread;\n    float cornerRadius = u_CornerRadius;\n    vec2 shadowOffset = u_ShadowOffset; // Shadow offset as a vec2\n\n    vec4 rect = u_InnerRect;\n    vec4 shadowRect = vec4(vec2(rect.x + shadowOffset.x, rect.y + shadowOffset.y), vec2(rect.z, rect.w)); // Apply offset to shadow\n    vec4 shadowColor = u_Color;\n\n    // Calculate shadow with offset - Corrected\n    vec2 offsetPos = f_Position - shadowOffset; // Subtract vec2 from vec2\n    vec4 result = drawRectShadow(offsetPos, shadowRect, shadowColor, sigma, cornerRadius);\n\n    // Anti-aliasing for inner corners, based on original position\n    float dist = distToRoundedRect(f_Position, rect, cornerRadius);\n    float aaWidth = 0.1 * sigma;\n    float alpha = smoothstep(-aaWidth, aaWidth, dist);\n\n    result.a *= alpha;\n\n    fragColor = result;\n}";

    public void setCornerRadius(float cornerRadius) {
        GL20.glUniform1f((int)3, (float)cornerRadius);
    }

    public void setShadowOffset(float x, float y) {
        GL20.glUniform2f((int)4, (float)x, (float)y);
    }

    public void setInnerRect(float x, float y, float width, float height) {
        GL20.glUniform4f((int)0, (float)x, (float)y, (float)width, (float)height);
    }

    public RoundedRectShadowShaderProgram() {
        super(VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE);
    }

    public void setShadowColor(Color color) {
        GL20.glUniform4f((int)2, (float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
    }

    public void setSpread(float spread) {
        GL20.glUniform1f((int)1, (float)spread);
    }
}
