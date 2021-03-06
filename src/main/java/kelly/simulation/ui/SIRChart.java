package kelly.simulation.ui;

import kelly.simulation.domain.SimulationEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SIRChart extends JComponent implements SimulationEventListener {
    private SimulationField simulationField;
    private static final Color[] DATACOLORS= new Color[]{
        Color.GRAY
            , Color.BLUE
            , Color.RED
    };

    public SIRChart(SimulationField simulationField) {
        this.simulationField = simulationField;
        simulationField.addSimulationEventListener(this);
    }

    @Override
    public void paint(Graphics g) {
        List<int[]> data = simulationField.getTimeData();
        int dataSize = data.size();
        for(int i = 0; i < dataSize; i++) {
            int[] rec = data.get(i);
            int x = i * getWidth() / dataSize;
            int yp = 0;
            int y;
            int j;
            for (j = 0; j<rec.length - 1; j++) {
                y = rec[j] * getHeight() / simulationField.getSubjectCount() + yp;
                g.setColor(DATACOLORS[j]);
                g.drawLine(x, yp, x, y);
                yp = y;
            }
            g.setColor(DATACOLORS[j]);
            g.drawLine(x, yp, x, getHeight());
        }
    }

    @Override
    public void onSimulationEvent() {
        repaint();
    }
}
