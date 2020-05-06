package ca.chrischristakis.pgl.scene;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import ca.chrischristakis.pgl.Main;
import ca.chrischristakis.pgl.gl.ShaderProgram;
import ca.chrischristakis.pgl.input.KeyInput;
import ca.chrischristakis.pgl.scene.entities.DefaultBlock;
import ca.chrischristakis.pgl.scene.entities.Player;

public class Scene 
{
	
	//Note! (0,0) on an entity is the bottom left corner, like a normal graph in math.
	
	public static Matrix4f projection, view;
	private Player player;
	public static ShaderProgram blockShader, playerShader, hitboxShader;
	
	private EntityManager em;
	private Camera camera;
	private Level[] levels = new Level[2];
	
	public static int currentLevel = 0;
	
	public Scene()
	{
		projection = new Matrix4f().ortho(0.0f, Main.WIDTH, 0.0f, Main.HEIGHT, -10.0f, 10.0f); //should always be the first line.
		view = new Matrix4f();
		
		blockShader = new ShaderProgram("block.vert", "block.frag");
		playerShader = new ShaderProgram("player.vert", "player.frag");
		hitboxShader = new ShaderProgram("player.vert", "hitbox.frag");
		
		player = new Player(0, 0, 50, 100);
		em = new EntityManager(player);
		
		camera = new Camera(player);
		levels[0] = new Level();
		levels[0].loadLevelFromCXMT(player, em, "level1.ctmx");
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
		if(player.position.y < 0)
			levels[currentLevel].reset();
		
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
