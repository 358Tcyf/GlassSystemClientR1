package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.entity.BaseChart;
import project.ys.glasssystem_r1.data.entity.BaseEntry;
import project.ys.glasssystem_r1.ui.fragment.base.CommonChartFragment;
import project.ys.glasssystem_r1.util.managers.LineChartManager;

@EFragment(R.layout.fragment_chart_line)
public class CommonOnlyLineChart extends CommonChartFragment {

    private static final String ARG_CHART = "arg_chart";
    private BaseChart mBaseChart;


    public static CommonOnlyLineChart newInstance(BaseChart baseChart) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_CHART, baseChart);
        CommonOnlyLineChart fragment = new CommonOnlyLineChart_();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewById(R.id.line_chart)
    LineChart mLineChart;

    @AfterInject
    void afterInject() {
        Bundle args = getArguments();
        if (args != null) {
            mBaseChart = args.getParcelable(ARG_CHART);
        }
    }

    @AfterViews
    void afterViews() {
        showLinerChartAlong();
    }

    private void showLinerChartAlong() {
        LineChartManager lineChartManager = new LineChartManager(mLineChart);
        String[] xValues = mBaseChart.getxValues();
        String label = mBaseChart.getLabel();
        lineChartManager.showLineChart(setData(), label, xValues, _mActivity.getColor(R.color.color4));
        lineChartManager.setDescription(mBaseChart.getTitle());
    }

    private List<Entry> setData() {
        List<Entry> data = new ArrayList<>();
        List<BaseEntry> entries = mBaseChart.getyValues();
        for (BaseEntry entry : entries) {
            Logger.d(entry);
            float x = Float.valueOf(entry.getX().toString());
            float y = entry.getY() == null ? 0 : Float.valueOf(entry.getY().toString());
            data.add(new Entry(x, y));
        }
        return data;
    }

}
