package project.ys.glasssystem_r1.ui.fragment.second.child;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.HashMap;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.fragment.second.child.user_hild.SectionInfoFragment;
import project.ys.glasssystem_r1.ui.fragment.second.child.user_hild.SelfInfoFragment;

import static project.ys.glasssystem_r1.common.constant.UserConstant.USER_ACCOUNT;
import static project.ys.glasssystem_r1.common.constant.UserConstant.USER_NAME;

@EFragment(R.layout.fragment_qmui_collasping)
public class UserFragment extends BaseBackFragment {

    public static UserFragment newInstance(String no, String name) {
        Bundle args = new Bundle();
        args.putString(USER_ACCOUNT, no);
        args.putString(USER_NAME, name);
        UserFragment fragment = new UserFragment_();
        fragment.setArguments(args);
        return fragment;
    }


    @ViewById(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    @ViewById(R.id.topbar)
    QMUITopBar mTopBar;
    @ViewById(R.id.tabs)
    QMUITabSegment mTabs;
    @ViewById(R.id.pager)
    ViewPager mPager;

    @StringArrayRes(R.array.userDetails)
    String[] detailsTab;
    private String no;
    private String name;

    @AfterInject
    void afterInject(){
        no = getArguments().getString(USER_ACCOUNT);
        name = getArguments().getString(USER_NAME);
    }

    @AfterViews
    void afterView() {
        initTopBar();
        initTabs();
        initPagers();

    }

    private void initTopBar() {
        mCollapsingTopBarLayout.setTitle(name);
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            pop();
        });
    }

    private void initTabs() {
        int normalColor = QMUIResHelper.getAttrColor(getContext(), R.attr.colorPrimaryText);
        int selectColor = QMUIResHelper.getAttrColor(getContext(), R.attr.colorPrimaryDark);
        mTabs.setDefaultNormalColor(normalColor);
        mTabs.setDefaultSelectedColor(selectColor);
        mTabs.addTab(new QMUITabSegment.Tab(detailsTab[0]))
                .addTab(new QMUITabSegment.Tab(detailsTab[1]));
    }

    private HashMap<Pager, SupportFragment> mPages;
    private SupportFragment pushFragment;
    private SupportFragment memberFragment;

    private void initPagers() {
        mPages = new HashMap<>();
        pushFragment = new SelfInfoFragment().newInstance(no, name);
        mPages.put(Pager.SELF, pushFragment);
        memberFragment = new SectionInfoFragment().newInstance(no, name);
        mPages.put(Pager.SECTION, memberFragment);
        FragmentPagerAdapter mPageAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mPages.get(Pager.getPagerFromPosition(position));
            }

            @Override
            public int getCount() {
                return mPages.size();
            }
        };

        mPager.setAdapter(mPageAdapter);
        mTabs.setupWithViewPager(mPager, false);
    }

    enum Pager {
        SELF, SECTION;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return SELF;
                case 1:
                    return SECTION;
                default:
                    return SELF;
            }
        }
    }


}
