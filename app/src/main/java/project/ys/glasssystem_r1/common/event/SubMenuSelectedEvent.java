package project.ys.glasssystem_r1.common.event;

import project.ys.glasssystem_r1.data.entity.BaseChart;

public class SubMenuSelectedEvent {
    public BaseChart chart;

    public SubMenuSelectedEvent(BaseChart chart) {
        this.chart = chart;
    }
}