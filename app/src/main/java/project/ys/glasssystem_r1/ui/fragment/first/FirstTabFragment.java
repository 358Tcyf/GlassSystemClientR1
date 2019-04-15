package project.ys.glasssystem_r1.ui.fragment.first;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringArrayRes;
import org.androidannotations.annotations.res.StringRes;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.FirstTabMenuEvent;
import project.ys.glasssystem_r1.common.event.FirstTabSelectedEvent;
import project.ys.glasssystem_r1.common.event.TabSelectedEvent;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.AlarmFragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.PushFragment;

import static project.ys.glasssystem_r1.common.constant.Constant.FIRST;
import static project.ys.glasssystem_r1.common.constant.Constant.SECOND;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITopBarHelper.addBtnItem;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITopBarHelper.addViews;

@EFragment(R.layout.fragment_push_root)
public class FirstTabFragment extends BaseBackFragment {
    public static FirstTabFragment newInstance() {
        return new FirstTabFragment_();
    }

    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;
    QMUITabSegment mTabs;
    @ViewById(R.id.pager)
    ViewPager mPager;

    QMUIAlphaImageButton upDownBtn;
    QMUIAlphaImageButton searchBtn;
    QMUIAlphaImageButton sortBtn;



    @StringArrayRes(R.array.pushTabs)
    String[] pushTabs;

    @AfterInject
    void afterInject() {
        EventBusActivityScope.getDefault(_mActivity).register(this);
    }

    @AfterViews
    void afterView() {
        initTopBar();
        initPagers();

    }

    private void initTopBar() {

        LayoutInflater inflater = LayoutInflater.from(_mActivity);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMarginStart(QMUIDisplayHelper.dp2px(_mActivity, 120));
        layoutParams.setMarginEnd(QMUIDisplayHelper.dp2px(_mActivity, 120));
        mTabs = (QMUITabSegment) inflater.inflate(R.layout.layout_tab, null);
        mTopBar.addLeftView(mTabs, R.id.tab, layoutParams);

        int normalColor = QMUIResHelper.getAttrColor(_mActivity, R.attr.colorPrimary);
        int selectColor = QMUIResHelper.getAttrColor(_mActivity, R.attr.colorText_Icon);
        mTabs.setDefaultNormalColor(normalColor);
        mTabs.setDefaultSelectedColor(selectColor);
        mTabs.addTab(new QMUITabSegment.Tab(pushTabs[FIRST]))
                .addTab(new QMUITabSegment.Tab(pushTabs[SECOND]));


//
        RelativeLayout.LayoutParams btnLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        btnLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        upDownBtn = addBtnItem(_mActivity, R.drawable.ic_up_down);
        searchBtn = addBtnItem(_mActivity, R.drawable.ic_search);
        sortBtn = addBtnItem(_mActivity, R.drawable.ic_sort);
        upDownBtn.setOnClickListener(v ->
                EventBusActivityScope.getDefault(_mActivity).post(new FirstTabMenuEvent(mTabs.getSelectedIndex(), strSync))
        );
        searchBtn.setOnClickListener(v ->
                EventBusActivityScope.getDefault(_mActivity).post(new FirstTabMenuEvent(mTabs.getSelectedIndex(), strSearch))
        );
        sortBtn.setOnClickListener(v ->
                EventBusActivityScope.getDefault(_mActivity).post(new FirstTabMenuEvent(mTabs.getSelectedIndex(), strSort))
        );
        mTopBar.addRightView(addViews(_mActivity, upDownBtn, searchBtn, sortBtn), R.id.btn, btnLayoutParams);

    }


    private HashMap<Integer, SupportFragment> mPages;
    private SupportFragment pushFragment;
    private SupportFragment alarmFragment;

    private void initPagers() {
        mPages = new HashMap<>();
        pushFragment = new PushFragment().newInstance();
        mPages.put(FIRST, pushFragment);
        alarmFragment = new AlarmFragment().newInstance();
        mPages.put(SECOND, alarmFragment);
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

    @DrawableRes(R.drawable.ic_user_search)
    Drawable icSearch;
    @DrawableRes(R.drawable.ic_user_sort)
    Drawable icSort;
    @DrawableRes(R.drawable.ic_user_refresh)
    Drawable icRefresh;
    @StringRes(R.string.sync)
    String strSync;
    @StringRes(R.string.search)
    String strSearch;
    @StringRes(R.string.sort)
    String strSort;
    @StringRes(R.string.refresh)
    String strRefresh;

    private void showBottomSheetList() {
        QMUIBottomSheet.BottomListSheetBuilder builder =
                new QMUIBottomSheet.BottomListSheetBuilder(getContext());
        builder.addItem(icSearch, strSearch)
                .addItem(icSort, strSort)
                .addItem(icRefresh, strRefresh)
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    EventBusActivityScope.getDefault(_mActivity).post(new FirstTabMenuEvent(mTabs.getSelectedIndex(), tag));
                    dialog.dismiss();
                });
        QMUIBottomSheet sheet = builder.build();
        sheet.show();
    }


    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != FIRST) return;
        EventBusActivityScope.getDefault(_mActivity).post(new FirstTabSelectedEvent(mTabs.getSelectedIndex()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }
}
