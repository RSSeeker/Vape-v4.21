#version 430 core
precision highp float;

layout(location = 0) in float f_SelectedShader;
layout(location = 1) in vec3 f_Position;
layout(location = 2) in vec2 f_TexCoords;
layout(location = 3) in vec4 f_VertexColor;
layout(location = 4) in float f_Inner;
layout(location = 5) in float f_Feather;
layout(location = 6) in vec2 f_CenterPos;
layout(location = 7) in float f_MiddleAngle;
layout(location = 8) in float f_SweepAngle;
layout(location = 9) in vec2 f_ScreenDimensions;
layout(location = 10) in vec2 f_Origin;
layout(location = 11) in vec4 f_InnerRect;
layout(location = 12) in vec3 f_Radius3;
layout(location = 13) in float f_Spread;
layout(location = 14) in vec4 f_Corners;

out vec4 FragColor;

uniform sampler2D imgTexture;

void arcStroke() {
    vec2 v = f_Position.xy - f_CenterPos;
    float strokeRadius = f_Radius3.x - f_Inner;
    float d1 = abs(length(v) - f_Radius3.x) - strokeRadius;
    float a1 = smoothstep(-f_Feather, 0.0, d1);
    float c = cos(f_SweepAngle * 0.00872664626);
    float f = f_MiddleAngle * 0.01745329252;
    vec2 up = vec2(cos(f), sin(f));
    float d2 = dot(up, normalize(v)) - c;
    float w = f_Feather * fwidth(d2);
    float a2 = smoothstep(w * -1.0, w * 1.0, d2);
    float a = (1.0 - a1) * a2;
    FragColor = f_VertexColor * vec4(1.0, 1.0, 1.0, a);
}

float gaussian(float x, float sigma) {
    return exp(-(x * x) / (2.0 * sigma * sigma)) / (2.0 * 3.14159 * sigma * sigma);
}

vec4 get_gaussian_blurred() {
    vec4 blurred = vec4(0.0);
    vec2 coords = f_TexCoords.xy;
    float sigma = f_Radius3.y / 3.0; // Adjust sigma value as needed
    float totalWeight = 0.0;

    for (float x = -f_Radius3.y; x <= f_Radius3.y; x++) {
        // Calculate weight
        float weight = gaussian(x, sigma);
        // Sample texture
        vec2 offset = vec2(x, 0.0) / f_ScreenDimensions;
        vec4 sample1 = texture(imgTexture, coords + offset) * weight;
        // Accumulate weighted sample
        blurred += sample1;
        totalWeight += weight;
    }

    blurred /= totalWeight;
    totalWeight = 0.0;
    vec4 blurred2 = vec4(0.0);

    for (float y = -f_Radius3.y; y <= f_Radius3.y; y++) {
        // Calculate weight
        float weight = gaussian(y, sigma);
        // Sample texture
        vec2 offset = vec2(0.0, y) / f_ScreenDimensions;
        vec4 sample1 = texture(imgTexture, coords + offset) * weight;
        // Accumulate weighted sample
        blurred2 += sample1;
        totalWeight += weight;
    }
    return blurred2 / totalWeight;
}

void blur() {
    vec4 blurred = vec4(0.0);
    vec2 coords = f_TexCoords.xy;
    float radius = max(f_Radius3.y, 0.0001);
    float sigma = radius / 3.0;
    float totalWeight = 0.0;
    for (float i = -radius; i <= radius; i++) {
        float weight = gaussian(i, sigma);
        vec2 offset = int(f_Spread) == 0 ? vec2(i, 0) : vec2(0, i);
        offset /= f_ScreenDimensions;
        vec4 sample1 = texture(imgTexture, coords + offset) * weight;
        blurred += sample1;
        totalWeight += weight;
    }
    FragColor = blurred / totalWeight;
}

void circle() {
    float v = length(f_Position.xy - f_CenterPos);
    float a = 1.0 - smoothstep(f_Radius3.x - f_Feather, f_Radius3.x, v);
    FragColor = f_VertexColor * vec4(1.0, 1.0, 1.0, a);
}

