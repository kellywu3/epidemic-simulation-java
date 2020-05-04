package kelly;

import kelly.simulation.DrawingPane;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;

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
        DrawingPane dp = new DrawingPane(field);
        jf.add(dp);
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
