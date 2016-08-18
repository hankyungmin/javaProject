package GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
	MainPanel mp;
	JTextField jt;
	ButtonGroup teamBg; // team
	JRadioButton bjb;
	JRadioButton bjb2;

	public LoginPanel(MainPanel mp) {
		super();
		this.setBounds(0, 0, 1800, 50);
		this.setLayout(null);
		this.setBackground(Color.GREEN);
		this.mp = mp;

		// sever entrance - ID
		JLabel ja = new JLabel("User ID : ");
		ja.setBounds(200, 20, 50, 30);
		this.add(ja);

		JLabel ja2 = new JLabel("Team Selection : ");
		ja2.setBounds(500, 20, 100, 30);
		this.add(ja2);

		// login text field
		jt = new JTextField("�������̵� �Է�");
		jt.setBounds(300, 20, 150, 30);
		this.add(jt);

		// Team selection button
		// Blue team
		bjb = new JRadioButton("Blue");
		bjb.setBounds(600, 20, 100, 30);
		bjb.setBackground(Color.CYAN);
		this.add(bjb);
		// Red team
		bjb2 = new JRadioButton("Red");
		bjb2.setBounds(700, 20, 100, 30);
		bjb2.setBackground(Color.red);
		this.add(bjb2);

		// Team button group
		teamBg = new ButtonGroup();
		teamBg.add(bjb);
		teamBg.add(bjb2);

		// Entrance button
		JButton entB = new JButton("Entrance");
		entB.setBounds(900, 20, 100, 30);
		this.add(entB);

		entB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				mp.drawingPlayImage();
				mp.repaint();
				
				// Login change
					loginAfter();
					repaint();
			}
		});
	}

	public void loginAfter() {

		// Connect ID
		JLabel ja = new JLabel(this.jt.getText());
		ja.setBounds(300, 20, 100, 30);
		this.add(ja);

		// Radiobutton value extraction
		Enumeration<AbstractButton> enums = teamBg.getElements();
		String teams = null;
		while (enums.hasMoreElements()) { // hasMoreElements() Enum�� �� ���� ��ü��
											// �ִ��� üũ�Ѵ�. ������ false ��ȯ
			AbstractButton ab = enums.nextElement(); // ���׸����� AbstractButton �̴ϱ�
														// �翬�� AbstractButton����
														// �޾ƾ���
			JRadioButton jb = (JRadioButton) ab; // ����ȯ. ���� ���ٰ� ������ ���ļ� �ٷ� ����ȯ
													// �ؼ� �޾Ƶ� �ȴ�.

			if (jb.isSelected()) // �޾Ƴ� ������ư�� üũ ���¸� Ȯ���Ѵ�. üũ�Ǿ������ true ��ȯ.
				teams = jb.getText().trim(); // getText() �޼ҵ�� ���ڿ� �޾Ƴ���.
		}

		// Connect Team
		JLabel team = new JLabel(teams);
		team.setBounds(650, 20, 100, 30);
		this.add(team);

		this.remove(bjb);
		this.remove(bjb2);
		this.remove(jt);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	}

}
