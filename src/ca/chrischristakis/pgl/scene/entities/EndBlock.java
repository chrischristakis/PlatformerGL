package ca.chrischristakis.pgl.scene.entities;

import ca.chrischristakis.pgl.gl.Texture;
import ca.chrischristakis.pgl.scene.Scene;

public class EndBlock extends Block
{
	
	public EndBlock(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		texture = new Texture("end.png");
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
