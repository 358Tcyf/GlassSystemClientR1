package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.util.manager.BarChartManager;

@EFragment(R.layout.fragment_chart_bar)
public class Item_1Fragment extends BaseBackFragment {

    public static Item_1Fragment newInstance() {
        return new Item_1Fragment_();
    }

    @ViewById(R.id.bar_chart)
    BarChart mBarChart;

    @AfterViews
    void afterViews() {
        showBarChartAlong();
    }

    private void showBarChartAlong() {
        BarChartManager barChartManager = new BarChartManager(mBarChart);
        String[] xValues = {"0时", "4时", "8时", "12时", "16时", "20时", "24时"};
        String label = "生产总量";
        List<String> labels =Arrays.asList("生产数", "残片数");
        List<Integer> colours = new ArrayList<>();
        colours.add(_mActivity.getColor(R.color.color2));
        colours.add(_mActivity.getColor(R.color.color1));
//        barChartManager.showBarChart(setData(), label, xValues, _mActivity.getColor(R.color.colorPrimaryDark), false);
        barChartManager.showListBarChart(setListData(), labels, xValues, colours, false);
        barChartManager.setDescription("产品生产总量统计");
    }

    private List<BarEntry> setData() {
        List<BarEntry> data = new ArrayList<>();
        data.add(new BarEntry(1f, 80f));
        data.add(new BarEntry(2f, 50f));
        data.add(new BarEntry(3f, 60f));
        data.add(new BarEntry(4f, 60f));
        data.add(new BarEntry(5f, 70f));
        data.add(new BarEntry(6f, 80f));
        return data;
    }

    private List<List<BarEntry>> setListData() {
        List<List<BarEntry>> data = new ArrayList<>();
        List<BarEntry> yValues1 = new ArrayList<>();
        yValues1.add(new BarEntry(1f, 80f));
        yValues1.add(new BarEntry(2f, 50f));
        yValues1.add(new BarEntry(3f, 60f));
        yValues1.add(new BarEntry(4f, 60f));
        yValues1.add(new BarEntry(5f, 70f));
        yValues1.add(new BarEntry(6f, 80f));
        List<BarEntry> yValues2 = new ArrayList<>();
        yValues2.add(new BarEntry(1f, 50f));
        yValues2.add(new BarEntry(2f, 25f));
        yValues2.add(new BarEntry(3f, 30f));
        yValues2.add(new BarEntry(4f, 30f));
        yValues2.add(new BarEntry(5f, 30f));
        yValues2.add(new BarEntry(6f, 40f));
        data.add(yValues1);
        data.add(yValues2);
        return data;
    }

}
