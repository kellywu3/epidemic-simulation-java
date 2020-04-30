package kelly.simulation;

import kelly.simulation.things.RadiatingDot;

import java.awt.*;

public enum HealthStatus {
    SUSCEPTIBLE(new RadiatingDot(Color.BLUE, 4, 100, 1, 200), 0.001f),
    INFECTED(new RadiatingDot(Color.RED, 4, 30, 1, 60), 0.005f),
    RECOVERED(new RadiatingDot(Color.GRAY, 4, 4, 1, 1), 0.000f);

    private Animatable animatable;
    private float odds;

    HealthStatus(Animatable animatable, float odds) {
        this.animatable = animatable;
        this.odds = odds;
    }

    public Image getFrame(int index) {
        return animatable.getFrame(index);
    }

    public float getOdds() {
        return odds;
    }
}
