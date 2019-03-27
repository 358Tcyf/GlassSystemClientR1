package project.ys.glasssystem_r1.ui.activity;

import android.annotation.SuppressLint;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.common.SplashFragment;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
@Fullscreen
public class SplashActivity extends SupportActivity {
    @AfterInject
    void afterInject() {
    }

    @AfterViews
    void afterViews() {
        SupportFragment fragment = findFragment(SplashFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.fl_container, SplashFragment.newInstance());
        }
    }
}
