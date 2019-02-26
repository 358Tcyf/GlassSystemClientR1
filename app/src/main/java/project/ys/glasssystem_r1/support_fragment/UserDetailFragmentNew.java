package project.ys.glasssystem_r1.support_fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.HashMap;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;

@EFragment(R.layout.fragment_qmui_collasping)
public class UserDetailFragmentNew extends SupportFragment {
    public static UserDetailFragmentNew newInstance() {
        return new UserDetailFragmentNew_();
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

    @AfterViews
    void afterView() {
        initTopBar();
        initTabs();
        initPagers();

    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            //TODO 关闭当前fragment
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

    private void initPagers() {
        mPages = new HashMap<>();

        SupportFragment pushFragment = new UserSelfInfoFragment().newInstance();
        mPages.put(Pager.SELF, pushFragment);
        SupportFragment memberFragment = new UserSelfInfoFragment().newInstance();
        mPages.put(Pager.SECTION, memberFragment);

        FragmentPagerAdapter mPageAdapter = new FragmentPagerAdapter(getFragmentManager()) {
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