void circleStroke() {
    float v = length(f_Position.xy - f_CenterPos);
    float a = smoothstep(f_Inner, f_Inner + f_Feather, v) * (1.0 - smoothstep(f_Radius3.x - f_Feather, f_Radius3.x, v));
    FragColor = f_VertexColor * vec4(1.0, 1.0, 1.0, a);
}

void roundedRect() {
    vec2 tl = f_InnerRect.xy - f_Position.xy;
    vec2 br = f_Position.xy - f_InnerRect.zw;
    vec2 dis = max(br, tl);

    bool inTopLeft = (f_Position.x <= f_InnerRect.x && f_Position.y <= f_InnerRect.y);
    bool inBottomLeft = (f_Position.x <= f_InnerRect.x && f_Position.y >= f_InnerRect.w);
    bool inTopRight = (f_Position.x >= f_InnerRect.z && f_Position.y <= f_InnerRect.y);
    bool inBottomRight = (f_Position.x >= f_InnerRect.z && f_Position.y >= f_InnerRect.w);

    bool drawTopLeft = inTopLeft && f_Corners.x > 0.5;
    bool drawTopRight = inTopRight && f_Corners.y > 0.5;
    bool drawBottomRight = inBottomRight && f_Corners.z > 0.5;
    bool drawBottomLeft = inBottomLeft && f_Corners.w > 0.5;

    bool inCorner = drawTopLeft || drawTopRight || drawBottomLeft || drawBottomRight;

    float v = length(max(vec2(0.0), dis)) - f_Radius3.x;
    float a = inCorner ? 1.0 - smoothstep(0.0, f_Spread, v) : 1.0;

    FragColor = f_VertexColor * vec4(1.0, 1.0, 1.0, a);
}

void roundedRectStroke() {
    vec2 tl = f_InnerRect.xy - f_Position.xy;
    vec2 br = f_Position.xy - f_InnerRect.zw;
    vec2 dis = max(br, tl);
    float v = length(max(vec2(0.0), dis)) - f_Radius3.x;
    float a = 1.0 - smoothstep(-f_Radius3.y, 0.0, abs(v) - f_Radius3.z);
    FragColor = f_VertexColor * vec4(1.0, 1.0, 1.0, a);
}

vec4 blend(vec4 src, vec4 append) {
    return vec4(src.rgb + append.rgb, 1.0 - (1.0 - (src.a)) * (1.0 - append.a));
}

float gi(float x) {
    float i6 = 1.0 / 6.0;
    float i4 = 1.0 / 4.0;
    float i3 = 1.0 / 3.0;
    if (x > 1.5) return 0.0;
    if (x < -1.5) return 1.0;
    float x2 = x * x;
    float x3 = x2 * x;
    if (x >  0.5) return .5625  - (x3 * i6 - 3. * x2 * i4 + 1.125 * x);
    if (x > -0.5) return 0.5    - (0.75 * x - x3 * i3);
    return 0.4375 + (-x3 * i6 - 3. * x2 * i4 - 1.125 * x);
}

float lineShadow(vec2 border, float pos, float sigma) {
    float t = (border.y - border.x) / sigma;
    float pos1 = ((border.x - pos) / sigma) * 1.5;
    float pos2 = ((pos - border.y) / sigma) * 1.5;
    return 1.0 - abs(gi(pos1) - gi(pos2));
}

float distToRoundedRect(vec2 p, vec4 rect, float r) {
    vec2 d = abs(p - rect.xy - rect.zw * 0.5) - rect.zw * 0.5 + vec2(r);
    return length(max(d, 0.0)) - r;
}

float rectShadow(vec4 rect, vec2 point, float sigma, float cornerRadius) {
    float lineV = lineShadow(vec2(rect.x, rect.x + rect.z), point.x, sigma);
    float lineH = lineShadow(vec2(rect.y, rect.y + rect.w), point.y, sigma);
    return lineV * lineH;
}

vec4 drawRectShadow(vec2 pos, vec4 rect, vec4 color, float sigma, float cornerRadius) {
    vec4 result = color;
    float shadowMask = rectShadow(rect, pos, sigma, cornerRadius);
    result.a *= shadowMask;
    return result;
}

