package kelly.simulation.ui;

import kelly.simulation.domain.FieldEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;

public class SimulationControlPanel extends JPanel implements FieldEventListener {
    private static final int CHAR_COUNT = 6;
    private SimulationField field;
    private LabeledInput numberOfSubjects;
    private LabeledInput massOfSubjects;
    private LabeledInput boundHeight, boundWidth;
    private LabeledInput oddsOfInitialSick;
    private LabeledInput oddsOfDestination;
    private LabeledInput oddsOfInfection;
    private LabeledInput destinationX, destinationY;
    private LabeledInput infectionRadius;
    private LabeledInput minInfectionTime, maxInfectionTime;
    private LabeledInput minDestinationStayTime, maxDestinationStayTime;
    private LabeledInput frictionFactor;

    public SimulationControlPanel(SimulationField field) {
        this.field = field;
        field.addFieldEventListener(this);

        BoxLayout bl = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(bl);
        numberOfSubjects = new LabeledInput("Number of Subjects:" , CHAR_COUNT, field.getSubjectCount());
        numberOfSubjects.setAlignmentX(SwingConstants.RIGHT);
        numberOfSubjects.addActionListener(e -> {
            int oldVal = field.getSubjectCount();
            try {
                field.updateSubjectCount(numberOfSubjects.getIntValue());

            } catch(NumberFormatException nfe) {
                numberOfSubjects.setValue(oldVal);
            }
        });
        add(numberOfSubjects);

        infectionRadius = new LabeledInput("Infection Radius:" , CHAR_COUNT, field.getInfectionRadius());
        infectionRadius.setAlignmentX(SwingConstants.RIGHT);
        infectionRadius.addActionListener(e -> {
            int oldVal = field.getInfectionRadius();
            try {
                field.updateInfectionRadius(infectionRadius.getIntValue());

            } catch(NumberFormatException nfe) {
                infectionRadius.setValue(oldVal);
            }
        });
        add(infectionRadius);

        massOfSubjects = new LabeledInput("Mass of Subjects:" , CHAR_COUNT, field.getSubjectMass());
        massOfSubjects.setAlignmentX(SwingConstants.RIGHT);
        massOfSubjects.addActionListener(e -> {
            double oldVal = field.getSubjectMass();
            try {
                field.setSubjectMass(massOfSubjects.getDoubleValue());
            } catch(NumberFormatException nfe) {
                massOfSubjects.setValue(oldVal);
            }
        });
        add(massOfSubjects);

        boundWidth = new LabeledInput("Bound Width:", CHAR_COUNT, field.getWidth());
        boundWidth.setAlignmentX(SwingConstants.RIGHT);
        boundWidth.addActionListener(e -> {
            int oldVal = field.getWidth();
            try {
                field.updateHiBound(new int[] {boundWidth.getIntValue(), boundHeight.getIntValue()});
            } catch(NumberFormatException nfe) {
                boundWidth.setValue(oldVal);
            }
        });
        add(boundWidth);

        boundHeight = new LabeledInput("Bound Height:", CHAR_COUNT, field.getHeight());
        boundHeight.setAlignmentX(SwingConstants.RIGHT);
        boundHeight.addActionListener(e -> {
            int oldVal = field.getHeight();
            try {
                field.updateHiBound(new int[] {boundWidth.getIntValue(), boundHeight.getIntValue()});
            } catch(NumberFormatException nfe) {
                boundHeight.setValue(oldVal);
            }
        });
        add(boundHeight);

        oddsOfInitialSick = new LabeledInput("Odds of initial infection:" , CHAR_COUNT, field.getOddsInitialSick());
        oddsOfInitialSick.setAlignmentX(SwingConstants.RIGHT);
        oddsOfInitialSick.addActionListener(e -> {
            double oldVal = field.getOddsInitialSick();
            try {
                field.updateOddsInitialSick(oddsOfInitialSick.getDoubleValue());
            } catch(NumberFormatException nfe) {
                oddsOfInitialSick.setValue(oldVal);
            }
        });
        add(oddsOfInitialSick);

        oddsOfDestination = new LabeledInput("Odds of Traveling to Destination:" , CHAR_COUNT, field.getOddsOfDestination());
        oddsOfDestination.setAlignmentX(SwingConstants.RIGHT);
        oddsOfDestination.addActionListener(e -> {
            double oldVal = field.getOddsOfDestination();
            try {
                field.setOddsOfDestination(oddsOfDestination.getDoubleValue());
            } catch(NumberFormatException nfe) {
                oddsOfDestination.setValue(oldVal);
            }
        });
        add(oddsOfDestination);

        destinationX = new LabeledInput("X-Coordinate Destination:", CHAR_COUNT, field.getDestinationX());
        destinationX.setAlignmentX(SwingConstants.RIGHT);
        destinationX.addActionListener(e -> {
            double oldVal = field.getDestinationX();
            try {
                field.setDestination(new double[] {destinationX.getDoubleValue(), destinationY.getDoubleValue()});
            } catch(NumberFormatException nfe) {
                destinationX.setValue(oldVal);
            }
        });
        add(destinationX);

        destinationY = new LabeledInput("Y-Coordinate Destination:", CHAR_COUNT, field.getDestinationY());
        destinationY.setAlignmentX(SwingConstants.RIGHT);
        destinationY.addActionListener(e -> {
            double oldVal = field.getDestinationY();
            try {
                field.setDestination(new double[] {destinationX.getDoubleValue(), destinationY.getDoubleValue()});
            } catch(NumberFormatException nfe) {
                destinationY.setValue(oldVal);
            }
        });
        add(destinationY);

        oddsOfInfection = new LabeledInput("Odds of Infection:" , CHAR_COUNT, field.getOddsOfInfection());
        oddsOfInfection.setAlignmentX(SwingConstants.RIGHT);
        oddsOfInfection.addActionListener(e -> {
            double oldVal = field.getOddsOfInfection();
            try {
                field.setOddsOfInfection(oddsOfInfection.getDoubleValue());
            } catch(NumberFormatException nfe) {
                oddsOfInfection.setValue(oldVal);
            }
        });
        add(oddsOfInfection);

        minInfectionTime = new LabeledInput("Minimum Infection Time:" , CHAR_COUNT, field.getMinInfectionTime());
        minInfectionTime.setAlignmentX(SwingConstants.RIGHT);
        minInfectionTime.addActionListener(e -> {
            int oldVal = field.getMinInfectionTime();
            try {
                field.setMinInfectionTime(minInfectionTime.getIntValue());
            } catch(NumberFormatException nfe) {
                minInfectionTime.setValue(oldVal);
            }
        });
        add(minInfectionTime);

        maxInfectionTime = new LabeledInput("Maximum Infection Time:" , CHAR_COUNT, field.getMaxInfectionTime());
        maxInfectionTime.setAlignmentX(SwingConstants.RIGHT);
        maxInfectionTime.addActionListener(e -> {
            int oldVal = field.getMaxInfectionTime();
            try {
                field.setMaxInfectionTime(maxInfectionTime.getIntValue());
            } catch(NumberFormatException nfe) {
                maxInfectionTime.setValue(oldVal);
            }
        });
        add(maxInfectionTime);

        minDestinationStayTime = new LabeledInput("Minimum Destination Stay Time:" , CHAR_COUNT, field.getMinStayTime());
        minDestinationStayTime.setAlignmentX(SwingConstants.RIGHT);
        minDestinationStayTime.addActionListener(e -> {
            int oldVal = field.getMinStayTime();
            try {
                field.setMinStayTime(minDestinationStayTime.getIntValue());
            } catch(NumberFormatException nfe) {
                minDestinationStayTime.setValue(oldVal);
            }
        });
        add(minDestinationStayTime);

        maxDestinationStayTime = new LabeledInput("Maximum Destination Stay Time:" , CHAR_COUNT, field.getMaxStayTime());
        maxDestinationStayTime.setAlignmentX(SwingConstants.RIGHT);
        maxDestinationStayTime.addActionListener(e -> {
            int oldVal = field.getMaxStayTime();
            try {
                field.setMaxStayTime(maxDestinationStayTime.getIntValue());
            } catch(NumberFormatException nfe) {
                maxDestinationStayTime.setValue(oldVal);
            }
        });
        add(maxDestinationStayTime);

        frictionFactor = new LabeledInput("Friction Factor:" , CHAR_COUNT, field.getFrictionFactor());
        frictionFactor.setAlignmentX(SwingConstants.RIGHT);
        frictionFactor.addActionListener(e -> {
            double oldVal = field.getFrictionFactor();
            try {
                field.setFrictionFactor(frictionFactor.getDoubleValue());
            } catch(NumberFormatException nfe) {
                frictionFactor.setValue(oldVal);
            }
        });
        add(frictionFactor);
    }

    @Override
    public void onFieldEvent() {
        numberOfSubjects.setValue(field.getSubjectCount());
        massOfSubjects.setValue(field.getSubjectMass());
        boundHeight.setValue(field.getHeight());
        boundWidth.setValue(field.getWidth());
        oddsOfInitialSick.setValue(field.getOddsInitialSick());
        oddsOfDestination.setValue(field.getOddsOfDestination());
        oddsOfInfection.setValue(field.getOddsOfInfection());
        destinationX.setValue(field.getDestinationX());
        destinationY.setValue(field.getDestinationY());
        infectionRadius.setValue(field.getInfectionRadius());
        minInfectionTime.setValue(field.getMinInfectionTime());
        maxInfectionTime.setValue(field.getMinInfectionTime());
        minDestinationStayTime.setValue(field.getMinStayTime());
        maxDestinationStayTime.setValue(field.getMaxStayTime());
        frictionFactor.setValue(field.getFrictionFactor());
    }
}
