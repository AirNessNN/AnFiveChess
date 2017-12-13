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
 * ��������<br/>���ڴ�������ʱ������UDP����������
 * @author AN
 *
 */
public class Server{
	// �����׽���
	public ServerSocket serverSocket=null;
	
	public Socket socket=null;

	private ObjectInputStream is = null;

	private ObjectOutputStream os = null;

	User user;
	
	public Server() {
		// TODO Auto-generated constructor stub
		try {
			//��ʼ���������׽���
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
				//��������
				try {
					System.out.println("�ȴ�����");
					socket=serverSocket.accept();
					os = new ObjectOutputStream(socket.getOutputStream());
					is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
					//����Ϸ
					Checkerboard ck=new Checkerboard(Server.this);
					ck.setVisible(true);
					System.out.println("���ӳɹ�");
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				/*User user=new User(Room.cr.name, Room.cr.password, Room.cr.passwordF, Room.cr.timeLimit, Room.cr.timeLimitValue, Room.cr.jinShou, Room.cr.regrect);
				//���Ͷ���
				try {
					os.writeObject(user);
					//��ջ�����
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
	 * ������Ϸ״̬
	 * @param gameState intֵ<br/>1��ɾ���б����Ϸ�����Ѿ�ȡ��������<br/>2������б����Ϸ�������<br/>3����Ϸ��֤ͨ��<br/>
	 * @throws IOException
	 */
	public void sendGameState(int gameState) throws IOException{
		if(socket!=null&&socket.isBound())
			os.writeObject(new Integer(gameState));
		//����
		System.out.println("����������Ϸ����:"+gameState);
		
		//���տͻ��˷���ȷ������
		acceptGameState();
	}
	
	public void acceptGameState(){
		if(socket!=null&&socket.isBound()){
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						System.out.println("��ʼ����ȷ������");
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
	 * �ж��Ƿ�����
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
	 * �ر�socket
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
