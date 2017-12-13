package MainPackge;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import SwingTool.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;


/**
 * ������
 * @author AN
 *
 */
public class Checkerboard extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public  static boolean chessFlag=true;

	public final static int NUM=23;
	
	public static Chess[][] chesses=new Chess[NUM][NUM];
	//��һ���建��
	public Point lastChess;
	//������
	private boolean regrectFlag=true;

	//�жϲ�����
	public Strategy blackChess=new Strategy();
	public Strategy whiteChess=new Strategy();
	
	//��������
	public ChessListener chessListener=new ChessListener();
	public WhiteListener whiteListener=null;
	public BlackListener blackListener=null;
	
	//�����׽�����
	public Server server=null;
	public Client client=null;
	
	//���̿ؼ���
	public JPanel panel = new JPanel();
	public MyLabel blackTime=new MyLabel("0:0");
	public MyLabel whiteTime=new MyLabel("0:0");
	public JLabel logo=new JLabel();
	private MyButton startGame=new MyButton("���¿�ʼ");
	private MyButton btnRegect = new MyButton("����");
	public JLabel blackLogo=new JLabel();
	public JLabel whiteLogo=new JLabel();
	
	public static boolean threadFlag=false;
	//��ʱ���߳�
	public Thread chessThread=new Thread(new Runnable() {
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String text;
			int bm=0;
			int bs=0;
			int wm=0;
			int ws=0;
			while(threadFlag){
				if(Checkerboard.chessFlag){
					text=String.valueOf(bm)+":"+String.valueOf(bs++);
					if(bs==60){
						bm++;
						bs=0;
					}
					blackTime.setText(text);
				}else{
					text=String.valueOf(wm)+":"+String.valueOf(ws++);
					if(ws==60){
						wm++;
						ws=0;
					}
					whiteTime.setText(text);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	});
	
	//��ʼ������
	public void initialize(){
		
		setTitle("An������");
		setResizable(false);
		setSize(806, 879);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		panel.setLayout(null);
		panel.setBounds(0, 0, 800, 50);
		getContentPane().add(panel);
		//�������̵�ͼ�ͼ�����
		setMap();
		panel.add(startGame);
		startGame.setBounds(40, 15, 93, 23);
		startGame.addActionListener(new btnRestartGameListener());
		
		logo.setBounds((800-40)/2, 5, 40, 40);
		panel.add(logo);
		
		blackTime.setBounds((800-40)/2-100, 5, 100, 40);
		panel.add(blackTime);
		
		whiteTime.setBounds((800-40)/2+100, 5, 100, 40);
		panel.add(whiteTime);
		
		blackLogo.setBounds((800-32)/2-150, 9, 32, 32);
		blackLogo.setIcon(new IconMaker("blackChess").getIconPath());
		panel.add(blackLogo);
		whiteLogo.setBounds((800-32)/2+145, 9, 32, 32);
		whiteLogo.setIcon(new IconMaker("whiteChess").getIconPath());
		panel.add(whiteLogo);
		
		btnRegect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!regrectFlag){
					chesses[lastChess.y][lastChess.x].initialize();
					chesses[lastChess.y][lastChess.x].addMouseListener(chessListener);
					regrectFlag=true;
					lastChess=null;
				}else{
					java.awt.Toolkit.getDefaultToolkit().beep(); 
					MesageBox.show(Checkerboard.this, "ֻ�ܻ���һ����", "��ʾ");
				}
			}
		});
		btnRegect.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		btnRegect.setBounds(637, 13, 93, 23);
		panel.add(btnRegect);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setIcon(new IconMaker("ChessMap").getIconPath());
		lblNewLabel.setBounds(0, 50, 800, 800);
		getContentPane().add(lblNewLabel);
		
		//���úڰ�������
		blackChess.setFlag(1);
		whiteChess.setFlag(2);
		
		//��ʼ���ֶ�
		chessFlag=true;
		
		threadFlag=false;
		
		lastChess=null;
		
		regrectFlag=false;
		
	}

	
	
	/**
	 * 
	 * ���캯��
	 */
	public Checkerboard() {
		//��ʼ��
		initialize();
	}
	
	public Checkerboard(Client client) {
		this.client=client;
		initialize();
		startGame.setEnabled(false);
		btnRegect.setEnabled(false);
		removeAllListener();
		
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//���յ�һ����������
				Point pos;
				try {
					pos = client.acceptPosition();
					lastChess=pos;
					blackChess.downChess(pos.x, pos.y);
					if(!Checkerboard.threadFlag){
						Checkerboard.threadFlag=true;
						chessThread.start();
					}
					chesses[pos.y][pos.x].setIcon(chessFlag,2);
					whiteListener=new WhiteListener();
					setAllListener(whiteListener);
					chesses[pos.y][pos.x].removeMouseListener(whiteListener);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
		
	}
	
	public Checkerboard(Server server){
		this.server=server;
		initialize();
		btnRegect.setEnabled(false);
		startGame.setEnabled(false);
		removeAllListener();
		
		blackListener=new BlackListener();
		setAllListener(blackListener);
	}
	
	/*
	 * ********************
	 * 
	 * ********************������Ϸ�Ĺ��췽��
	 */
	public Checkerboard(int mark){
		initialize();
		//�Ƴ����м�����
		removeAll();
		if(mark==1){
			//����
			setAllListener(null);
			
		}
	}
	
	
	/**
	 * 
	 * ���õ�ͼ�ͼ�����
	 */
	public void setMap() {
		int kase=1;
		int w=9,h=59;
		for(int i=0;i<NUM;i++){
			for(int j=0;j<NUM;j++){
				chesses[i][j]=new Chess();
				chesses[i][j].setLocation(w, h);
				chesses[i][j].setName(String.valueOf(kase++));
				getContentPane().add(chesses[i][j]);
				chesses[i][j].addMouseListener(chessListener);
				w+=34;
			}
			h+=34;
			w=9;
		}
	}
	
	
	public void setAllListener(MouseAdapter ma){
		for(int i=0;i<NUM;i++){
			for(int j=0;j<NUM;j++){
				chesses[i][j].addMouseListener(ma);
			}
		}
	}
	
	
	/**
	 * 
	 * ��������в�����鷽��
	 */
	Point transitionBtnName(String name, int width, int height) {
		int x = 0, y = 0, value = 0;
		Point pos = new Point();
		value = Integer.parseInt(name) - 1;
		y = value / width;
		x = value % width;
		pos.x = x;
		pos.y = y;
		return pos;
	}
	
	
	//�������ڲ��࣬ʵ����Ӯ�ж�
	class ChessListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			boolean game;
			//��������
			Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), NUM, NUM);
			//����һ���µ����Ӹı��ԭ��
			if(lastChess!=null){
				chesses[lastChess.y][lastChess.x].setIcon(!chessFlag, 1);
			}
			//������һ������
			lastChess=pos;
			regrectFlag=false;
			
			//����
			if(Checkerboard.chessFlag){
				//������
				game=blackChess.downChess(pos.x, pos.y);
				if(!Checkerboard.threadFlag){
					Checkerboard.threadFlag=true;
					chessThread.start();
				}
			}else{
				//������
				game=whiteChess.downChess(pos.x, pos.y);
			}
			
			int x=pos.x;
			int y=pos.y;
			
			chesses[y][x].setIcon(chessFlag,2);
			chesses[y][x].removeMouseListener(this);
			
			//��Ӯ�ж�
			if(game){
				threadFlag=false;
				btnRegect.setEnabled(false);
				if(!Checkerboard.chessFlag){
					MesageBox.show(Checkerboard.this, "�ڷ�Ӯ��ʤ��", "��Ϸ��ʾ");
				}else{
					MesageBox.show(Checkerboard.this, "�׷�Ӯ��ʤ��", "��Ϸ��ʾ");
				}
				//�Ƴ����м�����
				removeAllListener();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), NUM, NUM);
			if(chesses[pos.y][pos.x].getFlag()==0){
				chesses[pos.y][pos.x].Chioce(true);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), NUM, NUM);
			if(chesses[pos.y][pos.x].getFlag()==0){
				chesses[pos.y][pos.x].Chioce(false);
			}
		}
	}
	
	
	/**
	 * ���������ս������
	 * @author AN
	 *
	 */
	class WhiteListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(!chessFlag){
				Thread t=new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						boolean game;
						//��������
						Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), NUM, NUM);
						//����һ���µ����Ӹı��ԭ��
						if(lastChess!=null){
							chesses[lastChess.y][lastChess.x].setIcon(!chessFlag, 1);
						}
						//������һ������
						lastChess=pos;
						regrectFlag=false;
						
						game=whiteChess.downChess(pos.x, pos.y);
						try {
							client.sendPosition(pos);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						int x=pos.x;
						int y=pos.y;
						
						chesses[y][x].setIcon(chessFlag,2);
						chesses[y][x].removeMouseListener(whiteListener);
						
						//��Ӯ�ж�
						if(game){
							threadFlag=false;
							btnRegect.setEnabled(false);
							if(!Checkerboard.chessFlag){
								MesageBox.show(Checkerboard.this, "�ڷ�Ӯ��ʤ��", "��Ϸ��ʾ");
							}else{
								MesageBox.show(Checkerboard.this, "�׷�Ӯ��ʤ��", "��Ϸ��ʾ");
							}
							//�Ƴ����м�����
							removeAllListener(whiteListener);
							return;
						}
						
						//���պ������================================================
						regrectFlag=false;
						
						try {
							pos=client.acceptPosition();

							if(lastChess!=null){
								chesses[lastChess.y][lastChess.x].setIcon(!chessFlag, 1);
								lastChess=pos;
							}
						} catch (ClassNotFoundException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						game=blackChess.downChess(pos.y, pos.x);
						
						x=pos.x;
						y=pos.y;
						
						chesses[y][x].setIcon(chessFlag,2);
						chesses[y][x].removeMouseListener(whiteListener);
						
						if(game){
							threadFlag=false;
							btnRegect.setEnabled(false);
							if(!Checkerboard.chessFlag){
								MesageBox.show(Checkerboard.this, "�ڷ�Ӯ��ʤ��", "��Ϸ��ʾ");
							}else{
								MesageBox.show(Checkerboard.this, "�׷�Ӯ��ʤ��", "��Ϸ��ʾ");
							}
							//�Ƴ����м�����
							removeAllListener(whiteListener);
						}
					}
				});
				t.start();
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), NUM, NUM);
			if(chesses[pos.y][pos.x].getFlag()==0){
				chesses[pos.y][pos.x].Chioce(true);
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), NUM, NUM);
			if(chesses[pos.y][pos.x].getFlag()==0){
				chesses[pos.y][pos.x].Chioce(false);
			}
		}
	}
	
	
	
	
	class BlackListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(chessFlag){
				Thread t=new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						boolean game;
						//��������
						Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), NUM, NUM);
						//����һ���µ����Ӹı��ԭ��
						if(lastChess!=null){
							chesses[lastChess.y][lastChess.x].setIcon(!chessFlag, 1);
						}
						//������һ������
						lastChess=pos;
						regrectFlag=false;
						
						game=blackChess.downChess(pos.x, pos.y);
						try {
							server.sendPosition(pos);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						int x=pos.x;
						int y=pos.y;
						
						chesses[y][x].setIcon(chessFlag,2);
						chesses[y][x].removeMouseListener(blackListener);
						
						//��Ӯ�ж�
						if(game){
							threadFlag=false;
							btnRegect.setEnabled(false);
							if(!Checkerboard.chessFlag){
								MesageBox.show(Checkerboard.this, "�ڷ�Ӯ��ʤ��", "��Ϸ��ʾ");
							}else{
								MesageBox.show(Checkerboard.this, "�׷�Ӯ��ʤ��", "��Ϸ��ʾ");
							}
							//�Ƴ����м�����
							removeAllListener(blackListener);
							return;
						}
						
						//���հ������================================================
						regrectFlag=false;
						
						try {
							pos=server.acceptPosition();
							
							if(lastChess!=null){
								chesses[lastChess.y][lastChess.x].setIcon(!chessFlag, 1);
								lastChess=pos;
							}
						} catch (ClassNotFoundException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						game=whiteChess.downChess(pos.x, pos.y);
						
						x=pos.x;
						y=pos.y;
						
						chesses[y][x].setIcon(chessFlag,2);
						chesses[y][x].removeMouseListener(blackListener);
						
						if(game){
							threadFlag=false;
							btnRegect.setEnabled(false);
							if(!Checkerboard.chessFlag){
								MesageBox.show(Checkerboard.this, "�ڷ�Ӯ��ʤ��", "��Ϸ��ʾ");
							}else{
								MesageBox.show(Checkerboard.this, "�׷�Ӯ��ʤ��", "��Ϸ��ʾ");
							}
							//�Ƴ����м�����
							removeAllListener(blackListener);
						}
					}
				});
				t.start();
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseEntered(e);
			Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), NUM, NUM);
			if(chesses[pos.y][pos.x].getFlag()==0){
				chesses[pos.y][pos.x].Chioce(true);
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseExited(e);
			Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), NUM, NUM);
			if(chesses[pos.y][pos.x].getFlag()==0){
				chesses[pos.y][pos.x].Chioce(false);
			}
		}
	}
	
	
	
	class BlackSingleListener extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(chessFlag){
				boolean game;
				//��������
				Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), NUM, NUM);
				//����һ���µ����Ӹı��ԭ��
				if(lastChess!=null){
					chesses[lastChess.y][lastChess.x].setIcon(!chessFlag, 1);
				}
				//������һ������
				lastChess=pos;
				regrectFlag=false;
				
				game=blackChess.downChess(pos.x, pos.y);
				try {
					server.sendPosition(pos);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				int x=pos.x;
				int y=pos.y;
				
				chesses[y][x].setIcon(chessFlag,2);
				chesses[y][x].removeMouseListener(blackListener);
				
				//��Ӯ�ж�
				if(game){
					threadFlag=false;
					btnRegect.setEnabled(false);
					if(!Checkerboard.chessFlag){
						MesageBox.show(Checkerboard.this, "�ڷ�Ӯ��ʤ��", "��Ϸ��ʾ");
					}else{
						MesageBox.show(Checkerboard.this, "�׷�Ӯ��ʤ��", "��Ϸ��ʾ");
					}
					//�Ƴ����м�����
					removeAllListener(blackListener);
					return;
				}
				
				//���հ������================================================
				regrectFlag=false;
				
				try {
					pos=server.acceptPosition();
					
					if(lastChess!=null){
						chesses[lastChess.y][lastChess.x].setIcon(!chessFlag, 1);
						lastChess=pos;
					}
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				game=whiteChess.downChess(pos.x, pos.y);
				
				x=pos.x;
				y=pos.y;
				
				chesses[y][x].setIcon(chessFlag,2);
				chesses[y][x].removeMouseListener(blackListener);
				
				if(game){
					threadFlag=false;
					btnRegect.setEnabled(false);
					if(!Checkerboard.chessFlag){
						MesageBox.show(Checkerboard.this, "�ڷ�Ӯ��ʤ��", "��Ϸ��ʾ");
					}else{
						MesageBox.show(Checkerboard.this, "�׷�Ӯ��ʤ��", "��Ϸ��ʾ");
					}
					//�Ƴ����м�����
					removeAllListener(blackListener);
				}
			}
		}
		
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseEntered(e);
			Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), NUM, NUM);
			if(chesses[pos.y][pos.x].getFlag()==0){
				chesses[pos.y][pos.x].Chioce(true);
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseExited(e);
			Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), NUM, NUM);
			if(chesses[pos.y][pos.x].getFlag()==0){
				chesses[pos.y][pos.x].Chioce(false);
			}
		}
	}
	
	/**
	 * 
	 * �Ƴ����м�����
	 */
	private void removeAllListener(){
		for(int i=0;i<NUM;i++){
			for(int j=0;j<NUM;j++){
				chesses[i][j].removeMouseListener(chessListener);
			}
		}
	}
	
	private void removeAllListener(MouseAdapter ma){
		for(int i=0;i<NUM;i++){
			for(int j=0;j<NUM;j++){
				chesses[i][j].removeMouseListener(ma);
			}
		}
	}
	
	//���β
}