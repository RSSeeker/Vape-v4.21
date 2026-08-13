#version 430 compatibility
precision highp float;
in vec3 f_Position;
in vec4 f_VertexColor;

out vec4 FragColor;

void main() {
    FragColor = f_VertexColor;
}