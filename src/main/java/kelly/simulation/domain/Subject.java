package kelly.simulation.domain;

import kelly.simulation.MatrixUtil;

public class Subject {
    private double[] position;
    private double[] velocity;
    private double[] destination;
    private double[] oldLocation;
    private int community;

    private HealthStatus status = HealthStatus.SUSCEPTIBLE;
    private int eventTime;
    private int timeToChange = -1;
    private int returnTime = -1;

    public Subject(double[] velocity) {
        this.velocity = velocity;
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
        if(destination != null) {
            applyDestinationForce(forceFactor, force);
        } else if(returnTime >= 0 && returnTime < timeIndex) {
            destination = oldLocation;
            oldLocation = null;
            returnTime = -1;
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

    public void assignDestination(double[] destination, int returnTime) {
        this.destination = destination;
        if(returnTime >= 0) {
            this.oldLocation = MatrixUtil.clone(position);
            this.returnTime = returnTime;
        }
    }

    private void applyDestinationForce(double forceFactor, double[] force) {
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

    public void assignCommunity(int community) {
        this.community = community;
    }

    public int getCommunity() {
        return community;
    }
}