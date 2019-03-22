package project.ys.glasssystem_r1.common.event;

public class FirstTabMenuEvent {
    public String tag;
    public int position;

    public FirstTabMenuEvent(int position, String tag) {
        this.position = position;
        this.tag = tag;

    }
}