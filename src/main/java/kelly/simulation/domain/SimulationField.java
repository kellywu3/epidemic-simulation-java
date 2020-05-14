package kelly.simulation.domain;

import kelly.simulation.MatrixUtil;
import kelly.simulation.things.Animatable;
import kelly.simulation.things.RadiatingDot;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.*;

public class SimulationField {
    private static final long DELAY = 10;
    private static final double SUBJECT_INITIAL_MAX_VELOCITY = 1;
    private static final double MAX_RANDOM_FORCE = 2;
    public static final double FRICTION_FACTOR = 0.98;
    public static final double DESTINATION_FORCE_FACTOR = 1;

    private static final Random random = new Random();
    private Set<SimulationEventListener> listeners;
    private int subjectCount = 200;
    private double subjectMass = 10;
    private ArrayList<int[]> timeData;
    private Subject[] subjects;
    private int timeIndex;
    private boolean paused = false;
    private boolean restarting;
    private int eradicatedTime;
    private int[] hiBound = new int[] {640, 480};
    private int[] loBound = new int[] {0, 0};
    private double[] destination = new double[] {0.5 * hiBound[0], 0.5 * hiBound[1]};
    private double oddsOfDestination = 0.02;
    private double oddsInitialSick = 0.02;
    private boolean destinationOn = false;
    private int infectionRadius = 30;
    private double oddsOfInfection = 0.2;
    private int minInfectionTime = 7;
    private int maxInfectionTime = 21;
    private int timeScale = 72;
    private int maxInfected = 0;
    private int minStayTime = 36;
    private int maxStayTime = 72;

    private EnumMap<HealthStatus, Animatable> healthAnimation;

    public SimulationField() {
        listeners = new HashSet<>();
    }

    public void init() {
        healthAnimation = new EnumMap<>(HealthStatus.class);
        healthAnimation.put(HealthStatus.SUSCEPTIBLE, new RadiatingDot(Color.BLUE, 4, 4, 1, 1));
        healthAnimation.put(HealthStatus.INFECTED, new RadiatingDot(Color.RED, 4, infectionRadius, 1, 60));
        healthAnimation.put(HealthStatus.REMOVED, new RadiatingDot(Color.GRAY, 4, 4, 1, 1));
        timeData = new ArrayList<>();
        this.subjects =  new Subject[subjectCount];
        for(int i = 0; i < subjectCount; i++) {
            Subject s = createRandomSubject();
            s.setEventTime(i);
            subjects[i] = s;
            if (random.nextDouble() < oddsInitialSick) {
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

    private synchronized void doSimulation() {
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
            int returnTime = timeIndex + minStayTime + random.nextInt(maxStayTime - minStayTime);
            subjects[s].assignDestination(destination, returnTime);
        }
    }

    private void updateSubjects() {
        for(Subject s : subjects) {
            s.update(subjectMass, randomVector(MAX_RANDOM_FORCE), loBound, hiBound, 1, DESTINATION_FORCE_FACTOR, timeIndex);
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
        int len = hiBound.length;
        double[] position = new double[len];
        for(int i = 0; i < len; i ++) {
            position[i] = random.nextDouble() * hiBound[i];
        }
        return position;
    }

    private Subject createRandomSubject() {
        return new Subject(randomPosition(), randomVector(SUBJECT_INITIAL_MAX_VELOCITY));
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
        return hiBound[1];
    }

    public int getWidth() {
        return hiBound[0];
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setRestarting(boolean restarting) {
        this.restarting = restarting;
    }

    public void setDestinationOn(boolean destinationOn) {
        this.destinationOn = destinationOn;
    }

    public int getMaxInfected() {
        return maxInfected;
    }

    public synchronized void updateSubjectCount(int subjects) {
        subjectCount = subjects;
        init();
    }

    public double getSubjectMass() {
        return subjectMass;
    }

    public void setSubjectMass(double subjectMass) {
        this.subjectMass = subjectMass;
    }

    public void updateHiBound(int[] highBound) {
        hiBound = highBound;
    }

    public double getOddsInitialSick() {
        return oddsInitialSick;
    }

    public synchronized void updateOddsInitialSick(double oddsInitialSick) {
        this.oddsInitialSick = oddsInitialSick;
        init();
    }

    public double getDestinationX() {
        return destination[0];
    }

    public double getDestinationY() {
        return destination[1];
    }

    public void setDestination(double[] destination) {
        this.destination = destination;
    }

    public double getOddsOfDestination() {
        return oddsOfDestination;
    }

    public void setOddsOfDestination(double oddsOfDestination) {
        this.oddsOfDestination = oddsOfDestination;
    }

    public double getOddsOfInfection() {
        return oddsOfInfection;
    }

    public void setOddsOfInfection(double oddsOfInfection) {
        this.oddsOfInfection = oddsOfInfection;
    }

    public int getInfectionRadius() {
        return infectionRadius;
    }

    public synchronized void updateInfectionRadius(int infectionRadius) {
        this.infectionRadius = infectionRadius;
        init();
    }

    public int getMinInfectionTime() {
        return minInfectionTime;
    }

    public void setMinInfectionTime(int minInfectionTime) {
        this.minInfectionTime = minInfectionTime;
    }

    public int getMaxInfectionTime() {
        return maxInfectionTime;
    }

    public void setMaxInfectionTime(int maxInfectionTime) {
        this.maxInfectionTime = maxInfectionTime;
    }

    public int getMinStayTime() {
        return minStayTime;
    }

    public void setMinStayTime(int minStayTime) {
        this.minStayTime = minStayTime;
    }

    public int getMaxStayTime() {
        return maxStayTime;
    }

    public void setMaxStayTime(int maxStayTime) {
        this.maxStayTime = maxStayTime;
    }
}
