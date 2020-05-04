package kelly.simulation;

import kelly.simulation.domain.Position;
import kelly.simulation.things.RadiatingDot;
import kelly.simulation.things.Subject;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DrawingPane extends JComponent {
    private static int WIDTH = 640;
    private static int HEIGHT = 480;
    private RadiatingDot dot;
    private Simulatable[] animatables;
    private int frameIndex = 0;
    private Random random = new Random();

    public DrawingPane() {
        init(30);
    }

    @Override
    public void paint(Graphics g) {
        for(Simulatable a : animatables) {
            a.simulate();
            Image img = a.getFrame(frameIndex);
            Position p = a.getPosition();
            int x = (int) (p.getX() - img.getWidth(this) / 2);
            int y = (int) (p.getY() - img.getHeight(this) / 2);
            g.drawImage(img, x, y, this);
        }
        frameIndex++;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    private void init(int people) {
        animatables = new Simulatable[people];
        for(int i = 0; i < people; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            animatables[i] = new Subject(new Position(x, y));
        }
    }
}
