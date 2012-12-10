package b0538705;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public class Model {
	List<Vector3f> verticies = new ArrayList<Vector3f>();
	List<Vector3f> normals = new ArrayList<Vector3f>();
	List<Face> faces = new ArrayList<Face>();
	List<Vector2f> texVerticies = new ArrayList<Vector2f>();
	List<Texture> textures = new ArrayList<Texture>();
	
}
