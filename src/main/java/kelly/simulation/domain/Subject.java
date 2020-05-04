package kelly.simulation.domain;

public class Subject {
    private double[] position;
    private double[] velocity;
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
}