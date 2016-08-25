package GamePanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import Network.GameData;

public class MainPanel extends JPanel{
	
	public BufferedImage bimg;
	public Graphics2D g2d;
	public JFrame f;
	public PlayCharacter pCharac;		// ĳ���� Ŭ����
	public HashSet<Integer> keyCodes = new HashSet<>();
	public Timer timer;

	public MainPanel(JFrame f) {
		super();
		this.setBounds(0, 50, 1600, 900);
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		this.f = f;
		
		repaint();
		drawingMainImage();
		
	}
	public void eventKey(){
		// ĳ���͸� �����Ѵ�.
		pCharac = new PlayCharacter(this);
		
		//���� ������ ��ü ����
		GameData gData = new GameData();
		// Ű���� Ÿ�̸Ӹ� �����ν� �ߺ��� Ű�� �ȴ������� �Ѵ�.
				timer = new Timer(50, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//������ ��ü �ʱ�ȭ
						gData.setChx(0);
						Iterator<Integer> it = keyCodes.iterator();
						if(it.hasNext()){
							int keyCode = it.next();
							switch(keyCode){
							case KeyEvent.VK_A : 
								gData.setChx(-10);
								LoginPanel.gClient.sendGameData(gData);
								break;
							case KeyEvent.VK_D : 
								gData.setChx(+10);
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
	
	public void drawingMainImage(){
		InputStream is = getClass().getResourceAsStream("/imagePack/pangMain.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("����ȭ�� �ε� ����");
		} catch (IOException e) {
			System.out.println("����ȭ�� �ε� ����");
			e.printStackTrace();
		}
		//g2d.drawImage(bimg,0,0,1800,900,null);
		
	}
	public void drawingPlayImage(){
		InputStream is = getClass().getResourceAsStream("/imagePack/pangpangback1.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("����ȭ�� �ε� ����");
		} catch (IOException e) {
			System.out.println("����ȭ�� �ε� ����");
			e.printStackTrace();
		}
		//g2d.drawImage(bimg,0,0,1800,900,null);
		
	}
	
	/*public void drawBlackPanel(){
		BufferedImage chatBImg = new BufferedImage(350, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) chatBImg.getGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, chatBImg.getWidth(),chatBImg.getHeight());
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.dispose();		// �� �ʿ����.
	}*/

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D)g;
		g2d.drawImage(bimg,0,0,1600,900,null);
		
		//ĳ���͸� �׸���.
		if(pCharac!=null)pCharac.draw(g2d);
	}

}
