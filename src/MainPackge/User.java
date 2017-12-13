package MainPackge;
/**
 * 
 * �û���
 * @author huhao
 *
 */
public class User implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//��������
	private String roomName;
	//���������Ǻ�����
	private boolean canSetPassword;
	private String roomPassword;
	//ʱ�����Ʊ�Ǻ�ʱ��������ֵ
	private boolean canSetTimeLimit;
	private int timeLimitValue;
	//���ֱ��
	private boolean canJinShou;
	//������
	private boolean canRegrect;
	
	public User(String name,String password,boolean passwordFlag,boolean timeFlag,int timeValue,boolean jinShouFlag,boolean regrectFlag) {
		// TODO Auto-generated constructor stub
		setRoomName(name);
		setRoomPassword(password);
		setCanSetPassword(passwordFlag);
		setCanJinShou(jinShouFlag);
		setCanRegrect(regrectFlag);
		setTimeLimitValue(timeValue);
		setCanSetTimeLimit(timeFlag);
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public boolean isCanSetPassword() {
		return canSetPassword;
	}

	public void setCanSetPassword(boolean canSetPassword) {
		this.canSetPassword = canSetPassword;
	}

	public String getRoomPassword() {
		return roomPassword;
	}

	public void setRoomPassword(String roomPassword) {
		this.roomPassword = roomPassword;
	}

	public boolean isCanSetTimeLimit() {
		return canSetTimeLimit;
	}

	public void setCanSetTimeLimit(boolean canSetTimeLimit) {
		this.canSetTimeLimit = canSetTimeLimit;
	}

	public int getTimeLimitValue() {
		return timeLimitValue;
	}

	public void setTimeLimitValue(int timeLimitValue) {
		this.timeLimitValue = timeLimitValue;
	}

	public boolean isCanJinShou() {
		return canJinShou;
	}

	public void setCanJinShou(boolean canJinShou) {
		this.canJinShou = canJinShou;
	}

	public boolean isCanRegrect() {
		return canRegrect;
	}

	public void setCanRegrect(boolean canRegrect) {
		this.canRegrect = canRegrect;
	}

	
	
}
