package b0538705;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public abstract class AbstractEntity  {
	
	static final float DEFAULT_SCALE=20f;
	static final float DEFAULT_ANGLE=0f;
	static final float DEFAULT_XSPEED=0.0f;
	public final float DEFAULT_YSPEED=0.0f;
	public final float DEFAULT_ROTO_SPEED=0.0f;
	
	protected float xBase;
	protected float yBase;
	
	protected float x;
	protected float y;
	protected float xSpeed=DEFAULT_XSPEED;
	protected float ySpeed=DEFAULT_YSPEED;
	protected float scale = DEFAULT_SCALE;
	protected float angle = DEFAULT_ANGLE;
	protected float rotationSpeed=DEFAULT_ROTO_SPEED;
	
	public static Texture texture;
	
	public AbstractEntity()
	{
		
	}
	
	/*
	 * abstract method for getting textures(used by inheriting classes)
	 */
	public abstract Texture getTexture();


	public AbstractEntity(float X,float Y)
	{
		this.x = X;
		this.y = Y;
		this.xBase = X;
		this.yBase = Y;
	}
	
	
	
	public void draw(float angle,float scale)
	{
		this.angle = angle;
		this.scale = scale;
		draw();
	}
	
	public void draw()
	{
		//update the object
		update();
		
		
		//enable textures(they might have been disabled for drawing primitives
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		Color.white.bind();
		getTexture().bind();
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0.0f);
		GL11.glRotatef(angle, 0f, 0f, 1f);
		GL11.glScalef(scale, scale, 0f);

		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glNormal3f( 0.0f, 0.0f, 1.0f); 
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(-1,-1);
		GL11.glNormal3f( 0.0f, 0.0f, 1.0f); 
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(1,-1);
		GL11.glNormal3f( 0.0f, 0.0f, 1.0f); 
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(1,1);
		GL11.glNormal3f( 0.0f, 0.0f, 1.0f); 
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(-1,1);

		GL11.glEnd();

		GL11.glPopMatrix();
	}
	
	
	protected void update() {
		angle+=rotationSpeed;
		
		
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




}
