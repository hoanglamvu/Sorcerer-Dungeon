import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject {

	private Handler handler;
	Game game;
	
	
	public Bullet(int x, int y, ID id, Handler handler, int mx, int my, Game game, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		this.game = game;
		
		velX = (mx - x)/10;
		velY = (my - y)/10;
		
	}

	
	public void tick() {
		x += velX;
		y += velY;
		
	
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())){
					handler.removeObject(this);
				}
			}
			
		}
	   
		
	}

	
	public void render(Graphics g) {
		if(game.frosted) {
		
			g.setColor(Color.white);
			g.fillOval(x, y, 20, 20);
		}
		else {
			g.setColor(Color.green);
			g.fillOval(x, y, 10, 10);
		}
		
	}

	
	public Rectangle getBounds() {
		
		return new Rectangle(x,y,8,8);
	}

}
