package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.util.manager.PieChartManager;

@EFragment(R.layout.fragment_chart_pie)
public class Item_7Fragment extends BaseBackFragment {

    public static Item_7Fragment newInstance() {
        return new Item_7Fragment_();
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
        pieChartManager.setDescription("各型号销售统计");

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
