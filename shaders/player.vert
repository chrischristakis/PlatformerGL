#version 330 core

layout(location = 0) in vec3 VertexPosition;
layout(location = 1) in vec2 TexCoords;

uniform mat4 mvp = mat4(1.0);

out vec2 tc;

void main()
{
	gl_Position = mvp * vec4(VertexPosition, 1.0);
	tc = TexCoords;
}