void shadow() {
    float sigma = f_Spread;
    float cornerRadius = f_Radius3.x;
    vec4 rect = f_InnerRect;
    vec2 shadowOffset = f_CenterPos; // using center pos variable as shadowOffset since I don't want to add anymore uniform variables
    vec4 shadowColor = f_VertexColor;

    vec4 shadowRect = vec4(vec2(rect.x + shadowOffset.x, rect.y + shadowOffset.y), vec2(rect.z, rect.w)); // Apply offset to shadow
    vec2 offsetPos = f_Position.xy - shadowOffset; // Subtract vec2 from vec2
    vec4 result = drawRectShadow(offsetPos, shadowRect, shadowColor, sigma, cornerRadius);

    float dist = distToRoundedRect(f_Position.xy, rect, cornerRadius);
    float aaWidth = 0.1 * sigma;
    float alpha = smoothstep(-aaWidth, aaWidth, dist);

    result.a *= alpha;

    FragColor = result;
}

void defaultShader() {
    FragColor = f_VertexColor;
}

void textureShader(float a) {
    FragColor = texture(imgTexture, f_TexCoords) * f_VertexColor * vec4(1.0, 1.0, 1.0, a);
}

void itemTextureShader() {
    vec4 texColor = texture(imgTexture, f_TexCoords);
    vec3 brightenedColor = texColor.rgb * 1.8;
    brightenedColor = clamp(brightenedColor, 0.0, 1.0);
    FragColor = vec4(brightenedColor, texColor.a) * f_VertexColor;
}

void minecraftFontShader() {
    // Minecraft's font texture is RGBA, we sample it directly
    vec4 texColor = texture(imgTexture, f_TexCoords);
    float alpha = texColor.a;
    alpha = max(0.0, alpha + 0.25 * (1.0 - alpha));
    float smoothenAlpha = smoothstep(0.15, 0.8, alpha);
    alpha = mix(alpha, 0.0, 1.0 - smoothenAlpha);
    FragColor = texColor * vec4(f_VertexColor.rgb, alpha);
}

// Case 16: Minecraft font - RGBA8 bitmap glyphs (colored textures)
// Matches MC's rendertype_text shader: sample full RGBA, use texColor.a as alpha directly
// NO smoothstep - pixel-perfect with GL_NEAREST
void minecraftFontColorShader() {
    vec4 texColor = texture(imgTexture, f_TexCoords);
    FragColor = vec4(texColor.rgb * f_VertexColor.rgb, texColor.a * f_VertexColor.a);
}

// Case 17: Minecraft font - RED8 intensity glyphs (TrueType textures)
// Matches MC's rendertype_text_intensity shader: sample .r channel as alpha
// Vertex color provides RGB, texture's red channel provides alpha
// NO smoothstep - pixel-perfect with GL_NEAREST
void minecraftFontIntensityShader() {
    float intensity = texture(imgTexture, f_TexCoords).r;
    FragColor = vec4(f_VertexColor.rgb, intensity * f_VertexColor.a);
}

void fontShader() {
    float alpha = texture(imgTexture, f_TexCoords).x;

    alpha = max(0.0, alpha + 0.25 * (1.0 - alpha));
    float smoothenAlpha = smoothstep(0.2, 0.8, alpha);
    alpha = mix(alpha, 0.0, 1.0 - smoothenAlpha);
    FragColor = vec4(f_VertexColor.rgb, alpha * f_VertexColor.a);

}

void roundedTextureRect() {
    vec2 tl = f_InnerRect.xy - f_Position.xy;
    vec2 br = f_Position.xy - f_InnerRect.zw;
    vec2 dis = max(br, tl);
    float v = length(max(vec2(0.0), dis)) - f_Radius3.x;
    float a = 1.0 - smoothstep(0.0, f_Spread, v);
    textureShader(a);
}

void circleTexture() {
    float v = length(f_Position.xy - f_CenterPos);
    float a = 1.0 - smoothstep(f_Radius3.x - f_Feather, f_Radius3.x, v);
    textureShader(a);
}

