package kelly;

import kelly.simulation.DrawingPane;
import kelly.simulation.StatusBar;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;

/**
 * Hello world!
 *
 */
public class App 
{
    private SimulationField field;

    public App() throws InterruptedException {
        field = new SimulationField();

        JFrame jf = new JFrame("Epidemic Simulation");
        jf.getContentPane().setLayout(new BorderLayout());
        DrawingPane dp = new DrawingPane(field);
        jf.add(dp, BorderLayout.CENTER);

        jf.add(new StatusBar(field), BorderLayout.SOUTH);

        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void startSimulation() {
        field.startSimulation();
    }

    public static void main( String[] args ) throws InterruptedException {
        App app = new App();
        app.startSimulation();
    }
}
