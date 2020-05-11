package kelly;

import kelly.simulation.ui.*;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import javax.swing.border.Border;
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
        SimulationDisplay sp = new SimulationDisplay(field);
        jf.add(sp, BorderLayout.CENTER);

        jf.add(new StatusPanel(field), BorderLayout.SOUTH);

        SIRChart sirChart = new SIRChart(field);
        SIRPanel sirPanel = new SIRPanel(field);
        SimulationControlPanel scp = new SimulationControlPanel(field);
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        informationPanel.add(sirPanel, BorderLayout.NORTH);
        informationPanel.add(sirChart, BorderLayout.CENTER);
        informationPanel.add(scp, BorderLayout.SOUTH);
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
