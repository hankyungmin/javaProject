package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import GamePanel.ChatPanel;
import GamePanel.MainFrame;
import GamePanel.MainPanel;

public class GameClient {

	ObjectOutputStream toServer;
	ObjectInputStream fromServer;
	Socket socket;
	String clientId;
	MainPanel mp;

	public GameClient(MainPanel mp) {
		this.mp = mp;
	}

	// �Ѱ��� ���α׷��� �����ϴ� ������ ����������, ������Ʈ�ѹ����
	public boolean connect() {
		try {
			socket = new Socket("127.0.0.1", 1234);
			System.out.println("Server Connectted");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void streamOpen() {
		try {
			this.toServer = new ObjectOutputStream(socket.getOutputStream());
			this.fromServer = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("��Ʈ�� ����");
	}

	public boolean loginSend(ClientData cData) {

		boolean loginOk = false;
		String str = null;
		try {
			toServer.writeObject(cData);
			toServer.flush();
			System.out.println("��ü�� �i��.");

			cData = (ClientData) fromServer.readObject();
			System.out.println("�α��� ��ü�� ����");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// team �ο��� �ľ� �� �α��� ����
		if (cData.isLoginOK()) {
			loginOk = true;
			clientId = cData.getUserId();
			str = String.format("Red Team : %d/3\nBlue Team: %d/3", cData.getClientRedNum(), cData.getClientBlueNum());
			JOptionPane.showMessageDialog(mp, str);
			new ClientComThread(socket, mp,this).start();
			if (cData.getClientBlueNum() == 3 && cData.getClientRedNum() == 3) {
				try {
					// ��� �������� ���ͼ� ��� ��Ī�Ǿ���. ������ ��ȣ�� ���� -> �������� �ٽ� ��� Ŭ���̾�Ʈ�� ����
					// ������ ���ӿ� �����ϵ��� ��.
					System.out.println("��� ������ ���Դ�.");
					cData.setAllTeamOK(true);
					toServer.writeObject(cData);
					toServer.flush();
					System.out.println("��� ������ �α��� ���� ���Դ�.");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		} else if (cData.isLoginOK()) {
			str = String.format("Red Team : %d/3\nBlue Team: %d/3", cData.getClientRedNum(), cData.getClientBlueNum());
			JOptionPane.showMessageDialog(mp, str);
			loginOk = false;
		}
		return loginOk;

	}

	// Chatting sending
	public void sendMessage(String msg) {

		ClientData cData = new ClientData();
		cData.setChatMsg(msg);
		cData.setUserId(clientId);

		try {
			toServer.writeObject(cData);
			toServer.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

