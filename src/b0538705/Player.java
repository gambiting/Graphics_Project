package b0538705;


import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Player {

	static final float DEFAULT_SCALE=30f;
	static final float DEFAULT_ANGLE=180f;
	static final float DEFAULT_XSPEED=4.0f;
	static final float DEFAULT_YSPEED=0.0f;

	public static Texture texture;

	private float x;
	private float y;
	private float xSpeed=0.0f;
	private float ySpeed=0.0f;
	private float angle=DEFAULT_ANGLE;
	private float scale=DEFAULT_SCALE;
	private float rollAngle = 0f;
	private float maxRollAngle = 30f;
	private float modelScale=0.7f;
	
	private ShipModel model;

	public Player()
	{
		x=Support.SCREEN_WIDTH/2.0f;
		y=50f;
		model = new ShipModel("res/spaceship_blue.obj");
		
		
	}

	public void draw(float angle,float scale)
	{
		this.angle=angle;
		this.scale=scale;
		this.draw();
	}
	public void draw()
	{
		update();


		//enable textures(they might have been disabled for drawing primitives
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		//Color.white.bind();
		//texture.bind();



		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0.0f);
		GL11.glRotatef(90, 1f, 0f, 0f);
		GL11.glRotatef(rollAngle,0f,0f,1f);
		GL11.glScalef(modelScale, modelScale, modelScale);
		
		model.draw();

		GL11.glPopMatrix();
	}

	public void update()
	{
		if((x+xSpeed+scale)<Support.SCREEN_WIDTH && (x+xSpeed-scale)>0)
		{
			x+=xSpeed;
			
			//rolling angle
			if(xSpeed>0 && rollAngle > (-maxRollAngle))
			{
				rollAngle-=3f;
			}
			if(xSpeed<0 && rollAngle<maxRollAngle)
			{
				rollAngle+=3f;
			}
			
		}

		if((y+ySpeed+scale)<Support.SCREEN_HEIGHT && (y+ySpeed-scale)>0)
		{
			y+=ySpeed;
		}
		
		//anti-roll when ship is stationary
		if(xSpeed==0 && rollAngle!=0)
		{
			if(rollAngle>0)
			{
				rollAngle-=2;
			}else
			{
				rollAngle+=2f;
			}
		}
	}

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

	public Float getAngle() {
		return angle;
	}

	public void setAngle(Float angle) {
		this.angle = angle;
	}

	public Float getScale() {
		return scale;
	}

	public void setScale(Float scale) {
		this.scale = scale;
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





}
