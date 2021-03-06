package b0538705;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class AsteroidShard extends AbstractEntity {
	
	public static float DEFAULT_SCALE=8f;
	public static float SHRINKING_RATE=0.2f;
	public static ArrayList<Texture> shardTextures=new ArrayList<Texture>();
	public Texture texture;
	
	public AsteroidShard(float X,float Y)
	{
		super(X,Y);
		//select a texture for this shard at random
		this.texture = shardTextures.get((int) Math.round(Math.random()*(shardTextures.size()-1)));
		
		this.setScale(DEFAULT_SCALE);
	}
	
	@Override
	public void update()
	{
		this.scale-=SHRINKING_RATE;
		if(this.scale<=0.0f)
		{
			Support.entitiesToDestroy.add(this);
		}
		this.x+=this.xSpeed;
		this.y+=this.ySpeed;
	}
	
	

	@Override
	public void draw() {
		GL11.glDisable(GL11.GL_LIGHTING);
		super.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public Texture getTexture() {
		return this.texture;
	}

}
