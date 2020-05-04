package kelly.simulation.domain;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SimulationField {
    private static final int SUBJECTS = 100;
    private static final long DELAY = 10;
    private static final double SUBJECT_MASS = 3;
    private static final double SUBJECT_INITIAL_MAX_VELOCITY = 1;
    private static final int[] BOUNDS = new int[] {640, 480};
    private static final double ODDS_INITIAL_SICK = 0.02;
    private static final double MAX_RANDOM_FORCE = 1;
    public static final double FRICTION_FACTOR = 0.99;

    private static final Dimension FIELD_DIMENSION = new Dimension(BOUNDS[0], BOUNDS[1]);
    private static final Random random = new Random();
    private Set<SimulationEventListener> listeners;
    private Subject[] subjects;
    private int timeIndex;

    public SimulationField() {
        this.subjects =  new Subject[SUBJECTS];
        listeners = new HashSet<>();
        for(int i = 0; i < SUBJECTS; i++) {
            Subject s = createRandomSubject();
            s.setEventTime(i);
            subjects[i] = s;
        }
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
            Image img = s.getStatus().getFrame(timeIndex - s.getEventTime());
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
        timeIndex = subjects.length + 1;
        while(true) {
            updateSubjects();
            publishEvent();
            safeSleep(DELAY);
            timeIndex++;
        }
    }

    private void updateSubjects() {
        for(Subject s : subjects) {
            s.update(randomVector(MAX_RANDOM_FORCE), BOUNDS, 1);
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

    private HealthStatus randomStatus() {
        return random.nextDouble() < ODDS_INITIAL_SICK ? HealthStatus.INFECTED : HealthStatus.SUSCEPTIBLE;
    }

    private Subject createRandomSubject() {
        return new Subject(
                randomPosition(), randomVector(SUBJECT_INITIAL_MAX_VELOCITY), SUBJECT_MASS, randomStatus()
        );
    }

    public Dimension getFieldDimension() {
        return FIELD_DIMENSION;
    }
}
