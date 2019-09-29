import java.util.concurrent.atomic.AtomicInteger;

public class Score {
	private AtomicInteger missedWords;
	private AtomicInteger caughtWords;
	private AtomicInteger gameScore;
	
	Score() {
		missedWords = new AtomicInteger(0);
		caughtWords = new AtomicInteger(0);
		gameScore = new AtomicInteger(0);
	}

	public  int getMissed() {
		return missedWords.intValue();
	}

	public  int getCaught() {
		return caughtWords.intValue();
	}
	
	public  int getTotal() {
		return (missedWords.intValue() + caughtWords.intValue());
	}

	public  int getScore() {
		return gameScore.intValue();
	}
	
	void missedWord() {
		missedWords.getAndIncrement();
	}

	void caughtWord(int length) {
		caughtWords.getAndIncrement();
		gameScore.getAndAdd(length);
	}

	void resetScore() {
		missedWords = new AtomicInteger(0);
		caughtWords = new AtomicInteger(0);
		gameScore = new AtomicInteger(0);
	}
}
