package MainPackge;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import SwingTool.MesageBox;
import SwingTool.MyButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * 
 * ��֤����
 * @author AN
 *
 */
public class Verify extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField passwordField;
	
	private JLabel roomName = new JLabel("");
	
	private MyButton btnStart = new MyButton("����");
	
	/**
	 * ���캯��
	 * @param mod ��֤ģʽ<br/>1��������ģʽ<br/>2����֤����ģʽ
	 * @param user
	 */
	public Verify(int mod, User user,Client client) {
		setSize(298, 144);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setFont(new Font("΢���ź�", Font.PLAIN, 12));
		setTitle("\u8FDE\u63A5");
		setResizable(false);
		getContentPane().setLayout(null);
		setLocationRelativeTo(MainWindow.room);
		//���÷�������
		roomName.setText(user.getRoomName());
		
		switch(mod){
		case 1:{
			//����Ҫ����
			JPanel panel = new JPanel();
			panel.setBounds(0, 0, 293, 115);
			getContentPane().add(panel);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("\u6B63\u5728\u8FDE\u63A5\uFF0C\u8BF7\u7A0D\u540E");
			lblNewLabel.setBounds(10, 10, 180, 15);
			panel.add(lblNewLabel);
			
			//������
			try {
				client.sendGameState(1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//�رձ�����
			this.dispose();
			
			break;
		}
		case 2:{
			//������֤
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(0, 0, 293, 115);
			getContentPane().add(panel_1);
			panel_1.setLayout(null);
			
			JLabel label = new JLabel("\u623F\u95F4\u540D\uFF1A");
			label.setFont(new Font("΢���ź�", Font.PLAIN, 12));
			label.setBounds(10, 23, 54, 15);
			panel_1.add(label);
			
			
			roomName.setFont(new Font("΢���ź�", Font.PLAIN, 12));
			roomName.setBounds(74, 23, 112, 15);
			panel_1.add(roomName);
			
			JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
			label_1.setFont(new Font("΢���ź�", Font.PLAIN, 12));
			label_1.setBounds(10, 48, 54, 15);
			panel_1.add(label_1);
			
			passwordField = new JTextField();
			passwordField.setFont(new Font("΢���ź�", Font.PLAIN, 12));
			passwordField.setBounds(74, 45, 112, 21);
			panel_1.add(passwordField);
			passwordField.setColumns(10);
			
			btnStart.setFont(new Font("΢���ź�", Font.PLAIN, 12));
			btnStart.setBounds(190, 82, 93, 23);
			panel_1.add(btnStart);
			btnStart.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(passwordField.getText().equals(user.getRoomPassword())){
						try {
							client.sendGameState(1);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//�رձ�����
						Verify.this.dispose();
					}else{
						MesageBox.show(MainWindow.room, "�������", "��ʾ");
						passwordField.setText("");
					}
				}
			});
			break;
		}
		}
	}
}
