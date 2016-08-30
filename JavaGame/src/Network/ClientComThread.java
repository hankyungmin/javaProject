package Network;

import java.io.ObjectInputStream;
import java.net.Socket;

import GamePanel.ChatPanel;
import GamePanel.MainPanel;

public class ClientComThread extends Thread {
	private Socket socket;
	private ObjectInputStream fromServer;
	
	//����� �Ѿ� ��ǥ
	public static double buxB;
	public static double buyB;
	public static double bux2B;
	public static double buy2B;
	public static double bux3B;
	public static double buy3B;
	
	//������ �Ѿ� ��ǥ
	public static double buxR;
	public static double buyR;
	public static double bux2R;
	public static double buy2R;
	public static double bux3R;
	public static double buy3R;
	
	MainPanel mp;
	ChatPanel cp;
	GameClient gc;
	// static DataFormat uData;

	ClientComThread(Socket socket, MainPanel mp, GameClient gc) {
		this.socket = socket;
		this.mp = mp;
		this.gc=gc;
	}

	@Override
	public void run() {
		cp = new ChatPanel(gc,mp);
		while (true) {
			try {
				fromServer = new ObjectInputStream(socket.getInputStream());
				Object obj = fromServer.readObject();

				if (obj instanceof ClientData) {
					ClientData cData = (ClientData) obj;
					//��� �� ���� ����ȭ�� ����.
					if (cData.isAllTeamOK()) {
						mp.drawingPlayImage();
						mp.eventKey();		//�̺�Ʈ ó���� ���� Ű������
						mp.firstBall();
						mp.add(cp);
						mp.repaint();
					}
					if(cData.getChatMsg()!=null){
						cp.chatAppendMsg(cData);
						cp.repaint();
					}

				} else if (obj instanceof GameBroadData) {
					GameBroadData gbData = (GameBroadData) obj;
					//ĳ���Ͱ� �̵��Ͽ� ��ǥ�����Ͽ� �ٽ� �׷��ش�.
					
					//��� ĳ����
					mp.bCharac1.chx+=gbData.getBlueP1x(); 
					mp.bCharac2.chx+=gbData.getBlueP2x();
					mp.bCharac3.chx+=gbData.getBlueP3x();
					mp.bCharac1.loop();
					mp.bCharac2.loop();
					mp.bCharac3.loop();
					
					//���� ĳ����
					mp.rCharac1.chx+=gbData.getRedP1x(); 
					mp.rCharac2.chx+=gbData.getRedP2x();
					mp.rCharac3.chx+=gbData.getRedP3x();
					mp.rCharac1.loop();
					mp.rCharac2.loop();
					mp.rCharac3.loop();
					
					
					//Ball ���� 
					for(int i =0;i<mp.fb.size();i++){
						if(!mp.fb.get(i).fbswitch)	mp.fb.get(i).loop();	
					}
					for(int i =0;i<mp.sb.size();i++){
						if(!mp.sb.get(i).sbswitch)	mp.sb.get(i).loop();	
					}
					for(int i =0;i<mp.tb.size();i++){
						if(!mp.tb.get(i).tbswitch)	mp.tb.get(i).loop();	

					}
					//Ball ���� 
					
					//�Ѿ� start
					//��� �� �Ѿ�
					if(gbData.isbP1bulletStart()) mp. bP1bullet();
					if(gbData.isbP2bulletStart()) mp. bP2bullet();
					if(gbData.isbP3bulletStart()) mp. bP3bullet();

					//���� �� �Ѿ�
					if(gbData.isrP1bulletStart()) mp. rP1bullet();
					if(gbData.isrP2bulletStart()) mp. rP2bullet();
					if(gbData.isrP3bulletStart()) mp. rP3bullet();
					//�Ѿ� end
					
					//�Ѿ� ���� ����
					//��� 1�� �Ѿ� ����
					for (int i = 0; i < mp.bullet1B.size(); i++) {
						if (mp.bullet1B.size() > 0) {
							mp.bullet1B.get(i).loop();
							buxB = mp.bullet1B.get(i).x;
							buyB = mp.bullet1B.get(i).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
						}
					}
					// ��� 2�� �Ѿ� ����
					for (int i = 0; i < mp.bullet2B.size(); i++) {
						if (mp.bullet2B.size() > 0) {
							mp.bullet2B.get(i).loop();
							bux2B = mp.bullet2B.get(i).x;
							buy2B = mp.bullet2B.get(i).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
						}
					}
					// ��� 3�� �Ѿ� ����
					for (int i = 0; i < mp.bullet3B.size(); i++) {
						if (mp.bullet3B.size() > 0) {
							mp.bullet3B.get(i).loop();
							bux3B = mp.bullet3B.get(i).x;
							buy3B = mp.bullet3B.get(i).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
						}
					}
					
					//������ 1�� �Ѿ� ����
					for (int i = 0; i < mp.bullet1R.size(); i++) {
						if (mp.bullet1R.size() > 0) {
							mp.bullet1R.get(i).loop();
							buxR = mp.bullet1R.get(i).x;
							buyR = mp.bullet1R.get(i).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
						}
					}
					// ���� 2�� �Ѿ� ����
					for (int i = 0; i < mp.bullet2R.size(); i++) {
						if (mp.bullet2R.size() > 0) {
							mp.bullet2R.get(i).loop();
							bux2R = mp.bullet2R.get(i).x;
							buy2R = mp.bullet2R.get(i).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
						}
					}
					// ���� 3�� �Ѿ� ����
					for (int i = 0; i < mp.bullet3R.size(); i++) {
						if (mp.bullet3R.size() > 0) {
							mp.bullet3R.get(i).loop();
							bux3R = mp.bullet3R.get(i).x;
							buy3R = mp.bullet3R.get(i).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
						}
					}
			
					//�Ѿ� ���� ��
				
					mp.repaint();

					
				}
				} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
