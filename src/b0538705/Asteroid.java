package b0538705;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Asteroid extends AbstractEntity {
	
	public static Texture texture;
	public static float DEFAULT_SCALE=45f;
	public static float DEFAULT_ROTO_SPEED=0.3f;
	public static int HITS_TO_DESTROY=5;
	public static AsteroidModel asteroidModel;
	public float modelScale = 0.015f;
	
	//always initially set to one to avoid division by zero
	private int hitsTaken=1;


	public Asteroid(float X, float Y) {
		super(X,Y);
		this.scale=DEFAULT_SCALE;
		this.angle=(float) (Math.random()*360f);
		this.rotationSpeed=DEFAULT_ROTO_SPEED;
		
	}
	
	

	@Override
	public void draw() {
		super.update();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);


		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, -5f);
		GL11.glRotatef(angle, 1f, 1f, 1f);
		GL11.glScalef(modelScale, modelScale, modelScale);
		
		Asteroid.asteroidModel.draw();

		GL11.glPopMatrix();
	}



	@Override
	public Texture getTexture() {
		return Asteroid.texture;
	}

	public int getHitsTaken() {
		return hitsTaken;
	}

	public void setHitsTaken(int hitsTaken) {
		this.hitsTaken = hitsTaken;
	}


}
