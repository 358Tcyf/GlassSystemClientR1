package project.ys.glasssystem_r1.data.bean;

import project.ys.glasssystem_r1.data.entity.Alarm;

public class AlarmSelectedBean {

    public AlarmSelectedBean() {
    }


    public AlarmSelectedBean(boolean selected, Alarm alarm) {
        this.selected = selected;
        this.alarm = alarm;
    }

    private boolean selected;

    private boolean expand;


    private Alarm alarm;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }
}
