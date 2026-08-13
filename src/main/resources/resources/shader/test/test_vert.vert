#version 430 compatibility
precision highp float;

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec4 aColor;

out vec3 f_Position;
out vec4 f_VertexColor;

uniform mat4 u_Projection;
uniform mat4 u_View;
uniform mat4 u_Model;

void main() {
    gl_Position = u_Projection * u_View * u_Model * vec4(aPos, 1.0);

    f_Position = aPos;
    f_VertexColor = aColor;
}
