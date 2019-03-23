package project.ys.glasssystem_r1.ui.activity;

import android.annotation.SuppressLint;

import com.tencent.mmkv.MMKV;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.common.HomeFragmentPlus;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class HomeActivity extends SupportActivity {

    @AfterInject
    void afterInject() {
    }

    @AfterViews
    void afterViews() {
        SupportFragment fragment = findFragment(HomeFragmentPlus.class);
        if (fragment == null) {
            MMKV user = MMKV.defaultMMKV();
            loadRootFragment(R.id.fl_container, HomeFragmentPlus.newInstance());
        }
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

}