void invertedRoundedRect() {
    vec2 tl = f_InnerRect.xy - f_Position.xy;
    vec2 br = f_Position.xy - f_InnerRect.zw;
    vec2 dis = max(br, tl);

    // Determine if the fragment is in one of the corners
    bool inTopLeft = (f_Position.x <= f_InnerRect.x && f_Position.y <= f_InnerRect.y);
    bool inBottomLeft = (f_Position.x <= f_InnerRect.x && f_Position.y >= f_InnerRect.w);
    bool inTopRight = (f_Position.x >= f_InnerRect.z && f_Position.y <= f_InnerRect.y);
    bool inBottomRight = (f_Position.x >= f_InnerRect.z && f_Position.y >= f_InnerRect.w);

    // Check if the respective corner should be drawn based on u_Corners
    bool drawTopLeft = inTopLeft && f_Corners.x > 0.5;
    bool drawTopRight = inTopRight && f_Corners.y > 0.5;
    bool drawBottomLeft = inBottomLeft && f_Corners.w > 0.5;
    bool drawBottomRight = inBottomRight && f_Corners.z > 0.5;

    bool inCorner = drawTopLeft || drawTopRight || drawBottomLeft || drawBottomRight;
    float v = length(max(vec2(0.0), dis)) - f_Radius3.x;
    float a = inCorner ? 1.0 - smoothstep(0.0, f_Feather, v) : 0.0;

    // Only draw the selected corners, nothing in the middle
    FragColor = inCorner ? f_VertexColor * vec4(1.0, 1.0, 1.0, 1.0 - a) : vec4(0.0, 0.0, 0.0, 0.0);
}
// Combined shader helper: shadow for rounded rect
vec4 combinedDrawRectShadow(vec2 pos, vec4 rect, vec4 color, float sigma, float cornerRadius) {
    vec2 shadowOffsetPos = pos - rect.xy - rect.zw * 0.5;
    float shadowDist = length(max(abs(shadowOffsetPos) - rect.zw * 0.5 + vec2(cornerRadius), 0.0));
    float shadowMask = 1.0 - smoothstep(0.0, sigma, shadowDist);
    return vec4(color.rgb, color.a * shadowMask);
}

// Combined shader helper: distance to rounded rect edge
float combinedDistToRoundedRect(vec2 p, vec4 rect, float r) {
    vec2 d = abs(p - rect.xy - rect.zw * 0.5) - rect.zw * 0.5 + vec2(r);
    return length(max(d, 0.0)) - r;
}

// Combined shader helper: circle stroke
vec4 combinedDrawCircleStroke(vec2 pos, vec2 center, float radius, float thickness, vec4 strokeColor) {
    float dist = length(pos - center);
    float outerEdge = radius + thickness * 0.5;
    float innerEdge = radius - thickness * 0.5;
    float strokeAlpha = smoothstep(innerEdge - 0.5, innerEdge, dist) * (1.0 - smoothstep(outerEdge, outerEdge + 0.5, dist));
    return vec4(strokeColor.rgb, strokeColor.a * strokeAlpha);
}

