package ca.chrischristakis.pgl.scene;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import ca.chrischristakis.pgl.Main;
import ca.chrischristakis.pgl.scene.entities.Entity;

public class Camera 
{
	
	public Vector3f position;
	private Entity center;
	
	private float currentAmt = 0;
	
	public Camera(Entity center)
	{
		this.center = center;
		position =  new Vector3f((Main.WIDTH - center.width)/2 - center.position.x, (Main.HEIGHT-center.height)/2 - center.position.y, 0.0f);
	}
	
	public void zoom(float amt)
	{
		currentAmt += amt;
		float heightR = ((float)Main.HEIGHT/(float)Main.WIDTH);
		if(Main.WIDTH - currentAmt <= 40) currentAmt -= amt;
		Scene.projection = new Matrix4f().ortho(0.0f + (currentAmt)/2.0f, Main.WIDTH - (currentAmt)/2.0f,
				0.0f + (currentAmt*heightR)/2.0f, Main.HEIGHT - (currentAmt*heightR)/2.0f, -10.0f, 10.0f);
	}
	
	public void update()
	{
		position.x = (Main.WIDTH-center.width)/2 - center.position.x;
		position.y = (Main.HEIGHT-center.height)/2 - center.position.y;
	}
	
}
