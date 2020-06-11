package kelly.simulation.ui;

import kelly.simulation.domain.FieldEventListener;
import kelly.simulation.domain.SimulationField;

import javax.swing.*;

public class SimulationControlPanel extends JPanel implements FieldEventListener {
    private static final int CHAR_COUNT = 6;
    private SimulationField field;
    private LabeledInput numberOfSubjects;
    private LabeledInput massOfSubjects;
    private LabeledInput numberInitialSick;
    private LabeledInput oddsOfDestination;
    private LabeledInput oddsOfInfection;
    private LabeledInput communityRows, communityColumns;
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

        numberInitialSick = new LabeledInput("Number Initially Infected:" , CHAR_COUNT, field.getNumberInitialSick());
        numberInitialSick.setAlignmentX(SwingConstants.RIGHT);
        numberInitialSick.addActionListener(e -> {
            int oldVal = field.getNumberInitialSick();
            try {
                field.setNumberInitialSick(numberInitialSick.getIntValue());
            } catch(NumberFormatException nfe) {
                numberInitialSick.setValue(oldVal);
            }
        });
        add(numberInitialSick);

        communityRows = new LabeledInput("Number of Community Rows:" , CHAR_COUNT, field.getCommunityRows());
        communityRows.setAlignmentX(SwingConstants.RIGHT);
        communityRows.addActionListener(e -> {
            int oldVal = field.getCommunityRows();
            try {
                field.changeCommunityRows(communityRows.getIntValue());
                field.setCommunityOn(true);
            } catch(NumberFormatException nfe) {
                communityRows.setValue(oldVal);
            }
        });
        add(communityRows);

        communityColumns = new LabeledInput("Number of Community Columns:" , CHAR_COUNT, field.getCommunityColumns());
        communityColumns.setAlignmentX(SwingConstants.RIGHT);
        communityColumns.addActionListener(e -> {
            int oldVal = field.getCommunityColumns();
            try {
                field.changeCommunityColumns(communityColumns.getIntValue());
                field.setCommunityOn(true);
            } catch(NumberFormatException nfe) {
                communityColumns.setValue(oldVal);
            }
        });
        add(communityColumns);

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
                int time = minInfectionTime.getIntValue();
                if(time > field.getMaxInfectionTime()) {
                    time = field.getMaxInfectionTime() - 1;
                    minInfectionTime.setValue(time);
                }
                field.setMinInfectionTime(time);
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
                int time = maxInfectionTime.getIntValue();
                if(time < field.getMinInfectionTime()) {
                    time = field.getMinInfectionTime() + 1;
                    maxInfectionTime.setValue(time);
                }
                field.setMaxInfectionTime(time);
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
                int time = minDestinationStayTime.getIntValue();
                if(time > field.getMaxStayTime()) {
                    time = field.getMaxStayTime() - 1;
                    minDestinationStayTime.setValue(time);
                }
                field.setMinStayTime(time);
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
                int time = maxDestinationStayTime.getIntValue();
                if(time < field.getMinStayTime()) {
                    time = field.getMinStayTime() + 1;
                    maxDestinationStayTime.setValue(time);
                }
                field.setMaxStayTime(time);
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
        numberInitialSick.setValue(field.getNumberInitialSick());
        oddsOfDestination.setValue(field.getOddsOfDestination());
        oddsOfInfection.setValue(field.getOddsOfInfection());
        infectionRadius.setValue(field.getInfectionRadius());
        minInfectionTime.setValue(field.getMinInfectionTime());
        maxInfectionTime.setValue(field.getMaxInfectionTime());
        minDestinationStayTime.setValue(field.getMinStayTime());
        maxDestinationStayTime.setValue(field.getMaxStayTime());
        frictionFactor.setValue(field.getFrictionFactor());
    }
}
