package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import android.graphics.Paint;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.util.LineChartManager;

@EFragment(R.layout.fragment_chart_line)
public class Item_6Fragment extends BaseBackFragment {

    public static Item_6Fragment newInstance() {
        return new Item_6Fragment_();
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

        List<String> labels = new ArrayList<>();
        List<Integer> colours = new ArrayList<>();


        labels.add("产品1");
        labels.add("产品2");

        colours.add(_mActivity.getColor(R.color.color1));
        colours.add(_mActivity.getColor(R.color.color2));
//        String label = "销售额";
//        lineChartManager.showLineChart(setData(), label, xValues, _mActivity.getColor(R.color.colorPrimaryLight));
        lineChartManager.showMoreLineChart(setListData(),labels,xValues,colours);
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

    private List<List<Entry>> setListData() {
        List<List<Entry>> data = new ArrayList<>();

        List<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(0f, 85f));
        entries1.add(new Entry(1f, 88f));
        entries1.add(new Entry(2f, 75f));
        entries1.add(new Entry(3f, 69f));
        entries1.add(new Entry(4f, 95f));
        entries1.add(new Entry(5f, 77f));
        entries1.add(new Entry(6f, 88f));

        List<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(0f, 75f));
        entries2.add(new Entry(1f, 88f));
        entries2.add(new Entry(2f, 55f));
        entries2.add(new Entry(3f, 79f));
        entries2.add(new Entry(4f, 85f));
        entries2.add(new Entry(5f, 97f));
        entries2.add(new Entry(6f, 78f));

        data.add(entries1);
        data.add(entries2);

        return data;
    }

}
