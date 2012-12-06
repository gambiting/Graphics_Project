package b0538705;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class Support {

	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 600;
	public static int enemiesSettled=0;
	
	public static ArrayList<AbstractEntity> entitiesToDestroy = new ArrayList<AbstractEntity>();
	public static ArrayList<AbstractEntity> entitiesToAdd = new ArrayList<AbstractEntity>();
	public static float distance(float x1,float y1, float x2,float y2)
	{
		return (float) Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
}
