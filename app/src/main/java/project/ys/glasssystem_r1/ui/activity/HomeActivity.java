package project.ys.glasssystem_r1.ui.activity;

import android.annotation.SuppressLint;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.HomeFragment;
import project.ys.glasssystem_r1.ui.fragment.HomeFragmentNew;
import project.ys.glasssystem_r1.ui.fragment.first.child.ChartsFragment;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class HomeActivity extends SupportActivity {

    @AfterInject
    void afterInject() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @AfterViews
    void afterViews() {
        SupportFragment fragment = findFragment(ChartsFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.fl_container, ChartsFragment.newInstance());
        }
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }
}