// Combined shader: renders shadow + rounded rect + stroke + optional circle indicator
// or texture passthrough when disableShader is set.
//
// Vertex attribute mapping:
//   f_InnerRect      -> innerRect (x, y, w, h)
//   f_Spread         -> shadow spread (sigma)
//   f_Radius3.x      -> corner radius
//   f_VertexColor    -> shadow color (rgba)
//   f_CenterPos      -> shadow offset (x, y)
//   f_Inner          -> stroke width
//   f_Feather        -> circle Y offset
//   f_Corners        -> stroke color (rgba)
//   f_ScreenDimensions.xy + f_Origin.xy -> rect fill color (r, g, b, a)
//   f_MiddleAngle    -> circle thickness (0 = no circle)
//   f_Radius3.yz + f_TexCoords.xy -> circle color (r, g, b, a)
//   f_SweepAngle     -> disable shader flag (1.0 = texture passthrough)
void combinedShader() {
    // Texture passthrough mode
    if (f_SweepAngle >= 0.5) {
        vec2 coords = f_TexCoords.xy;
        FragColor = texture(imgTexture, coords);
        return;
    }

    float sigma = f_Spread;
    float cornerRadius = f_Radius3.x;
    vec2 shadowOffset = f_CenterPos;
    float strokeWidth = f_Inner;

    vec4 rect = f_InnerRect;
    vec4 shadowRect = vec4(vec2(rect.x + shadowOffset.x, rect.y + shadowOffset.y), vec2(rect.z, rect.w));
    vec4 shadowColor = f_VertexColor;
    vec4 strokeColorVec = f_Corners;
    vec3 strokeColor = strokeColorVec.rgb;

    // Unpack rect fill color from screen_dimensions (rg) and origin (ba)
    vec4 rectColor = vec4(f_ScreenDimensions.x, f_ScreenDimensions.y, f_Origin.x, f_Origin.y);
    vec3 fillColor = rectColor.rgb;
    float fillAlpha = rectColor.a;

    vec2 offsetPos = f_Position.xy - shadowOffset;
    vec4 result = combinedDrawRectShadow(offsetPos, shadowRect, shadowColor, sigma, cornerRadius);

    float dist = combinedDistToRoundedRect(f_Position.xy, rect, cornerRadius);
    float aaWidth = 0.05 * sigma;

    float strokeOuterEdge = strokeWidth;
    float strokeInnerEdge = 0.0;

    if (dist <= 0.0) {
        result.rgb = fillColor;
        result.a = fillAlpha;
    } else if (dist > 0.0 && dist <= strokeOuterEdge) {
        float innerStrokeAlpha = smoothstep(strokeInnerEdge, strokeInnerEdge + 0.5, dist);
        result.rgb = mix(fillColor, strokeColor, innerStrokeAlpha);
        result.a = mix(fillAlpha, 1.0, innerStrokeAlpha);
    } else if (dist > strokeOuterEdge && dist <= (strokeOuterEdge + aaWidth)) {
        float outerStrokeAlpha = smoothstep(strokeOuterEdge, strokeOuterEdge + aaWidth, dist);
        result.rgb = mix(strokeColor, shadowColor.rgb, outerStrokeAlpha);
        result.a = mix(1.0, result.a, outerStrokeAlpha);
    } else {
        float outerCornerSmoothing = smoothstep(0.0, aaWidth, dist);
        result.rgb = mix(fillColor, shadowColor.rgb, outerCornerSmoothing);
        result.a = mix(fillAlpha, result.a, outerCornerSmoothing);
    }

    // Circle indicator: enabled when circleThickness > 0
    float circleThickness = f_MiddleAngle;
    if (circleThickness > 0.0) {
        float circleYOffset = f_Feather;
        // Unpack circle color from f_Radius3.yz (rg) and f_TexCoords.xy (ba)
        vec4 circleColor = vec4(f_Radius3.y, f_Radius3.z, f_TexCoords.x, f_TexCoords.y);

        vec2 circleCenter = rect.xy + rect.zw * 0.5 + vec2(0.0, circleYOffset);
        vec4 circleStroke = combinedDrawCircleStroke(f_Position.xy, circleCenter, 5.0, circleThickness, circleColor);

        result.rgb = mix(result.rgb, circleStroke.rgb, circleStroke.a);
        result.a = mix(result.a, fillAlpha, circleStroke.a);
    }

    FragColor = result;
}

