package project.ys.glasssystem_r1.data.bean;

public class PushSet {

    public PushSet() {
    }

    public PushSet(boolean pushSwitch, int time, boolean alarmSwitch) {
        this(pushSwitch, time, alarmSwitch, 0, 0);
    }

    public PushSet(boolean pushSwitch, int time, boolean alarmSwitch, long start, long end) {
        this(true,true,true,true,pushSwitch,time, alarmSwitch,start,end);
    }

    public PushSet(boolean commonSwitch, boolean sound, boolean vibrate, boolean flags, boolean pushSwitch, int time, boolean alarmSwitch, long start, long end) {
        this.commonSwitch = commonSwitch;
        this.sound = sound;
        this.vibrate = vibrate;
        this.flags = flags;
        this.pushSwitch = pushSwitch;
        this.time = time;
        this.alarmSwitch = alarmSwitch;
        this.start = start;
        this.end = end;
    }

    private boolean commonSwitch;

    private boolean sound;

    private boolean vibrate;

    private boolean flags;

    private boolean pushSwitch;

    private int time;

    private boolean alarmSwitch;

    private long start;

    private long end;

    public boolean isCommonSwitch() {
        return commonSwitch;
    }

    public void setCommonSwitch(boolean commonSwitch) {
        this.commonSwitch = commonSwitch;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public boolean isFlags() {
        return flags;
    }

    public void setFlags(boolean flags) {
        this.flags = flags;
    }

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


    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
