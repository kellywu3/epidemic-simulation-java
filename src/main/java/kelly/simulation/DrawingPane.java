package kelly.simulation;

import kelly.simulation.domain.SimulationEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;

public class DrawingPane extends JComponent implements SimulationEventListener {
    private SimulationField simulationField;

    public DrawingPane(SimulationField simulationField) {
        this.simulationField = simulationField;
        simulationField.addSimulationEventListener(this);
    }

    @Override
    public void paint(Graphics g) {
        simulationField.drawSubjects(g, this);
    }

    @Override
    public Dimension getPreferredSize() {
        return simulationField.getFieldDimension();
    }

    @Override
    public void onSimulationEvent() {
        this.repaint();
    }
}
