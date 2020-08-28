package dev.myFishGame.display;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dev.myFishGame.Player;

public class Display implements MouseListener{

	private JFrame frame;
	private Canvas canvas;
	private String title;
	private int width, height;
	private JPanel panel;

	static public int mouseX;
	static public int mouseY;
	
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		createDisplay();
	}
	
	
	private void createDisplay() {
		
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(960, 540));
		//panel.addMouseListener(this);
		canvas.addMouseListener(this);
		
		
		
		frame.add(canvas);
		//frame.add(panel);
		//panel.add(canvas);
		
		frame.pack();
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
		Player.swinging = true;
		
		mouseX = e.getX();
		mouseY = e.getY();
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		Player.swinging = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println(e.getX() +" " + e.getY() );
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	
	
	
}
