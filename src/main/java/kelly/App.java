package kelly;

import kelly.simulation.ui.SIRChart;
import kelly.simulation.ui.SimulationDisplay;
import kelly.simulation.ui.StatusBar;
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
        SimulationDisplay sp = new SimulationDisplay(field);
        jf.add(sp, BorderLayout.CENTER);

        jf.add(new StatusBar(field), BorderLayout.SOUTH);

        SIRChart sir = new SIRChart(field);
        jf.add(sir, BorderLayout.WEST);

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
