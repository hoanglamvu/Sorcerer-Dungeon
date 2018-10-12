// Camera to allow player to move in the game world
public class Camera {
	private float x, y;
	
	public Camera (float x, float y) {
		this.x = x; 
		this.y = y;
	}
	
	public void tick(GameObject object) {
		x += ((object.getX() - x) - 1000/2) * 0.05f; // lock onto the character
		y += ((object.getY() - y) - 563/2) * 0.05f;
		
		if(x <= 0) x = 0;
		if(x >= 1032) x = 1032;
		if(y <= 0) y = 0;
		if(y >= 609) y = 609;
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
