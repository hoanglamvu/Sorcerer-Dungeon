import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Fury extends GameObject {

	private BufferedImage fury_skin;
	
	public Fury(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id, ss);
		fury_skin = ss.grabImage(11, 2, 32, 32);
	}

	
	public void tick() {
		
	}

	
	public void render(Graphics g) {
		g.drawImage(fury_skin, x, y, null);
		
	}

	
	public Rectangle getBounds() {
	
		return new Rectangle(x,y,32,32);
	}

}
