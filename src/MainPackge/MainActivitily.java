package MainPackge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

//import fileSync.IconMaker;
/**
 * 
 * ���࣬��������������Ϸ
 * @author AN
 *
 */
public class MainActivitily {
	
	public static MainWindow mw;
	
	public static void main(String[] args) {
		
		String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		/*checkBoard=new Checkerboard();
		checkBoard.setVisible(true);*/
		mw=new MainWindow();
		mw.setVisible(true);
		
	}

}

/**
 * �ؿ���Ϸ�ļ�����
 * @author huhao
 *
 */
class btnRestartGameListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		MainWindow.checkBoard.dispose();
		Checkerboard.threadFlag=false;
		Checkerboard.chessFlag=true;
		MainWindow.checkBoard=new Checkerboard();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		MainWindow.checkBoard.setVisible(true);
	}
}


class IconMaker {
	private URL url;
	private Icon icon;
	private String path;
	/**
	 * 
	 * @param path ����Ŀ¼�е��ļ���.png��ʽ
	 */
	public IconMaker(String path) {
		// TODO �Զ����ɵĹ��캯�����
				this.path=path+".png";
				url=IconMaker.class.getResource(this.path);
				this.icon=new ImageIcon(url);
	}
	public Icon getIconPath(){
		return icon;
	}
}