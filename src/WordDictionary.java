public class WordDictionary {
	private int size;
	private static String [] theDict = {"litchi","banana","apple","mango","pear","orange","strawberry",
		"cherry","lemon","apricot","peach","guava","grape","kiwi","quince","plum","prune",
		"cranberry","blueberry","rhubarb","fruit","grapefruit","kumquat","tomato","berry",
		"boysenberry","loquat","avocado"}; //default dictionary
	
	WordDictionary(String [] tmp) {
		size = tmp.length;
		theDict = new String[size];
		System.arraycopy(tmp, 0, theDict, 0, size);
		
	}
	
	WordDictionary() {
		size = theDict.length;
	}

	/**
	 * Obtain random new word from the dictionary.
	 * @return random word from dictionary.
	 */
	synchronized String getNewWord() {
		int wdPos = (int)(Math.random() * size);
		return theDict[wdPos];
	}
	
}
