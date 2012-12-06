package b0538705;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Font;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


/*
 * Graphics for this project come from a website called opengameart.org
 * and are distributed under CreativeCommons Licence, which means they are free to use and redistribute without the permission of the author.
 * 
 * 
 */
public class Main {

	//set in the Grid class
	public static int ALIENS_NUM;
	public static int ALIENS_TO_DESTROY=50;
	private ArrayList<AbstractEntity> entities;

	private float angle = 0.0f;
	private boolean quit = false;
	private Player player;
	private Grid grid;
	private UnicodeFont fpsFont;
	private int points=0;
	private int lives=3;

	private int wave=1;
	private boolean displayWave=true;
	private int displayWaveFrames=240;
	private int displayWaveCounter=0;
	private boolean fail=false;
	/*
	 * shooting frequency is expressed in the number of frames in between which the aliens should shoot
	 * 
	 * originally planed to use the Timer class,and set up the timer to fire off every X miliseconds, adding bullets,
	 * but it would have to add them to the Support.entitiesToAdd array, which may be modified at the very same time
	 * since the Timer runs in its own thread - and that would cause comodification exception
	 * don't know how to solve that yet, planning to get it done for the second coursework.
	 */
	private final int shootingFrequency=40;
	private int shootingCounter=0;

	private boolean left=false,right=false,up=false,down=false;

