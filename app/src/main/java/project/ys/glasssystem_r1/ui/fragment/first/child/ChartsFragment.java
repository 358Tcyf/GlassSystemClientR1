package project.ys.glasssystem_r1.ui.fragment.first.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.charts_child.ChartContentFragment;


@EFragment(R.layout.fragment_charts_root)
public class ChartsFragment extends BaseBackFragment {

    public static ChartsFragment newInstance() {
        Bundle args = new Bundle();
        ChartsFragment fragment = new ChartsFragment_();
        fragment.setArguments(args);
        return fragment;
    }


    @ViewById(R.id.nav_view_start)
    NavigationView navViewStart;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @AfterViews
    void afterViews() {
        if (drawerLayout == null) return;
        initStartDrawerView();
        showContent("生产量");
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
//        switch (item.getOrder()) {
//            case 1:
//                break;
//            case 2:
//                break;
//            case 3:
//                break;
//        }
        Logger.d(item.getTitle().toString());
        showContent(item.getTitle().toString());
        closeDrawer();
        return true;
    }

    private void showContent(String mMenu) {
        ChartContentFragment fragment = ChartContentFragment.newInstance(mMenu);
        switchContentFragment(fragment);
    }

    private void closeDrawer() {
        if (drawerLayout != null) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            if (drawerLayout.isDrawerOpen(GravityCompat.END))
                drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    /**
     * 替换加载 内容Fragment
     *
     * @param fragment
     */
    public void switchContentFragment(ChartContentFragment fragment) {
        SupportFragment contentFragment = findChildFragment(ChartContentFragment.class);
        if (contentFragment != null) {
            contentFragment.replaceFragment(fragment, false);
        }else {
            loadRootFragment(R.id.fl_content_container, fragment);
        }
    }
}
