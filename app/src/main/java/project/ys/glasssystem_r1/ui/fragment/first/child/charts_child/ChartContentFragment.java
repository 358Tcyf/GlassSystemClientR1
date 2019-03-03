package project.ys.glasssystem_r1.ui.fragment.first.child.charts_child;

import android.os.Bundle;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;


import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.mvp.contract.MemberContract;
import project.ys.glasssystem_r1.ui.fragment.first.child.charts_child.charts.BarChartFragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.charts_child.charts.LineChartFragment;

@EFragment(R.layout.fragment_content_main)
public class ChartContentFragment extends SupportFragment {

    private static final String ARG_MENU = "arg_menu";
    private String menuTitle;
    private MenuItem menuItem;

    public static ChartContentFragment newInstance() {
        return new ChartContentFragment_();
    }

    public static ChartContentFragment newInstance(MenuItem menuItem) {
        Bundle args = new Bundle();
        args.putString(ARG_MENU, menuItem.getTitle().toString());
        ChartContentFragment fragment = new ChartContentFragment_();
        fragment.setArguments(args);
        return fragment;
    }


    @AfterInject
    void afterInject() {
        Bundle args = getArguments();
        if (args != null) {
            menuTitle = args.getString(ARG_MENU);
        }
    }

    @AfterViews
    void afterViews() {
        showCharts(menuTitle);
        Logger.d(menuTitle);
    }


    private void showCharts(String menuTitle) {
        SupportFragment fragment;
        if (menuTitle.length() % 2 == 1) {
            fragment = LineChartFragment.newInstance();
        } else {
            fragment = BarChartFragment.newInstance();
        }
        switchContentFragment(fragment);

    }

    private void switchContentFragment(SupportFragment fragment) {
        loadRootFragment(R.id.fl_content_container, fragment);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }

}
