package Network;

import java.io.Serializable;

public class GameData implements Serializable {

	private int teamNum;				// ĳ���� ��ȣ
	private String teamColor;			// ĳ���� ��
	private double chx;					// ĳ������ǥ
	private boolean bulletStart;		// ĳ���� �Ѿ� ����
	private boolean leftMove;			//ĳ���� ���� ������
	private boolean rightMove;		//ĳ���� ������ ������
	
	public boolean isLeftMove() {
		return leftMove;
	}

	public void setLeftMove(boolean leftMove) {
		this.leftMove = leftMove;
	}

	public boolean isRightMove() {
		return rightMove;
	}

	public void setRightMove(boolean rightMove) {
		this.rightMove = rightMove;
	}

	public boolean isBulletStart() {
		return bulletStart;
	}

	public void setBulletStart(boolean bulletStart) {
		this.bulletStart = bulletStart;
	}

	
	
	public int getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(int teamNum) {
		this.teamNum = teamNum;
	}

	public String getTeamColor() {
		return teamColor;
	}

	public void setTeamColor(String teamColor) {
		this.teamColor = teamColor;
	}

	public double getChx() {
		return chx;
	}

	public void setChx(double chx) {
		this.chx = chx;
	}
	
	
}
