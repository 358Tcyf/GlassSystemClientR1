package project.ys.glasssystem_r1.ui.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.base.BaseMainFragment;

@EFragment(R.layout.fragment_data_charts_root)
public class DataChartsMainFragment extends BaseMainFragment {

    public static DataChartsMainFragment newInstance() {
        return new DataChartsMainFragment_();
    }


    @ViewById(R.id.nav_view_start)
    NavigationView navViewStart;
    @ViewById(R.id.nav_view_end)
    NavigationView navViewEnd;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;

    private boolean startDrawerEnable;
    private boolean endDrawerEnable;

    @AfterViews
    void afterViews() {
        if (drawerLayout == null) return;
        setStartDrawerEnable(true);
        setEndDrawerEnable(true);
        initStartDrawerView();
        initEndDrawerView();

        initTopBar();
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> pop());
    }


    private void initEndDrawerView() {
        if (navViewEnd == null) return;
        if (endDrawerEnable) {
            navViewEnd.setNavigationItemSelectedListener(item -> {
//                closeDrawer();
                return DataChartsMainFragment.this.onNavigationItemSelected(item, false);
            });
        } else {
            drawerLayout.removeView(navViewEnd);
        }
    }

    private void initStartDrawerView() {
        if (navViewStart == null) return;
        if (startDrawerEnable) {
            navViewStart.setNavigationItemSelectedListener(item -> {
//                    closeDrawer();
                return DataChartsMainFragment.this.onNavigationItemSelected(item, true);
            });
        } else {
            drawerLayout.removeView(navViewStart);
        }
    }


    @Override
    public boolean onBackPressedSupport() {
        if (drawerLayout != null && (drawerLayout.isDrawerOpen(GravityCompat.START)
                || drawerLayout.isDrawerOpen(GravityCompat.END))) {
            closeDrawer();
            return true;
        } else {
            pop();
            return true;
        }
    }

    private boolean onNavigationItemSelected(@NonNull final MenuItem item, final boolean isStartDrawer) {
        switch (item.getOrder()) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
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

    private void setEndDrawerEnable(boolean endDrawerEnable) {
        this.endDrawerEnable = endDrawerEnable;
    }

    private void setStartDrawerEnable(boolean startDrawerEnable) {
        this.startDrawerEnable = startDrawerEnable;
    }

}
