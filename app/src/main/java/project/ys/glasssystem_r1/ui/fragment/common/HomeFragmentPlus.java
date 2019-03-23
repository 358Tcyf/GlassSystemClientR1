package project.ys.glasssystem_r1.ui.fragment.common;

import android.os.Bundle;
import android.text.InputType;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import kotlin.Unit;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.StartBrotherEvent;
import project.ys.glasssystem_r1.common.event.TabSelectedEvent;
import project.ys.glasssystem_r1.ui.fragment.base.BaseMainFragment;
import project.ys.glasssystem_r1.ui.fragment.first.FirstTabFragment;
import project.ys.glasssystem_r1.ui.fragment.second.MemberFragment;
import project.ys.glasssystem_r1.ui.fragment.third.AboutFragment;
import project.ys.glasssystem_r1.ui.widget.bottomnavigation.BottomNavigation;

import static project.ys.glasssystem_r1.common.constant.Constant.FIRST;
import static project.ys.glasssystem_r1.common.constant.Constant.SECOND;
import static project.ys.glasssystem_r1.common.constant.Constant.THIRD;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.URL;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.changeURL;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.getURL;

@EFragment(R.layout.fragment_home_plus)
public class HomeFragmentPlus extends BaseMainFragment {

    private static final int REQ_MSG = 10;


    private SupportFragment[] mFragments = new SupportFragment[3];

    @ViewById(R.id.bottomNavigation)
    BottomNavigation mBottomNavigation;


    @AfterInject
    void afterInject() {
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initFragment();
    }

    @AfterViews
    void afterViews() {
        initBottomNavigation();
    }

    public static HomeFragmentPlus newInstance() {
        return new HomeFragmentPlus_();
    }

    private void initFragment() {

        SupportFragment firstFragment = findChildFragment(FirstTabFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = FirstTabFragment.newInstance();
            mFragments[SECOND] = MemberFragment.newInstance();
            mFragments[THIRD] = AboutFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(MemberFragment.class);
            mFragments[THIRD] = findChildFragment(AboutFragment.class);
        }
    }

    private void initBottomNavigation() {
        mBottomNavigation.add(new BottomNavigation.Model(FIRST, R.drawable.ic_icon_statistics, R.drawable.ic_icon_statistics_fill));
        mBottomNavigation.add(new BottomNavigation.Model(SECOND, R.drawable.ic_icon_meeting, R.drawable.ic_icon_meeting_fill));
        mBottomNavigation.add(new BottomNavigation.Model(THIRD, R.drawable.ic_icon_work, R.drawable.ic_icon_work_fill));
        mBottomNavigation.show(FIRST, false);
        mBottomNavigation.setOnClickMenuListener((MemoizedFunctionToNullable<BottomNavigation.Model, Unit>) model -> {
            showHideFragment(mFragments[model.getId()]);
            if (mBottomNavigation.isSameSelect())
                EventBusActivityScope.getDefault(_mActivity).post(new TabSelectedEvent(model.getId()));
            return null;
        });

    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MSG && resultCode == RESULT_OK) {

        }
    }



    @Subscribe
    public void onStartBrotherEvent(StartBrotherEvent event) {
        start(event.targetFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }


}
