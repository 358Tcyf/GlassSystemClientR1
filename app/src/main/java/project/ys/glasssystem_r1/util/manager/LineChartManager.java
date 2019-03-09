package project.ys.glasssystem_r1.util.manager;

import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.R;


public class LineChartManager {

    private LineChart mLineChart;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xAxis;
    private Legend legend;


    public LineChartManager(LineChart mLineChart) {
        this.mLineChart = mLineChart;
        leftAxis = this.mLineChart.getAxisLeft();
        rightAxis = this.mLineChart.getAxisRight();
        xAxis = this.mLineChart.getXAxis();
        legend = this.mLineChart.getLegend();
    }

    /**
     * 初始化LineChart
     */
    private void initChart() {
        mLineChart.setDrawGridBackground(false);
        //显示边界
        mLineChart.setDrawBorders(false);
        //不显示描述信息
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setTouchEnabled(true); // 所有触摸事件,默认true
        mLineChart.setDragEnabled(false);    // 可拖动,默认true
        mLineChart.setScaleEnabled(false);   // 两个轴上的缩放,X,Y分别默认为true
        mLineChart.setScaleXEnabled(false);  // X轴上的缩放,默认true
        mLineChart.setScaleYEnabled(false);  // Y轴上的缩放,默认true
        mLineChart.setPinchZoom(false);  // X,Y轴同时缩放，false则X,Y轴单独缩放,默认false
        mLineChart.setDoubleTapToZoomEnabled(false); // 双击缩放,默认true
        mLineChart.setDragDecelerationEnabled(true);    // 抬起手指，继续滑动,默认true

        //图例设置
        legend.setForm(Legend.LegendForm.CIRCLE);
        //图例文字的大小
        legend.setFormSize(12f);
        legend.setTextSize(12f);
        //图例文字的颜色
        legend.setTextColor(Color.parseColor("#a1a1a1"));

        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);


        //XY轴的设置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        leftAxis.setAxisMinimum(0f);

        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);
        //设置网格线的颜色
        xAxis.setGridColor(Color.parseColor("#B3E5FC"));
        leftAxis.setGridColor(Color.parseColor("#B3E5FC"));
        //设置最后和第一个标签不超出x轴
        xAxis.setAvoidFirstLastClipping(true);
        //设置坐标轴宽度
        xAxis.setAxisLineWidth(1.0f);
        leftAxis.setAxisLineWidth(1.0f);
        //设置坐标轴颜色
        xAxis.setAxisLineColor(Color.parseColor("#d5d5d5"));
        leftAxis.setAxisLineColor(Color.parseColor("#d5d5d5"));
        //设置坐标轴字体颜色
        xAxis.setTextColor(Color.parseColor("#d5d5d5"));
        leftAxis.setTextColor(Color.parseColor("#d5d5d5"));
        //设置网格为虚线
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        // 线跟数据都不显示
        rightAxis.setEnabled(false); //右侧Y轴不显示


    }

    /**
     * 初始化曲线 每一个LineDataSet代表一条线
     *
     * @param lineDataSet
     * @param color
     * @param mode        折线图是否填充
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, boolean mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setValueTextSize(9f);
        // 显示具体值
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextColor(color);
        lineDataSet.setValueTextSize(14f);
        lineDataSet.setHighlightEnabled(true);
        //设置折线图填充
        lineDataSet.setDrawFilled(mode);
        lineDataSet.setFillColor(color);
        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
    }


    /**
     * 展示折线图(一条)
     *
     * @param label
     * @param xValues
     * @param color
     */
    public void showLineChart(List<Entry> entries, String label, String[] xValues, int color) {
        initChart();
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, label);
        initLineDataSet(lineDataSet, color, true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        xAxis.setLabelCount(entries.size() + 1, true);
        xAxis.setDrawLabels(true);
        IAxisValueFormatter xAxisFormatter = new XAxisValueFormatter(xValues);
        xAxis.setValueFormatter(xAxisFormatter);
        mLineChart.setData(data);

    }

    /**
     * 展示线性图(多条)
     *
     * @param data
     * @param labels
     * @param xValues
     * @param colours
     */
    public void showMoreLineChart(List<List<Entry>> data, List<String> labels, String[] xValues, List<Integer> colours) {
        initChart();
        /*LineDataSet是点的集合，连成线*/
        List<ILineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < labels.size(); i++) {
            LineDataSet lineDataSet = new LineDataSet(data.get(i), labels.get(i));
            initLineDataSet(lineDataSet, colours.get(i), false);
            dataSets.add(lineDataSet);
        }
        /*要给Chart设置的数据（将dataSets作为数据对象）*/
        LineData lineData = new LineData(dataSets);
        IAxisValueFormatter xAxisFormatter = new XAxisValueFormatter(xValues);
        xAxis.setValueFormatter(xAxisFormatter);
        mLineChart.setData(lineData);//设置数据
        mLineChart.invalidate();//刷新显示


    }

    public class XAxisValueFormatter implements IAxisValueFormatter {

        private String[] xValues;

        public XAxisValueFormatter(String[] xValues) {
            this.xValues = xValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return xValues[(int) value];
        }

    }


    /**
     * 设置Y轴值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setYAxis(float max, float min, int labelCount) {
        if (max < min) {
            return;
        }
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(min);
        leftAxis.setLabelCount(labelCount, false);

        rightAxis.setAxisMaximum(max);
        rightAxis.setAxisMinimum(min);
        rightAxis.setLabelCount(labelCount, false);
        mLineChart.invalidate();
    }

    /**
     * 设置X轴的值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setXAxis(float max, float min, int labelCount) {
        xAxis.setAxisMaximum(max);
        xAxis.setAxisMinimum(min);
        xAxis.setLabelCount(labelCount, true);

        mLineChart.invalidate();
    }

    /**
     * 设置高限制线
     *
     * @param high
     * @param name
     */
    public void setHighLimitLine(float high, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        LimitLine hightLimit = new LimitLine(high, name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(10f);
        hightLimit.setLineColor(color);
        hightLimit.setTextColor(color);
        leftAxis.addLimitLine(hightLimit);
        mLineChart.invalidate();
    }

    /**
     * 设置低限制线
     *
     * @param low
     * @param name
     */
    public void setLowLimitLine(int low, String name) {
        if (name == null) {
            name = "低限制线";
        }
        LimitLine hightLimit = new LimitLine(low, name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(10f);
        leftAxis.addLimitLine(hightLimit);
        mLineChart.invalidate();
    }

    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setDescription(String str) {
        RelativeLayout relativeLayout = (RelativeLayout) mLineChart.getParent();
        TextView description = relativeLayout.findViewById(R.id.description);
        description.setText(str);
    }
}