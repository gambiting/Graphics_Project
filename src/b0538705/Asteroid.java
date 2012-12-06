package b0538705;

import org.newdawn.slick.opengl.Texture;

public class Asteroid extends AbstractEntity {
	
	public static Texture texture;
	public static float DEFAULT_SCALE=40f;
	public static float DEFAULT_ROTO_SPEED=0.1f;
	public static int HITS_TO_DESTROY=5;
	
	//always initially set to one to avoid division by zero
	private int hitsTaken=1;


	public Asteroid(float X, float Y) {
		super(X,Y);
		this.scale=DEFAULT_SCALE;
		this.angle=(float) (Math.random()*360f);
		this.rotationSpeed=DEFAULT_ROTO_SPEED;
		
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
