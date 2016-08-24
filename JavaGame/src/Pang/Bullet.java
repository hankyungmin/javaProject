package Pang;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Bullet extends BufferedImage {

	Random rd = new Random();

	double x;
	double y;
	double xspeed;
	double yspeed;
	JPanel j;
	double bx; // ����x
	double by; // ����y
	double bw; // ���� ���� ũ��
	double bh; // ���� ���� ũ��
	boolean bool;
	

	public Bullet(){
	      super(25, 25, BufferedImage.TYPE_INT_ARGB);

	      Graphics2D g2d = (Graphics2D) getGraphics();
	      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      g2d.setColor(Color.PINK);
	      g2d.fillOval(0, 0, 25, 25);
	}
	public Bullet(double x, double y, double yspeed, JPanel j) {
		this();
		this.x = x;
		this.y = y;
		this.yspeed = yspeed;
		this.j = j;
	}
	
	public void loop() { // x��ǥ�� ĳ������ x ��ǥ��.
		y -= yspeed;
		if (y <= 0) {
			bool = true;
		}
		for (int i = 0; i < MainPanel.brick.size(); i++) {
			bx = MainPanel.brick.get(i).x;
			by = MainPanel.brick.get(i).y;
			bw = MainPanel.brick.get(i).w;
			bh = MainPanel.brick.get(i).h;
			if (MainPanel.GetDistance(x + 12.5, y + 12.5, bx + bw / 2, by + bh / 2) <= this.getWidth() / 2 + bh) {
				bool = true;
			}

		}
	}
	
	public void draw(Graphics2D g2d){
		g2d.drawImage(this, (int)x,(int)y,null);
		
	}
}
