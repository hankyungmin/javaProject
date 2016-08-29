package GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import Ball.FirstBall;
import Bullets.BulletBlue1;
import Bullets.BulletBlue2;
import Charactor.BlueCharacter1;
import Charactor.BlueCharacter2;
import Network.GameData;
import Ball.SecondBall;
import Ball.ThirdBall;

public class MainPanel extends JPanel {

	public BufferedImage bimg;
	public Graphics2D g2d;
	public JFrame f;
	public BlueCharacter1 bCharac1;		// ����� 1�� ĳ���� Ŭ����
	public BlueCharacter2 bCharac2;		// ����� 1�� ĳ���� Ŭ����
	//public blueCharacter3 bCharac13;		// ����� 1�� ĳ���� Ŭ����
	

	boolean blue1fb ; 			//����� 1�� ĳ���� ó���� �Ѿ� ����!
	boolean blue2fb ; 			//����� 2�� ĳ���� ó���� �Ѿ� ����!

	//���� Ŭ���̾�Ʈ�� ���÷��� ������
	public String teamColor;
	public int teamNumber;
	
	public HashSet<Integer> keyCodes = new HashSet<>();
	public Timer timer;
	public GameData gData;				// ���ӵ����� ����
	
	public static List<FirstBall> fb  = new ArrayList<>();		
	public static List<SecondBall> sb  = new ArrayList<>();		
	public static List<ThirdBall> tb  = new ArrayList<>();	
	public static List<BulletBlue1> bullet = new ArrayList<>();		//Bullet �迭
	public static List<BulletBlue2> bullet2 = new ArrayList<>();		//Bullet �迭
	
	public MainPanel(JFrame f) {
		super();
		this.setBounds(0, 50, 1600, 900);
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		this.f = f;

		this.setFocusable(true);
		this.requestFocus();
		drawingMainImage();
		setFocusable(true);			// �����г��� ��Ŀ���� ���߾��ش�
		requestFocus();				// ��Ŀ���� ��û�Ѵ�.
	}
	
	public void firstBall(){
		fb.add(new FirstBall(this,0,getHeight()-420,10,-26)); 
		fb.add(new FirstBall(this,getWidth()-120,getHeight()-420,-10,-26)); 
	}
	



