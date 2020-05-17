package kelly.simulation.ui;

import kelly.simulation.domain.HealthStatus;
import kelly.simulation.domain.SimulationEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;

public class SIRPanel extends JPanel implements SimulationEventListener {
    private int ROWS = 4;
    private int COLUMNS = 5;
    private int HGAP = 0;
    private int VGAP = 0;
    private static final int WIDTH = 32;
    private SimulationField field;
    private JLabel eradicatedTimeLabel = new JLabel();
    private NumberDisplay timeValue = new NumberDisplay(WIDTH);
    private NumberDisplay susceptibleCount = new NumberDisplay(WIDTH);
    private NumberDisplay infectedCount = new NumberDisplay(WIDTH);
    private NumberDisplay removedCount = new NumberDisplay(WIDTH);
    private NumberDisplay highestInfectedCount = new NumberDisplay(WIDTH);
    private NumberDisplay eradicatedTime = new NumberDisplay(WIDTH);

    public SIRPanel(SimulationField field) {
        this.field = field;
        field.addSimulationEventListener(this);
        setLayout(new GridLayout(ROWS, COLUMNS, HGAP, VGAP));

        add(new Label("Susceptible:"));
        add(new Label("Infected:"));
        add(new JLabel("Removed:"));
        add(susceptibleCount);
        add(infectedCount);
        add(removedCount);
        add(new JLabel("Time:"));
        add(new Label("Max Infected:"));
        add(eradicatedTimeLabel);
        add(timeValue);
        add(highestInfectedCount);
        add(eradicatedTime);
    }

    @Override
    public void onSimulationEvent() {
        timeValue.setValue(field.getTimeIndex());

        if(field.getEradicatedTime() >= 0) {
            eradicatedTimeLabel.setText("Eradicated At:");
            eradicatedTime.setValue(field.getEradicatedTime());
        } else {
            int idx = field.getTimeData().size() - 1;
            int[] sirCount = field.getTimeData().get(idx);
            susceptibleCount.setValue(sirCount[HealthStatus.SUSCEPTIBLE.ordinal()]);
            infectedCount.setValue(sirCount[HealthStatus.INFECTED.ordinal()]);
            highestInfectedCount.setText(Integer.toString(field.getMaxInfected()));
            removedCount.setValue(sirCount[HealthStatus.REMOVED.ordinal()]);
            eradicatedTime.setText(null);
            eradicatedTimeLabel.setText(null);
        }
    }
}
