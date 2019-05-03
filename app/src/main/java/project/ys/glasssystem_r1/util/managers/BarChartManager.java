package project.ys.glasssystem_r1.util.managers;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.R;


public class BarChartManager {
    private BarChart mBarChart;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xAxis;
    private DecimalFormat mFormat;
    private Legend legend;

    public BarChartManager(BarChart barChart) {
        this.mBarChart = barChart;
        leftAxis = mBarChart.getAxisLeft();
        rightAxis = mBarChart.getAxisRight();
        xAxis = mBarChart.getXAxis();
        legend = mBarChart.getLegend();
    }

    /**
     * 初始化LineChart
     */
    private void initChart() {
        mFormat = new DecimalFormat("#,###.##");
        mBarChart.setTouchEnabled(true); // 所有触摸事件,默认true
        mBarChart.setDragEnabled(true);    // 可拖动,默认true
        mBarChart.setScaleEnabled(false);   // 两个轴上的缩放,X,Y分别默认为true
        mBarChart.setScaleXEnabled(false);  // X轴上的缩放,默认true
        mBarChart.setScaleYEnabled(false);  // Y轴上的缩放,默认true
        mBarChart.setPinchZoom(false);  // X,Y轴同时缩放，false则X,Y轴单独缩放,默认false
        mBarChart.setDoubleTapToZoomEnabled(false); // 双击缩放,默认true
        mBarChart.setDragDecelerationEnabled(true);    // 抬起手指，继续滑动,默认true
        //不显示边界
        mBarChart.setDrawBorders(false);
        //设置XY动画效果
        mBarChart.animateY(1000, Easing.EasingOption.Linear);
        mBarChart.animateX(1000, Easing.EasingOption.Linear);
        //不显示描述信息
        mBarChart.getDescription().setEnabled(false);

        //图例设置
        legend.setForm(Legend.LegendForm.CIRCLE);
        //图例文字的大小
        legend.setFormSize(14f);
        legend.setTextSize(12f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        legend.setXEntrySpace(7f);//x轴的间距
        legend.setTextColor(Color.parseColor("#a1a1a1")); //图例文字的颜色
        legend.setTextSize(14);  //图例文字的大小

    }

    private void initAxis() {
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴设置显示位置在底部
        xAxis.setGranularity(1f);//X轴最小间距
        xAxis.setAxisLineWidth(1f);
        xAxis.setDrawGridLines(false);//不绘制网格线
        xAxis.setTypeface(Typeface.DEFAULT_BOLD);//X轴字体样式
        xAxis.setTextSize(14f);
        xAxis.setTextColor(Color.parseColor("#212121"));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//XAxis显示的位置
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setEnabled(false); //右侧Y轴不显示
        //设置Y轴的最小值和最大值
        leftAxis.setAxisLineWidth(1f);
        leftAxis.setDrawGridLines(true);//绘制Y轴的网格线
        leftAxis.setTextSize(14f);
        leftAxis.setTextColor(Color.parseColor("#757575"));
        leftAxis.setTypeface(Typeface.DEFAULT);//X轴字体样式
    }

    /**
     * 展示柱状图(一条)
     */
    public void showBarChart(List<BarEntry> entries, String label, String[] xValues, int color, boolean center) {
        initChart();
        initAxis();
        if (center)
            xAxis.setCenterAxisLabels(true);//设置X轴文字剧中对齐
        // 每一个BarDataSet代表一类柱状图
        BarDataSet barDataSet = new BarDataSet(entries, label);
        barDataSet.setColor(color);
        //文字的大小
        barDataSet.setValueTextSize(10f);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        BarData data = new BarData(dataSets);
        //设置宽度
        data.setBarWidth(0.5f);
        //设置X轴的刻度数
        xAxis.setLabelCount(entries.size() + 1, true);
        xAxis.setDrawLabels(true);
        IAxisValueFormatter xAxisFormatter = new XAxisValueFormatter(xValues);
        xAxis.setValueFormatter(xAxisFormatter);
        mBarChart.setData(data);
    }


    /**
     * 展示柱状图(一条多组)
     */
    public void showListBarChart(List<List<BarEntry>> entries, List<String> labels, String[] xValues, List<Integer> colours, boolean center) {
        initChart();
        initAxis();
        if (center)
            xAxis.setCenterAxisLabels(true);//设置X轴文字剧中对齐
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            BarDataSet barDataSet = new BarDataSet(entries.get(i), labels.get(i));
            barDataSet.setColor(colours.get(i));
            barDataSet.setValueTextSize(10f);
            dataSets.add(barDataSet);
        }
        BarData data = new BarData(dataSets);
        //设置宽度
        data.setBarWidth(0.5f);
        //设置X轴的刻度数
        xAxis.setLabelCount(xValues.length
                , true);
        xAxis.setDrawLabels(true);
        IAxisValueFormatter xAxisFormatter = new XAxisValueFormatter(xValues);
        xAxis.setValueFormatter(xAxisFormatter);
        mBarChart.setData(data);
    }


    /**
     * 展示柱状图(多条)
     */
    public void showMoreBarChart(List<List<BarEntry>> listEntries, List<String> labels, String[] xValues, List<Integer> colours) {
        initChart();
        initAxis();
        xAxis.setCenterAxisLabels(true);//设置X轴文字剧中对齐
        for (String str : xValues) {
            if (str.length() >= 6)
                xAxis.setTextSize(10f);
        }
        BarData data = new BarData();
        for (int i = 0; i < listEntries.size(); i++) {
            List<BarEntry> entries = listEntries.get(i);
            BarDataSet barDataSet = new BarDataSet(entries, labels.get(i));
            barDataSet.setColor(colours.get(i));
            barDataSet.setValueTextColor(colours.get(i));
            barDataSet.setValueTextSize(10f);
            barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(barDataSet);
        }
        int amount = listEntries.size();

        float groupSpace = 0.3f; //设置柱状图组之间的间距
        float barSpace = (float) ((1 - 0.12) / amount / 10);
        float barWidth = (float) ((1 - 0.3) / amount / 10 * 9);
        xAxis.setLabelCount(labels.size(), false);
        data.setBarWidth(barWidth);
        xAxis.setValueFormatter((value, axis) -> { //设置横轴
            for (int i = 0; i < xValues.length; i++) {
                if (value == i) {
                    return xValues[i];
                }
            }
            return "";
        });
        leftAxis.setValueFormatter((value, axis) -> mFormat.format(value) + "%"); //设置纵轴
        data.groupBars(0, groupSpace, barSpace);
        mBarChart.setData(data);
    }

    public class YAxisValueFormatter implements IAxisValueFormatter {

        private String[] yValues;

        public YAxisValueFormatter(String[] yValues) {
            this.yValues = yValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format(value) + "%";
        }
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
        mBarChart.invalidate();
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
        xAxis.setLabelCount(labelCount, false);
        mBarChart.invalidate();
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
        mBarChart.invalidate();
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
        mBarChart.invalidate();
    }

    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setDescription(String str) {
        RelativeLayout relativeLayout = (RelativeLayout) mBarChart.getParent();
        TextView description = relativeLayout.findViewById(R.id.desc);
        description.setText(str);
    }


}
