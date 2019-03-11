package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.entity.BaseChart;
import project.ys.glasssystem_r1.data.entity.BaseEntry;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.util.manager.PieChartManager;

@EFragment(R.layout.fragment_chart_pie)
public class CommonPieChart extends BaseBackFragment {

    private static final String ARG_CHART = "arg_chart";
    private BaseChart mBaseChart;

    public static CommonPieChart newInstance(BaseChart baseChart) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_CHART, baseChart);
        CommonPieChart fragment = new CommonPieChart_();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewById(R.id.pie_chart)
    PieChart mPieChart;

    @AfterInject
    void afterInject() {
        Bundle args = getArguments();
        if (args != null) {
            mBaseChart = args.getParcelable(ARG_CHART);
        }
    }

    @AfterViews
    void afterViews() {
        showPieChart();
    }

    private void showPieChart() {
        PieChartManager pieChartManager = new PieChartManager(mPieChart);

        List<Integer> colors = new ArrayList<>();
        colors.add(_mActivity.getColor(R.color.colorPrimaryDark));
        colors.add(_mActivity.getColor(R.color.color1));
        colors.add(_mActivity.getColor(R.color.color2));
        colors.add(_mActivity.getColor(R.color.colorPrimaryLight));

        if (mBaseChart.getChart_type() == BaseChart.pie_chart)
            pieChartManager.showSolidPieChart(setData(), mBaseChart.getLabel()
                    , Arrays.asList(_mActivity.getColor(R.color.colorPrimaryDark)
                            , _mActivity.getColor(R.color.color1)
                            , _mActivity.getColor(R.color.color2)
                            , _mActivity.getColor(R.color.colorPrimaryLight)));
        if (mBaseChart.getChart_type() == BaseChart.ring_chart)
            pieChartManager.showRingPieChart(setData(), mBaseChart.getLabel(), "电量单位：kW·h\n煤单位：吨\n水单位：吨"
                    , Arrays.asList(_mActivity.getColor(R.color.colorPrimaryDark)
                            , _mActivity.getColor(R.color.color1)
                            , _mActivity.getColor(R.color.color2)));
        pieChartManager.setDescription(mBaseChart.getTitle());
        pieChartManager.setSubDescription(mBaseChart.getDescription());


    }


    private List<PieEntry> setData1() {
        String[] xValues = {"产品1", "产品2", "产品3", "产品4"};
        List<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(254, xValues[0]));
        data.add(new PieEntry(167, xValues[1]));
        data.add(new PieEntry(243, xValues[2]));
        data.add(new PieEntry(700, xValues[3]));
        return data;
    }

    private List<PieEntry> setData2() {
        String[] xValues = {"电消耗", "煤消耗", "水消耗"};
        List<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(254, xValues[0]));
        data.add(new PieEntry(167, xValues[1]));
        data.add(new PieEntry(243, xValues[2]));
        return data;
    }

    private List<PieEntry> setData() {
        List<PieEntry> data = new ArrayList<>();
        List<BaseEntry> entries = mBaseChart.getyValues();
        for (BaseEntry entry : entries) {
            data.add(new PieEntry(Float.valueOf(entry.getyValue().toString()), entry.getxValue()));
        }
        return data;
    }

}
