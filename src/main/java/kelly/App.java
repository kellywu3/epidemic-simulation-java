package kelly;

import kelly.simulation.ui.SIRChart;
import kelly.simulation.ui.SIRPanel;
import kelly.simulation.ui.SimulationDisplay;
import kelly.simulation.ui.StatusPanel;
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

    public App() {
        field = new SimulationField();

        JFrame jf = new JFrame("Epidemic Simulation");
        jf.getContentPane().setLayout(new BorderLayout());
        SimulationDisplay sp = new SimulationDisplay(field);
        jf.add(sp, BorderLayout.CENTER);

        jf.add(new StatusPanel(field), BorderLayout.SOUTH);

        SIRChart sirChart = new SIRChart(field);
        SIRPanel sirPanel = new SIRPanel(field);
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        informationPanel.add(sirPanel, BorderLayout.NORTH);
        informationPanel.add(sirChart, BorderLayout.CENTER);
        jf.add(informationPanel, BorderLayout.WEST);

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
