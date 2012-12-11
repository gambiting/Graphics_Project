package b0538705;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Ground {

	public static Texture texture;
	public static int objectDisplayList;

	public static void initGround()
	{
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/grass.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		objectDisplayList = GL11.glGenLists(1);
		GL11.glNewList(objectDisplayList, GL11.GL_COMPILE);
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		for(int i=-6;i<6;i++)
		{
			for(int j=-3;j<3;j++)
			{
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glNormal3f(0, 0, 1f);
				GL11.glTexCoord2f(0.0f, 0.0f);
				GL11.glVertex3f(i*100f, j*100f, 0f);
				
				GL11.glNormal3f(0, 0, 1f);
				GL11.glTexCoord2f(0.5f, 0.0f);
				GL11.glVertex3f((i+1)*100f, j*100f, 0f);
				
				GL11.glNormal3f(0, 0, 1f);
				GL11.glTexCoord2f(0.5f, 0.5f);
				GL11.glVertex3f((i+1)*100f, (j+1)*100f, 0f);
				
				GL11.glNormal3f(0, 0, 1f);
				GL11.glTexCoord2f(0.0f, 0.5f);
				GL11.glVertex3f(i*100f, (j+1)*100f, 0f);


				GL11.glEnd();
			}
		}
		GL11.glEndList();


	}

	public static void drawGround()
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPushMatrix();
		GL11.glTranslatef(400, 600, -200f);
		GL11.glScalef(2f, 2f, 1f);
		GL11.glCallList(objectDisplayList);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

}
