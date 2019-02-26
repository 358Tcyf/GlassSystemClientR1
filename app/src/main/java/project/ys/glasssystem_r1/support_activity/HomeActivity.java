package project.ys.glasssystem_r1.support_activity;

import android.annotation.SuppressLint;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.support_fragment.UserDetailFragmentNew;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class HomeActivity extends SupportActivity {
    @AfterViews
    void afterViews() {
        SupportFragment fragment = findFragment(UserDetailFragmentNew.class);
        if (fragment == null) {
            loadRootFragment(R.id.fl_container, UserDetailFragmentNew.newInstance());
        }
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }
}
