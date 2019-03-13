package project.ys.glasssystem_r1.util.manager;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

import project.ys.glasssystem_r1.R;

public class PieChartManager {

    private PieChart mPieChart;
    private Legend legend;


    public PieChartManager(PieChart pieChart) {
        this.mPieChart = pieChart;
        legend = mPieChart.getLegend();
    }

    //初始化
    private void initHalfPieChart() {
        mPieChart.setTouchEnabled(true); // 所有触摸事件,默认true
        mPieChart.setDrawHoleEnabled(false);//是否显示中间的洞

        mPieChart.setRotationAngle(0);// 初始旋转角度
        mPieChart.setRotationEnabled(true);// 可以手动旋转
//        mPieChart.setRotationAngle(180f);// 初始旋转角度
//        mPieChart.setRotationEnabled(false);// 可以手动旋转

        mPieChart.setUsePercentValues(false);//显示成百分比
        mPieChart.getDescription().setEnabled(false); //取消右下角描述

        //是否显示每个部分的文字描述
        mPieChart.setDrawEntryLabels(true);
        mPieChart.setEntryLabelColor(Color.BLACK); //描述文字的颜色
        mPieChart.setEntryLabelTextSize(14);//描述文字的大小
        mPieChart.setEntryLabelTypeface(Typeface.DEFAULT); //描述文字的样式


        //图相对于上下左右的偏移
        mPieChart.setExtraOffsets(10f, 0, 20f, 0);
        //图标的背景色
        mPieChart.setBackgroundColor(Color.TRANSPARENT);
//        设置pieChart图表转动阻力摩擦系数[0,1]
        mPieChart.setDragDecelerationFrictionCoef(0.75f);
//        mPieChart.setMaxAngle(180f);

        //图例设置
        legend.setForm(Legend.LegendForm.CIRCLE);
        //图例文字的大小
        legend.setFormSize(14f);
        legend.setTextSize(12f);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);  //设置图例水平显示
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); //顶部
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); //右对齐

        legend.setXEntrySpace(10f);//x轴的间距
        legend.setYEntrySpace(6f); //y轴的间距
        legend.setYOffset(10f);  //图例的y偏移量
        legend.setXOffset(6f);  //图例x的偏移量
        legend.setTextColor(Color.parseColor("#a1a1a1")); //图例文字的颜色
        legend.setTextSize(13);  //图例文字的大小

    }


    /**
     * 显示实心圆
     *
     * @param entry
     * @param label
     * @param colors
     */
    public void showSolidPieChart(List<PieEntry> entry, String label, List<Integer> colors) {
        initHalfPieChart();
        //数据集合
        PieDataSet dataSet = new PieDataSet(entry, label);
        //填充每个区域的颜色
        dataSet.setColors(colors);
        //是否在图上显示数值
        dataSet.setDrawValues(true);
        //文字的大小
        dataSet.setValueTextSize(14);
        //文字的颜色
        dataSet.setValueTextColor(Color.BLACK);
        //文字的样式
        dataSet.setValueTypeface(Typeface.DEFAULT);
        //设置Y值的位置是在圆内还是圆外
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        //设置饼状Item被选中时变化的距离
        dataSet.setSelectionShift(10f);
        //填充数据
        PieData pieData = new PieData(dataSet);
        //格式化显示的数据为%百分比
//        pieData.setValueFormatter(new MyValueFormatter());
        //显示视图
        mPieChart.setData(pieData);
    }


    //初始化
    private void initRingChart() {
        mPieChart.setTouchEnabled(true); // 所有触摸事件,默认true

        mPieChart.setRotationAngle(0);// 初始旋转角度
        mPieChart.setRotationEnabled(true);// 可以手动旋转

        mPieChart.setUsePercentValues(false);//显示成百分比
        mPieChart.getDescription().setEnabled(false); //取消右下角描述

        //是否显示每个部分的文字描述
        mPieChart.setDrawEntryLabels(true);
        mPieChart.setEntryLabelColor(Color.WHITE); //描述文字的颜色
        mPieChart.setEntryLabelTextSize(14);//描述文字的大小
        mPieChart.setEntryLabelTypeface(Typeface.DEFAULT); //描述文字的样式

        //图相对于上下左右的偏移
        mPieChart.setExtraOffsets(10f, 0, 20f, 0);
        //图标的背景色
        mPieChart.setBackgroundColor(Color.TRANSPARENT);
//        设置pieChart图表转动阻力摩擦系数[0,1]
        mPieChart.setDragDecelerationFrictionCoef(0.75f);

        //图例设置
        legend.setForm(Legend.LegendForm.CIRCLE);
        //图例文字的大小
        legend.setFormSize(14f);
        legend.setTextSize(12f);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);  //设置图例水平显示
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); //顶部
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); //右对齐

        legend.setXEntrySpace(10f);//x轴的间距
        legend.setYEntrySpace(6f); //y轴的间距
        legend.setYOffset(10f);  //图例的y偏移量
        legend.setXOffset(6f);  //图例x的偏移量
        legend.setTextColor(Color.parseColor("#a1a1a1")); //图例文字的颜色
        legend.setTextSize(13);  //图例文字的大小

    }

    /**
     * 显示圆环
     *
     * @param entry
     * @param str
     * @param colors
     */
    public void showRingPieChart(List<PieEntry> entry, String label, String str, List<Integer> colors) {
        initRingChart();
        //显示为圆环
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleRadius(50f);//设置中间洞的大小
        //饼状图中间可以添加文字
        mPieChart.setDrawCenterText(true);
        mPieChart.setCenterText(str); //设置中间文字
        mPieChart.setCenterTextColor(Color.BLUE); //中间问题的颜色
        mPieChart.setCenterTextSize(14);  //中间文字的大小px

        //数据集合
        PieDataSet dataSet = new PieDataSet(entry, label);
        //填充每个区域的颜色
        dataSet.setColors(colors);
        //是否在图上显示数值
        dataSet.setDrawValues(true);
        //文字的大小
        dataSet.setValueTextSize(14);
        //文字的颜色
        dataSet.setValueTextColor(Color.WHITE);
        //文字的样式
        dataSet.setValueTypeface(Typeface.DEFAULT);
        //设置Y值的位置是在圆内还是圆外
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        //设置饼状Item被选中时变化的距离
        dataSet.setSelectionShift(5f);
        //填充数据
        PieData pieData = new PieData(dataSet);
        //显示视图
        mPieChart.setData(pieData);

    }


    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setDescription(String str) {
        RelativeLayout relativeLayout = (RelativeLayout) mPieChart.getParent();
        TextView description = relativeLayout.findViewById(R.id.description);
        description.setText(str);
    }
    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setSubDescription(String str) {
        //图表描述
        Description description = new Description();
        description.setText(str);//描述内容
        description.setTextColor(Color.parseColor("#212121"));//描述字体颜色
        description.setTextSize(16f);//描述字体大小
        description.setTextAlign(Paint.Align.LEFT);//文字左对齐
        description.setPosition(100, 100);//设置图表描述
        mPieChart.setDescription(description);
    }
}
