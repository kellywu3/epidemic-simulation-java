package kelly.simulation.domain;

public class Position {
    private float x;
    private float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void moveTowards(Position destination, int frames) {
        x = (destination.getX() - x) / frames + x;
        y = (destination.getY() - y) / frames + y;
    }
}
