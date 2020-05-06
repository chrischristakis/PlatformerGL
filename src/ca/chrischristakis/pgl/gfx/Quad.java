package ca.chrischristakis.pgl.gfx;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import ca.chrischristakis.pgl.gl.ShaderProgram;
import ca.chrischristakis.pgl.gl.VAO;
import ca.chrischristakis.pgl.scene.Scene;

public class Quad
{
	private VAO vao;
	public float width, height;
	public Matrix4f model = new Matrix4f();
	
	public Vector3f position;
	
	public Quad(float x, float y, float width, float height)
	{
		position = new Vector3f(x, y, 0.0f);
		this.width = width; this.height = height;
		float[] verts = {
			0.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f
		};
		
		byte[] ind = {
			0, 1, 2,
			2, 3 ,0
		};
		
		float[] tc = {
			0.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f
		};
		
		vao = new VAO(verts, tc, ind);
		model.translate(position);
		model.scale(width, height, 0.0f);
	}
	
	public void setOutline(boolean value)
	{
		vao.outline = value;
	}

	public void render(ShaderProgram shader)
	{
		shader.bind();
		shader.setMat4f("mvp", calculateMvp());
		vao.draw();
		shader.unbind();
	}
	public Matrix4f calculateMvp()
	{
		Matrix4f dest = new Matrix4f();
		dest.mul(Scene.projection);
		dest.mul(Scene.view);
		dest.mul(model);
		return dest;
	}
	
	private void update()
	{
		model.identity();
		model.translate(position.x, position.y, 0.0f);
		model.scale(width, height, 0.0f);
	}
	
	public void updatePos(float x, float y)
	{
		position.x = x; position.y = y;
		update();
	}
	
	public void updateDim(float width, float height)
	{
		this.width = width; this.height = height;
		update();
	}
	
	public void updatePosDim(float x, float y, float width, float height)
	{
		this.width = width; this.height = height;
		position.x = x; position.y = y;
		update();
	}
	
	public void destory()
	{
		vao.destory();
	}
	
}
