package project.ys.glasssystem_r1.ui.fragment.first.child.charts_child.charts;

import android.graphics.Paint;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
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

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;

@EFragment(R.layout.fragment_chart_pie)
public class PieChartFragment extends SupportFragment {

    public static PieChartFragment newInstance() {
        return new PieChartFragment_();
    }

    @ViewById(R.id.pie_chart)
    PieChart mPieChart;

    @AfterViews
    void afterViews() {
        initChart();
        initAxis();
        setData();
    }

    private void initChart() {
        /*1.chart格式设置*/

    }

    private void initAxis() {
        /*2.获取坐标轴并进行设置*/

    }

    private void setData() {
        /*3.添加数据*/

    }


}
