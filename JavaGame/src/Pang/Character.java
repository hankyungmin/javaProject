package Pang;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO; 
import javax.swing.JPanel;

public class Character extends BufferedImage  {
	

	Image img;
	int a =0;
	int b =180;
	int c = 90;
	int d = 270;
	JPanel j;
	static boolean isDead;
	static long DeadTime ;

	public Character(JPanel j){
	    super(90, 72, BufferedImage.TYPE_INT_ARGB);
		this.j = j;
		InputStream is = getClass().getResourceAsStream("123.png"); //���� �ҷ��� �̹����� InputStream ������ �޾ƿ´�.
	        try { 
	        	img = ImageIO.read(is); //������Ʈ�� ���Ե� �̹��� ������ BufferedImage�� �ε��Ѵ�.
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
	        
	    Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(img, 0, 0,90,72,a,b,c,d,null);
	}
	
	public void loop() {
		a += 90;
		c += 90;
		if (a == 450) {
			a = 0;
			c = 90;
		}
	}
	
	public void draw(Graphics2D g2d) { //ĳ���Ͱ� �г� ������ �����¼��� ����ؾ� ���� �𸣰���.
		
		if(isDead&&System.currentTimeMillis()<=DeadTime+3000){
				return;
		}else{
				DeadTime=0;
				isDead=false;
		}
		if(!Character.isDead) {	
			AffineTransform old = g2d.getTransform(); //���� ��ȯ ��� ��ȯ�� ���������� ������ �ִ� ��ü�̴�. ���� �ʱ� ��ġ ���� ������ �ִ� 
			double x = j.getWidth() / 2 + MainPanel.chx;
			
			//�ɸ��Ͱ� ������ ���������
			if (x >= j.getWidth() - 80) { 
				MainPanel.chx = 710;
			} else if (x <= 0) {
				MainPanel.chx = -j.getWidth() / 2 + 10;
			}
			//�ɸ��Ͱ� ������ ���������

			g2d.translate(j.getWidth()/2+MainPanel.chx, j.getHeight()-80);
			g2d.drawImage(img, 0, 0,90,90,a,b,c,d,null);
			g2d.setTransform(old); //���� ������ġ�� ���ʱ�ȭ
		}
	}


}//Ŭ���� end
