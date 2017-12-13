package MainPackge;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * 
 * UPD�㲥������
 * 
 * @author AN
 *
 */
class UDP_Sender implements Runnable {

	public static final int port = 2333;
	public static final String HOST = "224.255.10.0";

	// �̺߳ͱ��
	private Thread thread = new Thread(this);
	private boolean threadFlag;
	/**
	 * �㲥�׽���
	 */
	MulticastSocket socket = null;
	InetAddress iaddress = null;
	/**
	 * ����IP��ַ
	 */
	public String IPconfig;
	private byte[] data;
	/**
	 * ���췽��
	 */
	public UDP_Sender() {
		// TODO Auto-generated constructor stub
		// ȡ�ñ���IP
		try {
			IPconfig = InetAddress.getLocalHost().getHostAddress();
			iaddress = InetAddress.getByName(HOST);
			socket = new MulticastSocket(port);// ��ʼ�����㲥
			socket.setTimeToLive(1);// ���ع㲥
			socket.joinGroup(iaddress);// ������

			// ��IPת��Ϊ������
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
				// ������Ϣ
				socket.send(packet);
				Thread.sleep(5000);
				// ����
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