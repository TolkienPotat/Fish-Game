package dev.myFishGame;

import java.awt.image.BufferedImage;

import dev.myFishGame.gfx.ImageLoader;

public class Player {
	static int i = 0;
	public static boolean swinging = false;
	public static BufferedImage PDraw;
	
	
	public Player(int x, int y) {
		
		
		
	}

	public static void UpdatePlayer() {
		
		if (swinging) {
			
			if (i > 24) {
				PDraw = ImageLoader.loadImage("/textures/sword1.png");
				
				i --;
			} else if (i > 18) {
				PDraw = ImageLoader.loadImage("/textures/sword2.png");
				i --;
			} else if (i > 12) {
				PDraw = ImageLoader.loadImage("/textures/sword3.png");
				i --;
			} else if (i > 6) {
				PDraw = ImageLoader.loadImage("/textures/sword4.png");
				i --;
			} else if (i > 0) {
				PDraw = ImageLoader.loadImage("/textures/sword5.png");
				i --;
			} else {
				i = 30;
			}
			
		} else {
			PDraw = ImageLoader.loadImage("/textures/player.png");
		}
		
	}
	
}
