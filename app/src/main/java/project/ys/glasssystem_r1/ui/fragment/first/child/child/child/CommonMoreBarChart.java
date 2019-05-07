package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

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
import project.ys.glasssystem_r1.util.managers.BarChartManager;

@EFragment(R.layout.fragment_chart_bar)
public class CommonMoreBarChart extends CommonChartFragment {

    private static final String ARG_CHART = "arg_chart";
    private BaseChart mBaseChart;


    public static CommonMoreBarChart newInstance(BaseChart baseChart) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_CHART, baseChart);
        CommonMoreBarChart fragment = new CommonMoreBarChart_();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewById(R.id.bar_chart)
    BarChart mBarChart;

    @AfterInject
    void afterInject() {
        Bundle args = getArguments();
        if (args != null) {
            mBaseChart = args.getParcelable(ARG_CHART);
        }
    }

    @AfterViews
    void afterViews() {
        showBarChartMore();
    }


    private void showBarChartMore() {
        BarChartManager barChartManager = new BarChartManager(mBarChart);
        List<Integer> colours = new ArrayList<>();
        colours.add(_mActivity.getColor(R.color.color1));
        colours.add(_mActivity.getColor(R.color.color2));
        colours.add(_mActivity.getColor(R.color.color3));
        barChartManager.showMoreBarChart(setListData(), mBaseChart.getLabels(), mBaseChart.getxValues(), colours);
        barChartManager.setXAxis(mBaseChart.getxValues().length, 0, mBaseChart.getxValues().length);
        barChartManager.setDescription(mBaseChart.getTitle());

    }

    private List<List<BarEntry>> setListData() {
        List<List<BarEntry>> data = new ArrayList<>();
        List<List<BaseEntry>> listEntries = mBaseChart.getyListValues();
        for (int i = 0; i < listEntries.size(); i++) {
            data.add(new ArrayList<>());
            List<BaseEntry> entries = listEntries.get(i);
            for (int j = 0; j < entries.size(); j++) {
                data.get(i).add(new BarEntry(Integer.valueOf(entries.get(j).getX().toString()), Integer.valueOf(entries.get(j).getY().toString())));
            }
        }
        return data;
    }

}
