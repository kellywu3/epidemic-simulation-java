package kelly.simulation.domain;

import kelly.simulation.MatrixUtil;

import java.util.Arrays;
import java.util.Random;

public class Bound {
    private static final double CENTER_SCALE = 0.3;
    private static final double DISTANCE_3 = 0.2;
    private static final double SLOW_DOWN_FACTOR_3 = 0.90;
    private static final double DISTANCE_2 = 0.1;
    private static final double SLOW_DOWN_FACTOR_2 = 0.50;
    private static final double DISTANCE_1 = 0.0005;
    private static final double SLOW_DOWN_FACTOR_1 = 0;
    private static final Random random = new Random();
    private double[] hiBound;
    private double[] loBound;
    private double[] center;
    private double[] dimensions;
    private double radius;

    public Bound(double[] loBound, double[] dimensions) {
        this.setBounds(loBound, dimensions);
        radius = getRadius();
    }

    public void setBounds(double[] loBound, double[] dimensions) {
        this.loBound = loBound;
        this.dimensions = dimensions;
        recalculateBounds();
    }

    public double[] getLoBound() {
        return loBound;
    }

    public double[] getHiBound() {
        return hiBound;
    }

    public double[] getCenter() {
        return center;
    }

    public double[] getDimensions() {
        return dimensions;
    }

    public double getRadius() {
        return Math.sqrt(Math.pow(dimensions[0], 2) + Math.pow(dimensions[1], 2)) / 4;
    }

    public double getCommunityRadius() {
        return radius * CENTER_SCALE;
    }

    private void recalculateBounds() {
        hiBound = MatrixUtil.addToFirst(
            MatrixUtil.clone(loBound), dimensions
        );
        center = MatrixUtil.applyScale(
            0.5
            , MatrixUtil.addToFirst(MatrixUtil.clone(hiBound), loBound)
        );
        radius = getRadius();
    }

    public double[] randomPosition() {
        int len = dimensions.length;
        double[] position = new double[len];
        for(int i = 0; i < len; i ++) {
            position[i] = loBound[i] + random.nextDouble() * dimensions[i];
        }
        return position;
    }

    public double calculateSlowDown(double distance) {
        if(distance < radius * DISTANCE_1) {
            return SLOW_DOWN_FACTOR_1;
        } else if(distance < radius * DISTANCE_2) {
            return SLOW_DOWN_FACTOR_2;
        } else if(distance < radius * DISTANCE_3) {
             return SLOW_DOWN_FACTOR_3;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "Bound{" +
            "hiBound=" + Arrays.toString(hiBound) +
            ", loBound=" + Arrays.toString(loBound) +
            ", center=" + Arrays.toString(center) +
            ", dimensions=" + Arrays.toString(dimensions) +
            '}';
    }
}
