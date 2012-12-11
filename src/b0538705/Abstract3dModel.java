package b0538705;

import java.io.File;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Abstract3dModel {
	
	
	static final float DEFAULT_SCALE=20f;
	static final float DEFAULT_ANGLE=0f;
	static final float DEFAULT_XSPEED=0.0f;
	public final float DEFAULT_YSPEED=0.0f;
	public final float DEFAULT_ROTO_SPEED=0.0f;
	
	protected float xBase;
	protected float yBase;
	
	protected float x;
	protected float y;
	protected float z;
	protected float xSpeed=DEFAULT_XSPEED;
	protected float ySpeed=DEFAULT_YSPEED;
	protected float scale = DEFAULT_SCALE;
	protected float angle = DEFAULT_ANGLE;
	protected float rotationSpeed=DEFAULT_ROTO_SPEED;
	
	Model m = null;
	int displayList = 0;
	int cullFacesDirection = GL11.GL_FRONT;
	
public Abstract3dModel(String filepath)
{
	try {
		m = OBJLoader.loadModel(new File(filepath));
	} catch (Exception e1) {
		e1.printStackTrace();
	} 
	
	this.displayList = prepareList();
	
}
//empty constructor,useful for copying
public Abstract3dModel()
{
	
}
	
public int prepareList()
{
	int objectDisplayList = GL11.glGenLists(1);
	GL11.glNewList(objectDisplayList, GL11.GL_COMPILE);
	
	
	
	int currentTexture=-1;
	//GL11.glDisable(GL11.GL_TEXTURE_2D);
	Face face = null;
	for(int i=0;i<m.faces.size();i++)
	{
		
		
		face=m.faces.get(i);
		if(face.texture!=currentTexture)
		{
			currentTexture = face.texture;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture);
		}

		GL11.glColor3f(1f, 1f, 1f);
		GL11.glBegin(GL11.GL_TRIANGLES);
		
		Vector3f n1 = m.normals.get((int) face.normal.x );
		GL11.glNormal3f(n1.x, n1.y, n1.z);
		Vector2f t1 = m.texVerticies.get((int) face.textures.x);
		GL11.glTexCoord2f(t1.x, t1.y);
		Vector3f v1 = m.verticies.get((int) face.vertex.x);
		GL11.glVertex3f(v1.x, v1.y, v1.z);
		
		
		
		Vector3f n2 = m.normals.get((int) face.normal.y);
		GL11.glNormal3f(n2.x, n2.y, n2.z);
		Vector2f t2 = m.texVerticies.get((int) face.textures.y);
		GL11.glTexCoord2f(t2.x, t2.y);
		Vector3f v2 = m.verticies.get((int) face.vertex.y);
		GL11.glVertex3f(v2.x, v2.y, v2.z);

		
		Vector3f n3 = m.normals.get((int) face.normal.z);
		GL11.glNormal3f(n3.x, n3.y, n3.z);
		Vector2f t3 = m.texVerticies.get((int) face.textures.z);
		GL11.glTexCoord2f(t3.x, t3.y);
		Vector3f v3 = m.verticies.get((int) face.vertex.z);
		GL11.glVertex3f(v3.x, v3.y, v3.z);
		
		//System.out.println(t1.x + "/" + t1.y + "\n" + t2.x + "/" + t2.y + "\n" +t3.x + "/" + t3.y);
		//System.out.println(v1.x + "/" + v1.y + "/" + v1.z + "\n" + v2.x + "/" + v2.y + "/" + v2.z + "\n" +v3.x + "/" + v3.y + "/" + v3.z);
		//System.out.println(n1.x + "/" + n1.y + "/" + n1.z + "\n" + n2.x + "/" + n2.y + "/" + n2.z + "\n" +n3.x + "/" + n3.y + "/" + n3.z);
		GL11.glEnd();
	}
	//GL11.glEnable(GL11.GL_TEXTURE_2D);
	GL11.glEndList();
	
	
	return objectDisplayList;
}


public abstract Abstract3dModel shallowCopy();


public void draw()
{
	GL11.glPushMatrix();
	//GL11.glColor3f(1f, 1f, 1f);
	GL11.glTranslatef(this.x, this.y, -this.z);
	GL11.glScalef(this.scale,this.scale,this.scale);
	GL11.glRotatef(this.angle, 0f, 0f, 0f);
	GL11.glCallList(displayList);
	GL11.glPopMatrix();
}

public float getxBase() {
	return xBase;
}

public void setxBase(float xBase) {
	this.xBase = xBase;
}

public float getyBase() {
	return yBase;
}

public void setyBase(float yBase) {
	this.yBase = yBase;
}

public float getX() {
	return x;
}

public void setX(float x) {
	this.x = x;
}

public float getY() {
	return y;
}

public void setY(float y) {
	this.y = y;
}

public float getZ() {
	return z;
}

public void setZ(float z) {
	this.z = z;
}

public float getxSpeed() {
	return xSpeed;
}

public void setxSpeed(float xSpeed) {
	this.xSpeed = xSpeed;
}

public float getySpeed() {
	return ySpeed;
}

public void setySpeed(float ySpeed) {
	this.ySpeed = ySpeed;
}

public float getScale() {
	return scale;
}

public void setScale(float scale) {
	this.scale = scale;
}

public float getAngle() {
	return angle;
}

public void setAngle(float angle) {
	this.angle = angle;
}

public float getRotationSpeed() {
	return rotationSpeed;
}

public void setRotationSpeed(float rotationSpeed) {
	this.rotationSpeed = rotationSpeed;
}

public Model getM() {
	return m;
}




}