void dottedLine() {
    vec2 start = f_CenterPos;
    vec2 end = f_Origin;
    float thickness = f_Radius3.x;
    float spacing = f_Radius3.y;

    vec2 lineDir = end - start;
    float lineLen = length(lineDir);
    if (lineLen < 0.001) { FragColor = vec4(0.0); return; }
    vec2 dir = lineDir / lineLen;
    vec2 perp = vec2(-dir.y, dir.x);
    vec2 toFrag = f_Position.xy - start;
    float along = dot(toFrag, dir);
    float across = dot(toFrag, perp);

    // Perpendicular falloff with fwidth AA
    float halfThick = thickness * 0.5;
    float perpEdge = fwidth(across) * 0.75;
    float perpAlpha = 1.0 - smoothstep(halfThick - perpEdge, halfThick + perpEdge, abs(across));

    // Dot pattern — cosine wave along line axis
    float freq = 3.14159265 / spacing;
    float wave = cos(along * freq * 2.0);
    float fw = fwidth(along) * freq * 2.0;
    float dotAlpha = smoothstep(-fw, fw, wave);

    // Clip to line segment
    float endFade = fwidth(along) * 0.75;
    float clipAlpha = smoothstep(-endFade, endFade, along) * (1.0 - smoothstep(lineLen - endFade, lineLen + endFade, along));

    float a = perpAlpha * dotAlpha * clipAlpha;
    FragColor = f_VertexColor * vec4(1.0, 1.0, 1.0, a);
}

// HSB to RGB conversion
vec3 hsb2rgb(float h, float s, float b) {
    vec3 rgb = clamp(abs(mod(h * 6.0 + vec3(0.0, 4.0, 2.0), 6.0) - 3.0) - 1.0, 0.0, 1.0);
    return b * mix(vec3(1.0), rgb, s);
}

// Gradient pill: pill shape with HSB gradient along its length
// f_VertexColor = start HSBA (0-1), f_Corners = end HSBA (0-1)
// f_InnerRect.xy/zw = capsule endpoints, f_Radius3.x = radius
void gradientPill() {
    vec2 a = f_InnerRect.xy;
    vec2 b = f_InnerRect.zw;
    vec2 pa = f_Position.xy - a;
    vec2 ba = b - a;
    float h = clamp(dot(pa, ba) / dot(ba, ba), 0.0, 1.0);
    float d = length(pa - ba * h) - f_Radius3.x;
    float w = fwidth(d);
    float alpha = 1.0 - smoothstep(-0.5 * w, 0.5 * w, d);

    vec4 hsba = mix(f_VertexColor, f_Corners, h);
    vec3 rgb = hsb2rgb(hsba.x, hsba.y, hsba.z);
    FragColor = vec4(rgb, hsba.w * alpha);
}

// Capsule/pill SDF: perfect pill shape for slider tracks etc.
// f_InnerRect.xy = left endpoint center, f_InnerRect.zw = right endpoint center
// f_Radius3.x = pill radius (half height)
void pill() {
    vec2 a = f_InnerRect.xy;
    vec2 b = f_InnerRect.zw;
    vec2 pa = f_Position.xy - a;
    vec2 ba = b - a;
    float h = clamp(dot(pa, ba) / dot(ba, ba), 0.0, 1.0);
    float d = length(pa - ba * h) - f_Radius3.x;
    float w = fwidth(d);
    float a_out = 1.0 - smoothstep(-0.5 * w, 0.5 * w, d);
    FragColor = f_VertexColor * vec4(1.0, 1.0, 1.0, a_out);
}

void main() {
    switch (int(f_SelectedShader + 0.2)) {
        case 0:
            arcStroke();
            break;
        case 1:
            blur();
            break;
        case 2:
            circle();
            break;
        case 3:
            circleStroke();
            break;
        case 4:
            circleTexture();
            break;
        case 5:
            roundedRect();
            break;
        case 6:
            roundedRectStroke();
            break;
        case 7:
            roundedTextureRect();
            break;
        case 8:
            shadow();
            break;
        case 9:
            defaultShader();
            break;
        case 10:
            textureShader(1.0);
            break;
        case 11:
            fontShader();
            break;
        case 12:
            invertedRoundedRect();
            break;
        case 13:
            combinedShader();
            break;
        case 14:
            itemTextureShader();
            break;
        case 15:
            minecraftFontShader();
            break;
        case 16:
            minecraftFontColorShader();
            break;
        case 17:
            minecraftFontIntensityShader();
            break;
        case 18:
            dottedLine();
            break;
        case 19:
            pill();
            break;
        case 20:
            gradientPill();
            break;

    }
}
