package project.ys.glasssystem_r1.ui.fragment.second.child;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.HashMap;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.fragment.second.child.user_hild.SectionInfoFragment;
import project.ys.glasssystem_r1.ui.fragment.second.child.user_hild.SelfInfoFragment;

import static android.text.TextUtils.isEmpty;
import static project.ys.glasssystem_r1.common.constant.Constant.FIRST;
import static project.ys.glasssystem_r1.common.constant.Constant.SECOND;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.HTTP;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.PORT;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.getURL;
import static project.ys.glasssystem_r1.util.utils.DateUtils.getNowTime;

@EFragment
public class UserFragment extends BaseBackFragment {
    private static final String CHECK_USER = "check_user";

    public static UserFragment newInstance(UserBeanPlus user) {
        Bundle args = new Bundle();
        args.putParcelable(CHECK_USER, user);
        UserFragment fragment = new UserFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewById(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    @ViewById(R.id.topbar)
    QMUITopBar mTopBar;
    @ViewById(R.id.imageView)
    ImageView userPic;
    @ViewById(R.id.tabs)
    QMUITabSegment mTabs;
    @ViewById(R.id.pager)
    ViewPager mPager;

    @StringArrayRes(R.array.userTabs)
    String[] detailsTab;

    private UserBeanPlus currentUser;

    @AfterInject
    void afterInject() {
        Bundle args = getArguments();
        if (args != null) {
            currentUser = args.getParcelable(CHECK_USER);
        }
    }

    @AfterViews
    void afterView() {
        initTopBar();
        initTabs();
        initPagers();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_qmui_collasping, container, false);
        return attachToSwipeBack(view);
    }

    private void initTopBar() {
        mCollapsingTopBarLayout.setTitle(currentUser.getName());
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            pop();
        });
        setUserPic();
    }

    @UiThread
    void setUserPic() {
        Bundle args = getArguments();
        if (args != null) {
            if (!isEmpty(currentUser.getPicPath())) {
                Glide.with(this)
                        .load(Uri.parse(HTTP + getURL() + PORT + currentUser.getPicPath() + "/" + getNowTime()))
                        .apply(new RequestOptions().error(R.mipmap.ic_account_circle))
                        .into(userPic);
            }
        }

    }

    private void initTabs() {
        int normalColor = QMUIResHelper.getAttrColor(getContext(), R.attr.colorPrimaryText);
        int selectColor = QMUIResHelper.getAttrColor(getContext(), R.attr.colorPrimaryDark);
        mTabs.setDefaultNormalColor(normalColor);
        mTabs.setDefaultSelectedColor(selectColor);
        mTabs.addTab(new QMUITabSegment.Tab(detailsTab[0]))
                .addTab(new QMUITabSegment.Tab(detailsTab[1]));
    }


    private HashMap<Integer, SupportFragment> mPages;
    private SelfInfoFragment selfInfoFragment;
    private SectionInfoFragment sectionInfoFragment;

    private void initPagers() {
        mPages = new HashMap<>();
        selfInfoFragment = new SelfInfoFragment().newInstance(currentUser);
        mPages.put(FIRST, selfInfoFragment);
        sectionInfoFragment = new SectionInfoFragment().newInstance(currentUser);
        mPages.put(SECOND, sectionInfoFragment);
        FragmentPagerAdapter mPageAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mPages.get(position);
            }

            @Override
            public int getCount() {
                return mPages.size();
            }
        };

        mPager.setAdapter(mPageAdapter);
        mTabs.setupWithViewPager(mPager, false);
    }


}
