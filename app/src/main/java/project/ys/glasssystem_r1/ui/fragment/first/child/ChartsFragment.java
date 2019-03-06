package project.ys.glasssystem_r1.ui.fragment.first.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.MenuSelectedEvent;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.ChartContentFragment;


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

    private HashMap<MenuItem, ChartContentFragment> mFragments;
    private MenuItem defaultMenu;

    @AfterViews
    void afterViews() {
        if (drawerLayout == null) return;
        initStartDrawerView();
        initDefaultMenu();
    }


    private void initDefaultMenu() {
        mFragments = new HashMap<>();
        defaultMenu = navViewStart.getMenu().getItem(0);
        ChartContentFragment fragment = ChartContentFragment.newInstance(defaultMenu.getItemId());
        loadRootFragment(R.id.fl_content_container, fragment);
        defaultMenu.setChecked(true);
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


}
