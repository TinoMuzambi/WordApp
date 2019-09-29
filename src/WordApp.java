import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class WordApp {
	private static int noWords=4;
	static int totalWords;

    private static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

	private static WordRecord[] words;
    private static JLabel caught;
    private static JLabel missed;
    private static JLabel scr;
	static 	Score score = new Score();
    static JFrame frame;

	private static WordPanel w;

    /**
     * Method used to update the GUI score labels.
     */
	static void updateLabels() {
        caught.setText("Caught: " + score.getCaught() + "    ");
        missed.setText("Missed:" + score.getMissed() + "    ");
        scr.setText("Score:" + score.getScore() + "    ");
    }

    /**
     * Creates the game GUI.
     * @param frameX x-size of the GUI frame.
     * @param frameY y-size of the GUI frame.
     * @param yLimit "bottom" of the GUI frame where words will be considered dropped.
     */
	private static void setupGUI(int frameX, int frameY, int yLimit) {
		// Frame init and dimensions
    	frame = new JFrame("WordGame");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameX, frameY);

      	JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS));
      	g.setSize(frameX,frameY);


		w = new WordPanel(words,yLimit);
		w.setSize(frameX,yLimit+100);
	    g.add(w);


	    JPanel txt = new JPanel();
	    txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS));
	    caught =new JLabel("Caught: " + score.getCaught() + "    ");
	    missed =new JLabel("Missed:" + score.getMissed()+ "    ");
	    scr =new JLabel("Score:" + score.getScore()+ "    ");
	    txt.add(caught);
	    txt.add(missed);
	    txt.add(scr);

	    //[snip]

	    final JTextField textEntry = new JTextField("",20);
	   textEntry.addActionListener(new AbstractAction() {
		   @Override
		   public void actionPerformed(ActionEvent e) {
			   String text = textEntry.getText();
			   for (int i = 0; i < noWords; i++) {
				   if (words[i].matchWord(text)) {
					   words[i].resetWord();
					   score.caughtWord(text.length());
				   }
				   updateLabels();
				   if (score.getTotal() >= totalWords) {
                       JOptionPane.showMessageDialog(frame, "Game over.");
                   }
			   }

			   //[snip]
			   textEntry.setText("");
			   textEntry.requestFocus();
		   }
	   });

	   txt.add(textEntry);
	   txt.setMaximumSize( txt.getPreferredSize() );
	   g.add(txt);

	    JPanel b = new JPanel();
        b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));
	   	JButton startB = new JButton("Start");

			startB.addActionListener(e -> {
                WordPanel.done = false;
                w.run();
                textEntry.requestFocus();  //return focus to the text entry field
            });
		JButton endB = new JButton("End");
				endB.addActionListener(e -> {
                    w.setStart(false);
                    score.resetScore();
                    for (int i = 0; i < noWords; i++) {
                        words[i].setWord("");
                        words[i].resetWord();
                    }
                    updateLabels();
                    w.repaint();
                });

		JButton quitB = new JButton("Quit");
		quitB.addActionListener(e -> System.exit(0));

		b.add(startB);
		b.add(endB);
		b.add(quitB);

		g.add(b);

      	frame.setLocationRelativeTo(null);  // Center window on screen.
      	frame.add(g); //add contents to window
        frame.setContentPane(g);
        frame.setVisible(true);
	}

    /**
     * Loads dictionary using textfile into an array.
     * @param filename name of the textfile with the words.
     * @return a list containing all the words from the textfile.
     */
    private static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();

			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]= dictReader.next();
			}
			dictReader.close();
		} catch (IOException e) {
	        System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
		return dictStr;

	}

	public static void main(String[] args) throws Exception {
		totalWords=Integer.parseInt(args[0]);  //total words to fall
		noWords=Integer.parseInt(args[1]); // total words falling at any point
		if (totalWords>=noWords) {
            String[] tmpDict = getDictFromFile(args[2]); //file of words
            if (tmpDict != null)
                dict = new WordDictionary(tmpDict);

            WordRecord.dict = dict; //set the class dictionary for the words.

            words = new WordRecord[noWords];  //shared array of current words

            int frameX = 1000;
            int frameY = 600;
            int yLimit = 480;
            setupGUI(frameX, frameY, yLimit);
            //Start WordPanel thread - for redrawing animation

            int x_inc = frameX / noWords;
            //initialize shared array of current words

            for (int i = 0; i < noWords; i++) {
                words[i] = new WordRecord(dict.getNewWord(), i * x_inc, yLimit);
            }
        }
		else {
		    throw new Exception("totalwords > noWords");
        }


	}

}
