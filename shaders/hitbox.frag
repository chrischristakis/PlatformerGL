#version 330 core

in vec2 tc;

uniform sampler2D tex;
uniform bool testing;
uniform bool collided;

out vec4 FragCol;

void main()
{
	FragCol = vec4(1.0, 0.0, 0.0, 1.0);
}
