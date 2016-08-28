package Network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class GameLogin extends Thread {

	BufferedReader br;

	ObjectInputStream fromClient;
	ObjectOutputStream toClient;
	Socket socket;
	//�÷��̾���� �� Ȯ��
	int playerNumber;

	public GameLogin(Socket socket) {
		super();
		this.socket = socket;
		try {
			fromClient = new ObjectInputStream(socket.getInputStream());
			toClient = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		super.run();
		ClientData cData;
		while (true) {
			try {
				cData = (ClientData) fromClient.readObject();

				if (loginMatch(cData)) {
					System.out.println("�α��� ����");
					// cData.setMsg("�α��� ����");
					cData.setLoginOK(true);
					cData.setClientBlueNum(GameServer.blueTeam.size());
					cData.setClientRedNum(GameServer.redTeam.size());
					toClient.writeObject(cData);
					toClient.flush();
					GameServer.userMap.put(cData.getUserId(), socket);
					new GameServerThread(fromClient, socket,playerNumber).start();
					break;

				} else {
					System.out.println("�α��� ����");
					// cData.setMsg("�α��� ����");
					cData.setClientBlueNum(GameServer.blueTeam.size());
					cData.setClientRedNum(GameServer.redTeam.size());
					cData.setLoginOK(false);
					toClient.writeObject(cData);
					toClient.flush();
				}

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

		}

	}

	public boolean loginMatch(ClientData cData) {
		FileReader fr;
		String line;
		PrintWriter pw;

		try {
			System.out.println("���Ͽ��� �α��� ��ġ�ϴ� ��");
			pw = new PrintWriter(new FileWriter("E:/test/test.txt", true));
			fr = new FileReader("E:/test/test.txt");
			br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {
				if (line.equals(cData.getUserId())) { // id��
					return false; // ��ϵ� ���̵� ������ ����
				}
			}
			if (teamMatch(cData)) {
				pw.println(cData.getUserId()); // userID input
				cData.setTeamOK(true);
			} else {
				cData.setTeamOK(false);
				return false;
			}
			pw.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

	// Team select Ok
	public boolean teamMatch(ClientData cData) {
		String team = cData.getTeamName();
		if (team.equals("Blue")) {
			if (GameServer.blueTeam.size() < 3) {

				// ����� ���� ��Ȳ Ȯ��.
				GameServer.blueTeam.put(cData.getUserId(), socket);
				// ������� �̿��� �ڽ��� ������ �� �ľ��� ���� ������ ���� - ĳ���͹����� ����
				cData.setTeamColor("Blue");
				cData.setTeamNum(GameServer.blueTeam.size());
				playerNumber+=1;		//������ �÷��̾� �߰�
				return true;
			} else {
				return false;
			}
		} else if (team.equals("Red")) {
			if (GameServer.redTeam.size() < 3) {

				// Red���� ���� ��Ȳ Ȯ��.
				GameServer.redTeam.put(cData.getUserId(), socket);
				// ������� �̿��� �ڽ��� ������ �� �ľ��� ���� ������ ���� - ĳ���͹����� ����
				cData.setTeamColor("Red");
				cData.setTeamNum(GameServer.redTeam.size());
				playerNumber+=1;		//������ �÷��̾� �߰�
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

}
