package kelly.simulation.ui;

import kelly.simulation.domain.SimulationEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class StatusPanel extends JPanel implements SimulationEventListener, ActionListener {
    public static final String TEXT_DESTINATION_ON = "Turn Destination On";
    public static final String TEXT_DESTINATION_OFF = "Turn Destination Off";
    public static final String TEXT_PAUSE = "Pause";
    public static final String TEXT_PLAY = "Play";
    public static final String TEXT_RESTART = "Restart";
    private SimulationField field;
    private JButton enableDestination;
    private JButton pauseButton;
    private JButton restartButton;
    private JLabel timeValue;

    public StatusPanel(SimulationField field) {
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

        JLabel timeLabel = new JLabel("Time:");
        add(timeLabel);
        add(timeValue);
        timeValue.setMinimumSize(new Dimension(500, 0));

        enableDestination.addActionListener(this);
        pauseButton.addActionListener(this);
        restartButton.addActionListener(this);
    }

    @Override
    public void onSimulationEvent() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        timeValue.setText(nf.format(field.getTimeIndex()) +
            (field.getEradicatedTime() < 0 ? "" : " Eradicated at: " + nf.format(field.getEradicatedTime()))
        );
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
        }
    }
}
