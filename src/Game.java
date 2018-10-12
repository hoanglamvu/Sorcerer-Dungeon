import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;



public class Game extends Canvas implements Runnable  {
	
	// serialVersionUID to prevent InvalidClassException 
	private static final long serialVersionUID = 1L;
	
	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	private Camera camera;
	private SpriteSheet ss;
	
	private BufferedImage level = null;
	private BufferedImage sprite_sheet = null;
	private BufferedImage floor = null;
	
	public int ammo = 100; // put here so the ammo bar can be easily drawn out later
	public int hp = 100; // wizard health bar
	public boolean frosted = false; // set up frost bullet

	public Game() {
		new Window(1000,563, "Wizard Game", this);
		start();
		
		handler = new Handler();
		camera = new Camera(0,0);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(new MouseInput(handler,camera,this,ss));
		
				
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/world.png");
		sprite_sheet = loader.loadImage("/sprite_sheet2.png");
		
		ss = new SpriteSheet(sprite_sheet);
		
		floor = ss.grabImage(12, 2, 32, 32);
		
		loadLevel(level);
	}
	
	private void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private void stop() {
		isRunning = false;
		try {
			thread.join();  // join throws so try catch is needed, join waits for the thread to finish 
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	// run houses the game loop 
	public void run() {
		
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				frames = 0;
			}
		}
		stop();		
	}
	
	//tick updates everything in the game 60 times/sec
	public void tick() {
		
		// loop through all of the game object to find the player and put in the center of the window
		for(int i = 0; i < handler.object.size(); i++) {
			if(handler.object.get(i).getId() == ID.Player) {
				camera.tick(handler.object.get(i));
			}  
		}
		
		handler.tick();
	}
	
	//render method renders everything in the game couple 1000s times/sec
	public void render() {
		BufferStrategy bs = this.getBufferStrategy(); // the way the frame is loaded, after a frame is shown, 3 (or whatever) is preloaded behind the window and ready to be shown next 
		if(bs == null) {
			this.createBufferStrategy(3); // 3 is optimal 
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		//////////////////////////////////////////////////////
		// what goes here is displayed onto window
		
		/*
		g.setColor(Color.RED);
		g.fillRect(0, 0, 1000, 563);  // background is rendered 
		*/
	
		g2d.translate(-camera.getX(), -camera.getY());
		
		for(int xx = 0; xx < 30*72; xx += 32) {
			for(int yy = 0; yy < 30*72; yy += 32) {
				g.drawImage(floor, xx, yy, null);
			}
		}
		
		handler.render(g); // objects are rendered after the background 
		
		g2d.translate(camera.getX(), camera.getY());  // everything between -x,x and -y,y is getting translated
		
		/////////////////////////////////////////////////////
		
		g.setColor(Color.gray);
		g.fillRect(5, 5, 200, 32);
		g.setColor(Color.green);
		g.fillRect(5, 5, hp*2, 32); // so when the hp is down the green bar is down
		g.setColor(Color.black);
		g.drawRect(5, 5, hp*2, 32);
		
		g.setColor(Color.white);
		g.drawString("Ammo: " + ammo, 5, 50);
		
		g.dispose();
		bs.show();
	}
	
	// loading the level
	private void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int xx = 0; xx < w; xx++) {
			for(int yy = 0; yy < h; yy++) {      
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255 && green == 0 && blue == 0) handler.addObject(new Block(xx*32,yy*32, ID.Block,ss));
				
				if(blue == 255 && green == 0 && red == 72) handler.addObject(new Wizard(xx*32,yy*32, ID.Player,handler, this,ss));
				
				if (green == 255 && blue == 0) handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handler,ss));
					
				if (green == 255 && blue == 255) handler.addObject(new Crate(xx*32, yy*32, ID.Crate,ss));
				
				if (green == 106 && blue == 0 && red == 255) handler.addObject(new Fury(xx*32, yy*32, ID.Fury,ss));
				
				if (green == 0 && blue == 255 && red == 178) handler.addObject(new Frost(xx*32, yy*32, ID.Frost,ss));
				
				
			}
		}
	}
	
	public static void main(String arg[]) {
		new Game();
	}
}
      