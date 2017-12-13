package MainPackge;

import javax.swing.JLabel;

/**
 * 
 * 棋子类
 * @author AN
 *
 */
class Chess extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//1是黑棋，2是白棋
	public int flag=0;
	
	public Chess() {
		// TODO Auto-generated constructor stub
		setSize(32, 32);
	}
	
	
	public void setIcon(boolean flag,int mod){
		
		if(mod==1){
			if(flag){
				this.setIcon(new IconMaker("blackChess").getIconPath());
				Checkerboard.chessFlag=false;
				this.flag=1;
			}else{
				this.setIcon(new IconMaker("whiteChess").getIconPath());
				Checkerboard.chessFlag=true;
				this.flag=2;
			}
		}else{
			if(flag){
				this.setIcon(new IconMaker("downBlack").getIconPath());
				Checkerboard.chessFlag=false;
				this.flag=1;
			}else{
				this.setIcon(new IconMaker("downWhite").getIconPath());
				Checkerboard.chessFlag=true;
				this.flag=2;
			}
		}
	}
	
	public void Chioce(boolean b){
		if(b){
			this.setIcon(new IconMaker("chioce").getIconPath());
		}else{
			this.setIcon(null);
		}
	}
	/**
	 * 
	 * 初始化棋子
	 */
	public void initialize(){
		this.setIcon(null);
		this.flag=0;
		if(Checkerboard.chessFlag){
			Checkerboard.chessFlag=false;
		}else{
			Checkerboard.chessFlag=true;
		}
	}
	
	public int getFlag() {
		return flag;
	}
}