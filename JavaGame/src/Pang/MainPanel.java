package Pang;

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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel  {
	
	static Character ch ; 			//ĳ���� Ŭ����
	static double chx; 					//Character �� x ��ǥ
	//static Bullet bu; 				//�Ѿ� Ŭ���� 
	static List<Bullet> bullet = new ArrayList<>();
	static List<FirstBall> fb  = new ArrayList<>();
	static List<SecondBall> sb = new ArrayList<>();
	static List<ThirdBall> tb = new ArrayList<>(); //ThirdBall �迭
	static List<Brick> brick = new ArrayList<>();
	//�� ���� ��ǥ�� ���ι޾ƾߵǳ�..
	//�� ��ü�� �ϳ��� �̿��ؼ� �� �� ����. �׷��� �ؾ� �ϴ���.
	static double bux; 				//Bullet �� x ��ǥ
	static double buy; 				//Bullet �� y ��ǥ 
	static double fbx; 				//firstBall �� x ��ǥ
	static double fby; 				//firstBall �� y ��ǥ
	static double sbx; 				//secondBall �� x ��ǥ
	static double sby; 				//secondBall �� y ��ǥ
	static double tbx; 				//thirdBall �� x ��ǥ
	static double tby; 				//thirdBall �� y ��ǥ
	boolean bool = true;
	boolean firstbullet ;

	
	Timer timer;
	Set<Integer> key = new HashSet<>(); //Ű���� �Է°� ���� �÷���
	
	public MainPanel(){
		setLayout(null);
		setBounds(0, 50, 1600, 900);
		setBackground(Color.WHITE);
		ch = new Character(this); //ĳ���� Ŭ���� 
		firstBall(); //ù��° �� �޼ҵ�
		brick();

		//Ÿ�̸�
		timer = new Timer(30, new ActionListener() { // �ݺ��Ǵ� �ֱ� �� 30 ���� ����.
			@Override
			public void actionPerformed(ActionEvent e) {
				// ball().loop();
				Iterator<Integer> it = key.iterator();
				while (it.hasNext()) {
					int keyCode = it.next();
					switch (keyCode) {
					case KeyEvent.VK_A: // ����Ű A , A�� �������!
						chx -= 10;
						ch.loop();
						break; 
					case KeyEvent.VK_D: // ����Ű D , D�� �������!
						chx += 10;
						ch.loop();
						break; 
					case KeyEvent.VK_N:// ����Ű n , n�� �������! bullet �޼ҵ带 �θ�.
						bullet();
						break; 

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


	//paintComponent ����
	@Override
	protected void paintComponent(Graphics g) {  //repaint �ؼ� �θ��� �ִ�.
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if(ch!=null) ch.draw(g2d); //ĳ���͸� �׸���. 

		if (bullet.size() > 0) {
			for (int i = 0; i < bullet.size(); i++) {
				if (!bullet.get(i).bool) bullet.get(i).draw(g2d); // �Ѿ��� ��ǥ�� ��� �гο� �׸���.
			}
		}
		if(fb.size()>0){ //ù��°�����׸���
			for (int i = 0; i < fb.size(); i++) {
				if(!fb.get(i).fbswitch) fb.get(i).draw(g2d); //bool ���� true �� �Ǹ� �׸�
			}
		}
		if(sb.size()>0){//�ι�°���� �׸���.
			for (int i = 0; i < sb.size(); i++) {
				if(!sb.get(i).sbswitch) sb.get(i).draw(g2d);
			}
		}
		if(tb.size()>0){//�ι�°���� �׸���.
			for (int i = 0; i < tb.size(); i++) {
				if(!tb.get(i).tbswitch) tb.get(i).draw(g2d);
			}
		} 
		for(int i = 0; i < brick.size(); i++){//������ �׸���.
			brick.get(i).draw(g2d);
		}
	}
	//paintComponent ����
	
	//MainPanel �� �޼ҵ� ����
	public void bullet(){ //�÷��ǿ� �־�����ҵ�! ��Ȱ�� ����!
		if (!firstbullet) {
			bullet.add(new Bullet((getWidth() + 65) / 2 + chx, getHeight() - 80, 15, this));
			firstbullet = true;
		}
		for (int i = 0; i < bullet.size(); i++) {
			if (bullet.get(i).bool) {
				bullet.get(i).x = (getWidth() + 65) / 2 + chx;
				bullet.get(i).y = getHeight() - 80;
				bullet.get(i).bool = false;
				return;
			} else {
				return;
			}
		}

	}
	public void firstBall(){ //ù��° ��
		fb.add(new FirstBall(this,0,getHeight()-400,10,-30)); 
		fb.add(new FirstBall(this,getWidth()-120,getHeight()-400,-10,-30)); 
	}
	public void secondBall(){ //�ι�° ��
		sb.add(new SecondBall(this,fbx+120,fby,13,-32));
		sb.add(new SecondBall(this,fbx-120,fby,-13,-32)); 
	}
	public void thirdBall(){//����°��
		tb.add(new ThirdBall(this,sbx+80,sby,16,-34));
		tb.add(new ThirdBall(this,sbx-80,sby,-16,-34)); 
	}
	private void brick() {
		brick.add(new Brick(this,200,50,300,300,Color.magenta));
		brick.add(new Brick(this,200,40,600,300,Color.RED));
		
	}
	public static double GetDistance(double x1, double y1, double x2, double y2) { // �Ÿ�
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}
	//MainPanel �޼ҵ� ��


}//Ŭ����end
