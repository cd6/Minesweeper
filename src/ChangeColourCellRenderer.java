import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ChangeColourCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Space[][] boardArray;

	public ChangeColourCellRenderer(Space[][] boardArray) {
		this.boardArray = boardArray;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {

		// Cells are by default rendered as a JLabel.
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		l.setHorizontalAlignment(CENTER);
		// Get the status for the current row.
		// CustomTableModel tableModel = (CustomTableModel) table.getModel();
		if (boardArray[row][col].isRevealed()) {
			l.setBackground(Color.LIGHT_GRAY.brighter());
			switch (l.getText()) {
			case "1":
				l.setForeground(Color.BLUE);
				break;
			case "2":
				l.setForeground(Color.GREEN);
				break;
			case "3":
				l.setForeground(Color.RED);
				break;
			case "4":
				l.setForeground(Color.BLUE.darker());
				break;
			case "5":
				l.setForeground(Color.RED.darker());
				break;
			case "6":
				l.setForeground(Color.CYAN.darker());
				break;
			case "7":
				l.setForeground(Color.MAGENTA.darker());
				break;
			case "8":
				l.setForeground(Color.GRAY);
				break;
			case "\uD83D\uDCA3":
				l.setBackground(Color.RED);
			default:
				l.setForeground(Color.BLACK);
				break;
			}
		} else {
			l.setBackground(Color.LIGHT_GRAY);
			if(l.getText().equals("X")){
				l.setForeground(Color.RED);
			} else {
				l.setForeground(Color.BLACK);
			}
		}

		// Return the JLabel which renders the cell.
		return l;

	}
}