import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;

public class GameWon extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JLabel lblWin;
	private JLabel lblTime;
	private JLabel lblName;
	private int time;
	private Level level;

	private JButton okButton;
	private JButton cancelButton;

	// for testing
	public static void main(String[] args) {
		try {
			GameWon dialog = new GameWon(0, Level.BEGINNER);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GameWon(int time, Level level) {
		this.time = time;
		this.level = level;
		setBounds(100, 100, 268, 185);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel = new JPanel();
		contentPanel.add(panel);

		Box verticalBox = Box.createVerticalBox();
		panel.add(verticalBox);

		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_2);
		{
			Component horizontalGlue = Box.createHorizontalGlue();
			horizontalBox_2.add(horizontalGlue);
		}
		{
			lblWin = new JLabel("You won!");
			horizontalBox_2.add(lblWin);
			lblWin.setHorizontalAlignment(SwingConstants.CENTER);
		}
		{
			Component horizontalGlue = Box.createHorizontalGlue();
			horizontalBox_2.add(horizontalGlue);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			verticalBox.add(verticalStrut);
		}

		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		{
			lblTime = new JLabel("Time:  ");

			horizontalBox.add(lblTime);
		}
		{
			Component horizontalStrut = Box.createHorizontalStrut(20);
			horizontalBox.add(horizontalStrut);
		}
		{
			JLabel lblGameTime = new JLabel("" + time);
			horizontalBox.add(lblGameTime);
		}
		{
			Component horizontalGlue = Box.createHorizontalGlue();
			horizontalBox.add(horizontalGlue);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			verticalBox.add(verticalStrut);
		}

		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);
		{
			lblName = new JLabel("Name:");
			horizontalBox_1.add(lblName);
		}
		{
			Component horizontalStrut = Box.createHorizontalStrut(20);
			horizontalBox_1.add(horizontalStrut);
		}
		{
			txtName = new JTextField();
			txtName.setDocument(new JTextFieldLimit(20));
			// Listen for changes in the text
			txtName.getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {
					checkTextLength();
				}

				public void removeUpdate(DocumentEvent e) {
					checkTextLength();
				}

				public void insertUpdate(DocumentEvent e) {
					checkTextLength();
				}

				public void checkTextLength() {
					String text = txtName.getText();
					if (text.length() == 0) {
						okButton.setEnabled(false);
					} else {
						okButton.setEnabled(true);
					}
				}
			});
			horizontalBox_1.add(txtName);
			txtName.setColumns(10);
		}
		{
			Component horizontalGlue = Box.createHorizontalGlue();
			horizontalBox_1.add(horizontalGlue);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// add score
						addNewScore();
						// display high scores
						viewHighScores();
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				okButton.setEnabled(false);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		pack();
	}

	public void addNewScore() {
		HighScore newScore = new HighScore(level);
		newScore.addScore(txtName.getText(), time);
	}

	public void viewHighScores() {
		HighScoreWindow hsWindow = new HighScoreWindow();
		hsWindow.setVisible(true);
	}
}
