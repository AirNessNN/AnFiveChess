package MainPackge;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * 
 * UPD广播发送器
 * 
 * @author AN
 *
 */
class UDP_Sender implements Runnable {

	public static final int port = 2333;
	public static final String HOST = "224.255.10.0";

	// 线程和标记
	private Thread thread = new Thread(this);
	private boolean threadFlag;
	/**
	 * 广播套接字
	 */
	MulticastSocket socket = null;
	InetAddress iaddress = null;
	/**
	 * 本地IP地址
	 */
	public String IPconfig;
	private byte[] data;
	/**
	 * 构造方法
	 */
	public UDP_Sender() {
		// TODO Auto-generated constructor stub
		// 取得本地IP
		try {
			IPconfig = InetAddress.getLocalHost().getHostAddress();
			iaddress = InetAddress.getByName(HOST);
			socket = new MulticastSocket(port);// 初始化多点广播
			socket.setTimeToLive(1);// 本地广播
			socket.joinGroup(iaddress);// 加入组

			// 将IP转化为二进制
			data = IPconfig.getBytes();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DatagramPacket packet = null;
		packet = new DatagramPacket(data, data.length, iaddress, port);
		while (true) {
			if (threadFlag) {
				break;
			}
			try {
				// 发送消息
				socket.send(packet);
				Thread.sleep(5000);
				// 调试
				// System.out.println(packet);

			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setThread(boolean b) {
		if (b) {
			if (!thread.isAlive()) {
				thread.start();
			}
		}
		threadFlag = !b;
	}
}