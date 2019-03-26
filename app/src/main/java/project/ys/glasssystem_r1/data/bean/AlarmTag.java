package project.ys.glasssystem_r1.data.bean;

public class AlarmTag {

    public AlarmTag() {
    }


    public AlarmTag(String content) {
        this.content = content;
    }

    private String content;

    private float min;

    private float max;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    @Override
    public String toString() {
        return "AlarmTag{" +
                "content='" + content + '\'' +
                ", min=" + min +
                ", max=" + max +
                '}';
    }

    public void setMax(float max) {
        this.max = max;
    }
}
