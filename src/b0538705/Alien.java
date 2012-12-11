package b0538705;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Alien extends AbstractEntity {
	
	public static EnemyModel enemyModel;
	
	public static Texture texture;
	int counter;
	
	float modelScale = 0.4f;
	public Alien()
	{
		y=Support.SCREEN_HEIGHT*2+scale;
		
		GridObject g = Grid.getFreePlace();
		x=(float) (Math.random()*Support.SCREEN_WIDTH);

		xBase = g.getX();
		yBase = g.getY();
		
		g.setObject(this);
		
		Grid.placesMap.put(this, g);
		
		
	}

	public Alien(float X, float Y) {
		super(X, Y);
		
		y=Support.SCREEN_HEIGHT+scale;
		counter = (int) (Math.random()*100);
	}
	
	public void draw(float angle,float scale)
	{
		this.update();
		super.draw(angle, scale);
	}
	
	public void draw()
	{
		this.update();
		super.update();
	
		GL11.glEnable(GL11.GL_TEXTURE_2D);


		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0.0f);
		GL11.glRotatef(90, 0f, 0f, 1f);
		GL11.glScalef(modelScale, modelScale, modelScale*3);
		
		Alien.enemyModel.draw();

		GL11.glPopMatrix();

	}
	
	protected void update()
	{
		
		
		counter++;
		//x+=xSpeed;
		
		
		//reverse direction
		if(x+scale > Support.SCREEN_WIDTH || x-scale<0)
			xSpeed=-xSpeed;
		angle+=0.5;
		
		
		GridObject g = Grid.placesMap.get(this);
		xBase = g.getX();
		yBase = g.getY();
		
		
		boolean coordinatesChanged=false;
		if( Math.abs(yBase-y)>5)
		{
			y+=(yBase-y)/100.0f;
			coordinatesChanged=true;
		}
		if( Math.abs(xBase-x)>5)
		{
			x+=(xBase-x)/50.0f;
			coordinatesChanged=true;
		}
		
		//add to the number of enemies that settled down - for the start of the game
		if(Support.enemiesSettled < Main.ALIENS_NUM && !coordinatesChanged)
		{
			Support.enemiesSettled++;
		}
	}
	
	public Texture getTexture()
	{
		return Alien.texture;
	}
	
	
	
	

}
