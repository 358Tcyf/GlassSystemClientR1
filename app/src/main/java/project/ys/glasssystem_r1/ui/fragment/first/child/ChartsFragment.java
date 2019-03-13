package project.ys.glasssystem_r1.ui.fragment.first.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.MenuSelectedEvent;
import project.ys.glasssystem_r1.common.event.SubMenuSelectedEvent;
import project.ys.glasssystem_r1.data.entity.BaseChart;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.ChartContract;
import project.ys.glasssystem_r1.mvp.presenter.ChartPresenter;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.ChartContentFragment;

import static com.alibaba.fastjson.JSON.toJSONString;


@EFragment(R.layout.fragment_charts_root)
public class ChartsFragment extends BaseBackFragment implements ChartContract.View {

    public static ChartsFragment newInstance() {
        Bundle args = new Bundle();
        ChartsFragment fragment = new ChartsFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    public static final String PUSH = "push";
    public static final String PUSH_CONTENT = "push_content";
    public static final String DEFAULT_SUBMENU = "default_submenu";

    public static ChartsFragment newInstance(Push push) {
        Bundle args = new Bundle();
        args.putParcelable(PUSH, push);
        args.putString(PUSH_CONTENT, push.getContent());
        args.putString(DEFAULT_SUBMENU, push.getDefaultSubMenu());
        ChartsFragment fragment = new ChartsFragment_();
        fragment.setArguments(args);
        return fragment;
    }


    @ViewById(R.id.nav_view_start)
    NavigationView navViewStart;
    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private Push push;
    private String content;
    private List<BaseChart> charts = new ArrayList<>();
    private List<String> subMenus = new ArrayList<>();

    private HashMap<MenuItem, ChartContentFragment> mFragments;
    private MenuItem defaultMenu;
    private String defaultSubMenu;
    private ChartPresenter chartPresenter;

    @AfterInject
    void afterInject() {
        chartPresenter = new ChartPresenter(this, _mActivity);
        push = getArguments().getParcelable(PUSH);
        if (push != null) {
            content = getArguments().getString(PUSH_CONTENT);
            charts = JSON.parseArray(content, BaseChart.class);
            for (BaseChart chart : charts) {
                subMenus.add(chart.getSubmenu());
            }
            defaultSubMenu = getArguments().getString(DEFAULT_SUBMENU);
        }
    }

    @AfterViews
    void afterViews() {
        if (drawerLayout == null) return;
//        initStartDrawerView();
        initDefaultMenu();
        initTopBar();
    }


    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            pop();
        });
        mTopBar.addRightImageButton(R.drawable.ic_more_vert, R.id.more)
                .setOnClickListener(view -> {
                    showBottomSheetList();
                });
    }

    @UiThread
    void initDefaultMenu() {
        mFragments = new HashMap<>();
        BaseChart defaultChart = charts.get(0);
        for (BaseChart chart : charts) {
            if (chart.getSubmenu().equals(defaultSubMenu))
                defaultChart = chart;
        }
        ChartContentFragment fragment = ChartContentFragment.newInstance(defaultChart);
        loadRootFragment(R.id.fl_content_container, fragment);
    }


    @Override
    public boolean onBackPressedSupport() {
        return false;
    }

    private void initStartDrawerView() {
        if (navViewStart == null) return;
        navViewStart.setNavigationItemSelectedListener(item ->
                this.onNavigationItemSelected(item, true));
    }

    private boolean onNavigationItemSelected(@NonNull final MenuItem item, final boolean isStartDrawer) {
        if (isStartDrawer) {
            EventBusActivityScope.getDefault(_mActivity).post(new MenuSelectedEvent(item.getItemId()));
        }
        closeDrawer();
        return true;
    }


    private void closeDrawer() {
        if (drawerLayout != null) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            if (drawerLayout.isDrawerOpen(GravityCompat.END))
                drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    private void showBottomSheetList() {
        QMUIBottomSheet.BottomListSheetBuilder builder =
                new QMUIBottomSheet.BottomListSheetBuilder(getContext());
        for (String submenu : subMenus) {
            builder.addItem(submenu, submenu);
        }
        builder.setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
            EventBusActivityScope.getDefault(_mActivity).post(new SubMenuSelectedEvent(charts.get(position)));
            chartPresenter.setDefault(content, subMenus.get(position));
            dialog.dismiss();
        });
        QMUIBottomSheet sheet = builder.build();
        sheet.show();
    }

}
