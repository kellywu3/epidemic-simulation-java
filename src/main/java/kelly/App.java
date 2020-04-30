package kelly;

import kelly.simulation.DrawingPane;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public App() throws InterruptedException {
        JFrame jf = new JFrame("Epidemic Simulation");
        DrawingPane dp = new DrawingPane();
        jf.add(dp);
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        while(true) {
            dp.repaint();
            Thread.sleep(20);
        }
    }

    public static void main( String[] args ) throws InterruptedException {
        App app = new App();
    }
}
