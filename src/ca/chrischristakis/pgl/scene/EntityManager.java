package ca.chrischristakis.pgl.scene;

import java.util.ArrayList;

import ca.chrischristakis.pgl.Main;
import ca.chrischristakis.pgl.scene.entities.BounceBlock;
import ca.chrischristakis.pgl.scene.entities.EndBlock;
import ca.chrischristakis.pgl.scene.entities.Entity;
import ca.chrischristakis.pgl.scene.entities.Player;
import ca.chrischristakis.pgl.scene.entities.SpikeBlock;

public class EntityManager
{
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	private Player player;
	private Scene scene;
	
	public boolean switchingLevels = false;
	
	public EntityManager(Player player, Scene scene)
	{
		this.player = player; this.scene = scene;
	}
	
	public void update()
	{
		player.falling = true;
		for(Entity e : entities)
		{
			if(switchingLevels) break;
			
			e.update();
			if((player.velX > 0) && player.getRightBox().collidesWith(e))
			{
				player.position.x = e.position.x - player.width + 1;
				player.velX = 0;
			}
			if((player.velX < 0) && player.getLeftBox().collidesWith(e))
			{
				player.position.x = e.position.x + e.width - 1;
				player.velX = 0;
			}
			if((player.velY > 0) && player.getTopBox().collidesWith(e))
			{
				player.position.y = e.position.y - player.height + 1;
				player.velY = 0;
			}
			if((player.velY < 0) && player.getBotBox().collidesWith(e))
			{
				player.jumping = false;
				player.position.y = e.position.y + e.width - 1;
				player.velY = 0;
			}
			
			if(player.collidesWith(e) && e instanceof EndBlock)
			{
				if(Scene.currentLevel + 1 == Scene.numOfLevels)
					Main.running = false;
				else
					scene.nextLevel();
			}
			
			if(player.collidesWith(e) && e instanceof BounceBlock)
			{
				player.velY = 20.0f;
			}
			
			if(player.collidesWith(e) && e instanceof SpikeBlock)
			{
				player.isDead = true;
			}
			
			if(player.getBotBox().collidesWith(e))
				player.falling = false;
		}
		player.update();
		
		if(switchingLevels) scene.notifyReady();
	}
	
	public void render()
	{
		player.render();
		for(Entity e : entities)
		{
			if(switchingLevels) break;
			e.render();
		}
	}
	
	public void add(Entity e)
	{
		entities.add(e);
	}
	
	public void clear()
	{
		for(Entity e : entities)
			e.destory();
		entities.clear();
	}

}
