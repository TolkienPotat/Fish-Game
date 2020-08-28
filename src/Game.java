package dev.myFishGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import dev.myFishGame.display.Display;
import dev.myFishGame.gfx.ImageLoader;

public class Game implements Runnable{

	private Display display;
	
	private BufferStrategy bs;
	private Graphics g;
	
	Point playerPoint = new Point(480, 270);
	Point mousePoint = new Point(0, 0);
	Point toppoint = new Point (500, 0);
	
	private BufferedImage boat;
	private BufferedImage player;
	
	int playery, playerx;
	int x = 480, y = 270;
	int rotation;
	public int width, height;
	public String title;
	private boolean running = false;
	private Thread thread;
	public float timeswung = 0;
	
	boolean colliding = false;
	public int score = 0;
	private static boolean ran1;
	
	private float neAng;
	private float posAng;
	
	public float getAngle(Point target, Point alt) {
	    float angle = (float) Math.toDegrees(Math.atan2(target.y - alt.y, target.x - alt.x));

	    if(angle < 0){
	        angle += 360;
	    }

	    return angle;
	}
	
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		
		
		
	}

	private void init() {
		display = new Display(title, width, height);
		boat = ImageLoader.loadImage("/textures/boat.png");
		rotation = 0;
		playerx = 500;
		playery = 240;
	}
	
	private void tick() {
		if (ran1) {
			collision();}

		ran1 = true;
		
		
		if (colliding) {
			
				neAng = Fish.compareAngle - 90;
			
			
				posAng = Fish.compareAngle + 90;
			
			if (Player.swinging) {
				
				
					if (rotation > (neAng) && rotation < (posAng)) {
						Fish.asg = false;
						new Fish();
						score++;
					}else {
						System.out.println("Your Score was: "+ score + "!");
						System.exit(0);
					}
				
				
			} else {
				System.out.println("Your Score was: "+ score + "!");
				System.out.println("Maybe try swinging next time?");
				System.exit(0);
			}
		} 
		
		
		
		//Player Stuff
		
		//swinging?
		if (Player.swinging) {
			timeswung ++;
			
		}else if (timeswung > 0){
			timeswung -=0.5;
		}
		
		if (timeswung >= 60) {
			Player.swinging = false;
		}
		
		//fish
		Fish.fall();
		if (!Fish.existed)
			Fish.newFish();
		//Rotate!
		mousePoint.setLocation(Display.mouseX, Display.mouseY);
		
		if ((mousePoint.y > 235 && mousePoint.y < 360 ) && mousePoint.x >= 483) {
			rotation = 90;
			if (Player.swinging) {
				playerx = 532;
				playery = 230;
			} else {
				playerx = 500;
				playery = 240;
			}
			
		}else if (mousePoint.y > 235 && mousePoint.y < 360 && mousePoint.x < 483) {
			rotation = 270;
			if (Player.swinging) {
				playerx = 430;
				playery = 310;
			} else {
				playerx = 455;
				playery = 300;
			}
		}else if (mousePoint.x > 350 && mousePoint.x < 620 && mousePoint.y <= 270) {
			rotation = 0;
			if(Player.swinging == true) {
				playerx = 437;
				playery = 209;
			} else {
				playerx = 446;
				playery = 240;
			}
		}else if (mousePoint.x > 350 && mousePoint.x < 620 && mousePoint.y > 270) {
			rotation = 180;
			if (Player.swinging) {
				playerx = 525;
				playery = 315;
			} else {
				playerx = 514;
				playery = 283;
			}
		}else if (mousePoint.y <= 270 && mousePoint.x >= 483) {
			rotation = 45;
			if (Player.swinging) {
				playerx = 486;
				playery = 205;
			} else {
				playerx = 470;
				playery = 230;
			}
		}else if (mousePoint.y > 270 && mousePoint.x >= 483) {
			rotation = 135;
			if (Player.swinging) {
				playerx = 546;
				playery = 270;
			} else {
				playerx = 519;
				playery = 260;
			}
		} else if (mousePoint.y > 270 && mousePoint.x < 483) {
			rotation = 225;
			if (Player.swinging) {
				playerx = 477;
				playery = 330;
			} else {
				playerx = 490;
				playery = 305;
			}
		} else if (mousePoint.y <= 270 && mousePoint.x < 483) {
			rotation = 315;
			if (Player.swinging) {
				playerx = 416;
				playery = 255;
			} else {
				playerx = 442;
				playery = 270;
			}
		}
		
		Player.UpdatePlayer();
		player = Player.PDraw;
		
		AffineTransform af = AffineTransform.getTranslateInstance(playerx, playery);
		af.rotate(Math.toRadians(getAngle(playerPoint, mousePoint)));
		Graphics2D g2d = (Graphics2D) g;
		
		
	//System.out.println("Ticked!");	
		
	}
	
	private void render() {
		
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//clear
		g.clearRect(0, 0, width, height);

		
		//draw
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, width, height);
		g.drawImage(boat, 448, 210, null);
		rotateComp(g);
		Fish.updateFish();
		g.drawImage(Fish.fDraw, Fish.coordx, Fish.coordy, null);
		g.setColor(Color.RED);
		g.drawString(Integer.toString(score), 25, 25);
		//end drawing
		
		bs.show();
		g.dispose();
		//System.out.println("Rendered!");
	}
	
	public void run() {
		init();
		//Timer
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		//Timer End
		while (running) {
			//More timer
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				ticks++;
				delta --;
			}
			
			if (timer >= 1000000000) {
				
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
	}
	
	public synchronized void start() {
		if(running) 
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
		
		
	}
	
	public synchronized void stop() {
		if (!running) 
			return;
		
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void rotateComp(Graphics g) {
		AffineTransform af = AffineTransform.getTranslateInstance(playerx, playery);
		af.rotate(Math.toRadians(rotation));
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(player, af, null);
	}
	
	
	public Rectangle pBounds() {
		return (new Rectangle (448, 210, boat.getWidth(), boat.getHeight()));
	}
	
	public void collision( ) {
		if (!Fish.asg)
			return;
		Rectangle rect1 = pBounds();
		Rectangle rect2 = Fish.fBounds();
		
		if (rect1.intersects(rect2)) {
			colliding = true;
		} else colliding = false;
	}
}
