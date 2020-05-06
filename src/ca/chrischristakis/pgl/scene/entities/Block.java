package ca.chrischristakis.pgl.scene.entities;

abstract public class Block extends Entity
{

	public boolean solid;
	public boolean collided;
	
	public Block(float x, float y, float width, float height) {super(x, y, width, height);}

}
