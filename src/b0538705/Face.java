package b0538705;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public class Face {
	public Vector3f vertex = new Vector3f();
	public Vector3f normal = new Vector3f();
	public Vector3f textures = new Vector3f();
	public int texture;
	
	public Face(Vector3f vertex,Vector3f textures,Vector3f normal,int texture)
	{
		this.vertex = vertex;
		this.normal = normal;
		this.textures = textures;
		this.texture = texture;
	}
}
