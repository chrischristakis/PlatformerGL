package ca.chrischristakis.pgl.scene;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import ca.chrischristakis.pgl.Main;
import ca.chrischristakis.pgl.gl.ShaderProgram;
import ca.chrischristakis.pgl.input.KeyInput;
import ca.chrischristakis.pgl.scene.entities.Player;

public class Scene 
{
	
	//Note! (0,0) on an entity is the bottom left corner, like a normal graph in math.
	
	public static Matrix4f projection, view;
	private Player player;
	public static ShaderProgram blockShader, playerShader, hitboxShader;
	
	private EntityManager em;
	private Camera camera;
	private Level[] levels;
	
	public static int currentLevel = 0, numOfLevels;
	
	private String[] levelNames = {"level1.ctmx","level2.ctmx","level3.ctmx", "level4.ctmx", "level5.ctmx"};
	
	public Scene()
	{
		numOfLevels = levelNames.length;
		levels = new Level[numOfLevels];
		projection = new Matrix4f().ortho(0.0f, Main.WIDTH, 0.0f, Main.HEIGHT, -10.0f, 10.0f); //should always be the first line.
		view = new Matrix4f();
		
		blockShader = new ShaderProgram("block.vert", "block.frag");
		playerShader = new ShaderProgram("player.vert", "player.frag");
		hitboxShader = new ShaderProgram("player.vert", "hitbox.frag");
		
		player = new Player(0, 0, 50, 100);
		em = new EntityManager(player, this);
		
		camera = new Camera(player);
		levels[currentLevel] = new Level();
		levels[currentLevel].loadLevelFromCXMT(player, em, "assets/" + levelNames[currentLevel]);
		camera.zoom(-50);
	}
	
	public void render()
	{
		view.identity();
		view.translate(camera.position);
		
		em.render();
	}
	
	public void update()
	{
		if(player.position.y < 0 || player.isDead)
		{
			player.isDead = false;
			levels[currentLevel].reset();
		}
		
		if(KeyInput.isPressed(GLFW.GLFW_KEY_Z))
			camera.zoom(50);
		if(KeyInput.isPressed(GLFW.GLFW_KEY_X))
			camera.zoom(-50);
		
		em.update();
		camera.update();
	}
	
	public void destory()
	{
		em.clear();
		player.destory();
	}
	
	public void nextLevel()
	{
		currentLevel++;
		em.switchingLevels = true;
	}
	
	//Notify that the scene is ready to switch levels.
	public void notifyReady()
	{
		em.clear();
		levels[currentLevel] = new Level();
		levels[currentLevel].loadLevelFromCXMT(player, em, "assets/" + levelNames[currentLevel]);
		em.switchingLevels = false;
	}
	
	public void triggerTest(boolean v)
	{
		if(v)
		{
			blockShader.bind();
			blockShader.setInt("testing", 1);
			playerShader.bind();
			playerShader.setInt("testing", 1);
		}
		else
		{
			blockShader.bind();
			blockShader.setInt("testing", 0);
			playerShader.bind();
			playerShader.setInt("testing", 0);
		}
	}
	
}
