
public class Space {

	private boolean mine;
	private int adjMines;
	private boolean revealed;
	private boolean flagged;
	
	public Space(){
		mine = false;
		adjMines = 0;
		revealed = false;
		flagged = false;
	}
	
	public void setMine(boolean mine){
		this.mine = mine;
	}
	
	public boolean isMine(){
		return mine;
	}
	
	public void setAdjMines(int adjMines){
		this.adjMines = adjMines;
	}
	
	public int getAdjMines(){
		return adjMines;
	}
	
	public void setRevealed(boolean isRevealed){
		this.revealed = isRevealed;
	}
	
	public boolean isRevealed(){
		return revealed;
	}
	
	public void setFlagged(boolean isFlagged){
		this.flagged = isFlagged;
	}
	
	public boolean isFlagged(){
		return flagged;
	}
}
