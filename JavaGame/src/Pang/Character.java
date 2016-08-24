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
	int b =288;
	int c = 90;
	int d = 360;
	JPanel j;

	public Character(JPanel j){
	    super(40, 40, BufferedImage.TYPE_INT_ARGB);
		this.j = j;
		InputStream is = getClass().getResourceAsStream("123.png"); //���� �ҷ��� �̹����� InputStream ������ �޾ƿ´�.
	        try { 
	        	img = ImageIO.read(is); //������Ʈ�� ���Ե� �̹��� ������ BufferedImage�� �ε��Ѵ�.
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
	        
	    Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(img, 0, 0,90,90,a,b,c,d,null);
	}
	
	public void loop(){
		
		a+=90;
		c+=90;
		if(a==450){
			a=0;
			c=90;				
		}
		
	}
	public void draw(Graphics2D g2d) { //ĳ���Ͱ� �г� ������ �����¼��� ����ؾ� ���� �𸣰���.
		AffineTransform old = g2d.getTransform(); //���� ��ȯ ��� ��ȯ�� ���������� ������ �ִ� ��ü�̴�. ���� �ʱ� ��ġ ���� ������ �ִ� 
		g2d.translate(j.getWidth()/2+MainPanel.chx, j.getHeight()-80);

		if (j.getWidth() / 2 + MainPanel.chx <= 0 || j.getWidth() / 2 + MainPanel.chx >= j.getWidth() - 80) {
			if (j.getWidth() / 2 + MainPanel.chx >= j.getWidth() - 80 || j.getWidth() / 2 + MainPanel.chx <= 0) {
				if (j.getWidth() / 2 + MainPanel.chx >= j.getWidth() - 80) {
					MainPanel.chx = 810;
					g2d.translate(MainPanel.chx, j.getHeight() / 2);
				}
				if (j.getWidth() / 2 + MainPanel.chx <= 0) {
					MainPanel.chx = -j.getWidth() / 2 + 10;
					g2d.translate(MainPanel.chx, j.getHeight() / 2);
				}
			}
		}
		g2d.drawImage(img, 0, 0,90,90,a,b,c,d,null);
		g2d.setTransform(old); //���� ������ġ�� ���ʱ�ȭ
	}


}//Ŭ���� end
