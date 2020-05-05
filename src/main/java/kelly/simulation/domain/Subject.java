package kelly.simulation.domain;

import kelly.simulation.MatrixUtil;

public class Subject {
    private double[] position;
    private double[] velocity;

    private double[] destination;
    private double[] oldLocation;

    private double mass;
    private HealthStatus status;
    private int eventTime;

    public Subject(double[] position, double[] velocity, double mass, HealthStatus status) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.status = status;
    }

    public void update(double[] force, int[] bound, int dt) {
        if(destination != null) {
            applyDestinationForce(2, force);
        }
        for(int i = 0; i < force.length; i++) {
            double a = force[i] / mass;
            double v = (velocity[i] + (a * dt)) * SimulationField.FRICTION_FACTOR;
            double p = position[i] + (v * dt);

            if(p < 0 || p >= bound[i]) {
                v = -v;
                p = position[i] + (v * dt);
            }

            velocity[i] = v;
            position[i] = p;
        }
    }

    public void setEventTime(int eventTime) {
        this.eventTime = eventTime;
    }

    public int getEventTime() {
        return eventTime;
    }

    public HealthStatus getStatus() {
        return status;
    }

    public double[] getPosition() {
        return position;
    }

    public void assignDestination(double[] destination, boolean saveLocation) {
        this.destination = destination;
        if(saveLocation) {
            this.oldLocation = MatrixUtil.clone(position);
        }
    }

    private void applyDestinationForce(int forceFactor, double[] force) {
        double[] destinationForce = new double[position.length];
        double distanceSquared = 0;
        for(int i = 0; i < position.length; i++) {
            double d = destination[i] - position[i];
            distanceSquared += (d * d);
            destinationForce[i] = d;
        }
        double distance = Math.sqrt(distanceSquared);
        if(distance < 1) {
            slowDown(0);
            destination = null;
        } else if(distance < 20) {
            slowDown(0.50);
        } else if(distance < 40) {
            slowDown(0.95);
        }

        for(int i = 0; i < destinationForce.length; i++) {
            destinationForce[i] = forceFactor * destinationForce[i] / distance;
        }

        MatrixUtil.addToFirst(force, destinationForce);
    }

    private void slowDown(double slowDownFactor) {
        MatrixUtil.applyScale(slowDownFactor, velocity);
    }
}