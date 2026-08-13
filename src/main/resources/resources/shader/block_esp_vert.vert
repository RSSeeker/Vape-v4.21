#version 430 core

layout(location = 0) in vec3 aPos;           // per-vertex cube position (0-1 unit cube)
layout(location = 1) in vec3 instanceOffset;  // per-instance block position (camera-relative)
layout(location = 2) in vec4 instanceColor;   // per-instance RGBA color

out vec4 vColor;

uniform mat4 u_Projection;
uniform mat4 u_View;
uniform mat4 u_Model;

void main() {
    vec3 worldPos = aPos + instanceOffset;
    gl_Position = u_Projection * u_View * u_Model * vec4(worldPos, 1.0);
    vColor = instanceColor;
}
