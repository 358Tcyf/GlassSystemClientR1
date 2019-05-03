package project.ys.glasssystem_r1.data.entity;

public class BaseEntry<T> {

    public BaseEntry() {
    }

    public BaseEntry(T xValue, T yValue) {
        this.x = xValue;
        this.y = yValue;
    }

    private T x;

    private T y;

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "BaseEntry{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
