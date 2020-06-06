package kelly.simulation.domain;

import kelly.simulation.MatrixUtil;

import java.util.Arrays;
import java.util.Random;

public class Bound {
    private static final Random random = new Random();
    private double[] hiBound;
    private double[] loBound;
    private double[] center;
    private double[] dimensions;

    public Bound(double[] loBound, double[] dimensions) {
        this.setBounds(loBound, dimensions);
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

    private void recalculateBounds() {
        hiBound = MatrixUtil.addToFirst(
            MatrixUtil.clone(loBound), dimensions
        );
        center = MatrixUtil.applyScale(
            0.5
            , MatrixUtil.addToFirst(MatrixUtil.clone(hiBound), loBound)
        );
    }

    public double[] randomPosition() {
        int len = dimensions.length;
        double[] position = new double[len];
        for(int i = 0; i < len; i ++) {
            position[i] = loBound[i] + random.nextDouble() * dimensions[i];
        }
        return position;
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
