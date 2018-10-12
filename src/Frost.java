import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Frost extends GameObject{

	private BufferedImage frost_skin;

	
	public Frost(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id, ss);
		frost_skin = ss.grabImage(10, 2, 32, 32);
	}

	
	public void tick() {
		
	}

	
	public void render(Graphics g) {
		g.drawImage(frost_skin, x, y, null);		
	}

	
	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);	
	}

}
