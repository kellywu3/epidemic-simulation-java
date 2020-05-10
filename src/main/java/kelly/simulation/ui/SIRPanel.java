package kelly.simulation.ui;

import kelly.simulation.domain.HealthStatus;
import kelly.simulation.domain.SimulationEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class SIRPanel extends JPanel implements SimulationEventListener {
    private SimulationField field;
    private JLabel susceptibleCount = new JLabel();
    private JLabel infectedCount = new JLabel();
    private JLabel removedCount = new JLabel();
    private JLabel highestInfectedCount = new JLabel();

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

    private static String extract(int[] counts, HealthStatus status) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        return nf.format(counts[status.ordinal()]);
    }

    @Override
    public void onSimulationEvent() {
        int idx = field.getTimeData().size() - 1;
        int[] sirCount = field.getTimeData().get(idx);
        susceptibleCount.setText(extract(sirCount, HealthStatus.SUSCEPTIBLE));
        infectedCount.setText(extract(sirCount, HealthStatus.INFECTED));
        highestInfectedCount.setText(Integer.toString(field.getMaxInfected()));
        removedCount.setText(extract(sirCount, HealthStatus.REMOVED));
    }
}
