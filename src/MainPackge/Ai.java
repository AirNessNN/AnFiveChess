package MainPackge;

/**
 * Ai类（已废弃）
 * @author AN
 *
 */
public class Ai {
	/**
	 * 棋盘缓存
	 */
	private static Chess[][] chess;
	
	/**
	 * mark=1：黑棋<br/>mark=2：白棋
	 */
	private static int mark;
	
	/**
	 * 构造函数
	 * @param mark 黑白棋标志
	 */
	public Ai(int mark) {
		// TODO Auto-generated constructor stub
		//初始化所有值
		chess=new Chess[Checkerboard.NUM][Checkerboard.NUM];
		
		this.mark=mark;
		
		
	}
	
	
}
