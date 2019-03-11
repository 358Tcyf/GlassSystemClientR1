package project.ys.glasssystem_r1.data.entity;

public class BaseEntry<T> {

    public BaseEntry() {
    }

    public BaseEntry(T xValue, T yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    private T xValue;

    private T yValue;

    public T getxValue() {
        return xValue;
    }

    public void setxValue(T xValue) {
        this.xValue = xValue;
    }

    public T getyValue() {
        return yValue;
    }

    public void setyValue(T yValue) {
        this.yValue = yValue;
    }

}
