package b0538705;

import org.newdawn.slick.opengl.Texture;

public class Alien extends AbstractEntity {
	
	public static Texture texture;
	int counter;
	
	public Alien()
	{
		y=Support.SCREEN_HEIGHT+scale;
		
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
	
		super.draw();

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
		if(yBase!=y && Math.abs(yBase-y)>1)
		{
			y+=(yBase-y)/100.0f;
			coordinatesChanged=true;
		}
		if(xBase!=x && Math.abs(xBase-x)>1)
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
