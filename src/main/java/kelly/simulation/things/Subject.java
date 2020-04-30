package kelly.simulation.things;

import kelly.simulation.Animatable;
import kelly.simulation.HealthStatus;
import kelly.simulation.Locatable;

import java.awt.*;
import java.util.Random;

public class Subject implements Animatable, Locatable {
    private float x;
    private float y;
    private HealthStatus health;
    private int infectedTime;
    private Random random = new Random();

    public Subject(int x, int y) {
        this.x = x;
        this.y = y;
        int rnd = random.nextInt(HealthStatus.values().length);
        this.health = HealthStatus.values()[rnd];
        this.infectedTime = random.nextInt(100);
    }

    @Override
    public Image getFrame(int frameIndex) {
        return health.getFrame(frameIndex + infectedTime);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
