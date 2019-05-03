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
import project.ys.glasssystem_r1.ui.fragment.base.CommonChartFragment;
import project.ys.glasssystem_r1.util.managers.PieChartManager;

@EFragment(R.layout.fragment_chart_ring)
public class CommonRingChart extends CommonChartFragment {

    private static final String ARG_CHART = "arg_chart";
    private BaseChart mBaseChart;

    public static CommonRingChart newInstance(BaseChart baseChart) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_CHART, baseChart);
        CommonRingChart fragment = new CommonRingChart_();
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

        if (mBaseChart.getType() == BaseChart.pie_chart)
            pieChartManager.showSolidPieChart(setData(), ""
                    , Arrays.asList(_mActivity.getColor(R.color.colorPrimaryDark)
                            , _mActivity.getColor(R.color.color1)
                            , _mActivity.getColor(R.color.color2)
                            , _mActivity.getColor(R.color.color3)));
        if (mBaseChart.getType() == BaseChart.ring_chart)
            pieChartManager.showRingPieChart(setData(), ""
                    , "电量单位：kW·h\n煤单位：吨\n水单位：吨"
                    , Arrays.asList(_mActivity.getColor(R.color.color1)
                            , _mActivity.getColor(R.color.color2)
                            , _mActivity.getColor(R.color.color3)));
        pieChartManager.setDescription(mBaseChart.getTitle());
        pieChartManager.setSubDescription(mBaseChart.getDesc());


    }


    private List<PieEntry> setData() {
        List<PieEntry> data = new ArrayList<>();
        List<BaseEntry> entries = mBaseChart.getyValues();
        for (BaseEntry entry : entries) {
            data.add(new PieEntry(Float.valueOf(entry.getY().toString()), String.valueOf(entry.getX())));
        }
        return data;
    }

}
