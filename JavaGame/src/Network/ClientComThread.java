package Network;

import java.io.ObjectInputStream;
import java.net.Socket;

import GamePanel.ChatPanel;
import GamePanel.MainPanel;

public class ClientComThread extends Thread {
	private Socket socket;
	private ObjectInputStream fromServer;
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
					mp.bCharac1.chx+=gbData.getBlueP1x();
					mp.bCharac2.chx+=gbData.getBlueP2x();
					//mp.bCharac1.loop();
					//mp.bCharac2.loop();
					mp.repaint();
					
					/*if(gData.getChx()!=0)
					{
						if(gData.getTeamColor().equals("Blue"))
						{
							if(gData.getTeamNum()==1)
							{
								mp.bCharac1.chx+=gData.getChx();
								mp.bCharac1.loop();
								mp.repaint();
							}
							else if(gData.getTeamNum()==2)
							{
								mp.bCharac2.chx+=gData.getChx();
								mp.bCharac2.loop();
								mp.repaint();
							}
						}
						
					}*/
					
				}
				} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
