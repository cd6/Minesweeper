import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HighScore {

	private ArrayList<Score> scores;
	// all scores written to one file
	private final String HIGHSCORE_FILE;
	private Level level;

	ObjectOutputStream outputStream = null;
	ObjectInputStream inputStream = null;

	public HighScore(Level level) {
		scores = new ArrayList<Score>();
		this.level = level;
		HIGHSCORE_FILE = level.name().toLowerCase() + "Scores.dat"; // binary
																	// file type
	}

	// return arrayList of sorted scores
	public ArrayList<Score> getHighScores() {
		loadScores();
		// sort in ascending order
		Collections.sort(scores, (a, b) -> a.score < b.score ? -1 : a.score == b.score ? 0 : 1);
		return scores;
	}

	// load file and check for exceptions
	public void loadScores() {
		try {
			inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
			scores = (ArrayList<Score>) inputStream.readObject();
		} catch (FileNotFoundException e) {
			// System.out.println("[Load] FNF Error: " + e.getMessage());
			updateScores(); // create file
		} catch (IOException e) {
			System.out.println("[Load] IO Error: " + e.getMessage());
			for (StackTraceElement s : e.getStackTrace()) {
				System.out.println(s);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("[Load] CBF: " + e.getMessage());
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
				System.out.println("[Load] IO Error: " + e.getMessage());
			}
		}
	}

	// add score don't check level in class, assume it is correct
	public void addScore(String name, int score) {
		loadScores();
		scores.add(new Score(name, score));
		updateScores();
	}

	public void updateScores() {
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
			outputStream.writeObject(scores);
		} catch (FileNotFoundException e) {
			System.out.println("[Update] FNF Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("[Update] IO Error: " + e.getMessage());
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
				System.out.println("[Update] Error: " + e.getMessage());
			}
		}
	}

	// returns array of n best scores retaining order
	public Score[] getNBest(int n) {
		scores = getHighScores();
		if (n > scores.size()) {
			n = scores.size();
		}
		Score[] best = new Score[n];

		for (int i = 0; i < n; i++) {
			best[i] = scores.get(i);
		}
		return best;
	}

	public void deleteAll() {
	}

	public static void main(String[] args) {
		HighScore hm = new HighScore(Level.BEGINNER);
		/*
		 * hm.addScore("Bart",240); hm.addScore("Marge",300);
		 * hm.addScore("Maggie",220); hm.addScore("Homer",100);
		 * hm.addScore("Lisa",270);
		 */

		Score[] n = hm.getNBest(7);
		for (Score s : n) {
			System.out.println(s.name + " " + s.score);
		}
	}
}
