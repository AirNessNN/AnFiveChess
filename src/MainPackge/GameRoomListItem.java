package MainPackge;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * ·ÏÆúµÄÀà
 * @author AN
 *
 */
public class GameRoomListItem {
	
	public String title;
	
	public User user;
	
	public Client client=null;
	
	public GameRoomListItem(User user,Client client) {
		// TODO Auto-generated constructor stub
		title=user.getRoomName();
		this.user=user;
		this.client=client;
	}
}

class MyCell extends JLabel implements ListCellRenderer<GameRoomListItem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<? extends GameRoomListItem> list, GameRoomListItem value,
			int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		setText("  "+value.title);
		setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 15));
		if(isSelected){
            setBackground(new Color(125, 125, 125));
            setForeground(list.getSelectionForeground());
        }else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		setEnabled(list.isEnabled());
	    setOpaque(true);
		return this;
	}  
}
