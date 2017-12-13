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
 * ����UDP���������õĿͻ��ˡ�<br/>�������ӷ�����������ķ���(�ѷ���)
 * @author AN
 *
 */
public class Client implements Runnable{
	//�����׽���
	public Socket socket=null;
	
	public ObjectInputStream is = null;
	
	private ObjectOutputStream os=null;
	
	public User user;
	
	//�̶߳���
	public Thread thread;
	//�̱߳��
	public boolean threadFlag=false;
	//��Ϸ���
	public boolean gameFlag=false;
	
	
	public Client(String ip){
		// TODO Auto-generated constructor stub
		System.out.println("׼������");
		//��ʼ������IP
		try {
			socket=new Socket(ip, 10086);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��ȡ�������������
		try {
			is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			os=new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("�������");
		//��ʼ���߳�
		System.out.println("��ʼ�������߳�");
		thread=new Thread(this);
		
	}
	/**
	 * 
	 * ����һ��User�����ڽ��չ����л�����
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
	 * ���շ�������
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
	 * ������Ϸ��������
	 * @return ����һ��point����
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Point acceptPosition() throws ClassNotFoundException, IOException{
		System.out.println("������Ϸ����");
		if(socket!=null&&socket.isBound())
			return (Point) is.readObject();
		return null;
		
	}
	/**
	 * ������Ϸ��������
	 * @param pos ����һ��point����
	 * @throws IOException
	 */
	public void sendPosition(Point pos) throws IOException{
		if(socket!=null&&socket.isBound()){
			os.writeObject(pos);
			System.out.println("��Ϸ���귢��");
		}
	}
	
	/**
	 * ���ͷ���ȷ������
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
	 * ʵ�ֶ�client�����socket���ӺͲ���
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//���true�ر��߳�
		while(!threadFlag){
			try {
				Integer mod = this.getGameState();
				System.out.println(mod.intValue());
				if (mod.intValue() == 1) {
					Room.removeListItem(user);
					System.out.println("ɾ���б���");
				} else if (mod.intValue() == 2) {
					System.out.println("�ָ�����");
					Room.setList(user,Client.this);
				} else if (mod.intValue() == 3) {
					System.out.println("��֤ͨ��");
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
