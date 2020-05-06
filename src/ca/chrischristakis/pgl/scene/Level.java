package ca.chrischristakis.pgl.scene;

import java.util.ArrayList;

import org.joml.Vector2f;

import ca.chrischristakis.pgl.scene.entities.Block;
import ca.chrischristakis.pgl.scene.entities.DefaultBlock;
import ca.chrischristakis.pgl.scene.entities.EndBlock;
import ca.chrischristakis.pgl.scene.entities.Player;
import ca.chrischristakis.pgl.utils.FileUtils;

public class Level 
{
	
	int width, height;
	int tileDim = 100;
	
	Vector2f spawn;
	
	ArrayList<Block> tiles = new ArrayList<Block>();
	
	Player p;
	
	//Format: Width, height, data
	public void loadLevelFromCXMT(Player p, EntityManager em, String path)
	{
		this.p = p;
		
		String data = FileUtils.readFileAsString(path);
		int lastIndex = data.indexOf("\n");
		int currentIndex = 0;
		int index = 0;
		String levelData = "";
		
		//This can definitely be cleaner.
		while(lastIndex != -1)
		{
			String result = data.substring(currentIndex, lastIndex);
			if(index == 0)//width
				width = Integer.parseInt(result);
			if(index == 1)
				height = Integer.parseInt(result);
			if(index == 2)
				levelData = result;
			currentIndex = lastIndex + 1;
			lastIndex = data.indexOf("\n", currentIndex);
			index++;
		}
		
		String old = levelData;
		levelData = "";
		for(int i = 1; i < old.length(); i++)
		{
			if(old.charAt(i) != ',')
				levelData += old.charAt(i);
		}
			
		int x = 0, y = height;
		for(int i = 0; i < levelData.length(); i++)
		{
			x = i % width;
			if(levelData.charAt(i) == '1')
				em.add(new DefaultBlock(x*tileDim, y*tileDim, tileDim, tileDim));
			if(levelData.charAt(i) == '2')
			{
				p.position.x = x*tileDim;
				p.position.y = y*tileDim;
				spawn = new Vector2f(x*tileDim, y*tileDim);
			}
			if(levelData.charAt(i) == '3')
			{
				em.add(new EndBlock(x*tileDim+30, y*tileDim+30, tileDim-60, tileDim-60));
			}
			if(i % height == 0)
				y--;
		}
	}
	
	public void reset()
	{
		p.velY = 0;
		p.velX = 0;
		p.position.x = spawn.x;
		p.position.y = spawn.y;
	}
	
	/* For future projects.
	private class Tile
	{
		Vector2f position;
		int id;
		
		public Tile(float x, float y, int id) {position = new Vector2f(x, y); this.id = id;}
	}
	*/
}
