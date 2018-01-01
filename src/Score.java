import java.io.Serializable;

public class Score implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	int score;
	
	public Score(String name, int score){
		this.name = name;
		this.score = score;
	}
	
	public String getName(){
		return name;
	}
	
	public int getScore(){
		return score;
	}
}
