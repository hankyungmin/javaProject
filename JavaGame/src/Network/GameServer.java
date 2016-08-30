package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GameServer {

	static Map<String, Socket> userMap = new HashMap();
	static Map<String, Socket> blueTeam = new HashMap<>();
	static Map<String, Socket> redTeam = new HashMap<>();
	static Set<ObjectOutputStream> oosSet = new HashSet<>();

	public static void main(String[] args) {
		ServerSocket ss;
		try {
			ss = new ServerSocket(1234);
			Socket socket = null;
			String clientIP = null;

			while (true) {
				socket = ss.accept();
				clientIP = socket.getInetAddress().getHostAddress();
				System.out.printf("Ŭ���̾�Ʈ(%s) ���ӵ�...\n", clientIP);

				new GameLogin(socket).start(); // �α��ξ�����
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class GameServerThread extends Thread {
	private Socket socket;
	private ObjectInputStream fromClient;
	GameLogicThread gLogicThread;
	public boolean oosflag;
	
	
	GameServerThread(ObjectInputStream fromClient, Socket socket, int playerNumber) {
		this.socket = socket;
		this.fromClient = fromClient;

		// �÷��̾ ��� ������ Ȯ�εǸ� ���������带 ����
		if (playerNumber == 6) {
			gLogicThread = new GameLogicThread();
			gLogicThread.start(); // ���ӷ���(��,�浹���� ��) ������ ����
		}

	}

	@Override
	public void run() {
		try {
			Object Data;

			while (true) {
				Data = fromClient.readObject();

				// ���ӵ����͸� �и��Ͽ� ����������� ����.
				if (Data instanceof GameData) {
					GameData gData = (GameData) Data;
					GameLogicThread.serverSetData(gData);
				} else
					broadCast(Data);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void broadCast(Object d) {
		if (!oosflag) {
			Set<String> keys = GameServer.userMap.keySet();
			Iterator<String> it = keys.iterator();
			ObjectOutputStream toClient;
			while (it.hasNext()) {
				String id = it.next();
				Socket soc = GameServer.userMap.get(id);
				try {
					toClient = new ObjectOutputStream(soc.getOutputStream());
					toClient.reset();
					toClient.writeObject(d);
					toClient.flush();
					GameServer.oosSet.add(toClient);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			oosflag = true;
		}else if(oosflag){
			Iterator<ObjectOutputStream> it =  GameServer.oosSet.iterator();
			ObjectOutputStream toClient;
			while (it.hasNext()) {
				toClient = it.next();
				try {
					toClient.reset();
					toClient.writeObject(d);
					toClient.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}

	}

	public static void gDatabroadCast(GameBroadData d) {
		Iterator<ObjectOutputStream> it =  GameServer.oosSet.iterator();
		ObjectOutputStream toClient;
		while (it.hasNext()) {
			toClient = it.next();
			try {
				toClient.reset();
				toClient.writeObject(d);
				toClient.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
