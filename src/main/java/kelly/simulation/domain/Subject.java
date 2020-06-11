package kelly.simulation.domain;

import kelly.simulation.MatrixUtil;
import java.util.Stack;

public class Subject {
    private int id;
    private double[] position;
    private double[] velocity;
    private Stack<Destination> destinations;
    private int community;
    private HealthStatus status = HealthStatus.SUSCEPTIBLE;
    private int eventTime;
    private int timeToChange = -1;
    private int timeForQuarantine = -1;
    private boolean quarantined;

    public Subject(int id, double[] velocity) {
        this.velocity = velocity;
        destinations = new Stack<>();
        this.id = id;
    }

    public void updateHealth(HealthStatus status, int timeIndex, int duration, int quarantineDelay) {
        this.status = status;
        this.eventTime = timeIndex;
        this.timeToChange = eventTime + duration;
        if(status == HealthStatus.INFECTED && timeForQuarantine < 0) {
            timeForQuarantine = eventTime + quarantineDelay;
        }
    }

    public boolean isTimeToChange(int timeIndex) {
        return timeToChange >= 0 && timeIndex > timeToChange;
    }

    public synchronized void update(double mass, double[] force, CommunityManager mgr, double forceFactor, int timeIndex, double frictionFactor) {
        Bound bound = mgr.getCommunity(community);
        if(position == null) {
            position = bound.randomPosition();
        }

        Destination destination = destinations.isEmpty() ? null : destinations.peek();
        if(destination != null) {
            if(destination.getReturnTime() < 0) {
                applyDestinationForce(destination, timeIndex, forceFactor, force, mgr);
                community = destination.getCommunity();
                bound = mgr.getCommunity(community);
            } else if(destination.getReturnTime() > 0 && destination.getReturnTime() > timeIndex) {
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

    public synchronized void assignDestination(Destination destination) {
        destinations.add(new Destination(MatrixUtil.clone(position), 1, community));
        destinations.add(destination);
    }

    public synchronized void assignQuarantine(CommunityManager mgr) {
        assignDestination(new Destination(mgr.getCommunity(0).getCenter(), 0));
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

        double slowFactor = mgr.calculateSlowDown(distance);
        // The calculate slow down method returns '0' once the distance is within a certain, close range to the destination.
        // Once the method returns '0', the subject has "arrived" at the destination.
        if(destination.getReturnTime() < 0) {
            if(slowFactor == 0){
                if(quarantined) {
                    destinations.clear();
                }
                destination.setReturnTime(timeIndex);
            }
            slowDown(slowFactor);
            for(int i = 0; i < destinationForce.length; i++) {
                destinationForce[i] = forceFactor * destinationForce[i] / distance;
            }

            MatrixUtil.addToFirst(force, destinationForce);
        }
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

    public boolean isTimeForQuarantine(int timeIndex) {
        if(!quarantined && timeForQuarantine > 0 && timeForQuarantine < timeIndex) {
            quarantined = true;
            return true;
        }
        return false;
    }

    public String describe() {
        double[] pos = destinations.isEmpty() ? new double[] {-1, -1} : destinations.peek().getPosition();
        return String.format("%d - %d - (%d, %d) - %d", id, community
            , Math.round(pos[0]), Math.round(pos[1])
            , destinations.size()
            );
    }
}