import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class WordPanel extends JPanel implements Runnable {
    public static volatile boolean done;
    private boolean start;
    private WordRecord[] words;
    private int noWords;
    private int maxY;

    public void setStart(boolean start) {
        this.start = start;
    }

    /**
     * Paint words to the screen.
     * @param g the parentComponent being painting to.
     */
    public void paintComponent(Graphics g) {
            WordThread wt = new WordThread();
		    int width = getWidth();
		    int height = getHeight();
            g.clearRect(0,0,width,height);
            g.setColor(Color.red);
            g.fillRect(0,maxY-10,width,height);

            g.setColor(Color.black);
            g.setFont(new Font("Helvetica", Font.PLAIN, 26));

		    for (int i=0;i<noWords;i++){
                g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()+20);  //y-offset for skeleton so that you can see the words
		    }
            wt.start();
		  }

		WordPanel(WordRecord[] words, int maxY) {
        this.words = words;
        noWords = words.length;
        done = false;
        this.maxY = maxY;
    }

    public void run() {
        if (!done) {
            start = true;
        }
    }

    /**
     * An object of this class is a thread which handles the falling words.
     */
    class WordThread extends Thread {

        @Override
        public void run() {
            super.run();
            if (WordApp.score.getTotal() < WordApp.totalWords) {
                try {
                    Thread.sleep(words[0].getSpeed());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (start) {
                    for (int i = 0; i < noWords; i++) {
                        Random r = new Random();
                        int count = r.nextInt(30);
                        words[i].drop(count);
                        if (words[i].dropped()) {
                            WordApp.score.missedWord();
                            WordApp.updateLabels();
                            words[i].setWord("");
                            words[i].resetWord();
                        }
                    }
                }
                repaint();
            }
            else {
                done = true;
                JOptionPane.showMessageDialog(WordApp.frame, "Game over.");
                interrupt();
            }
        }
    }

}


