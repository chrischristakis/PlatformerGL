package ca.chrischristakis.pgl.scene;

import java.util.ArrayList;

import ca.chrischristakis.pgl.Main;
import ca.chrischristakis.pgl.scene.entities.EndBlock;
import ca.chrischristakis.pgl.scene.entities.Entity;
import ca.chrischristakis.pgl.scene.entities.Player;

public class EntityManager
{
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	private Player player;
	
	public EntityManager(Player player)
	{
		this.player = player;
	}
	
	public void update()
	{
		for(Entity e : entities)
		{
			e.update();
			if((player.velX > 0) && player.getRightBox().collidesWith(e))
			{
				player.position.x = (e.position.x - player.width + 1);
				player.velX = 0;
			}
			if((player.velX < 0) && player.getLeftBox().collidesWith(e))
			{
				player.position.x = (e.position.x + e.width - 1);
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
				Main.running = false;
			}
		}
		player.update();
	}
	
	public void render()
	{
		player.render();
		for(Entity e : entities)
		{
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
