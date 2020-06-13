package kelly.simulation.ui;

import kelly.simulation.domain.SimulationEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SIRChart extends JComponent implements SimulationEventListener {
    private SimulationField field;
    private static final Color[] DATACOLORS= new Color[]{
        Color.GRAY
            , Color.BLUE
            , Color.RED
    };

    public SIRChart(SimulationField simulationField) {
        this.field = simulationField;
        simulationField.addSimulationEventListener(this);
    }

    @Override
    public void paint(Graphics g) {
        List<int[]> data = field.getTimeData();
        int dataSize = data.size();
        for(int i = 0; i < dataSize; i++) {
            int[] rec = data.get(i);
            int x = i * getWidth() / dataSize;
            int yp = 0;
            int y;
            int j;
            for (j = 0; j<rec.length - 1; j++) {
                y = rec[j] * getHeight() / field.getSubjectCount() + yp;
                g.setColor(DATACOLORS[j]);
                g.drawLine(x, yp, x, y);
                yp = y;
            }
            g.setColor(DATACOLORS[j]);
            g.drawLine(x, yp, x, getHeight());
        }

        g.setColor(Color.YELLOW);
        int max = field.getMaxInfected();
        int yBar = getHeight() - (max * getHeight() / field.getSubjectCount());
        g.drawLine(getWidth() - 8, yBar, getWidth(), yBar);
        g.drawString(Integer.toString(max), getWidth() - 40, yBar - g.getFont().getSize());
    }

    @Override
    public void onSimulationEvent() {
        repaint();
    }
}
