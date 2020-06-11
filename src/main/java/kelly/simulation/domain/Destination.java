package kelly.simulation.domain;

import java.util.Arrays;

public class Destination {
    private double[] position;
    private int duration;
    private int returnTime;
    private int community;
    private DestinationType type;

    public enum DestinationType {
        QUARANTINE
        , OLD_LOCATION
        , COMMUNITY_CENTER;
    }

    public Destination(double[] position, int duration, int community, DestinationType type) {
        this.position = position;
        this.duration = duration;
        this.community = community;
        this.type = type;
        returnTime = -1;
    }

    public static Destination createQuarantineDestination(double[] position, int community) {
        return new Destination(position, 1, community, DestinationType.QUARANTINE);
    }

    public static Destination createOldLocationDestination(double[] position, int duration, int community) {
        return new Destination(position, duration, community, DestinationType.OLD_LOCATION);
    }

    public static Destination createCommunityCenterDestination(double[] position, int duration, int community) {
        return new Destination(position, duration, community, DestinationType.COMMUNITY_CENTER);
    }

    public int getDuration() {
        return duration;
    }

    public double[] getPosition() {
        return position;
    }

    public int getCommunity() {
        return community;
    }

    public int getReturnTime() {
        return  returnTime;
    }

    public void setReturnTime(int timeIndex) {
        this.returnTime = timeIndex + duration;
    }

    public DestinationType checkType() {
        return type;
    }

    @Override
    public String toString() {
        return "Destination{" +
            "position=" + Arrays.toString(position) +
            ", duration=" + duration +
            ", returnTime=" + returnTime +
            ", community=" + community +
            ", type=" + type +
            '}';
    }

    public boolean isTimeToReturn(int timeIndex) {
        return returnTime > 0 && returnTime < timeIndex;
    }

}
