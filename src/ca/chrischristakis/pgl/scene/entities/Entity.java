package ca.chrischristakis.pgl.scene.entities;

import org.joml.Vector3f;

import ca.chrischristakis.pgl.gfx.Quad;
import ca.chrischristakis.pgl.gl.Texture;

abstract public class Entity 
{
	public float width, height;
	public Vector3f position;
	public Quad textureBox;
	public Texture texture;
	
	public Entity(float x, float y, float width, float height)
	{
		this.width = width; this.height = height;
		position = new Vector3f(x, y, 0.0f);
		textureBox = new Quad(x, y, width, height);
	}
	
	public boolean collidesWith(Entity other)
	{
		return (position.x < other.position.x + other.width && other.position.x < position.x + width
				&& position.y + height > other.position.y && other.position.y + other.height > position.y);
	}
	
	abstract public void update();
	abstract public void render();	
	
	public void destory() 
	{
		textureBox.destory();
		texture.destory();
	}
}
