package HKM;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO; 
import javax.swing.JPanel;

public class Character extends JPanel  {
	

	Image img;
	int a =0;
	int b =288;
	int c = 90;
	int d = 360;

	public Character(){
		setLayout(null);
		setBounds(0,0,52,52);
		InputStream is = getClass().getResourceAsStream("123.png"); //���� �ҷ��� �̹����� InputStream ������ �޾ƿ´�.
	        try { 
	        	img = ImageIO.read(is); //������Ʈ�� ���Ե� �̹��� ������ BufferedImage�� �ε��Ѵ�.
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
	}

	public void loop(){
		
		a+=90;
		c+=90;
		if(a==450){
			a=0;
			c=90;				
		}
		
	}


}//Ŭ���� end
