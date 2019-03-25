package project.ys.glasssystem_r1.data.bean;

import project.ys.glasssystem_r1.data.entity.Push;

public class PushSelectedBean {

    public PushSelectedBean() {
    }


    public PushSelectedBean(boolean selected, Push push) {
        this.selected = selected;
        this.push = push;
    }

    private boolean selected;

    private Push push;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Push getPush() {
        return push;
    }

    public void setPush(Push push) {
        this.push = push;
    }
}
