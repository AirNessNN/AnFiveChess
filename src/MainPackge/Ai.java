package MainPackge;

/**
 * Ai�ࣨ�ѷ�����
 * @author AN
 *
 */
public class Ai {
	/**
	 * ���̻���
	 */
	private static Chess[][] chess;
	
	/**
	 * mark=1������<br/>mark=2������
	 */
	private static int mark;
	
	/**
	 * ���캯��
	 * @param mark �ڰ����־
	 */
	public Ai(int mark) {
		// TODO Auto-generated constructor stub
		//��ʼ������ֵ
		chess=new Chess[Checkerboard.NUM][Checkerboard.NUM];
		
		this.mark=mark;
		
		
	}
	
	
}
