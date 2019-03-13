package project.ys.glasssystem_r1.ui.fragment.first.child.child;

import android.os.Bundle;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.SubMenuSelectedEvent;
import project.ys.glasssystem_r1.data.entity.BaseChart;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.CommonMoreBarChart;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.CommonOnlyBarChart;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.CommonPieChart;

import static project.ys.glasssystem_r1.data.entity.BaseChart.bar_chart;
import static project.ys.glasssystem_r1.data.entity.BaseChart.line_chart;
import static project.ys.glasssystem_r1.data.entity.BaseChart.pie_chart;
import static project.ys.glasssystem_r1.data.entity.BaseChart.ring_chart;

@EFragment(R.layout.fragment_content_main)
public class ChartContentFragment extends BaseBackFragment {

    public static ChartContentFragment newInstance() {
        Bundle args = new Bundle();
        ChartContentFragment fragment = new ChartContentFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String ARG_MENU = "arg_menu";
    private HashMap<Integer, BaseBackFragment> mFragments;
    private HashMap<BaseChart, BaseBackFragment> fragments;
    //    private int position;
//    private int prePosition;
    private BaseChart mBaseChart;
    private BaseChart preSBaseChart;


    public static ChartContentFragment newInstance(int itemId) {
        Bundle args = new Bundle();
        args.putInt(ARG_MENU, itemId);
        ChartContentFragment fragment = new ChartContentFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    public static ChartContentFragment newInstance(BaseChart baseChart) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_MENU, baseChart);
        ChartContentFragment fragment = new ChartContentFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    @AfterInject
    void afterInject() {
        Bundle args = getArguments();
        if (args != null) {
//            position = args.getInt(ARG_MENU);
            mBaseChart = args.getParcelable(ARG_MENU);
        }

        EventBusActivityScope.getDefault(_mActivity).register(this);
    }

    @AfterViews
    void afterViews() {
        mFragments = new HashMap<>();
        fragments = new HashMap<>();
        initContentFragment();
    }

    @UiThread
    void initContentFragment() {
//        BaseBackFragment fragment = chartsContent(position);
        BaseBackFragment fragment = chartsContent(mBaseChart);
        fragments.put(mBaseChart, fragment);
//        mFragments.put(position, fragment);
        loadRootFragment(R.id.fl_content_container, fragment);
    }



    private BaseBackFragment chartsContent(BaseChart baseChart) {
        BaseBackFragment fragment = null;
        switch (baseChart.getChart_type()) {
            case line_chart:
                break;
            case bar_chart:
                if (baseChart.isOnly())
                    fragment = CommonOnlyBarChart.newInstance(baseChart);
                else
                    fragment = CommonMoreBarChart.newInstance(baseChart);
                break;
            case pie_chart:
                fragment = CommonPieChart.newInstance(baseChart);
                break;
            case ring_chart:
                fragment = CommonPieChart.newInstance(baseChart);
                break;
        }
        return fragment;
    }


//    @Subscribe
//    public void onMenuSelectedEvent(MenuSelectedEvent event) {
//        prePosition = position;
//        position = event.position;
//        switchContentFragment();
//    }

    @Subscribe
    public void onSubMenuSelectedEvent(SubMenuSelectedEvent event) {
        mBaseChart = event.chart;
        switchChartsFragment();
    }

//    private void switchContentFragment() {
//        if (position == prePosition) {
//            return;
//        }
//        if (mFragments.get(position) != null) {
//            showHideFragment(mFragments.get(position), mFragments.get(prePosition));
//            loadRootFragment(R.id.fl_content_container, mFragments.get(position));
//        } else {
//            BaseBackFragment fragment = chartsContent(position);
//            mFragments.put(position, fragment);
//            loadRootFragment(R.id.fl_content_container, fragment);
//        }
//    }


    private void switchChartsFragment() {
        if (mBaseChart.equals(preSBaseChart)) {
            return;
        }
        if (mFragments.get(mBaseChart) != null) {
            showHideFragment(mFragments.get(mBaseChart), mFragments.get(preSBaseChart));
            loadRootFragment(R.id.fl_content_container, mFragments.get(mBaseChart));
        } else {
            BaseBackFragment fragment = chartsContent(mBaseChart);
            fragments.put(mBaseChart, fragment);
            loadRootFragment(R.id.fl_content_container, fragment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }
}
