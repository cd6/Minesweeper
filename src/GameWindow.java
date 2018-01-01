import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;

public class GameWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JMenuBar menuBar;
	private JMenu mnGame;
	private JMenuItem mntmNewGame;
	private JTable tblBoard;
	private JMenuItem mntmBeginner;
	private JMenuItem mntmIntermediate;
	private JMenuItem mntmExpert;
	private JSeparator separator;
	private JSeparator separator_1;
	private JMenuItem mntmHighScores;
	private JSeparator separator_2;
	private JMenuItem mntmExit;
	private JMenuItem mntmCustom;
	private MouseListener listener;
	private Box horizontalBox;
	private JButton btnRestart;
	private JLabel lblTime;
	private JLabel lblMinesLeft;
	private Timer timer;
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private JMenu mnOptions;
	private JCheckBoxMenuItem chckbxmntmLessDense;
	private Component horizontalStrut;
	private Component horizontalStrut_1;

	private Level level;
	private int rows;
	private int columns;
	private int mines;
	private Board board; // instance of class
	private Space[][] boardArray; // matrix of spaces
	private String[][] displayArray;
	private int time;
	private boolean dense;
	private int noRevealed;
	private String bomb = "\uD83D\uDCA3";
	private String happy = "\uD83D\uDE42";
	private String sad = "\uD83D\uDE41 ";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow frame = new GameWindow();
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
	public GameWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 215);

		addMenuBar();

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		horizontalBox = Box.createHorizontalBox();
		contentPane.add(horizontalBox, BorderLayout.NORTH);

		horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);

		lblMinesLeft = new JLabel("New label");
		horizontalBox.add(lblMinesLeft);

		horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);

		btnRestart = new JButton(happy);
		btnRestart.setAlignmentX(Component.CENTER_ALIGNMENT);
		horizontalBox.add(btnRestart);
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startGame();
			}
		});

		horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_1);

		lblTime = new JLabel("New label");
		horizontalBox.add(lblTime);

		horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue_1);

		// default on beginner;
		setLevel(Level.BEGINNER, 8, 8, 10);

		startGame();
	}

	public void startGame() {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();

		time = 0;
		lblMinesLeft.setText(mines + "");
		lblTime.setText(time + " ");
		btnRestart.setText(happy);

		if (!(timer == null)) {
			timer.stop();
		}
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				time++;
				lblTime.setText(time + " ");
			}
		});
		timer.setInitialDelay(0);

		dense = chckbxmntmLessDense.isSelected();

		// board = new Board(rows, columns, mines, dense);
		// boardArray = board.getBoardArray();

		noRevealed = 0;
		fillBoard();
	}

	TableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
		// Color backgroundColor = getBackground();

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (boardArray[row][column].isRevealed()) {
				c.setBackground(Color.LIGHT_GRAY);
			} else {
				c.setBackground(Color.GRAY);
			}
			return c;
		}
	};

	public void fillBoard() {
		displayArray = new String[rows][columns];

		tblBoard = new JTable(new GameTableModel(rows, columns, displayArray));

		tblBoard.setTableHeader(null);
		// set width and height of cells
		for (int i = 0; i < tblBoard.getColumnCount(); i++) {
			tblBoard.getColumnModel().getColumn(i).setPreferredWidth(17);
		}
		tblBoard.setRowHeight(17);
		tblBoard.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblBoard.setRowSelectionAllowed(false);
		tblBoard.setBackground(Color.LIGHT_GRAY);

		// for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
		// tblBoard.getColumnModel().getColumn(columnIndex).setCellRenderer(new
		// ChangeColourCellRenderer(boardArray));
		// }

		panel.add(tblBoard);
		pack();

		cellClick();
	}

	// mouse listener for cell being clicked
	public void cellClick() {
		listener = new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent evt) {
				int row = tblBoard.rowAtPoint(evt.getPoint());
				int col = tblBoard.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					if (SwingUtilities.isLeftMouseButton(evt)) {
						// timer only starts on left click
						if (!timer.isRunning()) {
							// opening move cannot be mine
							board = new Board(rows, columns, mines, dense, row, col);
							boardArray = board.getBoardArray();
							// set cell renderer after boardArray is created on
							// opening move
							for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
								tblBoard.getColumnModel().getColumn(columnIndex)
										.setCellRenderer(new ChangeColourCellRenderer(boardArray));
							}
							timer.start();
						}
						revealCells(row, col);
					} else {
						if (SwingUtilities.isRightMouseButton(evt)) {
							flagCell(row, col);
						}
					}
					panel.revalidate();
					panel.repaint();
				}
			}
		};
		tblBoard.addMouseListener(listener);

	}

	public void revealCells(int row, int col) {
		Space current = boardArray[row][col];
		if (!current.isFlagged()) {
			displayArray[row][col] = getCellValue(row, col);
			current.setRevealed(true);
			noRevealed++;
			if (current.isMine()) {
				gameOver();
			} else {
				checkWin();
				if (current.getAdjMines() == 0) {
					for (int i = row - 1; i <= row + 1; i++) {
						for (int j = col - 1; j <= col + 1; j++) {
							if (!(i == row && j == col) && !board.outOfBounds(i, j) && !boardArray[i][j].isRevealed()) {
								revealCells(i, j);
							}
						}
					}
				}
			}
		}
	}

	public void flagCell(int row, int col) {
		Space current = boardArray[row][col];
		int minesLeft = Integer.parseInt(lblMinesLeft.getText());
		if (!current.isRevealed()) {
			if (current.isFlagged()) {
				current.setFlagged(false);
				displayArray[row][col] = "";
				lblMinesLeft.setText(minesLeft + 1 + "");
			} else {
				current.setFlagged(true);
				displayArray[row][col] = "\uD83D\uDEA9"; // flag
				// "\uD83C\uDFF4"; 1
				lblMinesLeft.setText(minesLeft - 1 + "");
			}
		}

	}

	// String that will be shown when cell is clicked
	public String getCellValue(int row, int col) {
		String cell = "";
		if (boardArray[row][col].isMine()) {
			// mine is *
			StringBuffer b = new StringBuffer();
			b.append(Character.toChars(127467));
			b.append(Character.toChars(127479));

			cell = bomb;
		} else if (boardArray[row][col].getAdjMines() != 0) {
			// numbers
			cell = boardArray[row][col].getAdjMines() + "";
		}
		return cell;
	}

	public void setLevel(Level level, int rows, int columns, int mines) {
		this.level = level;
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		lblMinesLeft.setText(mines + "");
	}

	public void gameOver() {
		stopGame();
		btnRestart.setText(sad);
	}

	public void checkWin() {
		if (rows * columns - noRevealed == mines) {
			stopGame();
			// no custom high scores
			if (level == Level.CUSTOM) {
				JOptionPane.showMessageDialog(this, "You win.");
			} else {
				GameWon winScreen = new GameWon(time, level);
				winScreen.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				winScreen.setVisible(true);
			}
		}
	}

	public void stopGame() {
		tblBoard.removeMouseListener(listener);
		timer.stop();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (boardArray[i][j].isMine()) {
					displayArray[i][j] = getCellValue(i, j);
				} else if (boardArray[i][j].isFlagged()) {
					displayArray[i][j] = "X";
				}
			}
		}
	}

	public void addMenuBar() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnGame = new JMenu("Game");
		menuBar.add(mnGame);

		mntmNewGame = new JMenuItem("New");
		mntmNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startGame();
			}
		});
		mnGame.add(mntmNewGame);

		separator = new JSeparator();
		mnGame.add(separator);

		mntmBeginner = new JMenuItem("Beginner");
		mntmBeginner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setLevel(Level.BEGINNER, 8, 8, 10);
				startGame();
			}
		});
		mnGame.add(mntmBeginner);

		mntmIntermediate = new JMenuItem("Intermediate");
		mntmIntermediate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setLevel(Level.INTERMEDIATE, 16, 16, 40);
				startGame();
			}
		});
		mnGame.add(mntmIntermediate);

		mntmExpert = new JMenuItem("Expert");
		mntmExpert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setLevel(Level.EXPERT, 16, 30, 99);
				startGame();
			}
		});
		mnGame.add(mntmExpert);

		mntmCustom = new JMenuItem("Custom");
		mntmCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				customSetUp();
			}
		});
		mnGame.add(mntmCustom);

		separator_1 = new JSeparator();
		mnGame.add(separator_1);

		mntmHighScores = new JMenuItem("High Scores");
		mntmHighScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HighScoreWindow hs = new HighScoreWindow();
				hs.setVisible(true);
			}
		});
		mnGame.add(mntmHighScores);

		separator_2 = new JSeparator();
		mnGame.add(separator_2);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnGame.add(mntmExit);

		mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		chckbxmntmLessDense = new JCheckBoxMenuItem("Less dense");
		mnOptions.add(chckbxmntmLessDense);
	}

	public void customSetUp() {
		level = Level.CUSTOM;
		CustomSettingsWindow cs = new CustomSettingsWindow(this);
		cs.setVisible(true);
	}

	public void setCustomValues(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		startGame();
	}

}
