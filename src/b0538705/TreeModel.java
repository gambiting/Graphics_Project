package b0538705;

import org.lwjgl.opengl.GL11;

public class TreeModel extends Abstract3dModel {
	
	
	public static String DEFAULT_MODEL_PATH = "res/cartoon2.obj";
	
	public TreeModel()
	{
		super();
	}

	public TreeModel(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		GL11.glPushMatrix();
		//GL11.glRotatef(180f,0,1f,0);
		//GL11.glCullFace(GL11.GL_FRONT);
		super.draw();
		//GL11.glCullFace(GL11.GL_BACK);
		GL11.glPopMatrix();
	}

	@Override
	public Abstract3dModel shallowCopy() {
		TreeModel copy = new TreeModel();
		copy.angle = this.angle;
		copy.x = this.x;
		copy.y = this.y;
		copy.z = this.z;
		copy.displayList = this.displayList;
		copy.m = this.m; //shallow copy,but that's ok
		copy.rotationSpeed = this.rotationSpeed;
		copy.xBase = this.xBase;
		copy.yBase = this.yBase;
		copy.xSpeed = this.xSpeed;
		copy.ySpeed = this.ySpeed;
		
		
		return copy;
	}
	
	
	
	

}
