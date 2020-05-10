package kelly.simulation.domain;

import kelly.simulation.MatrixUtil;
import kelly.simulation.ui.StatusPanel;
import kelly.simulation.things.Animatable;
import kelly.simulation.things.RadiatingDot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.*;

public class SimulationField {
    private static final long DELAY = 10;
    private static final double SUBJECT_MASS = 15;
    private static final double SUBJECT_INITIAL_MAX_VELOCITY = 1;
    private static final int[] BOUNDS = new int[] {640, 480};
    private static final double ODDS_INITIAL_SICK = 0.02;
    private static final double MAX_RANDOM_FORCE = 2;
    public static final double FRICTION_FACTOR = 0.99;
    public static final double DESTINATION_FORCE_FACTOR = 2;
    public static final int MIN_STAY_TIME = 36;
    public static final int MAX_STAY_TIME = 48;

    private static final Dimension FIELD_DIMENSION = new Dimension(BOUNDS[0], BOUNDS[1]);
    private static final Random random = new Random();
    private Set<SimulationEventListener> listeners;
    private int subjectCount = 200;
    private ArrayList<int[]> timeData;
    private Subject[] subjects;
    private int timeIndex;
    private boolean paused = false;
    private boolean restarting;
    private int eradicatedTime;

    private double[] destination = new double[] {0.5 * BOUNDS[0], 0.5 * BOUNDS[1]};
//    private double[] destination = new double[] {0, 0};
    private double oddsOfDestination = 0.02;
    private boolean destinationOn = false;

    public int infectionRadius = 30;
    private double oddsOfInfection = 0.2;
    private int minInfectionTime = 7;
    private int maxInfectionTime = 21;
    private int timeScale = 72;
    public int maxInfected = 0;

    private EnumMap<HealthStatus, Animatable> healthAnimation;

    public SimulationField() {
        this.subjects =  new Subject[subjectCount];
        listeners = new HashSet<>();
    }

    public void init() {
        healthAnimation = new EnumMap<>(HealthStatus.class);
        healthAnimation.put(HealthStatus.SUSCEPTIBLE, new RadiatingDot(Color.BLUE, 4, 4, 1, 1));
        healthAnimation.put(HealthStatus.INFECTED, new RadiatingDot(Color.RED, 4, infectionRadius, 1, 60));
        healthAnimation.put(HealthStatus.REMOVED, new RadiatingDot(Color.GRAY, 4, 4, 1, 1));
        timeData = new ArrayList<>();
        for(int i = 0; i < subjectCount; i++) {
            Subject s = createRandomSubject();
            s.setEventTime(i);
            subjects[i] = s;
            if (random.nextDouble() < ODDS_INITIAL_SICK) {
                s.updateHealth(HealthStatus.INFECTED, i, randomDuration());
            }
        }
        timeIndex = subjects.length + 1;
        eradicatedTime = -1;
        maxInfected = 0;
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
        if(eradicatedTime <= 0) {
            doStatistics();

        }
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
            int returnTime = timeIndex + MIN_STAY_TIME + random.nextInt(MAX_STAY_TIME - MIN_STAY_TIME);
            subjects[s].assignDestination(destination, returnTime);
        }
    }

    private void updateSubjects() {
        for(Subject s : subjects) {
            s.update(randomVector(MAX_RANDOM_FORCE), BOUNDS, 1, DESTINATION_FORCE_FACTOR, timeIndex);
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

    public void doStatistics() {
        int[] data = new int[HealthStatus.values().length];
        for(Subject s : subjects) {
            data[s.getStatus().ordinal()]++;
        }
        timeData.add(data);
        if(data[HealthStatus.INFECTED.ordinal()]== 0) {
            eradicatedTime = getTimeIndex();
        }
        if(data[HealthStatus.INFECTED.ordinal()] > maxInfected) {
            maxInfected = data[HealthStatus.INFECTED.ordinal()];
        }
    }

    public ArrayList<int[]> getTimeData() {
        return timeData;
    }

    public int getSubjectCount() {
        return subjectCount;
    }

    public int getEradicatedTime() {
        return eradicatedTime;
    }

    public int getHeight() {
        return BOUNDS[1];
    }

    public int getWidth() {
        return BOUNDS[0];
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isRestarting() {
        return restarting;
    }

    public void setRestarting(boolean restarting) {
        this.restarting = restarting;
    }

    public boolean isDestinationOn() {
        return destinationOn;
    }

    public void setDestinationOn(boolean destinationOn) {
        this.destinationOn = destinationOn;
    }

    public int getMaxInfected() {
        return maxInfected;
    }
}
