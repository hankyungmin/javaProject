package Network;

public class GameLogicThread extends Thread {

	//ĳ���ͺ� x��ǥ
	//��� ĳ���� x ��ǥ
	public static double blueP1x;
	public static double blueP2x;
	public static double blueP3x;
	//���� ĳ���� x ��ǥ
	public static double redP1x;
	public static double redP2x;
	public static double redP3x;
	//ĳ���ͺ� x��ǥ ��
	
	
	//��ĳ���� �Ѿ� ��ǥ
	public static double blueBulletX1;
	public static double blueBulletY1;
	public static double blueBulletX2;
	public static double blueBulletY2;
	public static double blueBulletX3;
	public static double blueBulletY3;

	
	
	//�Ѿ� ��ŸƮ!
	//����� �Ѿ�
	public static boolean bP1BulletStart;
	public static boolean bP2BulletStart;
	public static boolean bP3BulletStart;
	//������ �Ѿ�
	public static boolean rP1BulletStart;
	public static boolean rP2BulletStart;
	public static boolean rP3BulletStart;
	
	//�Ѿ� ��!
	
	public GameLogicThread() {
		super();
	}
	
	public static void serverSetData(GameData gData){
		
		// ����϶�!
		if (gData.getTeamColor().equals("Blue")) {
			if (gData.getTeamNum() == 1) {
				if (gData.getChx() != 0) {
					blueP1x += gData.getChx();
				}
				if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
					bP1BulletStart = gData.isBulletStart();
				}
			}
			if (gData.getTeamNum() == 2) {
				if (gData.getChx() != 0) {
					blueP2x += gData.getChx();
				}
				if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
					bP2BulletStart = gData.isBulletStart();
				}
			}
			if (gData.getTeamNum() == 3) {
				if (gData.getChx() != 0) {
					blueP3x += gData.getChx();
				}
				if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
					bP3BulletStart = gData.isBulletStart();
				}
			}
		}
		
		
		//�����϶� !
		if (gData.getTeamColor().equals("Red")) {
			if (gData.getTeamNum() == 1) {
				if (gData.getChx() != 0) {
					redP1x += gData.getChx();
				}
				if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
					rP1BulletStart = gData.isBulletStart();
				}
			}
			if (gData.getTeamNum() == 2) {
				if (gData.getChx() != 0) {
					redP2x += gData.getChx();
				}
				if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
					rP2BulletStart = gData.isBulletStart();
				}
			}
			if (gData.getTeamNum() == 3) {
				if (gData.getChx() != 0) {
					redP3x += gData.getChx();
				}
				if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
					rP3BulletStart = gData.isBulletStart();
				}
			}
		}
	}
	@Override
	public void run() {
		super.run();
		
		while(true){
			GameBroadData gbData = new GameBroadData();

			// �����ĳ���� x��ǥ
			gbData.setBlueP1x(blueP1x);
			gbData.setBlueP2x(blueP2x);
			gbData.setBlueP3x(blueP3x);
			// ������ĳ���� x��ǥ
			gbData.setRedP1x(redP1x);
			gbData.setRedP2x(redP2x);
			gbData.setRedP3x(redP3x);

			// ���� ��� �Ѿ��� �߻���ٸ� ���⼭ true ������ �����͸� ������ �ƴϸ� false
			gbData.setbP1bulletStart(bP1BulletStart);
			gbData.setbP2bulletStart(bP2BulletStart);
			gbData.setbP3bulletStart(bP3BulletStart);
			// ���� ���� �Ѿ��� �߻���ٸ� ���⼭ true ������ �����͸� ������ �ƴϸ� false
			gbData.setrP1bulletStart(rP1BulletStart);
			gbData.setrP2bulletStart(rP2BulletStart);
			gbData.setrP3bulletStart(rP3BulletStart);

			// ��� ĳ���� �ӵ��ʱ�ȭ
			blueP1x = 0;
			blueP2x = 0;
			blueP3x = 0;
			// ���� ĳ���� �ӵ��ʱ�ȭ
			redP1x = 0;
			redP2x = 0;
			redP3x = 0;

			// ��� �Ѿ� �ʱ�ȭ
			bP1BulletStart = false;
			bP2BulletStart = false;
			bP3BulletStart = false;
			// ���� �Ѿ� �ʱ�ȭ
			rP1BulletStart = false;
			rP2BulletStart = false;
			rP3BulletStart = false;
			
			
			GameServerThread.gDatabroadCast(gbData);
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
