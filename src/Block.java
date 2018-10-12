import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

	
	private BufferedImage block_skin;
	
	public Block(int x, int y, ID id,SpriteSheet ss) {
		super(x, y, id, ss);
		
		block_skin = ss.grabImage(5, 2, 32, 32);
	}


	public void tick() {
		
	}


	public void render(Graphics g) {
	
		g.drawImage(block_skin, x, y, null);
	}

	public Rectangle getBounds() {
	
		return new Rectangle(x,y,32,32);
	}

}
