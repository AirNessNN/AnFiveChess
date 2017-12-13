package MainPackge;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 
 * 对象传送器<br/>用于创建房间时给房间UDP接收器连接
 * @author AN
 *
 */
public class Server{
	// 服务套接字
	public ServerSocket serverSocket=null;
	
	public Socket socket=null;

	private ObjectInputStream is = null;

	private ObjectOutputStream os = null;

	User user;
	
	public Server() {
		// TODO Auto-generated constructor stub
		try {
			//初始化服务器套接字
			serverSocket=new ServerSocket(10086);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void invoke() {

		Thread tSender=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//接受请求
				try {
					System.out.println("等待连接");
					socket=serverSocket.accept();
					os = new ObjectOutputStream(socket.getOutputStream());
					is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
					//打开游戏
					Checkerboard ck=new Checkerboard(Server.this);
					ck.setVisible(true);
					System.out.println("连接成功");
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				/*User user=new User(Room.cr.name, Room.cr.password, Room.cr.passwordF, Room.cr.timeLimit, Room.cr.timeLimitValue, Room.cr.jinShou, Room.cr.regrect);
				//发送对象
				try {
					os.writeObject(user);
					//清空缓存区
					os.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		});
		tSender.start();
	}
	
	/**
	 * 设置游戏状态
	 * @param gameState int值<br/>1：删除列表项，游戏房间已经取消创建。<br/>2：添加列表项，游戏创建完成<br/>3：游戏验证通过<br/>
	 * @throws IOException
	 */
	public void sendGameState(int gameState) throws IOException{
		if(socket!=null&&socket.isBound())
			os.writeObject(new Integer(gameState));
		//调试
		System.out.println("发送数据游戏数据:"+gameState);
		
		//接收客户端房间确认数据
		acceptGameState();
	}
	
	public void acceptGameState(){
		if(socket!=null&&socket.isBound()){
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						System.out.println("开始接收确认数据");
						Integer num=(Integer) is.readObject();
						if(num.intValue()==1){
							Checkerboard ck=new Checkerboard(Server.this);
							ck.setVisible(true);
						}
						
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();
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
	 * 判断是否连接
	 * @return
	 */
	public boolean isBound(){
		if(socket==null){
			return false;
		}else{
			return socket.isBound();
		}
	}
	
	/**
	 * 
	 * 关闭socket
	 * @throws IOException
	 */
	public void closeSocket() throws IOException{
		if(socket!=null){
			socket.close();
		}
	}
	
	public void closeSocketAndServer() {
		if(socket!=null){
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(serverSocket!=null){
			try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void clossServer(){
		if(serverSocket!=null){
			try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
