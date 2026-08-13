#version 430 core
precision highp float;

layout(location = 0) in float selectedShader;
layout(location = 1) in vec3 aPos;
layout(location = 2) in vec2 aTex;
layout(location = 3) in vec4 aColor;
layout(location = 4) in float u_Inner;
layout(location = 5) in float u_Feather;
layout(location = 6) in vec2 u_CenterPos;
layout(location = 7) in float u_MiddleAngle;
layout(location = 8) in float u_SweepAngle;
layout(location = 9) in vec2 screen_dimensions;
layout(location = 10) in vec2 origin;
layout(location = 11) in vec4 u_InnerRect;
layout(location = 12) in vec3 u_Radius3;
layout(location = 13) in float u_Spread;
layout(location = 14) in vec4 u_Corners;

layout(location = 0) out float f_SelectedShader;
layout(location = 1) out vec3 f_Position;
layout(location = 2) out vec2 f_TexCoords;
layout(location = 3) out vec4 f_VertexColor;
layout(location = 4) out float f_Inner;
layout(location = 5) out float f_Feather;
layout(location = 6) out vec2 f_CenterPos;
layout(location = 7) out float f_MiddleAngle;
layout(location = 8) out float f_SweepAngle;
layout(location = 9) out vec2 f_ScreenDimensions;
layout(location = 10) out vec2 f_Origin;
layout(location = 11) out vec4 f_InnerRect;
layout(location = 12) out vec3 f_Radius3;
layout(location = 13) out float f_Spread;
layout(location = 14) out vec4 f_Corners;

uniform mat4 u_Projection;
uniform mat4 u_View;
uniform mat4 u_Model;

void main() {
    gl_Position = u_Projection * u_View * u_Model * vec4(aPos, 1.0);

    f_SelectedShader = selectedShader;
    f_Position = aPos;
    f_TexCoords = aTex;
    f_VertexColor = aColor;
    f_Inner = u_Inner;
    f_Feather = u_Feather;
    f_CenterPos = u_CenterPos;
    f_MiddleAngle = u_MiddleAngle;
    f_SweepAngle = u_SweepAngle;
    f_ScreenDimensions = screen_dimensions;
    f_Origin = origin;
    f_InnerRect = u_InnerRect;
    f_Radius3 = u_Radius3;
    f_Spread = u_Spread;
    f_Corners = u_Corners;
}
