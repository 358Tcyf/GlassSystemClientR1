package project.ys.glasssystem_r1.ui.fragment.first.child.charts_child;

import android.os.Bundle;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.first.child.charts_child.charts.LineChartChartFragment;

@EFragment(R.layout.fragment_content_main)
public class ChartContentFragment extends SupportFragment {

    private static final String ARG_MENU = "arg_menu";
    private String mMenu;

    public static ChartContentFragment newInstance() {
        return new ChartContentFragment_();
    }

    public static ChartContentFragment newInstance(String menu) {
        Bundle args = new Bundle();
        args.putString(ARG_MENU, menu);
        ChartContentFragment fragment = new ChartContentFragment_();
        fragment.setArguments(args);
        return fragment;
    }



    @AfterInject
    void afterInject() {
        Bundle args = getArguments();
        if (args != null) {
            mMenu = args.getString(ARG_MENU);
        }
    }

    @AfterViews
    void afterViews() {
        showCharts(mMenu);
        Logger.d(mMenu);
    }


    private void showCharts(String mMenu) {
        LineChartChartFragment fragment = LineChartChartFragment.newInstance();
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
