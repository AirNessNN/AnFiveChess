package MainPackge;

import java.io.IOException;
import java.net.*;
import java.util.Vector;

/**
 * UDP广播接收器
 * 
 * @author huhao
 *
 */
public class UDP_Accept implements Runnable {
	// 端口号
	public static final int port = 2333;
	// 声明InteAddress
	public InetAddress group;
	// 创建多点套接字对象
	public MulticastSocket socket = null;
	// 创建线程
	private Thread thread = new Thread(this);
	// 创建线程退出标记
	public boolean threadFlag = false;
	// 内网地址
	public Vector<String> names=new Vector<>();
	// 客户端
	public Vector<Client> clients=new Vector<>();
	
	//下标
	private int index;

	// 初始化函数
	public void initialize() {
		group = null;
		if (socket != null) {
			socket.close();
		}
		threadFlag = false;
		index=0;
	}


	/**
	 * host 地址
	 */
	public static final String HOST = "224.255.10.0";

	/**
	 * 
	 * 构造函数
	 */
	public UDP_Accept() {
		// TODO Auto-generated constructor stub
		try {
			// 指定地址
			group = InetAddress.getByName(HOST);
			socket = new MulticastSocket(port);
			socket.joinGroup(group);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 关闭UDP接收器
	 */
	public void closeUDP() {
		threadFlag = true;
		if (socket != null) {
			socket.close();
		}
	}

	/**
	 * 
	 * 接收器初始化房间主线程
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (true) {
			// 线程退出标记
			if (threadFlag) {
				break;
			}
			System.out.println(index+"测试");
			byte date[] = new byte[1024];

			DatagramPacket packet = null;

			packet = new DatagramPacket(date, date.length, group, port);

			try {
				socket.receive(packet);
				String name = new String(packet.getData(), 0, packet.getLength());
				// 第一个房间
				if (index == 0 && !name.equals(InetAddress.getLocalHost().getHostAddress())) {
					// GameRoomListItem it=new GameRoomListItem(name,);
					names.addElement(new String(name));
					// 调试
					// System.out.println(names[index-1]);
					clients.addElement(new Client(name));

					User user = null;
					try {
						user = clients.elementAt(index).getRoom();
						clients.elementAt(index).thread.start();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (user != null) {
						Room.setList(user,clients.elementAt(index));
						Room.clients.addElement(clients.elementAt(index));
					}
					
					index++;
				} else {
					boolean b = true;
					for (int i = 0; i < index; i++) {
						if (!name.equals(names.elementAt(i)) && !name.equals(InetAddress.getLocalHost().getHostAddress())) {
							b = false;
						}
						if (b) {
							// 连接接收到的IP地址
							names.addElement(new String(name));
							try {
								clients.addElement(new Client(name));
								User user =clients.elementAt(index).getRoom();
								clients.elementAt(index).thread.start();
								if (user != null) {
									Room.setList(user,clients.elementAt(index));
									Room.clients.addElement(clients.elementAt(index));
								}
								
								index++;
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							b = true;
						}
					}
				}
				// 判断游戏连接状态
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 启动广播接收线程或者停止
	 * 
	 * @param b
	 *            true:启动线程 false:停止线程
	 */
	public void setThread(boolean b) {
		if (b) {
			if (!thread.isAlive()) {
				thread.start();
			}
		}
		threadFlag = !b;
	}
}
