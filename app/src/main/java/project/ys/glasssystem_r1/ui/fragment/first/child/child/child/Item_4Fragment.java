package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import android.graphics.Paint;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieEntry;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.util.PieChartManager;

@EFragment(R.layout.fragment_chart_pie)
public class Item_4Fragment extends BaseBackFragment {

    public static Item_4Fragment newInstance() {
        return new Item_4Fragment_();
    }

    @ViewById(R.id.pie_chart)
    PieChart mPieChart;

    final String[] xValues = {"电消耗", "煤消耗", "水消耗"};

    @AfterViews
    void afterViews() {
        showPieChart();
    }

    private void showPieChart() {
        PieChartManager pieChartManager = new PieChartManager(mPieChart);

        //设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(_mActivity.getColor(R.color.colorPrimaryDark));
        colors.add(_mActivity.getColor(R.color.color1));
        colors.add(_mActivity.getColor(R.color.color2));

        pieChartManager.showRingPieChart(setData(), "生产数", "电量单位：kW·h\n煤单位：吨\n水单位：吨", colors);
        pieChartManager.setDescription("生产能耗统计");
        pieChartManager.setSubDescription("各类型能源消耗");
    }


    private List<PieEntry> setData() {
        List<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(254, xValues[0]));
        data.add(new PieEntry(167, xValues[1]));
        data.add(new PieEntry(243, xValues[2]));
        return data;
    }

}
