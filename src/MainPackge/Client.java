package MainPackge;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * 
 * 房间UDP接收器所用的客户端。<br/>用于连接服务端所创建的房间(已废弃)
 * @author AN
 *
 */
public class Client implements Runnable{
	//连接套接字
	public Socket socket=null;
	
	public ObjectInputStream is = null;
	
	private ObjectOutputStream os=null;
	
	public User user;
	
	//线程对象
	public Thread thread;
	//线程标记
	public boolean threadFlag=false;
	//游戏标记
	public boolean gameFlag=false;
	
	
	public Client(String ip){
		// TODO Auto-generated constructor stub
		System.out.println("准备连接");
		//初始化连接IP
		try {
			socket=new Socket(ip, 10086);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取输入流和输出流
		try {
			is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			os=new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("连接完成");
		//初始化线程
		System.out.println("初始化操作线程");
		thread=new Thread(this);
		
	}
	/**
	 * 
	 * 返回一个User对象，在接收过程中会阻塞
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public User getRoom() throws ClassNotFoundException, IOException{
		if(socket.isBound())
			return user=(User)is.readObject();
		else
			return null;
	}
	
	/**
	 * 接收房间数据
	 * @return 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public int getGameState() throws IOException, ClassNotFoundException{
		if(socket.isBound()){
			return (Integer)is.readObject();
		}else{
			return -1;
		}
	}
	
	
	/**
	 * 接收游戏坐标数据
	 * @return 返回一个point对象
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Point acceptPosition() throws ClassNotFoundException, IOException{
		System.out.println("接受游戏坐标");
		if(socket!=null&&socket.isBound())
			return (Point) is.readObject();
		return null;
		
	}
	/**
	 * 发送游戏坐标数据
	 * @param pos 输入一个point对象
	 * @throws IOException
	 */
	public void sendPosition(Point pos) throws IOException{
		if(socket!=null&&socket.isBound()){
			os.writeObject(pos);
			System.out.println("游戏坐标发送");
		}
	}
	
	/**
	 * 发送房间确认请求
	 * @param num
	 * @throws IOException
	 */
	public void sendGameState(int num) throws IOException{
		if(socket!=null&&socket.isBound()){
			os.writeObject(new Integer(num));
		}
	}
	
	/**
	 * 
	 * 实现对client对象的socket连接和操作
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//标记true关闭线程
		while(!threadFlag){
			try {
				Integer mod = this.getGameState();
				System.out.println(mod.intValue());
				if (mod.intValue() == 1) {
					Room.removeListItem(user);
					System.out.println("删除列表项");
				} else if (mod.intValue() == 2) {
					System.out.println("恢复房间");
					Room.setList(user,Client.this);
				} else if (mod.intValue() == 3) {
					System.out.println("验证通过");
					Checkerboard ck=new Checkerboard(Client.this);
					ck.setVisible(true);
					threadFlag=!threadFlag;
				}else if(mod.intValue()==4){
					
					is.close();
					os.close();
					socket.close();
					threadFlag=!threadFlag;
				}
				
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				threadFlag=!threadFlag;
			}
		}
	}
	
	
}