	public void start() {

		//init display
		try {
			Display.setDisplayMode(new DisplayMode(Support.SCREEN_WIDTH, Support.SCREEN_HEIGHT));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		//init gl
		GL11.glEnable(GL11.GL_TEXTURE_2D);               
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          
		
		//GL11.glLoadIdentity();
		//GL11.glOrtho(0, Support.SCREEN_WIDTH, 0, Support.SCREEN_HEIGHT, 1, -1);
		//GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		

		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective (60.0f,800f/600f, 0.1f, 1000.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		// enable alpha blending
				
				
		
	

		//load textures and font
		try {
			Player.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/player_ship.png"));
			Alien.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ufo.png"));
			Asteroid.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/asteroid.png"));

			Texture shard1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/asteroid_shard1.png"));
			Texture shard2 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/asteroid_shard2.png"));
			AsteroidShard.shardTextures.add(shard1);
			AsteroidShard.shardTextures.add(shard2);

			Explosion.animationTextures.add(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ex1.png")));
			Explosion.animationTextures.add(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ex2.png")));
			Explosion.animationTextures.add(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ex3.png")));
			Explosion.animationTextures.add(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ex4.png")));


			//font 
			String fontPath = "res/Acens.ttf";
			fpsFont = new UnicodeFont(fontPath, 20, true, false);
			fpsFont.addAsciiGlyphs();
			fpsFont.addGlyphs(400, 600);
			fpsFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
			fpsFont.loadGlyphs();
		} catch (Exception e) {
			e.printStackTrace();
		}


		//init everything else

		grid = new Grid();

		player = new Player();
		entities = new ArrayList<AbstractEntity>();


		//create the given number of aliens
		for(int i=0;i<ALIENS_NUM;i++)
		{
			entities.add(new Alien());
		}

		//create 4 asteroids
		entities.add(new Asteroid(Support.SCREEN_WIDTH*0.2f,Support.SCREEN_HEIGHT*0.3f));
		entities.add(new Asteroid(Support.SCREEN_WIDTH*0.4f,Support.SCREEN_HEIGHT*0.3f));
		entities.add(new Asteroid(Support.SCREEN_WIDTH*0.6f,Support.SCREEN_HEIGHT*0.3f));
		entities.add(new Asteroid(Support.SCREEN_WIDTH*0.8f,Support.SCREEN_HEIGHT*0.3f));


		//render
		render();

		Display.destroy();
	}

	private void render()
	{

		while (!Display.isCloseRequested() && !quit) {
			angle+=1f;
			grid.update();
			
			

			// Clear the screen and depth buffer
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
			GLU.gluLookAt(player.getX(), -100f, 600f, player.getX(), 400.0f, 0.0f, 0f,1f,0f);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.5f);
			GL11.glDepthMask(true);
			


			//draw the player
			player.draw();


			//draw all the entities
			for(AbstractEntity e:entities)
			{
				e.draw();
			}

			pollInput();

			//if left is pressed,but not right
			if(left && !right)
			{
				player.setxSpeed(-Player.DEFAULT_XSPEED);

			}
			//if right is pressed,but not left
			else if(!left && right)
			{
				player.setxSpeed(Player.DEFAULT_XSPEED);
			}
			//if neither left nor right are pressed
			else if(!left && !right)
			{
				player.setxSpeed(0f);
			}


			//collisions
			processCollisions();

			//process enemies shooting
			enemyFire();

			//cleaning up
			cleanUp();

			//display overlay
			displayOverlay();

			//sync the display to provide 60fps
			Display.sync(60);
			//update the display
			Display.update();
		}
	}

	public void pollInput() {



		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				//allow movement only if player hasn't failed yet
				if(!fail)
				{
					if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
						left=true;
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						right=true;
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
						//shooting
						
						/*
						 * and yes, this means that there will be one bullet fired for every key press
						 * but that's intented and that is my method of limiting the rate of fire
						 * play the game to see why - there's hundreds of space invaders coming, you need to be able to fire that fast!
						 * you can't fire by holding the button down,so I am limiting the speed of shooting
						 */
						entities.add(new Bullet(player.getX(),player.getY()+player.getScale()));
					}
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
				{
					quit=true;
				}
			} else {
				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
					left=false;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
					right=false;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					System.out.println("D Key Released");
				}
			}
		}
	}

	public void displayOverlay()
	{
		//display overlay

		//display the red bar

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPushMatrix();
		GL11.glColor3f(1.0f, 0, 0);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2f(0, Support.SCREEN_HEIGHT*0.38f);
		GL11.glVertex2f(Support.SCREEN_WIDTH, Support.SCREEN_HEIGHT*0.38f);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		//enemies left string
		String string = "Enemies left: " + (ALIENS_TO_DESTROY-points);

		GL11.glPushMatrix();
		GL11.glTranslatef(5f, fpsFont.getHeight(string)+5f, 0f);
		GL11.glScalef(1.0f, -1.0f, 1.0f);
		fpsFont.drawString(0, 0,  string);
		GL11.glPopMatrix();


		//lives left string
		string = "Lives left: " + lives;

		GL11.glPushMatrix();
		GL11.glTranslatef(Support.SCREEN_WIDTH-fpsFont.getWidth(string)-5f, fpsFont.getHeight(string)+5f, 0f);
		GL11.glScalef(1.0f, -1.0f, 1.0f);
		fpsFont.drawString(0, 0,  string);
		GL11.glPopMatrix();


		//wave string
		if(displayWave)
		{
			displayWaveCounter++;
			string = "Wave: " + wave;

			GL11.glPushMatrix();
			GL11.glTranslatef(Support.SCREEN_WIDTH/2-(fpsFont.getWidth(string)/2), Support.SCREEN_HEIGHT/2f, 0f);
			GL11.glScalef(1.0f, -1.0f, 1.0f);
			fpsFont.drawString(0, 0,  string);
			GL11.glPopMatrix();


			if(displayWaveCounter==displayWaveFrames)
			{
				displayWave=false;
				displayWaveCounter=0;
			}
		}


		//game over screen
		if(fail)
		{
			string = "Game Over!";

			GL11.glPushMatrix();
			GL11.glTranslatef(Support.SCREEN_WIDTH/2-(fpsFont.getWidth(string)/2), Support.SCREEN_HEIGHT/2f, 0f);
			GL11.glScalef(1.0f, -1.0f, 1.0f);
			fpsFont.drawString(0, 0,  string);
			GL11.glPopMatrix();
		}
	}

	public void enemyFire()
	{

		//increment the counter
		shootingCounter++;

		//check whatever it's time to shoot
		if(shootingCounter==shootingFrequency)
		{
			//get the list of enemies with clear line of sight
			ArrayList<AbstractEntity> enemies= grid.findEnemiesWithClearLineOfSight();

			//if there were any enemies returned
			if(enemies.size()>0)
			{

				//a random enemy among these will shoot
				int enemyToShoot = (int) Math.round((Math.random()*(double)(enemies.size()-1)));

				//get that enemy object
				AbstractEntity a = enemies.get(enemyToShoot);

				//create a new bullet for that enemy
				Support.entitiesToAdd.add(new AlienBullet(a.getX(), a.getY()-a.getScale()));

			}
			//reset the counter
			shootingCounter=0;
		}
	}


	public void processCollisions()
	{

		//go through all the entities
		for(AbstractEntity e:entities)
		{

			//process bullets
			if(e.getClass().equals(Bullet.class) || e.getClass().equals(AlienBullet.class))
			{
				//remove the bullet if it goes off the screen
				if(e.getY()>Support.SCREEN_HEIGHT || (e.getY()+e.getScale())<0)
				{
					Support.entitiesToDestroy.add(e);
				}
				//collision with the player
				else if(Support.distance(e.getX(), e.getY(), player.getX(), player.getY())<player.getScale())
				{
					Support.entitiesToDestroy.add(e);
					Support.entitiesToAdd.add(new Explosion(player.getX(),player.getY()));
					lives--;

					player.setX(Support.SCREEN_WIDTH/2.0f);

					if(lives<=0)
					{
						fail=true;
						//move the player off screen
						player.setY(0-player.getScale());

						//stop the grid from moving downwards
						grid.setySpeed(0.0f);
					}

				}				
				else
				{

					//look for collisions between bullets and entities
					for(AbstractEntity e2:entities)
					{

						//collisions between bullets and aliens
						if(e2.getClass().equals(Alien.class))
						{

							//if the distance is smaller than Alien's scale(so the bullet is within the radius of the enemy)
							if(Support.distance(e.getX(), e.getY(), e2.getX(), e2.getY())<e2.getScale())
							{
								//add both the bullet and the alien to the list of things that need to be removed
								Support.entitiesToDestroy.add(e2);
								Support.entitiesToDestroy.add(e);

								//add an explosion at that spot
								Support.entitiesToAdd.add(new Explosion(e2.getX(), e2.getY()));

								//TESTING - shooting bullets every time an enemy is destroyed
								/*for(AbstractEntity a:grid.findEnemiesWithClearLineOfSight())
								{
									Support.entitiesToAdd.add(new AlienBullet(a.getX(), a.getY()));
								}*/



							}
						}
						//collisions between bullets and asteroids
						else if(e2.getClass().equals(Asteroid.class))
						{
							//if the distance between the bullet and the asteroid is smaller than the latter's scale,then collision occured
							if(Support.distance(e.getX(), e.getY(), e2.getX(), e2.getY())<e2.getScale())
							{
								//add the bullet to the list of things that need to be removed
								Support.entitiesToDestroy.add(e);

								int hitsTaken = ((Asteroid)e2).getHitsTaken();
								int hitsToDestroy = Asteroid.HITS_TO_DESTROY;


								//make the asteroid 10% smaller with every hit
								e2.setScale(Asteroid.DEFAULT_SCALE-Asteroid.DEFAULT_SCALE*(float)hitsTaken*0.1f);

								//increase the number of hits taken
								((Asteroid)e2).setHitsTaken(((Asteroid)e2).getHitsTaken()+1);

								//create an asteroid shard at the bullet's position
								AsteroidShard shard1 = new AsteroidShard(e.getX(),e.getY());
								//give it a reverse bullet speed on the Y axis
								shard1.setySpeed(-e.getySpeed()/4.0f);
								//and a random speed on the X axis
								shard1.setxSpeed((float) (Math.random()*2.0f));
								//add it to the game
								Support.entitiesToAdd.add(shard1);

								//same as above,exept this one is going to move to opposite direction on the X axis
								AsteroidShard shard2 = new AsteroidShard(e.getX(),e.getY());
								shard2.setySpeed(-e.getySpeed()/4.0f);
								shard2.setxSpeed((float) -(Math.random()*2.0f));

								Support.entitiesToAdd.add(shard2);

								//if the asteroid had enough hits then remove it
								if(hitsTaken==hitsToDestroy)
								{
									Support.entitiesToDestroy.add(e2);
									//add an explosion at that spot
									Support.entitiesToAdd.add(new Explosion(e2.getX(), e2.getY()));
								}
							}
						}
					}
				}
			}


		}

		//also make the player loose if the aliens reach the red line
		if(grid.getY()+Grid.SPACING<=Support.SCREEN_HEIGHT*0.38f)
		{
			fail=true;
			grid.setySpeed(0.0f);
		}

	}

	public void cleanUp()
	{
		//clean up
		//remove elements
		for(AbstractEntity e:Support.entitiesToDestroy)
		{

			//remove the entity from the array
			entities.remove(e);



			if(e.getClass().equals(Alien.class))
			{
				//set the alien associated with that place to null
				if(Grid.placesMap.get(e)!=null)
				{
					Grid.placesMap.get(e).setObject(null);
					//remove that mapping
					Grid.placesMap.remove(e);
				}



				//increase the number of points
				points++;


				if(points<=ALIENS_TO_DESTROY-ALIENS_NUM)
				{
					//generate a new alien
					entities.add(new Alien());
				}else if(points==ALIENS_TO_DESTROY)
				{
					//reset
					points=0;
					wave++;
					ALIENS_TO_DESTROY=wave*50;
					displayWave=true;





					//reset grid

					//get the movement difference on both axis
					float diffX=grid.getX()-Grid.DEFAULT_X;
					float diffY=grid.getY()-Grid.DEFAULT_Y;

					//reset grid itself
					grid.setX(Grid.DEFAULT_X);
					grid.setY(Grid.DEFAULT_Y);
					grid.setxSpeed(Grid.DEFAULT_XSPEED);
					grid.setySpeed(Grid.DEFAULT_YSPEED);

					//reset positions on the grid using the difference on both axis
					for(GridObject g:grid.places)
					{
						g.setX(g.getX()-diffX);
						g.setY(g.getY()-diffY);
					}

					//generate aliens again
					for(int i=0;i<ALIENS_NUM;i++)
					{
						entities.add(new Alien());
					}
				}

			}
		}
		//add elements
		for(AbstractEntity e:Support.entitiesToAdd)
		{
			entities.add(e);
		}

		//clear both arrays
		Support.entitiesToDestroy.clear();
		Support.entitiesToAdd.clear();
	}



	public static void main(String[] argv) {
		Main main = new Main();
		main.start();
	}
}
