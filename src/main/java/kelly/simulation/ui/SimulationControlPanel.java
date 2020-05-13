package kelly.simulation.ui;

import kelly.simulation.domain.SimulationField;

import javax.swing.*;
import java.awt.*;

public class SimulationControlPanel extends JPanel {
    private static final int CHAR_COUNT = 6;
    private SimulationField field;
    private LabeledInput numberOfSubjects;
    private LabeledInput massOfSubjects;
    private LabeledInput boundHeight, boundWidth;

    public SimulationControlPanel(SimulationField field) {
        this.field = field;
        numberOfSubjects = new LabeledInput("Number of Subjects:" , CHAR_COUNT, field.getSubjectCount());
        numberOfSubjects.addActionListener(e -> {
            int oldVal = field.getSubjectCount();
            try {
                field.updateSubjectCount(numberOfSubjects.getIntValue());

            } catch(NumberFormatException nfe) {
                numberOfSubjects.setValue(oldVal);
            }
        });
        add(numberOfSubjects);

        massOfSubjects = new LabeledInput("Mass of Subjects:" , CHAR_COUNT, field.getSubjectMass());
        massOfSubjects.addActionListener(e -> {
            double oldVal = field.getSubjectMass();
            try {
                field.setSubjectMass(massOfSubjects.getDoubleValue());
            } catch(NumberFormatException nfe) {
                massOfSubjects.setValue(oldVal);
            }
        });
        add(massOfSubjects);

        boundWidth = new LabeledInput("Bound Width:", 4, field.getWidth());
        boundWidth.addActionListener(e -> {
            int oldVal = field.getWidth();
            try {
                field.updateHiBound(new int[] {boundWidth.getIntValue(), boundHeight.getIntValue()});
            } catch(NumberFormatException nfe) {
                boundWidth.setValue(oldVal);
            }
        });
        add(boundWidth);

        boundHeight = new LabeledInput("Bound Height:", 4, field.getHeight());
        boundHeight.addActionListener(e -> {
            int oldVal = field.getHeight();
            try {
                field.updateHiBound(new int[] {boundWidth.getIntValue(), boundHeight.getIntValue()});
            } catch(NumberFormatException nfe) {
                boundHeight.setValue(oldVal);
            }
        });
        add(boundHeight);
    }
}
