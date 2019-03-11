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
public class Item_3Fragment extends BaseBackFragment {

    public static Item_3Fragment newInstance() {
        return new Item_3Fragment_();
    }

    @ViewById(R.id.bar_chart)
    BarChart mBarChart;

    @AfterViews
    void afterViews() {
        showBarChartMore();
    }

    final String[] xValues = {"产品1", "产品2", "产品3", "产品4"};

    private void showBarChartMore() {
        BarChartManager barChartManager = new BarChartManager(mBarChart);
        List<String> labels =Arrays.asList("镀膜成功率", "钢化成功率", "残片率");
        List<Integer> colours = new ArrayList<>();

        colours.add(_mActivity.getColor(R.color.color1));
        colours.add(_mActivity.getColor(R.color.color2));
        colours.add(_mActivity.getColor(R.color.colorPrimaryDark));
        barChartManager.showMoreBarChart(setListData(),labels, xValues, colours);
        barChartManager.setXAxis(xValues.length, 0,xValues.length);
        barChartManager.setYAxis(100, 0, 10);
        barChartManager.setDescription("产品生产质量统计");

    }


    private List<List<BarEntry>> setListData() {
        List<List<BarEntry>> data = new ArrayList<>();
        List<BarEntry> yValues1 = new ArrayList<>();
        List<BarEntry> yValues2 = new ArrayList<>();
        List<BarEntry> yValues3 = new ArrayList<>();
        yValues1.add(new BarEntry(1f, 10f));
        yValues1.add(new BarEntry(2f, 20f));
        yValues1.add(new BarEntry(3f, 30f));
        yValues1.add(new BarEntry(4f, 40f));

        yValues2.add(new BarEntry(1f, 50f));
        yValues2.add(new BarEntry(2f, 40f));
        yValues2.add(new BarEntry(3f, 30f));
        yValues2.add(new BarEntry(4f, 20f));

        yValues3.add(new BarEntry(1f, 40f));
        yValues3.add(new BarEntry(2f, 25f));
        yValues3.add(new BarEntry(3f, 50f));
        yValues3.add(new BarEntry(4f, 30f));

        data.add(yValues1);
        data.add(yValues2);
        data.add(yValues3);
        return data;
    }

}
