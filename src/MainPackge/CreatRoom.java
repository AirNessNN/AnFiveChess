package MainPackge;

import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import SwingTool.MesageBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;

/**
 * ��������
 * @author AN
 *
 */
public class CreatRoom extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField timeField;
	
	private JTextField roomNameField;
	
	private JTextField roomPasswordField;
	
	private Server server=new Server();
	
	//��������
	public String name;
	//��������
	public String password;
	//������
	public boolean regrect;
	//������
	public boolean passwordF;
	//ʱ�����Ʊ��
	public boolean timeLimit;
	//ʱ��������ֵ
	public int timeLimitValue;
	//���ֱ��
	public boolean jinShou;
	//UPD����ʵ��
	private UDP_Sender sender;
	
	//���캯��
	public CreatRoom() {
		//��ʼ��UDPsender
		sender=new UDP_Sender();
		//���ô���
		setTitle("\u623F\u95F4\u8BBE\u7F6E");
		setResizable(false);
		getContentPane().setLayout(null);
		setSize(451,258);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		addWindowListener(new clossWindow());
		
		JPanel panel = new JPanel();
		panel.setBounds(228, 16, 202, 87);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel stateLabel = new JLabel("\u7B49\u5F85\u8FDE\u63A5...");
		stateLabel.setVisible(false);
		
		JCheckBox canSetPassword = new JCheckBox("\u8BBE\u7F6E\u5BC6\u7801");
		JCheckBox canSetTime = new JCheckBox("\u65F6\u95F4\u9650\u5236");
		
		timeField = new JTextField();
		timeField.setEnabled(false);
		timeField.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		timeField.setBounds(96, 6, 58, 22);
		panel.add(timeField);
		timeField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\u79D2");
		lblNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblNewLabel.setBounds(168, 10, 24, 15);
		panel.add(lblNewLabel);
		
		JCheckBox canRegret = new JCheckBox("\u6094\u68CB");
		canRegret.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		canRegret.setBounds(0, 31, 103, 23);
		panel.add(canRegret);
		
		JCheckBox canJinShou = new JCheckBox("\u7981\u624B");
		canJinShou.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		canJinShou.setBounds(0, 58, 103, 23);
		panel.add(canJinShou);
		
		JLabel label = new JLabel("\u623F\u95F4\u540D\u79F0\uFF1A");
		label.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		label.setBounds(10, 26, 82, 15);
		getContentPane().add(label);
		
		roomNameField = new JTextField();
		roomNameField.setText("\u4E0D\u8D85\u8FC76\u4E2A\u5B57");
		roomNameField.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		roomNameField.setBounds(96, 23, 103, 21);
		getContentPane().add(roomNameField);
		roomNameField.setColumns(10);
		roomNameField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				super.mouseClicked(arg0);
				if(roomNameField.getText().equals("������6����")){
					roomNameField.setText("");
				}
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("\u623F\u95F4\u5BC6\u7801:");
		lblNewLabel_1.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 91, 82, 15);
		getContentPane().add(lblNewLabel_1);
		
		roomPasswordField = new JTextField();
		roomPasswordField.setEnabled(false);
		roomPasswordField.setText("6\u5B57\u7B26\u6216\u6570\u5B57");
		roomPasswordField.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		roomPasswordField.setBounds(96, 87, 103, 21);
		getContentPane().add(roomPasswordField);
		roomPasswordField.setColumns(6);
		roomPasswordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				roomPasswordField.setText("");
			}
		});
		
		//������ť
		JButton btnCreatRoom = new JButton("\u521B\u5EFA");
		JButton btnCancel = new JButton("\u53D6\u6D88");
		btnCreatRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				//������ֵ�Ƿ�ȫ�Ϸ�
				if(roomNameField.getText().length()>6){
					MesageBox.show(MainActivitily.mw, "�������Ʋ��ܳ���6�ַ�", "������ʾ");
					roomNameField.setText("������6����");
					return;
				}else if(roomNameField.getText().equals("������6����")||roomNameField.getText().equals("")){
					MesageBox.show(MainActivitily.mw, "�����뷿����", "������ʾ");
					roomNameField.setText("������6����");
					return;
				}else{
					name=roomNameField.getText();
				}
				if(roomPasswordField.getText().length()>6){
					MesageBox.show(MainActivitily.mw, "���벻�ܳ���6λ", "������ʾ");
					roomPasswordField.setText("6�ַ�������");
					return;
				}else if(canSetPassword.isSelected()&&(roomPasswordField.getText().equals("6�ַ�������")||roomPasswordField.getText().equals(""))){
					MesageBox.show(MainActivitily.mw, "����������", "������ʾ");
					roomPasswordField.setText("6�ַ�������");
					return;
				}else{
					if(canSetPassword.isSelected()){
						password=roomPasswordField.getText();
					}else{
						password=null;
					}
				}
				try{
					if(canSetTime.isSelected()){
						timeLimitValue=Integer.parseInt(timeField.getText());
					}else{
						timeLimitValue=0;
					}
					if(timeLimitValue>120){
						throw new NumberFormatException();
					}
				}catch (NumberFormatException e) {
					// TODO: handle exception
					MesageBox.show(MainActivitily.mw, "ʱ��Ӧ��Ϊ�Ǹ������֣�������120��", "����");
					return;
				}
				
				//��������������뵽����
				regrect=canRegret.isSelected();
				timeLimit=canSetTime.isSelected();
				passwordF=canSetPassword.isSelected();

				//��ȡ����ť
				btnCreatRoom.setEnabled(false);
				btnCancel.setEnabled(true);
				//�ر����п��õİ���
				canJinShou.setEnabled(false);
				canRegret.setEnabled(false);
				canSetPassword.setEnabled(false);
				canSetTime.setEnabled(false);
				roomNameField.setEnabled(false);
				roomPasswordField.setEnabled(false);
				timeField.setEnabled(false);
				stateLabel.setVisible(true);
				
				//����UDP��Ϣ
				sender=new UDP_Sender();
				sender.setThread(true);
				
				//����TCP����
				invoke();
				if(server.isBound()){
					try {
						server.sendGameState(2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				System.out.println(name+" "+password+" "+timeLimitValue);
			}
		});
		btnCreatRoom.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		btnCreatRoom.setBounds(10, 188, 93, 23);
		getContentPane().add(btnCreatRoom);
		
		
		canSetPassword.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		canSetPassword.setBounds(10, 59, 103, 23);
		getContentPane().add(canSetPassword);
		canSetPassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(canSetPassword.isSelected()){
					roomPasswordField.setEnabled(true);
				}else{
					roomPasswordField.setEnabled(false);
				}
			}
		}); 
		
		canSetTime.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		canSetTime.setBounds(0, 6, 89, 23);
		panel.add(canSetTime);
		canSetTime.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if(canSetTime.isSelected()){
					timeField.setEnabled(true);
				}else{
					timeField.setEnabled(false);
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnCreatRoom.setEnabled(true);
				btnCancel.setEnabled(false);
				
				canJinShou.setEnabled(true);
				canRegret.setEnabled(true);
				canSetPassword.setEnabled(true);
				canSetTime.setEnabled(true);
				roomNameField.setEnabled(true);
				if(canSetPassword.isSelected()){
					roomPasswordField.setEnabled(true);  
				}
				if(canSetTime.isSelected()){
					roomPasswordField.setEnabled(true);
				}
				stateLabel.setVisible(false);
				
				//�ر�UDP�㲥
				sender.setThread(false);
				try {
					server.sendGameState(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnCancel.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		btnCancel.setEnabled(false);
		btnCancel.setBounds(117, 188, 93, 23);
		getContentPane().add(btnCancel);
		
		stateLabel.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		stateLabel.setBounds(14, 157, 72, 18);
		getContentPane().add(stateLabel);
		
		//����������
		this.setModal(true);
	}
	
	/**
	 * ����server��������
	 */
	public void invoke(){
		server.invoke();
	}
	
	/**
	 * ���ڹر��¼�
	 * @author AN
	 *
	 */
	class clossWindow extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			super.windowClosing(e);
			System.out.println("�������ڹر�");
			try {
				server.sendGameState(4);
				server.clossServer();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}




