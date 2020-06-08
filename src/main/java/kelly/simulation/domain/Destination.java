package kelly.simulation.domain;

import java.util.Arrays;

public class Destination {
    private double[] position;
    private int duration;
    private int returnTime;
    private int community;

    public Destination(double[] position, int duration, int community) {
        this.position = position;
        this.duration = duration;
        this.community = community;
        returnTime = -1;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCommunity() {
        return community;
    }

    public void setCommunity(int community) {
        this.community = community;
    }

    public int getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(int timeIndex) {
        this.returnTime = timeIndex + duration;
    }

    @Override
    public String toString() {
        return "Destination{" +
            "position=" + Arrays.toString(position) +
            ", timeToReturn=" + duration +
            ", community=" + community +
            '}';
    }
}
