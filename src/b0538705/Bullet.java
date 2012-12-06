package b0538705;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Bullet extends AbstractEntity {

	public final float DEFAULT_SCALE=5f;
	public final float DEFAULT_XSPEED=0.0f;
	public final float DEFAULT_YSPEED=8.0f;
	
	private float xSpeed=DEFAULT_XSPEED;
	protected float ySpeed;

	public Bullet(float X, float Y) {
		super(X, Y);
		this.ySpeed=DEFAULT_YSPEED;
		this.scale = DEFAULT_SCALE;
	}


	public void draw()
	{
		//update the object
		update();
		
		//disable textures
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		//Color.red.bind();

		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0.0f);
		GL11.glRotatef(angle, 0f, 0f, 1f);
		GL11.glScalef(scale, scale, 0f);
		
		GL11.glColor3f(1f, 0f, 0f);

		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(-0.5f,-1);
		GL11.glVertex2f(0.5f,-1);
		GL11.glVertex2f(0.5f,1);
		GL11.glVertex2f(-0.5f,1);

		GL11.glEnd();

		GL11.glPopMatrix();
		
		
	}
	
	protected void update()
	{
		x+=xSpeed;
		y+=ySpeed;
		super.update();
	}


	@Override
	public Texture getTexture() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public float getySpeed() {
		// TODO Auto-generated method stub
		return ySpeed;
	}
	
	
	

}
