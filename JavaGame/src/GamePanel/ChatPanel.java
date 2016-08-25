package GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Network.ClientData;
import Network.GameClient;

public class ChatPanel extends JPanel {
	JTextArea chatField;
	JTextField ttf;
	//TransparentTextField ttf;
	GameClient gc;
	
	public ChatPanel(GameClient gc) {
		super();
		this.gc=gc;
		this.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.5f));
		this.setLayout(null);
		this.setBounds(0, 700, 600, 200);
		
		chatShowText();
		chatInputTextfield();
		
		
	}

	// ä�ú������� â
	public void chatShowText() {
		chatField = new JTextArea("");
		//JScrollPane sp = new JScrollPane(chatField);
		chatField.setBounds(0, 0, 600, 170);
		chatField.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.5f));
		chatField.setBorder(BorderFactory.createEmptyBorder());
//		sp.setOpaque(false);
//		sp.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.5f));
//		sp.setBorder(BorderFactory.createEmptyBorder());
		this.add(chatField);
	}
	//ä��â�� ���ε�
	public void chatAppendMsg(ClientData cdata){
		chatField.append(cdata.getUserId()+" : "+cdata.getChatMsg()+"\n");
		
	}
	
	// ä�� �Է��ϴ� â
	public void chatInputTextfield() {
		ttf = new JTextField("");
		//ttf = new TransparentTextField(" ");
		ttf.setBounds(0, 170, 600, 30);
		ttf.setOpaque(false);
		ttf.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.5f));
		ttf.setColumns(50);
		this.add(ttf);
		
		ttf.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_ENTER) {
					String sendM = ttf.getText();
					gc.sendMessage(sendM);
					ttf.setText("");
					ttf.requestFocus();
				}
			}

		});

	}

}

class TransparentTextField extends JTextField {

	public TransparentTextField() {
		super();
		init();
	}

	public TransparentTextField(String text) {
		super(text);
		init();
	}

	public TransparentTextField(int columns) {
		super(columns);
		init();
	}

	public TransparentTextField(String text, int columns) {
		super(text, columns);
		init();
	}

	protected void init() {
		setOpaque(false);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.SrcOver.derive(0.3f));
		super.paint(g2d);
		g2d.dispose();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g2d);
		g2d.dispose();
	}

}
