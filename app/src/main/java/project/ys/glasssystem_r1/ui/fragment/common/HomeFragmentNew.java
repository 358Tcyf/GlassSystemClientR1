package project.ys.glasssystem_r1.ui.fragment.common;

import android.os.Bundle;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.StartBrotherEvent;
import project.ys.glasssystem_r1.ui.fragment.base.BaseMainFragment;
import project.ys.glasssystem_r1.common.event.TabSelectedEvent;
import project.ys.glasssystem_r1.ui.fragment.first.FirstTabFragment;
import project.ys.glasssystem_r1.ui.fragment.second.MemberFragment;
import project.ys.glasssystem_r1.ui.fragment.third.AboutFragment;
import project.ys.glasssystem_r1.ui.widget.bottombar.BottomBar;
import project.ys.glasssystem_r1.ui.widget.bottombar.BottomBarTab;

import static project.ys.glasssystem_r1.common.constant.UserConstant.USER_ACCOUNT;

@EFragment(R.layout.fragment_home_new)
public class HomeFragmentNew extends BaseMainFragment {

    private static final int REQ_MSG = 10;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    private SupportFragment[] mFragments = new SupportFragment[3];
    private String no;

    @ViewById(R.id.bottomBar)
    BottomBar mBottomBar;


    @AfterInject
    void afterInject() {
        no = getArguments().getString(USER_ACCOUNT);
        initFragment();
        EventBusActivityScope.getDefault(_mActivity).register(this);
    }

    @AfterViews
    void afterViews() {
        initBottomBar();
    }

    public static HomeFragmentNew newInstance() {
        return new HomeFragmentNew_();
    }

    public static HomeFragmentNew newInstance(String no) {
        Bundle args = new Bundle();
        args.putString(USER_ACCOUNT, no);
        HomeFragmentNew fragment = new HomeFragmentNew_();
        fragment.setArguments(args);
        return fragment;
    }


    private void initFragment() {

        SupportFragment firstFragment = findChildFragment(FirstTabFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = FirstTabFragment.newInstance();
            mFragments[SECOND] = MemberFragment.newInstance();
            mFragments[THIRD] = AboutFragment.newInstance(no);

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

    private void initBottomBar() {
        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_icon_statistics, R.drawable.ic_icon_statistics_fill, getString(R.string.tabPush)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_icon_meeting, R.drawable.ic_icon_meeting_fill, getString(R.string.tabMember)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_icon_work, R.drawable.ic_icon_work_fill, getString(R.string.tabAbout)));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                EventBusActivityScope.getDefault(_mActivity).post(new TabSelectedEvent(position));
            }
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

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }
}
