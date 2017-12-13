package MainPackge;

import javax.swing.JFrame;
import javax.swing.JSlider;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 废弃的单人游戏类
 * @author AN
 *
 */
public class SinglePlayer extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SinglePlayer() {
		setResizable(false);
		setTitle("\u6E38\u620F\u524D\u914D\u7F6E");
		getContentPane().setLayout(null);
		setSize(197,182);
		
		JSlider slider = new JSlider();
		slider.setMinimum(1);
		slider.setBounds(6, 79, 167, 23);
		slider.setMaximum(3);
		slider.setPaintLabels(true);
		slider.setValue(1);
		getContentPane().add(slider);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("\u7535\u8111\u5148\u624B");
		chckbxNewCheckBox.setBounds(6, 6, 103, 23);
		getContentPane().add(chckbxNewCheckBox);
		
		JLabel label = new JLabel("\u7535\u8111\u7B49\u7EA7");
		label.setBounds(26, 54, 54, 15);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("1");
		label_1.setBounds(90, 54, 22, 15);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u7EA7");
		label_2.setBounds(109, 54, 54, 15);
		getContentPane().add(label_2);
		
		JButton btnNewButton = new JButton("\u5F00\u59CB");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxNewCheckBox.isSelected()){
					
				}else{
					
				}
			}
		});
		btnNewButton.setBounds(89, 123, 93, 23);
		getContentPane().add(btnNewButton);
		// TODO Auto-generated constructor stub
		
	}
}
