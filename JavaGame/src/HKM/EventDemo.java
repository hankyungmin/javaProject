package HKM;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;



public class EventDemo extends JFrame {

	
	public EventDemo(){
		super("�̺�Ʈ �׽�Ʈ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0,0,650,540);
		getContentPane().setLayout(null);
		

		
		GamePanel gp = new GamePanel();
		

		add(gp);
		setVisible(true);
		
		//��Ŀ���� �����гο� �������!! ��Ŀ���� �־������ �۾��� ���� �� �� �ִ�.
		
		new Thread(){
			@Override
			public void run(){
				while(true){
					gp.requestFocusInWindow();
					gp.repaint();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		}.start();
		
		
	}
	
	public static void main(String[] args){
		
		EventQueue.invokeLater(new Runnable() { //�̺�Ʈ�� �����Ҽ� �ִ� ����. �ý��� ���������� �ش�.
			@Override
			public void run() {
				new EventDemo();
			}
			});
	}

}