	public void eventKey() {
		// ĳ���͸� �����Ѵ�. �����
		bCharac1 = new BlueCharacter1(this);
		bCharac2 = new BlueCharacter2(this);

		// ���÷�, ������ �Է�
		teamColor = LoginPanel.gClient.teamColor;
		teamNumber = LoginPanel.gClient.clientNumber;
		// Ű���� Ÿ�̸Ӹ� �����ν� �ߺ��� Ű�� �ȴ������� �Ѵ�.
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Iterator<Integer> it = keyCodes.iterator();
				while(it.hasNext()) {
					int keyCode = it.next();

					switch (keyCode) {
					case KeyEvent.VK_A:
						gData = new GameData();
						// �� ���� �� ���� ����, ĳ������ ��ǥ����
						gData.setTeamColor(teamColor);
						gData.setTeamNum(teamNumber);
						gData.setChx(-10);

						LoginPanel.gClient.sendGameData(gData);
						break;
					case KeyEvent.VK_D:
						gData = new GameData();
						// �� ���� �� ���� ����, ĳ������ ��ǥ����
						gData.setTeamColor(teamColor);
						gData.setTeamNum(teamNumber);
						gData.setChx(+10);

						LoginPanel.gClient.sendGameData(gData);
						break;
					case KeyEvent.VK_N:// ����Ű n , n�� �������! bullet �޼ҵ带 �θ�.
						// if(!Character.isDead) bullet();
						/*if (teamColor.equals("Blue")) {
							if (teamNumber == 1) {
								bP1bullet();
							}
						}
						if (teamColor.equals("Blue")) {
							if (teamNumber == 2) {
								bP2bullet();
							}
						}*/
						
						gData = new GameData();
						// �� ���� �� ���� ����, ĳ������ ��ǥ����
						gData.setTeamColor(teamColor);
						gData.setTeamNum(teamNumber);
						gData.setBulletStart(true); // �Ѿ��� ����ߴ�.
						LoginPanel.gClient.sendGameData(gData);

						break;
					}
				}
			}
		});

				// adapter�� ���� ���ϴ� �޼ҵ常 ����Ͽ� ÷���Ҽ� �ִ�.
				// �׷��� listener�� 3���� �޼ҵ� ��� ����ؾ� �Ѵ�.
				this.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						
						int keyCode = e.getKeyCode();
						keyCodes.add(keyCode);
						if(!timer.isRunning()) timer.start();
						// Ű���尡 ������ Ÿ�̸Ӱ� �۵���. �������
					}
					
					@Override
					public void keyReleased(KeyEvent e){
						int keyCode = e.getKeyCode();
						keyCodes.remove(keyCode);
						if(timer.isRunning())timer.stop();
						//Ű���尡 �������� Ÿ�̸Ӱ� ������.��������
					}
				});
		
	}

	//MainPanel �� �޼ҵ� ����
	
	//����� 1�� �Ѿ�
	public void bP1bullet() { // �÷��ǿ� �־�����ҵ�! ��Ȱ�� ����!
		if (!blue1fb) {
			bullet.add(new BulletBlue1((getWidth() + 65) / 2 + bCharac1.chx, getHeight() - 270, 15, this));
			blue1fb = true;
			System.out.println("blue1fb:" + "�ǵ��ӳ�");
		}
		for (int i = 0; i < bullet.size(); i++) {
				bullet.get(i).initBulletX((getWidth() + 65) / 2 + bCharac1.chx);
				bullet.get(i).initBulletY(getHeight() - 270);
				bullet.get(i).initBullet(false);
				System.out.println("blue1fb:" + "���ε���");
			}
		}
	//����� 2�� �Ѿ�
		public void bP2bullet(){ //�÷��ǿ� �־�����ҵ�! ��Ȱ�� ����!
			if (!blue2fb) {
				bullet2.add(new BulletBlue2((getWidth() + 65) / 2 + bCharac2.chx, getHeight() - 270, 15, this));
				blue2fb = true;
			}
			for (int i = 0; i < bullet2.size(); i++) {
					bullet2.get(i).initBulletX((getWidth() + 65) / 2 + bCharac2.chx); 
					bullet2.get(i).initBulletY(getHeight() - 270);
					bullet2.get(i).initBullet(false);	
				}
			}
	
		
	// ��ü�� �Ÿ� ����
	public static double GetDistance(double x1, double y1, double x2, double y2) { // �Ÿ�
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}


	public void drawingMainImage() {
		InputStream is = getClass().getResourceAsStream("/imagePack/pangMain.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("����ȭ�� �ε� ����");
		} catch (IOException e) {
			System.out.println("����ȭ�� �ε� ����");
			e.printStackTrace();
		}
		// g2d.drawImage(bimg,0,0,1800,900,null);

	}

	public void drawingPlayImage() {
		InputStream is = getClass().getResourceAsStream("/imagePack/pangpangback1.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("����ȭ�� �ε� ����");
		} catch (IOException e) {
			System.out.println("����ȭ�� �ε� ����");
			e.printStackTrace();
		}
	}



	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D)g;
		g2d.drawImage(bimg,0,0,1600,900,null);
		
		//ĳ���͸� �׸���.
		if(bCharac1!=null)bCharac1.draw(g2d);
		if(bCharac2!=null)bCharac2.draw(g2d);
		for(int i =0;i<fb.size();i++){
			if(!fb.get(i).fbswitch) fb.get(i).draw(g2d);	
		}
		if (bullet.size() > 0) {
			for (int i = 0; i < bullet.size(); i++)  bullet.get(i).draw(g2d); // �Ѿ��� ��ǥ�� ��� �гο� �׸���.	
		}
		if (bullet2.size() > 0) {
			for (int i = 0; i < bullet2.size(); i++)  bullet2.get(i).draw(g2d); // �Ѿ��� ��ǥ�� ��� �гο� �׸���.	
		}
		for(int i =0;i<sb.size();i++){
			if(!sb.get(i).sbswitch) sb.get(i).draw(g2d);	
		}
		for(int i =0;i<tb.size();i++){
			if(!tb.get(i).tbswitch) tb.get(i).draw(g2d);	
		}
		
	}
	


}
