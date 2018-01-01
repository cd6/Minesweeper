
public class Board {

	private Space[][] board;
	private int rows;
	private int columns;
	private int mines;
	private boolean dense;
	private int startRow;
	private int startCol;

	
	public Board(int rows, int columns, int mines, boolean dense, int startRow, int startCol) {
		// set maximum number of mines to 1/board size
		if(mines > (rows*columns)/3){
			mines = (rows*columns)/3;
		}
		if(rows > 50){
			rows = 50;
		}
		if(columns > 50){
			columns = 50;
		}
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		this.dense = dense;
		this.startRow = startRow;
		this.startCol = startCol;
		createBoard();
	}

	private void createBoard() {
		
		board = new Space[rows][columns];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				board[i][j] = new Space();
			}
		}

		// randomly fill in board with correct number of mines
		while (mines > 0) {
			int r = (int) (rows * Math.random());
			int c = (int) (columns * Math.random());
			if (isValidSpace(r, c)) {
				board[r][c].setMine(true);
				mines--;
			}
		}
		
		// calculate number of adjacent mines for all non-mine spaces
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				// don't need number for mine spaces so don't waste time computing
				if(!board[i][j].isMine()){
					board[i][j].setAdjMines(minesAdjacent(i, j));
				}
			}
		}
	}

	// on board and not already mine or first space clicked
	// not dense mines cannot be surrounded entirely by mines or by more than 6
	private boolean isValidSpace(int row, int column) {
		if (!board[row][column].isMine() && !(row == startRow && column == startCol)) {
			// if dense option selected 
			if (dense || ((minesAdjacent(row, column) / totalAdjacent(row, column)) <= 0.8)) {
				return true;
			} 
		} 
		return false;
	}

	// number of mines surrounding space
	private int minesAdjacent(int row, int column) {
		int num = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				// not space itself or off board
				if (!(i == 0 && j == 0 || outOfBounds(row + i, column + j))) {
					if (board[row + i][column + j].isMine()) {
						num++;
					}
				}
			}
		}
		return num;
	}

	// total number of surrounding spaces
	private int totalAdjacent(int row, int column) {
		int num = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				// not space itself or off board
				if (!(i == 0 && j == 0 || outOfBounds(i, j))) {
					// only checks if space exists
					num++;
				}
			}
		}
		return num;
	}

	// true if coordinates are not on board
	public boolean outOfBounds(int row, int column) {
		if (row < 0 || column < 0 || row >= rows || column >= columns) {
			return true;
		}
		return false;
	}

	public Space getSpace(int row, int column){
		return board[row][column];
	}
	
	public Space[][] getBoardArray(){
		return board;
	}
}
