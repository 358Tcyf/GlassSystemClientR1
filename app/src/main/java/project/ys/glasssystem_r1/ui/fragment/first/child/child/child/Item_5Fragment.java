package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import com.airbnb.lottie.L;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.util.LineChartManager;

@EFragment(R.layout.fragment_chart_line)
public class Item_5Fragment extends BaseBackFragment {

    public static Item_5Fragment newInstance() {
        return new Item_5Fragment_();
    }

    @ViewById(R.id.line_chart)
    LineChart mLineChart;

    @AfterViews
    void afterViews() {
        showLineChart();
    }

    final String[] xValues = {"0时", "4时", "8时", "12时", "16时", "20时", "24时"};


    private void showLineChart() {
        LineChartManager lineChartManager = new LineChartManager(mLineChart);
        String label = "销售额";
        lineChartManager.showLineChart(setData(), label, xValues, _mActivity.getColor(R.color.colorPrimaryLight));
        lineChartManager.setDescription("产品销售额统计");
    }

    private List<Entry> setData() {
        List<Entry> data = new ArrayList<>();
        data.add(new Entry(0f, 20f));
        data.add(new Entry(1f, 50f));
        data.add(new Entry(2f, 80));
        data.add(new Entry(3f, 60f));
        data.add(new Entry(4f, 60f));
        data.add(new Entry(5f, 30f));
        data.add(new Entry(6f, 20f));
        return data;
    }

}
