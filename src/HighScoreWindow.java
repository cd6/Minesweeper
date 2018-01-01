import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HighScoreWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblBeginner;
	private JTable tblIntermediate;
	private JTable tblExpert;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HighScoreWindow frame = new HighScoreWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HighScoreWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		tblBeginner = fillTable(Level.BEGINNER);
		tabbedPane.addTab("Beginner", null, tblBeginner, null);
		
		tblIntermediate = fillTable(Level.INTERMEDIATE);
		tabbedPane.addTab("Intermediate", null, tblIntermediate, null);
		
		tblExpert = fillTable(Level.EXPERT);
		tabbedPane.addTab("Expert", null, tblExpert, null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnDeleteSelected = new JButton("Delete");
		btnDeleteSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				///////////////////////////////////////////////////
			}
		});
		panel.add(btnDeleteSelected);
		
		JButton btnDeleteAll = new JButton("Delete All");
		btnDeleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				////////////////////////////////////////////////////
			}
		});
		panel.add(btnDeleteAll);
		
	}
	
	public JTable fillTable( Level lvl){
		String[] headers = {"Rank", "Name", "Time"};
		
		HighScore hs = new HighScore(lvl);
		
		Score[] scores = hs.getNBest(10);
		int n = scores.length;
		// scores.length accounts for table with less than 10 entries
		Object[][] scoreMatrix = new Object[n][3];
		
		for(int i = 0; i < n; i++){
			scoreMatrix[i][0] = i + 1 + ".";
			scoreMatrix[i][1] = scores[i].getName();
			scoreMatrix[i][2] = scores[i].getScore();
		}
		
		JTable tbl = new JTable(new HighScoreTableModel(scoreMatrix, headers));
		
		return tbl;
	}
	

}
