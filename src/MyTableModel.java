import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
	
	
	private static final long serialVersionUID = -942888322203230285L;
	private int rows;
	private int columns;
	private String[][] displayArray;
	
	public MyTableModel(int rows, int columns, String[][] displayArray){
		this.rows = rows;
		this.columns = columns;
		this.displayArray = displayArray;
	}

	@Override
	public int getColumnCount() {
		return columns;
	}

	@Override
	public int getRowCount() {
		return rows;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return displayArray[row][col];
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	};
}
