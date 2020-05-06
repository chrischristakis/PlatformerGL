package ca.chrischristakis.pgl.scene.entities;

import ca.chrischristakis.pgl.gl.Texture;
import ca.chrischristakis.pgl.scene.Scene;

public class DefaultBlock extends Block
{
	
	public DefaultBlock(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		texture = new Texture("default.png");
	}

	@Override
	public void update() 
	{
	}

	@Override
	public void render() 
	{
		texture.bind();
		textureBox.render(Scene.blockShader);
		texture.unbind();
	}
	
}
