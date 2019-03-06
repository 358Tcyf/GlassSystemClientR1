package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.ColorStateListRes;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.util.PieChartManager;

@EFragment(R.layout.fragment_chart_pie)
public class Item_2Fragment extends BaseBackFragment {

    public static Item_2Fragment newInstance() {
        return new Item_2Fragment_();
    }

    @ViewById(R.id.pie_chart)
    PieChart mPieChart;

    final String[] xValues = {"产品1", "产品2", "产品3", "产品4"};

    @AfterViews
    void afterViews() {
        showPieChart();
    }

    private void showPieChart() {
        PieChartManager pieChartManager = new PieChartManager(mPieChart);

        //设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(_mActivity.getColor(R.color.colorPrimaryDark));
        colors.add(_mActivity.getColor(R.color.color1));
        colors.add(_mActivity.getColor(R.color.color2));
        colors.add(_mActivity.getColor(R.color.colorPrimaryLight));

        pieChartManager.showSolidPieChart(setData(), "生产数", colors);
        pieChartManager.setDescription("各型号生产统计");
        pieChartManager.setSubDescription("各型号玻璃生产数量");



    }


    private List<PieEntry> setData() {
        List<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(254, xValues[0]));
        data.add(new PieEntry(167, xValues[1]));
        data.add(new PieEntry(243, xValues[2]));
        data.add(new PieEntry(700, xValues[3]));
        return data;
    }

}
