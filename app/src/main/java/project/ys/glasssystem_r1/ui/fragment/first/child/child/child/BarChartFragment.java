package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import android.graphics.Paint;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
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
import org.androidannotations.annotations.res.ColorRes;

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
        //图表描述
        Description description = new Description();
        description.setText("近一周学习情况");//描述内容
        description.setTextColor(0xff000000);//描述字体颜色
        description.setTextSize(24f);//描述字体大小
        description.setTextAlign(Paint.Align.LEFT);//文字左对齐
        description.setPosition(100, 100);//设置图表描述
        mBarChart.setDescription(description);

        mBarChart.setTouchEnabled(false);//允许启用/禁用与图表的所有可能的触摸交互。
        mBarChart.setDragEnabled(false);//启用/禁用图表的拖动（平移）。
        mBarChart.setScaleEnabled(false);//启用/禁用两个轴上的图表缩放
//        mBarChart.setScaleXEnabled(true);//启用/禁用x轴缩放。
//        mBarChart.setScaleYEnabled(true);//启用/禁用y轴缩放。
//        mBarChart.setPinchZoom(true);//如果设置为true，则启用缩放缩放。如果禁用，则可以单独缩放x轴和y轴。
//        mBarChart.setDoubleTapToZoomEnabled(true);//将此设置为false以禁止通过双击来缩放图表
    }

    @ColorRes(R.color.colorPrimary)
    int colorPrimary;
    @ColorRes(R.color.color1)
    int color1;
    @ColorRes(R.color.color2)
    int color2;

    private void initAxis() {
        /*2.获取坐标轴并进行设置*/
        //获取并设置Y轴
        YAxis leftYAxis = mBarChart.getAxisLeft();//获取左侧Y轴
        YAxis rightYAxis = mBarChart.getAxisRight();//获取右侧Y轴
        /*
         * 控制应绘制哪些部分（轴）
         * */
        rightYAxis.setEnabled(false);//禁止显示右侧Y轴
        leftYAxis.setDrawLabels(true);//将其设置为true以启用绘制轴标签。
        leftYAxis.setDrawAxisLine(true);//如果应绘制沿轴（轴线）的线，则将此设置为true。
        leftYAxis.setDrawGridLines(true);//将此属性设置为true以启用绘制轴的网格线。
//        leftYAxis.setGridColor(colorPrimary);//设置此轴的网格线的颜色。
//        leftYAxis.setGridLineWidth(2);//设置此轴的网格线的宽度。
        /*
         * 自定义轴范围（最小/最大）
         * */
        leftYAxis.setAxisMaximum(100);//为此轴设置自定义最大值。如果设置，则不会根据提供的数据自动计算该值。
        leftYAxis.setAxisMinimum(10);//为此轴设置自定义最小值。如果设置，则不会根据提供的数据自动计算该值。
//        leftYAxis.setInverted(true);//如果设置为true，则此轴将被反转，这意味着最高值将位于底部，最低值将位于顶部。
//        leftYAxis.setSpaceTop(10);//设置图表中最高值的顶部间距（以总轴 - 范围的百分比表示）与轴上的最高值进行比较。
//        leftYAxis.setSpaceBottom(10);//设置图表中最低值的底部间距（以总轴 - 范围的百分比表示）与轴上的最低值进行比较。
        leftYAxis.setLabelCount(10, false);//设置y轴的标签数。请注意，此数字不固定（如果强制== false），并且只能近似。如果强制启用（true），则绘制精确指定的标签计数 - 这可能导致轴上的数字不均匀。
//        leftYAxis.setGranularity(1);//设置y轴值之间的最小间隔。这可用于避免在放大到为轴设置的小数位数不再允许区分两个轴值的点时重复值。
//        leftYAxis.setGranularityEnabled(true);//启用粒度特征，在放大时限制y轴的间隔。
        /*
         * 造型/修改轴
         * */
        leftYAxis.setAxisLineWidth(1);
        /*
         * 零线
         * */
//        leftYAxis.setDrawZeroLine(true);//启用/禁用绘制零线。
//        leftYAxis.setZeroLineWidth(5);//设置零线的线宽。
//        leftYAxis.setZeroLineColor(colorPrimary);//设置零线应具有的颜色。
        LimitLine yLL = new LimitLine(90f);
        leftYAxis.addLimitLine(yLL);
        leftYAxis.setDrawLimitLinesBehindData(false);//允许控制LimitLines和实际数据之间的z顺序。如果将其设置为true，LimitLines则会在实际数据后面绘制，否则将在顶部绘制。
        //获取和设置X轴
        XAxis xAxis = mBarChart.getXAxis();//获取X轴
        /*
         * 控制应绘制哪些部分（轴）
         * */
        xAxis.setEnabled(false);//将轴设置为启用或禁用。如果禁用，则无论其他任何设置如何，都不会绘制轴的任何部分。
        xAxis.setDrawLabels(false);//将其设置为true以启用绘制轴标签。
        xAxis.setDrawAxisLine(false);//如果应绘制沿轴（轴线）的线，则将此设置为true。
        xAxis.setDrawGridLines(false);//将此属性设置为true以启用绘制轴的网格线。
        /*
         * 自定义轴范围（最小/最大）
         * */
//        xAxis.setAxisMaximum(24);//为此轴设置自定义最大值。如果设置，则不会根据提供的数据自动计算该值。
//        xAxis.setAxisMinimum(0);//为此轴设置自定义最小值。如果设置，则不会根据提供的数据自动计算该值。
        xAxis.setLabelCount(6, false);//设置x轴的标签数。请注意，此数字不固定（如果强制== false），并且只能近似。如果强制启用（true），则绘制精确指定的标签计数 - 这可能导致轴上的数字不均匀。
//        xAxis.setGranularity(1);//设置x轴值之间的最小间隔。这可用于避免在放大到为轴设置的小数位数不再允许区分两个轴值的点时重复值。
//        xAxis.setGranularityEnabled(true);//启用粒度特征，在放大时限制xy轴的间隔。
        /*
         * 造型/修改轴
         * */
//        xAxis.setTextColor(colorPrimary);//设置轴标签的颜色。
        xAxis.setTextSize(14);//以dp为单位设置轴标签的文本大小。
//        xAxis.setTypeface(new Typeface());//Typeface为轴标签设置自定义。
//        xAxis.setGridColor(colorPrimary);//设置此轴的网格线的颜色。
//        xAxis.setGridLineWidth(2);//设置此轴的网格线的宽度。
//        xAxis.setAxisLineColor(colorPrimary);//设置此轴的轴线颜色。
//        xAxis.setAxisLineWidth(2);//设置此轴的轴线宽度。
//        xAxis.enableGridDashedLine(1,1,0);
        /*
         * 允许以虚线模式绘制网格线，例如像“ - - - - - - ”。
         * “lineLength”控制线条的长度，
         * “spaceLength”控制线条之间的空间，
         * “phase”控制起点。
         * */
        /*
         * 自定义轴值
         * */
//        xAxis.setLabelRotationAngle(10);//设置绘制x轴标签的角度（以度为单位）。
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置XAxis应显示的位置。选择TOP，BOTTOM，BOTH_SIDED，TOP_INSIDE或BOTTOM_INSIDE。
//        xAxis.setCenterAxisLabels(true);

        /*X轴数据*/
//        final String[] xValues = {"0", "4", "8", "12", "16", "20", "24"};
//        /*给X轴设置数据*/
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return xValues[(int) value];
//            }
//        });
    }

    private void setData() {
        /*3.添加数据*/


        ArrayList<BarEntry> entries1 = new ArrayList<>();
//        entries1.add(new BarEntry(0, 85));
        entries1.add(new BarEntry(0, 69));
        entries1.add(new BarEntry(1, 88));
        entries1.add(new BarEntry(2, 75));
        entries1.add(new BarEntry(3, 69));
        entries1.add(new BarEntry(4, 95));
        entries1.add(new BarEntry(5, 77));
        entries1.add(new BarEntry(6, 88));

        /*LineDataSet是点的集合，连成线*/
        BarDataSet barDataSet1 = new BarDataSet(entries1, "Group 1");
        barDataSet1.setColor(color1);
        barDataSet1.setValueTextColor(color1);
        barDataSet1.setValueTextSize(10);

        /*要给Chart设置的数据（将dataSets作为数据对象）*/
        BarData lineData = new BarData(barDataSet1);
        lineData.setBarWidth(0.9f); // set custom bar width
        mBarChart.setData(lineData);//设置数据
        mBarChart.setFitBars(true); // make the x-axis fit exactly all bars
        mBarChart.invalidate();//刷新显示
    }


}
