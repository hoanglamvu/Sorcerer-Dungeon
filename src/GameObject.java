import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	
	protected int x, y;
	protected float velX = 0, velY= 0;
	protected ID id; // id variable can be put into constructor below
	protected SpriteSheet ss;
	
	public GameObject (int x, int y, ID id,SpriteSheet ss) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.ss = ss;
	}
	
	

	public abstract void tick();   //every object needs to be updated 
	public abstract void render(Graphics g);  //every object needs to appear as something 
	public abstract Rectangle getBounds(); // every object is bounded for collision detection 

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}
	
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
}
