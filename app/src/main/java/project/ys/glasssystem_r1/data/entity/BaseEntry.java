package project.ys.glasssystem_r1.data.entity;

public class BaseEntry<T,V> {

    public BaseEntry() {
    }

    public BaseEntry(T xValue, V yValue) {
        this.x = xValue;
        this.y = yValue;
    }

    private T x;

    private V y;

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public V getY() {
        return y;
    }

    public void setY(V y) {
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
