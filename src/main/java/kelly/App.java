package kelly;

import kelly.simulation.ui.*;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;

public class App 
{
    private SimulationField field;

    public App() {
        field = new SimulationField();

        JFrame jf = new JFrame("Epidemic Simulation");
        SimulationDisplay sp = new SimulationDisplay(field);
        sp.setPreferredSize(new Dimension(640, 480));
        jf.add(sp, BorderLayout.CENTER);
        jf.add(new StatusPanel(field), BorderLayout.SOUTH);
        SIRChart sirChart = new SIRChart(field);
        sirChart.setPreferredSize(new Dimension(320, 240));
        SIRPanel sirPanel = new SIRPanel(field);
        SimulationControlPanel scp = new SimulationControlPanel(field);
        JScrollPane jsp  = new JScrollPane(scp);
        jsp.setPreferredSize(new Dimension(320, 240));
        JScrollBar jsb = new JScrollBar(JScrollBar.VERTICAL);
        jsp.add(jsb);
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        informationPanel.add(sirPanel, BorderLayout.NORTH);
        informationPanel.add(sirChart, BorderLayout.CENTER);
        informationPanel.add(jsp, BorderLayout.SOUTH);
        jf.add(informationPanel, BorderLayout.WEST);
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void startSimulation() {
        field.startSimulation();
    }

    public static void main( String[] args ) {
        App app = new App();
        app.startSimulation();
    }
}
