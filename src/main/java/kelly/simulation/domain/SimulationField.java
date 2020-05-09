package kelly.simulation.domain;

import kelly.simulation.MatrixUtil;
import kelly.simulation.ui.StatusBar;
import kelly.simulation.things.Animatable;
import kelly.simulation.things.RadiatingDot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.*;

public class SimulationField implements ActionListener {
    private static final long DELAY = 10;
    private static final double SUBJECT_MASS = 10;
    private static final double SUBJECT_INITIAL_MAX_VELOCITY = 1;
    private static final int[] BOUNDS = new int[] {640, 480};
    private static final double ODDS_INITIAL_SICK = 0.02;
    private static final double MAX_RANDOM_FORCE = 2;
    public static final double FRICTION_FACTOR = 0.99;
    public static final double DESTINATION_FORCE_FACTOR = 2;

    private static final Dimension FIELD_DIMENSION = new Dimension(BOUNDS[0], BOUNDS[1]);
    private static final Random random = new Random();
    private Set<SimulationEventListener> listeners;
    private int subjectCount = 200;
    private ArrayList<int[]> timeData;
    private Subject[] subjects;
    private int timeIndex;
    private boolean paused = false;
    private boolean restarting;

    private double[] destination = new double[] {0.5 * BOUNDS[0], 0.5 * BOUNDS[1]};
//    private double[] destination = new double[] {0, 0};
    private double oddsOfDestination = 0.02;
    private boolean destinationOn = false;

    public int infectionRadius = 30;
    private double oddsOfInfection = 0.2;
    private int minInfectionTime = 7;
    private int maxInfectionTime = 21;
    private int timeScale = 24;

    private EnumMap<HealthStatus, Animatable> healthAnimation;

    public SimulationField() {
        this.subjects =  new Subject[subjectCount];
        listeners = new HashSet<>();
        timeData = new ArrayList<>();
    }

    public void init() {
        healthAnimation = new EnumMap<>(HealthStatus.class);
        healthAnimation.put(HealthStatus.SUSCEPTIBLE, new RadiatingDot(Color.BLUE, 4, 4, 1, 1));
        healthAnimation.put(HealthStatus.INFECTED, new RadiatingDot(Color.RED, 4, infectionRadius, 1, 60));
        healthAnimation.put(HealthStatus.REMOVED, new RadiatingDot(Color.GRAY, 4, 4, 1, 1));

        for(int i = 0; i < subjectCount; i++) {
            Subject s = createRandomSubject();
            s.setEventTime(i);
            subjects[i] = s;
            if (random.nextDouble() < ODDS_INITIAL_SICK) {
                s.updateHealth(HealthStatus.INFECTED, i, randomDuration());
            }
        }

        timeIndex = subjects.length + 1;
    }

    private int randomDuration() {
        return timeScale * (random.nextInt(maxInfectionTime - minInfectionTime) + minInfectionTime);
    }

    public void addSimulationEventListener(SimulationEventListener l) {
        listeners.add(l);
    }

    private void publishEvent() {
        for(SimulationEventListener l : listeners) {
            l.onSimulationEvent();
        }
    }

    public void drawSubjects(Graphics g, ImageObserver observer) {
        for(Subject s : subjects) {
            Animatable a = healthAnimation.get(s.getStatus());
            Image img = a.getFrame(timeIndex - s.getEventTime());
            double[] position = s.getPosition();
            int x = (int) (position[0] - img.getWidth(observer) / 2);
            int y = (int) (position[1] - img.getHeight(observer) / 2);
            g.drawImage(img, x, y, observer);
        }
    }

    public static void safeSleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public void startSimulation() {
        restarting = true;

        while(true) {
            if(restarting) {
                init();
                restarting = false;
                paused = false;
            }
            if(!paused) {
                doSimulation();
            }
            safeSleep(DELAY);
        }
    }

    private void doSimulation() {
        if(destinationOn) {
            assignDestination();
        }
        updateSubjects();
        simulateTransmission();
        doStatistics();
        publishEvent();
        timeIndex++;
    }

    private void simulateTransmission() {
        double odds = oddsOfInfection / timeScale;
        int infectionRadiusSquared = infectionRadius * infectionRadius;
        for(Subject s : subjects) {
            if(HealthStatus.INFECTED.equals(s.getStatus())) {
                for(Subject s2 : subjects) {
                    if(HealthStatus.SUSCEPTIBLE.equals(s2.getStatus())
                        && random.nextDouble() < odds
                        && infectionRadiusSquared > MatrixUtil.distanceSquared(s2.getPosition(), s.getPosition())
                    ) {
                        s2.updateHealth(HealthStatus.INFECTED, timeIndex, randomDuration());
                    }
                }
            }
        }
    }

    private void assignDestination() {
        if (random.nextDouble() < oddsOfDestination) {
            int s = random.nextInt(subjects.length);
            subjects[s].assignDestination(destination, false);
        }
    }

    private void updateSubjects() {
        for(Subject s : subjects) {
            s.update(randomVector(MAX_RANDOM_FORCE), BOUNDS, 1, DESTINATION_FORCE_FACTOR);
            if(s.isTimeToChange(timeIndex)) {
                s.updateHealth(HealthStatus.REMOVED, timeIndex, -1);
            }
        }
    }

    private double[] randomVector(double max) {
        double angle = random.nextDouble() * Math.PI * 2;
        double v = random.nextDouble() * max;
        return new double[] {
                v * Math.cos(angle), v * Math.sin(angle)
        };
    }

    private double[] randomPosition() {
        int len = BOUNDS.length;
        double[] position = new double[len];
        for(int i = 0; i < len; i ++) {
            position[i] = random.nextDouble() * BOUNDS[i];
        }
        return position;
    }

    private Subject createRandomSubject() {
        return new Subject(randomPosition(), randomVector(SUBJECT_INITIAL_MAX_VELOCITY), SUBJECT_MASS);
    }

    public Dimension getFieldDimension() {
        return FIELD_DIMENSION;
    }

    public int getTimeIndex() {
        return timeIndex;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println(e);
        if(StatusBar.TEXT_PAUSE.equals(e.getActionCommand())) {
            paused = !paused;
        } else if(StatusBar.TEXT_RESTART.equals(e.getActionCommand())) {
            restarting = true;
        } else if (StatusBar.TEXT_DESTINATION_ON.equals(e.getActionCommand())) {
            destinationOn = !destinationOn;
        }
    }

    public void doStatistics() {
        int[] data = new int[HealthStatus.values().length];
        for(Subject s : subjects) {
            data[s.getStatus().ordinal()]++;
        }
        timeData.add(data);
    }

    public ArrayList<int[]> getTimeData() {
        return timeData;
    }

    public int getSubjectCount() {
        return subjectCount;
    }
}
