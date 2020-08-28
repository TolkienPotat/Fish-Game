package dev.myFishGame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.myFishGame.gfx.ImageLoader;

public class Fish {

	private static Graphics g;
	
	public static int max = 60;
	static int min = 1;
	static int range = max - min + 1;
	static int ticks = 0;
	static double dis;
	static int cenX = 478;
	static int cenY = 263;
	
	public static BufferedImage fDraw;
	static int coordx;
	static int coordy;
	
	static float speed = 1;
	static int prand;
	static int prand2;
	static float angle;
	
	public static float compareAngle;
	
	
	static Point center = new Point(cenX, cenY);
	static Point coords = new Point();
	
	static boolean asg = false;
	
	public static boolean existed = false;
	
	public static int xdrf = 0;
	public static int ydrf = 0;
	
	public static void newFish(){
		
		int rand = (int)(Math.random() * range) + min;
		if (rand == 1) {
			new Fish();
		}
		
	}
	
	
	
	
	Fish(){
		
		if (!asg) {
		fDraw = ImageLoader.loadImage("/textures/fish.png");
		findPoint();
		while (dis < 100) {
			findPoint();
		}
		
		boolean alive = true;
		coordx = prand;
		coordy = prand2;
		
		coords.setLocation(coordx, coordy);
		
		
		asg = true;
		
		}
	}
	
	
	public static void fall() {
		if (ticks < 600) {
			ticks++;
		} else {
			ticks = 0;
			if (max > 20) {
				max = max - 10;
			}
		}
	}
	
	
	private static void findPoint() {
		
		
		
		
		int pmax = 960;
		int pmin = 1;
		int prange = pmax - pmin + 1;
		
		prand = (int)(Math.random() * prange) + pmin;
		
		int pmax2 = 540;
		int pmin2 = 1;
		int prange2 = pmax - pmin + 1;
		
		prand2 = (int)(Math.random() * prange2) + pmin2;
		
		
		dis = Math.sqrt((prand - cenX)*(prand - cenX) + (prand2 - cenY)*(prand2 - cenY));
		
		
	}
	
	static void moveForward()
	{
		
		int fWidth = fDraw.getWidth();
		int fHeight = fDraw.getHeight();
		
		
		int ydis;
		int xdis;
		
		int centx = coordx + (fWidth / 2);
		int centy = coordy + (fHeight / 2);
		
		int xprev = 0;
		int yprev = 0;
		coords.setLocation(centx, centy);
		System.out.println("xdrf is: " + xdrf + "ydrf is: " + ydrf);
		//previous x pos is 1
		if (xdrf != 0) {
			if (xdrf == 1) {
				xdrf -=1;
			}else if (xprev == 1) {
				coordx += 2;
				xdrf -= 2;
			} else {
				coordx -= 2;
				xdrf -= 2;
			}
		} else if (centx == center.getX()) {
			if (centy > center.getY()) {
				ydis = (int) (centy - center.getY());
			} else {
				ydis = (int) (center.getY() - centy);
			}
			
			
			xdrf = ydis / 2;
		} else if (centx == center.getX() + 1 || centx == center.getX() - 1) {
			coordx = (int) center.getX();
		} else if (centx < center.getX()) {
			coordx += 2;
			xprev = 1;
		} else if (centx > center.getX()) {
			coordx -= 2;
			xprev = 0;
		}
		
		if (ydrf != 0) {
			if (ydrf == 1) {
				ydrf -= 1;
			}
			else if (yprev == 1) {
				coordy += 2;
				ydrf -= 2;
			} else {
				coordy -= 2;
				ydrf -= 2;
			}
		} else if (centy == center.getY()) {
			if (centx > center.getX()) {
				xdis = (int) (centx - center.getX());
			} else {
				xdis = (int) (center.getX() - centx);
			}
			
			ydrf = xdis / 2;
		} else if (centy == center.getY() + 1 || centy == center.getY() - 1) {
			coordy = (int) center.getY();
		} else if (centy < center.getY()) {
			coordy += 2;
			xprev = 1;
		} else if (centy > center.getY()) {
			coordy -= 2;
			xprev = 0;
		}
		
		
			
		 
		
		
      
	}
	
	public static float getAngle(Point target, Point alt) {
	    angle = (float) Math.toDegrees(Math.atan2(target.y - alt.y, target.x - alt.x));

	    if(angle < 0){
	        angle += 360;
	    }

	    return angle;
	}
	
	public static void updateFish() {
		if (asg) {
			
			
			
			compareAngle = (getAngle(center, coords) - 90);
			if (compareAngle < 0) {
				compareAngle += 360;
			}
			moveForward();
			
		}
	}
	
	public static Rectangle fBounds() {
		return (new Rectangle (coordx + 10, coordy + 10, fDraw.getWidth() - 10, fDraw.getHeight() - 10));
	}
	
}
