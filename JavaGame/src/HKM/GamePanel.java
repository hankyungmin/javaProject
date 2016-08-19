package HKM;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {
	static Image img; //ĳ����
	static Character ch ; //ĳ���� Ŭ����
	static Bullet bu;
	double chx;
	Timer timer;
	Set<Integer> key = new HashSet<>();
	
	public GamePanel(){
		setLayout(null);
		setBounds(10,10,600,500);
		setBackground(Color.WHITE);
		ch = new Character(); //ĳ���� Ŭ���� 

		this.img=ch.img;
			
		//Ÿ�̾�
		 timer=new Timer(50, new ActionListener() { //�ݺ��Ǵ� �ֱ� �� 50 ���� ����.
			@Override
			public void actionPerformed(ActionEvent e) {
				Iterator<Integer> it = key.iterator();
				while(it.hasNext()){
					int keyCode = it.next();
						switch(keyCode){
							case KeyEvent.VK_A: chx+=5;ch.loop(); break; 	//����Ű A , A�� �������!
							case KeyEvent.VK_D: chx-=5;ch.loop(); break;	//����Ű D , D�� �������!
							case KeyEvent.VK_N: bullet(); break;	//����Ű n , n�� �������!
					}
				}
				
			}
		});
		
		//Ű����
		this.addKeyListener(new KeyAdapter() {		
			@Override
			public void keyPressed(KeyEvent e) {  ////Ű�� ������ ���
				int keyCode = e.getKeyCode();//���Ű�����ȴ���!
				key.add(keyCode);
				if(!timer.isRunning()) timer.start();			
			}

			@Override
			public void keyReleased(KeyEvent e) {
				//�츮�� ���� �̺�Ʈó�� ������ �ߴܵǰ� �Ѵ�.
				key.remove(e.getKeyCode());
				if(key.isEmpty()) timer.stop(); //���࿡ set �÷��ǿ� ������ Ÿ�̸� ��ž! 
				
			}
		});
	} // ������ ��
	
	public void bullet(){
        bu = new Bullet((getWidth()+45)/2-chx, getHeight()/2,15,this);

	}
	
	
	@Override
	protected void paintComponent(Graphics g) {  //repaint �ؼ� �θ��� �ִ�.
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if(bu != null) 
		{
			bu.loop();
			bu.draw(g2d);
		}
		
		AffineTransform old = g2d.getTransform(); //���� ��ȯ ��� ��ȯ�� ���������� ������ �ִ� ��ü�̴�. ���� �ʱ� ��ġ ���� ������ �ִ� 
		g2d.translate(getWidth()/2-chx, getHeight()/2); //���� ������ ��ġ�� ����.
		if(getWidth()/2-chx>=this.getWidth()||getWidth()/2-chx<=0)
		{
			System.out.println("ĭ������");
		}
		else g2d.drawImage(img, 0, 0,90,90,ch.a,ch.b,ch.c,ch.d,this);
		g2d.setTransform(old); //���� ������ġ�� ���ʱ�ȭ
		
	}
	
	
}
