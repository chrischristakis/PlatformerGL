package ca.chrischristakis.pgl;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import ca.chrischristakis.pgl.input.KeyInput;
import ca.chrischristakis.pgl.scene.Scene;

public class Main implements Runnable
{

	/*/
	 * TODO:
	 * -FIx movement:
	 * 	-terminal velocity
	 * 	-Proper hitboxes that dont overlap
	 * 
	 * Bugs:
	 * 
	 *-Collision snapping.
	/*/
	
	/*
	 * Learned:
	 * -MVP matrix shouldnt be a part of entities.
	 * -Projection matrix to be used as a zoom feature
	 * -View matrix as a camera
	 * -Make x and y positions an int, not a float. Never deal with float pixel values, makes weird collision errors.
	 * -Batching is important to reduce drawcalls, implement in next game
	 * -Improve CTMX files to be more space efficient and parser friendly, for example group double digit id in |x|, such as 12342|22|21 <- 22 is only double digit one there
	 * -Make level an array of tiles, with x and y position. Then call renderQuad(x,y) instead of having individual block objects.
	 * -Make a renderer class! renderer.renderQuad, renderer.renderTriangle etc. Uses batch method.
	 * -Save level data upside down if possible (left up or left down.) since y = 0 at beginning in level loader
	 * -Overhaul the entire level system. Confusing unintuitive mess right now
	 * -Use GL_CLAMP_TO_EDGE when using alpha values, because GL_REPEAT interpolates with the colours around it. LearnOpengl speaks on this better
	 */
	
	public static final int WIDTH = 1100, HEIGHT = 900;
	public static boolean running, testing;
	
	private Scene scene;
	
	private long window;
	private Thread t;
	
	public void start()
	{
		t = new Thread(this);
		running = true;
		t.start();
	}
	
	public void init()
	{
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		window = glfwCreateWindow(WIDTH, HEIGHT, "PlatformerGL", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		glfwSetKeyCallback(window, new KeyInput());
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - WIDTH) / 2, (vidmode.height() - HEIGHT) / 2);

		glfwMakeContextCurrent(window);
		GL.createCapabilities(); //IMPORTANT, OPENS THE OPENGL BINDINGS
		
		glViewport(0, 0, WIDTH, HEIGHT);
		glClearColor(0.0f, 0.2f, 0.5f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE0);
		
		//glfwSwapInterval(1);

		glfwShowWindow(window);
		
		scene = new Scene();
	}
	
	public void update()
	{
		glfwPollEvents();
		scene.update();
	}
	
	public void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		scene.render();
		
		glfwSwapBuffers(window);
	}
	
	public void run()
	{
		init();
		
		float ups = 60.0f;
		double interval = 1e9/ups;
		long last = System.nanoTime();
		long now;
		double delta = 0;
		
		int cooldown = 1000;
		long cooldownTime = System.currentTimeMillis();
		
		int frames = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();

		while(!glfwWindowShouldClose(window) && running)
		{
			now = System.nanoTime();
			delta += (now - last)/interval;
			last = now;
			
			if(KeyInput.isPressed(GLFW_KEY_T) && !testing && System.currentTimeMillis() - cooldownTime > cooldown)
			{
				cooldownTime = System.currentTimeMillis();
				testing = true;
				scene.triggerTest(true);
			}
			
			
			if(KeyInput.isPressed(GLFW_KEY_T) && testing && System.currentTimeMillis() - cooldownTime > cooldown)
			{
				cooldownTime = System.currentTimeMillis();
				testing = false;
				scene.triggerTest(false);
			}
			
			if(delta >= 1)
			{
				update();
				updates++;
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer >= 1000)
			{
				timer = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}	
		running = false;

		System.out.println("Cleaning up...");
		scene.destory();
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		System.out.println("Terminated.");
	}
	
	public static void main(String[] args) 
	{
		new Main().start();
	}
	
}
