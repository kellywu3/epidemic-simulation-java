package kelly.simulation.ui;

import kelly.simulation.domain.FieldEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel implements FieldEventListener {
    public static final String TEXT_DESTINATION = "Destination";
    public static final String TEXT_COMMUNITIES = "Communities";
    public static final String TEXT_QUARANTINE = "Quarantine";
    public static final String TEXT_SOCIAL_DISTANCING = "Social Distancing";
    public static final String TEXT_DESCRIBE = "Describe Subjects";
    public static final String TEXT_PAUSE = "Pause";
    public static final String TEXT_PLAY = "Play";
    public static final String TEXT_RESTART = "Restart";
    public static final String TEXT_RESET_VALUES = "Reset Values";
    private SimulationField field;
    private JCheckBox enableDestination;
    private JCheckBox enableCommunities;
    private JCheckBox enableQuarantine;
    private JCheckBox enableSocialDistancing;
    private JCheckBox enableDescription;
    private JButton pauseButton;
    private JButton restartButton;
    private JButton resetValuesButton;

    public StatusPanel(SimulationField field) {
        this.field = field;
        field.addFieldEventListener(this);
        setLayout(new FlowLayout());

        enableDestination = new JCheckBox(TEXT_DESTINATION);
        enableDestination.addActionListener(e -> {
            field.setDestinationOn(!field.destinationOn);
        });
        add(enableDestination);
        enableDestination.setSelected(field.destinationOn);

        enableCommunities = new JCheckBox(TEXT_COMMUNITIES);
        enableCommunities.addActionListener(e -> {
                field.setCommunityOn(!field.communityOn);
        });
        add(enableCommunities);
        enableCommunities.setSelected(field.communityOn);

        enableQuarantine = new JCheckBox(TEXT_QUARANTINE);
        enableQuarantine.addActionListener(e -> {
            field.setQuarantineOn(!field.quarantineOn);
        });
        add(enableQuarantine);
        enableQuarantine.setSelected(field.quarantineOn);


        enableSocialDistancing = new JCheckBox(TEXT_SOCIAL_DISTANCING);
        enableSocialDistancing.addActionListener(e -> {
            field.setSocialDistanceOn(!field.socialDistanceOn);
        });
        add(enableSocialDistancing);
        enableSocialDistancing.setSelected(field.socialDistanceOn);

        enableDescription = new JCheckBox(TEXT_DESCRIBE);
        enableDescription.addActionListener(e -> {
            field.setDescribe(!field.describe);
        });
        add(enableDescription);
        enableDescription.setSelected(field.destinationOn);

        pauseButton = new JButton(TEXT_PAUSE);
        pauseButton.addActionListener(e -> {
            if(TEXT_PAUSE.equals(e.getActionCommand())) {
                field.setPaused(true);
                pauseButton.setText(TEXT_PLAY);
            } else if (TEXT_PLAY.equals(e.getActionCommand())) {
                field.setPaused(false);
                pauseButton.setText(TEXT_PAUSE);
            }
        });
        add(pauseButton);
        pauseButton.setSelected(field.paused);

        restartButton = new JButton(TEXT_RESTART);
        restartButton.addActionListener(e -> {
            field.setRestarting(true);
        });
        add(restartButton);

        resetValuesButton = new JButton(TEXT_RESET_VALUES);
        resetValuesButton.addActionListener(e -> {
            field.assignDefaultValues();
            field.setRestarting(true);
        });
        add(resetValuesButton);
    }

    @Override
    public void onFieldEvent() {
        enableDestination.setSelected(field.destinationOn);
        enableQuarantine.setSelected(field.quarantineOn);
        enableCommunities.setSelected(field.communityOn);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
    }
}
