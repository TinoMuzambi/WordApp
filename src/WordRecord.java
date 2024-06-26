public class WordRecord {
	private String text;
	private  int x;
	private int y;
	private int maxY;
	private boolean dropped;
	
	private int fallingSpeed;
	private static int maxWait = 1500;
	private static int minWait = 100;

	static WordDictionary dict;
	

	
	WordRecord() {
		text = "";
		x = 0;
		y = 0;
		maxY = 300;
		dropped = false;
		fallingSpeed = (int)(Math.random() * (maxWait - minWait) + minWait);
	}
	
	WordRecord(String text) {
		this();
		this.text = text;
	}
	
	WordRecord(String text,int x, int maxY) {
		this(text);
		this.x = x;
		this.maxY = maxY;
	}
	
	public synchronized void setY(int y) {
		if (y > maxY) {
			y = maxY;
			dropped = true;
		}
		this.y = y;
	}
	
	public synchronized void setX(int x) {
		this.x = x;
	}
	
	public synchronized void setWord(String text) {
		this.text = text;
	}

	public synchronized String getWord() {
		return text;
	}
	
	public synchronized int getX() {
		return x;
	}	
	
	public synchronized int getY() {
		return y;
	}
	
	public synchronized int getSpeed() {
		return fallingSpeed;
	}

	public void setPos(int x, int y) {
		setY(y);
		setX(x);
	}

	private void resetPos() {
		setY(0);
	}

	void resetWord() {
		resetPos();
		text = dict.getNewWord();
		dropped = false;
		fallingSpeed = (int)(Math.random() * (maxWait-minWait)+minWait);

	}
	
	boolean matchWord(String typedText) {
		if (typedText.equals(this.text)) {
			resetWord();
			return true;
		}
		else
			return false;
	}
	

	public synchronized void drop(int inc) {
		setY(y + inc);
	}
	
	public synchronized boolean dropped() {
		return dropped;
	}

}
