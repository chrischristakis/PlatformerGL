#version 330 core

in vec2 tc;

uniform sampler2D tex;
uniform bool testing;

out vec4 FragCol;

void main()
{
	if(!testing)
		FragCol = texture(tex, tc);
	else
		FragCol = vec4(1.0, 1.0, 1.0, 1.0);

}
