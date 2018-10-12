import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject {

	private Handler handler;
	Random r = new Random();
	int choose = 0;
	int hp = 100;
	
	private BufferedImage[] enemy_skin = new BufferedImage[3];
	Animation anime;
	
	public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		
		enemy_skin[0] = ss.grabImage(4, 1, 32, 32);
		enemy_skin[1] = ss.grabImage(5, 1, 32, 32);
		enemy_skin[2] = ss.grabImage(6, 1, 32, 32);
		
		anime = new Animation(3,enemy_skin[0],enemy_skin[1],enemy_skin[2]);

	}


	public void tick() {
		x += velX;
		y += velY;
		
		choose = r.nextInt(10);
		
		for(int i = 0; i < handler.object.size(); i ++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Block) {
				if(getBoundsBig().intersects(tempObject.getBounds())) {
					x += (velX*3) * -1;   // if the enemy contacts the block, they will be pushed back with x5 speed
					y += (velX*3) * -1;
					velX *= -1;
					velY *= -1;
				}
			
				else if(choose == 0) {
				velX = (r.nextInt(8) + -4);  // enemy moves randomly with certain bounds
				velY = (r.nextInt(8) + -4);
				}
			}
			
			if(tempObject.getId() == ID.Bullet) {
				if(getBounds().intersects(tempObject.getBounds())){
					hp -= 50;
				}
			}
		}
		anime.runAnimation();
		if(hp <= 0) handler.removeObject(this);
		
	}

	public void render(Graphics g) {
		
		anime.drawAnimation(g, x, y, 0);
		
		/*
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.GREEN);
		g2d.draw(getBoundsBig());
		*/
	}

	
	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}
	
	public Rectangle getBoundsBig() {
		return new Rectangle(x-16,y-16,64,64);
	}

}
