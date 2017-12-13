package MainPackge;

import java.awt.Point;

/**
 * 输赢判断类
 * @author AN
 *
 */
public class Strategy {
	
	private int flag;
	
	//public Queue<Point> position=new LinkedList<>();
	
	private Point thisPosition;
	
	private int[] kase=new int[4];
	
	public boolean downChess(int x,int y) {
		
		thisPosition=new Point(x, y);
		
		for(int i=0;i<4;i++){
			kase[i]=1;
		}
		
		//添加相邻列队
		for(int i=-1;i<2;i++){
			for(int j=-1;j<2;j++){
				if(i==0&&j==0){
					continue;
				}
				if(y+i<0||x+j<0||y+i>22||x+j>22)
					continue;
				if(Checkerboard.chesses[y+i][x+j].flag==flag){
					int a=0,b=0;
					int m=getGradle(new Point(x+j, y+i), thisPosition);
					
					switch(m){
					case 1:{
						
						while(Checkerboard.chesses[--a+y][--b+x].flag==flag){
							kase[0]++;
							if(a+y==0||b+x==0){
								break;
							}
						}
						break;
					}
					case 2:{
						
						while(Checkerboard.chesses[++a+y][++b+x].flag==flag){
							kase[0]++;
							if(a+y==22||b+x==22){
								break;
							}
						}
						break;
					}
					case 3:{
						
						while(Checkerboard.chesses[--a+y][x].flag==flag){
							kase[1]++;
							if(a+y==0){
								break;
							}
						}
						break;
					}
					case 4:{
	
						while(Checkerboard.chesses[++a+y][x].flag==flag){
							kase[1]++;
							if(a+y==22){
								break;
							}
						}
						break;
					}
					case 5:{
						
						while(Checkerboard.chesses[--a+y][++b+x].flag==flag){
							kase[2]++;
							if(a+y==0||b+x==22){
								break;
							}
						}
						break;
					}
					case 6:{
						
						while(Checkerboard.chesses[++a+y][--b+x].flag==flag){
							kase[2]++;
							if(a+y==22||b+x==0){
								break;
							}
						}
						break;
					}
					case 7:{
						
						while(Checkerboard.chesses[y][--b+x].flag==flag){
							kase[3]++;
							if(b+x==0){
								break;
							}
						}
						break;
					}
					case 8:{
						
						while(Checkerboard.chesses[y][++b+x].flag==flag){
							kase[3]++;
							if(b+x==22){
								break; 
							}
						}
						break;
					}
					}
				}
			}
		}
		//========
		for(int i=0;i<4;i++){
			if(flag==1)
				System.out.println(i+"黑"+kase[i]);
			else
				System.out.println(i+"白"+kase[i]);
			if(kase[i]>=5){
				return true;
			}
		}
		return false;
	}
	
	private int getGradle(Point target,Point chess) {
		
		if((target.x<chess.x&&target.y<chess.y)){
			System.out.println("左上");
			return 1;
		}else if((target.x>chess.x&&target.y>chess.y)){
			System.out.println("右下");
			return 2;
		}else if((target.x==chess.x&&target.y<chess.y)){
			System.out.println("正上");
			return 3;
		}else if((target.x==chess.x&&target.y>chess.y)){
			System.out.println("正下");
			return 4;
		}else if((target.x>chess.x&&target.y<chess.y)){
			System.out.println("右上");
			return 5;
		}else if((target.x<chess.x&&target.y>chess.y)){
			System.out.println("左下");
			return 6;
		}else if((target.x<chess.x&&target.y==chess.y)){
			System.out.println("正左");
			return 7;
		}else if((target.x>chess.x&&target.y==chess.y)){
			System.out.println("正右");
			return 8;
		}
		return 0;
	}
	
	/**
	 * 
	 * 配置黑棋还是白棋，黑棋true，白棋false
	 * @param flag
	 */
	public void setFlag(int flag) {
		this.flag=flag;
	}
	
	
}
