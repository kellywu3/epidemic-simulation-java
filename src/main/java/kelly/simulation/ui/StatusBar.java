package kelly.simulation.ui;

import kelly.simulation.domain.SimulationEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class StatusBar extends JPanel implements SimulationEventListener {
    public static final String TEXT_DESTINATION_ON = "Destination";
    public static final String TEXT_PAUSE = "Pause";
    public static final String TEXT_RESTART = "Restart";

    private SimulationField field;
    private JButton enableDestination;
    private JButton pauseButton;
    private JButton restartButton;
    private JLabel timeValue;

    public StatusBar(SimulationField field) {
        this.field = field;
        field.addSimulationEventListener(this);

        setLayout(new FlowLayout());

        enableDestination = new JButton(TEXT_DESTINATION_ON);
        pauseButton = new JButton(TEXT_PAUSE);
        restartButton = new JButton(TEXT_RESTART);
        timeValue = new JLabel();

        add(enableDestination);
        add(pauseButton);
        add(restartButton);

        add(new JLabel("Time:"));
        add(timeValue);

        enableDestination.addActionListener(field);
        pauseButton.addActionListener(field);
        restartButton.addActionListener(field);
    }

    @Override
    public void onSimulationEvent() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        timeValue.setText(nf.format(field.getTimeIndex()));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
    }
}
