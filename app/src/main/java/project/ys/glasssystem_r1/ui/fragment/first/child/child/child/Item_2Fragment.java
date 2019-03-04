package project.ys.glasssystem_r1.ui.fragment.first.child.child.child;

import com.github.mikephil.charting.charts.PieChart;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;

@EFragment(R.layout.fragment_chart_pie)
public class Item_2Fragment extends BaseBackFragment {

    public static Item_2Fragment newInstance() {
        return new Item_2Fragment_();
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
