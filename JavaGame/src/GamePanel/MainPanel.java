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

import Bullets.BulletBlue1;
import Bullets.Bullets;
import Charactor.BlueCharacter1;
import Charactor.BlueCharacter2;
import Network.GameData;
import Pang.Bullet;
import Ball.FirstBall;

public class MainPanel extends JPanel {

	public BufferedImage bimg;
	public Graphics2D g2d;
	public JFrame f;
	public BlueCharacter1 bCharac1;		// 블루팀 1번 캐릭터 클래스
	public BlueCharacter2 bCharac2;		// 블루팀 1번 캐릭터 클래스
	//public blueCharacter3 bCharac13;		// 블루팀 1번 캐릭터 클래스
	

	boolean blue1fb ; 			//블루팀 1번 캐릭터 처음에 총알 생성!
	boolean blue2fb ; 			//블루팀 2번 캐릭터 처음에 총알 생성!

	//현재 클라이언트의 팀컬러와 팀순번
	public String teamColor;
	public int teamNumber;
	
	public HashSet<Integer> keyCodes = new HashSet<>();
	public Timer timer;
	public GameData gData;				// 게임데이터 전송
	
	public static List<FirstBall> fb  = new ArrayList<>();		
	public static List<Bullets> bullet = new ArrayList<>();		//Bullet 배열
	
	public MainPanel(JFrame f) {
		super();
		this.setBounds(0, 50, 1600, 900);
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		this.f = f;

		this.setFocusable(true);
		this.requestFocus();
		drawingMainImage();
		setFocusable(true);			// 메인패널의 포커스를 맟추어준다
		requestFocus();				// 포커스를 요청한다.
	}
	
	public void firstBall(){
		fb.add(new FirstBall(this,0,getHeight()-420,10,-26)); 
		fb.add(new FirstBall(this,getWidth()-120,getHeight()-420,-10,-26)); 
	}

	public void eventKey(){
		// 캐릭터를 생성한다. 블루팀
		bCharac1 = new BlueCharacter1(this);
		bCharac2 = new BlueCharacter2(this);

		//팀컬러, 팀순번 입력
		teamColor=LoginPanel.gClient.teamColor;
		teamNumber = LoginPanel.gClient.clientNumber;
		// 키보드 타이머를 줌으로써 중복된 키가 안눌리도록 한다.
				timer = new Timer(50, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						
						Iterator<Integer> it = keyCodes.iterator();
						if(it.hasNext()){
							int keyCode = it.next();
			
							switch(keyCode){
							case KeyEvent.VK_A : 
								gData = new GameData();
								//팀 색과 팀 순번 전송, 캐릭터의 좌표전송
								gData.setTeamColor(teamColor);
								gData.setTeamNum(teamNumber);
								gData.setChx(-10);

								
								LoginPanel.gClient.sendGameData(gData);
								break;
							case KeyEvent.VK_D : 
								gData = new GameData();
								//팀 색과 팀 순번 전송, 캐릭터의 좌표전송
								gData.setTeamColor(teamColor);
								gData.setTeamNum(teamNumber);
								gData.setChx(+10);

								
								LoginPanel.gClient.sendGameData(gData);
								break;
							case KeyEvent.VK_N:// 가상키 n , n를 누른경우! bullet 메소드를 부름.
								//if(!Character.isDead) bullet();
								bullet();
								gData = new GameData();
								//팀 색과 팀 순번 전송, 캐릭터의 좌표전송
								gData.setTeamColor(teamColor);
								gData.setTeamNum(teamNumber);
								gData.setBulletStart(true);			// 총알이 출발했다.
								LoginPanel.gClient.sendGameData(gData);
							
								break; 

							}
						}
					}
				});
				
				// adapter는 내가 원하는 메소드만 사용하여 첨부할수 있다.
				// 그러나 listener는 3개의 메소드 모드 사용해야 한다.
				this.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						
						int keyCode = e.getKeyCode();
						keyCodes.add(keyCode);
						if(!timer.isRunning()) timer.start();
						// 키보드가 눌릴때 타이머가 작동함. 기능정지
					}
					
					@Override
					public void keyReleased(KeyEvent e){
						int keyCode = e.getKeyCode();
						keyCodes.remove(keyCode);
						if(timer.isRunning())timer.stop();
						//키보드가 떨어질때 타이머가 해제됨.정지해제
					}
				});
		
	}

	//MainPanel 에 메소드 시작
	
	// 블루팀 1번캐릭터 총알
	public void bullet(){ //컬렉션에 넣어줘야할듯! 재활용 성공!
		if (!blue1fb) {
			bullet.add(new BulletBlue1((getWidth() + 65) / 2 + bCharac1.chx, getHeight() - 80, 15, this));
			blue1fb = true;
		}
		for (int i = 0; i < bullet.size(); i++) {
			if (bullet.get(i).getBulletBool()) {
				bullet.get(i).initBulletX((getWidth() + 65) / 2 + bCharac1.chx); 
				bullet.get(i).initBulletY(getHeight() - 80);
				bullet.get(i).initBullet(false);
				
				return;
			} else {
				return;
			}
		}
	}

	
	// 객체간 거리 측정
	public static double GetDistance(double x1, double y1, double x2, double y2) { // 거리
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}


	public void drawingMainImage() {
		InputStream is = getClass().getResourceAsStream("/imagePack/pangMain.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("메인화면 로드 성공");
		} catch (IOException e) {
			System.out.println("메인화면 로드 실패");
			e.printStackTrace();
		}
		// g2d.drawImage(bimg,0,0,1800,900,null);

	}

	public void drawingPlayImage() {
		InputStream is = getClass().getResourceAsStream("/imagePack/pangpangback1.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("게임화면 로드 성공");
		} catch (IOException e) {
			System.out.println("게임화면 로드 실패");
			e.printStackTrace();
		}
		// g2d.drawImage(bimg,0,0,1800,900,null);

	}

	/*
	 * public void drawBlackPanel(){ BufferedImage chatBImg = new
	 * BufferedImage(350, 300, BufferedImage.TYPE_INT_ARGB); Graphics2D g2d =
	 * (Graphics2D) chatBImg.getGraphics(); g2d.setColor(Color.black);
	 * g2d.fillRect(0, 0, chatBImg.getWidth(),chatBImg.getHeight());
	 * g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	 * RenderingHints.VALUE_ANTIALIAS_ON);
	 * 
	 * g2d.dispose(); // 붓 필요없다. }
	 */

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D)g;
		g2d.drawImage(bimg,0,0,1600,900,null);
		
		//캐릭터를 그린다.
		if(bCharac1!=null)bCharac1.draw(g2d);
		if(bCharac2!=null)bCharac2.draw(g2d);
		for(int i =0;i<fb.size();i++){
			fb.get(i).draw(g2d);	
		}

		
	}
	


}
