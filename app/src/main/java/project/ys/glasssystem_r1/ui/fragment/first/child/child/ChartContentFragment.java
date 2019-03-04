package project.ys.glasssystem_r1.ui.fragment.first.child.child;

import android.os.Bundle;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIResHelper;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.greenrobot.eventbus.Subscribe;


import java.util.HashMap;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.MenuSelectedEvent;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.Item_1Fragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.Item_2Fragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.Item_3Fragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.Item_4Fragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.Item_5Fragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.Item_6Fragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.child.child.Item_7Fragment;

@EFragment(R.layout.fragment_content_main)
public class ChartContentFragment extends BaseBackFragment {

    public static ChartContentFragment newInstance() {
        Bundle args = new Bundle();
        ChartContentFragment fragment = new ChartContentFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String ARG_MENU = "arg_menu";
    private HashMap<Integer, BaseBackFragment> mFragments;
    private int position;
    private int prePosition;

    public static ChartContentFragment newInstance(int itemId) {
        Bundle args = new Bundle();
        args.putInt(ARG_MENU, itemId);
        ChartContentFragment fragment = new ChartContentFragment_();
        fragment.setArguments(args);
        return fragment;
    }


    @AfterInject
    void afterInject() {
        Bundle args = getArguments();
        if (args != null) {
            position = args.getInt(ARG_MENU);
        }
        EventBusActivityScope.getDefault(_mActivity).register(this);
    }

    @AfterViews
    void afterViews() {
        mFragments = new HashMap<>();
        initContentFragment();
    }

    @UiThread
    void initContentFragment() {
        BaseBackFragment fragment = chartsContent(position);
        mFragments.put(position, fragment);
        loadRootFragment(R.id.fl_content_container, fragment);
    }


    private BaseBackFragment chartsContent(int itemId) {
        BaseBackFragment fragment = null;
        switch (itemId) {
            case R.id.produce_count:
                fragment = Item_1Fragment.newInstance();
                break;
            case R.id.produce_model:
                fragment = Item_2Fragment.newInstance();
                break;
            case R.id.produce_quality:
                fragment = Item_3Fragment.newInstance();
                break;
            case R.id.produce_energy:
                fragment = Item_4Fragment.newInstance();
                break;
            case R.id.sale_count:
                fragment = Item_5Fragment.newInstance();
                break;
            case R.id.sale_profit:
                fragment = Item_6Fragment.newInstance();
                break;
            case R.id.sale_model:
                fragment = Item_7Fragment.newInstance();
                break;
            case R.id.sale_customer:
                fragment = Item_7Fragment.newInstance();
                break;
            default:

        }
        return fragment;
    }

    @Subscribe
    public void onMenuSelectedEvent(MenuSelectedEvent event) {
        prePosition = position;
        position = event.position;
        switchContentFragment();
    }

    private void switchContentFragment() {
        if (position == prePosition) {
            return;
        }
        if (mFragments.get(position) != null) {
            showHideFragment(mFragments.get(position), mFragments.get(prePosition));
            loadRootFragment(R.id.fl_content_container, mFragments.get(position));
        } else {
            BaseBackFragment fragment = chartsContent(position);
            mFragments.put(position, fragment);
            loadRootFragment(R.id.fl_content_container, fragment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }
}
