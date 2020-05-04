package kelly.simulation.things;

import kelly.simulation.Animatable;
import kelly.simulation.HealthStatus;
import kelly.simulation.Simulatable;
import kelly.simulation.domain.Position;

import java.awt.*;
import java.util.Random;

public class Subject implements Animatable, Simulatable {
    private Position position;
    private Position destination;
    private int framesToDestination;
    private HealthStatus health;
    private int infectedTime;
    private Random random = new Random();

    public Subject(Position position) {
        this.position = position;
        int rnd = random.nextInt(HealthStatus.values().length);
        this.health = HealthStatus.values()[rnd];
        this.infectedTime = random.nextInt(100);
    }

    @Override
    public Image getFrame(int frameIndex) {
        return health.getFrame(frameIndex + infectedTime);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void simulate() {
        if(framesToDestination <= 0 || destination == null) {
            framesToDestination = random.nextInt(600) + 240;
            destination = new Position(random.nextFloat() * 640, random.nextFloat() * 480);
        }
        position.moveTowards(destination, framesToDestination --);
    }

    public void travel(float newX, float newY, int frames) {
    }
}
