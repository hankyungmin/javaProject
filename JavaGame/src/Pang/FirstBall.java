package Pang;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;
//1
public class FirstBall extends BufferedImage {
	Random rd = new Random();
	double x;
	double y;
	double bx; //����x
	double by; //����y
	double bw; //���� ���� ũ��
	double bh; //���� ���� ũ��
	double xspeed;
	double yspeed;
	double firstyspeed;
	JPanel j;
	boolean fbswitch  ;

	public FirstBall(JPanel j,double x,double y,double xspeed,double yspeed) {
		super(120, 120, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		g2d.fillOval(0, 0, 120, 120);
		this.j = j;
		this.x = x;
		this.y = y;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.firstyspeed = yspeed;
	}

	public void loop() {
		//�� 
		x += xspeed;
		y += yspeed;
		yspeed += 1;
		if (y >= j.getHeight() - 120) {
			yspeed = firstyspeed - 8;
		}
		if (x >= j.getWidth() - 120) {
			x = j.getWidth() - 120;
			xspeed = -xspeed;
		}
		if (x <= 0) {
			x = 0; xspeed = -xspeed;
		}
		//��
		
		//���� �Ѿ� �浹 Ž��
		if (MainPanel.GetDistance(MainFrame.bux, MainFrame.buy,x + 60, y + 60) <= 72.5) { 
			MainPanel.fbx =x;
			MainPanel.fby =y;
			MainFrame.mp.secondBall();
			fbswitch = true;
		}
		//���� �Ѿ� �浹 Ž��
		
		//������ �� �浹Ž��
		
		
		
		//������ �� �浹Ž��
		
	}

	public void draw(Graphics2D g2d) {
		g2d.drawImage(this, (int) x, (int) y, null);

	}
	
}
