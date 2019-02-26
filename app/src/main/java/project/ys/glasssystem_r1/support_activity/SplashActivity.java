package project.ys.glasssystem_r1.support_activity;

import android.annotation.SuppressLint;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.support_fragment.SplashFragment;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class SplashActivity extends SupportActivity {
    @AfterViews
    void afterViews() {
        SupportFragment fragment = findFragment(SplashFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.fl_container, SplashFragment.newInstance());
        }
    }
}
