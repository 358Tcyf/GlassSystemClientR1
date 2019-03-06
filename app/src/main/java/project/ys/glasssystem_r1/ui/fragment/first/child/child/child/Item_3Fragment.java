package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.util.BarChartManager;

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
        List<Float> xAxisValues = new ArrayList<>();
        List<List<Float>> yAxisValues = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<Integer> colours = new ArrayList<>();
        List<String> models = new ArrayList<>();

        List<Float> x1 = new ArrayList<>();
        List<Float> x2 = new ArrayList<>();
        List<Float> x3 = new ArrayList<>();

        for (int i = 0; i < xValues.length; i++) {
            models.add(xValues[i]);
            xAxisValues.add(i + 1f);
        }

        /*镀膜成功率*/
        x1.add(10f);
        x1.add(20f);
        x1.add(30f);
        x1.add(40f);

        /*钢化成功率*/
        x2.add(50f);
        x2.add(40f);
        x2.add(30f);
        x2.add(20f);

        /*残片率*/
        x3.add(30f);
        x3.add(20f);
        x3.add(50f);
        x3.add(40f);

        yAxisValues.add(x1);
        yAxisValues.add(x2);
        yAxisValues.add(x3);

        labels.add("镀膜成功率");
        labels.add("钢化成功率");
        labels.add("残片率");

        colours.add(_mActivity.getColor(R.color.color1));
        colours.add(_mActivity.getColor(R.color.color2));
        colours.add(_mActivity.getColor(R.color.colorPrimaryDark));
        barChartManager.showMoreBarChart(xAxisValues, yAxisValues, labels, models, colours);
        barChartManager.setXAxis(models.size(), 0, models.size());
        barChartManager.setYAxis(100, 0, 10);
        barChartManager.setDescription("产品生产质量统计");

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


}
