package kelly.simulation;

public class MatrixUtil {
    public static double[] clone(double[] original) {
        double[] result = new double[original.length];
        for(int i = 0; i < original.length; i++) {
            result[i] = original[i];
        }
        return result;
    }

    public static double[] applyScale(double scale, double[] original) {
        for(int i = 0; i < original.length; i++) {
            original[i] = original[i] * scale;
        }
        return original;
    }

    public static double[] addToFirst(double[] first, double[] second) {
        for(int i = 0; i < first.length; i++) {
            first[i] = first[i] + second[i];
        }
        return first;
    }
}
