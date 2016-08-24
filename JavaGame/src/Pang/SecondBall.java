package Pang;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

public class SecondBall extends BufferedImage {
	Random rd = new Random();
	double x;
	double y;
	double xspeed;
	double yspeed;
	double firstyspeed;
	JPanel j;
	boolean sbswitch ;
	boolean yh; 

	public SecondBall(JPanel j,double fbx,double fby,double xspeed,double yspeed) {
		super(80, 80, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.GRAY);
		g2d.fillOval(0, 0, 80, 80);
		this.j = j;
		x = fbx;  				//FirstBall�� ��ġ�� ��� ��� ��
		y = fby;				//FirstBall�� ��ġ�� ��� ��� ��
		this.xspeed = xspeed;			//FirstBall���� �ణ ����ߵ�
		this.yspeed = yspeed;			//FirstBall���� �ణ ����ߵ�
		this.firstyspeed = yspeed;
	}

	public void loop() {
		if(!yh){
			if(y<j.getHeight()-80){
				yspeed=-yspeed;
				yh=true;

			}	
		}
		
		x += xspeed;
		y += yspeed;
		yspeed+=1;
		if(y >= j.getHeight() -80){
			yspeed=firstyspeed-4;
			
		}
		if (x >= j.getWidth() - 80) { //FirstBall���� �ణ ����ߵ� -500 �̶�� ���ڴ� FirstBall�� ũ�Ⱑ ���ԵȰ� �� Ŭ���� ���� �°� �ؾߵ�
			x = j.getWidth() - 80;
			xspeed = -xspeed;
		}
		if (x <= 0) {
			x = 0;
			xspeed = -xspeed;
		}
	}

	public void draw(Graphics2D g2d) {
		g2d.drawImage(this, (int) x, (int) y, null);

	}

}
