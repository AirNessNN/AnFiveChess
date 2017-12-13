package MainPackge;

import javax.swing.JFrame;
import javax.swing.JLabel;
import SwingTool.MyButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.awt.Font;
/**
 * 
 * ”Œœ∑∆Ù∂Ø¥∞ø⁄
 * @author huhao
 *
 */
public class MainWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Checkerboard checkBoard;
	
	public static Room room;
	
	public static Lan lan;

	public MainWindow() {
		setTitle("\u4E94\u5B50\u68CB");
		getContentPane().setLayout(null);
		setSize(668, 564);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		MyButton btnSingel = new MyButton("µ•»À”Œœ∑");
		btnSingel.setEnabled(false);
		btnSingel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnSingel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		btnSingel.setBounds(14, 248, 113, 27);
		getContentPane().add(btnSingel);
		
		MyButton btnLANgame = new MyButton("æ÷”ÚÕ¯∂‘’Ω");
		btnLANgame.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		btnLANgame.setBounds(14, 325, 113, 27);
		getContentPane().add(btnLANgame);
		btnLANgame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(lan==null||!lan.isVisible()){
					try {
						lan=new Lan();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					lan.setVisible(true);
				}else{
					lan.requestFocus();
				}
			}
		});
		
		MyButton game = new MyButton("ÕÊº“∂‘’Ω");
		game.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		game.setBounds(14, 285, 113, 27);
		getContentPane().add(game);
		game.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(checkBoard==null||!checkBoard.isVisible()){
					Checkerboard.threadFlag=false;
					Checkerboard.chessFlag=true;
					checkBoard=new Checkerboard();
					checkBoard.setVisible(true);
				}else{
					checkBoard.requestFocus();
				}
			}
		});
		
		MyButton btnSetting = new MyButton("…Ë÷√");
		btnSetting.setEnabled(false);
		btnSetting.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		btnSetting.setBounds(14, 365, 113, 27);
		getContentPane().add(btnSetting);
		
		MyButton btnExit = new MyButton("ÕÀ≥ˆ");
		btnExit.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		btnExit.setBounds(14, 471, 113, 27);
		getContentPane().add(btnExit);
		// TODO Auto-generated constructor stub
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
	}
}
