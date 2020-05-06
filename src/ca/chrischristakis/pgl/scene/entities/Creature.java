package ca.chrischristakis.pgl.scene.entities;

abstract public class Creature extends Entity
{
	public float velX, velY;
	public static final float GRAVITY = 0.5f;
	
	public Creature(float x, float y, float width, float height) 
	{
		super(x, y, width, height);
	}	
}
