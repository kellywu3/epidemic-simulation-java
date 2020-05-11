package kelly.simulation.ui;

import kelly.simulation.domain.HealthStatus;
import kelly.simulation.domain.SimulationEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;

public class SIRPanel extends JPanel implements SimulationEventListener {
    private static final int WIDTH = 32;
    private SimulationField field;
    private NumberDisplay susceptibleCount = new NumberDisplay(WIDTH);
    private NumberDisplay infectedCount = new NumberDisplay(WIDTH);
    private NumberDisplay removedCount = new NumberDisplay(WIDTH);
    private NumberDisplay highestInfectedCount = new NumberDisplay(WIDTH);

    public SIRPanel(SimulationField field) {
        this.field = field;
        field.addSimulationEventListener(this);

        setLayout(new FlowLayout());

        add(new JLabel("Susceptible:"));
        add(susceptibleCount);
        add(new JLabel("Infected:"));
        add(infectedCount);
        add(new JLabel("Max:"));
        add(highestInfectedCount);
        add(new JLabel("Removed:"));
        add(removedCount);
    }

    @Override
    public void onSimulationEvent() {
        int idx = field.getTimeData().size() - 1;
        int[] sirCount = field.getTimeData().get(idx);
        susceptibleCount.setValue(sirCount[HealthStatus.SUSCEPTIBLE.ordinal()]);
        infectedCount.setValue(sirCount[HealthStatus.INFECTED.ordinal()]);
        highestInfectedCount.setText(Integer.toString(field.getMaxInfected()));
        removedCount.setValue(sirCount[HealthStatus.REMOVED.ordinal()]);
    }
}
