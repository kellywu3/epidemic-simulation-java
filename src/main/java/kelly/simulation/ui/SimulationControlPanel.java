package kelly.simulation.ui;

import kelly.simulation.domain.SimulationField;

import javax.swing.*;

public class SimulationControlPanel extends JPanel {
    private static final int CHAR_COUNT = 6;
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

    public SimulationControlPanel(SimulationField field) {
        BoxLayout bl = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(bl);
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

        infectionRadius = new LabeledInput("Infection Radius:" , CHAR_COUNT, field.getInfectionRadius());
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
        maxDestinationStayTime.addActionListener(e -> {
            int oldVal = field.getMaxStayTime();
            try {
                field.setMaxStayTime(maxDestinationStayTime.getIntValue());
            } catch(NumberFormatException nfe) {
                maxDestinationStayTime.setValue(oldVal);
            }
        });
        add(maxDestinationStayTime);
    }
}
