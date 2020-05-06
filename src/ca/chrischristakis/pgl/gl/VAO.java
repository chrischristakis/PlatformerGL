package ca.chrischristakis.pgl.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.GL30;

import ca.chrischristakis.pgl.Main;
import ca.chrischristakis.pgl.utils.BufferUtils;

public class VAO 
{
	private int VAO, VBO, EBO, TBO;
	public int numOfVerts;
	public boolean outline;
	
	public VAO(float[] verts, float[] tex, byte[] indices)
	{
		numOfVerts = indices.length;
		VAO = glGenVertexArrays();
		VBO = glGenBuffers();
		TBO = glGenBuffers();
		EBO = glGenBuffers();
		
		bind();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(indices), GL_STATIC_DRAW);
		
		//Pos
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(verts), GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);
		
		//tex
		glBindBuffer(GL_ARRAY_BUFFER, TBO);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(tex), GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);
		
		unbind();
	}
	
	public void bind()
	{
		glBindVertexArray(VAO);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO); //Since not binded to the VAO
	}
	
	public void unbind()
	{
		glBindVertexArray(0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void draw()
	{
		bind();
		if(outline || Main.testing)
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glDrawElements(GL_TRIANGLES, numOfVerts, GL_UNSIGNED_BYTE, 0);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		unbind();
	}
	
	public void destory()
	{
		GL30.glDeleteBuffers(VBO);
		GL30.glDeleteBuffers(TBO);
		GL30.glDeleteBuffers(EBO);
		GL30.glDeleteVertexArrays(VAO);
	}

}
