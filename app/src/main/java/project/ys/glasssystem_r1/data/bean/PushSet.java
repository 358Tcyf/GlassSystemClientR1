package project.ys.glasssystem_r1.data.bean;

public class PushSet {

    public PushSet() {
    }

    public PushSet(boolean pushSwitch, int time, boolean alarmSwitch) {
        this.pushSwitch = pushSwitch;
        this.time = time;
        this.alarmSwitch = alarmSwitch;
    }

    private boolean pushSwitch;

    private int time;

    private boolean alarmSwitch;

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

    @Override
    public String toString() {
        return "PushSet{" +
                "pushSwitch=" + pushSwitch +
                ", time=" + time +
                ", alarmSwitch=" + alarmSwitch +
                '}';
    }
}
