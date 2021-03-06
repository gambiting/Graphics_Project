package b0538705;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Explosion  extends AbstractEntity {
	
	public final int animationDuration=15;
	public Texture texture;
	public static ArrayList<Texture> animationTextures=new ArrayList<Texture>();
	
	
	private int texturePosition=0;
	private int frameDuration;
	private int numOfRenderedFrames=0;
	private int currentFrameOfAnimation=0;
	
	public Explosion(float x,float y)
	{
		super(x,y);
		texture = animationTextures.get(0);
		frameDuration = animationDuration/animationTextures.size();
	}
	
	
	@Override
	public void draw() {
		update();
		GL11.glDisable(GL11.GL_LIGHTING);
		super.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	
	@Override
	protected void update()
	{
		numOfRenderedFrames++;
		if(numOfRenderedFrames%frameDuration==0)
		{
			currentFrameOfAnimation++;
			if(currentFrameOfAnimation<animationTextures.size())
				texture = animationTextures.get(currentFrameOfAnimation);
		}
		
		if(numOfRenderedFrames==animationDuration)
		{
			Support.entitiesToDestroy.add(this);
		}
	}
	
	@Override
	public Texture getTexture()
	{
		return this.texture;
	}

}
