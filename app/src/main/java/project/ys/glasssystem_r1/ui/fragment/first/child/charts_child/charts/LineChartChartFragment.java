package project.ys.glasssystem_r1.ui.fragment.first.child.charts_child.charts;

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

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;

@EFragment(R.layout.fragment_piechart)
public class LineChartChartFragment extends SupportFragment {

    public static LineChartChartFragment newInstance() {
        return new LineChartChartFragment_();
    }

    @ViewById(R.id.pie_chart)
    LineChart mLineChart;

    @AfterViews
    void afterViews() {
        initChart();
        initAxis();
        setData();
    }

    private void initChart() {
        /*1.chart格式设置*/
        mLineChart.setDrawGridBackground(false);//无背景网格
        mLineChart.setDrawBorders(false);

        //图表描述
        Description description = new Description();
        description.setText("近一周学习情况");//描述内容
        description.setTextColor(0xff000000);//描述字体颜色
        description.setTextSize(24f);//描述字体大小
        description.setTextAlign(Paint.Align.LEFT);//文字左对齐
        description.setPosition(100, 100);//设置图表描述
        mLineChart.setDescription(description);

        mLineChart.setTouchEnabled(false);//可触摸
        mLineChart.setDragEnabled(true);//可拖动
        mLineChart.setScaleEnabled(true);//可放缩
    }

    private void initAxis() {
        /*2.获取坐标轴并进行设置*/
        //获取和设置X轴
        XAxis xAxis = mLineChart.getXAxis();//获取X轴
        xAxis.setEnabled(true);//设置显示X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴位置
        xAxis.setAxisLineWidth(2);//设置X轴宽度
        xAxis.setDrawGridLines(false);//无网格
        xAxis.setDrawAxisLine(true);//显示X轴
        /*X轴数据*/
        final String[] xValues = {"3.14", "3.15", "3.16", "3.17", "3.18", "3.19", "3.20"};
        /*给X轴设置数据*/
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValues[(int) value];
            }
        });


        //获取并设置Y轴
        YAxis leftYAxis = mLineChart.getAxisLeft();//获取左侧Y轴
        YAxis rightYAxis = mLineChart.getAxisRight();//获取右侧Y轴
        rightYAxis.setEnabled(false);//禁止显示右侧Y轴
        leftYAxis.setAxisLineWidth(2);
        leftYAxis.setDrawGridLines(false);
        /*leftYAxis.setStartAtZero(true);//设置从零开始显示*/
    }

    private void setData() {
        /*3.添加数据*/
        ArrayList<Entry> entries1 = new ArrayList<>();//Entry就是折线图上的点
        entries1.add(new Entry(0, 85));
        entries1.add(new Entry(1, 88));
        entries1.add(new Entry(2, 75));
        entries1.add(new Entry(3, 69));
        entries1.add(new Entry(4, 95));
        entries1.add(new Entry(5, 77));
        entries1.add(new Entry(6, 88));

        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(0, 75));
        entries2.add(new Entry(1, 88));
        entries2.add(new Entry(2, 55));
        entries2.add(new Entry(3, 79));
        entries2.add(new Entry(4, 85));
        entries2.add(new Entry(5, 97));
        entries2.add(new Entry(6, 78));

        /*LineDataSet是点的集合，连成线*/
        LineDataSet lineDataSet1 = new LineDataSet(entries1, "Listen");
        lineDataSet1.setColor(0xff3f51b5);
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "speech");
        lineDataSet2.setColor(0xffff4081);

        /*线条的集合（可以添加多条线）*/
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);

        /*要给Chart设置的数据（将dataSets作为数据对象）*/
        LineData lineData = new LineData(dataSets);

        mLineChart.setData(lineData);//设置数据
        mLineChart.invalidate();//刷新显示
    }


}
