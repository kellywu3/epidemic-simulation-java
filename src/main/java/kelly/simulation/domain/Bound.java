package kelly.simulation.domain;

public class Bound {
    private int[] hiBound;
    private int[] loBound;
    private int[] center;
    private int width;
    private int height;

    public Bound(int[] loBound, int[] hiBound) {
        this.hiBound = hiBound;
        this.loBound = loBound;
        width = hiBound[0];
        height = hiBound[1];
    }

    public int[] getHiBound() {
        return hiBound;
    }

    public void setHiBound(int[] hiBound) {
        this.hiBound = hiBound;
        width = hiBound[0];
        height = hiBound[1];
    }

    public int[] getLoBound() {
        return loBound;
    }

    public void setLoBound(int[] loBound) {
        this.loBound = loBound;
    }
}
