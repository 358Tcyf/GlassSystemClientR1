package project.ys.glasssystem_r1.common;

import me.yokeyword.fragmentation.SupportFragment;

public class StartBrotherEvent {
    public SupportFragment targetFragment;

    public StartBrotherEvent(SupportFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
