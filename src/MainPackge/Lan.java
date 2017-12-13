package MainPackge;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import SwingTool.MesageBox;

import javax.swing.JLabel;

/**
 * 局域网游戏窗口
 * @author AN
 *
 */
public class Lan extends JFrame{
	
	private Server server=null;
	
	public Lan() throws UnknownHostException {
		setTitle("\u521B\u5EFA\u6216\u52A0\u5165");
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setSize(400, 200);
		
		
		JButton btnCreatGame = new JButton("创建游戏");
		JButton btnJoinGame = new JButton("加入游戏");
		btnCreatGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(btnCreatGame.getText().equals("创建游戏")){
					
					server=new Server();
					server.invoke();
					
					btnJoinGame.setEnabled(false);
					btnCreatGame.setText("取消创建");
				}else{
					if(server!=null)
						server.closeSocketAndServer();
					
					btnJoinGame.setEnabled(true);
					btnCreatGame.setText("创建游戏");
				}
			}
		});
		btnCreatGame.setBounds(10, 10, 93, 23);
		getContentPane().add(btnCreatGame);
		
		btnJoinGame.setBounds(113, 10, 93, 23);
		getContentPane().add(btnJoinGame);
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals(""))
					MesageBox.show(Lan.this, "请输入IP地址", "提示");
				else{
					Client client=new Client(textField.getText());
					if(client.socket.isBound()){
						Checkerboard ck=new Checkerboard(client);
						ck.setVisible(true);
					}
				}
					
			}
		});
		
		textField = new JTextField();
		textField.setBounds(216, 11, 130, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("本机IP："+InetAddress.getLocalHost().toString()+"  输入对方的IP连接即可，必须是要在同一个局域网内");
		lblNewLabel.setBounds(10, 43, 364, 15);
		getContentPane().add(lblNewLabel);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
}
