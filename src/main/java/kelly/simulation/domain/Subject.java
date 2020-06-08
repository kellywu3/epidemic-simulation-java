package kelly.simulation.domain;

import kelly.simulation.MatrixUtil;

import java.util.Stack;

public class Subject {
    private double[] position;
    private double[] velocity;
    private Stack<Destination> destinations;
    private int community;

    private HealthStatus status = HealthStatus.SUSCEPTIBLE;
    private int eventTime;
    private int timeToChange = -1;

    public Subject(double[] velocity) {
        this.velocity = velocity;
        destinations = new Stack<>();
    }

    public void updateHealth(HealthStatus status, int timeIndex, int duration) {
        this.status = status;
        this.eventTime = timeIndex;
        this.timeToChange = eventTime + duration;
    }

    public boolean isTimeToChange(int timeIndex) {
        return timeToChange >= 0 && timeIndex > timeToChange;
    }

    public void update(double mass, double[] force, CommunityManager mgr, double forceFactor, int timeIndex, double frictionFactor) {
        Bound bound = mgr.getCommunity(community);
        if(position == null) {
            position = bound.randomPosition();
        }

        Destination destination = destinations.isEmpty() ? null : destinations.peek();
        if(destination != null) {
            applyDestinationForce(destination, timeIndex, forceFactor, force, mgr);
            community = destination.getCommunity();
            if(destination.getReturnTime() != -1 && destination.getReturnTime() < timeIndex) {
                destinations.pop();
            }
        }

        for(int i = 0; i < force.length; i++) {
            double a = force[i] / mass;
            double v = (velocity[i] + a) * frictionFactor;
            double p = position[i] + v;

            if((p <= bound.getLoBound()[i] && v < 0) || (p >= bound.getHiBound()[i] && v > 0)) {
                v = -v;
                p = position[i] + v;
            } else {
                p = position[i] + v;
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

    public void assignDestination(Destination destination) {
        destinations.add(new Destination(MatrixUtil.clone(position), -1, community));
        destinations.add(destination);
    }

    private void applyDestinationForce(Destination destination, int timeIndex, double forceFactor, double[] force, CommunityManager mgr) {
        double[] destinationForce = new double[position.length];
        double distanceSquared = 0;
        for(int i = 0; i < position.length; i++) {
            double d = destination.getPosition()[i] - position[i];
            distanceSquared += (d * d);
            destinationForce[i] = d;
        }
        double distance = Math.sqrt(distanceSquared);

        double slowFactor = mgr.getCommunity(community).calculateSlowDown(distance);
        if(slowFactor == 0 && destination.getReturnTime() == -1) {
            slowDown(slowFactor);
            destination.setReturnTime(timeIndex);
        } else {
            slowDown(slowFactor);
        }

        for(int i = 0; i < destinationForce.length; i++) {
            destinationForce[i] = forceFactor * destinationForce[i] / distance;
        }

        MatrixUtil.addToFirst(force, destinationForce);
    }

    private void slowDown(double slowDownFactor) {
        MatrixUtil.applyScale(slowDownFactor, velocity);
    }

    public void assignCommunity(int community) {
        this.community = community;
    }

    public int getCommunity() {
        return community;
    }

    public String getDestination() {
        if(destinations.isEmpty()) {
            return null;
        }
        return Integer.toString(destinations.peek().getCommunity());
    }
}