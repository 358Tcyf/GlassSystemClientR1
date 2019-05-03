package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

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
public class CommonMoreLineChart extends CommonChartFragment {

    private static final String ARG_CHART = "arg_chart";
    private BaseChart mBaseChart;


    public static CommonMoreLineChart newInstance(BaseChart baseChart) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_CHART, baseChart);
        CommonMoreLineChart fragment = new CommonMoreLineChart_();
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
        showMoreLineChart();
    }


    private void showMoreLineChart() {
        LineChartManager lineChartManager = new LineChartManager(mLineChart);
        List<Integer> colours = new ArrayList<>();
        colours.add(_mActivity.getColor(R.color.color1));
        colours.add(_mActivity.getColor(R.color.color2));
        colours.add(_mActivity.getColor(R.color.color3));
        colours.add(_mActivity.getColor(R.color.color4));
        lineChartManager.showMoreLineChart(setListData(), mBaseChart.getLabels(), mBaseChart.getxValues(), colours);
        lineChartManager.setXAxis(mBaseChart.getxValues().length, 0, mBaseChart.getxValues().length);
        lineChartManager.setDescription(mBaseChart.getTitle());

    }


    private List<List<Entry>> setListData() {
        List<List<Entry>> data = new ArrayList<>();
        List<List<BaseEntry>> listEntries = mBaseChart.getyListValues();
        for (List<BaseEntry> baseEntries : listEntries) {
            List<Entry> e = new ArrayList<>();
            for (BaseEntry entry : baseEntries) {
                e.add(new BarEntry(Float.valueOf(entry.getX().toString()), Float.valueOf(entry.getY().toString())));
            }
            data.add(e);
        }
        return data;
    }

}
