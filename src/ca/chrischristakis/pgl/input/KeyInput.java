package ca.chrischristakis.pgl.input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;

import ca.chrischristakis.pgl.Main;

public class KeyInput extends GLFWKeyCallback
{
	public static boolean[] keys = new boolean[65536];
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) 
	{
		if(key < 0) return;
		keys[key] = (action != GLFW_RELEASE);
		if(key == GLFW_KEY_ESCAPE && keys[key])
			Main.running = false;
	}
	
	public static boolean isPressed(int key)
	{
		return keys[key];
	}

}