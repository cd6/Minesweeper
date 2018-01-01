import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomSettingsWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtHeight;
	private JTextField txtWidth;
	private JTextField txtMines;


	/**
	 * Create the frame.
	 */
	public CustomSettingsWindow(GameWindow game) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		Box verticalBox = Box.createVerticalBox();
		panel.add(verticalBox);

		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);

		JLabel lblHeight = new JLabel("Height");
		horizontalBox.add(lblHeight);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);

		txtHeight = new JTextField();
		horizontalBox.add(txtHeight);
		txtHeight.setColumns(3);
		txtHeight.setDocument(new JTextFieldLimit(2));

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut);

		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);

		JLabel lblWidth = new JLabel("Width");
		horizontalBox_1.add(lblWidth);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut_1);

		txtWidth = new JTextField();
		horizontalBox_1.add(txtWidth);
		txtWidth.setDocument(new JTextFieldLimit(2));
		txtWidth.setColumns(3);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_1);

		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_2);

		JLabel lblMines = new JLabel("Mines");
		horizontalBox_2.add(lblMines);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut_2);

		txtMines = new JTextField();
		txtMines.setColumns(3);
		txtMines.setDocument(new JTextFieldLimit(2));
		horizontalBox_2.add(txtMines);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		JButton btnOk = new JButton("Okay");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String heightStr = txtHeight.getText();
				String widthStr = txtWidth.getText();
				String mineStr = txtMines.getText();
				if (!isInteger(heightStr) || !isInteger(widthStr) || !isInteger(mineStr) || heightStr.length() == 0 || widthStr.length() == 0 || mineStr.length() == 0) {
					JOptionPane.showMessageDialog(null, "Enter integers between 8 and 99", "Invalid numbers",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					int height = Integer.parseInt(heightStr);
					int width = Integer.parseInt(widthStr);
					int mines = Integer.parseInt(mineStr);
					if (height < 8 || width < 8) {
						JOptionPane.showMessageDialog(null, "Height and width must be greater than 8", "Invalid size",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (mines > (height*width)/3){
						JOptionPane.showMessageDialog(null, "Number of mines must be less than 1/3 of the total number of spaces", "Invalid number of mines",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (mines < 1){
						JOptionPane.showMessageDialog(null, "Number of mines cannot be less than 1", "Invalid number of mines",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						game.setCustomValues(height, width, mines);
						dispose();
					}
				}
			}
		});
		panel_1.add(btnOk);

		pack();
	}

	public boolean isInteger(String str){
		if(str != null){
			for(int i = 0; i < str.length(); i++){
				if(!Character.isDigit(str.charAt(i))){
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
