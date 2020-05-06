package ca.chrischristakis.pgl.scene.entities;

import ca.chrischristakis.pgl.gfx.Quad;
import ca.chrischristakis.pgl.scene.Scene;

public class Hitbox extends Entity
{	
	private Quad box;
	private Entity owner;
	private float offsetX, offsetY;
	
	public Hitbox(float offsetX, float offsetY, float width, float height, Entity entity)
	{
		super(offsetX,offsetY,width,height);
		this.offsetX = offsetX; this.offsetY = offsetY;
		box = new Quad(offsetX, offsetY, width, height);
		this.owner = entity;
		box.setOutline(true);
	}

	@Override
	public void update() 
	{
		position.x = owner.position.x + offsetX;
		position.y = owner.position.y + offsetY;
		box.updatePosDim(position.x, position.y, width, height);
	}

	@Override
	public void render() 
	{
		box.render(Scene.hitboxShader);
	}

}
