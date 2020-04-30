package kelly.simulation;

import kelly.simulation.things.RadiatingDot;
import kelly.simulation.things.Subject;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DrawingPane extends JComponent {
    private static int WIDTH = 640;
    private static int HEIGHT = 480;
    private RadiatingDot dot;
    private Locatable[] animatables;
    private int frameIndex = 0;
    private Random random = new Random();

    public DrawingPane() {
//        dot = new RadiatingDot(Color.RED, 10, 100, 10, 100);
        init(10);
    }

    @Override
    public void paint(Graphics g) {
//        Image img = dot.getFrame(frameIndex);
//        g.drawImage(img, 220 - img.getWidth(this)/2, 200 - img.getHeight(this)/2, this);
//        frameIndex++;
//
//        Graphics2D g2d = (Graphics2D) g;
//        g.setColor(Color.GREEN);
//        g2d.setStroke(new BasicStroke(10));
//        g.drawOval(15, 15, 50, 50);
//
//        g2d.setStroke(new BasicStroke(1));
//        g.setColor(Color.BLACK);
//        g.drawRect(10, 10, 60, 60);

        for(Locatable a : animatables) {
            Image img = a.getFrame(frameIndex);
            int x = (int) (a.getX() - img.getWidth(this) / 2);
            int y = (int) (a.getY() - img.getHeight(this) / 2);
            g.drawImage(img, x, y, this);
        }

        frameIndex++;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    private void init(int people) {
        animatables = new Locatable[people];
        for(int i = 0; i < people; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            animatables[i] = new Subject(x, y);
        }
    }
}
