package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import android.graphics.Paint;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;

@EFragment(R.layout.fragment_chart_bar)
public class BarChartFragment extends BaseBackFragment {

    public static BarChartFragment newInstance() {
        return new BarChartFragment_();
    }

    @ViewById(R.id.bar_chart)
    BarChart mBarChart;

    @AfterViews
    void afterViews() {
        initChart();
        initAxis();
        setData();
    }

    private void initChart() {
        /*1.chart格式设置*/
        mBarChart.setDrawGridBackground(false);//无背景网格
        mBarChart.setDrawBorders(false);

        //图表描述
        Description description = new Description();
        description.setText("近一周学习情况");//描述内容
        description.setTextColor(0xff000000);//描述字体颜色
        description.setTextSize(24f);//描述字体大小
        description.setTextAlign(Paint.Align.LEFT);//文字左对齐
        description.setPosition(100, 100);//设置图表描述
        mBarChart.setDescription(description);

        mBarChart.setTouchEnabled(false);//可触摸
        mBarChart.setDragEnabled(true);//可拖动
        mBarChart.setScaleEnabled(true);//可放缩
    }

    private void initAxis() {
        /*2.获取坐标轴并进行设置*/
        //获取和设置X轴
        XAxis xAxis = mBarChart.getXAxis();//获取X轴
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
        YAxis leftYAxis = mBarChart.getAxisLeft();//获取左侧Y轴
        YAxis rightYAxis = mBarChart.getAxisRight();//获取右侧Y轴
        rightYAxis.setEnabled(false);//禁止显示右侧Y轴
        leftYAxis.setAxisLineWidth(2);
        leftYAxis.setDrawGridLines(false);
        /*leftYAxis.setStartAtZero(true);//设置从零开始显示*/
    }

    private void setData() {
        /*3.添加数据*/
        ArrayList<BarEntry> entries1 = new ArrayList<>();//Entry就是折线图上的点
        entries1.add(new BarEntry(0, 85));
        entries1.add(new BarEntry(1, 88));
        entries1.add(new BarEntry(2, 75));
        entries1.add(new BarEntry(3, 69));
        entries1.add(new BarEntry(4, 95));
        entries1.add(new BarEntry(5, 77));
        entries1.add(new BarEntry(6, 88));

        ArrayList<BarEntry> entries2 = new ArrayList<>();
        entries2.add(new BarEntry(0, 75));
        entries2.add(new BarEntry(1, 88));
        entries2.add(new BarEntry(2, 55));
        entries2.add(new BarEntry(3, 79));
        entries2.add(new BarEntry(4, 85));
        entries2.add(new BarEntry(5, 97));
        entries2.add(new BarEntry(6, 78));

        /*LineDataSet是点的集合，连成线*/
        BarDataSet barDataSet1 = new BarDataSet(entries1, "Listen");
        barDataSet1.setColor(0xff3f51b5);
        BarDataSet barDataSet2 = new BarDataSet(entries2, "speech");
        barDataSet2.setColor(0xffff4081);

        /*线条的集合（可以添加多条线）*/
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        /*要给Chart设置的数据（将dataSets作为数据对象）*/
        BarData lineData = new BarData(dataSets);

        mBarChart.setData(lineData);//设置数据
        mBarChart.invalidate();//刷新显示
    }


}
