package project.ys.glasssystem_r1.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.HashMap;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.TabSelectedEvent;
import project.ys.glasssystem_r1.ui.fragment.base.BaseMainFragment;
import project.ys.glasssystem_r1.ui.fragment.first.PushFragment;
import project.ys.glasssystem_r1.ui.fragment.second.MemberFragmentNew;
import project.ys.glasssystem_r1.ui.fragment.third.AboutFragment;

import static project.ys.glasssystem_r1.common.constant.UserConstant.USER_ACCOUNT;

@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseMainFragment {

    @ViewById(R.id.homePager)
    ViewPager homePager;

    @ViewById(R.id.homeTabs)
    QMUITabSegment homeTabs;
    @StringRes(R.string.tabPush)
    String tabPush;

    @StringRes(R.string.tabMember)
    String tabMember;
    @StringRes(R.string.tabAbout)
    String tabAbout;

    @AfterViews
    void afterViews() {
        initTitles();
        initTabs();
        initPagers();
    }

    public static HomeFragment newInstance() {
        return new HomeFragment_();
    }

    public static HomeFragment newInstance(String no) {
        Bundle args = new Bundle();
        args.putString(USER_ACCOUNT, no);
        HomeFragment fragment = new HomeFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    private String no;
    private CharSequence[] titles;

    private void initTitles() {
        titles = new String[3];
        titles[0] = tabPush;
        titles[1] = tabMember;
        titles[2] = tabAbout;
    }

    @SuppressWarnings("ConstantConditions")
    private void initTabs() {
        int normalColor = QMUIResHelper.getAttrColor(getContext(), R.attr.colorDivider);
        int selectColor = QMUIResHelper.getAttrColor(getContext(), R.attr.colorPrimaryDark);
        homeTabs.setDefaultNormalColor(normalColor);
        homeTabs.setDefaultSelectedColor(selectColor);

        QMUITabSegment.Tab push = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.drawable.ic_icon_statistics),
                ContextCompat.getDrawable(getContext(), R.drawable.ic_icon_statistics_fill),
                titles[0], false
        );

        QMUITabSegment.Tab member = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.drawable.ic_icon_meeting),
                ContextCompat.getDrawable(getContext(), R.drawable.ic_icon_meeting_fill),
                titles[1], false
        );
        QMUITabSegment.Tab about = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.drawable.ic_icon_work),
                ContextCompat.getDrawable(getContext(), R.drawable.ic_icon_work_fill),
                titles[2], false
        );
        homeTabs.addTab(push)
                .addTab(member)
                .addTab(about);
    }

    private HashMap<Pager, SupportFragment> mPages;

    private void initPagers() {
        mPages = new HashMap<Pager, SupportFragment>();

        SupportFragment pushFragment = new PushFragment().newInstance();
        mPages.put(HomeFragment.Pager.PUSH, pushFragment);
        SupportFragment memberFragment = new MemberFragmentNew().newInstance();
        mPages.put(HomeFragment.Pager.PEOPLE, memberFragment);
        no = getArguments().getString(USER_ACCOUNT);
        SupportFragment settingFragment = new AboutFragment().newInstance(no);
        mPages.put(HomeFragment.Pager.SETTING, settingFragment);

        FragmentPagerAdapter mPageAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mPages.get(HomeFragment.Pager.getPagerFromPosition(position));
            }

            @Override
            public int getCount() {
                return mPages.size();
            }
        };

        homePager.setAdapter(mPageAdapter);
        homeTabs.setupWithViewPager(homePager, false);
        homeTabs.setOnTabClickListener(new QMUITabSegment.OnTabClickListener() {
            @Override
            public void onTabClick(int position) {

                EventBusActivityScope.getDefault(_mActivity).post(new TabSelectedEvent(position));

            }
        });
    }

    enum Pager {
        PUSH, PEOPLE, SETTING;

        public static HomeFragment.Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return PUSH;
                case 1:
                    return PEOPLE;
                case 2:
                    return SETTING;
                default:
                    return PUSH;
            }
        }
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }
}
