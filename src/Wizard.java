import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Wizard extends GameObject {

	Handler handler;
	Game game;
	Bullet bullet;
	
	private BufferedImage[] wizard_skin = new BufferedImage[6];
	
	Animation anime;
	Animation fury_anime;
	
	public Wizard(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
		super(x, y, id,ss);
		this.handler = handler;
		this.game = game;
		
		wizard_skin[0] = ss.grabImage(1, 1, 32, 48);
		wizard_skin[1] = ss.grabImage(2, 1, 32, 48);
		wizard_skin[2] = ss.grabImage(3 , 1, 32, 48);
		wizard_skin[3] = ss.grabImage(7, 1, 32, 48);
		wizard_skin[4] = ss.grabImage(8, 1, 32, 48);
		wizard_skin[5] = ss.grabImage(9, 1, 32, 48);
		
		
		anime = new Animation(3, wizard_skin[0],wizard_skin[1],wizard_skin[2]);
		fury_anime = new Animation(3, wizard_skin[3],wizard_skin[4],wizard_skin[5]);
	}


	public void tick() {
		x += velX;
		y += velY;
		
		collision();
		
		//movement
		if(handler.isUp()) velY = -5;
		else if(!handler.isDown()) velY = 0;
		
		if(handler.isDown()) velY = 5;
		else if(!handler.isUp()) velY = 0;
		

		if(handler.isRight()) velX = 5;
		else if(!handler.isLeft()) velX = 0;
		

		if(handler.isLeft()) velX = -5;
		else if(!handler.isRight()) velX = 0;
		
		anime.runAnimation();
	}
	
	private void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					x += velX * -1;
					y += velY * -1;
				}
			}
			
			if(tempObject.getId() == ID.Crate) {
				if(getBounds().intersects(tempObject.getBounds())) {
					game.ammo += 20;
					handler.removeObject(tempObject);
				}
			}
			
			if(tempObject.getId() == ID.Enemy) {
				if(getBounds().intersects(tempObject.getBounds())) {
					game.hp -= 10;
					handler.removeObject(tempObject);
				}			
			}
			
			if(tempObject.getId() == ID.Fury) {
				if(getBounds().intersects(tempObject.getBounds())) {
					
					game.hp = 200;
					anime = fury_anime;
					wizard_skin[0] = wizard_skin[3];
					
					handler.removeObject(tempObject);
				}			
			}
			
			if(tempObject.getId() == ID.Frost) {
				if(getBounds().intersects(tempObject.getBounds())) {
					game.frosted = true;
					handler.removeObject(tempObject);
				}			
			}
		}
	}
	

	
	public void render(Graphics g) {  
		
			if(velX == 0 && velY == 0) {
				g.drawImage(wizard_skin[0],x,y,null); 
			}
			else {
				anime.drawAnimation(g, x, y, 0);
			}
		
		
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 48); // old val 32, 48
	}

}
