package kelly.simulation.ui;

import kelly.simulation.domain.SimulationEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SimulationDisplay extends JComponent implements SimulationEventListener {
    private SimulationField field;

    public SimulationDisplay(SimulationField field) {
        JComponent self = this;
        this.field = field;
        field.addSimulationEventListener(this);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                field.updateHiBound(new int[] {
                    self.getWidth(), self.getHeight()
                });
                field.updateDestination(new double[] {
                    self.getWidth() / 2, self.getHeight() / 2
                });
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        field.drawSubjects(g, this);
    }

    @Override
    public void onSimulationEvent() {
        this.repaint();
    }
}
