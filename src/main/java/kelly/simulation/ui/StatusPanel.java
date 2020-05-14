package kelly.simulation.ui;

import kelly.simulation.domain.SimulationEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatusPanel extends JPanel implements SimulationEventListener, ActionListener {
    private static final int WIDTH = 64;
    public static final String TEXT_DESTINATION_ON = "Turn Destination On";
    public static final String TEXT_DESTINATION_OFF = "Turn Destination Off";
    public static final String TEXT_PAUSE = "Pause";
    public static final String TEXT_PLAY = "Play";
    public static final String TEXT_RESTART = "Restart";
    public static final String TEXT_RESET_VALUES = "Reset Values";
    private JLabel timeLabel = new JLabel("Time:");
    private JLabel eradicatedTimeLabel = new JLabel();
    private SimulationField field;
    private JButton enableDestination;
    private JButton pauseButton;
    private JButton restartButton;
    private JButton resetValuesButton;
    private NumberDisplay timeValue = new NumberDisplay(WIDTH);
    private NumberDisplay eradicatedTime = new NumberDisplay(WIDTH);

    public StatusPanel(SimulationField field) {
        this.field = field;
        field.addSimulationEventListener(this);

        setLayout(new FlowLayout());

        enableDestination = new JButton(TEXT_DESTINATION_ON);
        pauseButton = new JButton(TEXT_PAUSE);
        restartButton = new JButton(TEXT_RESTART);
        resetValuesButton = new JButton(TEXT_RESET_VALUES);
        timeValue = new NumberDisplay(WIDTH);

        add(enableDestination);
        add(pauseButton);
        add(restartButton);
        add(resetValuesButton);

        add(timeLabel);
        add(timeValue);
        add(eradicatedTimeLabel);
        add(eradicatedTime);
        timeValue.setMinimumSize(new Dimension(500, 0));

        enableDestination.addActionListener(this);
        pauseButton.addActionListener(this);
        restartButton.addActionListener(this);
        resetValuesButton.addActionListener(this);
    }

    @Override
    public void onSimulationEvent() {
        timeValue.setValue(field.getTimeIndex());
        if(field.getEradicatedTime() >= 0) {
            eradicatedTimeLabel.setText("Eradicated At:");
            eradicatedTime.setValue(field.getEradicatedTime());
        } else {
            eradicatedTimeLabel.setText(null);
            eradicatedTime.setText(null);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println(e);
        if(TEXT_PAUSE.equals(e.getActionCommand())) {
            field.setPaused(true);
            pauseButton.setText(TEXT_PLAY);
        } else if (TEXT_PLAY.equals(e.getActionCommand())) {
            field.setPaused(false);
            pauseButton.setText(TEXT_PAUSE);
        } else if(TEXT_RESTART.equals(e.getActionCommand())) {
            field.setRestarting(true);
        } else if (TEXT_DESTINATION_ON.equals(e.getActionCommand())) {
            field.setDestinationOn(true);
            enableDestination.setText(TEXT_DESTINATION_OFF);
        } else if (TEXT_DESTINATION_OFF.equals(e.getActionCommand())) {
            field.setDestinationOn(false);
            enableDestination.setText(TEXT_DESTINATION_ON);
        } else if(TEXT_RESET_VALUES.equals(e.getActionCommand())) {
            field.initValues();
        }
    }
}
