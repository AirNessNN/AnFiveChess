package MainPackge;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import SwingTool.MyButton;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.DefaultListModel;
import java.awt.Font;

/**
 * 
 * 废弃的房间类
 * @author AN
 *
 */
public class Room extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//创造房间对象
	public static CreatRoom cr;
	//设置List模型
	public static DefaultListModel<GameRoomListItem> listMod=new DefaultListModel<GameRoomListItem>();
	//列表对象
	public static JList<GameRoomListItem> list = new JList<GameRoomListItem>(listMod);
	//UDP接收器对象
	private UDP_Accept UDP;
	
	public static Vector<Client>clients=new Vector<>();
	
	
	public Room() {
		setResizable(false);
		setTitle("\u623F\u95F4\u5217\u8868");
		setSize(383,619);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new clossWindow());
		
		JLabel lblNewLabel = new JLabel("\u623F\u95F4\u5217\u8868");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel.setBounds(14, 17, 72, 18);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 55, 377, 529);
		
		
		scrollPane.setViewportView(list);
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel);
		getContentPane().add(scrollPane);
		list.setCellRenderer(new MyCell());
		list.setFixedCellHeight(30);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if(e.getClickCount()==2){
					if(list.getSelectedValue().user.isCanSetPassword()){
						Verify vf=new Verify(2,list.getSelectedValue().user,list.getSelectedValue().client);
						vf.setVisible(true);
					}else{
						Verify vf=new Verify(1, list.getSelectedValue().user,list.getSelectedValue().client);
						vf.setVisible(true);
					}
				}
			}
		});
		
		MyButton button = new MyButton("\u521B\u5EFA\u623F\u95F4");
		button.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		button.setToolTipText("\u5728\u5C40\u57DF\u7F51\u7EDC\u5185\u5EFA\u7ACB\u4E00\u4E2A\u8FDE\u63A5");
		button.setBounds(250, 13, 113, 27);
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cr=new CreatRoom();
				cr.setVisible(true);
			}
		});
		
		//启动UPD接收器
		startUDP();
	}
	
	/**
	 * 
	 * 配置List的对象
	 * 
	 * @param user User对象
	 */
	public static void setList(User user,Client client){
		listMod.addElement(new GameRoomListItem(user,client));
	}
	
	public static void removeListItem(User user){
		for(int i=0;i<listMod.getSize();i++){
			if(user.getRoomName().equals(listMod.elementAt(i).user.getRoomName())){
				listMod.removeElementAt(i);
				break;
			}
		}
	}
	
	
	/**
	 * 
	 * 打开UDP的广播接收线程
	 */
	public  void startUDP(){
		UDP=null;
		UDP=new UDP_Accept();
		UDP.setThread(true);
	}
	
	class clossWindow extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			UDP.closeUDP();
		}
	}
	
}
