package kelly.simulation.ui;

import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationControlPanel extends JPanel implements ActionListener {
    private static final int CHAR_COUNT = 6;
    private SimulationField field;
    private LabeledInput numberOfSubjects;
    private LabeledInput massOfSubjects;

    public SimulationControlPanel(SimulationField field) {
        this.field = field;
        numberOfSubjects = new LabeledInput("Number of Subjects:" , CHAR_COUNT, field.getSubjectCount());
        numberOfSubjects.addActionListener(this);
        add(numberOfSubjects);

        massOfSubjects = new LabeledInput("Mass of Subjects:" , CHAR_COUNT, field.getSubjectMass());
        massOfSubjects.addActionListener(this);
        add(massOfSubjects);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (numberOfSubjects.isEventFromThis(e)) {
            field.updateSubjectCount(numberOfSubjects.getIntValue());
        } else if(massOfSubjects.isEventFromThis(e)) {
            field.setSubjectMass(massOfSubjects.getDoubleValue());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(field.getWidth() / 2, field.getHeight() / 2);
    }
}
