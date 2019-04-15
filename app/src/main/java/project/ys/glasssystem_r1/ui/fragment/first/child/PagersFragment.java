package project.ys.glasssystem_r1.ui.fragment.first.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.tmall.ultraviewpager.UltraViewPager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.RefreshListEvent;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.data.entity.BaseChart;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.ChartContract;
import project.ys.glasssystem_r1.mvp.presenter.ChartPresenter;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.fragment.base.CommonChartFragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.CommonMoreBarChart;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.CommonOnlyBarChart;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.CommonPieChart;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.CommonRingChart;

import static project.ys.glasssystem_r1.data.entity.BaseChart.bar_chart;
import static project.ys.glasssystem_r1.data.entity.BaseChart.line_chart;
import static project.ys.glasssystem_r1.data.entity.BaseChart.pie_chart;
import static project.ys.glasssystem_r1.data.entity.BaseChart.ring_chart;
import static project.ys.glasssystem_r1.data.entity.Push.PUSH_CHARTS;


@EFragment
public class PagersFragment extends BaseBackFragment implements ChartContract.View {

    public static final String PUSH = "push";
    public static final String PUSH_CONTENT = "push_content";
    public static final String DEFAULT_SUBMENU = "default_submenu";

    public static PagersFragment newInstance(Push push) {
        Bundle args = new Bundle();
        args.putParcelable(PUSH, push);
        args.putString(PUSH_CONTENT, push.getContent());
        args.putString(DEFAULT_SUBMENU, push.getDefaultSubMenu());
        PagersFragment fragment = new PagersFragment_();
        fragment.setArguments(args);
        return fragment;
    }


    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;
    @ViewById(R.id.ultraViewPager)
    UltraViewPager mUltraViewPager;


    @ColorRes(R.color.colorText_Icon)
    int topbarText;

    @StringRes(R.string.cancel)
    String cancel;

    @StringRes(R.string.ok)
    String ok;

    private Push push;
    private String content;
    private List<BaseChart> charts = new ArrayList<>();
    private List<String> subMenus = new ArrayList<>();
    private HashMap<String, Boolean> isBrowses = new HashMap<>();

    private String defaultSubMenu;
    private ChartPresenter chartPresenter;
    private HashMap<Integer, CommonChartFragment> mPages = new HashMap<>();
    private List<CommonChartFragment> mFragments = new ArrayList<>();
    private BaseChart mBaseChart;
    private BaseChart preSBaseChart;
    private UserBeanPlus currentUser;

    @AfterInject
    void afterInject() {
        chartPresenter = new ChartPresenter(this, _mActivity);
        currentUser = CustomerApp.getInstance().getCurrentUser();
        chartPresenter.getTags(currentUser.getNo());
        push = getArguments().getParcelable(PUSH);
        if (push != null) {
            content = getArguments().getString(PUSH_CONTENT);
            charts = JSON.parseArray(content, BaseChart.class);
            defaultSubMenu = getArguments().getString(DEFAULT_SUBMENU);
        }
    }


    @AfterViews
    void afterViews() {
        initTopBar();
        sortCharts();
        initAdapter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_charts_pagers, container, false);
        return attachToSwipeBack(view);
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            pop();
        });
        mTopBar.setTitle(R.string.push_detail).setTextColor(topbarText);
        mTopBar.addRightImageButton(R.drawable.ic_more_vert, R.id.more)
                .setOnClickListener(view -> {
                    showBottomSheetList();
                });
    }

    private void initAdapter() {
        for (int i = 0; i < mFragments.size(); i++) {
            mPages.computeIfAbsent(i, i1 -> mFragments.get(i1));
        }
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
        mUltraViewPager.setScrollMode(UltraViewPager.ScrollMode.VERTICAL);
        mUltraViewPager.setAdapter(mPageAdapter);
        mUltraViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                isBrowses.put(subMenus.get(i), true);
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

    }


    private void showBottomSheetList() {
        QMUIBottomSheet.BottomListSheetBuilder builder =
                new QMUIBottomSheet.BottomListSheetBuilder(getContext());
        for (String submenu : subMenus) {
            builder.addItem(submenu, submenu);
        }
        builder.setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
            mUltraViewPager.setCurrentItem(position);
            chartPresenter.setDefault(push, subMenus.get(position));
            EventBusActivityScope.getDefault(_mActivity).post(new RefreshListEvent());
            dialog.dismiss();
        });
        QMUIBottomSheet sheet = builder.build();
        sheet.show();
    }


    private CommonChartFragment chartsContent(BaseChart baseChart) {
        CommonChartFragment fragment = null;
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
                fragment = CommonRingChart.newInstance(baseChart);
                break;
        }
        return fragment;
    }

    private String[] push_charts = new String[4];

    void sortCharts() {
        String temp = null;
        for (int i = 0; i < 4; i++) {
            if (PUSH_CHARTS[i].equals(defaultSubMenu)) {
                push_charts[0] = PUSH_CHARTS[i];
            }
        }
        for (int i = 0; i < 4; i++)
            for (BaseChart chart : charts) {
                if (chart.getSubmenu().equals(PUSH_CHARTS[i])) {
                    subMenus.add(chart.getSubmenu());
                    mFragments.add(chartsContent(chart));
                    isBrowses.put(PUSH_CHARTS[i], false);
                }
            }

//         if(!push.isHaveRead())
        chartPresenter.cutFirst(currentUser.getNo(), subMenus);
    }

    @Override
    public boolean onBackPressedSupport() {
        //         if(!push.isHaveRead())
        for (String tag : subMenus) {
            if (isBrowses.get(tag)) {
                chartPresenter.addOne(currentUser.getNo(), tag);
            }
        }
        chartPresenter.checkCount(currentUser.getNo(), subMenus);
        chartPresenter.setRead(push);
        EventBusActivityScope.getDefault(_mActivity).post(new RefreshListEvent());
        return super.onBackPressedSupport();
    }

    @Override
    public void showTips(String tipMsg) {
        QMUIDialog.CheckBoxMessageDialogBuilder logoutDailog = new QMUIDialog.CheckBoxMessageDialogBuilder(_mActivity);
        logoutDailog.setTitle(tipMsg)
                .setMessage("不再提示")
                .setChecked(false)
                .addAction(cancel, (dialog, index) -> dialog.dismiss())
                .addAction(ok, (dialog, index) -> {

                    dialog.dismiss();
                }).show();
    }
}
