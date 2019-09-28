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

    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.clearRect(0, 0, width, height);
        g.setColor(Color.red);
        g.fillRect(0, maxY - 10, width, height);

        g.setColor(Color.black);
        g.setFont(new Font("Helvetica", Font.PLAIN, 26));
        //draw the words
        //animation must be added
        for (int i = 0; i < noWords; i++) {
            //g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());
            g.drawString(words[i].getWord(), words[i].getX(), words[i].getY() + 20);  //y-offset for skeleton so that you can see the words
        }
        if (WordApp.score.getTotal() < WordApp.totalWords) {
            try {
                Thread.sleep(words[0].getSpeed());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (start) {
                for (int i = 0; i < noWords; i++) {
                    WordThread wt = new WordThread(i);
                    wt.start();
                }
            }
            repaint();
        }
    }

		WordPanel(WordRecord[] words, int maxY) {
        this.words = words; //will this work?
        noWords = words.length;
        done = false;
        this.maxY = maxY;
    }

    public void run() {
        //add in code to animate this
        start = true;
    }

    class WordThread extends Thread {

        int i;

        WordThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            super.run();
            Random r = new Random();
            int count = r.nextInt(30);
            words[i].drop(count);
            if (words[i].dropped()) {
                WordApp.score.missedWord();
                WordApp.updateLabels();
                words[i].setWord("");
                if (WordApp.totalWords - 1 < WordApp.score.getTotal()) {
                    words[i].resetWord();
                    interrupt();
                }
            }
        }
    }

}


