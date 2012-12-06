package b0538705;

public class GridObject {
	
	private float x,y;
	
	private AbstractEntity object;
	
	public AbstractEntity getObject() {
		return object;
	}

	public void setObject(AbstractEntity object) {
		this.object = object;
	}

	public GridObject(float x, float y)
	{
		this.x = x;
		this.y = y;	
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

}
