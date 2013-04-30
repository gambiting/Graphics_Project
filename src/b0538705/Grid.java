package b0538705;

import java.util.ArrayList;
import java.util.HashMap;


/*
 * structure to manage the positions of aliens within the grid
 */



public class Grid {



	static final int WIDTH=5;
	static final int HEIGHT=4 ;

	static final float DEFAULT_XSPEED=4.0f;
	static final float DEFAULT_YSPEED=-0.1f;
	
	static float DEFAULT_X;
	static float DEFAULT_Y;

	//actually doubled in the game
	static final float SPACING=10f;

	private float x,y;
	private float xSpeed=DEFAULT_XSPEED,ySpeed=DEFAULT_YSPEED;
	private float gridWidth,gridHeight;
	public static ArrayList<GridObject> places;

	public static HashMap<AbstractEntity,GridObject> placesMap;

	public Grid()
	{

		//set the number of aliens to be exactly the number of places on the grid
		Main.ALIENS_NUM = WIDTH*HEIGHT;
		//initialize a new array for places on the grid
		places = new ArrayList<GridObject>();
		//create a new map binding entities to their places
		placesMap = new HashMap<AbstractEntity,GridObject>();
		//calculate x
		x=(Support.SCREEN_WIDTH/2)-(WIDTH*(AbstractEntity.DEFAULT_SCALE*2f+SPACING*2))/2f;
		Grid.DEFAULT_X= x;
		//calculate y
		y=(Support.SCREEN_HEIGHT*0.75f)-(HEIGHT*(AbstractEntity.DEFAULT_SCALE*2f+SPACING))/2;
		Grid.DEFAULT_Y=y;

		//generate grid objects
		for(int i=0;i<HEIGHT;i++)
		{
			for(int j=0;j<WIDTH;j++)
			{
				/*
				 * easy formula - each entity occupies its width/height+spacing
				 */
				places.add(new GridObject(x+j*(AbstractEntity.DEFAULT_SCALE+SPACING)*2+AbstractEntity.DEFAULT_SCALE+SPACING,y+i*(AbstractEntity.DEFAULT_SCALE+SPACING)*2+AbstractEntity.DEFAULT_SCALE+SPACING));
			}
		}

		gridWidth = AbstractEntity.DEFAULT_SCALE*2f*WIDTH + WIDTH * SPACING*2f;
		gridHeight = AbstractEntity.DEFAULT_SCALE*2f*HEIGHT + HEIGHT * SPACING*2f;


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

	public void update()
	{

		/*
		 * this check is used to check whatever the enemies have "settled down" before the grid starts moving
		 */
		if(Support.enemiesSettled==Main.ALIENS_NUM)
		{
			//move the grid
			x+=xSpeed;
			y+=ySpeed;


			//move the objects in the grid
			for(GridObject g:places)
			{
				g.setX(g.getX()+xSpeed);
				g.setY(g.getY()+ySpeed);
			}

			//reverse direction
			//if the grid reached any of the edges
			if(x<0 || (x+gridWidth)>Support.SCREEN_WIDTH)
			{
				xSpeed=-xSpeed;
			}
		}
	}

	
	//find an unnocupied place
	//returns null if none left
	public static GridObject getFreePlace()
	{
		//go through the places
		for(GridObject g:Grid.places)
		{
			//if it has no object associated with it,return it
			if(g.getObject()==null)
			{
				return g;
			}
		}

		return null;
	}

	
	/*
	 * returns an array of enemies that have no other enemies in front of them
	 * the amount of enemies returned is always equal to the width of the grid
	 */
	public ArrayList<AbstractEntity> findEnemiesWithClearLineOfSight()
	{
		//create the array
		ArrayList<AbstractEntity> enemies = new ArrayList<AbstractEntity>();
		for(GridObject g:places)
		{
			//if enemy is within the first row,just add it straight away
			if(places.indexOf(g)<WIDTH)
			{
				if(g.getObject() != null)
				{
					enemies.add(g.getObject());
				}
			}else if(enemies.size()<=WIDTH && g.getObject()!=null && places.indexOf(g)>=WIDTH)
			{
				boolean nothingInFront=true;
				//check whatever there's nothing in front
				for(int i=places.indexOf(g)-WIDTH;i>=0;i=i-WIDTH)
				{
					if(places.get(i).getObject() != null)
					{
						nothingInFront=false;
					}
				}
				//if there's nothing in front then add this enemy to the list.
				if(nothingInFront)
				{
					enemies.add(g.getObject());
				}
			}
		}

		return enemies;
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

	public ArrayList<GridObject> getPlaces() {
		return places;
	}

}
