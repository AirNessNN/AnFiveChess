package MainPackge;

import java.io.IOException;
import java.net.*;
import java.util.Vector;

/**
 * UDP�㲥������
 * 
 * @author huhao
 *
 */
public class UDP_Accept implements Runnable {
	// �˿ں�
	public static final int port = 2333;
	// ����InteAddress
	public InetAddress group;
	// ��������׽��ֶ���
	public MulticastSocket socket = null;
	// �����߳�
	private Thread thread = new Thread(this);
	// �����߳��˳����
	public boolean threadFlag = false;
	// ������ַ
	public Vector<String> names=new Vector<>();
	// �ͻ���
	public Vector<Client> clients=new Vector<>();
	
	//�±�
	private int index;

	// ��ʼ������
	public void initialize() {
		group = null;
		if (socket != null) {
			socket.close();
		}
		threadFlag = false;
		index=0;
	}


	/**
	 * host ��ַ
	 */
	public static final String HOST = "224.255.10.0";

	/**
	 * 
	 * ���캯��
	 */
	public UDP_Accept() {
		// TODO Auto-generated constructor stub
		try {
			// ָ����ַ
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
	 * �ر�UDP������
	 */
	public void closeUDP() {
		threadFlag = true;
		if (socket != null) {
			socket.close();
		}
	}

	/**
	 * 
	 * ��������ʼ���������߳�
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (true) {
			// �߳��˳����
			if (threadFlag) {
				break;
			}
			System.out.println(index+"����");
			byte date[] = new byte[1024];

			DatagramPacket packet = null;

			packet = new DatagramPacket(date, date.length, group, port);

			try {
				socket.receive(packet);
				String name = new String(packet.getData(), 0, packet.getLength());
				// ��һ������
				if (index == 0 && !name.equals(InetAddress.getLocalHost().getHostAddress())) {
					// GameRoomListItem it=new GameRoomListItem(name,);
					names.addElement(new String(name));
					// ����
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
							// ���ӽ��յ���IP��ַ
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
				// �ж���Ϸ����״̬
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * �����㲥�����̻߳���ֹͣ
	 * 
	 * @param b
	 *            true:�����߳� false:ֹͣ�߳�
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
