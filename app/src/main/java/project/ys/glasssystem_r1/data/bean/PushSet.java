package project.ys.glasssystem_r1.data.bean;

public class PushSet {

    public PushSet() {
    }

    public PushSet(boolean pushSwitch, int time, boolean alarmSwitch) {
        this(pushSwitch, time, alarmSwitch, 0, 0);
    }

    public PushSet(boolean pushSwitch, int time, boolean alarmSwitch, long start, long end) {
        this.pushSwitch = pushSwitch;
        this.time = time;
        this.alarmSwitch = alarmSwitch;
        this.start = start;
        this.end = end;
    }

    private boolean pushSwitch;


    private int time;

    private boolean alarmSwitch;


    private long start;

    private long end;


    public boolean isPushSwitch() {
        return pushSwitch;
    }

    public void setPushSwitch(boolean pushSwitch) {
        this.pushSwitch = pushSwitch;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isAlarmSwitch() {
        return alarmSwitch;
    }

    public void setAlarmSwitch(boolean alarmSwitch) {
        this.alarmSwitch = alarmSwitch;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "PushSet{" +
                "pushSwitch=" + pushSwitch +
                ", time=" + time +
                ", alarmSwitch=" + alarmSwitch +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
