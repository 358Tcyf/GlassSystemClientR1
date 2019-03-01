package project.ys.glasssystem_r1.ui.activity;

import android.annotation.SuppressLint;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.HomeFragment;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class HomeActivity extends SupportActivity {
    @AfterViews
    void afterViews() {
        SupportFragment fragment = findFragment(HomeFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.fl_container, HomeFragment.newInstance("M001"));
        }
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }
}
