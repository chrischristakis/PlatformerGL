package ca.chrischristakis.pgl.scene.entities;

import org.lwjgl.glfw.GLFW;

import ca.chrischristakis.pgl.gl.Texture;
import ca.chrischristakis.pgl.input.KeyInput;
import ca.chrischristakis.pgl.scene.Scene;

public class Player extends Creature
{
	
	public boolean jumping, falling;
	private Hitbox topbox, botbox, leftbox, rightbox;
	public boolean isDead;
	
	public Player(float x, float y, float width, float height) 
	{
		super(x, y, width, height);
		texture = new Texture("player.png");
		rightbox = new Hitbox(width, height/8, 1, height-height/8*2, this);
		leftbox = new Hitbox(0, height/8, 1, height - height/8*2, this);
		topbox = new Hitbox(width/8, height, width - width/8*2, 0, this);
		botbox = new Hitbox(width/8, 0, width - width/8*2, 0, this);
	}

	@Override
	public void update() 
	{	
		velY -= GRAVITY;
		
		if(KeyInput.isPressed(GLFW.GLFW_KEY_A))
			velX -= 0.5f;
		if(KeyInput.isPressed(GLFW.GLFW_KEY_D))
			velX += 0.5f;
		if(KeyInput.isPressed(GLFW.GLFW_KEY_SPACE) && !jumping && !falling)
		{
			jumping = true;
			velY = 10.0f;
		}
		
		if(!KeyInput.isPressed(GLFW.GLFW_KEY_A) && !KeyInput.isPressed(GLFW.GLFW_KEY_D))
			velX = velX *= 0.8f;
		
		if(velY <= -15.0f)
			velY = -15.0f;
		if(Math.abs(velX) >= 15.0f)
			velX = 15.0f * (velX)/Math.abs(velX);
		
		position.x += velX;
		position.y += velY;
		
		leftbox.width = velX;
		rightbox.width = velX;
		topbox.height = velY;
		botbox.height = velY;
		topbox.update();
		botbox.update();
		rightbox.update();
		leftbox.update();
		textureBox.updatePos(position.x, position.y);
	}
	
	public Hitbox getLeftBox()
	{
		return leftbox;
	}
	
	public Hitbox getRightBox()
	{
		return rightbox;
	}
	
	public Hitbox getTopBox()
	{
		return topbox;
	}
	
	public Hitbox getBotBox()
	{
		return botbox;
	}

	@Override
	public void render() 
	{
		texture.bind();
		textureBox.render(Scene.playerShader);
		texture.unbind();
		
		//topbox.render();
		//leftbox.render();
		//botbox.render();
		//rightbox.render();
	}

}
