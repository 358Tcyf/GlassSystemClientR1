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
public class CommonOnlyBarChart extends CommonChartFragment {

    private static final String ARG_CHART = "arg_chart";
    private BaseChart mBaseChart;


    public static CommonOnlyBarChart newInstance(BaseChart baseChart) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_CHART, baseChart);
        CommonOnlyBarChart fragment = new CommonOnlyBarChart_();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewById(R.id.bar_chart)
    BarChart mBarChart;

    @AfterInject
    void afterInject() {
        Bundle args = getArguments();
        if (args != null) {
//            position = args.getInt(ARG_MENU);
            mBaseChart = args.getParcelable(ARG_CHART);
        }
    }

    @AfterViews
    void afterViews() {
        showBarChartAlong();
    }

    private void showBarChartAlong() {
        BarChartManager barChartManager = new BarChartManager(mBarChart);
        String[] xValues = mBaseChart.getxValues();
        List<String> labels = mBaseChart.getLabels();
        List<Integer> colours = new ArrayList<>();
        colours.add(_mActivity.getColor(R.color.color4));
        colours.add(_mActivity.getColor(R.color.color3));
//        barChartManager.showBarChart(setData(), label, xValues, _mActivity.getColor(R.color.colorPrimaryDark), false);
        barChartManager.showListBarChart(setListData(), labels, xValues, colours, false);
        barChartManager.setDescription(mBaseChart.getTitle());
    }

    private List<BarEntry> setData() {
        List<BarEntry> data = new ArrayList<>();
        List<BaseEntry> entries = mBaseChart.getyValues();
        for (BaseEntry entry : entries) {
            data.add(new BarEntry(Float.valueOf(entry.getX().toString()), Float.valueOf(entry.getY().toString())));
        }
        return data;
    }

    private List<List<BarEntry>> setListData() {
        List<List<BarEntry>> data = new ArrayList<>();
        List<List<BaseEntry>> listEntries = mBaseChart.getyListValues();


        for (int i = 0; i < listEntries.size(); i++) {
            data.add(new ArrayList<>());
            List<BaseEntry> entries = listEntries.get(i);
            for (int j = 0; j < entries.size(); j++) {
                data.get(i).add(new BarEntry(Float.valueOf(entries.get(j).getX().toString()), Float.valueOf(entries.get(j).getY().toString())));
            }
        }
        return data;
    }

}
